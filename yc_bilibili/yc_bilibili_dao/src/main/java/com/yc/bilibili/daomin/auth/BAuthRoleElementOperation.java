package com.yc.bilibili.daomin.auth;

import java.util.Date;
import java.io.Serializable;

/**
 * 权限控制--角色与元素操作关联表(BAuthRoleElementOperation)实体类
 *
 * @author makejava
 * @since 2024-04-10 15:36:05
 */
public class BAuthRoleElementOperation implements Serializable {
    private static final long serialVersionUID = -41126603481558702L;
    /**
     * 主键id
     */
    private Long id;
    /**
     * 角色id
     */
    private Long roleId;
    /**
     * 元素操作id
     */
    private Long elementOperationId;
    /**
     * 创建时间
     */
    private Date createTime;

    private BAuthRoleElementOperation authRoleElementOperation;
}

