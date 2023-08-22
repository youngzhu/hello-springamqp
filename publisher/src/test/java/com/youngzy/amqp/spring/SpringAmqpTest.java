package com.youngzy.amqp.spring;

import lombok.SneakyThrows;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalTime;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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

    @Test
    public void testSendDirectExchange() {
        String exchange = "exchange.direct";
        String routingKey = "red";
        routingKey = "blue";
        routingKey = "yellow";

        rabbitTemplate.convertAndSend(exchange, routingKey, "hello, " + routingKey);
    }

    @Test
    public void testSendTopicExchange() {
        String exchange = "exchange.topic";

        Map<String, List<String>> map = new HashMap<>();

        map.put("china.news", Arrays.asList("中华人民共和国成立了！", "北京申奥成功了！"));
        map.put("china.taiwan.news", Arrays.asList("台湾回归了！"));
        map.put("china.weather", Arrays.asList("台风来了！", "入伏了！", "出伏了！"));
        map.put("china.shanghai.weather", Arrays.asList("太热了！还暴雨！"));
        map.put("usa.news", Arrays.asList("马斯克访华了！", "扎克伯格和马斯克干上了！", "美国换总统了！！"));

        map.forEach((key, list) -> {
            list.forEach((msg) -> {
                rabbitTemplate.convertAndSend(exchange, key, msg);
            });
        });


    }

}
