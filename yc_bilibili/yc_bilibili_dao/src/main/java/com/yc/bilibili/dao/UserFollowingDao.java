package com.yc.bilibili.dao;

import com.yc.bilibili.daomin.UserFollowing;
import com.yc.bilibili.daomin.UserInfo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Set;

@Mapper
public interface UserFollowingDao {
    /**
     * 删除用户关注关联关系
     * @param userId 用户id
     * @param followingId
     * @return
     */
    Integer deleteUserFollowing(@Param("userId") Long userId, @Param("followingId") Long followingId);

    Integer addUserFollowing(UserFollowing userFollowing);

    /**
     * 根据userid查询用户信息
     * @param userId
     * @return
     */
    List<UserFollowing> getUserFollowingList(Long userId);

    List<UserFollowing> getUserFans(Long userId);

}

