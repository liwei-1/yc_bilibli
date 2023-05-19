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

@Service
public class UserService {

    @Autowired
    private UserDao userDao;

    public void addUser(User user) {
        String phone = user.getPhone();
        if (StringUtils.isNullOrEmpty(phone)) {
            throw new ConditionException("手机号不能为空！");
        }
        User dbUser = this.getUserByPhone(phone);
        if (dbUser != null) {
            throw new ConditionException("改手机已经注册！");
        }
        Date now = new Date();
        String salt = String.valueOf(now.getTime());
        String password = user.getPassword();
        String rawPassword;
        try {
            rawPassword = RSAUtil.decrypt(password);
        } catch (Exception e) {
            throw new ConditionException("密码解析失败！");
        }
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

    public String login(User user) {
        String phone = user.getPhone();
        if (StringUtils.isNullOrEmpty(phone)) {
            throw new ConditionException("手机号不能为空！");
        }
        User dbUser = this.getUserByPhone(phone);
        if (dbUser == null) {
            throw new ConditionException("当前用户不存在！");
        }
        String passwrod = user.getPassword();
        String rawPassword;
        try {
            rawPassword = RSAUtil.decrypt(passwrod);
        } catch (Exception e) {
            throw new ConditionException("密码解析失败！");
        }
        String salt = dbUser.getSalt();
        String md5Passwrod = MD5Util.sign(rawPassword, salt, "UTF-8");
        if (!md5Passwrod.equals(dbUser.getPassword())) {
            throw new ConditionException("密码解析失败！");
        }
        TokenUtil tokenUtil = new TokenUtil();
        return tokenUtil.generateToken(dbUser.getId());
    }

}
