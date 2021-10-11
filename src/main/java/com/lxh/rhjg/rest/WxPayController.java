package com.lxh.rhjg.rest;

import com.lxh.newrhjg.api.IframeProject;
import com.lxh.newrhjg.entity.FrameCoupon;
import com.lxh.newrhjg.utils.PrecisionCount;
import com.alibaba.fastjson.JSONObject;
import com.lxh.newrhjg.api.IframePeople;
import com.lxh.newrhjg.entity.FramePeople;
import com.lxh.rhjg.active.api.IProject;
import com.lxh.rhjg.active.api.Icommon;
import com.lxh.rhjg.active.api.SMART_PROJECT;
import com.lxh.rhjg.active.api.SMART_RULE;
import com.lxh.rhjg.circle.api.ICircle;
import com.lxh.rhjg.common.util.Common;
import com.lxh.rhjg.common.util.HttpClient;
import com.lxh.rhjg.common.util.MD5Utils;
import com.lxh.rhjg.entity.SMART_SIGNDAY;
import com.lxh.rhjg.entity.SMART_TRADE;
import com.lxh.rhjg.subscribe.api.ISubscribe;
import com.lxh.rhjg.trade.api.ITrade;
import com.lxh.test.common.PropertiesUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.github.wxpay.sdk.WXPayUtil;
import com.lxh.newrhjg.utils.PrecisionCount;

