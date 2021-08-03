package com.lxh.test.MqHandle;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageListener;
import java.io.UnsupportedEncodingException;

public class MqClientHandle implements MessageListener {
    @Override
    public void onMessage(Message msg) {
        try {
            System.out.print("-------------------"+new String(msg.getBody(),"UTF-8"));
        } catch (UnsupportedEncodingException e) {
            // TODO 自动生成的 catch 块
            e.printStackTrace();
        }
    }

}
