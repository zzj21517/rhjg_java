package com.lxh.rhjg.rest;


import com.alibaba.fastjson.JSONObject;
import com.lxh.rhjg.active.api.IPeople;
import com.lxh.rhjg.active.api.Icommon;
import com.lxh.rhjg.common.util.Common;
import com.lxh.rhjg.common.util.HttpClient;
import com.lxh.rhjg.common.util.JGConstant;
import com.lxh.rhjg.entity.SMART_GAME;
import com.lxh.rhjg.entity.SMART_LINK;
import com.lxh.rhjg.game.api.IGame;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;

@RestController
@RequestMapping("/TuiJianService")
public class TuiJianServiceController {
    @Autowired
    Icommon icommon;
    @Autowired
    IPeople iPeople;
    /*
     *
     */
    @RequestMapping(value = "/GetServiceAmt", method = RequestMethod.POST)
    public String GetServiceAmt(@RequestBody String params) throws IOException {
        JSONObject rJsonObject = new JSONObject();
        JSONObject jsonObject = JSONObject.parseObject(params);
        jsonObject=JSONObject.parseObject(jsonObject.getString("param"));
        String key = jsonObject.get("key").toString();
         Common common=new Common();
        String sid = common.GetSSIDStr("TUIJIAN_SERVICE");
        if(sid.equals(key)){
            Map<String,Object>map=new HashMap<>();
            map.put("B.USER_TYPE=","01");
            map.put("A.STATUS=","06");
            map.put("B.USER_TYPE=","01");
            String condition=" and A.USER_ID=B.USER_ID ";
            String order=" GROUP BY B.NICK_NAME ORDER BY SUM(A.PROJECT_AMT) DESC";
            List<HashMap<String,Object>> list=icommon.findlist("SMART_PROJECT_USER A,SMART_PEOPLE B","B.NICK_NAME,SUM(A.PROJECT_AMT) TOTAL_AMT",map,condition,order);
            rJsonObject.put("code","200");
            rJsonObject.put("result","list");
        }else{
            rJsonObject.put("code","400");
        }
        return rJsonObject.toJSONString();
    }
    /*
     *
     */
    @RequestMapping(value = "/MaoSuiZiJian", method = RequestMethod.POST)
    public String MaoSuiZiJian(@RequestBody String params) throws IOException {
        JSONObject rJsonObject = new JSONObject();
        JSONObject jsonObject = JSONObject.parseObject(params);
        jsonObject=JSONObject.parseObject(jsonObject.getString("param"));
        String team_type = jsonObject.get("team_type").toString();
        String link_man = jsonObject.get("link_man").toString();
        String link_tel = jsonObject.get("link_tel").toString();
        String link_wx = jsonObject.get("link_wx").toString();
        String link_type = jsonObject.get("link_type").toString();
        String profession = jsonObject.get("profession").toString();
        String soft = jsonObject.get("soft").toString();
        String link_content = jsonObject.get("link_content").toString();
        String link_city = jsonObject.get("link_city").toString();
        String key = jsonObject.get("key").toString();
        SimpleDateFormat sDateFormat=new SimpleDateFormat("yyyy-MM-dd");
        String datatime=sDateFormat.format(new Date());
        Common common=new Common();
        String sid = common.GetSSIDStr("TUIJIAN_SERVICE");

        if(sid.equals(key)){
            SMART_LINK smartLink=new SMART_LINK();
            smartLink.setGUID(UUID.randomUUID().toString());
            smartLink.setLINK_MAN(link_man);
            smartLink.setLINK_TEL(link_tel);
            smartLink.setLINK_WX(link_wx);
            smartLink.setLINK_TYPE(link_type);
            smartLink.setDATATIME(datatime);
            smartLink.setLINK_CONTENT(link_content);
            smartLink.setPROFESSION(profession);
            smartLink.setLINK_SOFT(soft);
            smartLink.setTEAM_TYPE(team_type);
            smartLink.setLINK_CITY(link_city);
            iPeople.insertLink(smartLink);

            //发送微信消息
            String message = "融汇精工毛遂自荐伙伴：【"+team_type+"】"+link_man+",可以做的专业【"+profession+"】,电话：【"+link_tel+"】，微信：【"+link_wx+"】，拥有组价软件：【"+soft+"】,可以做活城市：【"+link_city+"】,个人优势为:【"+link_content+"】,需要与您合作，请客服人员及时回复。";

            String title = "融汇精工毛遂自荐";
            String url = "https://pushbear.ftqq.com/sub?sendkey=11327-43dcfcd1c3459ca125d43a074d707893&text="+title+"&desp="+message;

              try{
             HttpClient.doPost(url,"");
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
