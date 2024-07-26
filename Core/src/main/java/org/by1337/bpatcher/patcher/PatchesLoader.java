package org.by1337.bpatcher.patcher;

import org.by1337.bpatcher.patcher.api.Patcher;
import org.by1337.bpatcher.util.FunctionToByteArray;
import org.by1337.bpatcher.util.Pair;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.jar.JarEntry;
import java.util.zip.ZipEntry;

public class PatchesLoader {
    private final File dir;
    private final List<PatcherClassLoader> patcherClassLoaders = new ArrayList<>();
    private final List<Patcher> patchers = new ArrayList<>();
    private final Map<String, List<Patcher>> patchersByClass = new HashMap<>();
    private final Map<PatcherClassLoader, List<Pair<ZipEntry, FunctionToByteArray<ZipEntry>>>> injectClassesMap = new HashMap<>();

    public PatchesLoader(File dir) {
        this.dir = dir;
        dir.mkdirs();
    }

    public void load() {
        for (File file : dir.listFiles()) {
            if (!file.isDirectory() && file.getName().endsWith(".jar")) {
                try {
                    PatcherClassLoader classLoader = new PatcherClassLoader(this.getClass().getClassLoader(), file);
                    patchers.addAll(classLoader.findPatches());
                    List<Pair<ZipEntry, FunctionToByteArray<ZipEntry>>> toInject = classLoader.findInjectClasses();
                    if (!toInject.isEmpty())
                        injectClassesMap.put(classLoader, toInject);
                } catch (IOException e) {
                    System.err.println("Failed to load file " + file.getPath());
                    e.printStackTrace(System.err);
                }
            }
        }
        for (Patcher patcher : patchers) {
            patchersByClass.computeIfAbsent(patcher.targetClass(), k -> new ArrayList<>()).add(patcher);
        }

    }

    public void close() {
        for (PatcherClassLoader patcherClassLoader : patcherClassLoaders) {
            try {
                patcherClassLoader.close();
            } catch (IOException e) {
                e.printStackTrace(System.err);
            }
        }
        patcherClassLoaders.clear();
        patchers.clear();
    }

    @NotNull
    public List<Patcher> getPatchersByClass(String clazz) {
        return patchersByClass.getOrDefault(clazz, Collections.emptyList());
    }

    public List<Patcher> getPatchers() {
        return patchers;
    }

    public Map<PatcherClassLoader, List<Pair<ZipEntry, FunctionToByteArray<ZipEntry>>>> getInjectClassesMap() {
        return injectClassesMap;
    }
}
