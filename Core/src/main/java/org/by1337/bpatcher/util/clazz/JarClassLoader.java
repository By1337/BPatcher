package org.by1337.bpatcher.util.clazz;

import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.jetbrains.annotations.Nullable;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.tree.ClassNode;

import java.io.*;
import java.lang.reflect.Type;
import java.nio.file.Files;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.*;
import java.util.function.Predicate;
import java.util.jar.JarFile;

public class JarClassLoader {
    private final File workDir;
    private final Predicate<File> filter;
    private final File data;
    private Map<String, JarData> cashedJars;
    private Map<String, JarData> jars;
    private boolean hasNew;


    public JarClassLoader(File workDir, Predicate<File> filter) {
        this.workDir = workDir;
        this.filter = filter;
        data = new File(workDir, "bpatcher-classes.json");
    }

    public ClassFinder load() {
        if (data.exists()) {
            Gson gson = new Gson();
            try (Reader reader = new FileReader(data)) {
                Type type = new TypeToken<Map<String, JarData>>() {
                }.getType();

                cashedJars = gson.fromJson(reader, type);

            } catch (IOException ioException) {
                System.err.println("Failed to load bpatcher-classes.json!");
                ioException.printStackTrace(System.err);
                data.delete();
            }
        } else {
            cashedJars = Collections.emptyMap();
        }
        List<JarData> list = load(workDir);
        cashedJars = Collections.emptyMap();
        jars = new HashMap<>();
        for (JarData jarData : list) {
            jars.put(jarData.getSha1(), jarData);
        }
        if (hasNew) {
            Gson gson = new GsonBuilder().disableHtmlEscaping().create();
            try {
                Files.writeString(data.toPath(), gson.toJson(jars));
            } catch (IOException e) {
                System.err.println("Failed to save cash");
                e.printStackTrace(System.err);
            }
        }
        return new ClassFinder(jars);
    }

    private List<JarData> load(File dir) {
        if (filter.test(dir)) return Collections.emptyList();
        List<JarData> list = new ArrayList<>();
        if (dir.isDirectory()) {
            for (File file : dir.listFiles()) {
                list.addAll(load(file));
            }
        } else if (dir.getName().endsWith(".jar")) {
            JarData data1 = loadJar(dir);
            if (data1 != null)
                list.add(data1);
        }
        return list;
    }

    @Nullable
    private JarData loadJar(File file) {
        String sha1;
        try {
            sha1 = getFileChecksum(file);
        } catch (IOException | NoSuchAlgorithmException e) {
            System.err.println("Failed to load " + file);
            e.printStackTrace(System.err);
            return null;
        }
        JarData cashed = cashedJars.get(sha1);
        if (cashed != null) return cashed;
        hasNew = true;
        System.out.println("load " + file.getPath());
        try (JarFile jar = new JarFile(file)) {
            List<ClassData> classes = new ArrayList<>();
            jar.stream().forEach(entry -> {
                if (entry.getName().endsWith(".class")) {
                    final ClassReader classReader;
                    try {
                        try (InputStream inputStream = jar.getInputStream(entry)) {
                            classReader = new ClassReader(
                                    inputStream
                            );
                            final ClassNode classNode = new ClassNode();
                            classReader.accept(classNode, ClassReader.SKIP_CODE);
                            classes.add(new ClassData(
                                    classNode.name,
                                    classNode.superName,
                                    classNode.access,
                                    classNode.version,
                                    classNode.interfaces
                            ));
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            });

            return new JarData(
                    file.getPath(),
                    classes,
                    sha1
            );
        } catch (Throwable e) {
            System.err.println("Failed to load " + file);
            e.printStackTrace(System.err);
            return null;
        }
    }

    public static String getFileChecksum(File file) throws IOException, NoSuchAlgorithmException {
        MessageDigest digest = MessageDigest.getInstance("SHA-1");
        FileInputStream fis = new FileInputStream(file);
        byte[] byteArray = new byte[1024];
        int bytesCount = 0;
        while ((bytesCount = fis.read(byteArray)) != -1) {
            digest.update(byteArray, 0, bytesCount);
        }
        fis.close();
        byte[] bytes = digest.digest();
        StringBuilder sb = new StringBuilder();
        for (byte b : bytes) {
            sb.append(String.format("%02x", b));
        }
        return sb.toString();
    }
}
