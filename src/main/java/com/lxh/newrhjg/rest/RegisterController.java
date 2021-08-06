package com.lxh.newrhjg.rest;

import com.lxh.newrhjg.utils.WechatDecryptDataUtil;
import com.alibaba.fastjson.JSONObject;
import com.lxh.newrhjg.api.IframePeople;
import com.lxh.newrhjg.api.IframePeopleExtend;
import com.lxh.newrhjg.entity.FramePeople;
import com.lxh.newrhjg.entity.FramePeopleExtendinfo;
import com.lxh.rhjg.active.api.*;
import com.lxh.rhjg.common.util.Common;
import com.lxh.rhjg.common.util.HttpClient;
import com.lxh.rhjg.common.util.MD5Utils;
import com.lxh.rhjg.entity.*;
import com.lxh.rhjg.verifycode.api.IVerifycode;
import com.lxh.test.common.PropertiesUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.*;

@RestController
@RequestMapping("/register")
public class RegisterController {
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
    IframePeopleExtend iframePeopleExtend;

    /*
     * 注册商家用户
     */
    @RequestMapping(value = "/registerUser", method = RequestMethod.POST)
    public String registerUser(@RequestBody String params) {
        JSONObject rJsonObject = new JSONObject();
        JSONObject jsonObject = JSONObject.parseObject(params);
        jsonObject = JSONObject.parseObject(jsonObject.getString("param"));
        String rowGuid = jsonObject.get("rowguid").toString();
        String phone = jsonObject.get("phone").toString();
        String passwd = MD5Utils.stringToMD5(jsonObject.get("passWord").toString());
        String usertype = jsonObject.get("usertype").toString();
       /* String dealNum = jsonObject.get("dealNum").toString();
        String dealMoney = jsonObject.get("dealMoney").toString();*/
        String iconurl = jsonObject.get("iconurl").toString();
     /*   String familiar = jsonObject.get("familiar").toString();
        String familiarChina = jsonObject.get("familiarChina").toString();*/
        String areaPro = jsonObject.get("areaPro").toString();
        /*   String subTool = jsonObject.get("subTool").toString();*/
        String isDS = jsonObject.get("isDS").toString();
        String isPro = jsonObject.get("isPro").toString();
        String superDes = jsonObject.get("superDes").toString();
        String verifyCode = jsonObject.get("verifyCode").toString();
        String nickName = jsonObject.get("nickName").toString();
        SimpleDateFormat sDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String datatime = sDateFormat.format(new Date());
        String registerTime = datatime;
        if ("".equals(iconurl)) {//默认图片
            iconurl = "15dc53ac-f931-4911-bb02-976e4d1fd10d";
        }
        //判断账号是否存在
        if (IsUidExist(phone)) {
            rJsonObject.put("code", "100");
        } else if (!IsVerifyCode(phone, verifyCode)) {
            rJsonObject.put("code", "300");
        } else {
            try {
                FramePeople record = new FramePeople();
                record.setRowGuid(rowGuid);
                record.setPhone(phone);
                record.setPassword(passwd);
                record.setUsertype(usertype);
                record.setNickName(nickName);
                record.setIconurl(iconurl);
                record.setAreaPro(areaPro);
                record.setIsDS(isDS);
                if (!"".equals(isPro) && isPro != null)
                    record.setIsPro(Integer.parseInt(isPro));
                record.setSuperDes(superDes);
                record.setRegisterTime(registerTime);
                //插入SMART_USERS
                int n = iframePeople.insert(record);
                if (n == 0) {
                    rJsonObject.put("code", "400");
                } else {
                    switch (usertype) {
                        case "01"://个人扩展信息
                            String zjYear = jsonObject.get("zjYear").toString();
                            String customType = jsonObject.get("customType").toString();
                            FramePeopleExtendinfo peopleExtendinfo = null;
                            //获取用户扩展信息
                            Map<String, Object> map = new HashMap<>();
                            map.put("userguid", rowGuid);
                            peopleExtendinfo = iframePeopleExtend.find(map);
                            if (peopleExtendinfo == null) {//不存在，插入
                                peopleExtendinfo = new FramePeopleExtendinfo();
                                peopleExtendinfo.setZjYear(zjYear);
                                peopleExtendinfo.setCustomType(customType);
                                peopleExtendinfo.setRowGuid(UUID.randomUUID().toString());
                                peopleExtendinfo.setUserGuid(rowGuid);
                                iframePeopleExtend.insert(peopleExtendinfo);
                            } else {//存在则更新
                                peopleExtendinfo.setZjYear(zjYear);
                                peopleExtendinfo.setCustomType(customType);
                                iframePeopleExtend.update(peopleExtendinfo);
                            }
                            break;
                        case "02":
                            break;
                        case "03":
                            break;
                    }
                    //插入老库用户表
                    if (!IsUidExistOld(phone)) {
                        SMART_PEOPLE smartPeople = new SMART_PEOPLE();
                        smartPeople.setGUID(rowGuid);
                        smartPeople.setUSER_ID(phone);
                        smartPeople.setNICK_NAME("");
                        smartPeople.setSEX("00");
                        smartPeople.setPASS_WORD(passwd);
                        smartPeople.setDATATIME(datatime);
                        smartPeople.setMREGIE_ID("");
                        iPeople.insertUser(smartPeople);
                    }
                    rJsonObject.put("code", "200");
                }
            } catch (Exception e) {
                rJsonObject.put("code", "400");
            }
        }
        return rJsonObject.toJSONString();
    }

