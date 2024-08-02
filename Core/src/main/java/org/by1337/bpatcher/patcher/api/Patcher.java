package org.by1337.bpatcher.patcher.api;

import org.objectweb.asm.tree.ClassNode;

/**
 * Interface for class patchers.
 * <p>
 * Annotate an implementation of this interface with {@link org.by1337.bpatcher.patcher.api.Patch}
 * to include it in the bpatcher-lookup.json file.
 * </p>
 */
public interface Patcher {
    /**
     * Applies the patch to the specified class.
     * <p>
     * This method is called with the class specified in {@link Patcher#targetClass()}.
     * </p>
     *
     * @param classNode the class specified in {@link Patcher#targetClass()}.
     */
    void apply(ClassNode classNode);

    /**
     * Returns the name of the class that needs to be modified.
     * <p>
     * The class name should be in the format: org/by1337/example/ExampleClass.
     * </p>
     *
     * @return the name of the class that needs to be modified.
     */
    String targetClass();

    /**
     * Indicates if the frames need to be recomputed after modifications.
     * <p>
     * If modifications to the bytecode require frame recalculation, implementations should return {@code true}.
     * Otherwise, they should return {@code false}. Frame recalculation can be necessary when the bytecode
     * structure changes significantly, affecting the method frames.
     * </p>
     *
     * @return {@code true} if frame recomputation is needed, {@code false} otherwise.
     */
    default boolean needRecomputeFrames() {
        return false;
    }
}
