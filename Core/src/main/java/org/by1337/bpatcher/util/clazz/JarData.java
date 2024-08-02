package org.by1337.bpatcher.util.clazz;

import java.util.List;

public class JarData {
    private String patch;
    private List<ClassData> classes;
    private String sha1;

    public JarData(String patch, List<ClassData> classes, String sha1) {
        this.patch = patch;
        this.classes = classes;
        this.sha1 = sha1;
    }

    public String getPatch() {
        return patch;
    }

    public List<ClassData> getClasses() {
        return classes;
    }

    public String getSha1() {
        return sha1;
    }
}
