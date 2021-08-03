package com.lxh.rhjg.rest;
import com.alibaba.fastjson.JSONObject;
import com.lxh.rhjg.active.api.Icommon;
import com.lxh.rhjg.entity.*;
import com.lxh.rhjg.others.api.IOthers;
import com.lxh.rhjg.mall.api.IMall;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;

@RestController
@RequestMapping("/Mall")
public class MallController {
    @Autowired
    Icommon icommon;
    @Autowired
    IMall iMall;
    @Autowired
    IOthers iOthers;
    /*
     *新增需求设置
     */
    @RequestMapping(value = "/AddRequirement", method = RequestMethod.POST)
    public String AddRequirement(@RequestBody String params) throws IOException {
        JSONObject rJsonObject = new JSONObject();
        JSONObject jsonObject = JSONObject.parseObject(params);
        jsonObject=JSONObject.parseObject(jsonObject.getString("param"));
        String userId = jsonObject.get("uid").toString();
        String requirement = jsonObject.get("requirement").toString();
        SimpleDateFormat sDateFormat=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String datetime=sDateFormat.format(new Date());
        String guid=UUID.randomUUID().toString();
        SMART_REQUIREMENT smartRequirement=new SMART_REQUIREMENT();
        smartRequirement.setGuid(guid);
        smartRequirement.setDatetime(datetime);
        smartRequirement.setRequirement(requirement);
        smartRequirement.setUser_id(userId);
        try {
            int n=iMall.insert(smartRequirement);
            if(n==1){
               rJsonObject.put("code","200");//插入成功
             }
            else {
                rJsonObject.put("code","400");
            }
        }catch (Exception e){
            //插入报错信息
            SMART_ERRORLOG smartErrorlog=new SMART_ERRORLOG();
            smartErrorlog.setGuid(UUID.randomUUID().toString());
            smartErrorlog.setDatetime(datetime);
            smartErrorlog.setErrorlog(e.getMessage());
            icommon.insertlog(smartErrorlog);
            rJsonObject.put("code","400");//插入失败
        }
        return rJsonObject.toJSONString();
    }
    /*
     *修改需求设置
     */
    @RequestMapping(value = "/UpRequirement", method = RequestMethod.POST)
    public String UpRequirement(@RequestBody String params) throws IOException {
        JSONObject rJsonObject = new JSONObject();
        JSONObject jsonObject = JSONObject.parseObject(params);
        jsonObject=JSONObject.parseObject(jsonObject.getString("param"));
        String guid = jsonObject.get("guid").toString();
        String userId = jsonObject.get("uid").toString();
        String requirement = jsonObject.get("requirement").toString();
        SMART_REQUIREMENT smartRequirement=new SMART_REQUIREMENT();
        smartRequirement.setGuid(guid);
        smartRequirement.setRequirement(requirement);
        smartRequirement.setUser_id(userId);
        SimpleDateFormat sDateFormat=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String datetime=sDateFormat.format(new Date());
        try {
            int n=iMall.update(smartRequirement);
            if(n==1){
                rJsonObject.put("code","200");//插入成功
            }
            else {
                rJsonObject.put("code","400");
            }
        }catch (Exception e){
            //插入报错信息
            SMART_ERRORLOG smartErrorlog=new SMART_ERRORLOG();
            smartErrorlog.setGuid(UUID.randomUUID().toString());
            smartErrorlog.setGuid(datetime);
            smartErrorlog.setErrorlog(e.getMessage());
            icommon.insertlog(smartErrorlog);
            rJsonObject.put("code","400");//插入失败
        }
        return rJsonObject.toJSONString();
    }
    /*
     *获取需求设置
     */
    @RequestMapping(value = "/GetRequirement", method = RequestMethod.POST)
    public String GetRequirement(@RequestBody String params) throws IOException {
        JSONObject rJsonObject = new JSONObject();
        JSONObject jsonObject = JSONObject.parseObject(params);
        jsonObject=JSONObject.parseObject(jsonObject.getString("param"));
        String userId = jsonObject.get("uid").toString();
        SimpleDateFormat sDateFormat=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String datetime=sDateFormat.format(new Date());
        try {
            Map<String,Object> map=new HashMap<>();
            map.put("user_Id=",userId);
            List<HashMap<String, Object>> list=icommon.findlist("smart_requirement","*",map,"","");
            rJsonObject.put("code","200");//获取成功
            rJsonObject.put("result",list);//获取值
        }catch (Exception e){
            //插入报错信息
            SMART_ERRORLOG smartErrorlog=new SMART_ERRORLOG();
            smartErrorlog.setGuid(UUID.randomUUID().toString());
            smartErrorlog.setGuid(datetime);
            smartErrorlog.setErrorlog(e.getMessage());
            icommon.insertlog(smartErrorlog);
            rJsonObject.put("code","400");//插入失败
        }
        return rJsonObject.toJSONString();
    }
    /*
     *新增商家基本信息
     */
    @RequestMapping(value = "/AddMallBase", method = RequestMethod.POST)
    public String AddMallBase(@RequestBody String params) throws IOException {
        JSONObject rJsonObject = new JSONObject();
        JSONObject jsonObject = JSONObject.parseObject(params);
        jsonObject=JSONObject.parseObject(jsonObject.getString("param"));
        String userId = jsonObject.get("uid").toString();
        String mall_type = jsonObject.get("mall_type").toString();
        String mall_addr = jsonObject.get("mall_addr").toString();
        String mall_img = jsonObject.get("mall_img").toString();
        String mall_desc = jsonObject.get("mall_desc").toString();
        String mall_mark = jsonObject.get("mall_mark").toString();
        SimpleDateFormat sDateFormat=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String datetime=sDateFormat.format(new Date());
        String guid=UUID.randomUUID().toString();
        SMART_MALL_BASE smart_mall_base=new SMART_MALL_BASE();
        smart_mall_base.setGuid(guid);
        smart_mall_base.setDateTime(datetime);
        smart_mall_base.setMall_Addr(mall_addr);
        smart_mall_base.setMall_Desc(mall_desc);
        smart_mall_base.setMall_Type(mall_type);
        smart_mall_base.setMall_Mark(mall_mark);
        smart_mall_base.setMall_Img(mall_img);
        smart_mall_base.setUser_Id(userId);
        try {
           int n= iMall.insertmallbase(smart_mall_base);
            if(n==1){
                rJsonObject.put("code","200");//插入成功
            }
            else {
                rJsonObject.put("code","400");
            }
        }catch (Exception e){
            //插入报错信息
            SMART_ERRORLOG smartErrorlog=new SMART_ERRORLOG();
            smartErrorlog.setGuid(UUID.randomUUID().toString());
            smartErrorlog.setGuid(datetime);
            smartErrorlog.setErrorlog(e.getMessage());
            icommon.insertlog(smartErrorlog);
            rJsonObject.put("code","400");//插入失败
        }
        return rJsonObject.toJSONString();
    }
    /*
     *修改商家基本信息
     */
    @RequestMapping(value = "/UpMallBase", method = RequestMethod.POST)
    public String UpMallBase(@RequestBody String params) throws IOException {
        JSONObject rJsonObject = new JSONObject();
        JSONObject jsonObject = JSONObject.parseObject(params);
        jsonObject=JSONObject.parseObject(jsonObject.getString("param"));
        String guid = jsonObject.get("guid").toString();
        String userId = jsonObject.get("uid").toString();
        String mall_type = jsonObject.get("mall_type").toString();
        String mall_addr = jsonObject.get("mall_addr").toString();
        String mall_img = jsonObject.get("mall_img").toString();
        String mall_desc = jsonObject.get("mall_desc").toString();
        String mall_mark = jsonObject.get("mall_mark").toString();
        SMART_MALL_BASE smart_mall_base=new SMART_MALL_BASE();
        smart_mall_base.setGuid(guid);
        smart_mall_base.setMall_Addr(mall_addr);
        smart_mall_base.setMall_Desc(mall_desc);
        smart_mall_base.setMall_Type(mall_type);
        smart_mall_base.setMall_Mark(mall_mark);
        smart_mall_base.setMall_Img(mall_img);
        smart_mall_base.setUser_Id(userId);
        SimpleDateFormat sDateFormat=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String datetime=sDateFormat.format(new Date());
        try {
            int n=iMall.updatemallbase(smart_mall_base);
            if(n==1){
                rJsonObject.put("code","200");//插入成功
            }
            else {
                rJsonObject.put("code","400");
            }
        }catch (Exception e){
            //插入报错信息
            SMART_ERRORLOG smartErrorlog=new SMART_ERRORLOG();
            smartErrorlog.setGuid(UUID.randomUUID().toString());
            smartErrorlog.setGuid(datetime);
            smartErrorlog.setErrorlog(e.getMessage());
            icommon.insertlog(smartErrorlog);
            rJsonObject.put("code","400");//插入失败
        }
        return rJsonObject.toJSONString();
    }
    /*
     *获取商家基本信息
     */
    @RequestMapping(value = "/GetMallBase", method = RequestMethod.POST)
    public String GetMallBase(@RequestBody String params) throws IOException {
        JSONObject rJsonObject = new JSONObject();
        JSONObject jsonObject = JSONObject.parseObject(params);
        jsonObject=JSONObject.parseObject(jsonObject.getString("param"));
        String userId = jsonObject.get("uid").toString();
        SimpleDateFormat sDateFormat=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String datetime=sDateFormat.format(new Date());
        try {
            Map<String,Object> map=new HashMap<>();
            map.put("user_Id=",userId);
            List<HashMap<String, Object>> list=icommon.findlist("smart_mall_base","*",map,"","");
            rJsonObject.put("code","200");//获取成功
            rJsonObject.put("result",list);//获取值
        }catch (Exception e){
            //插入报错信息
            SMART_ERRORLOG smartErrorlog=new SMART_ERRORLOG();
            smartErrorlog.setGuid(UUID.randomUUID().toString());
            smartErrorlog.setGuid(datetime);
            smartErrorlog.setErrorlog(e.getMessage());
            icommon.insertlog(smartErrorlog);
            rJsonObject.put("code","400");//插入失败
        }
        return rJsonObject.toJSONString();
    }
    /*
     *新增商家案例
     */
    @RequestMapping(value = "/AddMallCase", method = RequestMethod.POST)
    public String AddMallCase(@RequestBody String params) throws IOException {
        JSONObject rJsonObject = new JSONObject();
        JSONObject jsonObject = JSONObject.parseObject(params);
        jsonObject=JSONObject.parseObject(jsonObject.getString("param"));
        String userId = jsonObject.get("uid").toString();
        String case_Name = jsonObject.get("case_Name").toString();
        String case_Price = jsonObject.get("case_Price").toString();
        String case_Detail = jsonObject.get("case_Detail").toString();
        int case_Scan =Integer.parseInt(jsonObject.get("case_Scan").toString());
        SimpleDateFormat sDateFormat=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String datetime=sDateFormat.format(new Date());
        String guid=UUID.randomUUID().toString();
        SMART_MALL_CASE smart_mall_case=new SMART_MALL_CASE();
        smart_mall_case.setGuid(guid);
        smart_mall_case.setDateTime(datetime);
        smart_mall_case.setCase_Name(case_Name);
        smart_mall_case.setCase_Price(case_Price);
        smart_mall_case.setCase_Detail(case_Detail);
        smart_mall_case.setCase_Scan(case_Scan);
        smart_mall_case.setUser_Id(userId);
        try {
            int n=iMall.insertmallcase(smart_mall_case);
            if(n==1){
                rJsonObject.put("code","200");//插入成功
            }
            else {
                rJsonObject.put("code","400");
            }
        }catch (Exception e){
            //插入报错信息
            SMART_ERRORLOG smartErrorlog=new SMART_ERRORLOG();
            smartErrorlog.setGuid(UUID.randomUUID().toString());
            smartErrorlog.setGuid(datetime);
            smartErrorlog.setErrorlog(e.getMessage());
            icommon.insertlog(smartErrorlog);
            rJsonObject.put("code","400");//插入失败
        }
        return rJsonObject.toJSONString();
    }
    /*
     *修改商家案例
     */
    @RequestMapping(value = "/UpMallCase", method = RequestMethod.POST)
    public String UpMallCase(@RequestBody String params) throws IOException {
        JSONObject rJsonObject = new JSONObject();
        JSONObject jsonObject = JSONObject.parseObject(params);
        jsonObject=JSONObject.parseObject(jsonObject.getString("param"));
        String userId = jsonObject.get("uid").toString();
        String guid = jsonObject.get("guid").toString();
        String case_Name = jsonObject.get("case_Name").toString();
        String case_Price = jsonObject.get("case_Price").toString();
        String case_Detail = jsonObject.get("case_Detail").toString();
        int case_Scan =Integer.parseInt(jsonObject.get("case_Scan").toString());
        SMART_MALL_CASE smart_mall_case=new SMART_MALL_CASE();
        smart_mall_case.setCase_Name(case_Name);
        smart_mall_case.setCase_Price(case_Price);
        smart_mall_case.setCase_Detail(case_Detail);
        smart_mall_case.setCase_Scan(case_Scan);
        smart_mall_case.setUser_Id(userId);
        smart_mall_case.setGuid(guid);
        SimpleDateFormat sDateFormat=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String datetime=sDateFormat.format(new Date());
        try {
            int n=iMall.updatemallcase(smart_mall_case);
            if(n==1){
                rJsonObject.put("code","200");//插入成功
            }
            else {
                rJsonObject.put("code","400");
            }
        }catch (Exception e){
            //插入报错信息
            SMART_ERRORLOG smartErrorlog=new SMART_ERRORLOG();
            smartErrorlog.setGuid(UUID.randomUUID().toString());
            smartErrorlog.setGuid(datetime);
            smartErrorlog.setErrorlog(e.getMessage());
            icommon.insertlog(smartErrorlog);
            rJsonObject.put("code","400");//插入失败
        }
        return rJsonObject.toJSONString();
    }
    /*
     *获取商家案例
     */
    @RequestMapping(value = "/GetMallCase", method = RequestMethod.POST)
    public String MallCase(@RequestBody String params) throws IOException {
        JSONObject rJsonObject = new JSONObject();
        JSONObject jsonObject = JSONObject.parseObject(params);
        jsonObject=JSONObject.parseObject(jsonObject.getString("param"));
        String userId = jsonObject.get("uid").toString();
        SimpleDateFormat sDateFormat=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String datetime=sDateFormat.format(new Date());
        try {
            Map<String,Object> map=new HashMap<>();
            map.put("user_Id=",userId);
            List<HashMap<String, Object>> list=icommon.findlist("smart_mall_case","*",map,"","");
            rJsonObject.put("code","200");//获取成功
            rJsonObject.put("result",list);//获取值
        }catch (Exception e){
            //插入报错信息
            SMART_ERRORLOG smartErrorlog=new SMART_ERRORLOG();
            smartErrorlog.setGuid(UUID.randomUUID().toString());
            smartErrorlog.setGuid(datetime);
            smartErrorlog.setErrorlog(e.getMessage());
            icommon.insertlog(smartErrorlog);
            rJsonObject.put("code","400");//插入失败
        }
        return rJsonObject.toJSONString();
    }
    /*
     *新增商家案例图
     */
    @RequestMapping(value = "/AddMallPhoto", method = RequestMethod.POST)
    public String AddMallPhoto(@RequestBody String params) throws IOException {
        JSONObject rJsonObject = new JSONObject();
        JSONObject jsonObject = JSONObject.parseObject(params);
        jsonObject=JSONObject.parseObject(jsonObject.getString("param"));
        String userId = jsonObject.get("uid").toString();
        String img_Path = jsonObject.get("img_Path").toString();
        String item_Type = jsonObject.get("item_Type").toString();
        int order =  Integer.parseInt(jsonObject.get("order").toString());
        SimpleDateFormat sDateFormat=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String datetime=sDateFormat.format(new Date());
        String guid=UUID.randomUUID().toString();
        String forn_Guid=UUID.randomUUID().toString();
        SMART_MALL_PHOTO smart_mall_photo=new SMART_MALL_PHOTO();
        smart_mall_photo.setGuid(guid);
        smart_mall_photo.setForn_Guid(forn_Guid);
        smart_mall_photo.setDateTime(datetime);
        smart_mall_photo.setUser_Id(userId);
        smart_mall_photo.setImg_Path(img_Path);
        smart_mall_photo.setItem_Type(item_Type);
        smart_mall_photo.setOrder(order);

        try {
            int n=iMall.insertmallphoto(smart_mall_photo);
            if(n==1){
                rJsonObject.put("code","200");//插入成功
            }
            else {
                rJsonObject.put("code","400");
            }
        }catch (Exception e){
            //插入报错信息
            SMART_ERRORLOG smartErrorlog=new SMART_ERRORLOG();
            smartErrorlog.setGuid(UUID.randomUUID().toString());
            smartErrorlog.setGuid(datetime);
            smartErrorlog.setErrorlog(e.getMessage());
            icommon.insertlog(smartErrorlog);
            rJsonObject.put("code","400");//插入失败
        }
        return rJsonObject.toJSONString();
    }
    /*
     *修改商家案例图
     */
    @RequestMapping(value = "/UpMallPhoto", method = RequestMethod.POST)
    public String UpMallPhoto(@RequestBody String params) throws IOException {
        JSONObject rJsonObject = new JSONObject();
        JSONObject jsonObject = JSONObject.parseObject(params);
        jsonObject=JSONObject.parseObject(jsonObject.getString("param"));
        String userId = jsonObject.get("uid").toString();
        String guid = jsonObject.get("guid").toString();
        String img_Path = jsonObject.get("img_Path").toString();
        String item_Type = jsonObject.get("item_Type").toString();
        int order =  Integer.parseInt(jsonObject.get("order").toString());
        SimpleDateFormat sDateFormat=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String datetime=sDateFormat.format(new Date());
        SMART_MALL_PHOTO smart_mall_photo=new SMART_MALL_PHOTO();
        smart_mall_photo.setGuid(guid);
        smart_mall_photo.setUser_Id(userId);
        smart_mall_photo.setImg_Path(img_Path);
        smart_mall_photo.setItem_Type(item_Type);
        smart_mall_photo.setOrder(order);
        try {
            int n=iMall.updatemallphoto(smart_mall_photo);
            if(n==1){
                rJsonObject.put("code","200");//插入成功
            }
            else {
                rJsonObject.put("code","400");
            }
        }catch (Exception e){
            //插入报错信息
            SMART_ERRORLOG smartErrorlog=new SMART_ERRORLOG();
            smartErrorlog.setGuid(UUID.randomUUID().toString());
            smartErrorlog.setGuid(datetime);
            smartErrorlog.setErrorlog(e.getMessage());
            icommon.insertlog(smartErrorlog);
            rJsonObject.put("code","400");//插入失败
        }
        return rJsonObject.toJSONString();
    }
    /*
     *获取商家案例
     */
    @RequestMapping(value = "/GetMallPhoto", method = RequestMethod.POST)
    public String GetMallPhoto(@RequestBody String params) throws IOException {
        JSONObject rJsonObject = new JSONObject();
        JSONObject jsonObject = JSONObject.parseObject(params);
        jsonObject=JSONObject.parseObject(jsonObject.getString("param"));
        String userId = jsonObject.get("uid").toString();
        SimpleDateFormat sDateFormat=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String datetime=sDateFormat.format(new Date());
        try {
            Map<String,Object> map=new HashMap<>();
            map.put("user_Id=",userId);
            List<HashMap<String, Object>> list=icommon.findlist("smart_mall_photo","*",map,"","");
            rJsonObject.put("code","200");//获取成功
            rJsonObject.put("result",list);//获取值
        }catch (Exception e){
            //插入报错信息
            SMART_ERRORLOG smartErrorlog=new SMART_ERRORLOG();
            smartErrorlog.setGuid(UUID.randomUUID().toString());
            smartErrorlog.setGuid(datetime);
            smartErrorlog.setErrorlog(e.getMessage());
            icommon.insertlog(smartErrorlog);
            rJsonObject.put("code","400");//插入失败
        }
        return rJsonObject.toJSONString();
    }

