package com.carverlee.pickle.compiler;

import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.FieldSpec;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.TypeSpec;

import java.io.IOException;

import javax.annotation.processing.Filer;
import javax.lang.model.element.Modifier;

/**
 * @author carverLee
 * 2019/12/16 16:27
 */
public class CodeGenerator {
    private static final String PACKAGE_NAME = "com.carverlee.pickle";

    public static void generate(Filer filer) {
        TypeSpec injectionTypeSpec = TypeSpec.classBuilder("PickleInjection")
                .addModifiers(Modifier.PUBLIC)
                .addField(classList())
                .addField(classMap())
                .addMethod(methodSpecBuilder.build())
                .build();
        JavaFile javaFile = JavaFile.builder(PACKAGE_NAME, injectionTypeSpec).build();
        try {
            javaFile.writeTo(filer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static FieldSpec classList() {
        ClassName className = ClassName.get("java.util", "ArrayList");
        return FieldSpec
                .builder(className, "classList", Modifier.PUBLIC)
                .build();
    }

    private static FieldSpec classMap() {
        ClassName className = ClassName.get("java.util", "HashMap");
        return FieldSpec
                .builder(className, "classMap", Modifier.PUBLIC)
                .build();
    }

    private static final MethodSpec.Builder methodSpecBuilder =
            MethodSpec
                    .constructorBuilder()
                    .addModifiers(Modifier.PUBLIC)
                    .addStatement("classList = new ArrayList()")
                    .addStatement("classMap = new HashMap()");

    public static void addClass(String className, String key) {
        if (key == null || key.isEmpty()) {
            methodSpecBuilder.addStatement("classList.add($S)", className);
        } else {
            methodSpecBuilder.addStatement("classMap.put($S,$S)", key, className);
        }
    }

}
