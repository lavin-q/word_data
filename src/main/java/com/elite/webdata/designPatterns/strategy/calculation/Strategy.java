package com.elite.webdata.designPatterns.strategy.calculation;

/**
 * @Description : 策略接口  //描述
 * @Author : qhm  //作者
 * @Date: 2020-07-02 15:57  //时间
 */
public interface  Strategy<Integer> {
    Integer doOperation(Integer num1, Integer num2);
}