    /*
     *新增商家案例图
     */
    @RequestMapping(value = "/AddMallOrg", method = RequestMethod.POST)
    public String AddMallOrg(@RequestBody String params) throws IOException {
        JSONObject rJsonObject = new JSONObject();
        JSONObject jsonObject = JSONObject.parseObject(params);
        jsonObject=JSONObject.parseObject(jsonObject.getString("param"));
        String userId = jsonObject.get("uid").toString();
        String aboutUS = jsonObject.get("aboutUS").toString();
        String comanyName = jsonObject.get("comanyName").toString();
        String createDate = jsonObject.get("createDate").toString();
        String comanySize = jsonObject.get("comanySize").toString();
        String comanyAddress = jsonObject.get("comanyAddress").toString();
        String comanyQualify = jsonObject.get("comanyQualify").toString();
        SimpleDateFormat sDateFormat=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String datetime=sDateFormat.format(new Date());
        String guid=UUID.randomUUID().toString();
        SMART_MALL_ORG smart_mall_org=new SMART_MALL_ORG();
        smart_mall_org.setGuid(guid);
        smart_mall_org.setUser_ID(userId);
        smart_mall_org.setAboutUS(aboutUS);
        smart_mall_org.setComanyName(comanyName);
        smart_mall_org.setCreateDate(createDate);
        smart_mall_org.setComanySize(comanySize);
        smart_mall_org.setComanyAddress(comanyAddress);
        smart_mall_org.setComanyQualify(comanyQualify);
        try {
            int n=iMall.insertmallorg(smart_mall_org);
            if(n==1){
                rJsonObject.put("code","200");//插入成功
            }
            else {
                rJsonObject.put("code","400");
            }
        }catch (Exception e){
            //插入报错信息
            SMART_ERRORLOG smartErrorlog=new SMART_ERRORLOG();
            smartErrorlog.setGuid(UUID.randomUUID().toString());
            smartErrorlog.setGuid(datetime);
            smartErrorlog.setErrorlog(e.getMessage());
            icommon.insertlog(smartErrorlog);
            rJsonObject.put("code","400");//插入失败
        }
        return rJsonObject.toJSONString();
    }
    /*
     *修改商家案例图
     */
    @RequestMapping(value = "/UpMallOrg", method = RequestMethod.POST)
    public String UpMallOrg(@RequestBody String params) throws IOException {
        JSONObject rJsonObject = new JSONObject();
        JSONObject jsonObject = JSONObject.parseObject(params);
        jsonObject=JSONObject.parseObject(jsonObject.getString("param"));
        String userId = jsonObject.get("uid").toString();
        String aboutUS = jsonObject.get("aboutUS").toString();
        String comanyName = jsonObject.get("comanyName").toString();
        String createDate = jsonObject.get("createDate").toString();
        String comanySize = jsonObject.get("comanySize").toString();
        String comanyAddress = jsonObject.get("comanyAddress").toString();
        String comanyQualify = jsonObject.get("comanyQualify").toString();
        String guid=jsonObject.get("guid").toString();
        SMART_MALL_ORG smart_mall_org=new SMART_MALL_ORG();
        smart_mall_org.setGuid(guid);
        smart_mall_org.setUser_ID(userId);
        smart_mall_org.setAboutUS(aboutUS);
        smart_mall_org.setComanyName(comanyName);
        smart_mall_org.setCreateDate(createDate);
        smart_mall_org.setComanySize(comanySize);
        smart_mall_org.setComanyAddress(comanyAddress);
        smart_mall_org.setComanyQualify(comanyQualify);

        SimpleDateFormat sDateFormat=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String datetime=sDateFormat.format(new Date());
        try {
            int n=iMall.updatemallorg(smart_mall_org);
            if(n==1){
                rJsonObject.put("code","200");//插入成功
            }
            else {
                rJsonObject.put("code","400");
            }
        }catch (Exception e){
            //插入报错信息
            SMART_ERRORLOG smartErrorlog=new SMART_ERRORLOG();
            smartErrorlog.setGuid(UUID.randomUUID().toString());
            smartErrorlog.setGuid(datetime);
            smartErrorlog.setErrorlog(e.getMessage());
            icommon.insertlog(smartErrorlog);
            rJsonObject.put("code","400");//插入失败
        }
        return rJsonObject.toJSONString();
    }
    /*
     *获取商家案例
     */
    @RequestMapping(value = "/GetMallOrg", method = RequestMethod.POST)
    public String GetMallOrg(@RequestBody String params) throws IOException {
        JSONObject rJsonObject = new JSONObject();
        JSONObject jsonObject = JSONObject.parseObject(params);
        jsonObject=JSONObject.parseObject(jsonObject.getString("param"));
        String userId = jsonObject.get("uid").toString();
        SimpleDateFormat sDateFormat=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String datetime=sDateFormat.format(new Date());
        try {
            Map<String,Object> map=new HashMap<>();
            map.put("user_Id=",userId);
            List<HashMap<String, Object>> list=icommon.findlist("SMART_MALL_ORG","*",map,"","");
            rJsonObject.put("code","200");//获取成功
            rJsonObject.put("result",list);//获取值
        }catch (Exception e){
            //插入报错信息
            SMART_ERRORLOG smartErrorlog=new SMART_ERRORLOG();
            smartErrorlog.setGuid(UUID.randomUUID().toString());
            smartErrorlog.setGuid(datetime);
            smartErrorlog.setErrorlog(e.getMessage());
            icommon.insertlog(smartErrorlog);
            rJsonObject.put("code","400");//插入失败
        }
        return rJsonObject.toJSONString();
    }
}
