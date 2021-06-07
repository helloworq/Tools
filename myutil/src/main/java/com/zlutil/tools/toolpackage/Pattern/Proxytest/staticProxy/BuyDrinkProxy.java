package com.zlutil.tools.toolpackage.Pattern.Proxytest.staticProxy;

import com.zlutil.tools.toolpackage.Pattern.Proxytest.BuyDrink;
import com.zlutil.tools.toolpackage.Pattern.Proxytest.BuySomething;

public class BuyDrinkProxy implements BuySomething {
    private BuyDrink buyDrink;

    public BuyDrinkProxy(final BuyDrink buyDrink) {
        this.buyDrink = buyDrink;
    }

    @Override
    public void buy() {
        System.out.println("...去大润发帮你看看有没有饮料...");
        buyDrink.buy();
        System.out.println("...买到了...");
    }
}
