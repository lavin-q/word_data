package com.elite.webdata.designPatterns.observer.achieve.observer;

import com.elite.webdata.designPatterns.observer.achieve.subject.Subject;

/**
 * @Description : 看NBA的同事(观察者)  //描述
 * @Author : qhm  //作者
 * @Date: 2020-07-06 16:27  //时间
 */
public class NBAObserver extends Observer {

    public NBAObserver(String name, Subject subject) {
        super(name, subject);
    }

    @Override
    public void update() {
        System.out.println(subject.getAction() + "\n" + name + "关闭NBA视频，继续工作");
    }
}
