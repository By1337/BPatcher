package org.by1337.bpatcher.patcher;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import org.by1337.bpatcher.patcher.api.Inject;
import org.by1337.bpatcher.patcher.api.Patcher;
import org.by1337.bpatcher.patcher.api.PatcherInit;
import org.by1337.bpatcher.util.FunctionToByteArray;
import org.by1337.bpatcher.util.Pair;
import org.jetbrains.annotations.Nullable;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.tree.ClassNode;

import java.io.*;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.*;
import java.util.function.Function;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import java.util.logging.Logger;
import java.util.zip.ZipEntry;

public class PatcherClassLoader extends URLClassLoader {
    private final File file;
    private final JarFile jar;
    private Set<String> injectClasses = new HashSet<>();
    private Set<String> patchClasses = new HashSet<>();
    private String main;
    private PatcherInit patcherInit;
    private PatchData patchData;

    public PatcherClassLoader(@Nullable ClassLoader parent, File file) throws IOException {
        super(new URL[]{file.toURI().toURL()}, parent);

        this.file = file;
        this.jar = new JarFile(file);
        load();
        if (main != null) {
            try {
                Class<?> mainClazz = loadClass(main);
                if (PatcherInit.class.isAssignableFrom(mainClazz) && !mainClazz.isInterface()) {
                    Class<? extends PatcherInit> pathClass = mainClazz.asSubclass(PatcherInit.class);
                    patcherInit = pathClass.newInstance();
                    patchData = new PatchData(file, this);
                    patcherInit.onLoad(patchData);
                }
            } catch (ClassNotFoundException e) {
                new ClassNotFoundException("Failed to load main class! main: " + main, e).printStackTrace();
            } catch (InstantiationException | IllegalAccessException e) {
                e.printStackTrace();
            }
        }
    }

    private void load() throws IOException {
        JarEntry entry = jar.getJarEntry("bpatcher-lookup.json");
        if (entry != null) {
            try (InputStream inputStream = jar.getInputStream(entry);
                 InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
                 BufferedReader reader = new BufferedReader(inputStreamReader)) {
                Gson gson = new Gson();
                JsonObject jsonObject = gson.fromJson(reader, JsonObject.class);
                if (jsonObject.has("inject")) {
                    injectClasses = new HashSet<>(asList(jsonObject.getAsJsonArray("inject")).stream().map(JsonElement::getAsString).toList());
                }
                if (jsonObject.has("patch")) {
                    patchClasses = new HashSet<>(asList(jsonObject.getAsJsonArray("patch")).stream().map(JsonElement::getAsString).toList());
                }
                if (jsonObject.has("main")) {
                    main = jsonObject.get("main").getAsString();
                }
            }
        }
    }

    private List<JsonElement> asList(JsonArray array) {
        List<JsonElement> result = new ArrayList<>(array.size());
        for (JsonElement element : array) {
            result.add(element);
        }
        return result;
    }

    public List<Patcher> findPatches() {
        List<Patcher> list = new ArrayList<>();
        for (String patchClass : patchClasses) {
            try {
                Class<?> cls = loadClass(patchClass);
                if (Patcher.class.isAssignableFrom(cls) && !cls.isInterface()) {
                    Class<? extends Patcher> pathClass = cls.asSubclass(Patcher.class);
                    list.add(pathClass.newInstance());
                }
            } catch (ClassNotFoundException | InstantiationException | IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        if (patchData != null)
            list.addAll(patchData.getCustomPatches());
        return list;
    }

    public List<Pair<ZipEntry, FunctionToByteArray<ZipEntry>>> findInjectClasses() {
        List<Pair<ZipEntry, FunctionToByteArray<ZipEntry>>> list = new ArrayList<>();
        for (String injectClass : injectClasses) {
            String entryName = injectClass.replace(".", "/") + ".class";
            JarEntry entry = jar.getJarEntry(entryName);
            if (entry != null) {
                list.add(Pair.of(entry, zipEntry -> {
                    try {
                        return jar.getInputStream(zipEntry).readAllBytes();
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }));
            } else {
                System.err.println("Failed to find class " + injectClass);
            }
        }
        if (patchData != null) {
            for (Class<?> aClass : patchData.getCustomInject()) {
                String entryName = aClass.getName().replace(".", "/") + ".class";
                JarEntry entry = jar.getJarEntry(entryName);
                if (entry != null) {
                    list.add(Pair.of(entry, zipEntry -> {
                        try {
                            return jar.getInputStream(zipEntry).readAllBytes();
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                    }));
                } else {
                    System.err.println("Failed to find class " + aClass.getName());
                }
            }
            for (ZipEntry zipEntryIn : patchData.getCustomInjectEntry()) {
                JarEntry zipEntry = jar.getJarEntry(zipEntryIn.getName());
                if (zipEntry != null) {
                    list.add(Pair.of(zipEntry, zipEntry0 -> {
                        try {
                            return jar.getInputStream(zipEntry0).readAllBytes();
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                    }));
                } else {
                    System.err.println("Failed to find class " + zipEntry.getName());
                }
            }
            for (ClassNode classNode : patchData.getCustomInjectClassNode()) {
                String zipName = classNode.name.replace(".", "/") + ".class";
                ZipEntry zipEntry = new ZipEntry(zipName);
                list.add(Pair.of(zipEntry, zip -> {
                    ClassWriter writer = new ClassWriter(0);
                    classNode.accept(writer);
                    return writer.toByteArray();
                }));
            }
        }
        return list;
    }

    public JarFile getJar() {
        return jar;
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
