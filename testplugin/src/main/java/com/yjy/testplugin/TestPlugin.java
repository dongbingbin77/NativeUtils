package com.yjy.testplugin;


import com.android.build.gradle.AppExtension;
import com.google.gson.Gson;

import org.gradle.api.Action;
import org.gradle.api.Plugin;
import org.gradle.api.Project;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Map;

public class TestPlugin implements Plugin<Project> {


    @Override
    public void apply(Project project) {
        project.getExtensions().create("testplugin",TestPluginConfig.class);
       // AppExtension appExtension = project.getExtensions().getByType(AppExtension);
        AppExtension appExtension = project.getExtensions().getByType(AppExtension.class);
        appExtension.registerTransform(new TestTransform(project), Collections.EMPTY_LIST);

        project.afterEvaluate(new Action<Project>() {
            @Override
            public void execute(Project project) {
                TestPluginConfig testPluginConfig = (TestPluginConfig)project.getExtensions().findByName("testplugin");
                try {
                    //System.out.println("dongbingbin testplugin:"+(new Gson()).toJson(testPluginConfig));
                }catch (Exception ex1){
                    ex1.printStackTrace();
                }

            }
        });
    }
}
