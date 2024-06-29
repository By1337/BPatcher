package org.by1337.bpatcher.util;

import com.google.errorprone.annotations.CanIgnoreReturnValue;
import org.objectweb.asm.Handle;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.tree.*;

import java.util.ListIterator;

public class ByteCodeBuilder extends InsnList {
    private final InsnList source;

    public ByteCodeBuilder(InsnList source) {
        this.source = source;
    }

    public ByteCodeBuilder() {
        source = new InsnList();
    }

    @CanIgnoreReturnValue
    public ByteCodeBuilder push(AbstractInsnNode abstractInsnNode) {
        source.add(abstractInsnNode);
        return this;
    }
    @CanIgnoreReturnValue
    public ByteCodeBuilder int_(int i) {
        source.add(BytecodeHelper.pushInt(i));
        return this;
    }

    @CanIgnoreReturnValue
    public ByteCodeBuilder nop() {
        return visitInsn(Opcodes.NOP);
    }

    @CanIgnoreReturnValue
    public ByteCodeBuilder monitorexit() {
        return visitInsn(Opcodes.MONITOREXIT);
    }

    @CanIgnoreReturnValue
    public ByteCodeBuilder athrow() {
        return visitInsn(Opcodes.ATHROW);
    }

    @CanIgnoreReturnValue
    public ByteCodeBuilder lcmp() {
        return visitInsn(Opcodes.LCMP);
    }

    @CanIgnoreReturnValue
    public ByteCodeBuilder fcmpl() {
        return visitInsn(Opcodes.FCMPL);
    }

    @CanIgnoreReturnValue
    public ByteCodeBuilder fcmpg() {
        return visitInsn(Opcodes.FCMPG);
    }

    @CanIgnoreReturnValue
    public ByteCodeBuilder dcmpl() {
        return visitInsn(Opcodes.DCMPL);
    }

    @CanIgnoreReturnValue
    public ByteCodeBuilder dcmpg() {
        return visitInsn(Opcodes.DCMPG);
    }

    @CanIgnoreReturnValue
    public ByteCodeBuilder aconst_null() {
        return visitInsn(Opcodes.ACONST_NULL);
    }

    @CanIgnoreReturnValue
    public ByteCodeBuilder iconst_m1() {
        return visitInsn(Opcodes.ICONST_M1);
    }

    @CanIgnoreReturnValue
    public ByteCodeBuilder iconst_0() {
        return visitInsn(Opcodes.ICONST_0);
    }

    @CanIgnoreReturnValue
    public ByteCodeBuilder iconst_1() {
        return visitInsn(Opcodes.ICONST_1);
    }

    @CanIgnoreReturnValue
    public ByteCodeBuilder iconst_2() {
        return visitInsn(Opcodes.ICONST_2);
    }

    @CanIgnoreReturnValue
    public ByteCodeBuilder iconst_3() {
        return visitInsn(Opcodes.ICONST_3);
    }

    @CanIgnoreReturnValue
    public ByteCodeBuilder iconst_4() {
        return visitInsn(Opcodes.ICONST_4);
    }

    @CanIgnoreReturnValue
    public ByteCodeBuilder iconst_5() {
        return visitInsn(Opcodes.ICONST_5);
    }

    @CanIgnoreReturnValue
    public ByteCodeBuilder lconst_0() {
        return visitInsn(Opcodes.LCONST_0);
    }

    @CanIgnoreReturnValue
    public ByteCodeBuilder lconst_1() {
        return visitInsn(Opcodes.LCONST_1);
    }

    @CanIgnoreReturnValue
    public ByteCodeBuilder fconst_0() {
        return visitInsn(Opcodes.FCONST_0);
    }

    @CanIgnoreReturnValue
    public ByteCodeBuilder fconst_1() {
        return visitInsn(Opcodes.FCONST_1);
    }

    @CanIgnoreReturnValue
    public ByteCodeBuilder fconst_2() {
        return visitInsn(Opcodes.FCONST_2);
    }

    @CanIgnoreReturnValue
    public ByteCodeBuilder dconst_0() {
        return visitInsn(Opcodes.DCONST_0);
    }

    @CanIgnoreReturnValue
    public ByteCodeBuilder dconst_1() {
        return visitInsn(Opcodes.DCONST_1);
    }

    @CanIgnoreReturnValue
    public ByteCodeBuilder iaload() {
        return visitInsn(Opcodes.IALOAD);
    }

    @CanIgnoreReturnValue
    public ByteCodeBuilder laload() {
        return visitInsn(Opcodes.LALOAD);
    }

    @CanIgnoreReturnValue
    public ByteCodeBuilder faload() {
        return visitInsn(Opcodes.FALOAD);
    }

    @CanIgnoreReturnValue
    public ByteCodeBuilder daload() {
        return visitInsn(Opcodes.DALOAD);
    }

    @CanIgnoreReturnValue
    public ByteCodeBuilder aaload() {
        return visitInsn(Opcodes.AALOAD);
    }

