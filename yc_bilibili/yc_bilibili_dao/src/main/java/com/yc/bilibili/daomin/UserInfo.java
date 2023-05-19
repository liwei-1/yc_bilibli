package com.yc.bilibili.daomin;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

/**
 * 用户信息表实体
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserInfo implements Serializable {
    private static final long serialVersionUID = 1L;
    /**
     * id
     */
    private Long id;
    /**
     * 用户id
     */
    private Long userId;
    /**
     * 昵称
     */
    private String nick;
    /**
     * 头像
     */
    private String avatar;
    /**
     * 签名
     */
    private String sign;
    /**
     * 生日
     */
    private String birth;
    /**
     * 性别：0男 1女 2未知
     */
    private String gender;
    /**
     * 创建时间
     */
    private Date createTime;
    /**
     * 更新时间
     */
    private Date updateTime;

}
