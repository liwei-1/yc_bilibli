package com.yc.bilibili.service;

import com.yc.bilibili.dao.AuthRoleElementOperationDao;
import com.yc.bilibili.daomin.auth.BAuthRoleElementOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
public class AuthRoleElementOperationService {

    @Autowired
    private AuthRoleElementOperationDao authRoleElementOperationDao;

    public List<BAuthRoleElementOperation> getRoleElementOperations(Set<Long> roleIdSet) {
        return authRoleElementOperationDao.getRoleElementOperations(roleIdSet);
    }
}
