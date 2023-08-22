# Spring AMQP

## v0.1.0
入门案例，消息的发送与接收

## v0.2.0
WorkQueue

一个队列有多个监听（消费者），理论上可以增加吞吐量。
消费者A：每秒处理50条（处理一条休眠20ms）
消费者B：每秒处理10条

按照设想，队列中有50条数据，两个监听者，1s内应该能处理完。

但实际上并不能。因为消息被平均分配了，而消费者B处理能力较弱（处理25条数据大概需要2.5s）。
加上这样的配置`spring.rabbitmq.listener.simple.prefetch`，就可以实现能者多劳

### 总结
WorkQueue模型：多个消费者监听一个队列；同一条消息只会被某个消费者处理一次

## v0.3.0
发布/订阅模型

引入了交换机（exchange），由交换机决定如何将消息投递到队列

注意：交换机只负责消息路由，不存储。如果路由（投递）失败则消息丢失

## v0.3.1-fanout
Fanout Exchange，广播模式，将接收到的消息投递到每一个与其绑定的队列

通过Bean注入的方式（FanoutConfig.java）来声明交换机、队列和绑定

## v0.3.2-direct
Direct Exchange，将接收到的消息按照规则（rule）分发到对应的队列，因此也称为路由（routes）模式

通过注解来声明交换机、队列和绑定

## v0.3.3-topic
Topic Exchange，与 Direct Exchange类似，区别在于routingKey必须是多个单词，以 `.` 分割

```text
#: 代指0个或多个单词
*: 代指一个单词
```

## v0.4.0
消息转换器

注入 MessageConverter 
```java
@Bean
public MessageConverter messageConverter(){
    return new Jackson2JsonMessageConverter();
}
```