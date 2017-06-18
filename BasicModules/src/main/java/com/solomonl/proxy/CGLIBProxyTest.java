package com.solomonl.proxy;

/**
 * Created by LC on 2017/6/18.
 */
public class CGLIBProxyTest {
    public static void main(String[] args) {

        CglibProxyFactory bookFacadeCglib = new CglibProxyFactory();
        ConcreteImpl newProxy = (ConcreteImpl) bookFacadeCglib.getInstance(new ConcreteImpl());
        System.out.println(newProxy.enhancedOperation());
        System.out.println(newProxy.normalOperation());
    }
}