    /*
     * 注册发布端新用户
     */
    @RequestMapping(value = "/registerCustumUser", method = RequestMethod.POST)
    public String registerCustumUser(@RequestBody String params) {
        JSONObject rJsonObject = new JSONObject();
        JSONObject jsonObject = JSONObject.parseObject(params);
        jsonObject = JSONObject.parseObject(jsonObject.getString("param"));
        String rowGuid = jsonObject.get("rowguid").toString();
        String phone = jsonObject.get("phone").toString();
        String passwd = MD5Utils.stringToMD5(jsonObject.get("passWord").toString());
        //String usertype = jsonObject.get("usertype").toString();
        //String dealNum = jsonObject.get("dealNum").toString();
        //String dealMoney = jsonObject.get("dealMoney").toString();
        String iconurl = jsonObject.get("iconurl").toString();
        //String familiar = jsonObject.get("familiar").toString();
        //String familiarChina = jsonObject.get("familiarChina").toString();
        // String areaPro = jsonObject.get("areaPro").toString();
        // String subTool = jsonObject.get("subTool").toString();
        // String isDS = jsonObject.get("isDS").toString();
        //String isPro = jsonObject.get("isPro").toString();
        //String superDes = jsonObject.get("superDes").toString();
        String verifyCode = jsonObject.get("verifyCode").toString();
        String nickName = jsonObject.get("nickName").toString();
        //String openId = jsonObject.get("openid").toString();
        SimpleDateFormat sDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String datatime = sDateFormat.format(new Date());
        String registerTime = datatime;
        if ("".equals(iconurl)) {//默认图片attchguid
            iconurl = "15dc53ac-f931-4911-bb02-976e4d1fd10d";
        }
        //判断账号是否存在
        if (IsUidExist(phone)) {
            rJsonObject.put("code", "100");
        } else if (!IsVerifyCode(phone, verifyCode)) {
            rJsonObject.put("code", "300");
        } else {
            try {
                FramePeople record = new FramePeople();
                record.setRowGuid(rowGuid);
                record.setPhone(phone);
                record.setPassword(passwd);
                record.setIconurl(iconurl);
                record.setNickName(nickName);
                record.setRegisterTime(registerTime);
                //插入SMART_USERS
                int n = iframePeople.insert(record);
                if (n == 0) {
                    rJsonObject.put("code", "400");
                } else {
                    //插入老库用户表
                    if (!IsUidExistOld(phone)) {
                        SMART_PEOPLE smartPeople = new SMART_PEOPLE();
                        smartPeople.setGUID(rowGuid);
                        smartPeople.setUSER_ID(phone);
                        smartPeople.setNICK_NAME("");
                        smartPeople.setSEX("00");
                        smartPeople.setPASS_WORD(passwd);
                        smartPeople.setDATATIME(datatime);
                        smartPeople.setMREGIE_ID("");
                        iPeople.insertUser(smartPeople);
                    }
                    rJsonObject.put("code", "200");
                }
            } catch (Exception e) {
                rJsonObject.put("code", "400");
            }
        }
        return rJsonObject.toJSONString();
    }

