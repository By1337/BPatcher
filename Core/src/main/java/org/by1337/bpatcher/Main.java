package org.by1337.bpatcher;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import org.by1337.bpatcher.patcher.PatcherClassLoader;
import org.by1337.bpatcher.patcher.PatchesLoader;
import org.by1337.bpatcher.patcher.api.Patcher;
import org.by1337.bpatcher.util.FunctionToByteArray;
import org.by1337.bpatcher.util.Pair;
import org.by1337.bpatcher.util.clazz.ClassFinder;
import org.by1337.bpatcher.util.clazz.JarClassLoader;
import org.by1337.bpatcher.util.clazz.write.CustomClassWriter;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.tree.ClassNode;

import java.io.*;
import java.lang.instrument.ClassFileTransformer;
import java.lang.instrument.Instrumentation;
import java.security.ProtectionDomain;
import java.util.List;
import java.util.Map;
import java.util.jar.JarFile;
import java.util.jar.JarOutputStream;
import java.util.zip.ZipEntry;

public class Main {
    private static ClassFinder classFinder;

    public static void premain(String args, Instrumentation inst) {
        File patches = new File("./patches");
        JarClassLoader loader = new JarClassLoader(new File("./"), f -> {
            return f.equals(patches) || f.getPath().contains("\\cache\\mojang_") || f.getPath().contains("\\plugins\\.paper-remapped");
        });
        classFinder = loader.load();

        PatchesLoader patchesLoader = new PatchesLoader(patches);
        patchesLoader.load();
        System.out.printf("Loaded %d patches\n", patchesLoader.getPatchers().size());
        injectClasses(inst, patchesLoader);

        inst.addTransformer(new ClassFileTransformer() {
            @Override
            public byte[] transform(ClassLoader loader, String className, Class<?> classBeingRedefined, ProtectionDomain protectionDomain, byte[] bytes) {
                if (className.equals("org/bukkit/craftbukkit/Main")) {

                    InputStream version = loader.getResourceAsStream("version.json");
                    if (version == null) {
                        throw new IllegalArgumentException("Unknown version!");
                    }
                    Gson gson = new Gson();
                    JsonObject jsonObject = new Gson().fromJson(new InputStreamReader(version), JsonObject.class);
                    String ver;
                    if (jsonObject.has("id")) {
                        ver = jsonObject.getAsJsonPrimitive("id").getAsString();
                    } else if (jsonObject.has("name")) {
                        ver = jsonObject.getAsJsonPrimitive("name").getAsString();
                    } else {
                        throw new IllegalArgumentException("Unknown version!" + gson.toJson(jsonObject));
                    }
                    Version.setCurrent(Version.getVersionById(ver));
                    System.out.println("Version: " + Version.getCurrent().getId());

                    try {
                        version.close();
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }

                List<Patcher> patchers = patchesLoader.getPatchersByClass(className);
                if (patchers.isEmpty()) return null;

                try {
                    ClassReader reader = new ClassReader(bytes);


                    ClassNode node = new ClassNode();
                    reader.accept(node, ClassReader.EXPAND_FRAMES);

                    boolean needRecomputeFrames = false;
                    for (Patcher patcher : patchers) {
                        patcher.apply(node);
                        if (patcher.needRecomputeFrames()) needRecomputeFrames = true;
                    }

                    ClassWriter writer = new CustomClassWriter(
                            needRecomputeFrames ? ClassWriter.COMPUTE_FRAMES : ClassWriter.COMPUTE_MAXS,
                            classFinder
                    );
                    node.accept(writer);
                    if (!patchers.isEmpty()) {
                        System.out.printf("Applied %d patches for %s\n", patchers.size(), className);
                    }
                    return writer.toByteArray();
                } catch (Throwable throwable) {
                    System.out.println("Failed to apply patches for " + className);
                    throwable.printStackTrace();
                }
                return null;
            }
        }, true);
    }

    private static void injectClasses(Instrumentation inst, PatchesLoader patchesLoader) {
        if (patchesLoader.getInjectClassesMap().isEmpty()) return;
        File cache = new File("./patches/cache");
        cache.mkdirs();

        int counter = 0;
        File outFile = new File(cache, "injected.jar");
        try (JarOutputStream out = new JarOutputStream(new FileOutputStream(outFile))) {

            Map<PatcherClassLoader, List<Pair<ZipEntry, FunctionToByteArray<ZipEntry>>>> map = patchesLoader.getInjectClassesMap();

            for (List<Pair<ZipEntry, FunctionToByteArray<ZipEntry>>> value : map.values()) {
                for (Pair<ZipEntry, FunctionToByteArray<ZipEntry>> pair : value) {
                    out.putNextEntry(pair.getLeft());
                    out.write(pair.getRight().apply(pair.getLeft()));
                    counter++;
                }
            }
        } catch (IOException e) {
            throw new RuntimeException("Failed to create injected classes jar!", e);
        }

        try {
            inst.appendToSystemClassLoaderSearch(new JarFile(outFile));
        } catch (IOException e) {
            throw new RuntimeException("Failed to injected classes!", e);
        }
        System.out.printf("Injected %d classes\n", counter);
    }
}
