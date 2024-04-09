package com.yc.bilibili.service;


import com.alibaba.fastjson.JSONObject;
import com.yc.bilibili.dao.UserMomentsDao;
import com.yc.bilibili.daomin.UserMoment;
import com.yc.bilibili.daomin.constant.UserMomentsConstant;
import com.yc.bilibili.service.util.RocketMQUtil;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.exception.RemotingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.util.Date;

@Service
public class UserMomentsService {

    @Autowired
    private UserMomentsDao userMomentsDao;

    @Autowired
    private ApplicationContext applicationContext;

    public void addUserMoments(UserMoment userMoment) throws RemotingException, InterruptedException, MQClientException {
        userMoment.setCreateTime(new Date());
        userMomentsDao.addUserMoments(userMoment);
        //获取mq 生产者和消费者
        DefaultMQProducer producer = (DefaultMQProducer)applicationContext.getBean("momentsProducer");
        Message msg = new Message(UserMomentsConstant.TOPIC_MOMENTS, JSONObject.toJSONString(userMoment).getBytes(StandardCharsets.UTF_8));
        RocketMQUtil.asynSendMsg(producer,msg);
    }
}
