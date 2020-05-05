package com.elite.webdata.jwt;


/**
 * 统一异常处理
 */
public class CustomException extends RuntimeException {

    private ResultCode resultCode;


    public CustomException(ResultCode resultCode){
        this.resultCode = resultCode;
    }

}
