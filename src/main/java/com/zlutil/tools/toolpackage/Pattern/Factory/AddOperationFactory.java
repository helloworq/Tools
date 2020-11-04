package com.zlutil.tools.toolpackage.Pattern.Factory;

public class AddOperationFactory implements ICalFactory {
    @Override
    public CalOperation creatCalOperation() {
        return new AddCalOperation();
    }
}
