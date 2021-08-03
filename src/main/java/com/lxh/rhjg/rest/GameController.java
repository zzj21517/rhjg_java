package com.lxh.rhjg.rest;


import com.alibaba.fastjson.JSONObject;
import com.lxh.rhjg.active.api.*;
import com.lxh.rhjg.common.util.Common;
import com.lxh.rhjg.entity.*;
import com.lxh.rhjg.game.api.IGame;
import com.lxh.rhjg.point.api.IPoint;
import com.lxh.rhjg.subscribe.api.ISubscribe;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.management.monitor.CounterMonitorMBean;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@RestController
@RequestMapping("/GameController")
public class GameController {

    @Autowired
    IGame iGame;
    @Autowired
    Icommon icommon;
    @Autowired
    IPeople iPeople;
    @Autowired
    IPoint iPoint;
    /*
     * 添加游戏获奖信息
     */
    @RequestMapping(value = "/AddGameInfo", method = RequestMethod.POST)
    public String AddGameInfo(@RequestBody String params) throws IOException {
        JSONObject rJsonObject = new JSONObject();
        JSONObject jsonObject = JSONObject.parseObject(params);
        jsonObject=JSONObject.parseObject(jsonObject.getString("param"));
        String uid = jsonObject.get("uid").toString();
        String key = jsonObject.get("key").toString();
        String image = jsonObject.get("image").toString();
        String name = jsonObject.get("name").toString();
        String awardId = jsonObject.get("awardId").toString();
        String awardName = jsonObject.get("awardName").toString();
        String gamePoint=jsonObject.get("gamePoint").toString();

         Common common=new Common();
        String sid = common.GetSSIDStr("RHJG001");
         if(key.equals(sid)){
             SimpleDateFormat sDateFormat=new SimpleDateFormat("yyyy-MM-dd");
             String datatime=sDateFormat.format(new Date());
             //统计每个奖品的数量
            List<HashMap<String,Object>> list=iGame.getGameCount();
            HashMap<String,Object> hashMap=new HashMap<>();
             hashMap=list.get(0);
             int gongzai = Integer.parseInt(hashMap.get("gongzai").toString());
             int huangjin = Integer.parseInt(hashMap.get("huangjin").toString());
             int zuanshi = Integer.parseInt(hashMap.get("'zuanshi'").toString());
             int fee_3 = Integer.parseInt(hashMap.get("'fee_3'").toString());
             int fee_1 = Integer.parseInt(hashMap.get("'fee_1'").toString());
             //超过一定数量的奖品不插入记录，显示中奖，不扣除比分
             if("1".equals(awardId)&& gongzai >= 40){
                 //不进行中奖操作
                 rJsonObject.put("code", "300");
             }else if("2".equals(awardId) && huangjin >= 8){
                 rJsonObject.put("code", "300");
             }else if("3".equals(awardId) &&zuanshi >= 4){
                 rJsonObject.put("code", "300");
             }else if("4".equals(awardId)&&fee_3 >= 10){
                 rJsonObject.put("code", "300");
             }else if("5".equals(awardId) &&fee_1 >= 20){
                 rJsonObject.put("code", "300");
             }else if("0".equals(awardId)){
                 //插入获奖记录,20金币
                 //$ExtPeole = M('People_ext');
                 //$ExtPeole->where("USER_ID='$uid'")->setInc("GAME_POINT",20);
                 rJsonObject.put("code", "200");
             }else{
                 //插入获奖记录
                 SMART_GAME smartGame=new SMART_GAME();
                 smartGame.setGUID(UUID.randomUUID().toString());
                 smartGame.setUSER_ID(uid);
                 smartGame.setAWARD_ID(awardId);
                 smartGame.setAWARD_NAME(awardName);
                 smartGame.setWX_NAME(name);
                 smartGame.setWX_IMG(image);
                 smartGame.setDATATIME(datatime);
                 smartGame.setSTATUS("02");
                 smartGame.setITEM_TYPE("0");
                 try {
                     iGame.insert(smartGame);
                     //更新剩余比分
                     icommon.updateCommon("SMART_PEOPLE_EXT", " GAME_POINT='" + gamePoint + "',DATATIME='" + datatime + "'", " and USER_ID='" + uid + "'");
                     rJsonObject.put("code", "200");
                 }catch (Exception e){
                     rJsonObject.put("code", "400");
                 }
             }
         } else{
             rJsonObject.put("code", "400");
         }

        return rJsonObject.toJSONString();
    }
    /*
     *
     */
    @RequestMapping(value = "/CheckGameNum", method = RequestMethod.POST)
    public String CheckGameNum(@RequestBody String params) throws IOException {
        JSONObject rJsonObject = new JSONObject();
        JSONObject jsonObject = JSONObject.parseObject(params);
        jsonObject=JSONObject.parseObject(jsonObject.getString("param"));
        String uid = jsonObject.get("uid").toString();
        String num = jsonObject.get("num").toString();
        int ActualValue = Integer.parseInt(num)%30 + 1;
        int TotalNum=iGame.getCount(" and USER_ID='"+uid+"' AND ITEM_TYPE='0'");
        if(TotalNum<2){
            if(ActualValue ==1){
                //1.判断用户是否中奖娃娃,娃娃的编码是【1】
                int BabyNum=iGame.getCount(" and USER_ID='"+uid+"' AND AWARD_ID='1'");
                if(BabyNum > 0){
                    //中过娃娃的奖，返回谢谢惠顾的奖品，奖品7
                    rJsonObject.put("code", "200");
                    rJsonObject.put("number", "900");
                }else{
                    rJsonObject.put("code", "200");
                    rJsonObject.put("number", num);
                }
            }else{
                rJsonObject.put("code", "200");
                rJsonObject.put("number", num);
            }
        }else if(TotalNum==2){
            //1.判断用户是否中奖娃娃,娃娃的编码是【1】
            int BabyNum=iGame.getCount(" and USER_ID='"+uid+"' AND AWARD_ID='1'");
            if(BabyNum > 0){
                //中过娃娃的奖，返回谢谢惠顾的奖品，奖品7
                rJsonObject.put("code", "200");
                rJsonObject.put("number", "900");
            }else{
                //必须中娃娃
                rJsonObject.put("code", "200");
                rJsonObject.put("number", "960");
            }
        }else if(TotalNum==7){
            rJsonObject.put("code", "200");
            rJsonObject.put("number", "960");
        }else if(TotalNum ==10){
            rJsonObject.put("code", "200");
            rJsonObject.put("number", "960");
        }else{
            rJsonObject.put("code", "200");
            rJsonObject.put("number", num);
        }
        return rJsonObject.toJSONString();
    }
    /*
     *
     */
    @RequestMapping(value = "/GetUserPoint", method = RequestMethod.POST)
    public String GetUserPoint(@RequestBody String params) throws IOException {
        JSONObject rJsonObject = new JSONObject();
        JSONObject jsonObject = JSONObject.parseObject(params);
        jsonObject=JSONObject.parseObject(jsonObject.getString("param"));
        String uid = jsonObject.get("uid").toString();
        SMART_PEOPLE_EXT smartPeopleExt=iPeople.GetSmartPeopleExt("USER_ID",uid);
        //获取用户奖励滚动
        Map<String ,Object> map=new HashMap<>();
        map.put("AWARD_ID!=","7");
        map.put("ITEM_TYPE=","0");
        List<SMART_GAME> list=iGame.findList(map);
        //统计每个奖品的数量
        List<HashMap<String,Object>> listcount=iGame.getGameCount();
        HashMap<String,Object> hashMap=new HashMap<>();
        hashMap=listcount.get(0);
        //判断是否弹出框
        int isBomb =isBombModal(uid);
        rJsonObject.put("code", "200");
        rJsonObject.put("isBombModal", isBomb);
        rJsonObject.put("isBombModal", isBomb);
        rJsonObject.put("results", smartPeopleExt);
        rJsonObject.put("subjects", list);
        rJsonObject.put("static", hashMap);
        return rJsonObject.toJSONString();
    }
    /*
     *添加大礼包信息
     */
    @RequestMapping(value = "/AddBigAward", method = RequestMethod.POST)
    public String AddBigAward(@RequestBody String params) throws IOException {
        JSONObject rJsonObject = new JSONObject();
        JSONObject jsonObject = JSONObject.parseObject(params);
        jsonObject=JSONObject.parseObject(jsonObject.getString("param"));
        String uid = jsonObject.get("uid").toString();
        String key = jsonObject.get("key").toString();
        String image = jsonObject.get("image").toString();
        String name = jsonObject.get("name").toString();
        String awardId = jsonObject.get("awardId").toString();
        String awardName = jsonObject.get("awardName").toString();
        String awardType = jsonObject.get("awardType").toString();
        SMART_SSID smartSsid=icommon.findSmartssid("URL_CODE","RHJG001");
        String sid=smartSsid.getSSID_VALUE();
        try{
        if(key.equals(sid)){
            SimpleDateFormat sDateFormat=new SimpleDateFormat("yyyy-MM-dd");
            String datatime=sDateFormat.format(new Date());
            String guid=UUID.randomUUID().toString();
            //积分奖品直接兑换101:40金币 301：100金币
            if("101".equals(awardId)||"301".equals(awardId)){
                //插入获奖记录
                SMART_GAME smartGame=new SMART_GAME();
                smartGame.setGUID(guid);
                smartGame.setUSER_ID(uid);
                smartGame.setAWARD_ID(awardId);
                smartGame.setAWARD_NAME(awardName);
                smartGame.setWX_NAME(name);
                smartGame.setWX_IMG(image);
                smartGame.setDATATIME(datatime);
                smartGame.setSTATUS("04");
                smartGame.setITEM_TYPE(awardType);
                iGame.insert(smartGame);
                //增加用户积分
                int point = 0;
                if("101".equals(awardId)){
                    point = 40;
                }else if("301".equals(awardId)){
                    point = 100;
                }
                SMART_PEOPLE_EXT smartPeopleExt=iPeople.GetSmartPeopleExt("USER_ID",uid);
                point=Integer.parseInt(smartPeopleExt.getGAME_POINT())+point;
                icommon.updateCommon("SMART_PEOPLE_EXT","set GAME_POINT="+point," and USER_ID='"+uid+"'");
                rJsonObject.put("code", "200");
        } else{
              //非金币转换AWARD_ID
                if("104".equals(awardId)){
                   awardId ="6";
                }else if("103".equals(awardId)|| "201".equals(awardId)){
                    awardId ="2";
                }else if("303".equals(awardId)){
                    awardId ="3";
                }else if("304".equals(awardId)){
                    awardId ="5";
                }
                //插入获奖记录
                //插入获奖记录
                SMART_GAME smartGame=new SMART_GAME();
                smartGame.setGUID(UUID.randomUUID().toString());
                smartGame.setUSER_ID(uid);
                smartGame.setAWARD_ID(awardId);
                smartGame.setAWARD_NAME(awardName);
                smartGame.setWX_NAME(name);
                smartGame.setWX_IMG(image);
                smartGame.setDATATIME(datatime);
                smartGame.setSTATUS("02");
                smartGame.setITEM_TYPE(awardType);
                iGame.insert(smartGame);
                rJsonObject.put("code", "200");
            }
        } else{
            rJsonObject.put("code", "400");
      }
        }catch (Exception e){
            rJsonObject.put("code", "400");
        }
        return rJsonObject.toJSONString();
    }
    /*
     *分享得积分活动
     */
    @RequestMapping(value = "/AddPointRecord", method = RequestMethod.POST)
    public String AddPointRecord(@RequestBody String params) throws IOException {
        JSONObject rJsonObject = new JSONObject();
        JSONObject jsonObject = JSONObject.parseObject(params);
        jsonObject=JSONObject.parseObject(jsonObject.getString("param"));
        String uid = jsonObject.get("uid").toString();
        String name = jsonObject.get("name").toString();
        String image = jsonObject.get("image").toString();
        String code = jsonObject.get("code").toString();
        Common common=new Common();
        String openId=common.GetOpenid(code);
        //主键
        SimpleDateFormat sDateFormat=new SimpleDateFormat("yyyy-MM-dd");
        String datatime=sDateFormat.format(new Date());
        String guid=UUID.randomUUID().toString();

        Map<String, Object> map=new HashMap<>();
        map.put("SHARE_ID=",openId);
        map.put("SHORT_CODE=",uid);
       List<SMART_POINT_RECORD> list=iPoint.findList(map);
       if(list.size()>0){
           rJsonObject.put("code", "100");
       }else{
           SMART_POINT_RECORD smartPointRecord=new SMART_POINT_RECORD();
           smartPointRecord.setGUID(guid);
           smartPointRecord.setSHORT_CODE(uid);
           smartPointRecord.setSHARE_ID(openId);
           smartPointRecord.setSHARE_NAME(name);
           smartPointRecord.setSHARE_IMAGE(image);
           smartPointRecord.setDATATIME(datatime);
           iPoint.insert(smartPointRecord);
           //增加5积分
           SMART_PEOPLE_EXT smartPeopleExt=iPeople.GetSmartPeopleExt("USER_ID",uid);
           int point=Integer.parseInt(smartPeopleExt.getGAME_POINT())+5;
           icommon.updateCommon("SMART_PEOPLE_EXT","set GAME_POINT="+point," and USER_ID='"+uid+"'");
           rJsonObject.put("code", "200");
       }
        return rJsonObject.toJSONString();
    }
    /*
     *活动是否开始
     */
    @RequestMapping(value = "/IsActivtyStart", method = RequestMethod.POST)
    public String IsActivtyStart(@RequestBody String params) throws IOException {
        JSONObject rJsonObject = new JSONObject();
        JSONObject jsonObject = JSONObject.parseObject(params);
       String isActivty=icommon.findSmartrule("RULE_ID","IS_ACTIVTY_START").getRuleValue();
        rJsonObject.put("code", "isActivty");
        return rJsonObject.toJSONString();
    }
    /*
     * 判断是否弹出大礼包
     */
    public int isBombModal(String userId){
        int isBomb = 0;
        if("".equals(userId)){
            isBomb = 0;
        }else{
           List<SMART_PEOPLE_EXT> list=iPeople.GetSmartPeopleExtlist(" and INVITE_MAN IN(SELECT SHORT_CODE FROM SMART_PEOPLE_EXT WHERE USER_ID='"+userId+"')");

           int cust_num = list.size();
            List<HashMap<String,Object>> list1=iGame.getBombCount(userId);
            HashMap<String,Object> hashMap=new HashMap<>();
            hashMap=list1.get(0);
           int type1=Integer.parseInt(hashMap.get("TYPE1").toString());
           int type2=Integer.parseInt(hashMap.get("TYPE2").toString());
           int type3=Integer.parseInt(hashMap.get("TYPE3").toString());
            //未领取大礼包
            if(type1 == 0){
                if(cust_num >= 5){
                    isBomb = 1;
                }else{
                    isBomb = 0;
                }
            }else if(type2 == 0){
                if(cust_num >= 10){
                    isBomb = 2;
                }else{
                    isBomb = 0;
                }
            }else if(type3 == 0){
                if(cust_num >= 20){
                    isBomb = 3;
                }else{
                    isBomb = 0;
                }
            }else{
                isBomb = 0;
            }
        }

        return isBomb;
    }
    /*
     *判断红包是否领取完毕
     */
    @RequestMapping(value = "/IsRedBagUseOut", method = RequestMethod.POST)
    public String IsRedBagUseOut(@RequestBody String params) throws IOException, ParseException {
        JSONObject rJsonObject = new JSONObject();
        JSONObject jsonObject = JSONObject.parseObject(params);
        jsonObject=JSONObject.parseObject(jsonObject.getString("param"));
        String key = jsonObject.get("key").toString();
        String uid = jsonObject.get("uid").toString();
        Common common=new Common();
        String sid=common.GetSSIDStr("RHJG001");
        if(key.equals(sid)){
            SimpleDateFormat sDateFormat=new SimpleDateFormat("hh:mm:ss");
            String current_time=sDateFormat.format(new Date());

            if(sDateFormat.parse(current_time).after(sDateFormat.parse("09:00:00"))){

                Map<String,Object> map=new HashMap<>();
                map.put("STATUS=","60");
                map.put("ITEM_TYPE=","08");
              List<HashMap<String,Object>> list= icommon.findlist("SMART_TRADE","SUM(TRADE_AMT) TRADE_AMT",map," AND SUBSTR(DATATIME,1,10) = SUBSTR(CURRENT_TIMESTAMP(),1,10)","");
                //$sql = "SELECT COUNT(*) COUNT_NUM FROM SMART_TRADE WHERE STATUS='60' AND DATATIME >= '2019-01-29 00:01:26' ";
                HashMap<String,Object> hashMap=list.get(0);
                int tradeAmt=Integer.parseInt(hashMap.get("TRADE_AMT").toString()) ;

                if(tradeAmt <= 10){  //采用多少金额的活动
                    //判断当前用户是否已经参加抢红包活动了
                    map=new HashMap<>();
                    map.put("STATUS=","60");
                    map.put("ITEM_TYPE=","08");
                    map.put("USER_ID=",uid);
                   list= icommon.findlist("SMART_TRADE","SUM(TRADE_AMT) TRADE_AMT",map," AND SUBSTR(DATATIME,1,10) = SUBSTR(CURRENT_TIMESTAMP(),1,10)","");

                    if(list.size() > 0){
                        rJsonObject.put("code", "500");
                        rJsonObject.put("tip", "每个用户当天只能参加一次活动，请明天再来。");
                    }else{
                        rJsonObject.put("code", "200");
                        rJsonObject.put("tip", "每个用户当天只能参加一次活动，请明天再来。");
                    }

                }else{
                    rJsonObject.put("code", "300");
                    rJsonObject.put("tip", "今日红包已被抢光，请明天9：00再来。");//红包抢没了
                }
            }else{
                rJsonObject.put("code", "100");
                rJsonObject.put("tip", "活动时间未到，每天上午9:00活动准时开始。");//时间未到
            }

        }else{
            rJsonObject.put("code", "400");
            rJsonObject.put("tip", "系统出错，请联系系统管理员。");
        }
        //$dataStr = "{\"code\": \"500\",\"tip\":\"本次活动已经结束，欢迎下次关注。\" } ";
        return rJsonObject.toJSONString();
        //$this->ajaxReturn($dataStr,"JSON");
    }
}
