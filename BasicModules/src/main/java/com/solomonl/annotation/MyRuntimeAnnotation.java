package com.solomonl.annotation;

import java.awt.event.ActionListener;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 *
 * 这是运行时注解。编译时注解最好用 source 的retention type 加上 Annotation Processing Tool来在 javac时进行处理。具体可以谷歌搜索。
 * Created by liangchuan on 2017/6/8.
 */

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface MyRuntimeAnnotation {

    String name() default "lc";
    int age() default 1;

    // 一个相关的用法，可以把某些方法目标 method 标示出来，例如。这也是为什么我们能在很多框架中看到 xx.class 的配置文件的原因。
    //Class<? extends ActionListener> listerner() default null;

    /**
     * 真正使用起来的代码大概是：
     * annotation = object.getClass.getAnnotation(MyRuntimeAnnotation);
     * Class clazz = annotation.listener();
     * Listerner ls = clazz.newInstance();
     * object.addListener(ls)
     */



}
