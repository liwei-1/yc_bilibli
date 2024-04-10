package com.yc.bilibili.daomin.auth;

import lombok.Data;

import java.util.Date;
import java.io.Serializable;

/**
 * 权限控制-页面访问表(BAuthMenu)实体类
 *
 * @author makejava
 * @since 2024-04-10 15:38:08
 */
@Data
public class BAuthMenu implements Serializable {
    private static final long serialVersionUID = 643357956005455439L;
    /**
     * 主键id
     */
    private Long id;
    /**
     * 菜单项目名称
     */
    private String name;
    /**
     * 唯一编码
     */
    private String code;
    /**
     * 创建时间
     */
    private Date createTime;
    /**
     * 更新时间
     */
    private Date updateTime;


}

