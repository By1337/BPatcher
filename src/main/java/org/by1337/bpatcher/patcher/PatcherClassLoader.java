package org.by1337.bpatcher.patcher;

import org.by1337.bpatcher.patcher.api.Patcher;
import org.jetbrains.annotations.Nullable;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import java.util.logging.Logger;

public class PatcherClassLoader extends URLClassLoader {
    private final File file;
    private final JarFile jar;

    public PatcherClassLoader(@Nullable ClassLoader parent, File file) throws IOException {
        super(new URL[]{file.toURI().toURL()}, parent);

        this.file = file;
        this.jar = new JarFile(file);
    }

    public List<Patcher> findPatches() {
        List<Patcher> list = new ArrayList<>();

        Enumeration<JarEntry> entries = jar.entries();

        while (entries.hasMoreElements()) {
            JarEntry entry = entries.nextElement();
            String entryName = entry.getName();

            if (entryName.endsWith(".class")) {
                String className = entryName.replace("/", ".").replace(".class", "");

                try {
                    Class<?> cls = loadClass(className);

                    if (Patcher.class.isAssignableFrom(cls) && !cls.isInterface()) {
                        Class<? extends Patcher> pathClass = cls.asSubclass(Patcher.class);
                        list.add(pathClass.newInstance());
                    }
                } catch (ClassNotFoundException | InstantiationException | IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }

        return list;
    }

    @Override
    public void close() throws IOException {
        try {
            super.close();
        } finally {
            jar.close();
        }
    }
}