import java.io.IOException;
import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
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
    @Autowired
    IframePeople iframePeople;
    @Autowired
    IProject iProject;
    @Autowired
    IframeProject iframeProject;
    @Autowired
    ISubscribe iSubscribe;

    /*
     * 获取未订单列表
     */
    @RequestMapping(value = "/getPaymentOrder", method = RequestMethod.POST)
    public String getPaymentOrder(@RequestBody String params, @RequestHeader("openId") String openId) throws IOException {
        JSONObject rJsonObject = new JSONObject();
        try {
            if (openId == null) {
                rJsonObject.put("code", "4000");
                return rJsonObject.toJSONString();
            }
            FramePeople people = new FramePeople();
            people = iframePeople.findPeople("openid", openId);
            if (people == null) {
                rJsonObject.put("code", "400");
                return rJsonObject.toJSONString();
            }
            String userGuid = people.getRowGuid();

            List<SMART_TRADE> smartTradeList = new ArrayList<SMART_TRADE>();
            smartTradeList = iTrade.findTrade("USER_ID='" + userGuid + "' AND PAY_STATUS='0'");
            if (smartTradeList.size() != 0) {
                rJsonObject.put("code", "200");
                rJsonObject.put("list", smartTradeList);
            } else {
                rJsonObject.put("code", "400");
                rJsonObject.put("error", "查询失败");
            }
        } catch (Exception e) {
            rJsonObject.put("code", "400");
        }
        return rJsonObject.toJSONString();
    }

    /*
     * 获取商城明细数据
     */
    @RequestMapping(value = "/paySuccess", method = RequestMethod.POST)
    public String paySuccess(@RequestBody String params, @RequestHeader("openId") String openId) throws IOException {
        JSONObject rJsonObject = new JSONObject();
        if (openId == null) {
            rJsonObject.put("code", "4000");
            return rJsonObject.toJSONString();
        }
        try {
            JSONObject jsonObject = JSONObject.parseObject(params);
            jsonObject = JSONObject.parseObject(jsonObject.getString("param"));
            /*获取参数*/
            String tradeNum = jsonObject.get("tradeNum").toString();
            GregorianCalendar now = new GregorianCalendar();
            SimpleDateFormat sDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String pay_time = sDateFormat.format(now.getTime());
            now.add(GregorianCalendar.MONTH, 1);
            String expire_time = sDateFormat.format(now.getTime());
            FramePeople record = null;
            record = iframePeople.findPeople("openid", openId);

            List<SMART_TRADE> smartTradeList = new ArrayList<SMART_TRADE>();
            smartTradeList = iTrade.findTrade("TRADE_NUM='" + tradeNum + "'");
            if (smartTradeList.size() != 0 && record != null) {
                SMART_TRADE smartRecord = smartTradeList.get(0);
                String itemType = smartRecord.getITEM_TYPE();
                if (itemType.equals("02")) {
                    String projectNum = jsonObject.getString("projectNum");
                    String couponId=jsonObject.getString("couponId");
                    int couponAmount=0;
                    if(!couponId.isEmpty()){
                        FrameCoupon coupon= iframeProject.findCouponList("rowGuid='"+couponId+"'").get(0);
                        coupon.setCouponStatus(1);
                        coupon.setProjectNum(projectNum);
                        int n3=iframeProject.updateCoupon(coupon);
                        if(n3==0){
                            rJsonObject.put("code", "500");
                            return rJsonObject.toJSONString();
                        }
                        couponAmount=coupon.getCouponAmount();
                    }
                    Double amt = Double.parseDouble(smartRecord.getTRADE_AMT());
                    iProject.Commonupdateproject("STATUS='03'", " PROJECT_NUM='" + projectNum + "'");
                    SMART_PROJECT project = iProject.findProject("PROJECT_NUM='" + projectNum + "'").get(0);
                    double payAmt = Double.parseDouble(project.getPAY_AMT());
                    double resAmt = PrecisionCount.add(amt, payAmt);
                    double withCouponAmt=PrecisionCount.add(couponAmount,resAmt);
                    project.setPAY_AMT(String.valueOf(withCouponAmt));
                    iProject.updateproject(project);
                    rJsonObject.put("code", "200");
                    return rJsonObject.toJSONString();
                } else if (itemType.equals("09")) {
                    int level = Integer.parseInt(jsonObject.get("level").toString());
                    String couponId=jsonObject.getString("couponId");
                    record.setLevel(level);
                    record.setExpireTime(expire_time);
//                    开通会员赠送30积分
                    record.setEngineerIntegralAmount(record.getEngineerIntegralAmount()+30);
                    SMART_SIGNDAY signDay = new SMART_SIGNDAY();
                    signDay.setGUID(UUID.randomUUID().toString());
                    signDay.setUSER_ID(record.getRowGuid());
                    String dateTime = sDateFormat.format(new Date());
                    signDay.setDATATIME(dateTime);
                    signDay.setINTEGRALCHANGE(30);
                    signDay.setINTEGRALTYPE(3);
                    int n4=iSubscribe.insertsignday(signDay);
                    if(n4==0){
                        rJsonObject.put("code", "500");
                        return rJsonObject.toJSONString();
                    }
                    smartRecord.setCOUPONID(couponId);
                    smartRecord.setExPIRE_TIME(expire_time);
                    if(!couponId.isEmpty()){
                        FrameCoupon coupon= iframeProject.findCouponList("rowGuid='"+couponId+"'").get(0);
                        coupon.setCouponStatus(1);
                        int n3=iframeProject.updateCoupon(coupon);
                        if(n3==0){
                            rJsonObject.put("code", "500");
                            return rJsonObject.toJSONString();
                        }
                    }
                } else if (itemType.equals("07")) {
                    Double money = Double.parseDouble(smartRecord.getTRADE_AMT());
                    Double reward = record.getReward();
                    reward = PrecisionCount.add(reward, money);
                    record.setReward(reward);
                } else if (itemType.equals("10")) {
                    Double money = Double.parseDouble(smartRecord.getTRADE_AMT());
                    Double deposit = record.getDeposit();
                    deposit = PrecisionCount.add(deposit, money);
                    record.setDeposit(deposit);
                }
                smartRecord.setPAY_STATUS(1);
                smartRecord.setPAY_TIME(pay_time);
                int n1 = iTrade.updateTrade(smartRecord);
                int n2 = iframePeople.update(record);
                if (n1 != 0 && n2 != 0) {
                    rJsonObject.put("code", "200");
                    rJsonObject.put("userInfo", record);
                } else {
                    rJsonObject.put("code", "500");
                    rJsonObject.put("error", "接口异常");
                }
            }
        } catch (Exception e) {
            System.out.println(e);
            rJsonObject.put("code", "400");
        }
        return rJsonObject.toJSONString();
    }


    /*
     * //余额下单
     */
    @RequestMapping(value = "/RemainingSumPay", method = RequestMethod.POST)
    public String RemainingSumPay(@RequestBody String params,@RequestHeader("openId") String openId) throws Exception {
        JSONObject rJsonObject = new JSONObject();
        try {
            if (openId == null) {
                rJsonObject.put("code", "4000");
                return rJsonObject.toJSONString();
            }
            FramePeople people = new FramePeople();
            people = iframePeople.findPeople("openid", openId);
            if (people == null) {
                rJsonObject.put("code", "400");
                rJsonObject.put("error", "接口异常");
                return rJsonObject.toJSONString();
            }
            JSONObject jsonObject = JSONObject.parseObject(params);
            jsonObject = JSONObject.parseObject(jsonObject.getString("param"));
            Double remainingSum=people.getRemainingSum();
            Double money=jsonObject.getDouble("money");
            System.out.println(money);
            Double resSum=PrecisionCount.sub(remainingSum,money);
            people.setRemainingSum(resSum);
            int n2=iframePeople.update(people);
            String guid = UUID.randomUUID().toString();
            String pid = jsonObject.get("pid").toString();
            String uid = jsonObject.get("uid").toString();
            String money1 = jsonObject.get("money").toString();
            String tid = GetRuleStr("TRADE_NUM");
            String content = "余额支付";
            String item_type = jsonObject.get("item").toString();
            SimpleDateFormat sDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String datatime = sDateFormat.format(new Date());
            SMART_TRADE smartTrade = new SMART_TRADE();
            smartTrade.setGUID(guid);
            smartTrade.setTRADE_NUM(tid);
            smartTrade.setTRADE_AMT(money1);
            smartTrade.setCONTENT(content);
            smartTrade.setSTATUS("01");
            smartTrade.setPAY_STATUS(1);
            smartTrade.setUSER_ID(uid);
            smartTrade.setITEM_TYPE(item_type);
            smartTrade.setDATATIME(datatime);
            smartTrade.setPROJECT_NUM(pid);
            int n = iTrade.insertTrade(smartTrade);
            String projectNum = jsonObject.getString("projectNum");
            List<SMART_TRADE> smartTradeList = new ArrayList<SMART_TRADE>();
            smartTradeList = iTrade.findTrade("TRADE_NUM='" + tid + "'");
            if (n == 1&&n2==1&&smartTradeList.size() != 0) {
                SMART_TRADE smartRecord = smartTradeList.get(0);
                Double amt = Double.parseDouble(smartRecord.getTRADE_AMT());
                SMART_PROJECT project = iProject.findProject("PROJECT_NUM='" + projectNum + "'").get(0);
//               使用优惠券的情况
                String couponId=jsonObject.getString("couponId");
                int couponAmount=0;
                if(!couponId.isEmpty()){
                    FrameCoupon coupon= iframeProject.findCouponList("rowGuid='"+couponId+"'").get(0);
                    coupon.setCouponStatus(1);
                    coupon.setProjectNum(projectNum);
                    int n3=iframeProject.updateCoupon(coupon);
                    if(n3==0){
                        rJsonObject.put("code", "500");
                        return rJsonObject.toJSONString();
                    }
                    couponAmount=coupon.getCouponAmount();
                }
                double payAmt = Double.parseDouble(project.getPAY_AMT());
                double resAmt = PrecisionCount.add(amt, payAmt);
                double withCouponAmt=PrecisionCount.add(couponAmount,resAmt);
                iProject.Commonupdateproject("STATUS='03',PAY_AMT='"+withCouponAmt+"'", " PROJECT_NUM='" + projectNum + "'");
//                iProject.updateproject(project);
                rJsonObject.put("userInfo",people);
                rJsonObject.put("code", "200");//插入成功
            }
        } catch (Exception e) {
            System.out.println(e);
            rJsonObject.put("code", "400");//插入失败
        }
        return rJsonObject.toJSONString();
    }


    /*
     * //请求下单
     */
    @RequestMapping(value = "/PreOrder", method = RequestMethod.POST)
    public String PreOrder(@RequestBody String params) throws Exception {
        //生成交易流水表
        JSONObject rJsonObject = new JSONObject();
        try {
            //获取参数
            Map<String, String> reqMap = new HashMap<String, String>();
            //获取参数
            JSONObject jsonObject = JSONObject.parseObject(params);
            jsonObject = JSONObject.parseObject(jsonObject.getString("param"));
            String guid = UUID.randomUUID().toString();
            String pid = jsonObject.get("pid").toString();
            String uid = jsonObject.get("uid").toString();
            String code = jsonObject.get("code").toString();
            String money = jsonObject.get("money").toString();
            PropertiesUtil.loadFile("encode.properties");
            String appid = PropertiesUtil.getPropertyValue("appid");
            String secret = PropertiesUtil.getPropertyValue("secret");
            String tokenUrl = "https://api.weixin.qq.com/sns/jscode2session?appid=" + appid + "&secret=" + secret + "&js_code=" + code + "&grant_type=authorization_code";
            String oauthResponseText = HttpClient.doGet(tokenUrl);
            JSONObject jo = JSONObject.parseObject(oauthResponseText);
            reqMap.put("appid", appid);
            String contentEn = jsonObject.get("contentEn").toString();
            reqMap.put("body", contentEn);
            reqMap.put("mch_id", PropertiesUtil.getPropertyValue("mch_id"));
            String nonce_str = WXPayUtil.generateNonceStr();
            reqMap.put("nonce_str", nonce_str);
            reqMap.put("notify_url", "https://www.baidu.com");
            String tid = GetRuleStr("TRADE_NUM");
            System.out.println(tid);
            reqMap.put("out_trade_no", tid);
//            reqMap.put("sign_type","MD5");
            reqMap.put("spbill_create_ip", "127.0.0.1");
//            String paySign=WXPayUtil.generateSignature(reqMap,PropertiesUtil.getPropertyValue("partnerkey"));
//            reqMap.put("paySign",paySign);
            //设置支付的金额
            int money_part = new Double(Double.parseDouble(money) * 100).intValue();
            System.out.println(money_part);
            String ACTO_money = String.valueOf(money_part);
            reqMap.put("total_fee", ACTO_money);
            reqMap.put("trade_type", "JSAPI");
            reqMap.put("openid", jo.get("openid").toString());
            String reqStr = WXPayUtil.generateSignedXml(reqMap, PropertiesUtil.getPropertyValue("partnerkey"));
            String url = PropertiesUtil.getPropertyValue("order_url");
            String resultXml = HttpClient.doPost(url, reqStr);
            Map<String, String> return_data = WXPayUtil.xmlToMap(resultXml);
            String content = jsonObject.get("content").toString();
            String item_type = jsonObject.get("item").toString();
            SimpleDateFormat sDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String datatime = sDateFormat.format(new Date());
            if (return_data.get("return_code").equals("SUCCESS")) {
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
                int n = iTrade.insertTrade(smartTrade);
                if (n == 1) {
                    Map<String, String> repData = new HashMap<>();
                    repData.put("appId", appid);

                    String packag = "prepay_id=" + return_data.get("prepay_id");

                    repData.put("package", packag);
                    repData.put("signType", "MD5");
                    repData.put("nonceStr", WXPayUtil.generateNonceStr());
                    repData.put("timeStamp", String.valueOf(System.currentTimeMillis() / 1000));

                    //签名
                    String sign = WXPayUtil.generateSignature(repData, PropertiesUtil.getPropertyValue("partnerkey"));
                    rJsonObject.put("tradeNum", tid);
                    rJsonObject.put("paySign", sign);
                    rJsonObject.put("timeStamp", repData.get("timeStamp"));
                    rJsonObject.put("nonceStr", repData.get("nonceStr"));
                    rJsonObject.put("package", repData.get("package"));
                    rJsonObject.put("signType", repData.get("signType"));
                    rJsonObject.put("code", "200");//插入成功
                }
            } else {
                rJsonObject.put("code", 500);
                rJsonObject.put("error", "支付失败");
            }
        } catch (Exception e) {
            rJsonObject.put("code", "400");//插入失败
        }
        return rJsonObject.toJSONString();
    }

    public static String get32UUID() {
        UUID id = UUID.randomUUID();
        String[] idd = id.toString().split("-");
        return idd[0] + idd[1] + idd[2] + idd[3] + idd[4];
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
