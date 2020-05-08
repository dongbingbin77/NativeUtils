package com.yjy.testplugin.asm;



import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.FieldVisitor;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

public class LogMethodVisitor extends MethodVisitor {
    String name;
    String className;
    public LogMethodVisitor(MethodVisitor mv, String name,String className) {
        super(Opcodes.ASM6, mv);
        this.name = name;
        this.className = className;
    }



    /**
     *    L2
     *     LINENUMBER 13 L2
     *     LDC "log_inject"
     *     LDC "onCreate"
     *     INVOKESTATIC android/util/Log.e (Ljava/lang/String;Ljava/lang/String;)I
     *     POP
     */
    @Override
    public void visitCode() {
        super.visitCode();
        // 在方法之前插入 Log.e("", "")
        // 这两个是参数
        visitLdcInsn("log_inject");
        visitLdcInsn(this.name);
        visitMethodInsn(Opcodes.INVOKESTATIC, "android/util/Log", "e", "(Ljava/lang/String;Ljava/lang/String;)I", false);

        System.currentTimeMillis();

        visitMethodInsn(Opcodes.INVOKESTATIC, "java/lang/System", "currentTimeMillis", "()J", false);
        visitVarInsn(Opcodes.LSTORE, 10);

        visitLdcInsn("dongbingbin2");
        visitVarInsn(Opcodes.ASTORE,8);
        visitFieldInsn(Opcodes.GETSTATIC, "java/lang/System", "out", "Ljava/io/PrintStream;");
        visitVarInsn(Opcodes.ALOAD, 8);
        visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/io/PrintStream", "println", "(Ljava/lang/String;)V", false);
        // 这里的用法有点奇怪，还需要研究一下
        // visitXXX 实际上会触发 MethodWriter 的方法，这些方法会将我们想要写入的字节码存放起来
        // 最后统一的写入到输出的 class 文件中
    }

    @Override
    public void visitInsn(int opcode) {
        if(opcode == Opcodes.ARETURN || opcode == Opcodes.RETURN ) {
            visitLdcInsn("dongbingbin2");
            visitVarInsn(Opcodes.ASTORE,8);
            visitFieldInsn(Opcodes.GETSTATIC, "java/lang/System", "out", "Ljava/io/PrintStream;");
            visitVarInsn(Opcodes.ALOAD, 8);
            visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/io/PrintStream", "println", "(Ljava/lang/String;)V", false);



            visitMethodInsn(Opcodes.INVOKESTATIC, "java/lang/System", "currentTimeMillis", "()J", false);
            visitVarInsn(Opcodes.LSTORE, 11);

        }
        super.visitInsn(opcode);
    }

    @Override
    public void visitEnd() {
//        visitLdcInsn("log_inject");
//        visitLdcInsn(this.name);
//        visitMethodInsn(Opcodes.INVOKESTATIC, "android/util/Log", "e", "(Ljava/lang/String;Ljava/lang/String;)I", false);
        super.visitEnd();

//
//        visitLdcInsn("dongbingbin2");
//        visitVarInsn(Opcodes.ASTORE,8);
//        visitFieldInsn(Opcodes.GETSTATIC, "java/lang/System", "out", "Ljava/io/PrintStream;");
//        visitVarInsn(Opcodes.ALOAD, 8);
//        visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/io/PrintStream", "println", "(Ljava/lang/String;)V", false);
    }

    /**
     *     MAXSTACK = 2
     *     MAXLOCALS = 2
     * @param maxStack
     * @param maxLocals
     */
    @Override
    public void visitMaxs(int maxStack, int maxLocals) {
        // 修改后方法需要的栈帧 可以从 byteCode 里面看到
        super.visitMaxs(4, 4);
    }
}
