package com.elite.webdata.designPatterns.strategy.calculation.operation;

import com.elite.webdata.designPatterns.strategy.calculation.Strategy;

/**
 * 加法
 */
public class OperationAdd implements Strategy<Integer> {
   @Override
   public Integer doOperation(Integer num1, Integer num2) {
      return num1 + num2;
   }
}