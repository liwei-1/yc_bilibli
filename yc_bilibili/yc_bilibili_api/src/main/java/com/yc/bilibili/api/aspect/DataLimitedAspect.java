package com.yc.bilibili.api.aspect;


import com.yc.bilibili.api.support.UserSupport;
import com.yc.bilibili.daomin.UserMoment;
import com.yc.bilibili.daomin.auth.BUserRole;
import com.yc.bilibili.daomin.constant.AuthRoleConstant;
import com.yc.bilibili.daomin.exception.ConditionException;
import com.yc.bilibili.service.UserRoleService;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 数据权限控制
 * 切面
 *
 */
@Order(1)
@Component
@Aspect
public class DataLimitedAspect {

    @Autowired
    private UserSupport userSupport;

    @Autowired
    private UserRoleService userRoleService;


    @Pointcut("@annotation(com.yc.bilibili.daomin.annotation.DataLimited)")
    public void check(){

    }

    @Before("check()")
    public void doBefore(JoinPoint joinPoint){
        Long userId = userSupport.getCurrentUserId();
        List<BUserRole> userRoleList =  userRoleService.getUserRoleByUserId(userId);
        // 从用户角色列表中提取角色代码，并转换为集合
        Set<String> roleCodeSet = userRoleList.stream().map(BUserRole::getRoleCode).collect(Collectors.toSet());
        Object[] args  = joinPoint.getArgs();
        for (Object arg : args) {
            if( arg instanceof UserMoment){
                UserMoment userMoment = (UserMoment) arg;
                String type = userMoment.getType();
                //判断 Set 集合是否包含指定的对象
                if(roleCodeSet.contains(AuthRoleConstant.ROLE_LV0)&& !"0".equals(type)){
                    throw new ConditionException("参数异常！");
                }
            }
        }

    }

}
