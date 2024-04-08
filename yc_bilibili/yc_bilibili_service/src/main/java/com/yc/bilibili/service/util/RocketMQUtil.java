package com.yc.bilibili.service.util;

import org.apache.rocketmq.client.exception.MQBrokerException;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendCallback;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.CountDownLatch2;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.exception.RemotingException;

import java.util.concurrent.TimeUnit;

/**
 * rockermq工具类
 *
 */
public class RocketMQUtil {
    public static void syncSendMsg(DefaultMQProducer producer, Message msg) throws MQBrokerException, RemotingException, InterruptedException, MQClientException {
        SendResult result = producer.send(msg);
        System.out.println(result);
    }
    public static void asynSendMsg(DefaultMQProducer producer, Message msg) throws RemotingException, InterruptedException, MQClientException {
        int messageCount = 2;
        CountDownLatch2 countDownLatch2 = new CountDownLatch2(messageCount);
        for (int i = 0; i < messageCount; i++) {
            producer.send(msg, new SendCallback() {
                //发送成功
                @Override
                public void onSuccess(SendResult sendResult) {
                    countDownLatch2.countDown();
                    System.out.println(sendResult.getMsgId());
                }
                //发送失败
                @Override
                public void onException(Throwable throwable) {
                    countDownLatch2.countDown();
                    System.out.println("发生消息异常了"+ throwable);
                    throwable.printStackTrace();
                }
            });
            //计时器停留五秒钟
            countDownLatch2.await(5, TimeUnit.SECONDS);
        }

    }
}
