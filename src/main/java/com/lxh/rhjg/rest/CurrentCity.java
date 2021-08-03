package com.lxh.rhjg.rest;


import com.alibaba.fastjson.JSONObject;
import com.lxh.rhjg.active.api.IPeople;
import com.lxh.rhjg.active.api.IProject;
import com.lxh.rhjg.active.api.Icommon;
import com.lxh.rhjg.active.api.SMART_PROJECT;
import com.lxh.rhjg.common.util.Common;
import com.lxh.rhjg.common.util.HttpClient;
import com.lxh.rhjg.entity.SMART_MARK;
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
import java.util.UUID;

@RestController
@RequestMapping("/CurrentCity")
public class CurrentCity {
    @Autowired
    Icommon icommon;
    /*
     *获取当前位置
     */
    @RequestMapping(value = "/GetCurAddress", method = RequestMethod.POST)
    public String GetCurAddress(@RequestBody String params) throws IOException {
        JSONObject rJsonObject = new JSONObject();
        JSONObject jsonObject = JSONObject.parseObject(params);
        jsonObject=JSONObject.parseObject(jsonObject.getString("param"));
        String lat = jsonObject.get("lat").toString();
        String lng = jsonObject.get("lng").toString();
        String url = "http://apis.map.qq.com/ws/geocoder/v1/?location="+lat+","+lng+"&key=ZGNBZ-YTU3D-CTP43-HM3VQ-FEHN2-ZABZA";
        String data=HttpClient.doPost(url,"");
        rJsonObject.put("subjects",data);
        return rJsonObject.toJSONString();
    }

}
