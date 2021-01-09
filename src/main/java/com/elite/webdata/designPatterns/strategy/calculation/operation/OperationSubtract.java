package com.elite.webdata.designPatterns.strategy.calculation.operation;

import com.elite.webdata.designPatterns.strategy.calculation.Strategy;

/**
 * 减法
 */
public class OperationSubtract implements Strategy<Integer> {
   @Override
   public Integer doOperation(Integer num1, Integer num2) {
      return num1 - num2;
   }
}