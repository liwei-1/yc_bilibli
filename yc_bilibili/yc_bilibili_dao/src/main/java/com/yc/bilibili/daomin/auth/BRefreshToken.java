package com.yc.bilibili.daomin.auth;

import lombok.Data;

import java.util.Date;
import java.io.Serializable;

/**
 * 刷新令牌记录表(BRefreshToken)实体类
 *
 * @author makejava
 * @since 2024-04-10 15:40:52
 */
@Data
public class BRefreshToken implements Serializable {
    private static final long serialVersionUID = 348814591466474167L;
    /**
     * 主键id
     */
    private Long id;
    /**
     * 用户id
     */
    private Long userId;
    /**
     * 刷新令牌
     */
    private String refreshToken;
    /**
     * 创建时间
     */
    private Date createTime;



}