    @CanIgnoreReturnValue
    public ByteCodeBuilder baload() {
        return visitInsn(Opcodes.BALOAD);
    }

    @CanIgnoreReturnValue
    public ByteCodeBuilder caload() {
        return visitInsn(Opcodes.CALOAD);
    }

    @CanIgnoreReturnValue
    public ByteCodeBuilder saload() {
        return visitInsn(Opcodes.SALOAD);
    }

    @CanIgnoreReturnValue
    public ByteCodeBuilder iastore() {
        return visitInsn(Opcodes.IASTORE);
    }

    @CanIgnoreReturnValue
    public ByteCodeBuilder lastore() {
        return visitInsn(Opcodes.LASTORE);
    }

    @CanIgnoreReturnValue
    public ByteCodeBuilder fastore() {
        return visitInsn(Opcodes.FASTORE);
    }

    @CanIgnoreReturnValue
    public ByteCodeBuilder dastore() {
        return visitInsn(Opcodes.DASTORE);
    }

    @CanIgnoreReturnValue
    public ByteCodeBuilder aastore() {
        return visitInsn(Opcodes.AASTORE);
    }

    @CanIgnoreReturnValue
    public ByteCodeBuilder bastore() {
        return visitInsn(Opcodes.BASTORE);
    }

    @CanIgnoreReturnValue
    public ByteCodeBuilder castore() {
        return visitInsn(Opcodes.CASTORE);
    }

    @CanIgnoreReturnValue
    public ByteCodeBuilder sastore() {
        return visitInsn(Opcodes.SASTORE);
    }

    @CanIgnoreReturnValue
    public ByteCodeBuilder i2l() {
        return visitInsn(Opcodes.I2L);
    }

    @CanIgnoreReturnValue
    public ByteCodeBuilder i2f() {
        return visitInsn(Opcodes.I2F);
    }

    @CanIgnoreReturnValue
    public ByteCodeBuilder i2d() {
        return visitInsn(Opcodes.I2D);
    }

    @CanIgnoreReturnValue
    public ByteCodeBuilder l2i() {
        return visitInsn(Opcodes.L2I);
    }

    @CanIgnoreReturnValue
    public ByteCodeBuilder l2f() {
        return visitInsn(Opcodes.L2F);
    }

    @CanIgnoreReturnValue
    public ByteCodeBuilder l2d() {
        return visitInsn(Opcodes.L2D);
    }

    @CanIgnoreReturnValue
    public ByteCodeBuilder f2i() {
        return visitInsn(Opcodes.F2I);
    }

    @CanIgnoreReturnValue
    public ByteCodeBuilder f2l() {
        return visitInsn(Opcodes.F2L);
    }

    @CanIgnoreReturnValue
    public ByteCodeBuilder f2d() {
        return visitInsn(Opcodes.F2D);
    }

    @CanIgnoreReturnValue
    public ByteCodeBuilder d2i() {
        return visitInsn(Opcodes.D2I);
    }

    @CanIgnoreReturnValue
    public ByteCodeBuilder d2l() {
        return visitInsn(Opcodes.D2L);
    }

    @CanIgnoreReturnValue
    public ByteCodeBuilder d2f() {
        return visitInsn(Opcodes.D2F);
    }

    @CanIgnoreReturnValue
    public ByteCodeBuilder i2b() {
        return visitInsn(Opcodes.I2B);
    }

    @CanIgnoreReturnValue
    public ByteCodeBuilder i2c() {
        return visitInsn(Opcodes.I2C);
    }

    @CanIgnoreReturnValue
    public ByteCodeBuilder i2s() {
        return visitInsn(Opcodes.I2S);
    }

    @CanIgnoreReturnValue
    public ByteCodeBuilder ireturn() {
        return visitInsn(Opcodes.IRETURN);
    }

    @CanIgnoreReturnValue
    public ByteCodeBuilder lreturn() {
        return visitInsn(Opcodes.LRETURN);
    }

    @CanIgnoreReturnValue
    public ByteCodeBuilder freturn() {
        return visitInsn(Opcodes.FRETURN);
    }

    @CanIgnoreReturnValue
    public ByteCodeBuilder dreturn() {
        return visitInsn(Opcodes.DRETURN);
    }

    @CanIgnoreReturnValue
    public ByteCodeBuilder areturn() {
        return visitInsn(Opcodes.ARETURN);
    }

    @CanIgnoreReturnValue
    public ByteCodeBuilder return_() {
        return visitInsn(Opcodes.RETURN);
    }

    @CanIgnoreReturnValue
    public ByteCodeBuilder arraylength() {
        return visitInsn(Opcodes.ARRAYLENGTH);
    }

    @CanIgnoreReturnValue
    public ByteCodeBuilder monitorenter() {
        return visitInsn(Opcodes.MONITORENTER);
    }

