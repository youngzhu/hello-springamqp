package com.youngzy.amqp.spring;

import lombok.SneakyThrows;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalTime;
import java.util.concurrent.TimeUnit;

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

    @SneakyThrows
    @Test
    public void testSendMessage2WorkQueue() {
        String queueName = "queue.work";

        for (int i = 1; i <= 50; i++) {
            rabbitTemplate.convertAndSend(queueName, "第【" + i + "】次问候！！" + LocalTime.now());
            TimeUnit.MILLISECONDS.sleep(10);
        }
    }

    @Test
    public void testSendFanoutExchange() {
        String exchange = "exchange.fanout";
        String msg = "hello, everyone!";

        rabbitTemplate.convertAndSend(exchange, "", msg);
    }

}
