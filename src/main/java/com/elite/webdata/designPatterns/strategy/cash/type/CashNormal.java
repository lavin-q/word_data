package com.elite.webdata.designPatterns.strategy.cash.type;

import com.elite.webdata.designPatterns.strategy.cash.CashSuper;

public class CashNormal implements CashSuper {

    @Override
    public double acceptCash(double money) {
        return money;
    }

}