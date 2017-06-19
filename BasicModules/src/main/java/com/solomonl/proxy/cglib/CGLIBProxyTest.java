package com.solomonl.proxy.cglib;

/**
 * Created by LC on 2017/6/18.
 */
public class CGLIBProxyTest {
    public static void main(String[] args) {

        MethodInterceptorProxyFactory methodInterceptorProxyFactory = new MethodInterceptorProxyFactory();
        ConcreteImpl newProxy = (ConcreteImpl) methodInterceptorProxyFactory.getInstance(new ConcreteImpl());
        System.out.println(newProxy.enhancedOperation());
        System.out.println(newProxy.normalOperation());

        FixValuedProxyFactory fixValuedProxyFactory = new FixValuedProxyFactory();
        newProxy = (ConcreteImpl) fixValuedProxyFactory.getInstance(new ConcreteImpl());
        System.out.println("Echo agc: " + newProxy.echo("agc"));
        System.out.println("Echo 123: " + newProxy.echo("123"));

    }
}
