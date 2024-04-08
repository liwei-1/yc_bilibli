package com.yc.bilibili.service;


import com.yc.bilibili.dao.UserMomentsDao;
import com.yc.bilibili.daomin.UserMoment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class UserMomentsService {

    @Autowired
    private UserMomentsDao userMomentsDao;

    @Autowired
    private ApplicationContext applicationContext;

    public void addUserMoments(UserMoment userMoment) {
        userMoment.setCreateTime(new Date());
        userMomentsDao.addUserMoments(userMoment);
    }
}
