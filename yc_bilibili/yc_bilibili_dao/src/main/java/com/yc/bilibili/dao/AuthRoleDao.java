package com.yc.bilibili.dao;

import com.yc.bilibili.daomin.auth.BAuthRole;
import org.apache.ibatis.annotations.Mapper;

import javax.annotation.ManagedBean;

@Mapper
public interface AuthRoleDao {
    BAuthRole getRoleByCode(String code);
}
