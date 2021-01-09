package com.elite.webdata.designPatterns.observer.achieve.subject;

import com.elite.webdata.designPatterns.observer.achieve.observer.Observer;

/**
 * @Description : 通知者接口  //描述
 * @Author : qhm  //作者
 * @Date: 2020-07-06 16:20  //时间
 */
public interface Subject {
    //增加
    public void attach(Observer observer);

    //删除
    public void detach(Observer observer);

    //通知
    public void notifyObservers();

    //状态
    public void setAction(String action);

    public String getAction();
}
