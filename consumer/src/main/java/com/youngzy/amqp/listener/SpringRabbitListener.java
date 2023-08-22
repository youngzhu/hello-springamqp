package com.youngzy.amqp.listener;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class SpringRabbitListener {

     @RabbitListener(queues = "queue.simple")
     public void listenSimpleQueue(String msg) {
         System.out.println("消费者接收到的消息：【" + msg + "】");
     }
}
