package com.yc.bilibili.service;

import com.yc.bilibili.dao.AuthRoleDao;
import com.yc.bilibili.daomin.auth.BAuthRole;
import com.yc.bilibili.daomin.auth.BAuthRoleElementOperation;
import com.yc.bilibili.daomin.auth.BAuthRoleMenu;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
public class AuthRoleService {

    @Autowired
    private AuthRoleDao authRoleDao;


    @Autowired
    private AuthRoleElementOperationService authRoleElementOperationService;


    @Autowired
    private AuthRoleMenuService authRoleMenuService;


    public List<BAuthRoleElementOperation> getRoleElementOperations(Set<Long> roleIdSet) {
         return authRoleElementOperationService.getRoleElementOperations(roleIdSet);
    }

    public List<BAuthRoleMenu> getRoleMenus(Set<Long> roleIdSet) {
        return authRoleMenuService.getRoleMenus(roleIdSet);
    }

    public BAuthRole getRoleByCode(String code) {
        return authRoleDao.getRoleByCode(code);
    }
}
