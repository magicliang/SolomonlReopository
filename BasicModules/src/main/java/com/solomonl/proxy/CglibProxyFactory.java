package com.solomonl.proxy;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

/**
 * Created by LC on 2017/6/18.
 */
public class CglibProxyFactory implements MethodInterceptor {
    private Object target;

    public Object getInstance(Object target) {
        this.target = target;
        Enhancer enhancer = new Enhancer();
        // 用 Enhancer 来当一个 dynamic proxy。
        enhancer.setSuperclass(this.target.getClass());
        enhancer.setCallback(this);
        return enhancer.create();
    }

    @Override
    public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {
        // 怎样只增强指定的方法？靠反射获取方法名的形式？是不是一定有返回值的必要呢？
        int result = 0;
        if ("enhancedOperation".equals(method.getName())) {
            System.out.println("开始增强方法");
            result = (Integer) methodProxy.invokeSuper(o, objects);
            System.out.println("方法增强完毕");
        }
        // 不需要 return 任何东西？
        return ++result;
    }
}
