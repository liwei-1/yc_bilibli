package com.yc.bilibili.daomin.auth;

import lombok.Data;

import java.util.Date;
import java.io.Serializable;

/**
 * 权限控制--角色页面菜单关联表(BAuthRoleMenu)实体类
 *
 * @author makejava
 * @since 2024-04-10 15:37:41
 */
@Data
public class BAuthRoleMenu implements Serializable {
    private static final long serialVersionUID = -53963617283740151L;
    /**
     * 主键id
     */
    private Long id;
    /**
     * 角色id
     */
    private Long roleId;
    /**
     * 页面菜单id
     */
    private Long menuId;
    /**
     * 创建时间
     */
    private Date createTime;

}

