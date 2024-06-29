package org.by1337.bpatcher.patcher.api;

import org.objectweb.asm.tree.ClassNode;

public interface Patcher {
    void apply(ClassNode classNode);
    String targetClass();
}
