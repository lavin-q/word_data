package com.elite.webdata.designPatterns.observer.achieve.subject;

import com.elite.webdata.designPatterns.observer.achieve.observer.Observer;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

/**
 * @Description : 老板(通知者)  //描述
 * @Author : qhm  //作者
 * @Date: 2020-07-06 16:30  //时间
 */
public class Boss implements Subject {
    //同事列表
    private List<Observer> observers = new LinkedList<>();
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


    public static int[] twoSum(int[] nums, int target) {
        HashMap<Integer,Integer> map = new HashMap<Integer,Integer>();
        for(int i=0;i<nums.length;i++){
            if(map.containsKey(nums[i])){
                return new int[] {map.get(nums[i]),i};
            }
            map.put(target-nums[i],i);
        }
        return new int[]{1,1,1};
    }

    public static void main(String[] args) {
        int[] i = new int[]{2, 7, 11, 15};
         int[] ints = twoSum(i, 17);
    }
}
