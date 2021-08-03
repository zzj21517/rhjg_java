package com.lxh.newrhjg.rest;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.lxh.newrhjg.api.*;
import com.lxh.newrhjg.entity.*;
import com.lxh.rhjg.active.api.IPeople;
import com.lxh.rhjg.active.api.IProject;
import com.lxh.rhjg.active.api.Icommon;
import com.lxh.rhjg.common.util.MD5Utils;
import com.lxh.rhjg.entity.SMART_ERRORLOG;
import com.lxh.rhjg.entity.SMART_MALL_CASE;
import com.lxh.rhjg.mall.api.IMall;
import com.lxh.rhjg.verifycode.api.IVerifycode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;

@RestController
@RequestMapping("/peopleextentinfo")
public class PeoplesExtentController {
    @Autowired
    IframeCollect iframeCollect;
    @Autowired
    Icommon icommon;
    @Autowired
    Inewcommon inewcommon;
    @Autowired
    IframeFollow iframeFollow;
    @Autowired
    IMall iMall;
    @Autowired
    IframePeopleEval iframePeopleEval;
    @Autowired
    IframePeople iframePeople;
    @Autowired
    IProductPrice productPrice;
    /*
     * 新增收藏
     */
    @RequestMapping(value = "/addCollect", method = RequestMethod.POST)
    public String addCollect(@RequestBody String params)  {
        JSONObject rJsonObject = new JSONObject();
        JSONObject jsonObject = JSONObject.parseObject(params);
        jsonObject = JSONObject.parseObject(jsonObject.getString("param"));
        String rowGuid = UUID.randomUUID().toString();
        SimpleDateFormat sDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String datatime = sDateFormat.format(new Date());
        String userguid = jsonObject.get("userGuid").toString();
        String storeGuid = jsonObject.get("storeGuid").toString();
        FrameCollect frameCollect;
        try {
            Map<String,Object> map=new HashMap<>();
            frameCollect = new FrameCollect();
            frameCollect.setRowGuid(rowGuid);
            frameCollect.setUserGuid(userguid);
            frameCollect.setStoreGuid(storeGuid);
            frameCollect.setEvalTime(datatime);
            iframeCollect.insert(frameCollect);
            rJsonObject.put("code", "200");//插入失败
        } catch (Exception e) {
            //插入报错信息
            SMART_ERRORLOG smartErrorlog = new SMART_ERRORLOG();
            smartErrorlog.setGuid(UUID.randomUUID().toString());
            smartErrorlog.setGuid(datatime);
            smartErrorlog.setErrorlog(e.getMessage());
            icommon.insertlog(smartErrorlog);
            rJsonObject.put("code", "400");//插入失败
        }
        return rJsonObject.toJSONString();
    }

    //删除收藏
    @RequestMapping(value = "/delCollect", method = RequestMethod.POST)
    public String delCollect(@RequestBody String params) {
        JSONObject rJsonObject = new JSONObject();
        JSONObject jsonObject = JSONObject.parseObject(params);
        jsonObject = JSONObject.parseObject(jsonObject.getString("param"));
        SimpleDateFormat sDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String datatime = sDateFormat.format(new Date());
        String rowGuid = jsonObject.get("rowGuid").toString();
        try {
            FrameCollect frameCollect = new FrameCollect();
            frameCollect.setRowGuid(rowGuid);
            iframeCollect.delete(frameCollect);
            rJsonObject.put("code", "200");
        } catch (Exception e) {
            //插入报错信息
            SMART_ERRORLOG smartErrorlog = new SMART_ERRORLOG();
            smartErrorlog.setGuid(UUID.randomUUID().toString());
            smartErrorlog.setGuid(datatime);
            smartErrorlog.setErrorlog(e.getMessage());
            icommon.insertlog(smartErrorlog);
            rJsonObject.put("code", "400");//插入失败
        }
        return rJsonObject.toJSONString();
    }

