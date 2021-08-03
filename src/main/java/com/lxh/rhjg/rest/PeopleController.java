package com.lxh.rhjg.rest;

import com.alibaba.fastjson.JSONObject;
import com.lxh.rhjg.active.api.*;
import com.lxh.rhjg.common.util.Common;
import com.lxh.rhjg.common.util.HttpClient;
import com.lxh.rhjg.common.util.JGConstant;
import com.lxh.rhjg.common.util.MD5Utils;
import com.lxh.rhjg.entity.*;
import com.lxh.rhjg.judge.api.IJudge;
import com.lxh.rhjg.photo.api.IPhoto;
import com.lxh.rhjg.reports.api.IReports;
import com.lxh.rhjg.verifycode.api.IVerifycode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartRequest;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.*;
@RestController
@RequestMapping("/PeopleController")
public class PeopleController {
    @Autowired
    IPeople iPeople;
    @Autowired
    IProject iProject;
    @Autowired
    IVerifycode iVerifycode;
    @Autowired
    IReports iReports;
    @Autowired
    IPhoto iPhoto;
    @Autowired
    Icommon icommon;
    @Autowired
    IJudge iJudge;
    //获取剩余投标的次数
    @RequestMapping(value = "/GetLeftCount", method = RequestMethod.POST)
    public String GetLeftCount(@RequestBody String params) throws IOException {
        JSONObject rJsonObject = new JSONObject();
        JSONObject jsonObject = JSONObject.parseObject(params);
        jsonObject=JSONObject.parseObject(jsonObject.getString("param"));
        String uid = jsonObject.get("uid").toString();
         List<JSONObject> list=iPeople.GetLeftCount(uid);
           if(list.size()==0){
               rJsonObject.put("code","250");
           }
           else{
               rJsonObject.put("code","200");
               rJsonObject.put("results",list);
           }
           return rJsonObject.toJSONString();
    }
    //获取用户详细数据
    @RequestMapping(value = "/GetPeopleDetail", method = RequestMethod.POST)
    public String GetPeopleDetail(@RequestBody String params) throws IOException {
        JSONObject rJsonObject = new JSONObject();
        JSONObject jsonObject = JSONObject.parseObject(params);
        jsonObject=JSONObject.parseObject(jsonObject.getString("param"));
        String uid = jsonObject.get("uid").toString();
         List<PeopleDetail>  list=iPeople.GetPeopleDetail(uid);
        rJsonObject.put("code","200");
        rJsonObject.put("results",list);
        return rJsonObject.toJSONString();
    }
    //获取用户详细数据（新）
    @RequestMapping(value = "/GetNewPeopleDetail", method = RequestMethod.POST)
    public String GetNewPeopleDetail(@RequestBody String params) throws IOException {
        JSONObject rJsonObject = new JSONObject();
        JSONObject jsonObject = JSONObject.parseObject(params);
        jsonObject=JSONObject.parseObject(jsonObject.getString("param"));
        String uid = jsonObject.get("uid").toString();
        List<NewPeopleDetail1> list= iPeople.GetNewPeopleDetail1(uid);
        //统计完成项目的类型
        List<HashMap<String,Object>> list2= iPeople.GetNewPeopleDetail2(uid);
        //统计完成项目的列表
        List<HashMap<String,Object>> list3= iPeople.GetNewPeopleDetail3(uid);
        //统计对用户的评价
        List<HashMap<String,Object>> list4= iPeople.GetNewPeopleDetail4(uid);
        rJsonObject.put("code","200");
        rJsonObject.put("results",list);
        rJsonObject.put("classify",list2);
        rJsonObject.put("judge",list4);
        rJsonObject.put("project",list3);
        return rJsonObject.toJSONString();
    }
    //成为推荐人员
    @RequestMapping(value = "/MakeTuiJian", method = RequestMethod.POST)
    public String MakeTuiJian(@RequestBody String params) throws IOException {
        JSONObject rJsonObject = new JSONObject();
        JSONObject jsonObject = JSONObject.parseObject(params);
        jsonObject=JSONObject.parseObject(jsonObject.getString("param"));
        String uid = jsonObject.get("uid").toString();
        String card = jsonObject.get("card").toString();
        String type = jsonObject.get("selectedType").toString();
        SimpleDateFormat sDateFormat=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        //日期
        Calendar c = Calendar.getInstance();
        c.setTime(new Date());

        //增加推荐人员日期 2018.01.07
        //c.add(Calendar.MONTH,1);
        Date recommend_date=c.getTime();
        switch (type){
            case "00": //月度
                c.add(Calendar.MONTH,1);
                 recommend_date= c.getTime();
                break;
            case "01": //季度
                c.add(Calendar.MONTH,3);
                recommend_date= c.getTime();
                break;
            case "02": //年度
                c.add(Calendar.MONTH,12);
                recommend_date= c.getTime();
                break;
            default:
                c.add(Calendar.MONTH,1);
                recommend_date= c.getTime();
                break;
        }
        try{
        iPeople.updateCommon("SMART_PEOPLE","USER_TYPE ='01',RECOMMEND_TIME='"+sDateFormat.format(recommend_date)+"',USER_TYPE_FLAG='"+type +"'"," and USER_ID='"+uid+"'");
        if(!"99".equals(card)){
            //更新会员卡状态
            iPeople.updateCommon("SMART_CARD","STATUS= '00'","  and GUID='"+card+"' AND STATUS='02'");
            rJsonObject.put("code","200");
        }
        else{
            rJsonObject.put("code","400");
        }
        }catch (Exception e){
            rJsonObject.put("code","400");
        }
        return rJsonObject.toJSONString();

    }
    /*
     * 重置用户密码
     */
    @RequestMapping(value = "/ResetPassword", method = RequestMethod.POST)
    public String ResetPassword(@RequestBody String params) throws IOException {
        JSONObject rJsonObject = new JSONObject();
        JSONObject jsonObject = JSONObject.parseObject(params);
        jsonObject=JSONObject.parseObject(jsonObject.getString("param"));
        String uid = jsonObject.get("uid").toString();
        String pwd = jsonObject.get("pwd").toString();
        String verify = jsonObject.get("verify").toString();
        String  passwd = MD5Utils.stringToMD5(pwd);
        if(IsUidExist(uid)){
            if(IsVerifyCode(uid,verify)){
                try{
                 iPeople.updateCommon("SMART_PEOPLE","PASS_WORD='"+passwd+"'"," and USER_ID='"+uid+"'");
                    rJsonObject.put("code","200");
                }catch (Exception e){
                    rJsonObject.put("code","400");
                }
            }else{
                rJsonObject.put("code","300");
            }
        }else{
            rJsonObject.put("code","100");
        }
        return  rJsonObject.toJSONString();
    }
    /*
     * 成为会员
     */
    @RequestMapping(value = "/MakeHuiYuan", method = RequestMethod.POST)
    public String MakeHuiYuan(@RequestBody String params) throws IOException {
        JSONObject rJsonObject = new JSONObject();
        JSONObject jsonObject = JSONObject.parseObject(params);
        jsonObject=JSONObject.parseObject(jsonObject.getString("param"));
        //利用服务器上的缓存判断，避免接口调用出现问题
        //设置会员的次数，大众会员5次，黄金会员10次，钻石会员20次
        String uid = jsonObject.get("uid").toString();
        SimpleDateFormat sDateFormat=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String datatime=sDateFormat.format(new Date());
        String level = jsonObject.get("level").toString();
        String cardId = jsonObject.get("cardId").toString();
        //充值类型（暂不使用） 2019.03.05
        String type  = jsonObject.get("selectType").toString();
        int count = 0;
        //注意修改
        //日期
        Calendar c = Calendar.getInstance();
        c.setTime(new Date());
        Date huiyuan_date =c.getTime();
        switch (level){
            case "00":
                count = JGConstant.VAR_PUTONG;
                break;
            case "01"://  //按照月充值
                count =JGConstant.VAR_HUANGJIN;
                c.add(Calendar.MONTH,1);
                huiyuan_date=c.getTime();
                break;
            case "02"://按照季度度充值
                count = JGConstant.VAR_ZUANSHI;
                c.add(Calendar.MONTH,3);
                huiyuan_date=c.getTime();
                break;
            case "03":////按照年度充值
                count = JGConstant.VAR_NIANDU;
                c.add(Calendar.MONTH,12);
                huiyuan_date=c.getTime();
                break;
            default:
                c.add(Calendar.MONTH,1);
                huiyuan_date=c.getTime();
                break;
        }
        String sql=" LEVEL ='"+level+"',TOTAL_COUNT='"+count+"',MEMBER_TIME='"+huiyuan_date+"', LEVEL_TYPE='"+type+"' ";
        iPeople.updateCommon("SMART_PEOPLE",sql,"  and USER_ID='"+uid+"'");
        if(!"99".equals(cardId)){
            sql="STATUS= '00'";
            try{
            iPeople.updateCommon("SMART_CARD",sql," and  USER_ID='GUID='"+cardId+"' AND STATUS='02'");
                rJsonObject.put("code","200");
            }catch (Exception e){
                rJsonObject.put("code","400");
            }
        }
        return  rJsonObject.toJSONString();
    }
    /*
     * 获取验证码
     */
    @RequestMapping(value = "/GetVerifyCode", method = RequestMethod.POST)
    public String GetVerifyCode(@RequestBody String params) throws IOException {
        JSONObject rJsonObject = new JSONObject();
        JSONObject jsonObject = JSONObject.parseObject(params);
        jsonObject=JSONObject.parseObject(jsonObject.getString("param"));
        String uid = jsonObject.get("uid").toString();
        /*
         * 1.产生随机数
         * 2.插入数据表
         * 3.发送短信
         */
         String code="100";
         SMART_PEOPLE smartPeople=iPeople.findPeople("USER_ID",uid);
         if(smartPeople==null){//账号不存在
           if(HasVerifyCode(uid)){//已经发送过
               code="300";
           }
           else{
               //账号不存在，发送短信
               int verifyCode = (int) ((Math.random() * 9 + 1) * 100000);
               SimpleDateFormat sDateFormat=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
               String datatime=sDateFormat.format(new Date());
               String guid=UUID.randomUUID().toString();
               SMART_VERIFY smartVerify=new SMART_VERIFY();
               smartVerify.setGUID(guid);
               smartVerify.setUSER_ID(uid);
               smartVerify.setVERIFY_CODE(String.valueOf(verifyCode));
               smartVerify.setDATATIME(datatime);
               iVerifycode.InsertVerifycode(smartVerify);
               String [] statusStr={};
               String smsapi="http://api.smsbao.com/";
               String user = "szgf"; //短信平台帐号
               String pass = MD5Utils.stringToMD5("shihoufeng2012"); //短信平台密码
               String content="【融汇精工】您的验证码是:"+verifyCode+",验证码10分钟内有效.若非本人操作请忽略此消息";//要发送的短信内容
               String phone=uid;
               String sendurl= smsapi+"sms?u="+user+"&p="+pass+"&m="+phone+"&c="+ URLEncoder.encode(content,"utf-8");
               String result= HttpClient.doPost(sendurl,"");
                  code = result;
               }
           }
            rJsonObject.put("code",code);
          return  rJsonObject.toJSONString();
     }
    /*
     * 获取验证码-重置密码
     */
    @RequestMapping(value = "/GetVerifyCode001", method = RequestMethod.POST)
    public String GetVerifyCode001(@RequestBody String params) throws IOException {
        /*
         * 1.产生随机数
         * 2.插入数据表
         * 3.发送短信
         */
        JSONObject rJsonObject = new JSONObject();
        JSONObject jsonObject = JSONObject.parseObject(params);
        jsonObject=JSONObject.parseObject(jsonObject.getString("param"));
        String uid = jsonObject.get("uid").toString();
        String code="100";
        SMART_PEOPLE smartPeople=iPeople.findPeople("USER_ID",uid);
        if(smartPeople!=null){
            //账号存在
            int verifyCode = (int) ((Math.random() * 9 + 1) * 100000);
            SimpleDateFormat sDateFormat=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String datatime=sDateFormat.format(new Date());
            String guid=UUID.randomUUID().toString();
            SMART_VERIFY smartVerify=new SMART_VERIFY();
            smartVerify.setGUID(guid);
            smartVerify.setUSER_ID(uid);
            smartVerify.setVERIFY_CODE(String.valueOf(verifyCode));
            smartVerify.setDATATIME(datatime);
            iVerifycode.InsertVerifycode(smartVerify);
            //插入成功，发送短信
         /*   $statusStr = array(
                    "0" => "短信发送成功",
                    "-1" => "参数不全",
                    "-2" => "服务器空间不支持,请确认支持curl或者fsocket，联系您的空间商解决或者更换空间！",
                    "30" => "密码错误",
                    "40" => "账号不存在",
                    "41" => "余额不足",
                    "42" => "帐户已过期",
                    "43" => "IP地址限制",
                    "50" => "内容含有敏感词"
				);*/

            String [] statusStr={};
            String smsapi="http://api.smsbao.com/";
            String user = "szgf"; //短信平台帐号
            String pass = MD5Utils.stringToMD5("shihoufeng2012"); //短信平台密码
            String content="【融汇精工】您正在重置密码，验证码是"+verifyCode+",验证码10分钟内有效.若非本人操作请忽略此消息";//要发送的短信内容
            String phone = uid;//要发送短信的手机号码
            String sendurl = smsapi+"sms?u="+user+"&p="+pass+"&m="+phone+"&c="+URLEncoder.encode(content);
            String result =HttpClient.doPost(sendurl,"") ;
            code = result;
    }
        rJsonObject.put("code",code);
        return  rJsonObject.toJSONString();
    }
    /*
     * 插入举报数据
     */
    @RequestMapping(value = "/SaveReport", method = RequestMethod.POST)
    public String SaveReport(@RequestBody String params) throws IOException {
        JSONObject rJsonObject = new JSONObject();
        JSONObject jsonObject = JSONObject.parseObject(params);
        jsonObject=JSONObject.parseObject(jsonObject.getString("param"));
        String fid = jsonObject.get("fid").toString();
        String title = jsonObject.get("title").toString();
        String content = jsonObject.get("title").toString();
        String link_tel = jsonObject.get("tel").toString();
        String link_eamil = jsonObject.get("mail").toString();
        String guid=UUID.randomUUID().toString();
        SimpleDateFormat sDateFormat=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String datatime=sDateFormat.format(new Date());
        SMART_REPORTS smartReports=new SMART_REPORTS();
        smartReports.setGUID(guid);
        smartReports.setTITLE(title);
        smartReports.setCONTENT(content);
        smartReports.setDATATIME(datatime);
        smartReports.setLINK_TEL(link_tel);
        smartReports.setLINK_EMAIL(link_eamil);
        smartReports.setCUST_ID(fid);
        try{
        iReports.InsertReports(smartReports);
            rJsonObject.put("code","200");
        }catch (Exception e){
            rJsonObject.put("code","400");
        }
        return  rJsonObject.toJSONString();
    }
    /*
     * 上传头像
     */
    @RequestMapping(value = "/UpLoadPhoto", method = RequestMethod.POST)
    public String UpLoadPhoto(HttpServletRequest request) throws IOException {
        JSONObject rJsonObject = new JSONObject();
        MultipartRequest multquest = (MultipartRequest) request;
        Map<String , MultipartFile> map = multquest.getFileMap();
        //addAttachFile(attachguid, filemap, attachService);
        String flag="";
        String uid="";
        if("00".equals(flag)){
        iPhoto.deletePhoto(uid);
        }
        return  rJsonObject.toJSONString();
    }
    /*
     * 获取用户信息
     */
    @RequestMapping(value = "/GetUserInfo", method = RequestMethod.POST)
    public String GetUserInfo(@RequestBody String params) throws IOException {
        JSONObject rJsonObject = new JSONObject();
        JSONObject jsonObject = JSONObject.parseObject(params);
        jsonObject=JSONObject.parseObject(jsonObject.getString("param"));
        String uid = jsonObject.get("uid").toString();
        List<HashMap<String,Object>> list=iPeople.GetUserInfo(uid);
        rJsonObject.put("code","200");
        rJsonObject.put("results",list);
        return rJsonObject.toJSONString();
    }

