package com.lxh.newrhjg.rest;

import com.alibaba.fastjson.JSONObject;
import com.lxh.newrhjg.api.*;
import com.lxh.newrhjg.entity.*;
import com.lxh.rhjg.active.api.IPeople;
import com.lxh.rhjg.active.api.IProject;
import com.lxh.rhjg.active.api.Icommon;
import com.lxh.rhjg.common.util.HttpClient;
import com.lxh.rhjg.common.util.MD5Utils;
import com.lxh.rhjg.entity.SMART_ERRORLOG;
import com.lxh.rhjg.verifycode.api.IVerifycode;
import com.lxh.test.common.PropertiesUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;

@RestController
@RequestMapping("/storeinfo")
public class StoreController {
    @Autowired
    Inewcommon inewcommon;
    @Autowired
    IframePeopleGoodAt iframePeopleGoodAt;
    @Autowired
    IframePeopleEval iframePeopleEval;
    @Autowired
    IframeStore iframeStore;
    @Autowired
    IframePeople iframePeople;
    /*
     * 获取图标
     */
    @RequestMapping(value = "/getTitle", method = RequestMethod.POST)
    public String UpdateUserInfo(@RequestBody String params)  {
        JSONObject rJsonObject = new JSONObject();
        try {
            Map<String, Object> map = new HashMap<>();
            String condtion=" and row_id<=10";
            List<HashMap<String, Object>> list = inewcommon.findlist("frame_code", "codeid,codename,remark", map, condtion, " order by ordernum desc", 0, 20);
            rJsonObject.put("code", "200");
            rJsonObject.put("result", list);
        } catch (Exception e) {
            rJsonObject.put("code", "400");
        }
        return rJsonObject.toJSONString();
    }
    /*
     * 获取图标类别
     */
    @RequestMapping(value = "/getTitleType", method = RequestMethod.POST)
    public String getTitleType(@RequestBody String params)  {
        JSONObject rJsonObject = new JSONObject();
        JSONObject jsonObject = JSONObject.parseObject(params);
        jsonObject = JSONObject.parseObject(jsonObject.getString("param"));
        try {
            String codeid=jsonObject.getString("codeid");
            Map<String, Object> map = new HashMap<>();
            map.put("codeid=",codeid);
            List<HashMap<String, Object>> list = inewcommon.findlist("frame_item", "itemtext,itemvalue,remark", map, "and LENGTH(itemvalue)=3", " order by ordernum desc", 0, 20);
            rJsonObject.put("code", "200");
            rJsonObject.put("result", list);
        } catch (Exception e) {
            rJsonObject.put("code", "400");
        }
        return rJsonObject.toJSONString();
    }
    /*
     * 获取图标类别
     */
    @RequestMapping(value = "/getMainType", method = RequestMethod.POST)
    public String getMainType(@RequestBody String params)  {
        JSONObject rJsonObject = new JSONObject();
        JSONObject jsonObject = JSONObject.parseObject(params);
        jsonObject = JSONObject.parseObject(jsonObject.getString("param"));
        try {
            String codeid=jsonObject.getString("codeid");
            Map<String, Object> map = new HashMap<>();
            Map<String, Object> childmap = new HashMap<>();
            map.put("codeid=",codeid);
            String itemvalue="";
            List<HashMap<String, Object>> childlist;//子类
            List<HashMap<String, Object>> newHashMap=new ArrayList<>();
            List<HashMap<String, Object>> list = inewcommon.findlist("frame_item", "itemtext,itemvalue,remark", map, "and LENGTH(itemvalue)=3", " order by ordernum desc", 0, 20);
            for(HashMap<String,Object> hashMap:list){
                itemvalue=hashMap.get("itemvalue").toString();
                childlist=inewcommon.findlist("frame_item", "itemtext,itemvalue,remark", childmap, "and LENGTH(itemvalue)=5 and itemvalue like '"+itemvalue+"%'", " order by ordernum desc", 0, 20);
                hashMap.put("childitem",childlist);
                newHashMap.add(hashMap);
            }
            rJsonObject.put("code", "200");
            rJsonObject.put("result", list);
        } catch (Exception e) {
            rJsonObject.put("code", "400");
        }
        return rJsonObject.toJSONString();
    }
    /*
     * 获取所有感兴趣
     */
    @RequestMapping(value = "/getinitLike", method = RequestMethod.POST)
    public String getinitLike(@RequestBody String params){
        JSONObject rJsonObject = new JSONObject();
        //JSONObject jsonObject = JSONObject.parseObject(params);
        //jsonObject = JSONObject.parseObject(jsonObject.getString("param"));
        try {
            List<HashMap<String, Object>> result = new ArrayList<>();
            List<HashMap<String, Object>> listitem = new ArrayList<>();
            Map<String, Object> map = new HashMap<>();
            String conditon=" and codeid>=10000";
            List<HashMap<String, Object>> list = inewcommon.findlist("frame_code", "codeid,codename", null, conditon, " order by ordernum desc", 0, 20);
            String codeid = "";
            for (HashMap<String, Object> hashMap : list) {
                codeid = hashMap.get("codeid").toString();
                listitem = new ArrayList<>();
                map = new HashMap<>();
                map.put("codeid=", codeid);
                listitem = inewcommon.findlist("frame_item", "itemtext,itemvalue", map, " and LENGTH(itemvalue)=3 ", " order by ordernum desc", 0, 100);
                hashMap.put("item", listitem);
                result.add(hashMap);
            }
            rJsonObject.put("code", "200");
            rJsonObject.put("result", result);
        } catch (Exception e) {
            rJsonObject.put("code", "400");
        }
        return rJsonObject.toJSONString();
    }
    /*
     * 获取所有感兴趣
     */
    @RequestMapping(value = "/getinitLike1", method = RequestMethod.POST)
    public String getinitLike1(@RequestBody String params){
        JSONObject rJsonObject = new JSONObject();

        try {
            List<HashMap<String, Object>> result = new ArrayList<>();
            List<HashMap<String, Object>> listitem = new ArrayList<>();
            Map<String, Object> map = new HashMap<>();
            String conditon=" and codeid>=10000";
            List<HashMap<String, Object>> list = inewcommon.findlist("frame_code", "codeid,codename", null, conditon, " order by ordernum desc", 0, 20);
            String codeid = "";
            for (HashMap<String, Object> hashMap : list) {
                codeid = hashMap.get("codeid").toString();
                listitem = new ArrayList<>();
                map = new HashMap<>();
                map.put("codeid=", codeid);
                listitem = inewcommon.findlist("frame_item", "itemtext,itemvalue", map, " and LENGTH(itemvalue)=5 ", " order by ordernum desc", 0, 100);
                hashMap.put("item", listitem);
                result.add(hashMap);
            }
            rJsonObject.put("code", "200");
            rJsonObject.put("result", result);
        } catch (Exception e) {
            rJsonObject.put("code", "400");
        }
        return rJsonObject.toJSONString();
    }

