package com.elite.webdata.jwt;


import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * JWT配置实体类
 */
@ConfigurationProperties(prefix = "audience")
@Component
@Getter
@Setter
public class Audience {
    private String clientId;
    private String base64Secret;
    private String name;
    /**
     * 过期时间
     */
    private int expiresSecond;
}
