package com.yc.bilibili.daomin.auth;

import lombok.Data;

import java.util.Date;
import java.io.Serializable;

/**
 * 权限控制--角色表(BAuthRole)实体类
 *
 * @author makejava
 * @since 2024-04-10 15:31:50
 */
@Data
public class BAuthRole implements Serializable {
    private static final long serialVersionUID = 983689101583711349L;
    /**
     * 主键id
     */
    private Long id;
    /**
     * 角色名称
     */
    private String name;
    /**
     * 角色唯一编码
     */
    private String code;
    /**
     * 创建时间
     */
    private Date createtime;
    /**
     * 更新时间
     */
    private Date updatetime;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Date getCreatetime() {
        return createtime;
    }

    public void setCreatetime(Date createtime) {
        this.createtime = createtime;
    }

    public Date getUpdatetime() {
        return updatetime;
    }

    public void setUpdatetime(Date updatetime) {
        this.updatetime = updatetime;
    }

}

