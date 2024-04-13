package com.yc.bilibili.daomin.auth;

import lombok.Data;

import java.util.Date;
import java.io.Serializable;

/**
 * 用户角色关联表(BUserRole)实体类
 *
 * @author makejava
 * @since 2024-04-10 11:20:35
 */

@Data
public class BUserRole implements Serializable {
    private static final long serialVersionUID = 492329081970893155L;
    
    private Long id;
    /**
     * 用户id
     */
    private Long userId;
    /**
     * 角色id
     */
    private Long roleId;

    /**
     * 角色名称
     */
    private String roleName;

    /**
     * 角色code
     */
    private String roleCode;

    /**
     * 创建时间
     */
    private Date createTime;




}

