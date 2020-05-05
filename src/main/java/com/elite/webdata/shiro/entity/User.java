package com.elite.webdata.shiro.entity;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

/**
 * 用户实体
 */
@Getter
@Setter
@AllArgsConstructor
public class User {

    private String id;

    private String userName;

    private String password;

    /**
     * 用户对应的角色集合
     */
    private Set<Role> roles;
}