    /*
     * 认证用户
     */
    @RequestMapping(value = "/AuthenMan", method = RequestMethod.POST)
    public String AuthenMan(@RequestBody String params) throws IOException {
        JSONObject rJsonObject = new JSONObject();
        JSONObject jsonObject = JSONObject.parseObject(params);
        jsonObject=JSONObject.parseObject(jsonObject.getString("param"));
        String uid = jsonObject.get("uid").toString();
        String pwd = jsonObject.get("pwd").toString();
        String code = jsonObject.get("code").toString();
         String true_pwd=MD5Utils.stringToMD5(pwd);
         Common common=new Common();
         String openId= common.GetOpenid(code);
        List<HashMap<String,Object>> listauthen= iPeople.AuthenMan(uid,true_pwd);
        if(listauthen==null){
            rJsonObject.put("code","250");
        }else{
            //登陆成功，更新OPEN_ID
            iPeople.updateCommon("SMART_PEOPLE_EXT","OPEN_ID='"+openId+"'"," and USER_ID='"+uid+"'");
            rJsonObject.put("code","200");
            rJsonObject.put("results",listauthen);
        }
        return rJsonObject.toJSONString();
    }
    /*
     * 注册新用户
     */
    @RequestMapping(value = "/RegisterUser", method = RequestMethod.POST)
    public String RegisterUser(@RequestBody String params) throws IOException {
        JSONObject rJsonObject = new JSONObject();
        JSONObject jsonObject = JSONObject.parseObject(params);
        jsonObject=JSONObject.parseObject(jsonObject.getString("param"));
        String uid = jsonObject.get("uid").toString();
        String pwd = jsonObject.get("pwd").toString();
        String professon = jsonObject.get("professon").toString();
        String sheng = jsonObject.get("sheng").toString();
        String shi = jsonObject.get("shi").toString();
        String xian = jsonObject.get("xian").toString();
        String name = jsonObject.get("name").toString();
        String content = jsonObject.get("content").toString();
        String verifyCode = jsonObject.get("verify").toString();
        String guid=UUID.randomUUID().toString();
        SimpleDateFormat sDateFormat=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String datatime=sDateFormat.format(new Date());
        String passwd = MD5Utils.stringToMD5(pwd);
        String code = jsonObject.get("code").toString();
        String TJ = jsonObject.get("shortCode").toString();
        String photo = jsonObject.get("photo").toString();
        String TeamType = jsonObject.get("TeamType").toString();
        String userPhoto = "https://www.ronghuijinggong.com/Uploads/MyPhoto/2017-01-01/people.jpg";
        if("".equals(photo)){//默认图片
            photo =userPhoto;
        }
         //判断账号是否存在
        if(IsUidExist(uid)){
            rJsonObject.put("code","100");
        }else if(!IsVerifyCode(uid,verifyCode)){
            rJsonObject.put("code","300");
        }else{
            try{
            SMART_PEOPLE smartPeople=new SMART_PEOPLE();
            smartPeople.setGUID(guid);
            smartPeople.setUSER_ID(uid);
            smartPeople.setNICK_NAME(name);
            smartPeople.setSEX("00");
            smartPeople.setPASS_WORD(passwd);
            smartPeople.setDATATIME(datatime);
            smartPeople.setMREGIE_ID(sheng);
            smartPeople.setCREGIE_ID(shi);
            smartPeople.setQREGIE_ID(xian);
            smartPeople.setPROFESSION(professon);
            smartPeople.setMONEY("0");
            smartPeople.setLEVEL("00");
            smartPeople.setUSER_TYPE("00");
            smartPeople.setCONTENT(content);
            smartPeople.setMEMBER_TIME(datatime);
            smartPeople.setREGISTER_TIME(datatime);
            smartPeople.setIMG_PATH(photo);
            //插入SMART_USERS
            iPeople.insertUser(smartPeople);
            Common common=new Common();
             String shortcode=common.GetRuleStr("SHORT_NUM");
            SMART_PEOPLE_EXT smartPeopleExt=new SMART_PEOPLE_EXT();
            smartPeopleExt.setUSER_ID(uid);
            smartPeopleExt.setGAME_POINT("60");
            smartPeopleExt.setSHORT_CODE(shortcode);
            smartPeopleExt.setDATATIME(datatime);
            smartPeopleExt.setINVITE_MAN(TJ);
            smartPeopleExt.setTEAM_TYPE(TeamType);

            iPeople.insertPeopleExt(smartPeopleExt);
            //赠送推荐人员20积分
            if(!"".equals(TJ)){
             SMART_PEOPLE_EXT smartPeopleExt1= iPeople.GetSmartPeopleExt("SHORT_CODE",TJ);
                if(smartPeopleExt1!=null){
                    int game_point=Integer.parseInt(smartPeopleExt1.getGAME_POINT())+20;
                    String sql=" GAME_POINT="+game_point;
                    iPeople.updateCommon("SMART_PEOPLE_EXT",sql," and SHORT_CODE='"+TJ+"'");
                    rJsonObject.put("code","200");
                }
            }
            }catch (Exception e){
                rJsonObject.put("code","400");
            }
        }
          return rJsonObject.toJSONString();
    }
    /*
     * 增加账户的保证金
     */
    @RequestMapping(value = "/UpdateAccount", method = RequestMethod.POST)
    public String UpdateAccount(@RequestBody String params) throws IOException {
        JSONObject rJsonObject = new JSONObject();
        JSONObject jsonObject = JSONObject.parseObject(params);
        jsonObject=JSONObject.parseObject(jsonObject.getString("param"));
        String uid = jsonObject.get("uid").toString();
        String amt = jsonObject.get("amt").toString();
        SMART_PEOPLE smartPeople=iPeople.findPeople("USER_ID",uid);
        double money=0;
        if(smartPeople!=null){
            money=Double.valueOf(smartPeople.getMONEY());
            double total = money +Double.valueOf(amt);
            try{
             iPeople.updateCommon("SMART_PEOPLE","MONEY="+total," and USER_ID='"+uid+"'");
                rJsonObject.put("code","200");
            }catch (Exception e){
                rJsonObject.put("code","400");
            }
        }
        else{
            rJsonObject.put("code","200");
        }
        return rJsonObject.toJSONString();
    }
    /*
     * 更新用户信息
     */
    @RequestMapping(value = "/UpdateUserInfo", method = RequestMethod.POST)
    public String UpdateUserInfo(@RequestBody String params) throws IOException {
        JSONObject rJsonObject = new JSONObject();
        JSONObject jsonObject = JSONObject.parseObject(params);
        jsonObject=JSONObject.parseObject(jsonObject.getString("param"));
        String uid = jsonObject.get("uid").toString();
        String professon = jsonObject.get("professon").toString();
        String name = jsonObject.get("name").toString();
        String content = jsonObject.get("content").toString();
        String birthday = jsonObject.get("birthday").toString();
        String workYear = jsonObject.get("workYear").toString();
        String sheng = jsonObject.get("sheng").toString();
        String soft = jsonObject.get("soft").toString();
        String xian = jsonObject.get("xian").toString();
        String shi = jsonObject.get("shi").toString();
        SimpleDateFormat sDateFormat=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String datatime=sDateFormat.format(new Date());
        try{
        String sql="NICK_NAME='"+name+"',PROFESSION='"+professon+"',CONTENT = '"+content+"',DATATIME ='"+datatime+"',MREGIE_ID='"+sheng+"',CREGIE_ID='"+shi+"'," +
                "  QREGIE_ID='"+xian+"' WHERE USER_ID='"+uid+"' ";
        iPeople.updateCommon("SMART_PEOPLE",sql," and USER_ID='"+uid+"'");
        sql="BIRTHDAY='"+birthday+"',USE_SOFT='"+soft+"',WORK_YEAR='"+workYear+"',IS_MATAIN='1'";
        iPeople.updateCommon("SMART_PEOPLE_EXT",sql," and USER_ID='"+uid+"'");
            rJsonObject.put("code","200");
        }catch (Exception e){
            rJsonObject.put("code","400");
        }
        return rJsonObject.toJSONString();
    }
    /*
     * 获取用户详细数据
     */
    @RequestMapping(value = "/GetTongJi", method = RequestMethod.POST)
    public String GetTongJi(@RequestBody String params) throws IOException {
        JSONObject rJsonObject = new JSONObject();
        JSONObject jsonObject = JSONObject.parseObject(params);
        jsonObject=JSONObject.parseObject(jsonObject.getString("param"));
        Map<String ,Object> map=new HashMap<>();
        map.put("ADMIN","0");
        List<HashMap<String,Object>> list= iPeople.GetTongJi(map);
        if(list!=null){
            rJsonObject.put("code","200");
            rJsonObject.put("results",list);
        }else{
            rJsonObject.put("code","400");
        }
        return rJsonObject.toJSONString();
    }
    /*
     * 删除用户
     */
    @RequestMapping(value = "/DeleteUser", method = RequestMethod.POST)
    public String DeleteUser(@RequestBody String params) throws IOException {
        JSONObject rJsonObject = new JSONObject();
        JSONObject jsonObject = JSONObject.parseObject(params);
        jsonObject=JSONObject.parseObject(jsonObject.getString("param"));
        String uid = jsonObject.get("uid").toString();
        try{
          icommon.deleteCommon("SMART_PEOPLE","USER_ID='"+uid+"'");
            rJsonObject.put("code","200");
        }catch (Exception e){
            rJsonObject.put("code","400");
        }
        return rJsonObject.toJSONString();
    }
    /*
     * 更新用户信息,管理员
     */
    @RequestMapping(value = "/UpdateUserInfo001", method = RequestMethod.POST)
    public String UpdateUserInfo001(@RequestBody String params) throws IOException {
        JSONObject rJsonObject = new JSONObject();
        JSONObject jsonObject = JSONObject.parseObject(params);
        jsonObject=JSONObject.parseObject(jsonObject.getString("param"));
        String uid = jsonObject.get("uid").toString();
        String level = jsonObject.get("level").toString();
        String total = jsonObject.get("total").toString();
        String member = jsonObject.get("member").toString();
        String money = jsonObject.get("money").toString();
        String user = jsonObject.get("user").toString();
        SimpleDateFormat sDateFormat=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String datatime=sDateFormat.format(new Date());
        String levelType = jsonObject.get("levelType").toString();
        String recommendTime = jsonObject.get("recommendTime").toString();
        String user_type_flag = jsonObject.get("user_type_flag").toString();
        String choulaotuoguan = jsonObject.get("choulaotuoguan").toString();
        String sql="UPDATE SMART_PEOPLE SET LEVEL='"+level+"',TOTAL_COUNT='"+total+"',";
        sql+="MEMBER_TIME = '"+member+"',DATATIME ='"+datatime+"', MONEY='"+money+"',USER_TYPE='"+user+"',";
        sql+="LEVEL_TYPE='"+levelType+"',RECOMMEND_TIME='"+recommendTime+"',USER_TYPE_FLAG='"+user_type_flag+"'";
        try{
        icommon.updateCommon("SMART_PEOPLE",sql," and USER_ID='"+uid+"'");
        sql="NOUSE_ACCOUNT='"+choulaotuoguan+"'";
        icommon.updateCommon("SMART_PEOPLE_EXT",sql," and USER_ID='"+uid+"'");
            rJsonObject.put("code","200");
        }catch (Exception e){
            rJsonObject.put("code","400");
        }
        return rJsonObject.toJSONString();
    }
    /*
     * 对项目的用户进行评价
     */
    @RequestMapping(value = "/JudgeProjectPeople", method = RequestMethod.POST)
    public String JudgeProjectPeople(@RequestBody String params) throws IOException {
        JSONObject rJsonObject = new JSONObject();
        JSONObject jsonObject = JSONObject.parseObject(params);
        jsonObject=JSONObject.parseObject(jsonObject.getString("param"));
        String uid = jsonObject.get("uid").toString();
        String pid = jsonObject.get("pid").toString();
        String content = jsonObject.get("content").toString();
        String key = jsonObject.get("key").toString();
        String sid=icommon.findSmartssid("rule_id","PEOPLE").getSSID_VALUE();
        String custId="";
        if(sid.equals(key)){
            Map<String ,Object> map=new HashMap<>();
            map.put("STATUS","06");
            map.put("PROJECT_NUM",pid);
           List<SMART_PROJECT_USER>  projectUserList= iPeople.GetProjectUser(map);
           if(projectUserList!=null)
           {
               //添加评价
               SimpleDateFormat sDateFormat=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
               String datatime=sDateFormat.format(new Date());
               custId=projectUserList.get(0).getUSER_ID();
               SMART_JUDGE smartJudge=new SMART_JUDGE();
               smartJudge.setGUID(UUID.randomUUID().toString());
               smartJudge.setCUST_ID(custId);
               smartJudge.setPROJECT_NUM(pid);
               smartJudge.setDATATIME1(datatime);
               smartJudge.setJUDGE_USER(uid);
               smartJudge.setCONTENT(content);
               try{
               iJudge.insert(smartJudge);
                   rJsonObject.put("code","200");
               }catch (Exception e){
                   rJsonObject.put("code","400");
               }

           }

        }else{
            rJsonObject.put("code","400");
        }
        return rJsonObject.toJSONString();
    }
    /*
     * 用户支付
     */
    @RequestMapping(value = "/PayAccount", method = RequestMethod.POST)
    public String PayAccount(@RequestBody String params) throws IOException {
        JSONObject rJsonObject = new JSONObject();
        JSONObject jsonObject = JSONObject.parseObject(params);
        jsonObject=JSONObject.parseObject(jsonObject.getString("param"));
        //更改账号，更新状态
        String uid = jsonObject.get("uid").toString();
        String pid = jsonObject.get("pid").toString();
        String amt = jsonObject.get("amt").toString();
        SMART_PEOPLE smartPeople= iPeople.findPeople("USER_ID",uid);
        double money=0;
           if(smartPeople!=null){
               money= Double.valueOf(smartPeople.getMONEY());
           }
          double total = money - Double.valueOf(amt);
           if(total<0){
               rJsonObject.put("code","100"); //余额不足
           }else {
               try {
                   icommon.updateCommon("SMART_PEOPLE", "MONEY='" + total + "'", " and USER_ID='" + uid + "'");
                   icommon.updateCommon("SMART_PEOPLE", "PAY_STATUS='01',PAY_AMT='" + amt + "',STATUS='02'", " and PROJECT_NUM='" + pid + "' AND USER_ID='" + uid + "'");
                   rJsonObject.put("code","200");
               }catch (Exception e){
                   rJsonObject.put("code","400");
               }
           }
        return rJsonObject.toJSONString();
    }
    /*
     * 退款，获取退款的天数
     */
    @RequestMapping(value = "/GetDays", method = RequestMethod.POST)
    public String GetDays(@RequestBody String params) throws IOException {
        JSONObject rJsonObject = new JSONObject();
        JSONObject jsonObject = JSONObject.parseObject(params);
        jsonObject=JSONObject.parseObject(jsonObject.getString("param"));
        //更改账号，更新状态
        String uid = jsonObject.get("uid").toString();
        Map<String ,Object> map=new HashMap<>();
        map.put("STATUS","06");
        map.put("ITEM_TYPE","03");
        map.put("USER_ID",uid);
       List<HashMap<String,Object>>  list=iPeople.GetDays(map);
        if(list!=null){
            rJsonObject.put("code","200");
            rJsonObject.put("results",list);
        }else{
            rJsonObject.put("code","400");
        }
        return rJsonObject.toJSONString();
    }
    /*
     *获取账户不可用金额
     */
    @RequestMapping(value = "/GetNouseAccount", method = RequestMethod.POST)
    public String GetNouseAccount(@RequestBody String params) throws IOException {
        JSONObject rJsonObject = new JSONObject();
        JSONObject jsonObject = JSONObject.parseObject(params);
        jsonObject=JSONObject.parseObject(jsonObject.getString("param"));
        String uid = jsonObject.get("uid").toString();
       SMART_PEOPLE_EXT smartPeopleExt= iPeople.GetSmartPeopleExt("USER_ID",uid);
       if(smartPeopleExt!=null){
           rJsonObject.put("code","200");
           rJsonObject.put("results",smartPeopleExt);
       }else{
           rJsonObject.put("code","400");
       }
        return rJsonObject.toJSONString();
    }
    /*
     *增加账户的保证金
     */
    @RequestMapping(value = "/UpdateNouseAccount", method = RequestMethod.POST)
    public String UpdateNouseAccount(@RequestBody String params) throws IOException {
        JSONObject rJsonObject = new JSONObject();
        JSONObject jsonObject = JSONObject.parseObject(params);
        jsonObject=JSONObject.parseObject(jsonObject.getString("param"));
        String uid = jsonObject.get("uid").toString();
        String amt = jsonObject.get("amt").toString();
        String key = jsonObject.get("key").toString();
       String sid=icommon.findSmartssid("rule_id","PEOPLE").getSSID_VALUE();
       if(sid.equals(key)){
         SMART_PEOPLE_EXT smartPeopleExt=iPeople.GetSmartPeopleExt("USER_ID",uid);
         double money=0;
         if(smartPeopleExt!=null){
             money= Double.valueOf(smartPeopleExt.getNOUSE_ACCOUNT());
             double total = money + Double.valueOf(amt);
             icommon.updateCommon("SMART_PEOPLE_EXT","NOUSE_ACCOUNT="+total," and USER_ID='"+uid+"'");
             //添加积分
             Common common=new Common();
             if(common.AddUserPoint(uid, "01", "0037facc4349f35ae180ae75bb351e1c",amt)){
                 rJsonObject.put("code","200");
             }
             else {
                 rJsonObject.put("code","400");
             }
         }else {
             rJsonObject.put("code","400");
         }

    }else{
           rJsonObject.put("code","400");
       }
        return rJsonObject.toJSONString();
    }

