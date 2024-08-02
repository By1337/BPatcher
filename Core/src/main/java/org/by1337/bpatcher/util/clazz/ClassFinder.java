package org.by1337.bpatcher.util.clazz;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.Nullable;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.tree.ClassNode;

import java.io.IOException;
import java.io.InputStream;
import java.util.*;

public class ClassFinder {
    private ClassCollection classCollection;
    private final Set<String> seenIllegalLoad = Collections.synchronizedSet(new HashSet<>());
    private int loadedClassesCount;

    public ClassFinder(Map<String, JarData> jars) {
        classCollection = new ClassCollection(jars.values());
    }

    @Nullable
    @Contract("null -> null")
    public ClassData findClass(String clazzName) {
        if (clazzName == null) return null;
        ClassData classData = classCollection.findClass(clazzName);
        if (classData == null) {
            if (!clazzName.startsWith("java/") && !seenIllegalLoad.contains(clazzName)) {
                seenIllegalLoad.add(clazzName);
                System.err.println("Attempt to load non-java base class: " + clazzName);
            }
            classData = load(clazzName);
            if (classData != null) {
                classCollection.putClass(clazzName.split("/"), 0, classData);
                classData.setFinder(this);
            }
            return classData;
        }
        return classData;
    }

    private ClassData load(String name) {
        try {
            ClassLoader loader = Thread.currentThread().getContextClassLoader();

            if (loader == null) {
                return null;
            }
            String className = name + ".class";
            InputStream classStream = loader.getResourceAsStream(className);

            if (classStream == null) {
                return null;
            }

            ClassReader classReader = new ClassReader(classStream);
            ClassNode node = new ClassNode();
            classReader.accept(node, ClassReader.SKIP_CODE);

            return new ClassData(
                    node.name,
                    node.superName,
                    node.access,
                    node.version,
                    node.interfaces
            );
        } catch (IOException ignore) {

        }
        return null;
    }

    public int getLoadedClassesCount() {
        return loadedClassesCount;
    }

    private class ClassCollection {
        private Map<String, ClassCollection> child = new HashMap<>();
        private Map<String, ClassData> classes = new HashMap<>();

        public ClassCollection(Collection<JarData> jarDataCollection) {
            for (JarData jarData : jarDataCollection) {
                for (ClassData aClass : jarData.getClasses()) {
                    putClass(aClass.getName().split("/"), 0, aClass);
                    aClass.setFinder(ClassFinder.this);
                }
            }
        }

        public ClassCollection() {
        }

        private synchronized void putClass(String[] clazzName, int index, ClassData data) {
            if (index == clazzName.length - 1) {
                classes.put(clazzName[index], data);
            } else {
                ClassCollection classCollection = child.computeIfAbsent(clazzName[index], k -> new ClassCollection());
                classCollection.putClass(clazzName, index + 1, data);
                loadedClassesCount++;
            }
        }

        @Nullable
        public ClassData findClass(String clazzName) {
            return findClass(clazzName.split("/"), 0);
        }

        @Nullable
        private synchronized ClassData findClass(String[] clazzName, int index) {
            if (index == clazzName.length - 1) {
                return classes.get(clazzName[index]);
            } else {
                ClassCollection classCollection = child.get(clazzName[index]);
                if (classCollection == null) return null;
                return classCollection.findClass(clazzName, index + 1);
            }
        }
    }

}
