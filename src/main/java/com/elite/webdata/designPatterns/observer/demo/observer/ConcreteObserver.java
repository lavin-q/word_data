package com.elite.webdata.designPatterns.observer.demo.observer;

/**
 * @Description : 具体观察者  //描述
 * @Author : qhm  //作者
 * @Date: 2020-07-03 11:20  //时间
 */
public class ConcreteObserver implements Observer {
    @Override
    public void update() {
        System.out.println("收到消息，进行处理");
    }
}
