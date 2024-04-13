package com.yc.bilibili.api.aspect;

import com.yc.bilibili.api.support.UserSupport;
import com.yc.bilibili.daomin.annotation.ApiLimitedRole;
import com.yc.bilibili.daomin.auth.BUserRole;
import com.yc.bilibili.daomin.exception.ConditionException;
import com.yc.bilibili.service.UserRoleService;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 切面
 * @author 12490
 */
@Order(1)
@Component
@Aspect
public class ApiLimitedRoleAspect {

    @Autowired
    private UserSupport userSupport;

    @Autowired
    private UserRoleService userRoleService;

    @Pointcut("@annotation(com.yc.bilibili.daomin.annotation.ApiLimitedRole)")
    public void check(){

    }
    @Before("check() && @annotation(apiLimitedRole)")
    public void doBefore(JoinPoint joinPoin, ApiLimitedRole apiLimitedRole){
        Long userId = userSupport.getCurrentUserId();
        List<BUserRole> userRoleList =  userRoleService.getUserRoleByUserId(userId);
        String[] limitedRoleCodeList = apiLimitedRole.limitedRoleCodeList();
        Set<String> limitedRoleCodeSet = Arrays.stream(limitedRoleCodeList).collect(Collectors.toSet());
        Set<String> roleCodeSet = userRoleList.stream().map(BUserRole::getRoleCode).collect(Collectors.toSet());
        roleCodeSet.retainAll(limitedRoleCodeSet);
        if (roleCodeSet.size()>0){
            throw new ConditionException("权限不足!");
        }

    }
}
