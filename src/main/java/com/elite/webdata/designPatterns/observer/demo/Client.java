package com.elite.webdata.designPatterns.observer.demo;

import com.elite.webdata.designPatterns.observer.demo.observer.ConcreteObserver;
import com.elite.webdata.designPatterns.observer.demo.observer.Observer;
import com.elite.webdata.designPatterns.observer.demo.subject.ConcreteSubject;

public class Client {

    public static void main(String[] args) {
        //创建一个主题
        ConcreteSubject subject = new ConcreteSubject();
        for (int i = 0; i < 10; i++){
            //定义一个观察者
            Observer observer = new ConcreteObserver();
            //观察
            subject.addObserver(observer,subject.getoVector());
        }
        //开始活动
        subject.doSomething();
    }

}