    /*
     * 根据类别获取商户商品信息
     */
    @RequestMapping(value = "/getStorInfo", method = RequestMethod.POST)
    public String getStorInfo(@RequestBody String params) {
        JSONObject rJsonObject = new JSONObject();
        JSONObject jsonObject = JSONObject.parseObject(params);
        jsonObject = JSONObject.parseObject(jsonObject.getString("param"));
        try {
            String type = jsonObject.get("type").toString();
            int pagesize = Integer.parseInt(jsonObject.get("pagesize").toString());
            int pagenum = Integer.parseInt(jsonObject.get("pagenum").toString());
            String condition = " and (";
            if("".equals(type)){//如果搜索条件为空
                condition = " locate('010',goodAt)>0 )";
            }
            else{
                String [] arrtype=type.split(";");
                for(String atype:arrtype){
                  condition+="locate(" + atype + ",goodAt)>0 or ";
                }
                condition+=" 1=2) ";
            }
            String userGuid = "";
            String productGuid="";
            List<HashMap<String, Object>> listattach;
            Map<String, Object> attachmap ;
            List<HashMap<String, Object>> resultlist = new ArrayList<>();
            List<HashMap<String, Object>> list = inewcommon.findlist("frame_people_goodAt", "*", null, condition, "", pagenum, pagesize);
            int count= inewcommon.findlist("frame_people_goodAt",null,condition);
            for (HashMap<String, Object> hashMap : list) {
                userGuid = hashMap.get("userGuid").toString();//用户标识
                productGuid = hashMap.get("rowGuid").toString();//用户标识
                Map<String, Object> map = new HashMap<>();
                map.put("rowGuid", userGuid);//添加用户
                //获取店铺信息
                FramePeople framePeople = iframePeople.find(map);
                if (framePeople != null) {
                    hashMap.put("userGuid", framePeople.getRowGuid());
                    hashMap.put("nickName", framePeople.getNickName());
                } else {
                    hashMap.put("userGuid", "无");
                    hashMap.put("nickName", "无");
                }
                //获取评价信息
                map = new HashMap<>();
                map.put("userGuid",productGuid);
                FramePeopleEval eval = iframePeopleEval.find(map);//添加评论
                if (eval != null) {
                    hashMap.put("result", eval.getResult());
                    hashMap.put("content", eval.getEvalContent());
                } else {
                    hashMap.put("result", "无");
                    hashMap.put("content", "无");
                }
                //获取商品销售个数
                String conditionnew = " and productguid='"+productGuid+"'";
                int productcount= inewcommon.findlist("smart_project",null,conditionnew);
                hashMap.put("dealcount", productcount);
                //获取商品销售金额
                 conditionnew = " and  productguid='"+productGuid+"' ";
                List<HashMap<String, Object>> newlist = inewcommon.findlist("smart_project"," sum(PROJECT_AMT) as PROJECT_AMT,productguid ",null,conditionnew,"",0,10);
                String dealmoney= newlist.get(0).get("PROJECT_AMT")==null?"0":newlist.get(0).get("PROJECT_AMT").toString();
                hashMap.put("dealmoney",dealmoney);
               //头像
                attachmap=new HashMap<>();
                attachmap.put("clientGuid=",framePeople.getIconurl());
                listattach = inewcommon.findlist("frame_attachinfo", "*", attachmap, "", "", 0, 20);
                if(listattach.size()>0){
                    hashMap.put("filepath",listattach.get(0).get("filepath"));
                }else{
                    hashMap.put("filepath","../../../images/index/ldh.jpg");
                }
                resultlist.add(hashMap);
            }
            rJsonObject.put("code", "200");
            rJsonObject.put("result", resultlist);
            rJsonObject.put("count", count);
        } catch (Exception e) {
            rJsonObject.put("code", "400");
        }
        return rJsonObject.toJSONString();
    }
    /*
     * 获取搜搜关键字前10
     */
    @RequestMapping(value = "/getTopserch", method = RequestMethod.POST)
    public String getTopserch(@RequestBody String params) {
        JSONObject rJsonObject = new JSONObject();
        try {
            //获取搜索关键字前10
            Map map=new HashMap();
          List<Frameserch> list=iframeStore.findTop(map);
            rJsonObject.put("result", list);
        }catch (Exception e) {
            rJsonObject.put("code", "400");
        }
        return rJsonObject.toJSONString();
    }
    /*
     * 根据关键字综合查询
     */
    @RequestMapping(value = "/newserchInfo", method = RequestMethod.POST)
    public String newserchInfo(@RequestBody String params) {
        JSONObject rJsonObject = new JSONObject();
       JSONObject jsonObject = JSONObject.parseObject(params);
       jsonObject = JSONObject.parseObject(jsonObject.getString("param"));
       String keyvalue=jsonObject.get("keyvalue").toString();
       //插入搜索记录
       Frameserch frameserch=new Frameserch();
        frameserch.setSerchinfo(keyvalue);
        SimpleDateFormat sDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String datatime = sDateFormat.format(new Date());
        frameserch.setInsertdate(datatime);
        iframeStore.insert(frameserch);
        int pagesize = Integer.parseInt(jsonObject.get("pagesize").toString());
        int pagenum = Integer.parseInt(jsonObject.get("pagenum").toString());
        try {
            List<HashMap<String, Object>> listitem = new ArrayList<>();
            Map<String, Object> map = new HashMap<>();
            //根据关键字查询出查询种类
            listitem = inewcommon.findlist("frame_item", "itemtext ,itemvalue ", map, " and itemtext like '%"+keyvalue+"%' ", " order by ordernum desc", pagenum, pagesize);
            //获取用户详情
            String condition=" and (";//查询关键字用户条件
            for(HashMap hashMap:listitem){
                condition+=" locate(" + hashMap.get("itemvalue") + ",familiar)>0 or";
            }
            condition+=" 1=2)";
            String userGuid = "";
            List<HashMap<String, Object>> resultlist = new ArrayList<>();
            //获取用户信息
            List<HashMap<String, Object>> list = inewcommon.findlist("frame_people", "*", null, condition, "", pagenum, pagesize);
            int count= inewcommon.findlist("frame_people",null,condition);//获取总数量
            HashMap<String, Object> usermap;
            for (HashMap<String, Object> hashMap : list) {
              userGuid = hashMap.get("rowguid").toString();
              usermap = new HashMap<>();
                usermap.put("userguid", userGuid);//添加擅长
                FramepeopleGoodat goodat = iframePeopleGoodAt.find(map);
                if (goodat != null) {
                    usermap.put("goodAt", goodat.getGoodAt());
                    usermap.put("price", goodat.getPrice());
                } else {
                    usermap.put("goodAt", "");
                    usermap.put("price", "");
                }
                resultlist.add(usermap);//加入结果集
            }
            rJsonObject.put("code", "200");
            rJsonObject.put("result", resultlist);
            rJsonObject.put("count", count);
        } catch (Exception e) {
            rJsonObject.put("code", "400");
        }
        return rJsonObject.toJSONString();
    }
    ///老的查询
    @RequestMapping(value = "/serchInfo", method = RequestMethod.POST)
    public String serchInfo(@RequestBody String params) {
        JSONObject rJsonObject = new JSONObject();
        JSONObject jsonObject = JSONObject.parseObject(params);
        jsonObject = JSONObject.parseObject(jsonObject.getString("param"));
        String keyvalue=jsonObject.get("keyvalue").toString();

        //插入搜索记录
        Frameserch frameserch=new Frameserch();
        frameserch.setSerchinfo(keyvalue);
        SimpleDateFormat sDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String datatime = sDateFormat.format(new Date());
        frameserch.setInsertdate(datatime);
        iframeStore.insert(frameserch);

        int pagesize = Integer.parseInt(jsonObject.get("pagesize").toString());
        int pagenum = Integer.parseInt(jsonObject.get("pagenum").toString());
        try {
            List<HashMap<String, Object>> listitem = new ArrayList<>();
            Map<String, Object> map = new HashMap<>();
            listitem = inewcommon.findlist("frame_item", "itemtext ,itemvalue ", map, " and itemtext like '%"+keyvalue+"%' ", " order by ordernum desc", pagenum, pagesize);
            int count= inewcommon.findlist("frame_item",map," and itemtext like '%"+keyvalue+"%' ");
            rJsonObject.put("code", "200");
            rJsonObject.put("result", listitem);
            rJsonObject.put("count", count);
        } catch (Exception e) {
            rJsonObject.put("code", "400");
        }
        return rJsonObject.toJSONString();
    }
}
