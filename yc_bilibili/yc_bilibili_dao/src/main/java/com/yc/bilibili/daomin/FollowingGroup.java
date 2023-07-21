package com.yc.bilibili.daomin;

import lombok.Data;

import java.util.Date;

/**
 * 用户关注分组表实体类
 * @author HashQ
 */
@Data
public class FollowingGroup {

    /**
     * id
     */
    private Long id;

    /**
     * 用户id
     */
    private Long userId;

    /**
     * 关注分组名称
     */
    private String name;
    /**
     * 0特别关注  1悄悄关注 2默认分组  3用户自定义分组
     */
    private String type;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新时间
     */
    private Date updateTime;
}
