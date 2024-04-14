package com.yc.bilibili.dao;

import com.yc.bilibili.daomin.auth.BAuthRole;
import com.yc.bilibili.daomin.auth.BAuthRoleElementOperation;
import com.yc.bilibili.daomin.auth.BUserRole;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;


@Mapper
public interface UserRoleDao {

    List<BUserRole> getUserRoleByUserId(Long userId);


    Integer addUserRole(BUserRole bUserRole);
}
