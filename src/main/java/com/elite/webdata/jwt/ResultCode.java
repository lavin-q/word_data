package com.elite.webdata.jwt;


import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 返回码枚举
 */
@Getter
@AllArgsConstructor
public enum ResultCode {

    USER_NOT_LOGGED_IN(1001,"用户未登录!"),
    PERMISSION_SIGNATURE_ERROR(1002,"签名失败!"),
    PERMISSION_TOKEN_INVALID(1003,"签名解析异常!"),
    PERMISSION_TOKEN_EXPIRED(1004,"签名过期!")
    ;

    private Integer code;
    private String message;
}
