package com.yc.bilibili.service;

import com.yc.bilibili.dao.AuthRoleMenuDao;
import com.yc.bilibili.daomin.auth.BAuthRoleMenu;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
public class AuthRoleMenuService {

    @Autowired
    private AuthRoleMenuDao authRoleMenuDao;
    public List<BAuthRoleMenu> getRoleMenus(Set<Long> roleIdSet) {
        return authRoleMenuDao.getRoleMenus(roleIdSet);
    }
}
