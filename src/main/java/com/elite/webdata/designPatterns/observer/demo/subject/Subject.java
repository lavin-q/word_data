package com.elite.webdata.designPatterns.observer.demo.subject;

import com.elite.webdata.designPatterns.observer.demo.observer.Observer;

import java.util.Vector;

/**
 * @Description : 主题接口  //描述
 * @Author : qhm  //作者
 * @Date: 2020-07-03 11:18  //时间
 */
public interface Subject {

    //增加一个观察者
    default void addObserver(Observer observer, Vector<Observer> oVector) {
        oVector.add(observer);
    }

    //删除一个观察者
    default void deleteObserver(Observer observer, Vector<Observer> oVector) {
        oVector.remove(observer);
    }

    //通知所有观察者
    default void notifyObserver(Vector<Observer> oVector) {
        for (Observer observer : oVector) {
            observer.update();
        }
    }
}
