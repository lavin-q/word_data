package com.elite.webdata.designPatterns.observer.demo.subject;

import com.elite.webdata.designPatterns.observer.demo.observer.Observer;

import java.util.Vector;

/**
 * @Description : 具体主题  //描述
 * @Author : qhm  //作者
 * @Date: 2020-07-03 11:20  //时间
 */
public class ConcreteSubject implements Subject {
    Vector<Observer> oVector = new Vector<>();
    //具体业务
     public void doSomething() {
                 //...
                 Subject.super.notifyObserver(oVector);
            }

    public Vector<Observer> getoVector() {
        return oVector;
    }

}