    @CanIgnoreReturnValue
    public ByteCodeBuilder pop() {
        return visitInsn(Opcodes.POP);
    }

    @CanIgnoreReturnValue
    public ByteCodeBuilder pop2() {
        return visitInsn(Opcodes.POP2);
    }

    @CanIgnoreReturnValue
    public ByteCodeBuilder dup() {
        return visitInsn(Opcodes.DUP);
    }

    @CanIgnoreReturnValue
    public ByteCodeBuilder dup_x1() {
        return visitInsn(Opcodes.DUP_X1);
    }

    @CanIgnoreReturnValue
    public ByteCodeBuilder dup_x2() {
        return visitInsn(Opcodes.DUP_X2);
    }

    @CanIgnoreReturnValue
    public ByteCodeBuilder dup2() {
        return visitInsn(Opcodes.DUP2);
    }

    @CanIgnoreReturnValue
    public ByteCodeBuilder dup2_x1() {
        return visitInsn(Opcodes.DUP2_X1);
    }

    @CanIgnoreReturnValue
    public ByteCodeBuilder dup2_x2() {
        return visitInsn(Opcodes.DUP2_X2);
    }

    @CanIgnoreReturnValue
    public ByteCodeBuilder swap() {
        return visitInsn(Opcodes.SWAP);
    }

    @CanIgnoreReturnValue
    public ByteCodeBuilder iadd() {
        return visitInsn(Opcodes.IADD);
    }

    @CanIgnoreReturnValue
    public ByteCodeBuilder ladd() {
        return visitInsn(Opcodes.LADD);
    }

    @CanIgnoreReturnValue
    public ByteCodeBuilder fadd() {
        return visitInsn(Opcodes.FADD);
    }

    @CanIgnoreReturnValue
    public ByteCodeBuilder dadd() {
        return visitInsn(Opcodes.DADD);
    }

    @CanIgnoreReturnValue
    public ByteCodeBuilder isub() {
        return visitInsn(Opcodes.ISUB);
    }

    @CanIgnoreReturnValue
    public ByteCodeBuilder lsub() {
        return visitInsn(Opcodes.LSUB);
    }

    @CanIgnoreReturnValue
    public ByteCodeBuilder fsub() {
        return visitInsn(Opcodes.FSUB);
    }

    @CanIgnoreReturnValue
    public ByteCodeBuilder dsub() {
        return visitInsn(Opcodes.DSUB);
    }

    @CanIgnoreReturnValue
    public ByteCodeBuilder imul() {
        return visitInsn(Opcodes.IMUL);
    }

    @CanIgnoreReturnValue
    public ByteCodeBuilder lmul() {
        return visitInsn(Opcodes.LMUL);
    }

    @CanIgnoreReturnValue
    public ByteCodeBuilder fmul() {
        return visitInsn(Opcodes.FMUL);
    }

    @CanIgnoreReturnValue
    public ByteCodeBuilder dmul() {
        return visitInsn(Opcodes.DMUL);
    }

    @CanIgnoreReturnValue
    public ByteCodeBuilder idiv() {
        return visitInsn(Opcodes.IDIV);
    }

    @CanIgnoreReturnValue
    public ByteCodeBuilder ldiv() {
        return visitInsn(Opcodes.LDIV);
    }

    @CanIgnoreReturnValue
    public ByteCodeBuilder fdiv() {
        return visitInsn(Opcodes.FDIV);
    }

    @CanIgnoreReturnValue
    public ByteCodeBuilder ddiv() {
        return visitInsn(Opcodes.DDIV);
    }

    @CanIgnoreReturnValue
    public ByteCodeBuilder irem() {
        return visitInsn(Opcodes.IREM);
    }

    @CanIgnoreReturnValue
    public ByteCodeBuilder lrem() {
        return visitInsn(Opcodes.LREM);
    }

    @CanIgnoreReturnValue
    public ByteCodeBuilder frem() {
        return visitInsn(Opcodes.FREM);
    }

    @CanIgnoreReturnValue
    public ByteCodeBuilder drem() {
        return visitInsn(Opcodes.DREM);
    }

    @CanIgnoreReturnValue
    public ByteCodeBuilder ineg() {
        return visitInsn(Opcodes.INEG);
    }

    @CanIgnoreReturnValue
    public ByteCodeBuilder lneg() {
        return visitInsn(Opcodes.LNEG);
    }

    @CanIgnoreReturnValue
    public ByteCodeBuilder fneg() {
        return visitInsn(Opcodes.FNEG);
    }

    @CanIgnoreReturnValue
    public ByteCodeBuilder dneg() {
        return visitInsn(Opcodes.DNEG);
    }

    @CanIgnoreReturnValue
    public ByteCodeBuilder ishl() {
        return visitInsn(Opcodes.ISHL);
    }

    @CanIgnoreReturnValue
    public ByteCodeBuilder lshl() {
        return visitInsn(Opcodes.LSHL);
    }

