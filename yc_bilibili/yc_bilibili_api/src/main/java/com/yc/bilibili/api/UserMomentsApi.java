package com.yc.bilibili.api;

import com.yc.bilibili.api.support.UserSupport;
import com.yc.bilibili.daomin.JsonResponse;
import com.yc.bilibili.daomin.UserMoment;
import com.yc.bilibili.service.UserMomentsService;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.remoting.exception.RemotingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * 用户动态相关
 * @author
 */
@RestController
public class UserMomentsApi {
    @Autowired
    private UserMomentsService userMomentsService;

    @Autowired
    private UserSupport userSupport;


    /**
     * 用户发布动态接口
     */
    @PostMapping("/user-moments")
    public JsonResponse<String> addUserMoments(@RequestBody UserMoment userMoment) throws RemotingException, InterruptedException, MQClientException {
        Long userId = userSupport.getCurrentUserId();
        userMoment.setUserId(userId);
        userMomentsService.addUserMoments(userMoment);
        return JsonResponse.success();
    }


}
