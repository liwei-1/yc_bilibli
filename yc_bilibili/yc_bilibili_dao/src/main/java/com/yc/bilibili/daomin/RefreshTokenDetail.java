package com.yc.bilibili.daomin;

import lombok.Data;

import java.util.Date;

/**
 * 刷新token 实体
 */
@Data
public class RefreshTokenDetail {
    private Long id;
    private String refreshToken;
    private Long userId;
    private Date createTime;

}
