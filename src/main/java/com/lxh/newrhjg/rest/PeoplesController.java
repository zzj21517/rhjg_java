package com.lxh.newrhjg.rest;

import com.alibaba.fastjson.JSONObject;
import com.ctc.wstx.util.StringUtil;
import com.lxh.newrhjg.api.*;
import com.lxh.newrhjg.entity.*;
import com.lxh.rhjg.active.api.IPeople;
import com.lxh.rhjg.active.api.IProject;
import com.lxh.rhjg.active.api.Icommon;
import com.lxh.rhjg.active.api.SMART_PROJECT;
import com.lxh.rhjg.common.util.Common;
import com.lxh.rhjg.common.util.HttpClient;
import com.lxh.rhjg.common.util.MD5Utils;
import com.lxh.rhjg.entity.SMART_ERRORLOG;
import com.lxh.rhjg.entity.SMART_VERIFY;
import com.lxh.rhjg.verifycode.api.IVerifycode;
import com.lxh.test.common.PropertiesUtil;
import com.mysql.jdbc.StringUtils;
import org.apache.cxf.ws.addressing.MAPAggregator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.*;

@RestController
@RequestMapping("/peopleinfo")
public class PeoplesController {
    @Autowired
    IPeople iPeople;
    @Autowired
    IframePeople iframePeople;
    @Autowired
    IProject iProject;
    @Autowired
    IVerifycode iVerifycode;
    @Autowired
    Icommon icommon;
    @Autowired
    Inewcommon inewcommon;
    @Autowired
    IframePeopleExtend iframePeopleExtend;
    @Autowired
    IframePeopleGoodAt iframePeopleGoodAt;
    @Autowired
    IframeCompanyExtendinfo iframeCompanyExtendinfo;

