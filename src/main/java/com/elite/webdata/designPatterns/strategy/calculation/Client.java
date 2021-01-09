package com.elite.webdata.designPatterns.strategy.calculation;

import com.elite.webdata.designPatterns.strategy.calculation.operation.OperationAdd;
import com.elite.webdata.designPatterns.strategy.calculation.operation.OperationMultiply;
import com.elite.webdata.designPatterns.strategy.calculation.operation.OperationSubtract;

public class Client {

    public static void main(String[] args) {
        Context context = new Context(new OperationAdd());
        System.out.println("10 + 5 = " + context.executeStrategy(10, 5));

        context = new Context(new OperationSubtract());
        System.out.println("10 - 5 = " + context.executeStrategy(10, 5));

        context = new Context(new OperationMultiply());
        System.out.println("10 * 5 = " + context.executeStrategy(10, 5));
    }

}