package com.zlutil.tools.toolpackage.Pattern.Proxytest;

import com.zlutil.tools.toolpackage.Pattern.Proxytest.dynamicProxy.ProxyHandller;
import com.zlutil.tools.toolpackage.Pattern.Proxytest.staticProxy.BuyDrinkProxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;

public class ProxyRunner {
    public static void main(String[] args) {
        //测试代理模式
        /**
         * 静态代理测试
         */
        System.out.println("---静态代理---");
        BuyDrink buyDrink = new BuyDrink();
        BuyDrinkProxy buyDrinkProxy = new BuyDrinkProxy(buyDrink);
        buyDrinkProxy.buy();

        System.out.println("\n\n");
        /**
         * 动态代理测试
         */
        System.out.println("---动态代理---");
        //创建被代理类
        BuySomething buydrink=new BuyDrink();
        //创建调用器，将被代理类作为参数
        InvocationHandler handler = new ProxyHandller(buydrink);
        //创建代理类
        BuySomething proxyBuyDrink = (BuySomething)
                Proxy.newProxyInstance(
                        buydrink.getClass().getClassLoader(),
                        buydrink.getClass().getInterfaces(),
                        new ProxyHandller(buydrink)
                );
        proxyBuyDrink.buy();
    }
}