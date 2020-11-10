package com.carverlee.pickle.compiler;

import com.carverlee.pickle.annotation.Bean;
import com.google.auto.service.AutoService;

import java.util.LinkedHashSet;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Filer;
import javax.annotation.processing.Messager;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import javax.lang.model.util.Elements;
import javax.tools.Diagnostic;

/**
 * @author carverLee
 * 2019/12/16 16:16
 */
@AutoService(Processor.class)
public class PickleProcessor extends AbstractProcessor {

    private Messager messager;
    private Elements elementUtil;
    private Filer filer;

    @Override
    public synchronized void init(ProcessingEnvironment processingEnv) {
        super.init(processingEnv);
        messager = processingEnv.getMessager();
        elementUtil = processingEnv.getElementUtils();
        filer = processingEnv.getFiler();
        messager.printMessage(Diagnostic.Kind.NOTE, "ljh init");
    }


    @Override
    public Set<String> getSupportedAnnotationTypes() {
        Set<String> supportTypes = new LinkedHashSet<>();
        supportTypes.add(Bean.class.getCanonicalName());
        return supportTypes;
    }

    @Override
    public SourceVersion getSupportedSourceVersion() {
        return SourceVersion.latestSupported();
    }

    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        Set<? extends Element> elements = roundEnv.getElementsAnnotatedWith(Bean.class);
        if (elements == null) {
            return false;
        }
        messager.printMessage(Diagnostic.Kind.NOTE, "start to gen");
        CollectionUtils.doForEach(elements,
                (item) -> {
                    Bean bean = item.getAnnotation(Bean.class);
                    CodeGenerator.addClass(elementUtil.getPackageOf(item)
                            .getQualifiedName().toString() + "." + item.getSimpleName(), bean.value());
                });
        CodeGenerator.generate(filer);
        return true;
    }
}