    /*
     * 判断验证码是否正确
     */
    public boolean HasVerifyCode(String uid){
        if("".equals(uid)||uid==null){
            return false;
        }else{
           List<SMART_VERIFY> listVerify=iPeople.getVerify("DATE_ADD(DATATIME,INTERVAL 10 MINUTE) >= NOW() and USER_ID='"+uid+"'",null);
                    if(listVerify==null){
                     return false;
                    }
                    else{
                        return  true;
                    }
        }
    }

    /*
     * 判断注册账号是否存在
     */
    public boolean IsUidExist(String uid){
        if("".equals(uid)||uid==null){
            return  false;
        }
        else{
            SMART_PEOPLE smartPeople=iPeople.findPeople("USER_ID",uid);
            if(smartPeople!=null){
                return true;
            }
            else{
                return  false;
            }
        }
    }
    /*
     * 判断验证码是否正确
     */
    public boolean IsVerifyCode(String uid,String verifyCode){

        if("".equals(verifyCode) ||verifyCode ==null||"".equals(uid) ||uid==null){
            return false;
        }else{
            String sqlconditon=" and DATE_ADD(DATATIME,INTERVAL 10 MINUTE) >= NOW()";
            Map<String ,Object> map=new HashMap<>();
            map.put("USER_ID",uid);
            map.put("VERIFY_CODE",verifyCode);

           List<SMART_VERIFY>  list=iPeople.getVerify(sqlconditon,map);
           if(list.size()<=0){
               return false;
           }
           else {
               return true;
           }
        }
    }
}
