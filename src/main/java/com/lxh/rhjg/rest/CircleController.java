package com.lxh.rhjg.rest;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.lxh.rhjg.circle.api.ICircle;
import com.lxh.rhjg.circle.api.SMART_PHOTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/CircleController")
public class CircleController {
    @Autowired
    ICircle iCircle;
    /*
     * 圈子的列表数据
     */
    @RequestMapping(value = "/GetCircle", method= RequestMethod.POST)
    public String GetCircle(@RequestBody String params) throws IOException {
        JSONObject rJsonObject=new JSONObject();
        JSONObject jsonObject= JSONObject.parseObject(params);
        String start=jsonObject.get("start").toString();
        String count=jsonObject.get("count").toString();
        try{
        int  length=Integer.parseInt(start)+Integer.parseInt(count);
         List<SMART_PHOTO> photoList=iCircle.getSMARTPHOTO(Integer.parseInt(start),length);
        //拼装数据
        rJsonObject.put("code","200");
        rJsonObject.put("count",count);
        rJsonObject.put("start",start);
        String str = JSON.toJSONString(photoList); // List转json
        rJsonObject.put("subjects",str);
        }catch (Exception e){
            rJsonObject.put("code","100");
        }
        return rJsonObject.toJSONString();
    }
    /*
     * 圈子详细数据
     */
    @RequestMapping(value = "/GetCircleDetail", method= RequestMethod.POST)
    public String GetCircleDetail(@RequestBody String params) throws IOException {
        JSONObject rJsonObject=new JSONObject();
        JSONObject jsonObject= JSONObject.parseObject(params);
        String fid=jsonObject.get("'param.fid'").toString();
        try{
         List<SMART_PHOTO> smartPhoto=iCircle.GetCircleDetail("  and ITEM_TYPE='08' AND SHORT_CODE='"+fid+"'");
          //SMART_SIGN 不存在
            rJsonObject.put("code","200");
            rJsonObject.put("current_num","{}");
            String str = JSON.toJSONString(smartPhoto); // List转json
            rJsonObject.put("subjects",str);
            rJsonObject.put("signinfo","{}");
        }catch (Exception e){
            rJsonObject.put("code","100");
        }
        return rJsonObject.toJSONString();
    }
}
