package com.solomonl.proxy.cglib;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * Created by LC on 2017/6/18.
 */
public class CGLIBProxyTest {
    public static void main(String[] args) {

        MethodInterceptorProxyFactory<ConcreteImpl> methodInterceptorProxyFactory = new MethodInterceptorProxyFactory<>();
        ConcreteImpl newProxy = methodInterceptorProxyFactory.getInstance(new ConcreteImpl());
        System.out.println(newProxy.enhancedOperation());
        System.out.println(newProxy.normalOperation());

        FixValuedProxyFactory<ConcreteImpl> fixValuedProxyFactory = new FixValuedProxyFactory<>();
        newProxy = fixValuedProxyFactory.getInstance(new ConcreteImpl());
        System.out.println("Echo agc: " + newProxy.echo("agc"));
        System.out.println("Echo 123: " + newProxy.echo("123"));

        InvocationHandlerProxyFactory<ConcreteImpl> invocationHandlerProxyFactory = new InvocationHandlerProxyFactory<>();
        newProxy = invocationHandlerProxyFactory.getInstance(new ConcreteImpl());
        System.out.println("Echo agc: " + newProxy.echo("agc"));

        CallBackFilterFactory<ConcreteImpl> callBackFilterFactory = new CallBackFilterFactory<>();
        newProxy = callBackFilterFactory.getInstance(new ConcreteImpl());
        System.out.println("Echo agc: " + newProxy.echo("agc"));

        ImmutableBeanFactory<SampleBean> immutableBeanFactory1 = new ImmutableBeanFactory<>();
        SampleBean immutableBean1 = immutableBeanFactory1.getInstance(new SampleBean());
        try {
            immutableBean1.setValue("agc");
        } catch (IllegalStateException ex) {
            System.out.println(ex);
        }

        ImmutableBeanFactory<ConcreteImpl> immutableBeanFactory2 = new ImmutableBeanFactory<>();
        ConcreteImpl concrete = new ConcreteImpl();
        /**
         * 注意，这一句和
         *         ConcreteImpl newProxy = methodInterceptorProxyFactory.getInstance(new ConcreteImpl());
         *         System.out.println(newProxy.enhancedOperation());
         *         这两句矛盾。只要执行了这个enhancedOperation，这个类型的任何实例都不能生成 ImmutableBean 了。奇怪得很。不知道是不是bug？
         *         简而言之， MethodInterceptor 和
         * */
//        ConcreteImpl immutableBean2 = immutableBeanFactory2.getInstance(concrete);
//        try {
//            immutableBean2.setValue("agc");
//        } catch (IllegalStateException ex) {
//            System.out.println(ex);
//        }

        BeanGeneratorFactory<SampleBean> beanGeneratorFactory = new BeanGeneratorFactory<>();
        SampleBean sampleBean = beanGeneratorFactory.getInstance(new SampleBean());
        Method setter = null;
        try {
            setter = sampleBean.getClass().getMethod("setHelloWord", String.class);
            setter.invoke(sampleBean, "Hello cglib");

            Method getter = sampleBean.getClass().getMethod("getHelloWord");
            System.out.println("beanGeneratorFactory: " + getter.invoke(sampleBean));
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }


    }
}
