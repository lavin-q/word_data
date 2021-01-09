package com.elite.webdata.designPatterns.observer.achieve;

import com.elite.webdata.designPatterns.observer.achieve.observer.NBAObserver;
import com.elite.webdata.designPatterns.observer.achieve.observer.StockObserver;
import com.elite.webdata.designPatterns.observer.achieve.subject.Boss;

/**
 * @Description : 模拟实现  //描述
 * @Author : qhm  //作者
 * @Date: 2020-07-06 16:29  //时间
 */
public class Client {

    /*public static void main(String[] args) {
        //前台为通知者
        Secretary secretary = new Secretary();
        StockObserver observer1 = new StockObserver("adam", secretary);
        NBAObserver observer2 = new NBAObserver("tom", secretary);

        //前台通知
        secretary.attach(observer1);
        secretary.attach(observer2);

        //adam没被前台通知到，所以被老板抓了个现行
        secretary.detach(observer1);

        //老板回来了
        secretary.setAction("小心！Boss回来了！");
        //发通知
        secretary.notifyObservers();

    }*/

    public static void main(String[] args) {
        //老板为通知者
        Boss boss = new Boss();

        StockObserver observer = new StockObserver("adam", boss);
        NBAObserver observer2 = new NBAObserver("tom", boss);

        //老板通知
        boss.attach(observer);
        boss.attach(observer2);

        //tom没被老板通知到，所以不用挨骂
        boss.detach(observer2);

        //老板回来了
        boss.setAction("咳咳，我大Boss回来了！");
        //发通知
        boss.notifyObservers();
    }
}