    /*
     * 获取收藏
     */
    @RequestMapping(value = "/getCollect", method = RequestMethod.POST)
    public String getCollect(@RequestBody String params) {
        JSONObject rJsonObject = new JSONObject();
        JSONObject jsonObject = JSONObject.parseObject(params);
        jsonObject = JSONObject.parseObject(jsonObject.getString("param"));

        SimpleDateFormat sDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String datatime = sDateFormat.format(new Date());
        String userguid = jsonObject.get("userGuid").toString();
        int pagesize = Integer.parseInt(jsonObject.get("pagesize").toString());
        int pagenum = Integer.parseInt(jsonObject.get("pagenum").toString());
        try {
            Map<String, Object> map = new HashMap<>();
            map.put("a.userguid=", userguid);
            //获取个人偏好
            String condition=" and a.storeGuid=b.rowguid ";
            List<HashMap<String, Object>> list = inewcommon.findlist("frame_collect a,frame_people_goodat b", "a.*,b.productname", map, condition, "", pagenum, pagesize);
            int count= inewcommon.findlist("frame_collect a,frame_people_goodat b",map,condition);
            rJsonObject.put("code", "200");
            rJsonObject.put("result", list);
            rJsonObject.put("count", count);
        } catch (Exception e) {
            //插入报错信息
            SMART_ERRORLOG smartErrorlog = new SMART_ERRORLOG();
            smartErrorlog.setGuid(UUID.randomUUID().toString());
            smartErrorlog.setGuid(datatime);
            smartErrorlog.setErrorlog(e.getMessage());
            icommon.insertlog(smartErrorlog);
            rJsonObject.put("code", "400");//插入失败
        }
        return rJsonObject.toJSONString();
    }


    /*
     * 新增关注
     */
    @RequestMapping(value = "/addFollow", method = RequestMethod.POST)
    public String addFollow(@RequestBody String params)  {
        JSONObject rJsonObject = new JSONObject();
        JSONObject jsonObject = JSONObject.parseObject(params);
        jsonObject = JSONObject.parseObject(jsonObject.getString("param"));
        String rowGuid = UUID.randomUUID().toString();
        SimpleDateFormat sDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String datatime = sDateFormat.format(new Date());
        String userguid = jsonObject.get("userGuid").toString();
        String storeGuid = jsonObject.get("storeGuid").toString();
        FrameFollow frameFollow;
        try {
            Map<String,Object> map=new HashMap<>();
            frameFollow = new FrameFollow();
            frameFollow.setRowGuid(rowGuid);
            frameFollow.setUserGuid(userguid);
            frameFollow.setStoreGuid(storeGuid);
            frameFollow.setFollowTime(datatime);
            iframeFollow.insert(frameFollow);
            rJsonObject.put("code", "200");//插入失败
        } catch (Exception e) {
            //插入报错信息
            SMART_ERRORLOG smartErrorlog = new SMART_ERRORLOG();
            smartErrorlog.setGuid(UUID.randomUUID().toString());
            smartErrorlog.setGuid(datatime);
            smartErrorlog.setErrorlog(e.getMessage());
            icommon.insertlog(smartErrorlog);
            rJsonObject.put("code", "400");//插入失败
        }
        return rJsonObject.toJSONString();
    }

    //删除关注
    @RequestMapping(value = "/delFollow", method = RequestMethod.POST)
    public String delFollow(@RequestBody String params) {
        JSONObject rJsonObject = new JSONObject();
        JSONObject jsonObject = JSONObject.parseObject(params);
        jsonObject = JSONObject.parseObject(jsonObject.getString("param"));
        SimpleDateFormat sDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String datatime = sDateFormat.format(new Date());
        String rowGuid = jsonObject.get("rowGuid").toString();
        try {
            FrameFollow frameFollow = new FrameFollow();
            frameFollow.setRowGuid(rowGuid);
            iframeFollow.delete(frameFollow);
            rJsonObject.put("code", "200");
        } catch (Exception e) {
            //插入报错信息
            SMART_ERRORLOG smartErrorlog = new SMART_ERRORLOG();
            smartErrorlog.setGuid(UUID.randomUUID().toString());
            smartErrorlog.setGuid(datatime);
            smartErrorlog.setErrorlog(e.getMessage());
            icommon.insertlog(smartErrorlog);
            rJsonObject.put("code", "400");//插入失败
        }
        return rJsonObject.toJSONString();
    }

