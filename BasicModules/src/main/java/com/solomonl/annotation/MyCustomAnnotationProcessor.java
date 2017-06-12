package com.solomonl.annotation;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Messager;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import javax.tools.Diagnostic;
import java.util.HashSet;
import java.util.Set;

/**
 *
 * 这个类要求被处理的注解类至少有 source level 的注解水平。
 * Created by liangchuan on 2017/6/12.
 */
public class MyCustomAnnotationProcessor extends AbstractProcessor {
    //private Set<Class<? extends Annotation>> supportedAnnotationTypes = new HashSet<>();
    private Set<String> supportedAnnotationTypes = new HashSet<String>();
    /**
     * 注意区分 ProcessingEnvironment 和 RoundEnvironment。
     * @param env
     */
    @Override
    public synchronized void init(ProcessingEnvironment env) {
        super.init(env);
        //supportedAnnotationTypes.add(MyRuntimeAnnotation.class);

        supportedAnnotationTypes.add(MyRuntimeAnnotation.class
                .getCanonicalName());
    }

    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        Messager messager = processingEnv.getMessager();
        // Java 语言模型包里的 element
        for(TypeElement typeElement : annotations){
            for(Element element : roundEnv.getElementsAnnotatedWith(typeElement)){

                String info = "### content = " + element.toString();
                messager.printMessage(Diagnostic.Kind.NOTE, info);
                //获取Annotation

                MyRuntimeAnnotation myRuntimeAnnotation = element.getAnnotation(MyRuntimeAnnotation.class);


                if (myRuntimeAnnotation != null) {
                    String name = myRuntimeAnnotation.name();

                    messager.printMessage(Diagnostic.Kind.NOTE, "Name is: " + name);
                }



            }
        }
        return false;
    }

    @Override
    public SourceVersion getSupportedSourceVersion() {
        return SourceVersion.latestSupported();
    }

    @Override
    public Set<String> getSupportedAnnotationTypes() {
        return supportedAnnotationTypes;
    }
}
