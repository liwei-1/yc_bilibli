package com.yc.bilibili.service;

import com.yc.bilibili.dao.UserRoleDao;
import com.yc.bilibili.daomin.auth.BAuthRole;
import com.yc.bilibili.daomin.auth.BAuthRoleElementOperation;
import com.yc.bilibili.daomin.auth.BUserRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserRoleService {

    @Autowired
    private UserRoleDao userRoleDao;
    public List<BUserRole> getUserRoleByUserId(Long userId) {
        return userRoleDao.getUserRoleByUserId(userId);
    }

}
