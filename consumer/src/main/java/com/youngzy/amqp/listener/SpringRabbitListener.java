package com.youngzy.amqp.listener;

import lombok.SneakyThrows;
import org.springframework.amqp.core.ExchangeTypes;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.time.LocalTime;
import java.util.concurrent.TimeUnit;

@Component
public class SpringRabbitListener {

     @RabbitListener(queues = "queue.simple")
     public void listenSimpleQueue(String msg) {
         System.out.println("消费者接收到的消息：【" + msg + "】");
     }

    @SneakyThrows
    @RabbitListener(queues = "queue.work")
    public void listenWorkQueue1(String msg) {
        System.out.println("消费者【1】接收到的消息：【" + msg + "】" + LocalTime.now());
        TimeUnit.MILLISECONDS.sleep(20);
    }

    @SneakyThrows
    @RabbitListener(queues = "queue.work")
    public void listenWorkQueue2(String msg) {
         // 为了明显区分
        System.err.println("====消费者【2】接收到的消息：【" + msg + "】" + LocalTime.now());
//        TimeUnit.MILLISECONDS.sleep(200);
        Thread.sleep(100);
    }

    @RabbitListener(queues = "queue.fanout1")
    public void listenFanoutQueue1(String msg) {
        System.out.println("+++接收到queue.fanout1的消息：【" + msg + "】");
    }

    @RabbitListener(queues = "queue.fanout2")
    public void listenFanoutQueue2(String msg) {
        System.out.println("---接收到queue.fanout2的消息：【" + msg + "】");
    }

    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(name = "queue.direct1"),
            exchange = @Exchange(name = "exchange.direct", type = ExchangeTypes.DIRECT),
            key = {"red", "blue"}
    ))
    public void listenDirectQueue1(String msg) {
        System.out.println("+++接收到queue.direct1的消息：【" + msg + "】");
    }

    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(name = "queue.direct2"),
            exchange = @Exchange(name = "exchange.direct", type = ExchangeTypes.DIRECT),
            key = {"yellow", "blue"}
    ))
    public void listenDirectQueue2(String msg) {
        System.out.println("---接收到queue.direct2的消息：【" + msg + "】");
    }

    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(name = "queue.china"),
            exchange = @Exchange(name = "exchange.topic", type = ExchangeTypes.TOPIC),
            key = "china.#"
    ))
    public void listenChinaQueue(String msg) {
        System.out.println("+++接收到China的消息：【" + msg + "】");
    }

    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(name = "queue.news"),
            exchange = @Exchange(name = "exchange.topic", type = ExchangeTypes.TOPIC),
            key = "#.news"
    ))
    public void listenNewsQueue(String msg) {
        System.out.println("---接收到News的消息：【" + msg + "】");
    }
}
