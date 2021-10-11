package com.lxh.rhjg.rest;


import com.alibaba.fastjson.JSONObject;
import com.lxh.newrhjg.api.IframePeople;
import com.lxh.newrhjg.entity.FramePeople;
import com.lxh.rhjg.active.api.*;
import com.lxh.rhjg.circle.api.SMART_PHOTO;
import com.lxh.rhjg.common.util.Common;
import com.lxh.rhjg.entity.SMART_POINT_DETAIL;
import com.lxh.rhjg.entity.SMART_SIGNDAY;
import com.lxh.rhjg.entity.SMART_TIP;
import com.lxh.rhjg.subscribe.api.ISubscribe;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@RestController
@RequestMapping("/SignController")
public class SignController {

    @Autowired
    ISubscribe iSubscribe;
    @Autowired
    Icommon icommon;
    @Autowired
    IProject iProject;
    @Autowired
    IPeople iPeople;
    @Autowired
    IframePeople iframePeople;

    /*
     * 增加订阅项目
     */
    @RequestMapping(value = "/AddSubscribe", method = RequestMethod.POST)
    public String AddSubscribe(@RequestBody String params) throws IOException {
        JSONObject rJsonObject = new JSONObject();
        JSONObject jsonObject = JSONObject.parseObject(params);
        jsonObject = JSONObject.parseObject(jsonObject.getString("param"));
        String uid = jsonObject.get("uid").toString();
        String professon = jsonObject.get("professon").toString();
        String sheng = jsonObject.get("sheng").toString();
        String shi = jsonObject.get("shi").toString();
        String xian = jsonObject.get("xian").toString();
        String name = jsonObject.get("name").toString();
        String guid = UUID.randomUUID().toString();
        String amt_type = jsonObject.get("radio").toString();
        String amt_begin = jsonObject.get("begin").toString();
        String amt_end = jsonObject.get("end").toString();
        SimpleDateFormat sDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String datatime = sDateFormat.format(new Date());
        SMART_SUBSCRIBE smartSubscribe = new SMART_SUBSCRIBE();
        smartSubscribe.setGuid(guid);
        smartSubscribe.setUserId(uid);
        smartSubscribe.setProfession(professon);
        smartSubscribe.setSregieId(sheng);
        smartSubscribe.setMregieId(shi);
        smartSubscribe.setCregieId(xian);
        smartSubscribe.setAmtType(amt_type);
        smartSubscribe.setBeginAmt(amt_begin);
        smartSubscribe.setEndAmt(amt_end);
        smartSubscribe.setStatus("02");
        smartSubscribe.setDatatime(datatime);
        try {
            iSubscribe.insertSubscribe(smartSubscribe);
            icommon.updateCommon("SMART_SUBSCRIBE", "STATUS='00'", "  AND USER_ID='" + uid + "' AND STATUS='02'");
            rJsonObject.put("code", "200");
        } catch (Exception e) {
            rJsonObject.put("code", "400");
        }
        return rJsonObject.toJSONString();
    }

    /*
     * 查询是否已经订阅
     */
    @RequestMapping(value = "/QuerySub", method = RequestMethod.POST)
    public String QuerySub(@RequestBody String params) throws IOException {
        JSONObject rJsonObject = new JSONObject();
        JSONObject jsonObject = JSONObject.parseObject(params);
        jsonObject = JSONObject.parseObject(jsonObject.getString("param"));
        String uid = jsonObject.get("uid").toString();
        Map<String, Object> map = new HashMap<>();

        try {
            List<SMART_SUBSCRIBE> list = iProject.SubscribeProject(" and USER_ID='" + uid + "' AND STATUS='02'");
            if (list.size() > 0) {
                rJsonObject.put("code", "200");
            } else {
                rJsonObject.put("code", "300");
            }
            rJsonObject.put("results", list);
        } catch (Exception e) {
            rJsonObject.put("code", "100");
        }
        return rJsonObject.toJSONString();
    }

    /*
     * 取消订阅
     */
    @RequestMapping(value = "/DelSubscribe", method = RequestMethod.POST)
    public String DelSubscribe(@RequestBody String params) throws IOException {
        JSONObject rJsonObject = new JSONObject();
        JSONObject jsonObject = JSONObject.parseObject(params);
        jsonObject = JSONObject.parseObject(jsonObject.getString("param"));
        String uid = jsonObject.get("uid").toString();
        Map<String, Object> map = new HashMap<>();

        try {
            icommon.updateCommon("SMART_SUBSCRIBE", " STATUS='00'", " and USER_ID='" + uid + "' AND STATUS='02'");
            rJsonObject.put("code", "200");
        } catch (Exception e) {
            rJsonObject.put("code", "100");
        }
        return rJsonObject.toJSONString();
    }

