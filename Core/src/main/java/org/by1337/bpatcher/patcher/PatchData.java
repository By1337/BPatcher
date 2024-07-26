package org.by1337.bpatcher.patcher;

import org.by1337.bpatcher.patcher.api.Patcher;
import org.objectweb.asm.tree.ClassNode;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.zip.ZipEntry;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.zip.ZipEntry;
import org.objectweb.asm.tree.ClassNode;

/**
 * The class allows adding patchers and inject classes at program startup.
 */
public class PatchData {
    private final List<Class<?>> customInject = new ArrayList<>();
    private final List<ZipEntry> customInjectEntry = new ArrayList<>();
    private final List<ClassNode> customInjectClassNode = new ArrayList<>();
    private final List<Patcher> customPatches = new ArrayList<>();
    private final File file;
    private final PatcherClassLoader classLoader;

    /**
     * Constructor that initializes PatchData with the specified file and class loader.
     *
     * @param file the file associated with the patches
     * @param classLoader the class loader for the patches
     */
    public PatchData(File file, PatcherClassLoader classLoader) {
        this.file = file;
        this.classLoader = classLoader;
    }

    /**
     * Adds a class for injection.
     *
     * @param clazz the class to add
     * @return the current PatchData object
     * @throws IllegalArgumentException if the class is loaded by a different class loader
     */
    public PatchData addToInject(Class<?> clazz) {
        if (clazz.getClassLoader() != classLoader) {
            throw new IllegalArgumentException("Unable to add a class from another module!");
        }
        customInject.add(clazz);
        return this;
    }

    /**
     * Adds a patcher.
     *
     * @param patcher the patcher to add
     * @return the current PatchData object
     */
    public PatchData addPatcher(Patcher patcher) {
        customPatches.add(patcher);
        return this;
    }

    /**
     * Adds a ZipEntry for injection.
     *
     * @param zipEntry the ZipEntry to add
     * @return the current PatchData object
     */
    public PatchData addInjectEntry(ZipEntry zipEntry) {
        customInjectEntry.add(zipEntry);
        return this;
    }

    /**
     * Adds a ClassNode for injection.
     *
     * @param classNode the ClassNode to add
     * @return the current PatchData object
     */
    public PatchData addInjectClassNode(ClassNode classNode) {
        customInjectClassNode.add(classNode);
        return this;
    }

    /**
     * Returns the file associated with the patches.
     *
     * @return the file associated with the patches
     */
    public File getFile() {
        return file;
    }

    /**
     * Returns an unmodifiable list of classes for injection.
     *
     * @return the list of classes for injection
     */
    public List<Class<?>> getCustomInject() {
        return Collections.unmodifiableList(customInject);
    }

    /**
     * Returns an unmodifiable list of patchers.
     *
     * @return the list of patchers
     */
    public List<Patcher> getCustomPatches() {
        return Collections.unmodifiableList(customPatches);
    }

    /**
     * Returns an unmodifiable list of ZipEntries for injection.
     *
     * @return the list of ZipEntries for injection
     */
    public List<ZipEntry> getCustomInjectEntry() {
        return Collections.unmodifiableList(customInjectEntry);
    }

    /**
     * Returns an unmodifiable list of ClassNodes for injection.
     *
     * @return the list of ClassNodes for injection
     */
    public List<ClassNode> getCustomInjectClassNode() {
        return Collections.unmodifiableList(customInjectClassNode);
    }

    /**
     * Returns the class loader for the patches.
     *
     * @return the class loader for the patches
     */
    public PatcherClassLoader getClassLoader() {
        return classLoader;
    }
}
