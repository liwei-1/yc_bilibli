package com.yc.bilibili.dao;

import com.yc.bilibili.daomin.FollowingGroup;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface FollowingGroupDao {

    FollowingGroup getByType(String type);

    FollowingGroup getById(Long id);

    List<FollowingGroup>  getByUserId(Long userId);
}
