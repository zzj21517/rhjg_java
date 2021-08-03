package com.lxh.newrhjg.rest;

import com.alibaba.fastjson.JSONObject;
import com.lxh.newrhjg.api.IIcon;
import com.lxh.newrhjg.api.IMssages;
import com.lxh.newrhjg.api.Inewcommon;
import com.lxh.newrhjg.entity.FrameIcon;
import com.lxh.newrhjg.entity.FrameMessages;
import com.lxh.newrhjg.entity.FrameMessagesHistroy;
import com.lxh.rhjg.entity.SMART_ERRORLOG;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;

@RestController
@RequestMapping("/icon")
public class IconController {
    @Autowired
    IIcon icon;
    @Autowired
    Inewcommon icommon;

    //添加图标
    @RequestMapping(value = "/addIcon", method = RequestMethod.POST)
    public String addIcon(@RequestBody String params)  {
        JSONObject rJsonObject = new JSONObject();
        JSONObject jsonObject = JSONObject.parseObject(params);
        jsonObject=JSONObject.parseObject(jsonObject.getString("param"));
        SimpleDateFormat sDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String datatime = sDateFormat.format(new Date());
        try {
        String iconThem = jsonObject.getString("iconThem");//图标主题
        String iconName = jsonObject.getString("iconName");//图标名称
        String iconurl = jsonObject.getString("iconurl");//图标图片地址
        String Iconlinkurl = jsonObject.getString("Iconlinkurl");//跳转地址
        String ordernum = jsonObject.getString("ordernum");//排序号
        String guid = UUID.randomUUID().toString();
        FrameIcon record = new FrameIcon();
        record.setRowGuid(guid);
        record.setIconThem(iconThem);
        record.setIconName(iconName);
        record.setIconurl(iconurl);
        record.setIconlinkurl(Iconlinkurl);
        record.setOrdernum(Integer.parseInt(ordernum));

            int n = icon.insert(record);
            if (n == 1) {
                rJsonObject.put("code", "200");//插入成功
            } else {
                rJsonObject.put("code", "400");
            }
        } catch (Exception e) {
            //插入报错信息
            SMART_ERRORLOG smartErrorlog = new SMART_ERRORLOG();
            smartErrorlog.setGuid(UUID.randomUUID().toString());
            smartErrorlog.setGuid(datatime);
            smartErrorlog.setErrorlog(e.getMessage());
            icommon.insertlog(smartErrorlog);
            rJsonObject.put("code", "400");//插入失败
            rJsonObject.put("error",e.getMessage());//插入失败
        }
        return rJsonObject.toJSONString();
    }
    //更新图标
    @RequestMapping(value = "/upIcon", method = RequestMethod.POST)
    public String upIcon(@RequestBody String params)  {
        JSONObject rJsonObject = new JSONObject();
        JSONObject jsonObject = JSONObject.parseObject(params);
        jsonObject=JSONObject.parseObject(jsonObject.getString("param"));
        SimpleDateFormat sDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String datatime = sDateFormat.format(new Date());
        try {
        String iconThem = jsonObject.getString("iconThem");//图标主题
        String iconName = jsonObject.getString("iconName");//图标名称
        String iconurl = jsonObject.getString("iconurl");//图标图片地址
        String Iconlinkurl = jsonObject.getString("Iconlinkurl");//跳转地址
        String rowguid = jsonObject.getString("rowguid");//跳转地址
        String ordernum = jsonObject.getString("ordernum");//排序号
        FrameIcon record = new FrameIcon();
        record.setRowGuid(rowguid);
        record.setIconThem(iconThem);
        record.setIconName(iconName);
        record.setIconurl(iconurl);
        record.setIconlinkurl(Iconlinkurl);
        record.setOrdernum(Integer.parseInt(ordernum));

            int n = icon.update(record);
            if (n == 1) {
                rJsonObject.put("code", "200");//插入成功
            } else {
                rJsonObject.put("code", "400");
            }
        } catch (Exception e) {
            //插入报错信息
            SMART_ERRORLOG smartErrorlog = new SMART_ERRORLOG();
            smartErrorlog.setGuid(UUID.randomUUID().toString());
            smartErrorlog.setGuid(datatime);
            smartErrorlog.setErrorlog(e.getMessage());
            icommon.insertlog(smartErrorlog);
            rJsonObject.put("code", "400");//插入失败
            rJsonObject.put("error",e.getMessage());//插入失败

        }
        return rJsonObject.toJSONString();
    }
    //删除图标
    @RequestMapping(value = "/delIcon", method = RequestMethod.POST)
    public String delIcon(@RequestBody String params)  {
        JSONObject rJsonObject = new JSONObject();
        JSONObject jsonObject = JSONObject.parseObject(params);
        SimpleDateFormat sDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String datatime = sDateFormat.format(new Date());
        jsonObject=JSONObject.parseObject(jsonObject.getString("param"));
        try {
        String rowguid = jsonObject.getString("rowguid");//

            FrameIcon frameIcon=new FrameIcon();
            frameIcon.setRowGuid(rowguid);
            int n = icon.delete(frameIcon);
            if (n == 1) {
                rJsonObject.put("code", "200");//插入成功
            } else {
                rJsonObject.put("code", "400");
            }
        } catch (Exception e) {
            //插入报错信息
            SMART_ERRORLOG smartErrorlog = new SMART_ERRORLOG();
            smartErrorlog.setGuid(UUID.randomUUID().toString());
            smartErrorlog.setGuid(datatime);
            smartErrorlog.setErrorlog(e.getMessage());
            icommon.insertlog(smartErrorlog);
            rJsonObject.put("code", "400");//插入失败
            rJsonObject.put("error",e.getMessage());//插入失败
        }
        return rJsonObject.toJSONString();
    }
    //获取图标
    @RequestMapping(value = "/getIcon", method = RequestMethod.POST)
    public String Delmessges(@RequestBody String params)  {
        JSONObject rJsonObject = new JSONObject();
        SimpleDateFormat sDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String datatime = sDateFormat.format(new Date());
        JSONObject jsonObject = JSONObject.parseObject(params);
        jsonObject=JSONObject.parseObject(jsonObject.getString("param"));
        try {
        String iconThem = jsonObject.getString("iconThem");//图标主题
        Map<String,Object> map=new HashMap<>();
        map.put("iconThem=",iconThem);
            List<HashMap<String, Object>> list=icommon.findlist("frame_icon","*",map,"","",0,20);
            rJsonObject.put("code", "200");//插入成功
            rJsonObject.put("result", list);//插入成功
        } catch (Exception e) {
            //插入报错信息
            SMART_ERRORLOG smartErrorlog = new SMART_ERRORLOG();
            smartErrorlog.setGuid(UUID.randomUUID().toString());
            smartErrorlog.setGuid(datatime);
            smartErrorlog.setErrorlog(e.getMessage());
            icommon.insertlog(smartErrorlog);
            rJsonObject.put("code", "400");//插入失败
            rJsonObject.put("error",e.getMessage());//插入失败
        }
        return  rJsonObject.toJSONString();
    }
}