    /*
     * 简单注册
     */
    @RequestMapping(value = "/simpleRegist", method = RequestMethod.POST)
    public String simpleRegist(@RequestBody String params) {
        JSONObject rJsonObject = new JSONObject();
        JSONObject jsonObject = JSONObject.parseObject(params);
        jsonObject = JSONObject.parseObject(jsonObject.getString("param"));
        String phone = jsonObject.get("phone").toString();
        String content = jsonObject.get("content").toString();
        String verifyCode = jsonObject.get("verifyCode").toString();
        String openid = jsonObject.get("openid").toString();
        SimpleDateFormat sDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String datatime = sDateFormat.format(new Date());
        if (!IsVerifyCode(phone, verifyCode)) {
            rJsonObject.put("code", "300");//验证码不正确
            return rJsonObject.toJSONString();
        }
        //判断openid是否存在
        if (IsOpenidExist(openid)) {
            rJsonObject.put("code", "100");//openid已存在
            return rJsonObject.toJSONString();
        }
        String rowGuid = UUID.randomUUID().toString();
        //初始化密码，随机6位数
        int value = (int) ((Math.random() * 9 + 1) * 100000);
        String password = MD5Utils.stringToMD5(String.valueOf(value));
        FramePeople record = null;
        try {
            record = iframePeople.findPeople("phone", phone);
            if (record == null) {//初始化用户registerUser
                FramePeople people = new FramePeople();
                people.setRowGuid(rowGuid);
                people.setPhone(phone);
                people.setPassword(password);
                people.setOpenId(openid);
                iframePeople.insert(people);
                record = people;
                //发送初始化密码
                String contents = "【融汇精工】您本次注册账号的初始化密码是：" + value + "，请注意保存";
                sendMsg(contents, phone);
            }
            //不为空插入项目信息
            if (!"".equals(content)) {
                //插入投标信息
                SMART_PROJECT smartProject = new SMART_PROJECT();
                String project_num = GetRuleStr("PROJECT_NUM");
                smartProject.setLINK_TEL(phone);
                smartProject.setCUST_ID(phone);
                smartProject.setPROJECT_DESC(content);
                smartProject.setPROJECT_NUM("N" + project_num);
                smartProject.setDATATIME(datatime);
                smartProject.setSTATUS("00");
                int n = iProject.InsertProject(smartProject);
                if (n == 0) {
                    rJsonObject.put("code", "400");
                    rJsonObject.put("error", "项目信息插入失败");

                } else {
                    //插入老库项目信息
                    iProject.InsertoldProject(smartProject);
                    rJsonObject.put("result", record);
                    rJsonObject.put("code", "200");
                }
            } else {
                rJsonObject.put("result", record);
                rJsonObject.put("code", "200");
            }
            //
            //插入老库用户表
            if (!IsUidExistOld(phone)) {
                SMART_PEOPLE smartPeople = new SMART_PEOPLE();
                smartPeople.setGUID(rowGuid);
                smartPeople.setUSER_ID(phone);
                smartPeople.setNICK_NAME("");
                smartPeople.setSEX("00");
                smartPeople.setPASS_WORD(password);
                smartPeople.setDATATIME(datatime);
                smartPeople.setMREGIE_ID("");
                iPeople.insertUser(smartPeople);
            }
        } catch (Exception e) {
            rJsonObject.put("code", "400");
        }
        return rJsonObject.toJSONString();
    }

    /*
     * 修改手机号
     */
    @RequestMapping(value = "/changPhone", method = RequestMethod.POST)
    public String changPhone(@RequestBody String params) {
        JSONObject rJsonObject = new JSONObject();
        JSONObject jsonObject = JSONObject.parseObject(params);
        jsonObject = JSONObject.parseObject(jsonObject.getString("param"));
        String phone = jsonObject.get("phone").toString();
        String oldphone = jsonObject.get("oldphone").toString();
        String verifyCode = jsonObject.get("verifyCode").toString();
        boolean result = IsVerifyCode(phone, verifyCode);//判断验证码是否正确
        if (!result) {
            rJsonObject.put("code", "300");
            return rJsonObject.toJSONString();
        }
        FramePeople people = iframePeople.findPeople("phone", oldphone);
        if (people != null) {//老用户信息
            people.setPhone(phone);
            iframePeople.update(people);
            rJsonObject.put("code", "200");
        } else {
            rJsonObject.put("code", "400");
        }
        return rJsonObject.toJSONString();

    }