    /*
     * 积分兑换
     */
    @RequestMapping(value = "/IntegralConvert", method = RequestMethod.POST)
    public String IntegralConvert(@RequestBody String params, @RequestHeader("openId") String openId) throws IOException {
        JSONObject rJsonObject = new JSONObject();
        try {
            JSONObject jsonObject = JSONObject.parseObject(params);
            jsonObject = JSONObject.parseObject(jsonObject.getString("param"));
            int convertNum=jsonObject.getIntValue("convertNum");
            if (openId == null) {
                rJsonObject.put("code", "4000");
                return rJsonObject.toJSONString();
            }
            FramePeople people = new FramePeople();
            people = iframePeople.findPeople("openid", openId);
            if (people == null) {
                rJsonObject.put("code", "500");
                rJsonObject.put("error", "接口异常");
                return rJsonObject.toJSONString();
            }
            SMART_SIGNDAY signDay = new SMART_SIGNDAY();
            signDay.setGUID(UUID.randomUUID().toString());
            signDay.setUSER_ID(people.getRowGuid());
            SimpleDateFormat sDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String dateTime = sDateFormat.format(new Date());
            signDay.setDATATIME(dateTime);
            signDay.setINTEGRALCHANGE(100*convertNum);
            int userFlag=people.getUserFlag();
            if(userFlag==0){
                signDay.setINTEGRALTYPE(6);
                people.setEngineerIntegralAmount(people.getEngineerIntegralAmount()-100*convertNum);
                people.setEngineerIntegralConvertAmount(people.getEngineerIntegralConvertAmount()+convertNum);
            }else{
                signDay.setINTEGRALTYPE(7);
                people.setCustomIntegralAmount(people.getCustomIntegralAmount()-100*convertNum);
                people.setCustomIntegralConvertAmount(people.getCustomIntegralConvertAmount()+convertNum);
            }
            int n1=iframePeople.update(people);
            int n2=iSubscribe.insertsignday(signDay);
            if(n1!=0&&n2!=0){
                rJsonObject.put("code","200");
            }else{
                rJsonObject.put("code","500");
            }
        } catch (Exception e) {
            rJsonObject.put("code", "500");
        }
        return rJsonObject.toJSONString();
    }

