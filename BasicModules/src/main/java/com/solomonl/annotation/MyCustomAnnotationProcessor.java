package com.solomonl.annotation;

import javax.annotation.processing.*;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import javax.tools.Diagnostic;
import java.util.Set;

/**
 *
 * Annotation Processing tools 已经过时了，特别是在 Java 8 里。
 * 用法：
 *  要在同一个类路径下才可以读到这个 processor，要不然就找不到么？
 *  [magicliang@magicliang:/Users/magicliang/IdeaProjects/SolomonlReopository/BasicModules/src/main/java]:javac com/solomonl/annotation/MyCustomAnnotationProcessor.java
 *  javac -XprintRounds -processor com.solomonl.annotation.MyCustomAnnotationProcessor com/solomonl/annotation/TestAnnotation.java
 *  循环 1:
 *  输入文件: {com.solomonl.annotation.TestAnnotation}
 *  注释: [com.solomonl.annotation.MyRuntimeAnnotation]
 *  最后一个循环: false
 *  注: ### content = com.solomonl.annotation.TestAnnotation
 *  注: Name is: lc
 *  循环 2:
 *  输入文件: {}
 *  注释: []
 *  最后一个循环: true
 *
 * 这个类要求被处理的注解类至少有 source level 的注解水平。
 *
 * 另一篇文章： http://www.baeldung.com/java-annotation-processing-builder
 * Created by liangchuan on 2017/6/12.
 */
@SupportedAnnotationTypes("com.solomonl.annotation.MyRuntimeAnnotation")
@SupportedSourceVersion(SourceVersion.RELEASE_8)
//@AutoService(Processor.class)
public class MyCustomAnnotationProcessor extends AbstractProcessor {
    //private Set<Class<? extends Annotation>> supportedAnnotationTypes = new HashSet<>();

    /**
     * 不要用这种覆盖的方法，应该使用注解为好。
     */
    //private Set<String> supportedAnnotationTypes = new HashSet<String>();
    /**
     * 注意区分 ProcessingEnvironment 和 RoundEnvironment。
     * @param env
     */
//    @Override
//    public synchronized void init(ProcessingEnvironment env) {
//        super.init(env);
//        //supportedAnnotationTypes.add(MyRuntimeAnnotation.class);
//
//        supportedAnnotationTypes.add(MyRuntimeAnnotation.class
//                .getCanonicalName());
//    }

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

//    @Override
//    public SourceVersion getSupportedSourceVersion() {
//        return SourceVersion.latestSupported();
//    }
//
//    @Override
//    public Set<String> getSupportedAnnotationTypes() {
//        return supportedAnnotationTypes;
//    }
}
