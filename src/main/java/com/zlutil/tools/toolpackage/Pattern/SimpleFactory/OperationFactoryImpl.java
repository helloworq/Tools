package com.zlutil.tools.toolpackage.Pattern.SimpleFactory;

public class OperationFactoryImpl implements IOperationFactory {
    @Override
    public CalOperation getOperation(String operation) {
        CalOperation calOperation;
        switch (operation) {
            case "+": {
                calOperation = new AddCalOperation();
            }
            break;
            case "-": {
                calOperation = new DeleteCalOperation();
            }
            break;
            case "*": {
                calOperation = new MuiltCalOperation();
            }
            break;
            case "/": {
                calOperation = new DivCalOperation();
            }
            break;
            default:
                throw new IllegalStateException("Unexpected value: " + operation);
        }
        return calOperation;
    }
}
