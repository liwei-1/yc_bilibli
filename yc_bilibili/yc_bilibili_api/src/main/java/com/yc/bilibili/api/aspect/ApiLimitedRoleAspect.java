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
 * 接口跟角色的控制
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

    /**
     * @Pointcut 注解定义了一个名为 check 的切点。
     * 这个切点匹配所有被 com.yc.bilibili.daomin.annotation.ApiLimitedRole 注解标记的方法。
     * 这个方法体是空的，因为它只是一个切点的定义，并不执行任何操作。其存在只是为了让 @Pointcut 注解能够附加到一个方法上。
     * check 切点可以在其他的通知（advice）中使用，比如之前提到的 @Before
     */
    @Pointcut("@annotation(com.yc.bilibili.daomin.annotation.ApiLimitedRole)")
    public void check(){
    }

    /**
     * doBefore方法是一个前置通知，它会在所有被 com.yc.bilibili.daomin.annotation.ApiLimitedRole 注解标记的方法执行之前执行。
     * 由于我们使用了 check() 切点，所以 doBefore 方法会针对所有这样的方法被调用。
     * @param joinPoin  JoinPoint joinPoint：这是当前连接点的对象，代表被拦截的方法。你可以通过它获取到很多关于方法执行的信息，比如方法名、参数等。
     * @param apiLimitedRole
     */
    @Before("check() && @annotation(apiLimitedRole)")
    public void doBefore(JoinPoint joinPoin, ApiLimitedRole apiLimitedRole){
        Long userId = userSupport.getCurrentUserId();
        List<BUserRole> userRoleList =  userRoleService.getUserRoleByUserId(userId);
        // 从注解中获取被限制的角色列表
        String[] limitedRoleCodeList = apiLimitedRole.limitedRoleCodeList();
        //将角色列表转换为集合 set过滤去重
        Set<String> limitedRoleCodeSet = Arrays.stream(limitedRoleCodeList).collect(Collectors.toSet());
        // 从用户角色列表中提取角色代码，并转换为集合
        Set<String> roleCodeSet = userRoleList.stream().map(BUserRole::getRoleCode).collect(Collectors.toSet());
        // 保留 roleCodeSet 中与 limitedRoleCodeSet 相交的部分，即用户具有的、同时也在被限制的角色代码列表中的角色。
        roleCodeSet.retainAll(limitedRoleCodeSet);
        if (!roleCodeSet.isEmpty()){
            // 如果用户具有任何一个被限制的角色，则抛出异常，表示权限不足
            throw new ConditionException("权限不足!");
        }

    }
}
