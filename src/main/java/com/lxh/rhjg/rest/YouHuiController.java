package com.lxh.rhjg.rest;


import com.alibaba.fastjson.JSONObject;
import com.lxh.rhjg.active.api.IPeople;
import com.lxh.rhjg.active.api.Icommon;
import com.lxh.rhjg.active.api.SMART_PEOPLE_EXT;
import com.lxh.rhjg.common.util.Common;
import com.lxh.rhjg.common.util.JGConstant;
import com.lxh.rhjg.entity.SMART_GAME;
import com.lxh.rhjg.entity.SMART_POINT_RECORD;
import com.lxh.rhjg.entity.SMART_SSID;
import com.lxh.rhjg.game.api.IGame;
import com.lxh.rhjg.point.api.IPoint;
import net.sf.ehcache.transaction.xa.ExpiredXidTransactionIDImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;

@RestController
@RequestMapping("/YouHuiController")
public class YouHuiController {
    @Autowired
    IGame iGame;
    @Autowired
    IPeople iPeople;
    @Autowired
    Icommon icommon;
    /*
     * 根据ID获取优惠券和用户信息
     */
    @RequestMapping(value = "/GetCardById", method = RequestMethod.POST)
    public String GetCardById(@RequestBody String params) throws IOException {
        JSONObject rJsonObject = new JSONObject();
        JSONObject jsonObject = JSONObject.parseObject(params);
        jsonObject=JSONObject.parseObject(jsonObject.getString("param"));
        String guid = jsonObject.get("guid").toString();
        String uid = jsonObject.get("uid").toString();
        String awardId = jsonObject.get("awardId").toString();

        //红包信息
        Map<String,Object> map=new HashMap<>();
        map.put("STATUS=","02");
        map.put("GUID=",guid);
        map.put("USER_ID=",uid);
        map.put("AWARD_ID=",awardId);
        List<SMART_GAME>  list=iGame.findList(map);
        //用户信息
        List<HashMap<String,Object>> list1=iPeople.findUserinfo(uid);
        rJsonObject.put("code", "200");
        rJsonObject.put("results", list);
        rJsonObject.put("user", list1.get(0));
        return rJsonObject.toJSONString();
    }
    /*
     * 兑换推荐服务
     */
    @RequestMapping(value = "/BuyTuiJian", method = RequestMethod.POST)
    public String BuyTuiJian(@RequestBody String params) throws IOException {
        JSONObject rJsonObject = new JSONObject();
        JSONObject jsonObject = JSONObject.parseObject(params);
        jsonObject=JSONObject.parseObject(jsonObject.getString("param"));
        String uid = jsonObject.get("uid").toString();
        String guid = jsonObject.get("guid").toString();
        String key = jsonObject.get("key").toString();
        String awardId = jsonObject.get("awardId").toString();
        String sid=icommon.findSmartssid("URL_CODE","RHJG002").getSSID_VALUE();
        if(key.equals(sid)){
            //增加推荐人员日期 2018.01.07
            SimpleDateFormat sDateFormat=new SimpleDateFormat("yyyy-MM-dd");
            String datatime=sDateFormat.format(new Date());
            String UserTypeFlag="";
            Calendar c = Calendar.getInstance();
            c.setTime(new Date());
            //注意修改
            String recommend_date="";
            UserTypeFlag = "99";
            if("5".equals(awardId)){
                //月度
                c.add(Calendar.MONTH,1);
                recommend_date=sDateFormat.format(c.getTime());
                UserTypeFlag = "00";
            }else if("4".equals(awardId)){
                //季度
                c.add(Calendar.MONTH,3);
                recommend_date=sDateFormat.format(c.getTime());
                UserTypeFlag = "01";
            }else{
                c.add(Calendar.MONTH,1);
                recommend_date=sDateFormat.format(c.getTime());
            }
            try {
                icommon.updateCommon("SMART_PEOPLE", "USER_TYPE ='01',RECOMMEND_TIME='" + recommend_date + "',USER_TYPE_FLAG='" + UserTypeFlag + "' ", " and USER_ID='" + uid + "'");
                //更新会员卡状态
                icommon.updateCommon("SMART_GAME", "STATUS= '04' ", " and GUID='" + guid + "' AND AWARD_ID='" + awardId + "' AND USER_ID='" + uid + "' and STATUS='02' ");
                rJsonObject.put("code", "200");//成功
            }catch (Exception e){
                rJsonObject.put("code", "400"); //失败
            }

        }else{
            rJsonObject.put("code", "400"); //失败
        }
        return rJsonObject.toJSONString();
    }
    /*
     * 兑换会员
     */
    @RequestMapping(value = "/BuyHuiYuan", method = RequestMethod.POST)
    public String BuyHuiYuan(@RequestBody String params) throws IOException {
        JSONObject rJsonObject = new JSONObject();
        JSONObject jsonObject = JSONObject.parseObject(params);
        jsonObject=JSONObject.parseObject(jsonObject.getString("param"));
        String uid = jsonObject.get("uid").toString();
        String guid = jsonObject.get("guid").toString();
        String awardId = jsonObject.get("awardId").toString();
        String key = jsonObject.get("key").toString();
        String level = jsonObject.get("level").toString();
        SimpleDateFormat sDateFormat=new SimpleDateFormat("yyyy-MM-dd");
        String datatime=sDateFormat.format(new Date());
        String sid=icommon.findSmartssid("URL_CODE","RHJG002").getSSID_VALUE();

        if(key.equals(sid)){
            int count = 0;
            if("00".equals(level)){
                count = JGConstant.VAR_PUTONG ;
            }else if("01".equals(level)){
                count = JGConstant.VAR_HUANGJIN;
            }else if("02".equals(level)){
                count = JGConstant.VAR_ZUANSHI;
            }else{
               count = 0;
            }
            Calendar c = Calendar.getInstance();
            c.setTime(new Date());
            //注意修改
            c.add(Calendar.MONTH,1);
            String huiyuan_date=sDateFormat.format(c.getTime());
            try {
                icommon.updateCommon("SMART_PEOPLE", "LEVEL ='" + level + "',TOTAL_COUNT='" + count + "',MEMBER_TIME='" + huiyuan_date + "',LEVEL_TYPE='00'", " and USER_ID='" + uid + "'");
                //更新会员卡状态
                icommon.updateCommon("SMART_GAME", "STATUS= '04'", " and  GUID='" + guid + "' AND AWARD_ID='" + awardId + "' AND USER_ID='" + uid + "' AND STATUS='02'");
                rJsonObject.put("code", "200");//成功
            }catch (Exception e){
                rJsonObject.put("code", "400");//成功
            }
        }else{
            rJsonObject.put("code", "400");//成功
        }
        return rJsonObject.toJSONString();
    }
    /*
     * 兑换优惠券
     */
    @RequestMapping(value = "/ConvertToCard", method = RequestMethod.POST)
    public String ConvertToCard(@RequestBody String params) throws IOException {
        JSONObject rJsonObject = new JSONObject();
        JSONObject jsonObject = JSONObject.parseObject(params);
        jsonObject=JSONObject.parseObject(jsonObject.getString("param"));
        //类型(兑换会员还是推荐服务)
        String type = jsonObject.get("type").toString();
        String uid = jsonObject.get("uid").toString();
        String guid = jsonObject.get("guid").toString();
        String awardId = jsonObject.get("awardId").toString();
        String key = jsonObject.get("key").toString();

        SimpleDateFormat sDateFormat=new SimpleDateFormat("yyyy-MM-dd");
        String datatime=sDateFormat.format(new Date());
        //01：
        String selectTuiJianType = jsonObject.get("selectTuiJianType").toString();

        //02：
        String selectMonthType = jsonObject.get("selectMonthType").toString();
        String selectLevel = jsonObject.get("selectLevel").toString();
        Common common =new Common();
       String sid= common.GetSSIDStr("RHJG002");
        if(key.equals(sid)){
            if("00".equals(type)){
                int count = 0;
                if("00".equals(selectLevel)){
                    count = JGConstant.VAR_PUTONG;
                }else if("01".equals(selectLevel)){
                    count = JGConstant.VAR_HUANGJIN;
                }else if("02".equals(selectLevel)){
                    count =JGConstant.VAR_ZUANSHI;
                }else{
                    count = 0;
                }
                //兑换会员
                Calendar c = Calendar.getInstance();
                c.setTime(new Date());
                //注意修改
                String huiyuan_date="";
                if("00".equals(selectMonthType)){
                    //按照月充值
                    c.add(Calendar.MONTH,1);
                    huiyuan_date=sDateFormat.format(c.getTime());
                }else if("01".equals(selectMonthType)){
                    //按照年度充值
                    c.add(Calendar.MONTH,12);
                    huiyuan_date=sDateFormat.format(c.getTime());
                }else{
                    c.add(Calendar.MONTH,1);
                    huiyuan_date=sDateFormat.format(c.getTime());
                }
                try {
                    icommon.updateCommon("SMART_PEOPLE", "  LEVEL ='" + selectLevel + "',TOTAL_COUNT='" + count + "',MEMBER_TIME='" + huiyuan_date + "',LEVEL_TYPE='" + selectMonthType + "'", " and  USER_ID='" + uid + "'");
                    //更新会员卡状态
                    icommon.updateCommon("SMART_GAME", " STATUS= '04'", " and  GUID='" + guid + "' AND AWARD_ID='" + awardId + "' AND USER_ID='" + uid + "'  AND STATUS='02'");
                    rJsonObject.put("code", "200");//成功
                }catch (Exception e){
                    rJsonObject.put("code", "400");//失败
                }
            }else if("01".equals(type)){
                //兑换推荐服务
                Calendar c = Calendar.getInstance();
                c.setTime(new Date());
                //注意修改
                String recommend_date="";
                String UserTypeFlag = "99";
                if("00".equals(selectTuiJianType)){
                    //月度
                    c.add(Calendar.MONTH,1);
                    recommend_date=sDateFormat.format(c.getTime());
                    UserTypeFlag ="00";
                }else if("01".equals(selectTuiJianType)){
                    //季度
                    c.add(Calendar.MONTH,3);
                    recommend_date=sDateFormat.format(c.getTime());
                    UserTypeFlag ="01";
                }else if("02".equals(selectTuiJianType)){
                    //年度
                    c.add(Calendar.MONTH,12);
                    recommend_date=sDateFormat.format(c.getTime());
                    UserTypeFlag ="02";
                }
                try {
                    icommon.updateCommon("SMART_PEOPLE", "  USER_TYPE ='01',RECOMMEND_TIME='"+recommend_date+"',USER_TYPE_FLAG='"+UserTypeFlag+"'", " and  USER_ID='" + uid + "'");
                    //更新会员卡状态
                    icommon.updateCommon("SMART_GAME", " STATUS= '04'", " and  GUID='" + guid + "' AND AWARD_ID='" + awardId + "' AND USER_ID='" + uid + "'  AND STATUS='02'");
                    rJsonObject.put("code", "200");//成功
                }catch (Exception e){
                    rJsonObject.put("code", "400");//失败
                }

            }else{
                rJsonObject.put("code", "400");//失败
            }
        }else{
            rJsonObject.put("code", "400");//失败
        }
        return rJsonObject.toJSONString();
    }
    /*
     * 领取推荐服务费五折券
     */
    @RequestMapping(value = "/GetYouHuiCard", method = RequestMethod.POST)
    public String GetYouHuiCard(@RequestBody String params) throws IOException {
        JSONObject rJsonObject = new JSONObject();
        JSONObject jsonObject = JSONObject.parseObject(params);
        jsonObject = JSONObject.parseObject(jsonObject.getString("param"));
        //
        String uid = jsonObject.get("uid").toString();
        String key = jsonObject.get("key").toString();
        Common common=new Common();
        String sid=common.GetSSIDStr("RHJG002");
        if(key.equals(sid)){
            SimpleDateFormat sDateFormat=new SimpleDateFormat("yyyy-MM-dd");
            String datatime=sDateFormat.format(new Date());
            String guid =UUID.randomUUID().toString();
            //推荐服务费5折 AWARD_ID 500
            Map<String,Object> map=new HashMap<>();
            map.put("USER_ID=",uid);
            map.put("AWARD_ID=","500");
            map.put("ITEM_TYPE=","5");
            List<HashMap<String,Object>> list=icommon.findlist("SMART_GAME","*",map,"","");
            if(list.size()> 0){
                //插入获奖记录
                rJsonObject.put("code", "100");
            }else{
                SMART_GAME smartGame=new SMART_GAME();
                smartGame.setGUID(UUID.randomUUID().toString());
                smartGame.setUSER_ID(uid);
                smartGame.setAWARD_ID("500");
                smartGame.setAWARD_NAME("'融汇精工推荐服务费5折券'");
                smartGame.setWX_NAME("未填写");
                smartGame.setWX_IMG("未填写");
                smartGame.setDATATIME(datatime);
                smartGame.setSTATUS("02");
                smartGame.setITEM_TYPE("5");
                  iGame.insert(smartGame);
                rJsonObject.put("code", "200");
            }
            //插入BIG_AWARD记录，标记领取奖品
        }else{
            rJsonObject.put("code", "400");
        }
        return rJsonObject.toJSONString();
    }
}
