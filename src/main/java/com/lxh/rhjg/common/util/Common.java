package com.lxh.rhjg.common.util;

import com.lxh.rhjg.active.api.IPeople;
import com.lxh.rhjg.active.api.Icommon;
import com.lxh.rhjg.active.api.SMART_PEOPLE_EXT;
import com.lxh.rhjg.active.api.SMART_RULE;
import com.lxh.rhjg.entity.SMART_POINT_DETAIL;
import com.lxh.rhjg.entity.SMART_SSID;
import com.lxh.test.common.PropertiesUtil;
import org.springframework.beans.factory.annotation.Autowired;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class Common {
    @Autowired
    Icommon icommon;
    @Autowired
    IPeople iPeople;
    /*
     * 组织规则编码
     */
    public String  GetRuleStr(String ruleid){
        String returnvalue="";
       SMART_RULE smartRule=icommon.findSmartrule("rule_id",ruleid);
       if(smartRule!=null){
               String id=smartRule.getRuleValue();
               if(id.equals("")||id==null){
                   id="1000000";
               }
               int num=Integer.parseInt(id)+1;
           smartRule.setRuleValue(String.valueOf(num));
           //更新最大编号
           icommon.upSmartrule(smartRule);
           returnvalue=smartRule.getPrefix()+String.valueOf(num);
       }
       return  returnvalue;
    }
    /*
     * 获取加密菜单项
     */
    public String  GetSSIDStr(String code){
        String returnvalue="";
        SMART_SSID smartSsid=icommon.findSmartssid("URL_CODE",code);
        if(smartSsid!=null){
            returnvalue=smartSsid.getSSID_VALUE();
        }
        return  returnvalue;
    }
    public  String GetOpenid(String code){
        PropertiesUtil.loadFile("wx.properties");
        String appid = PropertiesUtil.getPropertyValue("appid");
        String secret = PropertiesUtil.getPropertyValue("secret");
        String openid="";
        String tokenUrl = "https://api.weixin.qq.com/sns/jscode2session?appid=" + appid + "&secret=" + secret + "&js_code=" + code + "&grant_type=authorization_code";
        String oauthResponseText = HttpClient.doPost(tokenUrl,"");
        com.alibaba.fastjson.JSONObject jo = com.alibaba.fastjson.JSONObject.parseObject(oauthResponseText);
        Map<String, String> resultMap = new HashMap<String, String>();
        if ("".equals(jo.get("openid").toString())) {
            openid=jo.get("openid").toString();
        }
        return openid;
    }
    /*
     * 用户增加积分函数
     * param:uid 用户ID
     * vtype:积分类型
     * key:传的密钥
     * poin:积分
     */
    public boolean AddUserPoint(String uid,String vtype,String key,String point){
     String sid=icommon.findSmartrule("rule_id","JIFEN").getRuleValue();
       if(sid.equals(key)){
           double jifen_point=0;
           double total=0;
           //增加用户的积分
         SMART_PEOPLE_EXT smartPeopleExt=iPeople.GetSmartPeopleExt("USER_ID",uid);
         if(smartPeopleExt!=null){
             jifen_point=Double.valueOf(smartPeopleExt.getJIFEN_POINT());
             total =jifen_point+ Double.valueOf(point);
             try{
             icommon.updateCommon("SMART_PEOPLE_EXT","JIFEN_POINT="+total,"USER_ID='"+uid+"'");
             //插入日志
             SMART_POINT_DETAIL smartPointDetail=new SMART_POINT_DETAIL();
             smartPointDetail.setGUID(UUID.randomUUID().toString());
             SimpleDateFormat sDateFormat=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
             String datatime=sDateFormat.format(new Date());
             smartPointDetail.setDATATIME(datatime);
             smartPointDetail.setJIFEN_POINT(point);
             smartPointDetail.setJIFEN_TYPE(vtype);
             smartPointDetail.setUSER_ID(uid);
              iPeople.insertPointDetail(smartPointDetail);
              return true;
             }catch (Exception e){
                   return false;
             }
         }else{
             return  false;
         }
       }else {
           return false;
       }
    }

    }
