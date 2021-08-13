package com.lxh.rhjg.rest;


import com.alibaba.fastjson.JSONObject;
import com.lxh.rhjg.active.api.Icommon;
import com.lxh.rhjg.active.api.SMART_RULE;
import com.lxh.rhjg.circle.api.ICircle;
import com.lxh.rhjg.common.util.Common;
import com.lxh.rhjg.common.util.HttpClient;
import com.lxh.rhjg.common.util.MD5Utils;
import com.lxh.rhjg.entity.SMART_TRADE;
import com.lxh.rhjg.trade.api.ITrade;
import com.lxh.test.common.PropertiesUtil;
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
    public String PreOrder(@RequestBody String params) throws Exception {
        //生成交易流水表
        JSONObject rJsonObject = new JSONObject();
        try{
            //获取参数
            Map<String,String> reqMap=new HashMap<String,String>();
            //获取参数
            JSONObject jsonObject = JSONObject.parseObject(params);
            jsonObject = JSONObject.parseObject(jsonObject.getString("param"));
            String guid = UUID.randomUUID().toString();
            String pid=jsonObject.get("pid").toString();
            String uid=jsonObject.get("uid").toString();
            String code=jsonObject.get("code").toString();
            String money = jsonObject.get("money").toString();
            String tid = GetRuleStr("TRADE_NUM");
            PropertiesUtil.loadFile("encode.properties");
            String appid=PropertiesUtil.getPropertyValue("appid");
            String secret=PropertiesUtil.getPropertyValue("secret");
            String tokenUrl = "https://api.weixin.qq.com/sns/jscode2session?appid=" + appid + "&secret=" + secret + "&js_code=" + code + "&grant_type=authorization_code";
            String oauthResponseText = HttpClient.doGet(tokenUrl);
            JSONObject jo = JSONObject.parseObject(oauthResponseText);
            reqMap.put("appid",appid);
            reqMap.put("mch_id",PropertiesUtil.getPropertyValue("mch_id"));
            String nonce_str=get32UUID();
            reqMap.put("nonce_str",nonce_str);
            reqMap.put("body","pay");
            reqMap.put("openid",jo.get("openid").toString());
            reqMap.put("out_trade_no",tid);
            reqMap.put("sign_type","MD5");
            reqMap.put("notify_url", "http://www.example.com/wxpay/notify");
//            String paySign=WXPayUtil.generateSignature(reqMap);
//            reqMap.put("paySign",paySign);
            System.out.println(reqMap);
            //设置支付的金额
            int money_part=Integer.parseInt(money) * 100;
            String ACTO_money =String.valueOf(money_part);
            reqMap.put("total_fee",ACTO_money);
            reqMap.put("trade_type","JSAPI");
            String reqStr=WXPayUtil.mapToXml(reqMap);
            String url=PropertiesUtil.getPropertyValue("order_url");
            String resultXml= HttpClient.doPost(url,reqStr);
            System.out.println(resultXml);
            System.out.println("resultXml");
            Map<String,String> return_data=WXPayUtil.xmlToMap(resultXml);

            String content = jsonObject.get("content").toString();
            String item_type = jsonObject.get("item").toString();
            SimpleDateFormat sDateFormat=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String datatime=sDateFormat.format(new Date());

            SMART_TRADE smartTrade = new SMART_TRADE();
            smartTrade.setGUID(guid);
            smartTrade.setTRADE_NUM(tid);
            smartTrade.setTRADE_AMT(money);
            smartTrade.setCONTENT(content);
            smartTrade.setSTATUS("01");
            smartTrade.setUSER_ID(uid);
            smartTrade.setITEM_TYPE(item_type);
            smartTrade.setDATATIME(datatime);
            smartTrade.setPROJECT_NUM(pid);
            iTrade.insertTrade(smartTrade);

            rJsonObject.put("code", "200");//插入成功
            Integer time=(int)(new Date().getTime()/1000);
            rJsonObject.put("timeStamp",time);
            String nonceStr=get32UUID();
            rJsonObject.put("nonceStr",nonceStr);
            rJsonObject.put("package","prepay_id="+return_data.get("prepay_id"));
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

    public static String get32UUID(){
        UUID id=UUID.randomUUID();
        String[] idd=id.toString().split("-");
        return idd[0]+idd[1]+idd[2]+idd[3]+idd[4];
    }

    public String GetRuleStr(String ruleid) {
        String returnvalue = "";
        SMART_RULE smartRule = icommon.findSmartrule("rule_id", ruleid);
        if (smartRule != null) {
            String id = smartRule.getRuleValue();
            if (id.equals("") || id == null) {
                id = "1000000";
            }
            int num = Integer.parseInt(id) + 1;
            smartRule.setRuleValue(String.valueOf(num));
            //更新最大编号
            icommon.upSmartrule(smartRule);
            returnvalue = smartRule.getPrefix() + String.valueOf(num);
        }
        return returnvalue;
    }

    /*
     * 获取商城明细数据
     */
    @RequestMapping(value = "/AddTradeFlow", method = RequestMethod.POST)
    public String AddTradeFlow(@RequestBody String params) throws IOException {
        JSONObject rJsonObject = new JSONObject();
        JSONObject jsonObject = JSONObject.parseObject(params);
        jsonObject = JSONObject.parseObject(jsonObject.getString("param"));
        /*获取参数*/
        String guid = UUID.randomUUID().toString();
        String uid = jsonObject.get("uid").toString();
        String pid = jsonObject.get("pid").toString();
        String money = jsonObject.get("money").toString();
        String content = jsonObject.get("content").toString();
        Common common = new Common();
        String tid = common.GetRuleStr("TRADE_NUM");
        SimpleDateFormat sDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String datatime = sDateFormat.format(new Date());
        SMART_TRADE smartTrade = new SMART_TRADE();
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
        try {
            iTrade.insertTrade(smartTrade);
            rJsonObject.put("code", "200");
        } catch (Exception e) {
            rJsonObject.put("code", "400");
        }
        return rJsonObject.toJSONString();
    }
}
