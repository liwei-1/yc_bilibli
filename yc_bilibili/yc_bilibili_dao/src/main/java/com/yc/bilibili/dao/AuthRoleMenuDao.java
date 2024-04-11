package com.yc.bilibili.dao;

import com.yc.bilibili.daomin.auth.BAuthRoleMenu;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Set;

@Mapper
public interface AuthRoleMenuDao {


    List<BAuthRoleMenu> getRoleMenus(@Param("roleIdSet") Set<Long> roleIdSet);
}