    /*
     * 获取关注
     */
    @RequestMapping(value = "/getFollow", method = RequestMethod.POST)
    public String getFollow(@RequestBody String params) {
        JSONObject rJsonObject = new JSONObject();
        JSONObject jsonObject = JSONObject.parseObject(params);
        jsonObject = JSONObject.parseObject(jsonObject.getString("param"));

        SimpleDateFormat sDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String datatime = sDateFormat.format(new Date());
        String userguid = jsonObject.get("userGuid").toString();
        int pagesize = Integer.parseInt(jsonObject.get("pagesize").toString());
        int pagenum = Integer.parseInt(jsonObject.get("pagenum").toString());
        try {
            Map<String, Object> map = new HashMap<>();
            map.put("userguid=", userguid);
          String   conditon=" and  a.storeGuid=b.rowguid";
            //获取个人偏好
            List<HashMap<String, Object>> list = inewcommon.findlist("frame_follow a,frame_people b ", "a.*,b.nickName", map, conditon, "", pagenum, pagesize);
            int count= inewcommon.findlist("frame_follow a,frame_people b  ",map,conditon);
            rJsonObject.put("code", "200");
            rJsonObject.put("count", count);
            rJsonObject.put("result", list);
        } catch (Exception e) {
            //插入报错信息
            SMART_ERRORLOG smartErrorlog = new SMART_ERRORLOG();
            smartErrorlog.setGuid(UUID.randomUUID().toString());
            smartErrorlog.setGuid(datatime);
            smartErrorlog.setErrorlog(e.getMessage());
            icommon.insertlog(smartErrorlog);
            rJsonObject.put("code", "400");//插入失败
        }
        return rJsonObject.toJSONString();
    }
    /*
     * 获取所有偏好
     */
    @RequestMapping(value = "/getCode", method = RequestMethod.POST)
    public String getCode(@RequestBody String params){
        JSONObject rJsonObject = new JSONObject();
       JSONObject jsonObject = JSONObject.parseObject(params);
        jsonObject = JSONObject.parseObject(jsonObject.getString("param"));
        String type = jsonObject.get("type").toString();
        String codeid = jsonObject.get("codeid").toString();
        try {
            List<HashMap<String, Object>> result = new ArrayList<>();
            Map<String, Object> map = new HashMap<>();
            String condition="";
            switch (type){
                case "1"://一级
                    condition+=" and LENGTH(itemvalue)=3";
                    break;
                case "2"://二级
                    condition+=" and LENGTH(itemvalue)<=5";
                    break;
                case "3"://三级
                    condition+=" and LENGTH(itemvalue)<=8";
                    break;
                default:
                    condition+=" and LENGTH(itemvalue)<=5";
                  break;
            }
            if(!"".equals(codeid)){
                condition+=" and codeid='"+codeid+"'";
            }
            result = inewcommon.findlist("frame_item", "codeid,itemtext,itemvalue", map, condition, " order by ordernum desc", 0, 100);
            rJsonObject.put("code", "200");
            rJsonObject.put("result", result);
        } catch (Exception e) {
            rJsonObject.put("code", "400");
        }
        return rJsonObject.toJSONString();
    }

    /*
     * 获取子节点
     */
    @RequestMapping(value = "/getChildrenCode", method = RequestMethod.POST)
    public String getChildrenCode(@RequestBody String params){
        JSONObject rJsonObject = new JSONObject();
        JSONObject jsonObject = JSONObject.parseObject(params);
        jsonObject = JSONObject.parseObject(jsonObject.getString("param"));
        String itemvalue = jsonObject.get("itemvalue").toString();
        try {
            List<HashMap<String, Object>> result = new ArrayList<>();
            Map<String, Object> map = new HashMap<>();
            String condition="";
            condition=" and itemvalue like '"+itemvalue+"%' ";
            int lenght=itemvalue.length();
            switch (lenght){
                case 4:
                    condition+="and LENGTH(itemvalue)=5";
                    break;
                case 5:
                    condition+="and LENGTH(itemvalue)=8";
                    break;
                case 8:
                    break;
            }
            result = inewcommon.findlist("frame_item", "codeid,itemtext,itemvalue", map, condition, " order by ordernum desc", 0, 100);
            rJsonObject.put("code", "200");
            rJsonObject.put("result", result);
        } catch (Exception e) {
            rJsonObject.put("code", "400");
        }
        return rJsonObject.toJSONString();
    }
    /*
     * 获取子节点
     */
    @RequestMapping(value = "/getitem", method = RequestMethod.POST)
    public String getItem(@RequestBody String params){
        JSONObject rJsonObject = new JSONObject();
        JSONObject jsonObject = JSONObject.parseObject(params);
        jsonObject = JSONObject.parseObject(jsonObject.getString("param"));
        String codeid = jsonObject.get("codeid").toString();
        try {
            List<HashMap<String, Object>> result = new ArrayList<>();
            Map<String, Object> map = new HashMap<>();
            String condition="";
            condition=" and codeid='"+codeid+"'";
            result = inewcommon.findlist("frame_item", "codeid,itemtext,itemvalue", map, condition, " order by ordernum desc", 0, 100);
            rJsonObject.put("code", "200");
            rJsonObject.put("result", result);
        } catch (Exception e) {
            rJsonObject.put("code", "400");
        }
        return rJsonObject.toJSONString();
    }

