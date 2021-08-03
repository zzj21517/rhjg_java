package com.lxh.rhjg.rest;


import com.alibaba.fastjson.JSONObject;
import com.lxh.rhjg.active.api.ILuck;
import com.lxh.rhjg.active.api.IPeople;
import com.lxh.rhjg.active.api.Icommon;
import com.lxh.rhjg.active.api.SMART_PEOPLE_EXT;
import com.lxh.rhjg.common.util.Common;
import com.lxh.rhjg.common.util.HttpClient;
import com.lxh.rhjg.entity.SMART_TRADE;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

@RestController
@RequestMapping("/RedBag")
public class RedBag {
    @Autowired
    Icommon icommon;
    @Autowired
    IPeople iPeople;
    @Autowired
    ILuck iLuck;
    /*
     *获取红包账户
     */
    @RequestMapping(value = "/GetRedBagAccount", method = RequestMethod.POST)
    public String GetRedBagAccount(@RequestBody String params) throws IOException {
        JSONObject rJsonObject = new JSONObject();
        JSONObject jsonObject = JSONObject.parseObject(params);
        jsonObject=JSONObject.parseObject(jsonObject.getString("param"));
        String uid = jsonObject.get("uid").toString();
        String key = jsonObject.get("key").toString();
        Common common=new Common();
        String sid=common.GetSSIDStr("REDBAG");

        if(key.equals(sid)){
           SMART_PEOPLE_EXT smartPeopleExt=iPeople.GetSmartPeopleExt("USER_ID",uid);
             if(smartPeopleExt!=null){
                 rJsonObject.put("subjects",smartPeopleExt);
                 rJsonObject.put("code","200");
             }else {
                 rJsonObject.put("subjects",smartPeopleExt);
                 rJsonObject.put("code","400");
             }


        }else{
            rJsonObject.put("code","400");
        }
        return rJsonObject.toJSONString();
    }
    /*
     *增加红包的账户
     */
    @RequestMapping(value = "/AddRegBagAccount", method = RequestMethod.POST)
    public String AddRegBagAccount(@RequestBody String params) throws IOException {
        JSONObject rJsonObject = new JSONObject();
        JSONObject jsonObject = JSONObject.parseObject(params);
        jsonObject=JSONObject.parseObject(jsonObject.getString("param"));
        String uid = jsonObject.get("uid").toString();
        String key = jsonObject.get("key").toString();
        String money = jsonObject.get("money").toString();
        Common common=new Common();
        String sid=common.GetSSIDStr("REDBAG");
        if(key.equals(sid)){
            //增加用户的积分
            SMART_PEOPLE_EXT smartPeopleExt=iPeople.GetSmartPeopleExt("USER_ID",uid);
            double redbag_account =Double.valueOf(smartPeopleExt.getREDBAG_ACCOUNT()) ;

            double total = redbag_account + Double.valueOf(money);
            icommon.updateCommon("SMART_PEOPLE_EXT","REDBAG_ACCOUNT='"+total+"'"," and USER_ID='"+uid+"'");
            //插入日志
            SimpleDateFormat sDateFormat=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String datatime=sDateFormat.format(new Date());
           // $tradeNum = $comm->GetRuleStr("TRADE_NUM");
            String tradeNum=common.GetRuleStr("TRADE_NUM");
            SMART_TRADE smartTrade=new SMART_TRADE();
            smartTrade.setGUID(UUID.randomUUID().toString());
            smartTrade.setTRADE_NUM(tradeNum);
            smartTrade.setTRADE_AMT(money);
            smartTrade.setCONTENT("领取红包活动");
            smartTrade.setSTATUS("60");
            smartTrade.setUSER_ID(uid);
            smartTrade.setITEM_TYPE("08");
            smartTrade.setDATATIME(datatime);
            smartTrade.setPROJECT_NUM(uid);
            try {
                iLuck.insettrade(smartTrade);
                rJsonObject.put("code","200");
            }catch (Exception e){
                rJsonObject.put("code","400");
            }
        }else{
            rJsonObject.put("code","400");
        }
        return rJsonObject.toJSONString();
    }
}
