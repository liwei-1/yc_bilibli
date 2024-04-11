package com.yc.bilibili.daomin.auth;

import lombok.Data;

import java.util.List;

@Data
public class UserAuthorities {

    /**
     * 页面操作权限
     */
    List<BAuthRoleElementOperation> roleElementOperationList;

    /**
     * 页面菜单权限
     */
    List<BAuthRoleMenu> roleMenuList;
}
