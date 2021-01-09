package com.elite.webdata.designPatterns.observer.achieve.observer;

import com.elite.webdata.designPatterns.observer.achieve.subject.Subject;

/**
 * @Description : 观察者接口  //描述
 * @Author : qhm  //作者
 * @Date: 2020-07-06 16:21  //时间
 */
public abstract class Observer {

    protected String name;
    
    protected Subject subject;

    public Observer(String name, Subject subject) {
        this.name = name;
        this.subject = subject;
    }

    public abstract void update();
}
