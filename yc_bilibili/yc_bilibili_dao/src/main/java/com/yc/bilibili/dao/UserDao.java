package com.yc.bilibili.dao;

import com.alibaba.fastjson.JSONObject;
import com.yc.bilibili.daomin.User;
import com.yc.bilibili.daomin.UserInfo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;
import java.util.Set;

@Mapper
public interface UserDao {

    User  getUserByPhone(String phone);

    User getUserByUserInfo(User user);

    Integer addUser(User user);

    Integer addUserInfo(UserInfo userInfo);

    User getUserById(Long id);

    UserInfo getUserInfoBuUserId(Long userId);


    Integer updateUserInfos(UserInfo userInfo);

    Integer updateUsers(User user);

    /**
     * 感觉关注用户id查询 用户详细信息
     * @param followingIdSet
     * @return UserInfo
     */
    List<UserInfo> getUserInfoByUserIdList(Set<Long> followingIdSet);

    Integer pageCountUserInfos(Map<String,Object> params);

    List<UserInfo> pageListUserInfos(Map<String,Object> params);
}