    /*
     * 修改密码
     */
    @RequestMapping(value = "/changPassword", method = RequestMethod.POST)
    public String changPassword(@RequestBody String params) {
        JSONObject rJsonObject = new JSONObject();
        JSONObject jsonObject = JSONObject.parseObject(params);
        jsonObject = JSONObject.parseObject(jsonObject.getString("param"));
        String phone = jsonObject.get("phone").toString();
        String password = jsonObject.get("password").toString();
        String verifyCode = jsonObject.get("verifyCode").toString();
        boolean result = IsVerifyCode(phone, verifyCode);//判断验证码是否正确
        if (!result) {
            rJsonObject.put("code", "300");
            return rJsonObject.toJSONString();
        }
        FramePeople people = iframePeople.findPeople("phone", phone);
        if (people != null) {//老用户信息
            people.setPassword(MD5Utils.stringToMD5(String.valueOf(password)));
            iframePeople.update(people);
            rJsonObject.put("code", "200");
        } else {
            rJsonObject.put("code", "400");
        }
        return rJsonObject.toJSONString();

    }

    //验证验证码
    @RequestMapping(value = "/isVerifyCode", method = RequestMethod.POST)
    public String isVerifyCode(@RequestBody String params) {
        JSONObject rJsonObject = new JSONObject();
        JSONObject jsonObject = JSONObject.parseObject(params);
        jsonObject = JSONObject.parseObject(jsonObject.getString("param"));
        String phone = jsonObject.get("phone").toString();
        String verifyCode = jsonObject.get("verifyCode").toString();
        boolean result = IsVerifyCode(phone, verifyCode);
        if (result) {
            rJsonObject.put("code", "200");
        } else {
            rJsonObject.put("code", "400");
        }
        return rJsonObject.toJSONString();
    }

    /*
     * 获取验证码
     */
    @RequestMapping(value = "/getVerifyCode", method = RequestMethod.POST)
    public String getVerifyCode(@RequestBody String params) {
        JSONObject rJsonObject = new JSONObject();
        JSONObject jsonObject = JSONObject.parseObject(params);
        jsonObject = JSONObject.parseObject(jsonObject.getString("param"));
        try {
            String phone = jsonObject.get("phone").toString();
            /*
             * 1.产生随机数
             * 2.插入数据表
             * 3.发送短信
             */
            String code = "100";
   /*     FramePeople record = iframePeople.findPeople("phone", phone);
        if (record == null) {//账号不存在*/
            if (HasVerifyCode(phone)) {//已经发送过
                code = "300";
            } else {
                //账号不存在，发送短信
                int verifyCode = (int) ((Math.random() * 9 + 1) * 100000);
                SimpleDateFormat sDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                String datatime = sDateFormat.format(new Date());
                String guid = UUID.randomUUID().toString();
                SMART_VERIFY smartVerify = new SMART_VERIFY();
                smartVerify.setGUID(guid);
                smartVerify.setUSER_ID(phone);
                smartVerify.setVERIFY_CODE(String.valueOf(verifyCode));
                smartVerify.setDATATIME(datatime);
                iVerifycode.InsertVerifycode(smartVerify);
                String[] statusStr = {};
                String smsapi = "http://api.smsbao.com/";
                String user = "szgf"; //短信平台帐号
                String pass = MD5Utils.stringToMD5("shihoufeng2012"); //短信平台密码
                String content = "【融汇精工】您的验证码是:" + verifyCode + ",验证码10分钟内有效.若非本人操作请忽略此消息";//要发送的短信内容
                String sendurl = smsapi + "sms?u=" + user + "&p=" + pass + "&m=" + phone + "&c=" + URLEncoder.encode(content, "utf-8");
                String result = HttpClient.doPost(sendurl, "");
                code = result;
                //  }
                if ("0".equals(code)) {
                    rJsonObject.put("code", "200");
                } else {
                    rJsonObject.put("code", "400");
                }
            }
        } catch (Exception e) {
            rJsonObject.put("code", "400");
            rJsonObject.put("error", e.getMessage());
        }
        return rJsonObject.toJSONString();
    }

