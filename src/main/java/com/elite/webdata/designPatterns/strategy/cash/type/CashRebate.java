package com.elite.webdata.designPatterns.strategy.cash.type;

import com.elite.webdata.designPatterns.strategy.cash.CashSuper;

public class CashRebate implements CashSuper {

    private double moneyRebate = 1;    //折扣

    public CashRebate(double moneyRebate) {
        this.moneyRebate = moneyRebate;
    }

    @Override
    public double acceptCash(double money) {
        return money * moneyRebate;
    }

}