    @CanIgnoreReturnValue
    public ByteCodeBuilder ishr() {
        return visitInsn(Opcodes.ISHR);
    }

    @CanIgnoreReturnValue
    public ByteCodeBuilder lshr() {
        return visitInsn(Opcodes.LSHR);
    }

    @CanIgnoreReturnValue
    public ByteCodeBuilder iushr() {
        return visitInsn(Opcodes.IUSHR);
    }

    @CanIgnoreReturnValue
    public ByteCodeBuilder lushr() {
        return visitInsn(Opcodes.LUSHR);
    }

    @CanIgnoreReturnValue
    public ByteCodeBuilder iand() {
        return visitInsn(Opcodes.IAND);
    }

    @CanIgnoreReturnValue
    public ByteCodeBuilder land() {
        return visitInsn(Opcodes.LAND);
    }

    @CanIgnoreReturnValue
    public ByteCodeBuilder ior() {
        return visitInsn(Opcodes.IOR);
    }

    @CanIgnoreReturnValue
    public ByteCodeBuilder lor() {
        return visitInsn(Opcodes.LOR);
    }

    @CanIgnoreReturnValue
    public ByteCodeBuilder ixor() {
        return visitInsn(Opcodes.IXOR);
    }

    @CanIgnoreReturnValue
    public ByteCodeBuilder lxor() {
        return visitInsn(Opcodes.LXOR);
    }

    @CanIgnoreReturnValue
    public ByteCodeBuilder visitInsn(int opcode) {
        source.add(new InsnNode(opcode));
        return this;
    }

    @CanIgnoreReturnValue
    public ByteCodeBuilder bipush(int i) {
        return visitIntInsn(Opcodes.BIPUSH, i);
    }

    @CanIgnoreReturnValue
    public ByteCodeBuilder sipush(int i) {
        return visitIntInsn(Opcodes.SIPUSH, i);
    }

    @CanIgnoreReturnValue
    public ByteCodeBuilder newarray(int i) {
        return visitIntInsn(Opcodes.NEWARRAY, i);
    }

    @CanIgnoreReturnValue
    public ByteCodeBuilder istore(int varindex) {
        return visitVarInsn(Opcodes.ISTORE, varindex);
    }

    @CanIgnoreReturnValue
    public ByteCodeBuilder lstore(int varindex) {
        return visitVarInsn(Opcodes.LSTORE, varindex);
    }

    @CanIgnoreReturnValue
    public ByteCodeBuilder fstore(int varindex) {
        return visitVarInsn(Opcodes.FSTORE, varindex);
    }

    @CanIgnoreReturnValue
    public ByteCodeBuilder dstore(int varindex) {
        return visitVarInsn(Opcodes.DSTORE, varindex);
    }

    @CanIgnoreReturnValue
    public ByteCodeBuilder astore(int varindex) {
        return visitVarInsn(Opcodes.ASTORE, varindex);
    }

    @CanIgnoreReturnValue
    public ByteCodeBuilder iload(int varindex) {
        return visitVarInsn(Opcodes.ILOAD, varindex);
    }

    @CanIgnoreReturnValue
    public ByteCodeBuilder lload(int varindex) {
        return visitVarInsn(Opcodes.LLOAD, varindex);
    }

    @CanIgnoreReturnValue
    public ByteCodeBuilder fload(int varindex) {
        return visitVarInsn(Opcodes.FLOAD, varindex);
    }

    @CanIgnoreReturnValue
    public ByteCodeBuilder dload(int varindex) {
        return visitVarInsn(Opcodes.DLOAD, varindex);
    }

    @CanIgnoreReturnValue
    public ByteCodeBuilder aload(int varindex) {
        return visitVarInsn(Opcodes.ALOAD, varindex);
    }

    @CanIgnoreReturnValue
    public ByteCodeBuilder ret(int varindex) {
        return visitVarInsn(Opcodes.RET, varindex);
    }

    @CanIgnoreReturnValue
    public ByteCodeBuilder ldc(Object value) {
        source.add(new LdcInsnNode(value));
        return this;
    }

    @CanIgnoreReturnValue
    public ByteCodeBuilder iinc(final int varindex, final int incr) {
        source.add(new IincInsnNode(varindex, incr));
        return this;
    }

    @CanIgnoreReturnValue
    public ByteCodeBuilder ifeq(LabelNode labelnode) {
        return visitJumpInsn(Opcodes.IFEQ, labelnode);
    }

    @CanIgnoreReturnValue
    public ByteCodeBuilder ifne(LabelNode labelnode) {
        return visitJumpInsn(Opcodes.IFNE, labelnode);
    }

    @CanIgnoreReturnValue
    public ByteCodeBuilder iflt(LabelNode labelnode) {
        return visitJumpInsn(Opcodes.IFLT, labelnode);
    }

    @CanIgnoreReturnValue
    public ByteCodeBuilder ifge(LabelNode labelnode) {
        return visitJumpInsn(Opcodes.IFGE, labelnode);
    }

