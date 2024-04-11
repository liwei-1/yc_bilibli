package com.yc.bilibili.api;

import com.yc.bilibili.api.support.UserSupport;
import com.yc.bilibili.daomin.JsonResponse;
import com.yc.bilibili.daomin.auth.UserAuthorities;
import com.yc.bilibili.service.UserAuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class UserAuthApi {

    @Autowired
    private UserSupport userSupport;

    @Autowired
    private UserAuthService userAuthService;

    @GetMapping("/getUserAuthorities")
    public JsonResponse<UserAuthorities>  getUserAuthorities(){
        Long userId = userSupport.getCurrentUserId();
        UserAuthorities userAuthoritiesList =  userAuthService.getUserAuthorities(userId);
        return new JsonResponse<>(userAuthoritiesList);
    }

}
