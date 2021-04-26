package com.zlutil.tools.toolpackage.Pattern.Factory;

public class DivOperationFactory implements ICalFactory {
    @Override
    public CalOperation creatCalOperation() {
        return new DivCalOperation();
    }
}
