package com.elite.webdata.designPatterns.observer.achieve.subject;

import com.elite.webdata.designPatterns.observer.achieve.observer.Observer;

import java.util.LinkedList;
import java.util.List;

/**
 * @Description : 具体通知者（公司前台）  //描述
 * @Author : qhm  //作者
 * @Date: 2020-07-06 16:22  //时间
 */
public class Secretary implements Subject {
    //同事列表
    private List<Observer> observers = new LinkedList<>();
    //状态更迭
    private String action;

    @Override
    public void attach(Observer observer) {
        observers.add(observer);
    }

    @Override
    public void detach(Observer observer) {
        observers.remove(observer);
    }

    @Override
    public void notifyObservers() {
        for (Observer observer : observers) {
            observer.update();
        }
    }

    @Override
    public void setAction(String action) {
        this.action = action;
    }

    @Override
    public String getAction() {
        return action;
    }
}
