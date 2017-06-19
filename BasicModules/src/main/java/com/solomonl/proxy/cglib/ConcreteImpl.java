package com.solomonl.proxy.cglib;

/**
 * Created by LC on 2017/6/18.
 */
public class ConcreteImpl implements TestInterface {
    public int enhancedOperation() {
        System.out.println("增强方法...");
        return 1;
    }

    @Override
    public int normalOperation() {
        System.out.println("普通方法...");
        return 1;
    }

    @Override
    public String echo(String str) {
        return str;
    }
}