    /*
     * 更新用户信息
     */
    @RequestMapping(value = "/updateUserInfo", method = RequestMethod.POST)
    public String updateUserInfo(@RequestBody String params)  {
        JSONObject rJsonObject = new JSONObject();
        JSONObject jsonObject = JSONObject.parseObject(params);
        jsonObject = JSONObject.parseObject(jsonObject.getString("param"));
        String userguid = jsonObject.get("userguid").toString();
        //String phone = jsonObject.get("phone").toString();
        //String passwd = MD5Utils.stringToMD5(jsonObject.get("passWord").toString());
        String usertype = jsonObject.get("usertype").toString();
        String iconurl = jsonObject.get("iconurl").toString();
       // String familiar = jsonObject.get("familiar").toString();
        //String familiarChina = jsonObject.get("familiarChina").toString();
        String areaPro = jsonObject.get("areaPro").toString();
        //String subTool = jsonObject.get("subTool").toString();
        String isDS = jsonObject.get("isDS").toString();
        String isPro = jsonObject.get("isPro").toString();
        String superDes = jsonObject.get("superDes").toString();
         //新加字段
        String nickName = jsonObject.get("nickName").toString();
        //String openid = jsonObject.get("openid").toString();
        try {
            FramePeople record = new FramePeople();
            record=iframePeople.findPeople("rowguid",userguid);
            if(record ==null){
                rJsonObject.put("code", "300");
                return  rJsonObject.toJSONString();
            }
            record.setRowGuid(userguid);
           // record.setPhone(phone);
           // record.setPassword(passwd);
            record.setUsertype(usertype);
            //record.setDealNum(Integer.parseInt(dealNum));
            //record.setDealMoney(Double.valueOf(dealMoney));
            record.setIconurl(iconurl);
           // record.setFamiliar(familiar);
         //   record.setFamiliarChina(familiarChina);
            record.setAreaPro(areaPro);
          //  record.setSubTool(subTool);
            record.setIsDS(isDS);
            record.setIsPro(Integer.parseInt(isPro));
            record.setSuperDes(superDes);
            record.setNickName(nickName);
           // record.setOpenId(openid);
            int n = iframePeople.update(record);
            switch (usertype) {
                case "01"://个人扩展信息
                    String zjYear = jsonObject.get("zjYear").toString();
                    String customType = jsonObject.get("customType").toString();
                    FramePeopleExtendinfo peopleExtendinfo = null;
                    //获取用户扩展信息
                    Map<String, Object> map = new HashMap<>();
                    map.put("userguid", userguid);
                    peopleExtendinfo = iframePeopleExtend.find(map);
                    if (peopleExtendinfo == null) {//不存在，插入
                        peopleExtendinfo=new FramePeopleExtendinfo();
                        peopleExtendinfo.setZjYear(zjYear);
                        peopleExtendinfo.setCustomType(customType);
                        peopleExtendinfo.setRowGuid(UUID.randomUUID().toString());
                        peopleExtendinfo.setUserGuid(userguid);
                        iframePeopleExtend.insert(peopleExtendinfo);
                    } else {//存在则更新
                        peopleExtendinfo.setZjYear(zjYear);
                        peopleExtendinfo.setCustomType(customType);
                        iframePeopleExtend.update(peopleExtendinfo);
                    }
                    break;
                default:
                    String isYZ = jsonObject.get("isYZ").toString();
                    String memberNum = jsonObject.get("memberNum").toString();
                    String license = jsonObject.get("license").toString();
                    String aboutUS = jsonObject.get("aboutUS").toString();
                    String comanyName = jsonObject.get("comanyName").toString();
                    String createDate = jsonObject.get("createDate").toString();
                    String comanyAddress = jsonObject.get("comanyAddress").toString();
                    String comanyQualify = jsonObject.get("comanyQualify").toString();
                    FrameCompanyExtendinfo companyExtendinfo = null;
                    //获取用户扩展信息
                     map = new HashMap<>();
                    map.put("userguid", userguid);
                    companyExtendinfo = iframeCompanyExtendinfo.find(map);
                    if (companyExtendinfo == null) {//不存在，插入
                        companyExtendinfo=new FrameCompanyExtendinfo();
                        companyExtendinfo.setIsYZ(isYZ);
                        companyExtendinfo.setMemberNum(Integer.parseInt(memberNum));
                        companyExtendinfo.setLicense(license);
                        companyExtendinfo.setRowGuid(UUID.randomUUID().toString());
                        companyExtendinfo.setUserGuid(userguid);
                        companyExtendinfo.setAboutUS(aboutUS);
                        companyExtendinfo.setComanyName(comanyName);
                        companyExtendinfo.setCreateDate(createDate);
                        companyExtendinfo.setComanyAddress(comanyAddress);
                        companyExtendinfo.setComanyQualify(comanyQualify);
                        iframeCompanyExtendinfo.insert(companyExtendinfo);
                    } else {//存在则更新
                        companyExtendinfo.setIsYZ(isYZ);
                        companyExtendinfo.setMemberNum(Integer.parseInt(memberNum));
                        companyExtendinfo.setAboutUS(aboutUS);
                        companyExtendinfo.setComanyName(comanyName);
                        companyExtendinfo.setCreateDate(createDate);
                        companyExtendinfo.setComanyAddress(comanyAddress);
                        companyExtendinfo.setComanyQualify(comanyQualify);
                        iframeCompanyExtendinfo.update(companyExtendinfo);
                    }
                    break;
            }
            if (n == 0) {
                rJsonObject.put("code", "400");
            } else {
                rJsonObject.put("code", "200");
            }
        } catch (Exception e) {
            rJsonObject.put("code", "400");
        }
        return rJsonObject.toJSONString();
    }

