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
    @Autowired
    IframeProject iframeProject;

    /*
     * 客户需求登记
     */
    @RequestMapping(value = "/codeApply", method = RequestMethod.POST)
    public String codeApply(@RequestBody String params) {
        JSONObject rJsonObject = new JSONObject();
        JSONObject jsonObject = JSONObject.parseObject(params);
        jsonObject = JSONObject.parseObject(jsonObject.getString("param"));
        int pid = Integer.parseInt(jsonObject.get("pid").toString());
        int cid = Integer.parseInt(jsonObject.get("cid").toString());
        String userName = jsonObject.get("userName").toString();
        String phone = jsonObject.get("phone").toString();
        String verifyCode = jsonObject.get("verifyCode").toString();
        SimpleDateFormat sDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String dateTime = sDateFormat.format(new Date());
        try {
            if (!IsVerifyCode(phone, verifyCode)) {
                rJsonObject.put("code", "300");//验证码不正确
                rJsonObject.put("error", "验证码错误");
                return rJsonObject.toJSONString();
            }
            FrameApply frameApply = new FrameApply();
            frameApply.setUserName(userName);
            frameApply.setPhone(phone);
            frameApply.setPid(pid);
            frameApply.setCid(cid);
            frameApply.setCreateTime(dateTime);
            int n = iframeProject.insertApply(frameApply);
            if (n == 0) {
                System.out.println("提交失败");
                rJsonObject.put("code", "400");
                rJsonObject.put("error", "提交失败");
            } else {
                rJsonObject.put("code", "200");
                rJsonObject.put("msg", "提交成功");
                rJsonObject.put("result", frameApply);//存在则返回用户信息
            }
        } catch (Exception e) {
            System.out.println(e);
            rJsonObject.put("code", "400");
        }
        return rJsonObject.toJSONString();
    }


    /*
     * 更新用户信息
     */
    @RequestMapping(value = "/newUpdateUserInfo", method = RequestMethod.POST)
    public String newUpdateUserInfo(@RequestBody String params) {
        JSONObject rJsonObject = new JSONObject();
        JSONObject jsonObject = JSONObject.parseObject(params);
        jsonObject = JSONObject.parseObject(jsonObject.getString("param"));
        try {
            String userguid = jsonObject.get("userguid") != null ? jsonObject.get("userguid").toString() : "";
            //新加字段
            String nickName = jsonObject.get("nickName") != null ? jsonObject.get("nickName").toString() : "";
            String avatarUrl = jsonObject.get("avatarUrl") != null ? jsonObject.get("avatarUrl").toString() : "";
            String province = jsonObject.get("province") != null ? jsonObject.get("province").toString() : "";
            String country = jsonObject.get("country") != null ? jsonObject.get("country").toString() : "";
            String city = jsonObject.get("city") != null ? jsonObject.get("city").toString() : "";
            String gender = jsonObject.get("gender") != null ? jsonObject.get("gender").toString() : "";
            FramePeople record = new FramePeople();
            record = iframePeople.findPeople("rowguid", userguid);
            if (record == null) {
                rJsonObject.put("code", "300");
                rJsonObject.put("error", "没查到该条数据");
                return rJsonObject.toJSONString();
            }
            record.setRowGuid(userguid);
            record.setNickName(nickName);
            record.setProvince(province);
            record.setCountry(country);
            record.setCity(city);
            record.setGender(gender);
            record.setAvatarUrl(avatarUrl);
            int n = iframePeople.update(record);
            if (n == 0) {
                rJsonObject.put("code", "400");
            } else {
                rJsonObject.put("userInfo", record);
                rJsonObject.put("code", "200");
            }
        } catch (Exception e) {
            System.out.println(e);
            rJsonObject.put("code", "400");
        }
        return rJsonObject.toJSONString();
    }

    /*
     * 更新用户信息
     */
    @RequestMapping(value = "/switchUser", method = RequestMethod.POST)
    public String switchUser(@RequestBody String params, @RequestHeader("openId") String openId) {
        JSONObject rJsonObject = new JSONObject();
        try {
            if (openId == null) {
                rJsonObject.put("code", "4000");
                return rJsonObject.toJSONString();
            }
            FramePeople people = new FramePeople();
            people = iframePeople.findPeople("openid", openId);
            if (people == null) {
                rJsonObject.put("code", "400");
                rJsonObject.put("error", "接口异常");
                return rJsonObject.toJSONString();
            }
            int userFlag = people.getUserFlag() == 0 ? 1 : 0;
            people.setUserFlag(userFlag);
            int engineerType = people.getEngineerType();
            String userGuid = people.getRowGuid();
            Boolean hasDetailInfo = true;
            if (userFlag == 1) { //客户
                FramePeopleCustom record = iframePeople.findCustom("userGuid", userGuid);
                if (record == null) {
                    hasDetailInfo = false;
                }
            } else if (userFlag == 0) {
                if (engineerType == 1) {
                    FramePeopleEngineerPerson record = iframePeople.findEngineerPerson("userGuid", userGuid);
                    if (record == null) {
                        hasDetailInfo = false;
                    }
                }
            }
            int n = iframePeople.update(people);
            if (n == 0) {
                rJsonObject.put("code", "400");
            } else {
                rJsonObject.put("hasDetailInfo", hasDetailInfo);
                rJsonObject.put("userInfo", people);
                rJsonObject.put("code", "200");
            }
        } catch (Exception e) {
            System.out.println(e);
            rJsonObject.put("code", "400");
        }
        return rJsonObject.toJSONString();
    }

    /*
     * 获取用户信息
     */
    @RequestMapping(value = "/getUserInfo", method = RequestMethod.POST)
    public String getUserinfo(@RequestBody String params) {
        JSONObject rJsonObject = new JSONObject();
        JSONObject jsonObject = JSONObject.parseObject(params);
        jsonObject = JSONObject.parseObject(jsonObject.getString("param"));
        try {
            SimpleDateFormat sDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String uid = jsonObject.getString("uid");
            FramePeople people = new FramePeople();
            people = iframePeople.findPeople("rowguid", uid);
            if (people == null) {
                rJsonObject.put("code", "500");
                return rJsonObject.toJSONString();
            } else {
                String expireTime = people.getExpireTime();
                Date expireDate = sDateFormat.parse(expireTime);
                Date curDate = sDateFormat.parse(sDateFormat.format(new Date()));
                if (expireDate.before(curDate)) {
                    people.setLevel(0);
                    int n = iframePeople.update(people);
                    if (n == 0) {
                        rJsonObject.put("code", "500");
                        return rJsonObject.toJSONString();
                    }
                }
                rJsonObject.put("code", "200");
                rJsonObject.put("userInfo", people);
            }
        } catch (Exception e) {
            rJsonObject.put("code", "400");
        }
        return rJsonObject.toJSONString();
    }


    /*
     * 获取用户详细信息
     */
    @RequestMapping(value = "/getUserDetailInfo", method = RequestMethod.POST)
    public String getUserDetailInfo(@RequestBody String params, @RequestHeader("openId") String openId) {
        JSONObject rJsonObject = new JSONObject();
        try {
            if (openId == null) {
                rJsonObject.put("code", "4000");
                return rJsonObject.toJSONString();
            }
            FramePeople people = new FramePeople();
            people = iframePeople.findPeople("openid", openId);
            if (people == null) {
                rJsonObject.put("code", "400");
                return rJsonObject.toJSONString();
            }
            int userFlag = people.getUserFlag();
            int engineerType = people.getEngineerType();
            String userGuid = people.getRowGuid();
            if (userFlag == 1) { //客户
                FramePeopleCustom record = iframePeople.findCustom("userGuid", userGuid);
                if (record == null) {
                    rJsonObject.put("code", "500");
                } else {
                    rJsonObject.put("code", 200);
                    rJsonObject.put("data", record);
                }
            } else if (userFlag == 0) {
                if (engineerType == 1) {
                    FramePeopleEngineerPerson record = iframePeople.findEngineerPerson("userGuid", userGuid);
                    if (record == null) {
                        rJsonObject.put("code", "500");
                    } else {
                        rJsonObject.put("code", 200);
                        rJsonObject.put("data", record);
                    }
                } else if (engineerType == 2) {
                    FramePeopleEngineerTeam record = iframePeople.findEngineerTeam("userGuid", userGuid);
                    if (record == null) {
                        rJsonObject.put("code", "500");
                    } else {
                        rJsonObject.put("code", 200);
                        rJsonObject.put("data", record);
                    }
                }
            }
        } catch (Exception e) {
            rJsonObject.put("code", "400");
        }
        return rJsonObject.toJSONString();
    }

    /*
     * 更新客户信息
     */
    @RequestMapping(value = "/updateCustomInfo", method = RequestMethod.POST)
    public String updateCustomInfo(@RequestBody String params, @RequestHeader("openId") String openId) {
        JSONObject rJsonObject = new JSONObject();
        if (openId == null) {
            rJsonObject.put("code", "4000");
            return rJsonObject.toJSONString();
        }
        FramePeople people = new FramePeople();
        people = iframePeople.findPeople("openid", openId);
        if (people == null) {
            rJsonObject.put("code", "400");
            return rJsonObject.toJSONString();
        }
        JSONObject jsonObject = JSONObject.parseObject(params);
        jsonObject = JSONObject.parseObject(jsonObject.getString("param"));
        String userGuid = people.getRowGuid();
        String customTypes = jsonObject.get("customTypes").toString();
        int customFundAge = jsonObject.get("customFundAge") != null ? Integer.parseInt(jsonObject.get("customFundAge").toString()) : 0;
        int customMemberNum = jsonObject.get("customMemberNum") != null ? Integer.parseInt(jsonObject.get("customMemberNum").toString()) : 0;
        SimpleDateFormat sDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            FramePeopleCustom record = iframePeople.findCustom("userGuid", userGuid);
            int n = 0;
            if (record == null) {
                record = new FramePeopleCustom();
                record.setRowGuid(UUID.randomUUID().toString());
                record.setUserGuid(userGuid);
                record.setCustomTypes(customTypes);
                if (customFundAge != 0) {
                    record.setCustomFundAge(customFundAge);
                }
                if (customMemberNum != 0) {
                    record.setCustomMemberNum(customMemberNum);
                }
                record.setCreateTime(sDateFormat.format(new Date()));
                record.setModifyTime(sDateFormat.format(new Date()));
                n = iframePeople.insertCustom(record);
            } else {
                record.setCustomTypes(customTypes);
                if (customFundAge != 0) {
                    record.setCustomFundAge(customFundAge);
                }
                if (customMemberNum != 0) {
                    record.setCustomMemberNum(customMemberNum);
                }
                record.setModifyTime(sDateFormat.format(new Date()));
                n = iframePeople.updateCustom(record);
            }
            if (n == 0) {
                rJsonObject.put("code", "400");
            } else {
                rJsonObject.put("code", "200");
            }
        } catch (Exception e) {
            System.out.println(e);
            rJsonObject.put("code", "400");
        }
        return rJsonObject.toJSONString();
    }


    /*
     * 更新工程师个人信息
     */
    @RequestMapping(value = "/updateEngineerPersonInfo", method = RequestMethod.POST)
    public String updateEngineerPersonInfo(@RequestBody String params, @RequestHeader("openId") String openId) {
        JSONObject rJsonObject = new JSONObject();
        if (openId == null) {
            rJsonObject.put("code", "4000");
            return rJsonObject.toJSONString();
        }
        FramePeople people = new FramePeople();
        people = iframePeople.findPeople("openid", openId);
        if (people == null) {
            rJsonObject.put("code", "400");
            return rJsonObject.toJSONString();
        }
        JSONObject jsonObject = JSONObject.parseObject(params);
        jsonObject = JSONObject.parseObject(jsonObject.getString("param"));
        String userGuid = people.getRowGuid();
        String engineerName = jsonObject.get("engineerName").toString();
        String majorTypes = jsonObject.get("majorTypes").toString();
        String canDoArea = jsonObject.get("canDoArea").toString();
        String partJob = jsonObject.get("partJob").toString();
        String certImgs = jsonObject.get("certImgs").toString();
        int canUploadElecBidding = jsonObject.getIntValue("canUploadElecBidding");
        int canUseBim = jsonObject.getIntValue("canUseBim");
        int canLocalCheck = jsonObject.getIntValue("canLocalCheck");
        int canFieldCheck = jsonObject.getIntValue("canFieldCheck");
        int canStamp = jsonObject.getIntValue("canStamp");
        int technologyLevel = jsonObject.getIntValue("technologyLevel");
        int workYears = jsonObject.getIntValue("workYears");
        int spaceTime = jsonObject.getIntValue("spaceTime");
        String companyType = jsonObject.getString("companyType");
        String itemPricingSoftware = jsonObject.getString("itemPricingSoftware");
        String calcuVolumeSoftware = jsonObject.getString("calcuVolumeSoftware");
        String projectTypes = jsonObject.getString("projectTypes");
        String serviceTypes = jsonObject.getString("serviceTypes");
        String purchaseTypes = jsonObject.getString("purchaseTypes");
        System.out.println("*****");
        System.out.println(jsonObject.getString("certImgs"));
        System.out.println(jsonObject.getString("zzj"));
        SimpleDateFormat sDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            FramePeopleEngineerPerson record = iframePeople.findEngineerPerson("userGuid", userGuid);
            int n = 0;
            if (record == null) {
                record = new FramePeopleEngineerPerson();
                record.setRowGuid(UUID.randomUUID().toString());
                record.setUserGuid(userGuid);
                record.setEngineerName(engineerName);
                record.setMajorTypes(majorTypes);
                record.setCanDoArea(canDoArea);
                record.setPartJob(partJob);
                record.setCertImgs(certImgs);
                record.setCanUploadElecBidding(canUploadElecBidding);
                record.setCanUseBim(canUseBim);
                record.setCanLocalCheck(canLocalCheck);
                record.setCanFieldCheck(canFieldCheck);
                record.setCanStamp(canStamp);
                record.setTechnologyLevel(technologyLevel);
                record.setWorkYears(workYears);
                record.setSpaceTime(spaceTime);
                record.setCompanyType(companyType);
                record.setItemPricingSoftware(itemPricingSoftware);
                record.setCalcuVolumeSoftware(calcuVolumeSoftware);
                record.setProjectTypes(projectTypes);
                record.setServiceTypes(serviceTypes);
                record.setPurchaseTypes(purchaseTypes);
                record.setCreateTime(sDateFormat.format(new Date()));
                record.setModifyTime(sDateFormat.format(new Date()));
                n = iframePeople.insertEngineerPerson(record);
            } else {
                record.setEngineerName(engineerName);
                record.setMajorTypes(majorTypes);
                record.setCanDoArea(canDoArea);
                record.setPartJob(partJob);
                record.setCertImgs(certImgs);
                record.setCanUploadElecBidding(canUploadElecBidding);
                record.setCanUseBim(canUseBim);
                record.setCanLocalCheck(canLocalCheck);
                record.setCanFieldCheck(canFieldCheck);
                record.setCanStamp(canStamp);
                record.setTechnologyLevel(technologyLevel);
                record.setWorkYears(workYears);
                record.setSpaceTime(spaceTime);
                record.setCompanyType(companyType);
                record.setItemPricingSoftware(itemPricingSoftware);
                record.setCalcuVolumeSoftware(calcuVolumeSoftware);
                record.setProjectTypes(projectTypes);
                record.setServiceTypes(serviceTypes);
                record.setPurchaseTypes(purchaseTypes);
                record.setModifyTime(sDateFormat.format(new Date()));
                n = iframePeople.updateEngineerPerson(record);
            }
            if (n == 0) {
                rJsonObject.put("code", "400");
            } else {
                rJsonObject.put("code", "200");
            }
        } catch (Exception e) {
            System.out.println(e);
            rJsonObject.put("code", "400");
        }
        return rJsonObject.toJSONString();
    }


    /*
     * 更新工程师个人信息
     */
    @RequestMapping(value = "/updateEngineerTeamInfo", method = RequestMethod.POST)
    public String updateEngineerTeamInfo(@RequestBody String params, @RequestHeader("openId") String openId) {
        JSONObject rJsonObject = new JSONObject();
        if (openId == null) {
            rJsonObject.put("code", "4000");
            return rJsonObject.toJSONString();
        }
        FramePeople people = new FramePeople();
        people = iframePeople.findPeople("openid", openId);
        if (people == null) {
            rJsonObject.put("code", "400");
            return rJsonObject.toJSONString();
        }
        JSONObject jsonObject = JSONObject.parseObject(params);
        jsonObject = JSONObject.parseObject(jsonObject.getString("param"));
        String userGuid = people.getRowGuid();
        String engineerName = jsonObject.get("engineerName").toString();
        int memberNum = jsonObject.getIntValue("memberNum");
        int tjNum = jsonObject.getIntValue("tjNum");
        int azNum = jsonObject.getIntValue("azNum");
        int ylNum = jsonObject.getIntValue("ylNum");
        int jzxNum = jsonObject.getIntValue("jzxNum");
        int szdlqlNum = jsonObject.getIntValue("szdlqlNum");
        int dlNum = jsonObject.getIntValue("dlNum");
        int dtNum = jsonObject.getIntValue("dtNum");
        int tlNum = jsonObject.getIntValue("tlNum");
        int txNum = jsonObject.getIntValue("txNum");
        int tjjsbNum = jsonObject.getIntValue("tjjsbNum");
        int azjsbNum = jsonObject.getIntValue("azjsbNum");
        int yljsbNum = jsonObject.getIntValue("yljsbNum");
        int jzxjsbNum = jsonObject.getIntValue("jzxjsbNum");
        int szdlqljsbNum = jsonObject.getIntValue("szdlqljsbNum");
        int dljsbNum = jsonObject.getIntValue("dljsbNum");
        int dtjsbNum = jsonObject.getIntValue("dtjsbNum");
        int tljsbNum = jsonObject.getIntValue("tljsbNum");
        int txjsbNum = jsonObject.getIntValue("txjsbNum");
        int wyfwNum = jsonObject.getIntValue("wxfwNUm");
        int bafwNum = jsonObject.getIntValue("bafwNum");
        int bjfwNum = jsonObject.getIntValue("bjfwNum");
        int stcbfwNum = jsonObject.getIntValue("stcbfwNum");
        int ljqyfwNum = jsonObject.getIntValue("ljqyfwNum");
        int wlysfwNum = jsonObject.getIntValue("wlysfwNum");
        int spzzfwNum = jsonObject.getIntValue("spzzfwNum");
        int gccgNum = jsonObject.getIntValue("gccgNum");
        int hwcgNum = jsonObject.getIntValue("hwcgNum");
        int fwcgNum = jsonObject.getIntValue("fwcgNum");
        int zfcgNum = jsonObject.getIntValue("zfcgNum");
        int jzxcswjNum = jsonObject.getIntValue("jzxcswjNum");
        int jzNum = jsonObject.getIntValue("jzNum");
        int qzNum = jsonObject.getIntValue("qzNum");
        int jzSpaceTime = jsonObject.getIntValue("jzSpaceTime");
        int qzSpaceTime = jsonObject.getIntValue("qzSpaceTime");
        int lessThan3WorkYears = jsonObject.getIntValue("lessThan3WorkYears");
        int moreThan3WorkYears = jsonObject.getIntValue("moreThan3WorkYears");
        int moreThan5WorkYears = jsonObject.getIntValue("moreThan5WorkYears");
        String canDoArea = jsonObject.getString("canDoArea");
        String businessCertImgs = jsonObject.getString("businessCertImgs");
        String idCardCertImgs = jsonObject.getString("idCardCertImgs");
        String engineerCertImgs = jsonObject.getString("engineerCertImgs");
        int canUseBim = jsonObject.getIntValue("canUseBim");
        int canUploadElecBidding = jsonObject.getIntValue("canUploadElecBidding");
        int canLocalCheck = jsonObject.getIntValue("canLocalCheck");
        int canFieldCheck = jsonObject.getIntValue("canFieldCheck");
        int technologyLevel = jsonObject.getIntValue("technologyLevel");
        String itemPricingSoftware = jsonObject.getString("itemPricingSoftware");
        String calcuVolumeSoftware = jsonObject.getString("calcuVolumeSoftware");
        SimpleDateFormat sDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            FramePeopleEngineerTeam record = iframePeople.findEngineerTeam("userGuid", userGuid);
            int n = 0;
            if (record == null) {
                record = new FramePeopleEngineerTeam();
                record.setRowGuid(UUID.randomUUID().toString());
                record.setUserGuid(userGuid);
                record.setEngineerName(engineerName);
                record.setMemberNum(memberNum);
                record.setTjNum(tjNum);
                record.setAzNum(azNum);
                record.setYlNum(ylNum);
                record.setJzxNum(jzxNum);
                record.setSzdlqlNum(szdlqlNum);
                record.setDlNum(dlNum);
                record.setDtNum(dtNum);
                record.setTlNum(tlNum);
                record.setTxNum(txNum);
                record.setTjjsbNum(tjjsbNum);
                record.setAzjsbNum(azjsbNum);
                record.setYljsbNum(yljsbNum);
                record.setJzxjsbNum(jzxjsbNum);
                record.setSzdlqljsbNum(szdlqljsbNum);
                record.setDljsbNum(dljsbNum);
                record.setDtjsbNum(dtjsbNum);
                record.setTljsbNum(tljsbNum);
                record.setTxjsbNum(txjsbNum);
                record.setWyfwNum(wyfwNum);
                record.setBafwNum(bafwNum);
                record.setBjfwNum(bjfwNum);
                record.setStcbfwNum(stcbfwNum);
                record.setLjqyfwNum(ljqyfwNum);
                record.setWlysfwNum(wlysfwNum);
                record.setSpzzfwNum(spzzfwNum);
                record.setGccgNum(gccgNum);
                record.setHwcgNum(hwcgNum);
                record.setFwcgNum(fwcgNum);
                record.setZfcgNum(zfcgNum);
                record.setJzxcswjNum(jzxcswjNum);
                record.setJzNum(jzNum);
                record.setQzNum(qzNum);
                record.setJzSpaceTime(jzSpaceTime);
                record.setQzSpaceTime(qzSpaceTime);
                record.setLessThan3WorkYears(lessThan3WorkYears);
                record.setMoreThan3WorkYears(moreThan3WorkYears);
                record.setMoreThan5WorkYears(moreThan5WorkYears);
                record.setCanDoArea(canDoArea);
                record.setBusinessCertImgs(businessCertImgs);
                record.setIdCardCertImgs(idCardCertImgs);
                record.setEngineerCertImgs(engineerCertImgs);
                record.setCanUseBim(canUseBim);
                record.setCanUploadElecBidding(canUploadElecBidding);
                record.setCanLocalCheck(canLocalCheck);
                record.setCanFieldCheck(canFieldCheck);
                record.setTechnologyLevel(technologyLevel);
                record.setItemPricingSoftware(itemPricingSoftware);
                record.setCalcuVolumeSoftware(calcuVolumeSoftware);
                record.setCreateTime(sDateFormat.format(new Date()));
                record.setModifyTime(sDateFormat.format(new Date()));
                n = iframePeople.insertEngineerTeam(record);
            } else {
                record.setEngineerName(engineerName);
                record.setMemberNum(memberNum);
                record.setTjNum(tjNum);
                record.setAzNum(azNum);
                record.setYlNum(ylNum);
                record.setJzxNum(jzxNum);
                record.setSzdlqlNum(szdlqlNum);
                record.setDlNum(dlNum);
                record.setDtNum(dtNum);
                record.setTlNum(tlNum);
                record.setTxNum(txNum);
                record.setTjjsbNum(tjjsbNum);
                record.setAzjsbNum(azjsbNum);
                record.setYljsbNum(yljsbNum);
                record.setJzxjsbNum(jzxjsbNum);
                record.setSzdlqljsbNum(szdlqljsbNum);
                record.setDljsbNum(dljsbNum);
                record.setDtjsbNum(dtjsbNum);
                record.setTljsbNum(tljsbNum);
                record.setTxjsbNum(txjsbNum);
                record.setWyfwNum(wyfwNum);
                record.setBafwNum(bafwNum);
                record.setBjfwNum(bjfwNum);
                record.setStcbfwNum(stcbfwNum);
                record.setLjqyfwNum(ljqyfwNum);
                record.setWlysfwNum(wlysfwNum);
                record.setSpzzfwNum(spzzfwNum);
                record.setGccgNum(gccgNum);
                record.setHwcgNum(hwcgNum);
                record.setFwcgNum(fwcgNum);
                record.setZfcgNum(zfcgNum);
                record.setJzxcswjNum(jzxcswjNum);
                record.setJzNum(jzNum);
                record.setQzNum(qzNum);
                record.setJzSpaceTime(jzSpaceTime);
                record.setQzSpaceTime(qzSpaceTime);
                record.setLessThan3WorkYears(lessThan3WorkYears);
                record.setMoreThan3WorkYears(moreThan3WorkYears);
                record.setMoreThan5WorkYears(moreThan5WorkYears);
                record.setCanDoArea(canDoArea);
                record.setBusinessCertImgs(businessCertImgs);
                record.setIdCardCertImgs(idCardCertImgs);
                record.setEngineerCertImgs(engineerCertImgs);
                record.setCanUseBim(canUseBim);
                record.setCanUploadElecBidding(canUploadElecBidding);
                record.setCanLocalCheck(canLocalCheck);
                record.setCanFieldCheck(canFieldCheck);
                record.setTechnologyLevel(technologyLevel);
                record.setItemPricingSoftware(itemPricingSoftware);
                record.setCalcuVolumeSoftware(calcuVolumeSoftware);
                record.setModifyTime(sDateFormat.format(new Date()));
                n = iframePeople.updateEngineerTeam(record);
            }
            if (n == 0) {
                rJsonObject.put("code", "400");
            } else {
                rJsonObject.put("code", "200");
            }
        } catch (Exception e) {
            System.out.println(e);
            rJsonObject.put("code", "400");
        }
        return rJsonObject.toJSONString();
    }

    /*
     * 更新用户信息
     */
    @RequestMapping(value = "/updateUserInfo", method = RequestMethod.POST)
    public String updateUserInfo(@RequestBody String params) {
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
            record = iframePeople.findPeople("rowguid", userguid);
            if (record == null) {
                rJsonObject.put("code", "300");
                return rJsonObject.toJSONString();
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
                        peopleExtendinfo = new FramePeopleExtendinfo();
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
                        companyExtendinfo = new FrameCompanyExtendinfo();
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
    public String updatesimpUserInfo(@RequestBody String params) {
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
            record = iframePeople.findPeople("rowguid", userguid);
            if (record == null) {
                rJsonObject.put("code", "300");
                return rJsonObject.toJSONString();
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
    @RequestMapping(value = "/getUserInfo1", method = RequestMethod.POST)
    public String getUserInfo1(@RequestBody String params) {
        JSONObject rJsonObject = new JSONObject();
        JSONObject jsonObject = JSONObject.parseObject(params);
        jsonObject = JSONObject.parseObject(jsonObject.getString("param"));
        String rowGuid = jsonObject.get("userguid").toString();
        try {
            FramePeople record = iframePeople.findPeople("rowguid", rowGuid);
            Map<String, Object> map = new HashMap<>();
            map.put("userGuid", rowGuid);
            switch (record.getUsertype()) {
                case "01":
                    FramePeopleExtendinfo FramePeopleExtendinfo = iframePeopleExtend.find(map);
                    rJsonObject.put("extend", FramePeopleExtendinfo);
                    break;
                default:
                    FrameCompanyExtendinfo frameCompanyExtendinfo = iframeCompanyExtendinfo.find(map);
                    rJsonObject.put("extend", frameCompanyExtendinfo);
                    break;
            }
            record.setScore("5.0"); //评分
            record.setBzj("0"); //保证金
            record.setDealNum(0); //成交数
            record.setDealMoney(0); //成交额
            if (record.getFinishPer() == null) {
                record.setFinishPer("100%");
            }
            if (record.getCustom() == null) {
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
    public String login(@RequestBody String params) {
        System.out.println("登錄");
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
    public String codelogin(@RequestBody String params) {
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
            if (framePeople == null) {
                System.out.println("失败");
                rJsonObject.put("code", "400");
            } else {
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
    public String addEnjoy(@RequestBody String params) {
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
            Map<String, Object> map = new HashMap<>();
            map.put("rowguid", userguid);
            FramePeople framePeople = iframePeople.find(map);
            if (framePeople == null) {
                rJsonObject.put("code", "300");//插入失败
                return rJsonObject.toJSONString();
            }
            iframePeople.deleteEnjoy(userguid);
            for (int i = 0; i < arr.length; i++) {
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
            Map<String, Object> map = new HashMap<>();
            map.put("rowguid", userguid);
            FramePeople framePeople = iframePeople.find(map);
            if (framePeople == null) {
                rJsonObject.put("code", "300");//商家信息不存在
                return rJsonObject.toJSONString();
            }
            Map<String, Object> maptype = new HashMap<>();
            maptype.put("userguid", userguid);
            maptype.put("goodAt", goodAt);
            FramepeopleGoodat framepeopleGoodat = iframePeopleGoodAt.find(maptype);
            if (framepeopleGoodat != null) {
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
            Map<String, Object> map = new HashMap<>();
            map.put("rowguid", userguid);
            FramePeople framePeople = iframePeople.find(map);
            if (framePeople == null) {
                rJsonObject.put("code", "300");//商家信息不存在
                return rJsonObject.toJSONString();
            }
            Map<String, Object> maptype = new HashMap<>();
            maptype.put("rowGuid", rowGuid);
            FramepeopleGoodat framepeopleGoodat = iframePeopleGoodAt.find(maptype);
            if (framepeopleGoodat == null) {
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
    public String delGoodAt(@RequestBody String params) {
        JSONObject rJsonObject = new JSONObject();
        JSONObject jsonObject = JSONObject.parseObject(params);
        jsonObject = JSONObject.parseObject(jsonObject.getString("param"));
        SimpleDateFormat sDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String datatime = sDateFormat.format(new Date());
        String rowGuid = jsonObject.get("rowGuid").toString();
        try {
            FramepeopleGoodat goodat = new FramepeopleGoodat();
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
        String rowGuid = "";
        String CliengGuid = "";
        try {
            List<HashMap<String, Object>> listattach;
            Map<String, Object> attachmap;
            Map<String, Object> map = new HashMap<>();
            map.put("userguid=", userguid);
            List<HashMap<String, Object>> newlist = new ArrayList<>();
            List<HashMap<String, Object>> list = inewcommon.findlist("frame_people_goodat", "*", map, "", "", pagenum, pagesize);
            int count = inewcommon.findlist("frame_people_goodat", map, "");
            for (HashMap<String, Object> hashMap : list) {
                rowGuid = hashMap.get("rowGuid").toString();
                hashMap.put("isSJ", hashMap.get("isSJ").toString().equals("true") ? true : false);
                if (hashMap.get("addtime") != null) {
                    hashMap.put("addtime", simpleDateFormat.format(simpleDateFormat.parse(hashMap.get("addtime").toString())));
                } else {
                    hashMap.put("addtime", "");
                }
                hashMap.put("salenum", "0");//销售量
                CliengGuid = hashMap.get("productIcon").toString();
                attachmap = new HashMap<>();
                attachmap.put("clientGuid=", CliengGuid);
                listattach = inewcommon.findlist("frame_attachinfo", "*", attachmap, "", "", 0, 20);
                if (listattach.size() > 0) {
                    hashMap.put("filepath", listattach.get(0).get("filepath"));
                } else {
                    hashMap.put("filepath", "../../../../images/other/touxiang_3.png");
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
            FramepeopleGoodat goodat = new FramepeopleGoodat();
            Map<String, Object> map = new HashMap<>();
            map.put("rowGuid", rowGuid);
            goodat = iframePeopleGoodAt.find(map);
            //获取商品销售个数
            String conditionnew = " and productguid='" + rowGuid + "'";
            int productcount = inewcommon.findlist("smart_project", null, conditionnew);
            map.put("dealcount", productcount);
            //获取商品销售金额
            conditionnew = " and  productguid='" + rowGuid + "' ";
            List<HashMap<String, Object>> newlist = inewcommon.findlist("smart_project", " sum(PROJECT_AMT) as PROJECT_AMT,productguid ", null, conditionnew, "", 0, 10);
            String dealmoney = newlist.get(0).get("PROJECT_AMT") == null ? "0" : newlist.get(0).get("PROJECT_AMT").toString();
            map.put("dealmoney", dealmoney);

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
