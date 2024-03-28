package com.yc.bilibili.dao;

import com.yc.bilibili.daomin.User;
import com.yc.bilibili.daomin.UserInfo;
import org.apache.ibatis.annotations.Mapper;

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
}
