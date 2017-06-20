package com.solomonl.proxy.cglib;

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
        ConcreteImpl immutableBean2 = immutableBeanFactory2.getInstance(concrete);
        try {
            immutableBean2.setValue("agc");
        } catch (IllegalStateException ex) {
            System.out.println(ex);
        }
    }
}