    @CanIgnoreReturnValue
    public ByteCodeBuilder ifgt(LabelNode labelnode) {
        return visitJumpInsn(Opcodes.IFGT, labelnode);
    }

    @CanIgnoreReturnValue
    public ByteCodeBuilder ifle(LabelNode labelnode) {
        return visitJumpInsn(Opcodes.IFLE, labelnode);
    }

    @CanIgnoreReturnValue
    public ByteCodeBuilder if_icmpeq(LabelNode labelnode) {
        return visitJumpInsn(Opcodes.IF_ICMPEQ, labelnode);
    }

    @CanIgnoreReturnValue
    public ByteCodeBuilder if_icmpne(LabelNode labelnode) {
        return visitJumpInsn(Opcodes.IF_ICMPNE, labelnode);
    }

    @CanIgnoreReturnValue
    public ByteCodeBuilder if_icmplt(LabelNode labelnode) {
        return visitJumpInsn(Opcodes.IF_ICMPLT, labelnode);
    }

    @CanIgnoreReturnValue
    public ByteCodeBuilder if_icmpge(LabelNode labelnode) {
        return visitJumpInsn(Opcodes.IF_ICMPGE, labelnode);
    }

    @CanIgnoreReturnValue
    public ByteCodeBuilder if_icmpgt(LabelNode labelnode) {
        return visitJumpInsn(Opcodes.IF_ICMPGT, labelnode);
    }

    @CanIgnoreReturnValue
    public ByteCodeBuilder if_icmple(LabelNode labelnode) {
        return visitJumpInsn(Opcodes.IF_ICMPLE, labelnode);
    }

    @CanIgnoreReturnValue
    public ByteCodeBuilder if_acmpeq(LabelNode labelnode) {
        return visitJumpInsn(Opcodes.IF_ACMPEQ, labelnode);
    }

    @CanIgnoreReturnValue
    public ByteCodeBuilder if_acmpne(LabelNode labelnode) {
        return visitJumpInsn(Opcodes.IF_ACMPNE, labelnode);
    }

    @CanIgnoreReturnValue
    public ByteCodeBuilder goto_(LabelNode labelnode) {
        return visitJumpInsn(Opcodes.GOTO, labelnode);
    }

    @CanIgnoreReturnValue
    public ByteCodeBuilder jsr(LabelNode labelnode) {
        return visitJumpInsn(Opcodes.JSR, labelnode);
    }

    @CanIgnoreReturnValue
    public ByteCodeBuilder ifnull(LabelNode labelnode) {
        return visitJumpInsn(Opcodes.IFNULL, labelnode);
    }

    @CanIgnoreReturnValue
    public ByteCodeBuilder ifnonnull(LabelNode labelnode) {
        return visitJumpInsn(Opcodes.IFNONNULL, labelnode);
    }

    @CanIgnoreReturnValue
    public ByteCodeBuilder new_(String type) {
        return visitTypeInsn(Opcodes.NEW, type);
    }

    @CanIgnoreReturnValue
    public ByteCodeBuilder checkcast(String type) {
        return visitTypeInsn(Opcodes.CHECKCAST, type);
    }

    @CanIgnoreReturnValue
    public ByteCodeBuilder instanceof_(String type) {
        return visitTypeInsn(Opcodes.INSTANCEOF, type);
    }

    @CanIgnoreReturnValue
    public ByteCodeBuilder anewarray(String type) {
        return visitTypeInsn(Opcodes.ANEWARRAY, type);
    }

    @CanIgnoreReturnValue
    public ByteCodeBuilder visitIntInsn(int opcode, int operand) {
        source.add(new IntInsnNode(opcode, operand));
        return this;
    }

    @CanIgnoreReturnValue
    public ByteCodeBuilder visitVarInsn(int opcode, int varIndex) {
        source.add(new VarInsnNode(opcode, varIndex));
        return this;
    }

    @CanIgnoreReturnValue
    public ByteCodeBuilder visitJumpInsn(int opcode, LabelNode labelNode) {
        source.add(new JumpInsnNode(opcode, labelNode));
        return this;
    }

    @CanIgnoreReturnValue
    public ByteCodeBuilder visitTypeInsn(int opcode, String type) {
        source.add(new TypeInsnNode(opcode, type));
        return this;
    }

    @CanIgnoreReturnValue
    public ByteCodeBuilder getstatic(String owner, String name, String descriptor) {
        return visitFieldInsn(Opcodes.GETSTATIC, owner, name, descriptor);
    }

    @CanIgnoreReturnValue
    public ByteCodeBuilder getstatic(ClassNode owner, String name, String descriptor) {
        return visitFieldInsn(Opcodes.GETSTATIC, owner.name, name, descriptor);
    }

    @CanIgnoreReturnValue
    public ByteCodeBuilder getstatic(ClassNode owner, FieldNode fieldNode) {
        return visitFieldInsn(Opcodes.GETSTATIC, owner.name, fieldNode.name, fieldNode.desc);
    }