    /*
     * 更新用户信息
     */
    @RequestMapping(value = "/updatesimpUserInfo", method = RequestMethod.POST)
    public String updatesimpUserInfo(@RequestBody String params)  {
        JSONObject rJsonObject = new JSONObject();
        JSONObject jsonObject = JSONObject.parseObject(params);
        jsonObject = JSONObject.parseObject(jsonObject.getString("param"));
        String userguid = jsonObject.get("userguid").toString();
        String usertype = jsonObject.get("usertype").toString();
        String iconurl = jsonObject.get("iconurl").toString();
        String areaPro = jsonObject.get("areaPro").toString();
        String isDS = jsonObject.get("isDS").toString();
        String isPro = jsonObject.get("isPro").toString();
        String superDes = jsonObject.get("superDes").toString();
        //新加字段
        String nickName = jsonObject.get("nickName").toString();
        try {
            FramePeople record = new FramePeople();
            record=iframePeople.findPeople("rowguid",userguid);
            if(record ==null){
                rJsonObject.put("code", "300");
                return  rJsonObject.toJSONString();
            }
            record.setRowGuid(userguid);

            record.setUsertype(usertype);

            record.setIconurl(iconurl);

            record.setAreaPro(areaPro);

            record.setIsDS(isDS);
            record.setIsPro(Integer.parseInt(isPro));
            record.setSuperDes(superDes);
            record.setNickName(nickName);
            int n = iframePeople.update(record);
            if (n == 0) {
                rJsonObject.put("code", "400");
            } else {
                rJsonObject.put("code", "200");
            }
        } catch (Exception e) {
            rJsonObject.put("code", "400");
        }
        return rJsonObject.toJSONString();
    }
    /*
     * 获取商铺信息
     */
    @RequestMapping(value = "/getUserInfo", method = RequestMethod.POST)
    public String getUserInfo(@RequestBody String params) {
        JSONObject rJsonObject = new JSONObject();
        JSONObject jsonObject = JSONObject.parseObject(params);
        jsonObject = JSONObject.parseObject(jsonObject.getString("param"));
        String rowGuid = jsonObject.get("userguid").toString();
        try {
            FramePeople record = iframePeople.findPeople("rowguid", rowGuid);
            Map<String,Object> map=new HashMap<>();
            map.put("userGuid",rowGuid);
            switch (record.getUsertype()){
                case "01":
           FramePeopleExtendinfo  FramePeopleExtendinfo = iframePeopleExtend.find(map);
                    rJsonObject.put("extend", FramePeopleExtendinfo);
                    break;
                default:
            FrameCompanyExtendinfo frameCompanyExtendinfo= iframeCompanyExtendinfo.find(map);
                    rJsonObject.put("extend", frameCompanyExtendinfo);
                    break;
            }
            record.setScore("5.0"); //评分
            record.setBzj("0"); //保证金
            record.setDealNum(0); //成交数
            record.setDealMoney(0); //成交额
            if(record.getFinishPer()==null){
                record.setFinishPer("100%");
            }
            if(record.getCustom()==null){
                record.setCustom("0");
            }
            rJsonObject.put("code", "200");
            rJsonObject.put("result", record);
        } catch (Exception e) {
            rJsonObject.put("code", "400");
        }
        return rJsonObject.toJSONString();
    }

    /*
     * 获取用户信息
     */
    @RequestMapping(value = "/getUserInfoByopenid", method = RequestMethod.POST)
    public String getUserInfoByopenid(@RequestBody String params) {
        JSONObject rJsonObject = new JSONObject();
        JSONObject jsonObject = JSONObject.parseObject(params);
        jsonObject = JSONObject.parseObject(jsonObject.getString("param"));
        String openid = jsonObject.get("openid").toString();
        try {
            FramePeople record = iframePeople.findPeople("openid", openid);
            rJsonObject.put("code", "200");
            rJsonObject.put("result", record);
        } catch (Exception e) {
            rJsonObject.put("code", "400");
        }
        return rJsonObject.toJSONString();
    }

    /*
     * 登录
     */
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public String login(@RequestBody String params)  {
        JSONObject rJsonObject = new JSONObject();
        JSONObject jsonObject = JSONObject.parseObject(params);
        jsonObject = JSONObject.parseObject(jsonObject.getString("param"));
        String phone = jsonObject.get("phone").toString();
        String password = jsonObject.get("password").toString();
        try {
            Map<String, Object> map = new HashMap<>();
            map.put("phone", phone);
            map.put("password", MD5Utils.stringToMD5(password));
            FramePeople framePeople = iframePeople.find(map);//用户是否存在
            if (framePeople == null)
                rJsonObject.put("code", "400");
            else {
                rJsonObject.put("code", "200");
                rJsonObject.put("result", framePeople);//存在则返回用户信息
            }
        } catch (Exception e) {
            rJsonObject.put("code", "400");
        }
        return rJsonObject.toJSONString();
    }

    /*
     * 登录
     */
    @RequestMapping(value = "/codelogin", method = RequestMethod.POST)
    public String codelogin(@RequestBody String params)  {
        JSONObject rJsonObject = new JSONObject();
        JSONObject jsonObject = JSONObject.parseObject(params);
        jsonObject = JSONObject.parseObject(jsonObject.getString("param"));
        String phone = jsonObject.get("phone").toString();
        String verifyCode = jsonObject.get("verifyCode").toString();
        try {
            if (!IsVerifyCode(phone, verifyCode)) {
                rJsonObject.put("code", "300");//验证码不正确
                return rJsonObject.toJSONString();
            }
            Map<String, Object> map = new HashMap<>();
            map.put("phone", phone);
            FramePeople framePeople = iframePeople.find(map);//用户是否存在
            if (framePeople == null)
            {
                System.out.println("失败");
                rJsonObject.put("code", "400");
            }
            else {
                rJsonObject.put("code", "200");
                rJsonObject.put("result", framePeople);//存在则返回用户信息
            }
        } catch (Exception e) {
            System.out.println(e);
            rJsonObject.put("code", "400");
        }
        return rJsonObject.toJSONString();
    }

