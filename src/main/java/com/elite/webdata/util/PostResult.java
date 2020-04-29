package com.elite.webdata.util;

import lombok.Getter;
import lombok.Setter;

/**
 * HTTP请求结果
 */
@Getter
@Setter
public class PostResult {
    /**
     * 是否成功
     */
    public boolean success;
    /**
     * http code
     */
    public int code;
    /**
     * 返回的消息体
     */
    private String responsebody;
}
