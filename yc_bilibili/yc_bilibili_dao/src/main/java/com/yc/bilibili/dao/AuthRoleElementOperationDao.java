package com.yc.bilibili.dao;

import com.yc.bilibili.daomin.auth.BAuthRoleElementOperation;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Set;

@Mapper
public interface AuthRoleElementOperationDao {

    List<BAuthRoleElementOperation> getRoleElementOperations(@Param("roleIdSet") Set<Long> roleIdSet);
}
