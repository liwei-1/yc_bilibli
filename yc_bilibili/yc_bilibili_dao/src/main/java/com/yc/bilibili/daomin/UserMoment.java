package com.yc.bilibili.daomin;

import lombok.Data;

import java.util.Date;

/**
 * 用户动态
 */
@Data
public class UserMoment {
    private Long id;
    private Long userId;
    private String type;
    private Long contentId;
    private Date createTime;
    private Date updateTime;
}
