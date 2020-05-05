package com.elite.webdata.jwt;


import com.alibaba.fastjson.JSON;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Result {

    private String message;
    private Integer code;
    private Object object;

    public Result(Object object,String message ,Integer code){
        this.code = code;
        this.message = message;
        this.object = JSON.parseObject(object.toString());
    }


    public static Result SUCCESS(Object  object){

        return new Result(object,"操作成功",0);
    }

    public static Result SUCCESS(){
        return new Result("","操作成功",0);
    }

}
