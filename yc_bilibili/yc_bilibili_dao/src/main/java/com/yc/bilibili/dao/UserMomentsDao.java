package com.yc.bilibili.dao;

import com.yc.bilibili.daomin.UserFollowing;
import com.yc.bilibili.daomin.UserMoment;
import org.apache.ibatis.annotations.Mapper;

import javax.lang.model.type.IntersectionType;

@Mapper
public interface UserMomentsDao {
    Integer addUserMoments(UserMoment moment);
}
