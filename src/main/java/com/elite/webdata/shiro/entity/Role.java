package com.elite.webdata.shiro.entity;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

/**
 * 角色实体
 */
@Getter
@Setter
@AllArgsConstructor
public class Role {

    private String id;

    private String roleName;
    /**
     * 角色对应权限集合
     */
    private Set<Permissions> permissions;
}
