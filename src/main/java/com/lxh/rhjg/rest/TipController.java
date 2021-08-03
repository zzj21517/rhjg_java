package com.lxh.rhjg.rest;


import com.alibaba.fastjson.JSONObject;
import com.lxh.rhjg.tip.api.ITip;
import com.lxh.rhjg.circle.api.ICircle;
import com.lxh.rhjg.circle.api.SMART_PHOTO;
import com.lxh.rhjg.entity.SMART_TIP;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/TipController")
public class TipController {
    @Autowired
    ITip iTip;
    @Autowired
    ICircle iCircle;
    /*
     * 新增商城数据
     */
    @RequestMapping(value = "/AddTip", method = RequestMethod.POST)
    public String AddTip(@RequestBody String params) throws IOException {
        JSONObject rJsonObject = new JSONObject();
        JSONObject jsonObject = JSONObject.parseObject(params);
        jsonObject=JSONObject.parseObject(jsonObject.getString("param"));
        String uid = jsonObject.get("uid").toString();
        String title = jsonObject.get("title").toString();
        String content = jsonObject.get("content").toString();
        String guid = jsonObject.get("guid").toString();
        SimpleDateFormat sDateFormat=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String datatime=sDateFormat.format(new Date());

        SMART_TIP smart_tip=new SMART_TIP();
        smart_tip.setGUID(guid);
        smart_tip.setUSER_ID(uid);
        smart_tip.setTITLE(title);
        smart_tip.setCONTENT(content);
        smart_tip.setDATATIME(datatime);
        try {
            iTip.inserttip(smart_tip);
            rJsonObject.put("code", "200");
        }catch (Exception e){
            rJsonObject.put("code", "400");
        }
        return rJsonObject.toJSONString();
    }
    /*
     * 获取商城明细数据
     */
    @RequestMapping(value = "/GetTipDetail", method = RequestMethod.POST)
    public String GetTipDetail(@RequestBody String params) throws IOException {
        JSONObject rJsonObject = new JSONObject();
        JSONObject jsonObject = JSONObject.parseObject(params);
        jsonObject=JSONObject.parseObject(jsonObject.getString("param"));
        String guid = jsonObject.get("guid").toString();
        Map<String,Object> map=new HashMap<>();
        map.put("SHORT_CODE",guid);
        map.put("ITEM_TYPE","01");
        try {
            List<SMART_TIP> list = iTip.find(map);
            List<SMART_PHOTO> photoList = iCircle.GetCircleDetail(" and SHORT_CODE='" + guid + "' AND ITEM_TYPE='01'");
            rJsonObject.put("code", "200");
            rJsonObject.put("photo", photoList);
            rJsonObject.put("subjects", list);
        }
        catch (Exception e){
            rJsonObject.put("code", "400");
        }
        return rJsonObject.toJSONString();
    }
}
