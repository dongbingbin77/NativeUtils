package com.yjy.testplugin;

import com.android.build.api.transform.QualifiedContent;
import com.android.build.api.transform.Transform;
import com.android.build.api.transform.TransformException;
import com.android.build.api.transform.TransformInvocation;
import com.android.build.gradle.internal.pipeline.TransformManager;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.Set;

public class TestTransform extends Transform {

    /**
     * Executes the Transform.
     *
     * <p>The inputs are packaged as an instance of {@link TransformInvocation}
     * <ul>
     *     <li>The <var>inputs</var> collection of {@link TransformInput}. These are the inputs
     *     that are consumed by this Transform. A transformed version of these inputs must
     *     be written into the output. What is received is controlled through
     *     {@link #getInputTypes()}, and {@link #getScopes()}.</li>
     *     <li>The <var>referencedInputs</var> collection of {@link TransformInput}. This is
     *     for reference only and should be not be transformed. What is received is controlled
     *     through {@link #getReferencedScopes()}.</li>
     * </ul>
     * <p>
     * A transform that does not want to consume anything but instead just wants to see the content
     * of some inputs should return an empty set in {@link #getScopes()}, and what it wants to
     * see in {@link #getReferencedScopes()}.
     *
     * <p>Even though a transform's {@link Transform#isIncremental()} returns true, this method may
     * be receive <code>false</code> in <var>isIncremental</var>. This can be due to
     * <ul>
     *     <li>a change in secondary files ({@link #getSecondaryFiles()},
     *     {@link #getSecondaryFileOutputs()}, {@link #getSecondaryDirectoryOutputs()})</li>
     *     <li>a change to a non file input ({@link #getParameterInputs()})</li>
     *     <li>an unexpected change to the output files/directories. This should not happen unless
     *     tasks are improperly configured and clobber each other's output.</li>
     *     <li>a file deletion that the transform mechanism could not match to a previous input.
     *     This should not happen in most case, except in some cases where dependencies have
     *     changed.</li>
     * </ul>
     * In such an event, when <var>isIncremental</var> is false, the inputs will not have any
     * incremental change information:
     * <ul>
     *     <li>{@link JarInput#getStatus()} will return {@link Status#NOTCHANGED} even though
     *     the file may be added/changed.</li>
     *     <li>{@link DirectoryInput#getChangedFiles()} will return an empty map even though
     *     some files may be added/changed.</li>
     * </ul>
     *
     * @param transformInvocation the invocation object containing the transform inputs.
     * @throws IOException          if an IO error occurs.
     * @throws InterruptedException
     * @throws TransformException   Generic exception encapsulating the cause.
     */
    @Override
    public void transform(TransformInvocation transformInvocation) throws TransformException, InterruptedException, IOException {
        super.transform(transformInvocation);
        Gson gson = new Gson();
        System.out.println("dongbingbin TestTransform:"+gson.toJson(transformInvocation.getInputs()));
    }

    /**
     * Returns the unique name of the transform.
     *
     * <p>This is associated with the type of work that the transform does. It does not have to be
     * unique per variant.
     */
    @Override
    public String getName() {
        return "TestTransform";
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
}
