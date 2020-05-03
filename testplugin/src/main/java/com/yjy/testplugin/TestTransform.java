package com.yjy.testplugin;

import com.android.build.api.transform.DirectoryInput;
import com.android.build.api.transform.Format;
import com.android.build.api.transform.JarInput;
import com.android.build.api.transform.QualifiedContent;
import com.android.build.api.transform.Transform;
import com.android.build.api.transform.TransformException;
import com.android.build.api.transform.TransformInput;
import com.android.build.api.transform.TransformInvocation;
import com.android.build.api.transform.TransformOutputProvider;
import com.android.build.gradle.internal.pipeline.TransformManager;
import com.google.gson.Gson;
import com.yjy.testplugin.asm.LogClassVisitor;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.io.FileUtils;
import org.gradle.api.Project;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.ClassWriter;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Set;

public class TestTransform extends Transform {

    Project mProject;

    public TestTransform(Project mProject) {
        this.mProject = mProject;
    }

    private void findClass(File file){
        if(file.isDirectory()){
            for(File file1 : file.listFiles()){
                findClass(file1);
            }
        }else{
            if(checkFileName(file.getName())){
                injectClassFile(file);
            }
        }
    }

    @Override
    public void transform(TransformInvocation transformInvocation) throws TransformException, InterruptedException, IOException {
        super.transform(transformInvocation);
        Gson gson = new Gson();
        System.out.println("dongbingbin TestTransform:"+gson.toJson(transformInvocation.getInputs()));

        for(TransformInput input : transformInvocation.getInputs()){
            for(DirectoryInput directoryInput: input.getDirectoryInputs()){
                for(File file:directoryInput.getFile().listFiles()){
                    findClass(file);
                }
                copyDirectory(directoryInput,transformInvocation.getOutputProvider());
            }

            for(JarInput jarInput:input.getJarInputs()){
                copyJar(jarInput,transformInvocation.getOutputProvider());
            }
        }

    }

    /**
     * Returns the unique name of the transform.
     *
     * <p>This is associated with the type of work that the transform does. It does not have to be
     * unique per variant.
     */
    @Override
    public String getName() {
        return "TestTransform1";
    }

    /**
     * Returns the type(s) of data that is consumed by the Transform. This may be more than
     * one type.
     *
     * <strong>This must be of type {@link QualifiedContent.DefaultContentType}</strong>
     */
    @Override
    public Set<QualifiedContent.ContentType> getInputTypes() {
        return TransformManager.CONTENT_CLASS;
    }

    /**
     * Returns the scope(s) of the Transform. This indicates which scopes the transform consumes.
     */
    @Override
    public Set<? super QualifiedContent.Scope> getScopes() {
        return TransformManager.SCOPE_FULL_PROJECT;
    }

    /**
     * Returns whether the Transform can perform incremental work.
     *
     * <p>If it does, then the TransformInput may contain a list of changed/removed/added files, unless
     * something else triggers a non incremental run.
     */
    @Override
    public boolean isIncremental() {
        return false;
    }


    static boolean checkFileName(String name) {
        boolean b = name.endsWith(".class") && !name.startsWith("R$") &&
                "R.class" != name && "BuildConfig.class" != name;
        return b;
    }

    static void injectClassFile(File file) {
        try {
            ClassReader classReader = new ClassReader(new FileInputStream(file));
            ClassWriter classWriter = new ClassWriter(classReader, ClassWriter.COMPUTE_MAXS);
            ClassVisitor cv = new LogClassVisitor(classWriter);
            classReader.accept(cv, ClassReader.EXPAND_FRAMES);
            byte[] code = classWriter.toByteArray();
            FileOutputStream fos = new FileOutputStream(
                    file.getParentFile().getAbsolutePath() + File.separator + file.getName());
            fos.write(code);
            fos.close();
        }catch (Exception ex1){
            ex1.printStackTrace();
        }

    }

    static void copyDirectory(DirectoryInput directoryInput, TransformOutputProvider outputProvider) {
        // 获取output目录
        File dest = outputProvider.getContentLocation(directoryInput.getName(),
                directoryInput.getContentTypes(), directoryInput.getScopes(), Format.DIRECTORY);

        // 将input的目录复制到output指定目录
        try {
            FileUtils.copyDirectory(directoryInput.getFile(), dest);
        }catch (Exception ex1){
            ex1.printStackTrace();
        }

    }

    static void copyJar(JarInput jarInput, TransformOutputProvider outputProvider) {
        // 重命名输出文件（同目录copyFile会冲突）
        // 这里也是我的一个疑惑的地方
        // 几乎所有网上的代码都是这样的
        // 难道说是 一个 transform 从一个目录读取 jar 文件，处理完成之后然后写回这个目录？？？
        String jarName = jarInput.getName();
        System.out.println("jar = " + jarInput.getFile().getAbsolutePath());
        String md5Name = DigestUtils.md5Hex(jarInput.getFile().getAbsolutePath());
        // 避免出现 xxx.jar.jar 这样的名字
        if (jarName.endsWith(".jar")) {
            jarName = jarName.substring(0, jarName.length() - 4);
        }
        File dest = outputProvider.getContentLocation(jarName + md5Name, jarInput.getContentTypes(), jarInput.getScopes(), Format.JAR);
        try {
            FileUtils.copyFile(jarInput.getFile(), dest);
        }catch (Exception ex1){
            ex1.printStackTrace();
        }

    }
}
