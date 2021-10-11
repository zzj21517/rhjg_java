package com.lxh.rhjg.rest;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.lxh.rhjg.active.api.ILuck;
import com.lxh.rhjg.active.api.IProject;
import com.lxh.rhjg.active.api.SMART_LUCKY;
import com.lxh.rhjg.active.api.SMART_PROJECT;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.UUID;


@RestController
@RequestMapping("/ActiveController")
public class ActiveController {
    @Autowired
    private ILuck iLuck;
    @Autowired
    private IProject iProject;
    //添加红包记录
    @RequestMapping(value = "/AddLuckyBag", method=RequestMethod.POST)
    public String AddLuckyBag(@RequestBody String params) throws IOException {
        JSONObject rJsonObject=new JSONObject();
       JSONObject jsonObject= JSONObject.parseObject(params);
       String  uid=jsonObject.getString("uid");
       String  pid=jsonObject.getString("pid");
       String  lucky=jsonObject.getString("lucky");
       String guid= UUID.randomUUID().toString();
        SimpleDateFormat sDateFormat=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String datatime=sDateFormat.format(new Date());
        SMART_LUCKY luckBag=null;
        //查找是否存在
        luckBag=iLuck.findLuckbag("PROJECT_NUM",pid);
         if(luckBag==null){
             luckBag=new SMART_LUCKY();
             luckBag.setGuid(guid);
             luckBag.setUSER_ID(uid);
             luckBag.setLUCKY_AMT(lucky);
             luckBag.setDATA_TIME(datatime);
             luckBag.setSTATUS("00");
             luckBag.setPROJECT_NUM(pid);
            iLuck.insetLuckbag(luckBag);
            SMART_PROJECT project=iProject.findProject("PROJECT_NUM='"+pid+"'").get(0);
             if(project!=null){
                 SMART_LUCKY smartLucky=new SMART_LUCKY();
                 smartLucky.setGuid(UUID.randomUUID().toString());
                 smartLucky.setUSER_ID(project.getTUIJIAN());
                 smartLucky.setPROJECT_NUM(pid);
                 smartLucky.setLUCKY_AMT(lucky);
                 smartLucky.setDATA_TIME(datatime);
                 smartLucky.setSTATUS("00");
                 iLuck.insetLuckbag(smartLucky);
             }
             rJsonObject.put("code","200");
         }
         else{
             //amt  code
             rJsonObject.put("code","100");
             rJsonObject.put("amt",luckBag.getLUCKY_AMT());

         }

        try {
        } catch (Exception e) {
            e.printStackTrace();
        }
        return rJsonObject.toJSONString();
    }
    //获取红包记录
    @RequestMapping(value = "/GetLuckyBag", method=RequestMethod.POST)
    public String GetLuckyBag(@RequestBody String params) throws IOException {
        JSONObject rJsonObject=new JSONObject();
        JSONObject jsonObject= JSONObject.parseObject(params);
        String  uid=jsonObject.getString("uid");
        String  start=jsonObject.getString("start");
        String  count=jsonObject.getString("count");
        try {
        int length=Integer.parseInt(start)+Integer.parseInt(count);
        //查找是否存在
        List<SMART_LUCKY> listluckBag=iLuck.GetLuckyBag(uid,Integer.parseInt(start),length);
        rJsonObject.put("code","200");
        rJsonObject.put("count",count);
        rJsonObject.put("start",start);
        String str = JSON.toJSONString(listluckBag); // List转json
        rJsonObject.put("subjects",str);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return rJsonObject.toJSONString();
    }
    //获取红包总金额
    @RequestMapping(value = "/GetLuckyBagTotal", method=RequestMethod.POST)
    public String GetLuckyBagTotal(@RequestBody String params) throws IOException {
        String returnvalue="";
        JSONObject rJsonObject=new JSONObject();
        JSONObject jsonObject= JSONObject.parseObject(params);
        String  uid=jsonObject.getString("uid");
        try {
           JSONObject object= iLuck.GetLuckyBagTotal(uid);
            //查找是否存在
            rJsonObject.put("code","200");
            rJsonObject.put("results",object);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return rJsonObject.toJSONString();
    }

    //分享得红包活动,添加分享的记录
    @RequestMapping(value = "/AddShareRecord", method=RequestMethod.POST)
    public String AddShareRecord(@RequestBody String params) throws IOException {
        String returnvalue="";
        JSONObject rJsonObject=new JSONObject();
        JSONObject jsonObject= JSONObject.parseObject(params);
        String  uid=jsonObject.getString("uid");
        String  name=jsonObject.getString("name");
        String  image=jsonObject.getString("image");
        String  code=jsonObject.getString("code");
        try {
            String guid= UUID.randomUUID().toString();
            SimpleDateFormat sDateFormat=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String datatime=sDateFormat.format(new Date());
            JSONObject object=iLuck.GetSmartShare(code,uid);
            if(object.size()>0){
                rJsonObject.put("code","100");
            }else {
              JSONObject jsonshare=new JSONObject();
                jsonshare.put("guid",guid);
                jsonshare.put("uid",uid);
                jsonshare.put("openId",code);
                jsonshare.put("name",name);
                jsonshare.put("image",image);
                jsonshare.put("datatime",datatime);
                iLuck.insertSmartShare(jsonshare);
                rJsonObject.put("code","200");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return rJsonObject.toJSONString();
    }
    //获取分享红包的好友信息
    @RequestMapping(value = "/GetShareRecord", method=RequestMethod.POST)
    public String GetShareRecord(@RequestBody String params) throws IOException {
        String returnvalue="";
        JSONObject rJsonObject=new JSONObject();
        JSONObject jsonObject= JSONObject.parseObject(params);
        String  uid=jsonObject.getString("uid");
        try {
            JSONObject object=iLuck.GetSmartSharebyUid(uid);
            if(object.size()>0){
                rJsonObject.put("code","200");
                rJsonObject.put("results",object);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return rJsonObject.toJSONString();
    }
}

