package com.lxh.rest;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.lxh.encode.RSAUtil;
import com.lxh.test.api.IfindSomething;
import com.lxh.test.common.PropertiesUtil;
import com.lxh.test.mq.MessageProducer;
import com.lxh.test.mq.MqUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;


@RestController
@RequestMapping("/testyou")
public class TestController {
    @Autowired
    private IfindSomething ifindSomething;
    @RequestMapping(value = "/login", method=RequestMethod.POST)
    public String login(@RequestBody String params) throws IOException {
        PropertiesUtil.loadFile("encode.properties");
        String privateKey = PropertiesUtil.getPropertyValue("privateKey");
        String key = PropertiesUtil.getPropertyValue("key");
        String content = PropertiesUtil.getPropertyValue("content");
       JSONObject jsonObject= JSONObject.parseObject(params);
       String  timestamp=jsonObject.getString("timestamp");
        try {
            String value=RSAUtil.publicDecrypt(content,privateKey);
            if((timestamp+key).equals(value)){
                System.out.println("加密认证成功");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        MessageProducer messageProducer=new MessageProducer();
        //查询数据库信息
         String returnvalue=ifindSomething.getSomething();
      //推送mq消息
        messageProducer.sendMessage("lxhExchange","lxh_queue",returnvalue);
      return returnvalue;
    }
}
