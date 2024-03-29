package com.yc.bilibili.daomin;

import lombok.Data;

import java.util.Date;

/**
 * 用户关注表实体类
 * @author HashQ
 */
@Data
public class UserFollowing {

    /**
     * id
     */
    private Long id;

    /**
     * 用户id
     */
    private Long userId;

    /**
     * 关注用户id
     */
    private Long followingId;

    /**
     * 关注分组id
     */
    private Long groupId;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 用户信息
     */
    private UserInfo userInfo;
}
