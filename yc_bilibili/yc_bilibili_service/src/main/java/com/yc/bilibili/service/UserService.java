package com.yc.bilibili.service;

import com.mysql.cj.util.StringUtils;
import com.yc.bilibili.dao.UserDao;
import com.yc.bilibili.daomin.User;
import com.yc.bilibili.daomin.UserInfo;
import com.yc.bilibili.daomin.constant.UserConstant;
import com.yc.bilibili.daomin.exception.ConditionException;
import com.yc.bilibili.service.util.MD5Util;
import com.yc.bilibili.service.util.RSAUtil;
import com.yc.bilibili.service.util.TokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Set;

@Service
public class UserService {


    private UserDao userDao;

    @Autowired
    public void setUserDao(UserDao userDao){
        this.userDao = userDao;

    }

    public void addUser(User user) {
        String phone = user.getPhone();
        if (StringUtils.isNullOrEmpty(phone)) {
            throw new ConditionException("手机号不能为空！");
        }
        User dbUser = this.getUserByPhone(phone);
        if (dbUser != null) {
            throw new ConditionException("改手机已经注册！");
        }
        // 用户密码需要MD5的加密
        Date now = new Date();
        String salt = String.valueOf(now.getTime());
        // rsa加密 密码
        String password = user.getPassword();
        String rawPassword;
        try {
            // 密码解密
            rawPassword = RSAUtil.decrypt(password);
        } catch (Exception e) {
            throw new ConditionException("密码解析失败！");
        }
        // 通过时间戳生成盐值
        String md5Passwrod = MD5Util.sign(rawPassword, salt, "UTF-8");
        user.setSalt(salt);
        user.setPassword(md5Passwrod);
        user.setCreateTime(now);
        userDao.addUser(user);
        //添加用户信息表
        UserInfo userInfo = new UserInfo();
        userInfo.setUserId(user.getId());
        userInfo.setNick(UserConstant.DEFAULT_NICK);
        userInfo.setBirth(UserConstant.DEFAULT_BIRTH);
        userInfo.setGender(UserConstant.GENDER_UNKNOW);
        userInfo.setCreateTime(now);
        userDao.addUserInfo(userInfo);

    }

    public User getUserByPhone(String phone) {
        return userDao.getUserByPhone(phone);
    }

    public User getUserByUserInfo(User user) {
        return userDao.getUserByUserInfo(user);
    }

    public String login(User user) throws Exception{
        String phone = user.getPhone();
        String email = user.getEmail();
        User dbUser =null;
        if (StringUtils.isNullOrEmpty(phone) && StringUtils.isNullOrEmpty(email)) {
            throw new ConditionException("手机号和邮箱不能同时为空！");
        }else {
            dbUser = this.getUserByUserInfo(user);
            if (dbUser == null) {
                throw new ConditionException("当前用户不存在！");
            }
        }
        //        if (StringUtils.isNullOrEmpty(phone)) {
        //       throw new ConditionException("手机号不能为空！");
        //        }

        String passwrod = user.getPassword();
        String rawPassword;
        try {
            rawPassword = RSAUtil.decrypt(passwrod);
        } catch (Exception e) {
            throw new ConditionException("密码解析失败！");
        }
        String salt = dbUser.getSalt();
        // 加密后的MD5密码
        String md5Passwrod = MD5Util.sign(rawPassword, salt, "UTF-8");
        if (!md5Passwrod.equals(dbUser.getPassword())) {
            throw new ConditionException("密码错误！");
        }
        return TokenUtil.generateToken(dbUser.getId());
    }


    public User getUserInfo(Long userId){
        User user = userDao.getUserById(userId);
        UserInfo userInfo = userDao.getUserInfoBuUserId(userId);
        user.setUserInfo(userInfo);
        return user;
    }

    public void updateUserInfos(UserInfo userInfo){
        userInfo.setUpdateTime(new Date());
        userDao.updateUserInfos(userInfo);
    }

    public void updateUsers(User user){
        user.setUpdateTime(new Date());
        userDao.updateUsers(user);
    }

    public User getUserById(Long followingId){
        return userDao.getUserById(followingId);
    }


    public List<UserInfo> getUserInfoByUserIdList(Set<Long> followingIdSet) {
        return userDao.getUserInfoByUserIdList(followingIdSet);
    }
}
