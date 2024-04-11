package com.yc.bilibili.service;

import com.yc.bilibili.daomin.auth.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class UserAuthService {

    @Autowired
    private UserRoleService userRoleService;

    @Autowired
    private AuthRoleService authRoleService;

    public UserAuthorities getUserAuthorities(Long userId) {
        List<BUserRole> authRoleList = userRoleService.getUserRoleByUserId(userId);
        Set<Long> roleIdSet = authRoleList.stream().map(BUserRole::getRoleId).collect(Collectors.toSet());
        //查询按钮操作权限
        List<BAuthRoleElementOperation> authRoleElementOperationList = authRoleService.getRoleElementOperations(roleIdSet);
        //查询页面权限
        List<BAuthRoleMenu> authRoleMenuList =authRoleService.getRoleMenus(roleIdSet);
        UserAuthorities userAuthorities= new UserAuthorities();
        userAuthorities.setRoleMenuList(authRoleMenuList);
        userAuthorities.setRoleElementOperationList(authRoleElementOperationList);
        return userAuthorities;
    }
}
