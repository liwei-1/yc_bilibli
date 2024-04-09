package com.yc.bilibili.service.config;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.support.spring.JSONPResponseBodyAdvice;
import com.yc.bilibili.daomin.UserFollowing;
import com.yc.bilibili.daomin.UserMoment;
import com.yc.bilibili.daomin.constant.UserMomentsConstant;
import com.yc.bilibili.service.UserFollowingService;
import io.netty.util.internal.StringUtil;
import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListener;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.common.message.MessageExt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.ArrayList;
import java.util.List;

/**
 * 注解@Configuration  标识该类是一个配置文件
 *
 */
@Configuration
public class RocketMQConfig {

    @Value("${rocketmq.name,server.address}")
    private String nameServeAddr;

    @Autowired
    private RedisTemplate<String,String> redisTemplate;


    @Autowired
    private UserFollowingService userFollowingService;


    /**
     * 生产者
     * @return producer
     * @throws MQClientException
     */
    @Bean("momentsProducer")
    public DefaultMQProducer momentsProducer() throws MQClientException {
        DefaultMQProducer producer = new DefaultMQProducer(UserMomentsConstant.GROUP_MOMENTS);
        producer.setNamesrvAddr(nameServeAddr);
        producer.start();
        return producer;
    }

    /**
     * 消费者
     * @return consumer
     * @throws MQClientException
     *
     */
    @Bean("momentsConsumer")
    public DefaultMQPushConsumer momentsConsumer() throws MQClientException {
        DefaultMQPushConsumer consumer = new DefaultMQPushConsumer(UserMomentsConstant.GROUP_MOMENTS);
        consumer.setNamesrvAddr(nameServeAddr);
        //订阅内容
        consumer.subscribe(UserMomentsConstant.TOPIC_MOMENTS,"*");
        // 添加监听器，生产者把消息推给MQ   MQ推送给 消费者    消费者用监听器抓起消息 做xxx处理
        //注册消息监听方法
        consumer.registerMessageListener(new MessageListenerConcurrently() {
            @Override
            public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> msgs, ConsumeConcurrentlyContext consumeConcurrentlyContext) {
                //用户动态实体发送到mq  获取消息处理
                Message msg = msgs.get(0);
                if(msg == null ){
                    return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
                }
                String bodyStr = new String(msg.getBody());
                UserMoment userMoment = JSONObject.toJavaObject(JSONObject.parseObject(bodyStr),UserMoment.class);
                Long userId = userMoment.getUserId();
                //获取用户的粉丝
                List<UserFollowing> fanList = userFollowingService.getUserFans(userId);
                for (UserFollowing fan : fanList) {
                    //消息放到redis  那个用户订阅的内容
                    String key = "subscribed-"+ fan.getUserId();
                    String subscribedListStr = redisTemplate.opsForValue().get(key);
                    List<UserMoment> subscribedList ;
                    if(StringUtil.isNullOrEmpty(subscribedListStr)){
                        subscribedList = new ArrayList<>();
                    }else {
                        // 字符串转换成实体
                        subscribedList = JSONArray.parseArray(subscribedListStr,UserMoment.class);
                    }
                    subscribedList.add(userMoment);
                    redisTemplate.opsForValue().set(key,JSONObject.toJSONString(subscribedList));
                }
                return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
            }
        });
        consumer.start();
        return consumer;
    }


}
