package com.yjy.testplugin;


import com.android.build.gradle.AppExtension;

import org.gradle.api.Plugin;
import org.gradle.api.Project;

import java.util.Collections;

public class TestPlugin implements Plugin<Project> {
    @Override
    public void apply(Project project) {
       // AppExtension appExtension = project.getExtensions().getByType(AppExtension);
        AppExtension appExtension = project.getExtensions().getByType(AppExtension.class);
        appExtension.registerTransform(new TestTransform(project), Collections.EMPTY_LIST);
    }
}
