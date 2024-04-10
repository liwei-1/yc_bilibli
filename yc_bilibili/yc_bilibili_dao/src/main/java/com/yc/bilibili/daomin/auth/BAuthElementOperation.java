package com.yc.bilibili.daomin.auth;

import lombok.Data;

import java.util.Date;
import java.io.Serializable;

/**
 * 权限控制--页面元素操作表(BAuthElementOperation)实体类
 *
 * @author makejava
 * @since 2024-04-10 15:41:56
 */
@Data
public class BAuthElementOperation implements Serializable {
    private static final long serialVersionUID = -99414985182662311L;
    /**
     * 主键id
     */
    private Long id;
    /**
     * 页面元素名称
     */
    private String elementName;
    /**
     * 页面元素唯一编码
     */
    private String elementCode;
    /**
     * 操作类型：0可点击  1可见
     */
    private String operationType;
    /**
     * 创建时间
     */
    private Date createTime;
    /**
     * 更新时间
     */
    private Date updateTime;


}

