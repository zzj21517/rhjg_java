package com.lxh.newrhjg.rest;

import com.alibaba.fastjson.JSONObject;
import com.lxh.newrhjg.api.IMssages;
import com.lxh.newrhjg.api.Inewcommon;
import com.lxh.newrhjg.entity.FrameMessages;
import com.lxh.newrhjg.entity.FrameMessagesHistroy;
import com.lxh.rhjg.active.api.IPeople;
import com.lxh.rhjg.entity.SMART_ERRORLOG;
import com.lxh.rhjg.verifycode.api.IVerifycode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.text.SimpleDateFormat;
import java.util.*;

@RestController
@RequestMapping("/messages")
public class MessagesController {
    @Autowired
    IMssages iMssages;
    @Autowired
    Inewcommon icommon;
    @Autowired
    IPeople iPeople;
    @Autowired
    IVerifycode iVerifycode;
    //添加消息
    @RequestMapping(value = "/addMessges", method = RequestMethod.POST)
    public String addMessges(@RequestBody String params) {
        JSONObject rJsonObject = new JSONObject();
        JSONObject jsonObject = JSONObject.parseObject(params);
        jsonObject=JSONObject.parseObject(jsonObject.getString("param"));
        String title = jsonObject.getString("title");//标题
        String content = jsonObject.getString("content");//内容
        String targetuser = jsonObject.getString("targetuser");//目标人
        String targetuserphone = jsonObject.getString("targetuserphone");//目标人手机号
        String type = jsonObject.getString("type");//消息类型
        String sendUser = jsonObject.getString("sendUser");//发送人
        String guid = UUID.randomUUID().toString();
        SimpleDateFormat sDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String datatime = sDateFormat.format(new Date());
        String sendtime = datatime;//发送时间
        FrameMessages frameMessages = new FrameMessages();
        frameMessages.setRowGuid(guid);
        frameMessages.setTitile(title);
        frameMessages.setSendTime(sendtime);
        frameMessages.setContent(content);
        frameMessages.setTargetUser(targetuser);
        frameMessages.setTargetuserphone(targetuserphone);
        frameMessages.setType(type);
        frameMessages.setSendUser(sendUser);
        try {
            int n = iMssages.insert(frameMessages);
            if (n == 1) {
                rJsonObject.put("code", "200");//插入成功
            } else {
                rJsonObject.put("code", "400");
                rJsonObject.put("error", "插入失败");
            }
        } catch (Exception e) {
            //插入报错信息
            SMART_ERRORLOG smartErrorlog = new SMART_ERRORLOG();
            smartErrorlog.setGuid(UUID.randomUUID().toString());
            smartErrorlog.setGuid(datatime);
            smartErrorlog.setErrorlog(e.getMessage());
            icommon.insertlog(smartErrorlog);
            rJsonObject.put("code", "400");//插入失败
            rJsonObject.put("error",e.getMessage());//插入失败
        }
        return rJsonObject.toJSONString();
    }
    //更新消息
    @RequestMapping(value = "/upMessges", method = RequestMethod.POST)
    public String upMessges(@RequestBody String params) {
        JSONObject rJsonObject = new JSONObject();
        JSONObject jsonObject = JSONObject.parseObject(params);
        jsonObject=JSONObject.parseObject(jsonObject.getString("param"));
        String rowguid = jsonObject.getString("rowguid");//消息标识
        String title = jsonObject.getString("title");//标题
        String content = jsonObject.getString("content");//内容
        String targetuser = jsonObject.getString("targetuser");//目标人
        String targetuserphone = jsonObject.getString("targetuserphone");//目标人手机号
        String type = jsonObject.getString("type");//消息类型
        String sendUser = jsonObject.getString("sendUser");//发送人
        SimpleDateFormat sDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String datatime = sDateFormat.format(new Date());
        String sendtime = datatime;//发送时间
        FrameMessages frameMessages = new FrameMessages();
        frameMessages.setRowGuid(rowguid);
        frameMessages.setTitile(title);
        frameMessages.setSendTime(sendtime);
        frameMessages.setContent(content);
        frameMessages.setTargetUser(targetuser);
        frameMessages.setTargetuserphone(targetuserphone);
        frameMessages.setType(type);
        frameMessages.setSendUser(sendUser);
        try {
            int n = iMssages.update(frameMessages);
            if (n == 1) {
                rJsonObject.put("code", "200");//插入成功
            } else {
                rJsonObject.put("code", "400");
                rJsonObject.put("error","插入失败");//插入失败
            }
        } catch (Exception e) {
            //插入报错信息
            SMART_ERRORLOG smartErrorlog = new SMART_ERRORLOG();
            smartErrorlog.setGuid(UUID.randomUUID().toString());
            smartErrorlog.setGuid(datatime);
            smartErrorlog.setErrorlog(e.getMessage());
            icommon.insertlog(smartErrorlog);
            rJsonObject.put("code", "400");//插入失败
            rJsonObject.put("error",e.getMessage());//插入失败
        }
        return rJsonObject.toJSONString();
    }
    //删除消息
    @RequestMapping(value = "/delMessges", method = RequestMethod.POST)
    public String delMessges(@RequestBody String params) {
        JSONObject rJsonObject = new JSONObject();
        JSONObject jsonObject = JSONObject.parseObject(params);
        jsonObject=JSONObject.parseObject(jsonObject.getString("param"));
        String rowguid = jsonObject.getString("rowguid");//消息标识
        SimpleDateFormat sDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String datatime = sDateFormat.format(new Date());
        String sendtime = datatime;//发送时间
        FrameMessagesHistroy messagesHistroy=new FrameMessagesHistroy();
        List<HashMap<String, Object>> list=icommon.findlist("frame_messages","*",null," and rowguid='"+rowguid+"'","",0,1);
       if(list.size()<0) {
           rJsonObject.put("code", "400");
           rJsonObject.put("error","查询失败");//插入失败
       }else{
             HashMap<String,Object>  map=list.get(0);
             messagesHistroy.setRow_id(Integer.parseInt(map.get("row_id").toString()));
             messagesHistroy.setRowGuid(map.get("rowGuid").toString());
             messagesHistroy.setTitile(map.get("titile").toString());
             messagesHistroy.setContent(map.get("content").toString());
             messagesHistroy.setTargetUser(map.get("targetUser").toString());
             messagesHistroy.setType(map.get("type").toString());
             messagesHistroy.setSendTime(map.get("sendTime").toString());
             messagesHistroy.setRemoveTime(sendtime);
             messagesHistroy.setTargetuserphone(map.get("targetuserphone").toString());
             messagesHistroy.setSendUser(map.get("sendUser").toString());
       }
        try {
            int n = iMssages.removehistroy(messagesHistroy);
            if (n == 1) {
                rJsonObject.put("code", "200");//插入成功
            } else {
                rJsonObject.put("code", "400");
            }
        } catch (Exception e) {
            //插入报错信息
            SMART_ERRORLOG smartErrorlog = new SMART_ERRORLOG();
            smartErrorlog.setGuid(UUID.randomUUID().toString());
            smartErrorlog.setGuid(datatime);
            smartErrorlog.setErrorlog(e.getMessage());
            icommon.insertlog(smartErrorlog);
            rJsonObject.put("code", "400");//插入失败
            rJsonObject.put("error",e.getMessage());//插入失败
        }
        return rJsonObject.toJSONString();
    }
    //获取消息
    @RequestMapping(value = "/getMessges", method = RequestMethod.POST)
    public String getMessges(@RequestBody String params) {
        JSONObject rJsonObject = new JSONObject();
        JSONObject jsonObject = JSONObject.parseObject(params);
        jsonObject=JSONObject.parseObject(jsonObject.getString("param"));
        String targetUser = jsonObject.getString("targetUser");//消息标识
        int pagesize= Integer.parseInt(jsonObject.getString("pagesize"));//每页大小
        int pagenum= Integer.parseInt(jsonObject.getString("pagenum"));//当前页数
        SimpleDateFormat sDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String datatime = sDateFormat.format(new Date());
        Map<String,Object> map=new HashMap<>();
        map.put("targetUser=",targetUser);
        try {
            List<HashMap<String, Object>> list=icommon.findlist("frame_messages","*",map,"","",pagenum,pagesize);
            int count= icommon.findlist("frame_messages",map,"");
            rJsonObject.put("code", "200");
            rJsonObject.put("count",count);
            rJsonObject.put("result",list);
        } catch (Exception e) {
            //插入报错信息
            SMART_ERRORLOG smartErrorlog = new SMART_ERRORLOG();
            smartErrorlog.setGuid(UUID.randomUUID().toString());
            smartErrorlog.setGuid(datatime);
            smartErrorlog.setErrorlog(e.getMessage());
            icommon.insertlog(smartErrorlog);
            rJsonObject.put("code", "400");//插入失败
            rJsonObject.put("error",e.getMessage());//插入失败
        }
        return rJsonObject.toJSONString();
    }
}