    //微信小程序手机号一键登录
    @RequestMapping(value = "/wxlogin", method = RequestMethod.POST)
    public String wxLogin(@RequestBody String params) {
        JSONObject rJsonObject = new JSONObject();
        JSONObject jsonObject = JSONObject.parseObject(params);
        jsonObject = JSONObject.parseObject(jsonObject.getString("param"));
        // 错误信息
        String errorMsg = "";
        // 解析参数
        String code = jsonObject.get("code").toString();
        String encryptedData = jsonObject.get("encryptedData").toString();
        String iv = jsonObject.get("iv").toString();
        int userFlag=Integer.parseInt(jsonObject.get("userFlag").toString());
        try {
            PropertiesUtil.loadFile("encode.properties");
            String appid = PropertiesUtil.getPropertyValue("appid");
            String secret = PropertiesUtil.getPropertyValue("secret");
            String tokenUrl = "https://api.weixin.qq.com/sns/jscode2session?appid=" + appid + "&secret=" + secret + "&js_code=" + code + "&grant_type=authorization_code";
            String oauthResponseText = HttpClient.doGet(tokenUrl);
            com.alibaba.fastjson.JSONObject jo = com.alibaba.fastjson.JSONObject.parseObject(oauthResponseText);
//               && phoneNumber !=null
            if (jo.get("openid") != null && !"".equals(jo.get("openid"))) {
                //    获取手机号
                String session_key = jo.get("session_key").toString();
                JSONObject phoneInfo = WechatDecryptDataUtil.getPhoneNumber(session_key, encryptedData, iv);
                System.out.println(phoneInfo.toString());
                String phoneNumber = phoneInfo.get("phoneNumber").toString();
                System.out.println(phoneNumber);
//                    插入用户
                FramePeople record = null;
                record = iframePeople.findPeople("phone", phoneNumber);
                int n=0;
                if (record == null) {
                    SimpleDateFormat sDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    String registerTime = sDateFormat.format(new Date());
                    FramePeople people = new FramePeople();
                    people.setRowGuid(UUID.randomUUID().toString());
                    people.setRegisterTime(registerTime);
                    people.setPhone(phoneNumber);
                    people.setOpenId(jo.get("openid").toString());
                    people.setUserFlag(userFlag);
                    record = people;
                    n = iframePeople.insert(record);
                } else if (record.getOpenId() == null) {
                    record.setOpenId(jo.get("openid").toString());
                    n=iframePeople.update(record);
                }else{
                    n=1;
                }
                if (n == 0) {
                    rJsonObject.put("code", "400");
                    rJsonObject.put("error", "登录失败!");
                } else {
                    rJsonObject.put("openid", record.getOpenId());
                    rJsonObject.put("phoneNumber", record.getPhone());
                    rJsonObject.put("userguid",record.getRowGuid());
                    rJsonObject.put("userFlag",record.getUserFlag());
                    rJsonObject.put("code", "200");
                }
            } else {
                rJsonObject.put("code", "400");
                rJsonObject.put("msg", jo.get("errmsg").toString());
            }
        } catch (
                Exception e) {
            rJsonObject.put("code", "400");
        }

        return rJsonObject.toJSONString();
    }

    //获取小程序用户openid
    @RequestMapping(value = "/getopenid", method = RequestMethod.POST)
    public String getOpenid(@RequestBody String params) {
        JSONObject rJsonObject = new JSONObject();
        JSONObject jsonObject = JSONObject.parseObject(params);
        jsonObject = JSONObject.parseObject(jsonObject.getString("param"));
        // 错误信息
        String errorMsg = "";
        // 解析参数
        String code = jsonObject.get("code").toString();
        try {
            PropertiesUtil.loadFile("encode.properties");
            String appid = PropertiesUtil.getPropertyValue("appid");
            String secret = PropertiesUtil.getPropertyValue("secret");
            String tokenUrl = "https://api.weixin.qq.com/sns/jscode2session?appid=" + appid + "&secret=" + secret + "&js_code=" + code + "&grant_type=authorization_code";
            String oauthResponseText = HttpClient.doGet(tokenUrl);
            System.out.println(oauthResponseText);
            com.alibaba.fastjson.JSONObject jo = com.alibaba.fastjson.JSONObject.parseObject(oauthResponseText);
            Map<String, String> resultMap = new HashMap<String, String>();
            System.out.println(jo.get("openid"));
            if (jo.get("openid") != null && !"".equals(jo.get("openid"))) {
                rJsonObject.put("openid", jo.get("openid").toString());
                rJsonObject.put("code", "200");
            } else {
                rJsonObject.put("code", "400");
                rJsonObject.put("msg", jo.get("errmsg").toString());
            }
        } catch (Exception e) {
            rJsonObject.put("code", "400");
        }

        return rJsonObject.toJSONString();
    }

