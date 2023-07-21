package com.yc.bilibili.api.support;

import com.yc.bilibili.daomin.exception.ConditionException;
import com.yc.bilibili.service.util.TokenUtil;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

/**
 * 通用方法api
 */
@Component
public class UserSupport {
    public Long getCurrentUserId(){
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        String token = requestAttributes.getRequest().getHeader("token");
        Long userId = TokenUtil.verifyToken(token);

        if(userId < 0){
            throw new ConditionException("非法用户！");
        }
        return userId;
    }

}
