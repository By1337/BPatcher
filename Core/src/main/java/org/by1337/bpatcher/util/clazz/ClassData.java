package org.by1337.bpatcher.util.clazz;

import java.lang.reflect.Modifier;
import java.util.List;
import java.util.Objects;

public class ClassData {
    private String name;
    private String superName;
    private int access;
    private int version;
    private List<String> interfaces;
    private ClassFinder finder;

    public ClassData(String name, String superName, int access, int version, List<String> interfaces) {
        this.name = name;
        this.superName = superName;
        this.access = access;
        this.version = version;
        this.interfaces = interfaces;
    }

    void setFinder(ClassFinder finder) {
        this.finder = finder;
    }

    public boolean isAssignableFrom(ClassData other) {
        if (name.equals(other.name)) return true;
        ClassData superClass = finder.findClass(other.superName);
        if (superClass != null && isAssignableFrom(superClass)) {
            return true;
        }
        for (String iface : other.interfaces) {
            ClassData interfaceInfo = finder.findClass(iface);
            if (interfaceInfo != null && isAssignableFrom(interfaceInfo)) {
                return true;
            }
        }
        return false;
    }
    public String getName() {
        return name;
    }

    public String getSuperName() {
        return superName;
    }

    public int getAccess() {
        return access;
    }

    public int getVersion() {
        return version;
    }

    public List<String> getInterfaces() {
        return interfaces;
    }

    public boolean isPublic() {
        return Modifier.isPublic(access);
    }

    public boolean isPrivate() {
        return Modifier.isPrivate(access);
    }

    public boolean isProtected() {
        return Modifier.isProtected(access);
    }

    public boolean isStatic() {
        return Modifier.isStatic(access);
    }

    public boolean isFinal() {
        return Modifier.isFinal(access);
    }

    public boolean isInterface() {
        return Modifier.isInterface(access);
    }

    public boolean isAbstract() {
        return Modifier.isAbstract(access);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ClassData classData = (ClassData) o;
        return access == classData.access && version == classData.version && Objects.equals(name, classData.name) && Objects.equals(superName, classData.superName) && Objects.equals(interfaces, classData.interfaces);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, superName, access, version, interfaces);
    }

    @Override
    public String toString() {
        return "ClassData{" +
               "name='" + name + '\'' +
               ", superName='" + superName + '\'' +
               ", access=" + access +
               ", version=" + version +
               ", interfaces=" + interfaces +
               ", finder=" + finder +
               '}';
    }
}
