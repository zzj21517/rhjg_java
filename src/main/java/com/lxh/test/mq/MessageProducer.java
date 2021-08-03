package com.lxh.test.mq;

import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Service;
import java.io.IOException;

@Service
public class MessageProducer {
    private Logger logger = LoggerFactory.getLogger(MessageProducer.class);
    RabbitTemplate   rabbitTemplate;
    public  MessageProducer(){
/*        AbstractApplicationContext  ctx = new ClassPathXmlApplicationContext(
                "classpath:spring/spring-rabbitmq.xml");
        rabbitTemplate = (RabbitTemplate)ctx.getBean(RabbitTemplate.class);*/
    }
    //RabbitMQ模板
    //获取RabbitTemplate
    public void sendMessage(String exchange,String routingKey, Object message) throws IOException {
      rabbitTemplate.convertAndSend(exchange,routingKey, message);
    }
}