    @CanIgnoreReturnValue
    public ByteCodeBuilder putstatic(String owner, String name, String descriptor) {
        return visitFieldInsn(Opcodes.PUTSTATIC, owner, name, descriptor);
    }

    @CanIgnoreReturnValue
    public ByteCodeBuilder putstatic(ClassNode owner, String name, String descriptor) {
        return visitFieldInsn(Opcodes.PUTSTATIC, owner.name, name, descriptor);
    }

    @CanIgnoreReturnValue
    public ByteCodeBuilder putstatic(ClassNode owner, FieldNode fieldNode) {
        return visitFieldInsn(Opcodes.PUTSTATIC, owner.name, fieldNode.name, fieldNode.desc);
    }

    @CanIgnoreReturnValue
    public ByteCodeBuilder getfield(String owner, String name, String descriptor) {
        return visitFieldInsn(Opcodes.GETFIELD, owner, name, descriptor);
    }

    @CanIgnoreReturnValue
    public ByteCodeBuilder getfield(ClassNode owner, String name, String descriptor) {
        return visitFieldInsn(Opcodes.GETFIELD, owner.name, name, descriptor);
    }

    @CanIgnoreReturnValue
    public ByteCodeBuilder getfield(ClassNode owner, FieldNode fieldNode) {
        return visitFieldInsn(Opcodes.GETFIELD, owner.name, fieldNode.name, fieldNode.desc);
    }

    @CanIgnoreReturnValue
    public ByteCodeBuilder putfield(String owner, String name, String descriptor) {
        return visitFieldInsn(Opcodes.PUTFIELD, owner, name, descriptor);
    }

    @CanIgnoreReturnValue
    public ByteCodeBuilder putfield(ClassNode owner, String name, String descriptor) {
        return visitFieldInsn(Opcodes.PUTFIELD, owner.name, name, descriptor);
    }

    @CanIgnoreReturnValue
    public ByteCodeBuilder putfield(ClassNode owner, FieldNode fieldNode) {
        return visitFieldInsn(Opcodes.PUTFIELD, owner.name, fieldNode.name, fieldNode.desc);
    }

    @CanIgnoreReturnValue
    public ByteCodeBuilder multianewarray(String descriptor, int numDimensions) {
        return visitMultiANewArrayInsn(descriptor, numDimensions);
    }

    @CanIgnoreReturnValue
    public ByteCodeBuilder visitFieldInsn(int opcode, String owner, String name, String descriptor) {
        source.add(new FieldInsnNode(opcode, owner, name, descriptor));
        return this;
    }

    @CanIgnoreReturnValue
    public ByteCodeBuilder visitMultiANewArrayInsn(String descriptor, int numDimensions) {
        source.add(new MultiANewArrayInsnNode(descriptor, numDimensions));
        return this;
    }

    @CanIgnoreReturnValue
    public ByteCodeBuilder invokevirtual(String owner, String name, String descriptor) {
        return visitMethodInsn(Opcodes.INVOKEVIRTUAL, owner, name, descriptor);
    }

    @CanIgnoreReturnValue
    public ByteCodeBuilder invokevirtual(ClassNode owner, String name, String descriptor) {
        return visitMethodInsn(Opcodes.INVOKEVIRTUAL, owner.name, name, descriptor);
    }

    @CanIgnoreReturnValue
    public ByteCodeBuilder invokevirtual(ClassNode owner, MethodNode methodnode) {
        return visitMethodInsn(Opcodes.INVOKEVIRTUAL, owner.name, methodnode.name, methodnode.desc);
    }

    @CanIgnoreReturnValue
    public ByteCodeBuilder invokespecial(String owner, String name, String descriptor) {
        return visitMethodInsn(Opcodes.INVOKESPECIAL, owner, name, descriptor);
    }

    @CanIgnoreReturnValue
    public ByteCodeBuilder invokespecial(ClassNode owner, String name, String descriptor) {
        return visitMethodInsn(Opcodes.INVOKESPECIAL, owner.name, name, descriptor);
    }

    @CanIgnoreReturnValue
    public ByteCodeBuilder invokespecial(ClassNode owner, MethodNode methodNode) {
        return visitMethodInsn(Opcodes.INVOKESPECIAL, owner.name, methodNode.name, methodNode.desc);
    }

    @CanIgnoreReturnValue
    public ByteCodeBuilder invokestatic(String owner, String name, String descriptor) {
        return visitMethodInsn(Opcodes.INVOKESTATIC, owner, name, descriptor);
    }

    @CanIgnoreReturnValue
    public ByteCodeBuilder invokestatic(ClassNode owner, String name, String descriptor) {
        return visitMethodInsn(Opcodes.INVOKESTATIC, owner.name, name, descriptor);
    }