    /*
     * 新增感兴趣
     */
    @RequestMapping(value = "/addEnjoy", method = RequestMethod.POST)
    public String addEnjoy(@RequestBody String params)  {
        JSONObject rJsonObject = new JSONObject();
        JSONObject jsonObject = JSONObject.parseObject(params);
        jsonObject = JSONObject.parseObject(jsonObject.getString("param"));
        String rowGuid = UUID.randomUUID().toString();
        SimpleDateFormat sDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String datatime = sDateFormat.format(new Date());
        String userguid = jsonObject.get("userguid").toString();
        String enjoyType = jsonObject.get("enjoyType").toString();
        String enjoyTypeChina = jsonObject.get("enjoyTypeChina").toString();
        String[] arr = enjoyType.split(";");
        String[] arrchina = enjoyTypeChina.split(";");
        FramePeopleEnjoy framePeopleEnjoy;
        try {
            Map<String,Object> map=new HashMap<>();
            map.put("rowguid",userguid);
           FramePeople framePeople= iframePeople.find(map);
           if(framePeople==null){
               rJsonObject.put("code", "300");//插入失败
               return rJsonObject.toJSONString();
           }
            iframePeople.deleteEnjoy(userguid);
            for(int i=0;i<arr.length;i++){
                framePeopleEnjoy = new FramePeopleEnjoy();
                framePeopleEnjoy.setRowGuid(rowGuid);
                framePeopleEnjoy.setEnjoyType(arr[i]);
                framePeopleEnjoy.setUserGuid(userguid);
                framePeopleEnjoy.setEnjoyTypeChina(arrchina[i]);
                iframePeople.insertEnjoy(framePeopleEnjoy);
           }
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

    //删除喜好
    @RequestMapping(value = "/delEnjoy", method = RequestMethod.POST)
    public String delEnjoy(@RequestBody String params) {
        JSONObject rJsonObject = new JSONObject();
        JSONObject jsonObject = JSONObject.parseObject(params);
        jsonObject = JSONObject.parseObject(jsonObject.getString("param"));
        SimpleDateFormat sDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String datatime = sDateFormat.format(new Date());
        String userguid = jsonObject.get("userguid").toString();
        try {
            iframePeople.deleteEnjoy(userguid);
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
     * 获取喜好
     */
    @RequestMapping(value = "/getEnjoy", method = RequestMethod.POST)
    public String getEnjoy(@RequestBody String params) {
        JSONObject rJsonObject = new JSONObject();
        JSONObject jsonObject = JSONObject.parseObject(params);
        jsonObject = JSONObject.parseObject(jsonObject.getString("param"));

        SimpleDateFormat sDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String datatime = sDateFormat.format(new Date());
        String userguid = jsonObject.get("userguid").toString();
        try {
            Map<String, Object> map = new HashMap<>();
            map.put("userguid=", userguid);
            //获取个人偏好
            List<HashMap<String, Object>> list = inewcommon.findlist("frame_people_enjoy", "*", map, "", "", 0, 20);
            rJsonObject.put("code", "200");
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
     * 新增商品服务
     */
    @RequestMapping(value = "/addGoodAt", method = RequestMethod.POST)
    public String addGoodAt(@RequestBody String params) {
        JSONObject rJsonObject = new JSONObject();
        JSONObject jsonObject = JSONObject.parseObject(params);
        jsonObject = JSONObject.parseObject(jsonObject.getString("param"));
        String rowGuid = UUID.randomUUID().toString();
        SimpleDateFormat sDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String datatime = sDateFormat.format(new Date());
        String userguid = jsonObject.get("userguid").toString();
        String goodAt = jsonObject.get("goodAt").toString();
        String goodAtChina = jsonObject.get("goodAtChina").toString();

        String price = jsonObject.get("price").toString();
        String remark = jsonObject.get("remark").toString();
        //新增
        String productIcon = jsonObject.get("productIcon").toString();
        String productName = jsonObject.get("productName").toString();
        String isSJ = jsonObject.get("isSJ").toString();
        String area = jsonObject.get("area").toString();
        FramepeopleGoodat record;
        try {
            Map<String,Object> map=new HashMap<>();
            map.put("rowguid",userguid);
            FramePeople framePeople= iframePeople.find(map);
            if(framePeople==null){
                rJsonObject.put("code", "300");//商家信息不存在
                return rJsonObject.toJSONString();
            }
            Map<String,Object> maptype=new HashMap<>();
            maptype.put("userguid",userguid);
            maptype.put("goodAt",goodAt);
            FramepeopleGoodat framepeopleGoodat=iframePeopleGoodAt.find(maptype);
            if(framepeopleGoodat!=null){
                rJsonObject.put("code", "500");//服务种类已存在
                return rJsonObject.toJSONString();
            }
            //iframePeopleGoodAt.deleteByUser(userguid);//删除现有的
            record = new FramepeopleGoodat();
            record.setRowGuid(rowGuid);
            record.setGoodAt(goodAt);
            record.setUserGuid(userguid);
            record.setGoodAtChina(goodAtChina);
            record.setPrice(price);
            record.setRemark(remark);
            record.setProductIcon(productIcon);
            record.setProductName(productName);
            record.setIsSJ(isSJ);
            record.setArea(area);
            record.setAddtime(datatime);
            iframePeopleGoodAt.insert(record);

            rJsonObject.put("code", "200");//
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
     * 更新商品服务
     */
    @RequestMapping(value = "/upGoodAt", method = RequestMethod.POST)
    public String upGoodAt(@RequestBody String params) {
        JSONObject rJsonObject = new JSONObject();
        JSONObject jsonObject = JSONObject.parseObject(params);
        jsonObject = JSONObject.parseObject(jsonObject.getString("param"));
        SimpleDateFormat sDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String datatime = sDateFormat.format(new Date());
        String rowGuid = jsonObject.get("rowGuid").toString();
        String userguid = jsonObject.get("userguid").toString();
        String goodAt = jsonObject.get("goodAt").toString();
        String goodAtChina = jsonObject.get("goodAtChina").toString();
        String price = jsonObject.get("price").toString();
        String remark = jsonObject.get("remark").toString();
        //新增
        String productIcon = jsonObject.get("productIcon").toString();
        String productName = jsonObject.get("productName").toString();
        String isSJ = jsonObject.get("isSJ").toString();
        String area = jsonObject.get("area").toString();
        try {
            Map<String,Object> map=new HashMap<>();
            map.put("rowguid",userguid);
            FramePeople framePeople= iframePeople.find(map);
            if(framePeople==null){
                rJsonObject.put("code", "300");//商家信息不存在
                return rJsonObject.toJSONString();
            }
            Map<String,Object> maptype=new HashMap<>();
            maptype.put("rowGuid",rowGuid);
            FramepeopleGoodat framepeopleGoodat=iframePeopleGoodAt.find(maptype);
            if(framepeopleGoodat==null){
                rJsonObject.put("code", "400");
                return rJsonObject.toJSONString();
            }
            //iframePeopleGoodAt.deleteByUser(userguid);//删除现有的
            framepeopleGoodat.setPrice(price);
            framepeopleGoodat.setRemark(remark);
            framepeopleGoodat.setProductIcon(productIcon);
            framepeopleGoodat.setProductName(productName);
            framepeopleGoodat.setIsSJ(isSJ);
            framepeopleGoodat.setArea(area);
            framepeopleGoodat.setGoodAt(goodAt);
            framepeopleGoodat.setGoodAtChina(goodAtChina);
            framepeopleGoodat.setAddtime(datatime);
            iframePeopleGoodAt.update(framepeopleGoodat);
            rJsonObject.put("code", "200");//
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
     * 删除服务商品
     */
    @RequestMapping(value = "/delGoodAt", method = RequestMethod.POST)
    public String delGoodAt(@RequestBody String params)  {
        JSONObject rJsonObject = new JSONObject();
        JSONObject jsonObject = JSONObject.parseObject(params);
        jsonObject = JSONObject.parseObject(jsonObject.getString("param"));
        SimpleDateFormat sDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String datatime = sDateFormat.format(new Date());
        String rowGuid = jsonObject.get("rowGuid").toString();
        try {
            FramepeopleGoodat goodat=new FramepeopleGoodat();
            goodat.setRowGuid(rowGuid);
            iframePeopleGoodAt.delete(goodat);
            rJsonObject.put("code", "200");//
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
     * 获取商家服务
     */
    @RequestMapping(value = "/getGoodAt", method = RequestMethod.POST)
    public String getGoodAt(@RequestBody String params) {
        JSONObject rJsonObject = new JSONObject();
        JSONObject jsonObject = JSONObject.parseObject(params);
        jsonObject = JSONObject.parseObject(jsonObject.getString("param"));

        SimpleDateFormat sDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String datatime = sDateFormat.format(new Date());
        String userguid = jsonObject.get("userguid").toString();//商家标识
        int pagesize = Integer.parseInt(jsonObject.get("pagesize").toString());
        int pagenum = Integer.parseInt(jsonObject.get("pagenum").toString());
        String rowGuid="";
        String CliengGuid="";
        try {
            List<HashMap<String, Object>> listattach;
            Map<String, Object> attachmap ;
            Map<String, Object> map = new HashMap<>();
            map.put("userguid=", userguid);
             List<HashMap<String, Object>> newlist=new ArrayList<>();
            List<HashMap<String, Object>> list = inewcommon.findlist("frame_people_goodat", "*", map, "", "", pagenum, pagesize);
            int count=inewcommon.findlist("frame_people_goodat",map,"");
            for(HashMap<String, Object> hashMap:list){
               rowGuid=hashMap.get("rowGuid").toString();
               hashMap.put("isSJ",hashMap.get("isSJ").toString().equals("true")?true:false);
               if(hashMap.get("addtime")!=null){
                  hashMap.put("addtime",simpleDateFormat.format(simpleDateFormat.parse(hashMap.get("addtime").toString())));
               }else{
                   hashMap.put("addtime","");
               }
               hashMap.put("salenum","0");//销售量
                CliengGuid =hashMap.get("productIcon").toString();
              attachmap=new HashMap<>();
              attachmap.put("clientGuid=",CliengGuid);
              listattach = inewcommon.findlist("frame_attachinfo", "*", attachmap, "", "", 0, 20);
              if(listattach.size()>0){
                  hashMap.put("filepath",listattach.get(0).get("filepath"));
              }else{
                  hashMap.put("filepath","../../../../images/other/touxiang_3.png");
              }
                newlist.add(hashMap);
           }
            rJsonObject.put("code", "200");
            rJsonObject.put("count", count);
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
     * 获取商家服务详情
     */
    @RequestMapping(value = "/getGoodAtDetail", method = RequestMethod.POST)
    public String getGoodAtDetail(@RequestBody String params) {
        JSONObject rJsonObject = new JSONObject();
        JSONObject jsonObject = JSONObject.parseObject(params);
        jsonObject = JSONObject.parseObject(jsonObject.getString("param"));

        SimpleDateFormat sDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String datatime = sDateFormat.format(new Date());
        String rowGuid = jsonObject.get("rowGuid").toString();
        try {
            FramepeopleGoodat goodat=new FramepeopleGoodat();
            Map<String, Object> map = new HashMap<>();
            map.put("rowGuid", rowGuid);
            goodat=iframePeopleGoodAt.find(map);
            //获取商品销售个数
            String conditionnew = " and productguid='"+rowGuid+"'";
            int productcount= inewcommon.findlist("smart_project",null,conditionnew);
            map.put("dealcount", productcount);
            //获取商品销售金额
            conditionnew = " and  productguid='"+rowGuid+"' ";
            List<HashMap<String, Object>> newlist = inewcommon.findlist("smart_project"," sum(PROJECT_AMT) as PROJECT_AMT,productguid ",null,conditionnew,"",0,10);
            String dealmoney= newlist.get(0).get("PROJECT_AMT")==null?"0":newlist.get(0).get("PROJECT_AMT").toString();
            map.put("dealmoney",dealmoney);

            rJsonObject.put("code", "200");
            rJsonObject.put("result", goodat);
            rJsonObject.put("extend", map);
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
     * 判断验证码是否正确
     */
    public boolean IsVerifyCode(String phone, String verifyCode) {

        if ("".equals(verifyCode) || verifyCode == null || "".equals(phone) || phone == null) {
            return false;
        } else {
            String sqlconditon = " and DATE_ADD(DATATIME,INTERVAL 10 MINUTE) >= NOW()";
            Map<String, Object> map = new HashMap<>();
            map.put("USER_ID", phone);
            map.put("VERIFY_CODE", verifyCode);

            List<SMART_VERIFY> list = iPeople.getVerify(sqlconditon, map);
            if (list.size() <= 0) {
                return false;
            } else {
                return true;
            }
        }
    }
}
