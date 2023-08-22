package com.youngzy.amqp.listener;

import lombok.SneakyThrows;
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
}
