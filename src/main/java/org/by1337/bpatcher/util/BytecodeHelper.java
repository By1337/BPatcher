package org.by1337.bpatcher.util;

import org.jetbrains.annotations.Nullable;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.tree.*;

public class BytecodeHelper {
    @Nullable
    public static MethodNode getMethod(final ClassNode classNode, final String name) {
        for (final MethodNode method : classNode.methods)
            if (method.name.equals(name))
                return method;
        return null;
    }
    public static AbstractInsnNode pushInt(int i) {
        if (i >= -1 && i <= 5) {
            return new InsnNode(Opcodes.ICONST_0 + i);
        } else if (i >= Byte.MIN_VALUE && i <= Byte.MAX_VALUE) {
            return new IntInsnNode(Opcodes.BIPUSH, i);
        } else if (i >= Short.MIN_VALUE && i <= Short.MAX_VALUE) {
            return new IntInsnNode(Opcodes.SIPUSH, i);
        } else {
            return new LdcInsnNode(i);
        }
    }
}
