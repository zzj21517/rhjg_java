package com.lxh.rhjg.rest;


import com.alibaba.fastjson.JSONObject;
import com.lxh.rhjg.active.api.IPeople;
import com.lxh.rhjg.active.api.IProject;
import com.lxh.rhjg.active.api.Icommon;
import com.lxh.rhjg.active.api.SMART_PROJECT;
import com.lxh.rhjg.common.util.Common;
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
@RequestMapping("/RMarkController")
public class RMarkController {
    @Autowired
    Icommon icommon;
    @Autowired
    IPeople iPeople;
    @Autowired
    IProject iProject;

    /*
     *
     */
    @RequestMapping(value = "/SaveMark", method = RequestMethod.POST)
    public String SaveMark(@RequestBody String params) throws IOException {
        JSONObject rJsonObject = new JSONObject();
        JSONObject jsonObject = JSONObject.parseObject(params);
        jsonObject = JSONObject.parseObject(jsonObject.getString("param"));
        String userId = jsonObject.get("uid").toString();
        String workId = jsonObject.get("pid").toString();
        String formId = jsonObject.get("fid").toString();
        String mark = jsonObject.get("mark").toString();
        String key = jsonObject.get("key").toString();
        String percent = jsonObject.get("percent").toString();
        Common common = new Common();
        String sid = common.GetSSIDStr("MARK_CODE");
        SimpleDateFormat sDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String datatime = sDateFormat.format(new Date());
        if (key.equals(sid)) {
            SMART_MARK smartMark = new SMART_MARK();
            smartMark.setGUID(UUID.randomUUID().toString());
            smartMark.setUSER_ID(userId);
            smartMark.setWORK_ID(workId);
            smartMark.setFORM_ID(formId);
            smartMark.setSTATUS("1");
            smartMark.setDATATIME(datatime);
            smartMark.setMARK_CONTENT(mark);
            smartMark.setPERCENT(percent);
            iProject.insertMark(smartMark);
            rJsonObject.put("code", "200");
            //发送信息，提示进度有更新
            SMART_PROJECT smartProject = iProject.findProject("PROJECT_NUM='"+workId+"'").get(0);
            String linkTel = smartProject.getCUST_ID();
            //self::SendMessage($linkTel, "【融汇精工】：尊敬的客户，您发布的项目进度有更新，请及时登录小程序查看。感谢您的信任，祝您生活愉快。");
        } else {
            rJsonObject.put("code", "400");
        }
        return rJsonObject.toJSONString();
    }

    /*
     *
     */
    @RequestMapping(value = "/GetMark", method = RequestMethod.POST)
    public String GetMark(@RequestBody String params) throws IOException {
        JSONObject rJsonObject = new JSONObject();
        JSONObject jsonObject = JSONObject.parseObject(params);
        jsonObject = JSONObject.parseObject(jsonObject.getString("param"));
        String workId = jsonObject.get("pid").toString();
        String key = jsonObject.get("key").toString();
        Common common = new Common();
        String sid = common.GetSSIDStr("MARK_CODE");
        if (key.equals(sid)) {
            List<HashMap<String, Object>> list = iProject.getremark1(workId);
            //查询项目的支付状态，去支付
            List<HashMap<String, Object>> list2 = iProject.getremark2(workId);
            rJsonObject.put("code", "200");
            rJsonObject.put("detail", list);
            rJsonObject.put("subjects", list2);
        } else {
            rJsonObject.put("code", "400");
        }
        return rJsonObject.toJSONString();
    }

    @RequestMapping(value = "/PayLeftMoney", method = RequestMethod.POST)
    public String PayLeftMoney(@RequestBody String params) throws IOException {
        JSONObject rJsonObject = new JSONObject();
        JSONObject jsonObject = JSONObject.parseObject(params);
        jsonObject = JSONObject.parseObject(jsonObject.getString("param"));
        String uid = jsonObject.get("uid").toString();
        String fid = jsonObject.get("fid").toString();
        String PayFlag = jsonObject.get("PayFlag").toString();
        String key = jsonObject.get("key").toString();
        Common common = new Common();
        String sid = common.GetSSIDStr("MARK_CODE");
        if (key.equals(sid)) {
            //更新支付状态 0：PAY_STATYS 1:PAY_STATYS10 2:PAY_STATYS20

            if ("0".equals(PayFlag)) {
                icommon.updateCommon("SMART_PROJECT", "PAY_STATUS='01',STATUS='02'", " and PROJECT_NUM='" + fid + "' AND CUST_ID='" + uid + "'");
                rJsonObject.put("code", "200");
            } else if ("1".equals(PayFlag)) {
                icommon.updateCommon("SMART_PROJECT", "PAY_STATUS10 ='1',STATUS='02'", " and PROJECT_NUM='" + fid + "' AND CUST_ID='" + uid + "'");
                rJsonObject.put("code", "200");
            } else if ("2".equals(PayFlag)) {
                icommon.updateCommon("SMART_PROJECT", "PAY_STATUS20 ='1',STATUS='02'", " and PROJECT_NUM='" + fid + "' AND CUST_ID='" + uid + "'");
                rJsonObject.put("code", "200");
            } else {
                rJsonObject.put("code", "200");
            }
        } else {
            rJsonObject.put("code", "400");
        }
        return rJsonObject.toJSONString();
    }
}