    /*
     * 获取子节点
     */
    @RequestMapping(value = "/gethotitem", method = RequestMethod.POST)
    public String gethotitem(@RequestBody String params){
        JSONObject rJsonObject = new JSONObject();
        JSONObject jsonObject = JSONObject.parseObject(params);
        jsonObject = JSONObject.parseObject(jsonObject.getString("param"));
        String codeid = jsonObject.get("codeid").toString();
        try {
            List<HashMap<String, Object>> result = new ArrayList<>();
            Map<String, Object> map = new HashMap<>();
            String condition="";
            condition=" and codeid='"+codeid+"'";
            condition+=" and LENGTH(itemvalue)=3";
            result = inewcommon.findlist("frame_item", "codeid,itemtext,itemvalue", map, condition, " order by ordernum desc", 0, 100);
            JSONArray jsonArray;
            for(HashMap<String,Object>objectHashMap:result){
              jsonArray=new JSONArray();
                condition="";
                condition=" and itemvalue like '"+objectHashMap.get("itemvalue").toString()+"%' ";
                condition+="and LENGTH(itemvalue)=5";
              List<HashMap<String, Object>> list=inewcommon.findlist("frame_item", "codeid,itemtext,itemvalue", map, condition, " order by ordernum desc", 0, 100);
            }
            rJsonObject.put("code", "200");
            rJsonObject.put("result", result);
        } catch (Exception e) {
            rJsonObject.put("code", "400");
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
        String userId = jsonObject.get("productGuid").toString();
        String case_Name = jsonObject.get("case_Name").toString();
        String case_Price = jsonObject.get("case_Price").toString();
        String case_Detail = jsonObject.get("case_Detail").toString();
        String clientGuid = jsonObject.get("clientGuid").toString();
        int case_Scan =0;
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
        smart_mall_case.setClientGuid(clientGuid);
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
/*        String userId = jsonObject.get("userGuid").toString();*/
        String guid = jsonObject.get("rowGuid").toString();
        String case_Name = jsonObject.get("case_Name").toString();
        String case_Price = jsonObject.get("case_Price").toString();
        String case_Detail = jsonObject.get("case_Detail").toString();
       // int case_Scan =Integer.parseInt(jsonObject.get("case_Scan").toString());
        SMART_MALL_CASE smart_mall_case=new SMART_MALL_CASE();
        Map<String,Object> map=new HashMap<>();
         map.put("Guid",guid);
         smart_mall_case=iMall.findCase(map);
       if(smart_mall_case==null){
           rJsonObject.put("code","300");//案例不存在
           return rJsonObject.toJSONString();
       }
        smart_mall_case.setCase_Name(case_Name);
        smart_mall_case.setCase_Price(case_Price);
        smart_mall_case.setCase_Detail(case_Detail);
       // smart_mall_case.setCase_Scan(case_Scan);
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
    @RequestMapping(value = "/getMallCasebyShop", method = RequestMethod.POST)
    public String getMallCasebyShop(@RequestBody String params) throws IOException {
        JSONObject rJsonObject = new JSONObject();
        JSONObject jsonObject = JSONObject.parseObject(params);
        jsonObject=JSONObject.parseObject(jsonObject.getString("param"));
        String userId = jsonObject.get("userGuid").toString();
        int pagesize = Integer.parseInt(jsonObject.get("pagesize").toString());
        int pagenum = Integer.parseInt(jsonObject.get("pagenum").toString());
        SimpleDateFormat sDateFormat=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String datetime=sDateFormat.format(new Date());
        try {
            Map<String, Object> attachmap ;
            List<HashMap<String, Object>> listattach;
            List<HashMap<String, Object>> newlist=new ArrayList<>();
            Map<String,Object> map=new HashMap<>();
             String condition="  and exists (select 1 from frame_people_goodat where frame_people_goodat.userGuid='"+userId+"'" +
                     " and smart_mall_case.User_Id=frame_people_goodat.rowGuid)";
            List<HashMap<String, Object>> list=inewcommon.findlist("smart_mall_case","*",map,condition,"",pagenum, pagesize);
            int count= inewcommon.findlist("smart_mall_case",map,"");
            for(HashMap<String, Object> hashMap:list) {
                attachmap = new HashMap<>();
                attachmap.put("clientGuid=", hashMap.get("clientGuid"));
                listattach = inewcommon.findlist("frame_attachinfo", "*", attachmap, "", "", 0, 20);
                if (listattach.size() > 0) {
                    hashMap.put("filepath", listattach.get(0).get("filepath"));
                } else {
                    hashMap.put("filepath", "../../../../images/other/spt1.jpg");
                }
                newlist.add(hashMap);
            }
            rJsonObject.put("code","200");//获取成功
            rJsonObject.put("count",count);
            rJsonObject.put("result",newlist);//获取值
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
    public String GetMallCase(@RequestBody String params) throws IOException {
        JSONObject rJsonObject = new JSONObject();
        JSONObject jsonObject = JSONObject.parseObject(params);
        jsonObject=JSONObject.parseObject(jsonObject.getString("param"));
        String userId = jsonObject.get("productGuid").toString();
        int pagesize = Integer.parseInt(jsonObject.get("pagesize").toString());
        int pagenum = Integer.parseInt(jsonObject.get("pagenum").toString());
        SimpleDateFormat sDateFormat=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String datetime=sDateFormat.format(new Date());
        try {
            Map<String,Object> map=new HashMap<>();
            map.put("user_Id=",userId);
            List<HashMap<String, Object>> list=inewcommon.findlist("smart_mall_case","*",map,"","",pagenum, pagesize);
            int count= inewcommon.findlist("smart_mall_case",map,"");
            rJsonObject.put("code","200");//获取成功
            rJsonObject.put("count",count);
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
     *获取商家案例详情
     */
    @RequestMapping(value = "/GetMallCaseDetail", method = RequestMethod.POST)
    public String GetMallCaseDetail(@RequestBody String params) throws IOException {
        JSONObject rJsonObject = new JSONObject();
        JSONObject jsonObject = JSONObject.parseObject(params);
        jsonObject=JSONObject.parseObject(jsonObject.getString("param"));
        String rowGuid = jsonObject.get("rowGuid").toString();
        SimpleDateFormat sDateFormat=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String datetime=sDateFormat.format(new Date());
        try {
            Map<String,Object> map=new HashMap<>();
            map.put("Guid",rowGuid);
            SMART_MALL_CASE smart_mall_case=iMall.findCase(map);
            rJsonObject.put("code","200");//获取成功
            rJsonObject.put("result",smart_mall_case);//获取值
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
     *删除商家案例
     */
    @RequestMapping(value = "/delMallCase", method = RequestMethod.POST)
    public String delMallCase(@RequestBody String params) throws IOException {
        JSONObject rJsonObject = new JSONObject();
        JSONObject jsonObject = JSONObject.parseObject(params);
        jsonObject=JSONObject.parseObject(jsonObject.getString("param"));
        String rowGuid = jsonObject.get("rowGuid").toString();
        SimpleDateFormat sDateFormat=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String datetime=sDateFormat.format(new Date());
        try {
            iMall.deletemallcase(rowGuid);
            rJsonObject.put("code","200");//获取成功
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
     * 新增评价
     */
    @RequestMapping(value = "/addEval", method = RequestMethod.POST)
    public String addEval(@RequestBody String params)  {
        JSONObject rJsonObject = new JSONObject();
        JSONObject jsonObject = JSONObject.parseObject(params);
        jsonObject = JSONObject.parseObject(jsonObject.getString("param"));
        String rowGuid = UUID.randomUUID().toString();
        SimpleDateFormat sDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String datatime = sDateFormat.format(new Date());
        String userguid = jsonObject.get("productGuid").toString();
        String projectGuid = jsonObject.get("projectGuid").toString();
        String evalUserGuid = jsonObject.get("evalUserGuid").toString();
        String result = jsonObject.get("result").toString();
        String content = jsonObject.get("content").toString();
        FramePeopleEval framePeopleEval;
        try {
            Map<String,Object> map=new HashMap<>();
            framePeopleEval = new FramePeopleEval();
            framePeopleEval.setRowGuid(rowGuid);
            framePeopleEval.setUserGuid(userguid);
            framePeopleEval.setEvalUserguid(evalUserGuid);
            framePeopleEval.setResult(result);
            framePeopleEval.setEvalTime(datatime);
            framePeopleEval.setEvalContent(content);
            framePeopleEval.setProjectGuid(projectGuid);
            iframePeopleEval.insert(framePeopleEval);
            rJsonObject.put("code", "200");//插入失败
        } catch (Exception e) {
            //插入报错信息
            SMART_ERRORLOG smartErrorlog = new SMART_ERRORLOG();
            smartErrorlog.setGuid(UUID.randomUUID().toString());
            smartErrorlog.setGuid(datatime);
            smartErrorlog.setErrorlog(e.getMessage());
            icommon.insertlog(smartErrorlog);
            rJsonObject.put("code", "400");//插入失败
        }
        return rJsonObject.toJSONString();
    }

    //删除评价
    @RequestMapping(value = "/delEval", method = RequestMethod.POST)
    public String delEval(@RequestBody String params) {
        JSONObject rJsonObject = new JSONObject();
        JSONObject jsonObject = JSONObject.parseObject(params);
        jsonObject = JSONObject.parseObject(jsonObject.getString("param"));
        SimpleDateFormat sDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String datatime = sDateFormat.format(new Date());
        String rowGuid = jsonObject.get("rowGuid").toString();
        try {
            FramePeopleEval framePeopleEval = new FramePeopleEval();
            framePeopleEval.setRowGuid(rowGuid);
            iframePeopleEval.delete(framePeopleEval);
            rJsonObject.put("code", "200");
        } catch (Exception e) {
            //插入报错信息
            SMART_ERRORLOG smartErrorlog = new SMART_ERRORLOG();
            smartErrorlog.setGuid(UUID.randomUUID().toString());
            smartErrorlog.setGuid(datatime);
            smartErrorlog.setErrorlog(e.getMessage());
            icommon.insertlog(smartErrorlog);
            rJsonObject.put("code", "400");//插入失败
        }
        return rJsonObject.toJSONString();
    }
    /*
     *获取商家案例
     */
    @RequestMapping(value = "/getEvalbyProject", method = RequestMethod.POST)
    public String getEvalbyProject(@RequestBody String params) throws IOException {
        JSONObject rJsonObject = new JSONObject();
        JSONObject jsonObject = JSONObject.parseObject(params);
        jsonObject=JSONObject.parseObject(jsonObject.getString("param"));
        String productGuid = jsonObject.get("productGuid").toString();
        SimpleDateFormat sDateFormat=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String datetime=sDateFormat.format(new Date());
        try {
            List<HashMap<String, Object>> newlist=new ArrayList<>();
            Map<String,Object> map=new HashMap<>();
            map.put("projectGuid=",productGuid);
            int count= inewcommon.findlist("frame_people_eval",map,"");
            rJsonObject.put("code","200");//获取成功
            rJsonObject.put("count",count);
            rJsonObject.put("result",newlist);//获取值
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
    @RequestMapping(value = "/getEvalbyShop", method = RequestMethod.POST)
    public String getEvalbyShop(@RequestBody String params) throws IOException {
        JSONObject rJsonObject = new JSONObject();
        JSONObject jsonObject = JSONObject.parseObject(params);
        jsonObject=JSONObject.parseObject(jsonObject.getString("param"));
        String userId = jsonObject.get("userGuid").toString();
        int pagesize = Integer.parseInt(jsonObject.get("pagesize").toString());
        int pagenum = Integer.parseInt(jsonObject.get("pagenum").toString());
        String type = jsonObject.get("type").toString();
        SimpleDateFormat sDateFormat=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String datetime=sDateFormat.format(new Date());
        try {
            Map<String, Object> usermap ;
            Map<String, Object> attachmap ;
            List<HashMap<String, Object>> listattach;
            List<HashMap<String, Object>> newlist=new ArrayList<>();
            Map<String,Object> map=new HashMap<>();
            String condition="  and exists (select 1 from frame_people_goodat where frame_people_goodat.userGuid='"+userId+"'" +
                    " and frame_people_eval.userguid=frame_people_goodat.rowGuid)";
            switch (type){
                case "0"://差评
                    condition+="  and result<=2";
                    break;
                case "1"://中评
                    condition+="  and result=3 ";
                    break;
                case "2"://好评
                    condition+="  and result>3 ";
                    break;
                default:
                    break;
            }

            List<HashMap<String, Object>> list=inewcommon.findlist("frame_people_eval","*",map,condition,"",pagenum, pagesize);
            int count= inewcommon.findlist("frame_people_eval",map,condition);
            for(HashMap<String, Object> hashMap:list) {
              String  envaluuser=hashMap.get("evalUserguid").toString();
                usermap=new HashMap<>();
                usermap.put("rowguid",envaluuser);
                FramePeople framePeople= iframePeople.find(usermap);
                hashMap.put("nickName",framePeople.getNickName());;//昵称
                //头像
                attachmap=new HashMap<>();
                attachmap.put("clientGuid=",framePeople.getIconurl());
                listattach = inewcommon.findlist("frame_attachinfo", "*", attachmap, "", "", 0, 20);
                if(listattach.size()>0){
                    hashMap.put("filepath",listattach.get(0).get("filepath"));//头像
                }else{
                    hashMap.put("filepath","../../../../images/other/tuandui.png");//头像
                }
                newlist.add(hashMap);
            }
            rJsonObject.put("code","200");//获取成功
            rJsonObject.put("count",count);
            rJsonObject.put("result",newlist);//获取值
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
     * 获取评价
     */
    @RequestMapping(value = "/getEval", method = RequestMethod.POST)
    public String getEval(@RequestBody String params) {
        JSONObject rJsonObject = new JSONObject();
        JSONObject jsonObject = JSONObject.parseObject(params);
        jsonObject = JSONObject.parseObject(jsonObject.getString("param"));
        List<HashMap<String, Object>> newlist=new ArrayList<>();
        SimpleDateFormat sDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String datatime = sDateFormat.format(new Date());
        String userguid = jsonObject.get("productGuid").toString();
        int pagesize = Integer.parseInt(jsonObject.get("pagesize").toString());
        int pagenum = Integer.parseInt(jsonObject.get("pagenum").toString());
        String envaluuser="";
        Map<String,Object> usermap;
        try {
            Map<String, Object> map = new HashMap<>();
            map.put("userguid=", userguid);
            //获取个人偏好
            List<HashMap<String, Object>> list = inewcommon.findlist("frame_people_eval", "*", map, "", "", pagenum, pagesize);
            for(HashMap<String,Object> hashMap:list){
                envaluuser=hashMap.get("evalUserguid").toString();
                usermap=new HashMap<>();
                usermap.put("rowguid",envaluuser);
                FramePeople framePeople= iframePeople.find(usermap);
                hashMap.put("nickName",framePeople.getNickName());;//昵称
                hashMap.put("iconurl",framePeople.getIconurl());;//头像client
                newlist.add(hashMap);
            }
            int count= inewcommon.findlist("frame_people_eval",map,"");
            rJsonObject.put("code", "200");
            rJsonObject.put("count",count);
            rJsonObject.put("result", newlist);
        } catch (Exception e) {
            //插入报错信息
            SMART_ERRORLOG smartErrorlog = new SMART_ERRORLOG();
            smartErrorlog.setGuid(UUID.randomUUID().toString());
            smartErrorlog.setGuid(datatime);
            smartErrorlog.setErrorlog(e.getMessage());
            icommon.insertlog(smartErrorlog);
            rJsonObject.put("code", "400");//插入失败
        }
        return rJsonObject.toJSONString();
    }
    /*
     * 新增收藏
     */
    @RequestMapping(value = "/addProductPrice", method = RequestMethod.POST)
    public String addProductPrice(@RequestBody String params)  {
        JSONObject rJsonObject = new JSONObject();
        JSONObject jsonObject = JSONObject.parseObject(params);
        jsonObject = JSONObject.parseObject(jsonObject.getString("param"));
        String rowGuid = UUID.randomUUID().toString();
        SimpleDateFormat sDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String datatime = sDateFormat.format(new Date());
        String productguid = jsonObject.get("productguid").toString();
        float price = Float.valueOf(jsonObject.get("price").toString());
        String dw = jsonObject.get("dw").toString();
        String content = jsonObject.get("content").toString();
        ProductPrice record;
        try {
            Map<String,Object> map=new HashMap<>();
            record = new ProductPrice();
            record.setRowguid(rowGuid);
            record.setProductguid(productguid);
            record.setPrice(price);
            record.setDw(dw);
            record.setContent(content);
            productPrice.insert(record);
            rJsonObject.put("code", "200");//插入失败
        } catch (Exception e) {
            //插入报错信息
            SMART_ERRORLOG smartErrorlog = new SMART_ERRORLOG();
            smartErrorlog.setGuid(UUID.randomUUID().toString());
            smartErrorlog.setGuid(datatime);
            smartErrorlog.setErrorlog(e.getMessage());
            icommon.insertlog(smartErrorlog);
            rJsonObject.put("code", "400");//插入失败
        }
        return rJsonObject.toJSONString();
    }
    @RequestMapping(value = "/upProductPrice", method = RequestMethod.POST)
    public String upProductPrice(@RequestBody String params) throws IOException {
        JSONObject rJsonObject = new JSONObject();
        JSONObject jsonObject = JSONObject.parseObject(params);
        jsonObject=JSONObject.parseObject(jsonObject.getString("param"));
        String rowguid = jsonObject.get("rowguid").toString();
        float price = Float.valueOf(jsonObject.get("price").toString());
        String dw = jsonObject.get("dw").toString();
        String content = jsonObject.get("content").toString();
        ProductPrice record=new ProductPrice();
        Map<String,Object> map=new HashMap<>();
        map.put("rowguid",rowguid);
        record=productPrice.find(map);
        if(record==null){
            rJsonObject.put("code","300");//案例不存在
            return rJsonObject.toJSONString();
        }
        record.setPrice(price);
        record.setDw(dw);
        record.setContent(content);
        SimpleDateFormat sDateFormat=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String datetime=sDateFormat.format(new Date());
        try {
            int n=productPrice.update(record);
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
    //删除收藏
    @RequestMapping(value = "/delProductPrice", method = RequestMethod.POST)
    public String delProductPrice(@RequestBody String params) {
        JSONObject rJsonObject = new JSONObject();
        JSONObject jsonObject = JSONObject.parseObject(params);
        jsonObject = JSONObject.parseObject(jsonObject.getString("param"));
        SimpleDateFormat sDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String datatime = sDateFormat.format(new Date());
        String rowGuid = jsonObject.get("rowGuid").toString();
        try {
            ProductPrice record = new ProductPrice();
            record.setRowguid(rowGuid);
            productPrice.delete(record);
            rJsonObject.put("code", "200");
        } catch (Exception e) {
            //插入报错信息
            SMART_ERRORLOG smartErrorlog = new SMART_ERRORLOG();
            smartErrorlog.setGuid(UUID.randomUUID().toString());
            smartErrorlog.setGuid(datatime);
            smartErrorlog.setErrorlog(e.getMessage());
            icommon.insertlog(smartErrorlog);
            rJsonObject.put("code", "400");//插入失败
        }
        return rJsonObject.toJSONString();
    }

    /*
     * 获取收藏
     */
    @RequestMapping(value = "/getProductPrice", method = RequestMethod.POST)
    public String getProductPrice(@RequestBody String params) {
        JSONObject rJsonObject = new JSONObject();
        JSONObject jsonObject = JSONObject.parseObject(params);
        jsonObject = JSONObject.parseObject(jsonObject.getString("param"));
        SimpleDateFormat sDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String datatime = sDateFormat.format(new Date());
        String productguid = jsonObject.get("productguid").toString();
        int pagesize = Integer.parseInt(jsonObject.get("pagesize").toString());
        int pagenum = Integer.parseInt(jsonObject.get("pagenum").toString());
        try {
            Map<String, Object> map = new HashMap<>();
            map.put("productguid=", productguid);
            //获取个人偏好
            List<HashMap<String, Object>> list = inewcommon.findlist("product_price", "*", map, "", "", pagenum, pagesize);
            int count=inewcommon.findlist("product_price",map,"");
            rJsonObject.put("code", "200");
            rJsonObject.put("result", list);
            rJsonObject.put("count", count);
        } catch (Exception e) {
            //插入报错信息
            SMART_ERRORLOG smartErrorlog = new SMART_ERRORLOG();
            smartErrorlog.setGuid(UUID.randomUUID().toString());
            smartErrorlog.setGuid(datatime);
            smartErrorlog.setErrorlog(e.getMessage());
            icommon.insertlog(smartErrorlog);
            rJsonObject.put("code", "400");//插入失败
        }
        return rJsonObject.toJSONString();
    }
    /*
     * 获取收藏
     */
    @RequestMapping(value = "/getProductPriceDetail", method = RequestMethod.POST)
    public String getProductPriceDetail(@RequestBody String params) {
        JSONObject rJsonObject = new JSONObject();
        JSONObject jsonObject = JSONObject.parseObject(params);
        jsonObject = JSONObject.parseObject(jsonObject.getString("param"));
        SimpleDateFormat sDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String datatime = sDateFormat.format(new Date());
        String rowguid = jsonObject.get("rowguid").toString();
        try {
            Map<String, Object> map = new HashMap<>();
            map.put("rowguid", rowguid);
            //获取个人偏好
            ProductPrice record = productPrice.find(map);
            rJsonObject.put("code", "200");
            rJsonObject.put("result", record);
        } catch (Exception e) {
            //插入报错信息
            SMART_ERRORLOG smartErrorlog = new SMART_ERRORLOG();
            smartErrorlog.setGuid(UUID.randomUUID().toString());
            smartErrorlog.setGuid(datatime);
            smartErrorlog.setErrorlog(e.getMessage());
            icommon.insertlog(smartErrorlog);
            rJsonObject.put("code", "400");//插入失败
        }
        return rJsonObject.toJSONString();
    }

}
