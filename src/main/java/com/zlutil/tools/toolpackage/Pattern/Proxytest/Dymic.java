package com.zlutil.tools.toolpackage.Pattern.Proxytest;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class Dymic implements InvocationHandler {
    private Object object;

    public Dymic(final Object object) {
        this.object = object;
    }


    public Object invoke(Object proxy, Method method, Object[] args) throws InvocationTargetException, IllegalAccessException {
        System.out.println("...去大润发帮你看看有没有饮料...");
        Object result = method.invoke(object, args);
        System.out.println("...买到了...");
        return result;
    }
}
