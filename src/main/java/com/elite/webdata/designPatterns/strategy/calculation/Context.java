package com.elite.webdata.designPatterns.strategy.calculation;

import javax.validation.constraints.NotNull;

/**
 * @Description : 上下文  //描述
 * @Author : qhm  //作者
 * @Date: 2020-07-02 15:55  //时间
 */
public class Context {

    @NotNull
    private Strategy strategy;

    public Context(Strategy strategy) {
        this.strategy = strategy;
    }

    public int executeStrategy(int num1, int num2) {
        return (int) strategy.doOperation(num1, num2);
    }
}
