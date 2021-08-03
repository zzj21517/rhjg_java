package com.lxh.rhjg.rest;


import com.alibaba.fastjson.JSONObject;
import com.lxh.rhjg.active.api.*;
import com.lxh.rhjg.circle.api.SMART_PHOTO;
import com.lxh.rhjg.common.util.Common;
import com.lxh.rhjg.entity.SMART_POINT_DETAIL;
import com.lxh.rhjg.entity.SMART_SIGNDAY;
import com.lxh.rhjg.entity.SMART_TIP;
import com.lxh.rhjg.subscribe.api.ISubscribe;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

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
    /*
     * 增加订阅项目
     */
    @RequestMapping(value = "/AddSubscribe", method = RequestMethod.POST)
    public String AddSubscribe(@RequestBody String params) throws IOException {
        JSONObject rJsonObject = new JSONObject();
        JSONObject jsonObject = JSONObject.parseObject(params);
        jsonObject=JSONObject.parseObject(jsonObject.getString("param"));
        String uid = jsonObject.get("uid").toString();
        String professon = jsonObject.get("professon").toString();
        String sheng = jsonObject.get("sheng").toString();
        String shi = jsonObject.get("shi").toString();
        String xian = jsonObject.get("xian").toString();
        String name = jsonObject.get("name").toString();
        String guid= UUID.randomUUID().toString();
        String amt_type=jsonObject.get("radio").toString();
        String amt_begin=jsonObject.get("begin").toString();
        String amt_end=jsonObject.get("end").toString();
        SimpleDateFormat sDateFormat=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String datatime=sDateFormat.format(new Date());
        SMART_SUBSCRIBE smartSubscribe=new SMART_SUBSCRIBE();
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
            icommon.updateCommon("SMART_SUBSCRIBE","STATUS='00'","  AND USER_ID='"+uid+"' AND STATUS='02'");
            rJsonObject.put("code", "200");
        }catch (Exception e){
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
        jsonObject=JSONObject.parseObject(jsonObject.getString("param"));
        String uid = jsonObject.get("uid").toString();
        Map<String,Object> map=new HashMap<>();

        try {
            List<SMART_SUBSCRIBE> list=iProject.SubscribeProject(" and USER_ID='"+uid+"' AND STATUS='02'");
            if(list.size()>0){
              rJsonObject.put("code", "200");
            }
            else {
                rJsonObject.put("code", "300");
            }
            rJsonObject.put("results", list);
        }
        catch (Exception e){
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
        jsonObject=JSONObject.parseObject(jsonObject.getString("param"));
        String uid = jsonObject.get("uid").toString();
        Map<String,Object> map=new HashMap<>();

        try {
           icommon.updateCommon("SMART_SUBSCRIBE"," STATUS='00'"," and USER_ID='"+uid+"' AND STATUS='02'");
            rJsonObject.put("code", "200");
        }
        catch (Exception e){
            rJsonObject.put("code", "100");
        }
        return rJsonObject.toJSONString();
    }
    /*
     * 用户签到(有问题)
     */
    @RequestMapping(value = "/SignAttend", method = RequestMethod.POST)
    public String SignAttend(@RequestBody String params) throws IOException, ParseException {
        JSONObject rJsonObject = new JSONObject();
        JSONObject jsonObject = JSONObject.parseObject(params);
        jsonObject=JSONObject.parseObject(jsonObject.getString("param"));
        String uid = jsonObject.get("uid").toString();
        Map<String,Object> map=new HashMap<>();
        String last="";
        String day="";
        String sign_date="";
        //查询出最后一次签到日期
        List<HashMap<String,Object>>  list=iSubscribe.getLastSign(" USER_ID='"+uid+"'");
        if(list.size()>0){
            last = list.get(0).get("last_sign_date").toString();
            day = list.get(0).get("'continue_sign'").toString();
            sign_date =list.get(0).get("'sign_date'").toString();
        }
        SimpleDateFormat sDateFormat=new SimpleDateFormat("yyyy-MM-dd");
        String datatime=sDateFormat.format(new Date());
        if("1".equals(last)){
             int total =Integer.parseInt(day) + 1 ;
             icommon.updateCommon("SMART_PEOPLE","LAST_SIGN_DATE='"+datatime+"',CONTINUE_SIGN='"+total+"'"," and USER_ID='"+uid+"' ");
            Calendar c = Calendar.getInstance();
            c.setTime(sDateFormat.parse(datatime));
            //注意修改
            c.add(Calendar.DAY_OF_MONTH,3);
            int jifen=0;
            //注意修改
            if(total >= 5){
               jifen = 50;
            }else{
              jifen = total * 10;
            }
            AddUserPoint(uid, "00", "0037facc4349f35ae180ae75bb351e1c", "10");
            rJsonObject.put("code", "400");
        }else if("0".equals(last) ){
            //今天已经签到，什么都不做
            rJsonObject.put("code", "200");
        }else{
            //记录上次签到的结束时间和长度
            String guid = UUID.randomUUID().toString();
            String datatime1=sDateFormat.format(new Date());
            SMART_SIGNDAY smartSignday=new SMART_SIGNDAY();
            smartSignday.setGUID(guid);
            smartSignday.setUSER_ID(uid);
            smartSignday.setLAST_SIGN_DATE(sign_date);
            smartSignday.setCONTINUE_DAY(day);
            smartSignday.setDATATIME(datatime1);
            iSubscribe.insertsignday(smartSignday);
             icommon.updateCommon("SMART_PEOPLE"," LAST_SIGN_DATE='"+datatime1+"',CONTINUE_SIGN='1'","USER_ID='"+uid+"'");

             AddUserPoint(uid, "00", "0037facc4349f35ae180ae75bb351e1c", "10");
            rJsonObject.put("code", "400");
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
        jsonObject=JSONObject.parseObject(jsonObject.getString("param"));
        String uid = jsonObject.get("uid").toString();
        List<HashMap<String,Object>> list= iSubscribe.getCard(" USER_ID ='$uid' AND STATUS='02' ORDER BY DATATIME DESC");
        rJsonObject.put("code", "200");
        rJsonObject.put("subjects", "list");
        return rJsonObject.toJSONString();
    }
    public boolean AddUserPoint(String uid,String vtype,String key,String point){
        Common common=new Common();
       String  sid=common.GetRuleStr("JIFEN");
        if(key.equals(sid)){
            //增加用户的积分
          SMART_PEOPLE_EXT smartPeopleExt=iPeople.GetSmartPeopleExt("USER_ID",uid);
           double jifen_point =Double.valueOf(smartPeopleExt.getJIFEN_POINT());
           double total=jifen_point+Double.valueOf(point);

           icommon.updateCommon("SMART_PEOPLE_EXT","JIFEN_POINT='"+total+"'"," and USER_ID='"+uid+"'");
            //插入日志
            String guid = UUID.randomUUID().toString();
            SimpleDateFormat sDateFormat=new SimpleDateFormat("yyyy-MM-dd");
            String datatime=sDateFormat.format(new Date());

            SMART_POINT_DETAIL smartPointDetail=new SMART_POINT_DETAIL();
            smartPointDetail.setGUID(guid);
            smartPointDetail.setUSER_ID(uid);
            smartPointDetail.setJIFEN_POINT(String.valueOf(jifen_point));
            smartPointDetail.setJIFEN_TYPE(vtype);
            smartPointDetail.setDATATIME(datatime);
            try {
                iPeople.insertPointDetail(smartPointDetail);
                return true;    //成功
            }catch (Exception e){
                return false;   //失败
            }
        }else{
            return false;
        }
    }
}