    @CanIgnoreReturnValue
    public ByteCodeBuilder invokestatic(ClassNode owner, MethodNode methodNode) {
        return visitMethodInsn(Opcodes.INVOKESTATIC, owner.name, methodNode.name, methodNode.desc);
    }

    @CanIgnoreReturnValue
    public ByteCodeBuilder invokeinterface(String owner, String name, String descriptor) {
        return visitMethodInsn(Opcodes.INVOKEINTERFACE, owner, name, descriptor);
    }

    @CanIgnoreReturnValue
    public ByteCodeBuilder invokeinterface(ClassNode owner, String name, String descriptor) {
        return visitMethodInsn(Opcodes.INVOKEINTERFACE, owner.name, name, descriptor);
    }

    @CanIgnoreReturnValue
    public ByteCodeBuilder invokeinterface(ClassNode owner, MethodNode methodnode) {
        return visitMethodInsn(Opcodes.INVOKEINTERFACE, owner.name, methodnode.name, methodnode.desc);
    }

    @CanIgnoreReturnValue
    public ByteCodeBuilder visitMethodInsn(int opcode, String owner, String name, String descriptor) {
        source.add(new MethodInsnNode(opcode, owner, name, descriptor));
        return this;
    }

    @CanIgnoreReturnValue
    public ByteCodeBuilder invokedynamic(
            final String name,
            final String descriptor,
            final Handle bootstrapMethodHandle,
            final Object... bootstrapMethodArguments
    ) {
        return visitInvokeDynamicInsn(name, descriptor, bootstrapMethodHandle, bootstrapMethodArguments);
    }

    @CanIgnoreReturnValue
    public ByteCodeBuilder visitInvokeDynamicInsn(
            final String name,
            final String descriptor,
            final Handle bootstrapMethodHandle,
            final Object... bootstrapMethodArguments
    ) {
        source.add(new InvokeDynamicInsnNode(name, descriptor, bootstrapMethodHandle, bootstrapMethodArguments));
        return this;
    }

    @CanIgnoreReturnValue
    public ByteCodeBuilder tableswitch(int min, int max, LabelNode dflt, LabelNode... labels) {
        return visiTableSwitchInsn(min, max, dflt, labels);
    }

    @CanIgnoreReturnValue
    public ByteCodeBuilder visiTableSwitchInsn(int min, int max, LabelNode dflt, LabelNode... labels) {
        source.add(new TableSwitchInsnNode(min, max, dflt, labels));
        return this;
    }

    @CanIgnoreReturnValue
    public ByteCodeBuilder lookupswitch(LabelNode dflt, int[] keys, LabelNode[] labels) {
        return visitLookupSwitch(dflt, keys, labels);
    }

    @CanIgnoreReturnValue
    public ByteCodeBuilder visitLookupSwitch(LabelNode dflt, int[] keys, LabelNode[] labels) {
        source.add(new LookupSwitchInsnNode(dflt, keys, labels));
        return this;
    }

    public InsnList getSource() {
        return source;
    }

    /**
     * Returns the number of instructions in this list.
     *
     * @return the number of instructions in this list.
     */
    @Override
    public int size() {
        return source.size();
    }

    /**
     * Returns the first instruction in this list.
     *
     * @return the first instruction in this list, or {@literal null} if the list is empty.
     */
    @Override
    public AbstractInsnNode getFirst() {
        return source.getFirst();
    }

    /**
     * Returns the last instruction in this list.
     *
     * @return the last instruction in this list, or {@literal null} if the list is empty.
     */
    @Override
    public AbstractInsnNode getLast() {
        return source.getLast();
    }

    /**
     * Returns the instruction whose index is given. This method builds a cache of the instructions in
     * this list to avoid scanning the whole list each time it is called. Once the cache is built,
     * this method runs in constant time. This cache is invalidated by all the methods that modify the
     * list.
     *
     * @param index the index of the instruction that must be returned.
     * @return the instruction whose index is given.
     * @throws IndexOutOfBoundsException if (index &lt; 0 || index &gt;= size()).
     */
    @Override
    public AbstractInsnNode get(int index) {
        return source.get(index);
    }

    /**
     * Returns {@literal true} if the given instruction belongs to this list. This method always scans
     * the instructions of this list until it finds the given instruction or reaches the end of the
     * list.
     *
     * @param insnNode an instruction.
     * @return {@literal true} if the given instruction belongs to this list.
     */
    @Override
    public boolean contains(AbstractInsnNode insnNode) {
        return source.contains(insnNode);
    }

    /**
     * Returns the index of the given instruction in this list. This method builds a cache of the
     * instruction indexes to avoid scanning the whole list each time it is called. Once the cache is
     * built, this method run in constant time. The cache is invalidated by all the methods that
     * modify the list.
     *
     * @param insnNode an instruction <i>of this list</i>.
     * @return the index of the given instruction in this list. <i>The result of this method is
     * undefined if the given instruction does not belong to this list</i>. Use {@link #contains }
     * to test if an instruction belongs to an instruction list or not.
     */
    @Override
    public int indexOf(AbstractInsnNode insnNode) {
        return source.indexOf(insnNode);
    }

