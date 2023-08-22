package com.youngzy.amqp.spring;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SpringAmqpTest {
    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Test
    public void testSendMessage2SimpleQueue() {
        // 队列是预先创建好的
        // 如果不存在，测试不会报错，但也不会创建队列，更不会发送消息
        String queueName = "queue.simple";
        String message = "Hello, Spring AMQP!";
        rabbitTemplate.convertAndSend(queueName, message);
    }

}
