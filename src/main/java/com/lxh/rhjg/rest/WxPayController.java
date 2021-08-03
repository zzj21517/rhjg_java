package com.lxh.rhjg.rest;


import com.alibaba.fastjson.JSONObject;
import com.lxh.rhjg.active.api.Icommon;
import com.lxh.rhjg.active.api.SMART_RULE;
import com.lxh.rhjg.circle.api.ICircle;
import com.lxh.rhjg.common.util.Common;
import com.lxh.rhjg.common.util.MD5Utils;
import com.lxh.rhjg.entity.SMART_TRADE;
import com.lxh.rhjg.trade.api.ITrade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;

@RestController
@RequestMapping("/WxPayController")
public class WxPayController {
    @Autowired
    ITrade iTrade;
    @Autowired
    ICircle iCircle;
    @Autowired
    Icommon icommon;
    /*
     * //请求下单
     */
    @RequestMapping(value = "/PreOrder", method = RequestMethod.POST)
    public String PreOrder(@RequestBody String params) throws IOException {
        //获取参数
        JSONObject rJsonObject = new JSONObject();
        JSONObject jsonObject = JSONObject.parseObject(params);
        jsonObject=JSONObject.parseObject(jsonObject.getString("param"));
        String guid = UUID.randomUUID().toString();
        String uid = jsonObject.get("uid").toString();
        String pid = jsonObject.get("pid").toString();
        String money = jsonObject.get("money").toString();
        String tid=GetRuleStr("TRADE_NUM");
        //设置支付的金额
        double ACTO_money = Double.valueOf(money) * 100;
        String content = jsonObject.get("content").toString();
        String code = jsonObject.get("code").toString();
        String item_type = jsonObject.get("item").toString();
        SimpleDateFormat sDateFormat=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String datatime=sDateFormat.format(new Date());

        SMART_TRADE smartTrade=new SMART_TRADE();
        smartTrade.setGUID(guid);
        smartTrade.setTRADE_NUM(tid);
        smartTrade.setTRADE_AMT(money);
        smartTrade.setCONTENT(content);
        smartTrade.setSTATUS("01");
        smartTrade.setUSER_ID(uid);
        smartTrade.setITEM_TYPE(item_type);
        smartTrade.setDATATIME(datatime);
        smartTrade.setPROJECT_NUM(pid);
        //生成交易流水表
        try{
        iTrade.insertTrade(smartTrade);
            rJsonObject.put("code", "200");//插入成功
            Integer time=(int)(new Date().getTime()/1000);
            rJsonObject.put("timeStamp",time);
            String nonceStr=UUID.randomUUID().toString();
            rJsonObject.put("nonceStr",nonceStr);
            rJsonObject.put("package","prepay_id="+tid);
            rJsonObject.put("signType","MD5");
            String paySign= MD5Utils.stringToMD5("appId=wx02bb0359fa55dd39&nonceStr="+nonceStr+"&package="+"prepay_id="+tid+"&signType=MD5&timeStamp="+time+"&key=d2f337acec3934f980f8d00b0c9fd895");
            rJsonObject.put("paySign",paySign);
            //①、获取用户openid
          //String openId=common.GetOpenid(code);
            //②、统一下单
        }catch (Exception e){
            rJsonObject.put("code", "400");//插入失败
        }
        return rJsonObject.toJSONString();
    }
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
     * 获取商城明细数据
     */
    @RequestMapping(value = "/AddTradeFlow", method = RequestMethod.POST)
    public String AddTradeFlow(@RequestBody String params) throws IOException {
        JSONObject rJsonObject = new JSONObject();
        JSONObject jsonObject = JSONObject.parseObject(params);
        jsonObject=JSONObject.parseObject(jsonObject.getString("param"));
        /*获取参数*/
        String guid = UUID.randomUUID().toString();
        String uid = jsonObject.get("uid").toString();
        String pid = jsonObject.get("pid").toString();
        String money = jsonObject.get("money").toString();
        String content = jsonObject.get("content").toString();
        Common common=new Common();
        String tid=common.GetRuleStr("TRADE_NUM");
        SimpleDateFormat sDateFormat=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String datatime=sDateFormat.format(new Date());
        SMART_TRADE smartTrade=new SMART_TRADE();
        smartTrade.setGUID(guid);
        smartTrade.setTRADE_NUM(tid);
        smartTrade.setTRADE_AMT(money);
        smartTrade.setCONTENT(content);
        smartTrade.setSTATUS("06");
        smartTrade.setUSER_ID(uid);
        smartTrade.setITEM_TYPE("02");
        smartTrade.setDATATIME(datatime);
        smartTrade.setPROJECT_NUM(pid);
        //生成交易流水表
        try{
            iTrade.insertTrade(smartTrade);
            rJsonObject.put("code", "200");
        }catch (Exception e){
            rJsonObject.put("code", "400");
        }
        return rJsonObject.toJSONString();
    }
}
