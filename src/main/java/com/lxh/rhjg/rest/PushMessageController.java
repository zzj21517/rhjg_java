package com.lxh.rhjg.rest;

import com.alibaba.fastjson.JSONObject;
import com.lxh.rhjg.common.util.HttpClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequestMapping("/PushMessageController")
public class PushMessageController {
    private static String appid ="wx9efcf692553fc391";
    private static String appsecret = "cd25400ad9145f2ab187ac19ebd0f28a";
    @RequestMapping(value = "/getAuthorizeUrl", method= RequestMethod.POST)
    public String getAuthorizeUrl(@RequestBody String params) throws IOException {
        JSONObject rJsonObject=new JSONObject();
        JSONObject jsonObject= JSONObject.parseObject(params);
         String url_link=  jsonObject.get("url").toString();
        return "https://open.weixin.qq.com/connect/oauth2/authorize?appid="+appid+"&redirect_uri="+url_link+"&response_type=code&scope=snsapi_base&state=1#wechat_redirect";
    }
    @RequestMapping(value = "/getToken", method= RequestMethod.POST)
    public String getToken(@RequestBody String params) throws IOException {
        JSONObject rJsonObject=new JSONObject();
        JSONObject jsonObject= JSONObject.parseObject(params);

        String urla = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid="+appid+"&secret="+appsecret;
        String  outputa=HttpClient.doGet(urla);
        JSONObject object= JSONObject.parseObject(outputa);
        return object.get("access_token").toString();
    }



}