    /*
     * 用户签到(有问题)
     */
    @RequestMapping(value = "/SignAttend", method = RequestMethod.POST)
    public String SignAttend(@RequestBody String params, @RequestHeader("openId") String openId) throws IOException, ParseException {
        JSONObject rJsonObject = new JSONObject();
        try {
            if (openId == null) {
                rJsonObject.put("code", "4000");
                return rJsonObject.toJSONString();
            }
            FramePeople people = new FramePeople();
            people = iframePeople.findPeople("openid", openId);
            if (people == null) {
                rJsonObject.put("code", "500");
                rJsonObject.put("error", "接口异常");
                return rJsonObject.toJSONString();
            }
            SimpleDateFormat sDateFormat = new SimpleDateFormat("yyyy-MM-dd");
            int userFlag = people.getUserFlag();
            if (userFlag == 0) {
                Boolean canSignIn = false;
                if (people.getEngineerLastSignInTime() != null) {
                    String lastSignInTime = sDateFormat.format(sDateFormat.parse(people.getEngineerLastSignInTime()));
                    String curTime = sDateFormat.format(new Date());
                    if (!lastSignInTime.equals(curTime)) {
                        canSignIn = true;
                    }
                } else {
                    canSignIn = true;
                }
                if (!canSignIn) {
                    rJsonObject.put("code", "200");
                    rJsonObject.put("canSignIn", canSignIn);
                } else {
                    String dateTime = sDateFormat.format(new Date());
                    System.out.println(dateTime);
                    SMART_SIGNDAY signDay = new SMART_SIGNDAY();
                    signDay.setINTEGRALTYPE(0);
                    signDay.setGUID(UUID.randomUUID().toString());
                    signDay.setUSER_ID(people.getRowGuid());
                    if (people.getEngineerLastSignInTime() != null) {
                        System.out.println("lasttime");
                        signDay.setLAST_SIGN_DATE(people.getEngineerLastSignInTime());
                    }
                    signDay.setCONTINUE_DAY("1");
                    signDay.setDATATIME(dateTime);
                    signDay.setINTEGRALCHANGE(10);
                    people.setEngineerLastSignInTime(dateTime);
                    people.setEngineerIntegralAmount(people.getEngineerIntegralAmount() + 10);
                    int n = iSubscribe.insertsignday(signDay);
                    int n1 = iframePeople.update(people);
                    if (n != 0 && n1 != 0) {
                        rJsonObject.put("code", "200");
                        rJsonObject.put("canSignIn", canSignIn);
                    } else {
                        rJsonObject.put("code", "500");
                    }
                }
            } else {
                Boolean canSignIn = false;
                if (people.getCustomLastSignInTime() != null) {
                    String lastSignInTime = sDateFormat.format(sDateFormat.parse(people.getCustomLastSignInTime()));
                    String curTime = sDateFormat.format(new Date());
                    if (!lastSignInTime.equals(curTime)) {
                        canSignIn = true;
                    }
                } else {
                    canSignIn = true;
                }
                if (!canSignIn) {
                    rJsonObject.put("code", "200");
                    rJsonObject.put("canSignIn", canSignIn);
                } else {
                    String dateTime = sDateFormat.format(new Date());
                    System.out.println(dateTime);
                    SMART_SIGNDAY signDay = new SMART_SIGNDAY();
                    signDay.setINTEGRALTYPE(1);
                    signDay.setGUID(UUID.randomUUID().toString());
                    signDay.setUSER_ID(people.getRowGuid());
                    if (people.getCustomLastSignInTime() != null) {
                        System.out.println("lasttime");
                        signDay.setLAST_SIGN_DATE(people.getCustomLastSignInTime());
                    }
                    signDay.setCONTINUE_DAY("1");
                    signDay.setDATATIME(dateTime);
                    signDay.setINTEGRALCHANGE(10);
                    people.setCustomLastSignInTime(dateTime);
                    people.setCustomIntegralAmount(people.getCustomIntegralAmount() + 10);
                    int n = iSubscribe.insertsignday(signDay);
                    int n1 = iframePeople.update(people);
                    if (n != 0 && n1 != 0) {
                        rJsonObject.put("code", "200");
                        rJsonObject.put("canSignIn", canSignIn);
                    } else {
                        rJsonObject.put("code", "500");
                    }
                }
            }

        } catch (Exception e) {
            System.out.println(e);
            rJsonObject.put("code", "500");//插入失败
        }
        return rJsonObject.toJSONString();
    }

    /*
     * 用户签到(有问题)
     */
    @RequestMapping(value = "/GetMyCard001", method = RequestMethod.POST)
    public String GetMyCard001(@RequestBody String params) throws IOException, ParseException {
        JSONObject rJsonObject = new JSONObject();
        JSONObject jsonObject = JSONObject.parseObject(params);
        jsonObject = JSONObject.parseObject(jsonObject.getString("param"));
        String uid = jsonObject.get("uid").toString();
        List<HashMap<String, Object>> list = iSubscribe.getCard(" USER_ID ='$uid' AND STATUS='02' ORDER BY DATATIME DESC");
        rJsonObject.put("code", "200");
        rJsonObject.put("subjects", "list");
        return rJsonObject.toJSONString();
    }

    public boolean AddUserPoint(String uid, String vtype, String key, String point) {
        Common common = new Common();
        String sid = common.GetRuleStr("JIFEN");
        if (key.equals(sid)) {
            //增加用户的积分
            SMART_PEOPLE_EXT smartPeopleExt = iPeople.GetSmartPeopleExt("USER_ID", uid);
            double jifen_point = Double.valueOf(smartPeopleExt.getJIFEN_POINT());
            double total = jifen_point + Double.valueOf(point);

            icommon.updateCommon("SMART_PEOPLE_EXT", "JIFEN_POINT='" + total + "'", " and USER_ID='" + uid + "'");
            //插入日志
            String guid = UUID.randomUUID().toString();
            SimpleDateFormat sDateFormat = new SimpleDateFormat("yyyy-MM-dd");
            String datatime = sDateFormat.format(new Date());

            SMART_POINT_DETAIL smartPointDetail = new SMART_POINT_DETAIL();
            smartPointDetail.setGUID(guid);
            smartPointDetail.setUSER_ID(uid);
            smartPointDetail.setJIFEN_POINT(String.valueOf(jifen_point));
            smartPointDetail.setJIFEN_TYPE(vtype);
            smartPointDetail.setDATATIME(datatime);
            try {
                iPeople.insertPointDetail(smartPointDetail);
                return true;    //成功
            } catch (Exception e) {
                return false;   //失败
            }
        } else {
            return false;
        }
    }
}
