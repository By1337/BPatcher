package org.by1337.bpatcher;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import org.by1337.bpatcher.patcher.PatchesLoader;
import org.by1337.bpatcher.patcher.api.Patcher;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.tree.ClassNode;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.instrument.ClassFileTransformer;
import java.lang.instrument.IllegalClassFormatException;
import java.lang.instrument.Instrumentation;
import java.security.ProtectionDomain;
import java.util.List;

public class Main {


    public static void premain(String args, Instrumentation inst) {

        File patches = new File("./patches");
        PatchesLoader patchesLoader = new PatchesLoader(patches);
        patchesLoader.load();
        System.out.printf("Loaded %d patches\n", patchesLoader.getPatchers().size());
        inst.addTransformer(new ClassFileTransformer() {
            @Override
            public byte[] transform(ClassLoader loader, String className, Class<?> classBeingRedefined, ProtectionDomain protectionDomain, byte[] bytes) throws IllegalClassFormatException {
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
                if (patchers == null) return null;

                try {
                    ClassReader reader = new ClassReader(bytes);
                    ClassWriter writer = new ClassWriter(0);

                    ClassNode node = new ClassNode();
                    reader.accept(node, ClassReader.EXPAND_FRAMES);

                    for (Patcher patcher : patchers) {
                        patcher.apply(node);
                    }
                    node.accept(writer);
                    System.out.printf("Applied %d patches for %s\n", patchers.size(), className);
                    return writer.toByteArray();
                } catch (Throwable throwable) {
                    System.out.println("Failed to apply patches for " + className);
                    throwable.printStackTrace();
                }
                return null;
            }
        });

    }
}