    /**
     * Makes the given visitor visit all the instructions in this list.
     *
     * @param methodVisitor the method visitor that must visit the instructions.
     */
    @Override
    public void accept(MethodVisitor methodVisitor) {
        source.accept(methodVisitor);
    }

    /**
     * Returns an iterator over the instructions in this list.
     *
     * @return an iterator over the instructions in this list.
     */
    @Override
    public ListIterator<AbstractInsnNode> iterator() {
        return source.iterator();
    }

    /**
     * Returns an iterator over the instructions in this list.
     *
     * @param index index of instruction for the iterator to start at.
     * @return an iterator over the instructions in this list.
     */
    @Override
    public ListIterator<AbstractInsnNode> iterator(int index) {
        return source.iterator(index);
    }

    /**
     * Returns an array containing all the instructions in this list.
     *
     * @return an array containing all the instructions in this list.
     */
    @Override
    public AbstractInsnNode[] toArray() {
        return source.toArray();
    }

    /**
     * Replaces an instruction of this list with another instruction.
     *
     * @param oldInsnNode an instruction <i>of this list</i>.
     * @param newInsnNode another instruction, <i>which must not belong to any {@link InsnList}</i>.
     */
    @Override
    public void set(AbstractInsnNode oldInsnNode, AbstractInsnNode newInsnNode) {
        source.set(oldInsnNode, newInsnNode);
    }

    /**
     * Adds the given instruction to the end of this list.
     *
     * @param insnNode an instruction, <i>which must not belong to any {@link InsnList}</i>.
     */
    @Override
    public void add(AbstractInsnNode insnNode) {
        source.add(insnNode);
    }

    /**
     * Adds the given instructions to the end of this list.
     *
     * @param insnList an instruction list, which is cleared during the process. This list must be
     *                 different from 'this'.
     */
    @Override
    public void add(InsnList insnList) {
        source.add(insnList);
    }

    /**
     * Inserts the given instruction at the beginning of this list.
     *
     * @param insnNode an instruction, <i>which must not belong to any {@link InsnList}</i>.
     */
    @Override
    public void insert(AbstractInsnNode insnNode) {
        source.insert(insnNode);
    }

    /**
     * Inserts the given instructions at the beginning of this list.
     *
     * @param insnList an instruction list, which is cleared during the process. This list must be
     *                 different from 'this'.
     */
    @Override
    public void insert(InsnList insnList) {
        source.insert(insnList);
    }

    /**
     * Inserts the given instruction after the specified instruction.
     *
     * @param previousInsn an instruction <i>of this list</i> after which insnNode must be inserted.
     * @param insnNode     the instruction to be inserted, <i>which must not belong to any {@link
     *                     InsnList}</i>.
     */
    @Override
    public void insert(AbstractInsnNode previousInsn, AbstractInsnNode insnNode) {
        source.insert(previousInsn, insnNode);
    }

    /**
     * Inserts the given instructions after the specified instruction.
     *
     * @param previousInsn an instruction <i>of this list</i> after which the instructions must be
     *                     inserted.
     * @param insnList     the instruction list to be inserted, which is cleared during the process. This
     *                     list must be different from 'this'.
     */
    @Override
    public void insert(AbstractInsnNode previousInsn, InsnList insnList) {
        source.insert(previousInsn, insnList);
    }

    /**
     * Inserts the given instruction before the specified instruction.
     *
     * @param nextInsn an instruction <i>of this list</i> before which insnNode must be inserted.
     * @param insnNode the instruction to be inserted, <i>which must not belong to any {@link
     *                 InsnList}</i>.
     */
    @Override
    public void insertBefore(AbstractInsnNode nextInsn, AbstractInsnNode insnNode) {
        source.insertBefore(nextInsn, insnNode);
    }

    /**
     * Inserts the given instructions before the specified instruction.
     *
     * @param nextInsn an instruction <i>of this list</i> before which the instructions must be
     *                 inserted.
     * @param insnList the instruction list to be inserted, which is cleared during the process. This
     *                 list must be different from 'this'.
     */
    @Override
    public void insertBefore(AbstractInsnNode nextInsn, InsnList insnList) {
        source.insertBefore(nextInsn, insnList);
    }

    /**
     * Removes the given instruction from this list.
     *
     * @param insnNode the instruction <i>of this list</i> that must be removed.
     */
    @Override
    public void remove(AbstractInsnNode insnNode) {
        source.remove(insnNode);
    }

    /**
     * Removes all the instructions of this list.
     */
    @Override
    public void clear() {
        source.clear();
    }

    /**
     * Resets all the labels in the instruction list. This method should be called before reusing an
     * instruction list between several <code>ClassWriter</code>s.
     */
    @Override
    public void resetLabels() {
        source.resetLabels();
    }
}