    //绑定小程序用户openid
    @RequestMapping(value = "/bindOpenid", method = RequestMethod.POST)
    public String bindOpenid(@RequestBody String params) {
        JSONObject rJsonObject = new JSONObject();
        JSONObject jsonObject = JSONObject.parseObject(params);
        try {
            jsonObject = JSONObject.parseObject(jsonObject.getString("param"));
            String userguid = jsonObject.get("userguid").toString();
            String openid = jsonObject.get("openid").toString();
            if (IsOpenidExist(openid)) {//如果openid已经绑定用户
                rJsonObject.put("code", "200");
                return rJsonObject.toJSONString();
            }
            FramePeople people = iframePeople.findPeople("rowguid", userguid);
            if (people != null) {//老用户信息
                people.setOpenId(openid);
                iframePeople.update(people);
                rJsonObject.put("code", "200");
            } else {
                rJsonObject.put("code", "400");
            }
        } catch (Exception e) {
            rJsonObject.put("code", "400");
        }
        return rJsonObject.toJSONString();
    }

    //绑定小程序用户openid
    @RequestMapping(value = "/unBindOpenid", method = RequestMethod.POST)
    public String unBindOpenid(@RequestBody String params) {
        JSONObject rJsonObject = new JSONObject();
        JSONObject jsonObject = JSONObject.parseObject(params);
        jsonObject = JSONObject.parseObject(jsonObject.getString("param"));
        String userguid = jsonObject.get("userguid").toString();
        try {
            FramePeople people = iframePeople.findPeople("rowguid", userguid);
            if (people != null) {//老用户信息
                people.setOpenId("");
                iframePeople.update(people);
                rJsonObject.put("code", "200");
            } else {
                rJsonObject.put("code", "400");
            }
        } catch (Exception e) {
            rJsonObject.put("code", "400");
        }
        return rJsonObject.toJSONString();
    }

    public String sendMsg(String content, String phone) {
        String code = "";
        String smsapi = "http://api.smsbao.com/";
        String user = "szgf"; //短信平台帐号
        String pass = MD5Utils.stringToMD5("shihoufeng2012"); //短信平台密码
        String sendurl = null;
        try {
            sendurl = smsapi + "sms?u=" + user + "&p=" + pass + "&m=" + phone + "&c=" + URLEncoder.encode(content, "utf-8");
            String result = HttpClient.doPost(sendurl, "");
            code = result;
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return code;
    }

    /*
     * 判断注册账号是否存在
     */
    public boolean IsUidExist(String phone) {
        if ("".equals(phone) || phone == null) {
            return false;
        } else {
            FramePeople record = iframePeople.findPeople("phone", phone);
            if (record != null) {
                return true;
            } else {
                return false;
            }
        }
    }

    /*
     * 判断注册账号是否存在
     */
    public boolean IsOpenidExist(String openid) {
        if ("".equals(openid) || openid == null) {
            return false;
        } else {
            FramePeople record = iframePeople.findPeople("openid", openid);
            if (record != null) {
                return true;
            } else {
                return false;
            }
        }
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

    /*
     * 判断验证码是否正确
     */
    public boolean HasVerifyCode(String phone) {
        if ("".equals(phone) || phone == null) {
            return false;
        } else {
            Map<String, Object> map = new HashMap<>();
            List<SMART_VERIFY> listVerify = iPeople.getVerify("DATE_ADD(DATATIME,INTERVAL 10 MINUTE) >= NOW() and USER_ID='" + phone + "'", map);
            if (listVerify.size() == 0) {
                return false;
            } else {
                return true;
            }
        }
    }

    /*
     * 组织规则编码
     */
    public String GetRuleStr(String ruleid) {
        String returnvalue = "";
        SMART_RULE smartRule = icommon.findSmartrule("rule_id", ruleid);
        if (smartRule != null) {
            String id = smartRule.getRuleValue();
            if (id.equals("") || id == null) {
                id = "1000000";
            }
            int num = Integer.parseInt(id) + 1;
            smartRule.setRuleValue(String.valueOf(num));
            //更新最大编号
            icommon.upSmartrule(smartRule);
            returnvalue = smartRule.getPrefix() + String.valueOf(num);
        }
        return returnvalue;
    }

    /*
     * 判断注册账号是否存在
     */
    public boolean IsUidExistOld(String uid) {
        if ("".equals(uid) || uid == null) {
            return false;
        } else {
            SMART_PEOPLE smartPeople = iPeople.findPeople("USER_ID", uid);
            if (smartPeople != null) {
                return true;
            } else {
                return false;
            }
        }
    }
}
