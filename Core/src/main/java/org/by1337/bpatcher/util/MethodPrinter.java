package org.by1337.bpatcher.util;

import com.google.common.base.Joiner;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.Type;
import org.objectweb.asm.tree.*;
import org.objectweb.asm.tree.analysis.*;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MethodPrinter {
    public static String write(MethodNode methodNode, ClassNode classNode) {
        return write(methodNode, new WriteOptions(), classNode);
    }

    public static String write(MethodNode methodNode, WriteOptions options, ClassNode classNode) {
        StringBuilder sb = new StringBuilder();

        Map<Integer, String> localVariablesMap = new HashMap<>();
        if (methodNode.localVariables != null) {
            for (LocalVariableNode localVariable : methodNode.localVariables) {
                sb.append("// ")
                        .append(localVariable.name).append(" ")
                        .append(localVariable.desc).append(" ")
                        .append(localVariable.index).append(" ")
                        .append("\n");
                localVariablesMap.put(localVariable.index, localVariable.name);
            }
        }

        for (Flags.AccessFlags value : Flags.AccessFlags.values()) {
            if ((methodNode.access & value.flag) != 0) {
                sb.append(value.name).append(" ");
            }
        }
        sb.append(methodNode.name).append(" ").append(methodNode.desc).append(" {\n");
        Map<LabelNode, String> labels = new HashMap<>();
        for (AbstractInsnNode instruction : methodNode.instructions) {
            if (instruction instanceof LabelNode labelNode) {
                String name = "label_" + labels.size();
                labels.put(labelNode, name);
            }
        }
        Analyzer<BasicValue> analyzer = new Analyzer<>(new SimpleVerifier());

        Frame<BasicValue>[] frames = null;
        if (options.writeStackTrace) {
            try {
                analyzer.analyze(classNode.name, methodNode);
                frames = analyzer.getFrames();
            } catch (AnalyzerException e) {
                throw new RuntimeException(e);
            }
        }
        boolean lastStackIsEmpty = true;

        for (int i = 0; i < methodNode.instructions.size(); i++) {
            if (options.writeStackTrace) {
                if (frames != null) {
                    Frame<BasicValue> frame = frames[i];
                    if (frame != null && frame.getStackSize() != 0) {
                        StringBuilder stringBuilder = new StringBuilder();
                        stringBuilder.append("[ ");
                        for (int x = 0; x < frame.getStackSize(); x++) {
                            stringBuilder.append(quoteAndEscape(frame.getStack(x).getType().toString(), true)).append(", ");
                        }
                        stringBuilder.setLength(stringBuilder.length() - 2);
                        stringBuilder.append(" ]");
                        sb.append("\t// stack: ").append(stringBuilder).append("\n");
                        lastStackIsEmpty = false;
                    } else if (frame != null && !lastStackIsEmpty) {
                        lastStackIsEmpty = true;
                        sb.append("\t// stack: [ ]\n");
                    }
                }
            }
            AbstractInsnNode instruction = methodNode.instructions.get(i);
            if (instruction instanceof InsnNode insnNode) {
                sb.append("\t").append(switch (insnNode.getOpcode()) {
                    case Opcodes.NOP -> "nop";
                    case Opcodes.ACONST_NULL -> "aconst_null";
                    case Opcodes.ICONST_M1 -> "iconst_m1";
                    case Opcodes.ICONST_0 -> "iconst_0";
                    case Opcodes.ICONST_1 -> "iconst_1";
                    case Opcodes.ICONST_2 -> "iconst_2";
                    case Opcodes.ICONST_3 -> "iconst_3";
                    case Opcodes.ICONST_4 -> "iconst_4";
                    case Opcodes.ICONST_5 -> "iconst_5";
                    case Opcodes.LCONST_0 -> "lconst_0";
                    case Opcodes.LCONST_1 -> "lconst_1";
                    case Opcodes.FCONST_0 -> "fconst_0";
                    case Opcodes.FCONST_1 -> "fconst_1";
                    case Opcodes.FCONST_2 -> "fconst_2";
                    case Opcodes.DCONST_0 -> "dconst_0";
                    case Opcodes.DCONST_1 -> "dconst_1";
                    case Opcodes.IALOAD -> "iaload";
                    case Opcodes.LALOAD -> "laload";
                    case Opcodes.FALOAD -> "faload";
                    case Opcodes.DALOAD -> "daload";
                    case Opcodes.AALOAD -> "aaload";
                    case Opcodes.BALOAD -> "baload";
                    case Opcodes.CALOAD -> "caload";
                    case Opcodes.SALOAD -> "saload";
                    case Opcodes.IASTORE -> "iastore";
                    case Opcodes.LASTORE -> "lastore";
                    case Opcodes.FASTORE -> "fastore";
                    case Opcodes.DASTORE -> "dastore";
                    case Opcodes.AASTORE -> "aastore";
                    case Opcodes.BASTORE -> "bastore";
                    case Opcodes.CASTORE -> "castore";
                    case Opcodes.SASTORE -> "sastore";
                    case Opcodes.POP -> "pop";
                    case Opcodes.POP2 -> "pop2";
                    case Opcodes.DUP -> "dup";
                    case Opcodes.DUP_X1 -> "dup_x1";
                    case Opcodes.DUP_X2 -> "dup_x2";
                    case Opcodes.DUP2 -> "dup2";
                    case Opcodes.DUP2_X1 -> "dup2_x1";
                    case Opcodes.DUP2_X2 -> "dup2_x2";
                    case Opcodes.SWAP -> "swap";
                    case Opcodes.IADD -> "iadd";
                    case Opcodes.LADD -> "ladd";
                    case Opcodes.FADD -> "fadd";
                    case Opcodes.DADD -> "dadd";
                    case Opcodes.ISUB -> "isub";
                    case Opcodes.LSUB -> "lsub";
                    case Opcodes.FSUB -> "fsub";
                    case Opcodes.DSUB -> "dsub";
                    case Opcodes.IMUL -> "imul";
                    case Opcodes.LMUL -> "lmul";
                    case Opcodes.FMUL -> "fmul";
                    case Opcodes.DMUL -> "dmul";
                    case Opcodes.IDIV -> "idiv";
                    case Opcodes.LDIV -> "ldiv";
                    case Opcodes.FDIV -> "fdiv";
                    case Opcodes.DDIV -> "ddiv";
                    case Opcodes.IREM -> "irem";
                    case Opcodes.LREM -> "lrem";
                    case Opcodes.FREM -> "frem";
                    case Opcodes.DREM -> "drem";
                    case Opcodes.INEG -> "ineg";
                    case Opcodes.LNEG -> "lneg";
                    case Opcodes.FNEG -> "fneg";
                    case Opcodes.DNEG -> "dneg";
                    case Opcodes.ISHL -> "ishl";
                    case Opcodes.LSHL -> "lshl";
                    case Opcodes.ISHR -> "ishr";
                    case Opcodes.LSHR -> "lshr";
                    case Opcodes.IUSHR -> "iushr";
                    case Opcodes.LUSHR -> "lushr";
                    case Opcodes.IAND -> "iand";
                    case Opcodes.LAND -> "land";
                    case Opcodes.IOR -> "ior";
                    case Opcodes.LOR -> "lor";
                    case Opcodes.IXOR -> "ixor";
                    case Opcodes.LXOR -> "lxor";
                    case Opcodes.I2L -> "i2l";
                    case Opcodes.I2F -> "i2f";
                    case Opcodes.I2D -> "i2d";
                    case Opcodes.L2I -> "l2i";
                    case Opcodes.L2F -> "l2f";
                    case Opcodes.L2D -> "l2d";
                    case Opcodes.F2I -> "f2i";
                    case Opcodes.F2L -> "f2l";
                    case Opcodes.F2D -> "f2d";
                    case Opcodes.D2I -> "d2i";
                    case Opcodes.D2L -> "d2l";
                    case Opcodes.D2F -> "d2f";
                    case Opcodes.I2B -> "i2b";
                    case Opcodes.I2C -> "i2c";
                    case Opcodes.I2S -> "i2s";
                    case Opcodes.LCMP -> "lcmp";
                    case Opcodes.FCMPL -> "fcmpl";
                    case Opcodes.FCMPG -> "fcmpg";
                    case Opcodes.DCMPL -> "dcmpl";
                    case Opcodes.DCMPG -> "dcmpg";
                    case Opcodes.IRETURN -> "ireturn";
                    case Opcodes.LRETURN -> "lreturn";
                    case Opcodes.FRETURN -> "freturn";
                    case Opcodes.DRETURN -> "dreturn";
                    case Opcodes.ARETURN -> "areturn";
                    case Opcodes.RETURN -> "return";
                    case Opcodes.ARRAYLENGTH -> "arraylength";
                    case Opcodes.ATHROW -> "athrow";
                    case Opcodes.MONITORENTER -> "monitorenter";
                    case Opcodes.MONITOREXIT -> "monitorexit";
                    default -> "UNKNOWN";
                }).append("\n");
            } else if (instruction instanceof IntInsnNode intInsnNode) {
                sb.append("\t").append(switch (intInsnNode.getOpcode()) {
                    case Opcodes.BIPUSH -> "bipush " + intInsnNode.operand;
                    case Opcodes.SIPUSH -> "sipush " + intInsnNode.operand;
                    case Opcodes.NEWARRAY -> "newarray " + intInsnNode.operand;
                    default -> "UNKNOWN";
                }).append("\n");

            } else if (instruction instanceof VarInsnNode varInsnNode) {
                String varName = localVariablesMap.get(varInsnNode.var);
                String info;
                if (varName != null) {
                    info = " // " + varName;
                } else {
                    info = "";
                }
                sb.append("\t").append(switch (varInsnNode.getOpcode()) {
                    case Opcodes.ILOAD -> "iload " + varInsnNode.var + info;
                    case Opcodes.LLOAD -> "lload " + varInsnNode.var + info;
                    case Opcodes.FLOAD -> "fload " + varInsnNode.var + info;
                    case Opcodes.DLOAD -> "dload " + varInsnNode.var + info;
                    case Opcodes.ALOAD -> "aload " + varInsnNode.var + info;
                    case Opcodes.ISTORE -> "istore " + varInsnNode.var + info;
                    case Opcodes.LSTORE -> "lstore " + varInsnNode.var + info;
                    case Opcodes.FSTORE -> "fstore " + varInsnNode.var + info;
                    case Opcodes.DSTORE -> "dstore " + varInsnNode.var + info;
                    case Opcodes.ASTORE -> "astore " + varInsnNode.var + info;
                    case Opcodes.RET -> "ret " + varInsnNode.var + info;
                    default -> "UNKNOWN";
                }).append("\n");

            } else if (instruction instanceof LdcInsnNode ldcInsnNode) {
                if (ldcInsnNode.cst instanceof String s) {
                    sb.append("\t").append("ldc ").append(quoteAndEscape(s, true)).append("\n");
                } else if (ldcInsnNode.cst instanceof Number n) {
                    sb.append("\t").append("ldc ").append(n).append("\n");
                } else if (ldcInsnNode.cst instanceof Type type) {
                    sb.append("\t").append("ldc ").append(type).append("\n");
                } else {
                    sb.append("\t").append("ldc ").append(ldcInsnNode.cst).append("\n");
                }
            } else if (instruction instanceof JumpInsnNode jumpInsnNode) {
                String labelName = labels.getOrDefault(jumpInsnNode.label, "unknown label!");
                sb.append("\t").append(switch (jumpInsnNode.getOpcode()) {
                    case Opcodes.IFEQ -> "ifeq " + labelName;
                    case Opcodes.IFNE -> "ifne " + labelName;
                    case Opcodes.IFLT -> "iflt " + labelName;
                    case Opcodes.IFGE -> "ifge " + labelName;
                    case Opcodes.IFGT -> "ifgt " + labelName;
                    case Opcodes.IFLE -> "ifle " + labelName;
                    case Opcodes.IF_ICMPEQ -> "if_icmpeq " + labelName;
                    case Opcodes.IF_ICMPNE -> "if_icmpne " + labelName;
                    case Opcodes.IF_ICMPLT -> "if_icmplt " + labelName;
                    case Opcodes.IF_ICMPGE -> "if_icmpge " + labelName;
                    case Opcodes.IF_ICMPGT -> "if_icmpgt " + labelName;
                    case Opcodes.IF_ICMPLE -> "if_icmple " + labelName;
                    case Opcodes.IF_ACMPEQ -> "if_acmpeq " + labelName;
                    case Opcodes.IF_ACMPNE -> "if_acmpne " + labelName;
                    case Opcodes.GOTO -> "goto " + labelName;
                    case Opcodes.JSR -> "jsr " + labelName;
                    case Opcodes.IFNULL -> "ifnull " + labelName;
                    case Opcodes.IFNONNULL -> "ifnonnull " + labelName;
                    default -> "UNKNOWN";
                }).append("\n");
            } else if (instruction instanceof FieldInsnNode fieldInsnNode) {
                sb.append("\t").append(switch (fieldInsnNode.getOpcode()) {
                    case Opcodes.GETSTATIC ->
                            "getstatic " + fieldInsnNode.owner + " " + fieldInsnNode.name + " " + fieldInsnNode.desc;
                    case Opcodes.PUTSTATIC ->
                            "putstatic " + fieldInsnNode.owner + " " + fieldInsnNode.name + " " + fieldInsnNode.desc;
                    case Opcodes.GETFIELD ->
                            "getfield " + fieldInsnNode.owner + " " + fieldInsnNode.name + " " + fieldInsnNode.desc;
                    case Opcodes.PUTFIELD ->
                            "putfield " + fieldInsnNode.owner + " " + fieldInsnNode.name + " " + fieldInsnNode.desc;
                    default -> "UNKNOWN";
                }).append("\n");
            } else if (instruction instanceof MethodInsnNode methodInsnNode) {
                String data = methodInsnNode.owner + " " + methodInsnNode.name + " " + methodInsnNode.desc;
                sb.append("\t").append(switch (methodInsnNode.getOpcode()) {
                    case Opcodes.INVOKEVIRTUAL -> "invokevirtual " + data;
                    case Opcodes.INVOKESPECIAL -> "invokespecial " + data;
                    case Opcodes.INVOKESTATIC -> "invokestatic " + data;
                    case Opcodes.INVOKEINTERFACE -> "invokeinterface " + data;
                    default -> "UNKNOWN";
                }).append("\n");

            } else if (instruction instanceof TypeInsnNode typeInsnNode) {
                sb.append("\t").append(switch (typeInsnNode.getOpcode()) {
                    case Opcodes.NEW -> "new " + typeInsnNode.desc;
                    case Opcodes.ANEWARRAY -> "anewarray " + typeInsnNode.desc;
                    case Opcodes.CHECKCAST -> "checkcast " + typeInsnNode.desc;
                    case Opcodes.INSTANCEOF -> "instanceof " + typeInsnNode.desc;
                    default -> "UNKNOWN";
                }).append("\n");
            } else if (instruction instanceof IincInsnNode insnNode) {
                sb.append("\t").append("iinc ").append(insnNode.var).append(" ").append(insnNode.incr).append("\n");
            } else if (instruction instanceof MultiANewArrayInsnNode multiANewArrayInsnNode) {
                sb.append("\t")
                        .append("multianewarray ").append(multiANewArrayInsnNode.dims)
                        .append(" ")
                        .append(multiANewArrayInsnNode.desc)
                        .append("\n");
            } else if (instruction instanceof TableSwitchInsnNode tableSwitch) {
                sb.append("\t")
                        .append("tableswitch {\n")
                        .append("\t  ")
                        .append("min: ")
                        .append(tableSwitch.min)
                        .append("\n\t  ")
                        .append("max: ")
                        .append(tableSwitch.max)
                        .append("\n\t  labels: [\n");
                for (LabelNode label : tableSwitch.labels) {
                    String labelName = labels.getOrDefault(label, "unknown label!");
                    sb.append("\t\t").append(labelName).append("\n");
                }
                sb.setLength(sb.length() - 1);
                sb.append("\n\t  ]\n");
                sb.append("\t  dflt: ").append(labels.getOrDefault(tableSwitch.dflt, "unknown label!"));
                sb.append("\n\t}\n");
            } else if (instruction instanceof LookupSwitchInsnNode lookupSwitchInsnNode) {
                sb.append("\t")
                        .append("lookupswitch {\n")
                        .append("\t  labels: [\n");

                for (int i1 = 0; i1 < lookupSwitchInsnNode.labels.size(); i1++) {
                    LabelNode label = lookupSwitchInsnNode.labels.get(i1);
                    int key = lookupSwitchInsnNode.keys.get(i1);
                    String labelName = labels.getOrDefault(label, "unknown label!");
                    sb.append("\t\t").append("case ").append(key).append(" -> ").append(labelName).append("\n");
                }
                sb.setLength(sb.length() - 1);
                sb.append("\n\t  ]\n");
                sb.append("\t  dflt: ").append(labels.getOrDefault(lookupSwitchInsnNode.dflt, "unknown label!"));
                sb.append("\n\t}\n");
            } else if (instruction instanceof LabelNode labelNode) {
                sb.append(labels.getOrDefault(labelNode, "unknown label!")).append(":").append("\n");
            } else if (instruction instanceof FrameNode frameNode) {
                String data = frameNode.local.size() + " " +
                              frameData(frameNode.local) + " " +
                              frameNode.stack.size() + " " +
                              frameData(frameNode.stack);
                sb.append("\t// ").append(switch (frameNode.getOpcode()) {
                    case Opcodes.F_NEW -> "F_NEW " + data;
                    case Opcodes.F_FULL -> "F_FULL " + data;
                    case Opcodes.F_APPEND -> "F_APPEND " + data;
                    case Opcodes.F_CHOP -> "F_CHOP " + data;
                    case Opcodes.F_SAME -> "F_SAME " + data;
                    case Opcodes.F_SAME1 -> "F_SAME1 " + data;
                    default -> "UNKNOWN";
                }).append("\n");
            } else if (instruction instanceof LineNumberNode lineNumberNode) {
                sb.append("\t// line ").append(lineNumberNode.line).append("\n");
            }
        }
        sb.append("}");
        return sb.toString();
    }

    private static String frameData(List<Object> objects) {
        StringBuilder sb = new StringBuilder("[");
        if (!objects.isEmpty()){
            sb.append(" ");
        }
        for (Object object : objects) {
            if (object instanceof Type type) {
                sb.append(quoteAndEscape(type.getInternalName(), true)).append(", ");
            } else if (object instanceof Integer integer) {
                if (integer.equals(Opcodes.TOP)) {
                    sb.append("Opcodes.TOP").append(", ");
                } else if (integer.equals(Opcodes.INTEGER)) {
                    sb.append("Opcodes.INTEGER").append(", ");
                } else if (integer.equals(Opcodes.FLOAT)) {
                    sb.append("Opcodes.FLOAT").append(", ");
                } else if (integer.equals(Opcodes.DOUBLE)) {
                    sb.append("Opcodes.DOUBLE").append(", ");
                } else if (integer.equals(Opcodes.LONG)) {
                    sb.append("Opcodes.LONG").append(", ");
                } else if (integer.equals(Opcodes.NULL)) {
                    sb.append("Opcodes.NULL").append(", ");
                } else if (integer.equals(Opcodes.UNINITIALIZED_THIS)) {
                    sb.append("Opcodes.UNINITIALIZED_THIS").append(", ");
                }
            } else {
                sb.append(quoteAndEscape(String.valueOf(object), true)).append(", ");
            }
        }
        if (!objects.isEmpty()) {
            sb.setLength(sb.length() - 2);
            sb.append(" ");
        }
        return sb.append("]").toString();
    }

    public static class WriteOptions {
        public boolean writeStackTrace;
    }

    public static class Flags {
        public enum AccessFlags {
            PUBLIC("public", Opcodes.ACC_PUBLIC),
            PRIVATE("private", Opcodes.ACC_PRIVATE),
            PROTECTED("protected", Opcodes.ACC_PROTECTED),
            STATIC("static", Opcodes.ACC_STATIC),
            FINAL("final", Opcodes.ACC_FINAL),
            SYNCHRONIZED("synchronized", Opcodes.ACC_SYNCHRONIZED),
            BRIDGE("bridge", Opcodes.ACC_BRIDGE),
            NATIVE("native", Opcodes.ACC_NATIVE),
            ABSTRACT("abstract", Opcodes.ACC_ABSTRACT),
            STRICT("strict", Opcodes.ACC_STRICT),
            SYNTHETIC("synthetic", Opcodes.ACC_SYNTHETIC),
            MANDATED("mandated", Opcodes.ACC_MANDATED),
            VARARGS("varargs", Opcodes.ACC_VARARGS),
            ;
            private final String name;
            private final int flag;

            AccessFlags(String name, int flag) {
                this.name = name;
                this.flag = flag;
            }

            public String getName() {
                return name;
            }

            public int getFlag() {
                return flag;
            }
        }
    }

    public static String quoteAndEscape(String raw, boolean json) {
        StringBuilder result = new StringBuilder(" ");
        int quoteChar = 0;
        for (int i = 0; i < raw.length(); ++i) {
            char currentChar = raw.charAt(i);
            switch (currentChar) {
                case '\\':
                    result.append("\\\\");
                    break;
                case '\n':
                    result.append("\\n");
                    break;
                case '\t':
                    result.append("\\t");
                    break;
                case '\b':
                    result.append("\\b");
                    break;
                case '\r':
                    result.append("\\r");
                    break;
                case '\f':
                    result.append("\\f");
                    break;
                case '\"':
                case '\'':
                    if (quoteChar == 0) {
                        quoteChar = currentChar == '\"' ? '\'' : '\"';
                    }
                    if (json) {
                        quoteChar = '\"';
                    }
                    if (quoteChar == currentChar) {
                        result.append('\\');
                    }
                    result.append(currentChar);
                    break;
                default:
                    result.append(currentChar);
            }
        }
        if (quoteChar == 0) {
            quoteChar = '\"';
        }
        result.setCharAt(0, (char) quoteChar);
        result.append((char) quoteChar);
        return result.toString();
    }
}
