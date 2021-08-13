package com.lxh.newrhjg.rest;

import com.alibaba.fastjson.JSONObject;
import com.lxh.contract.api.IContract;
import com.lxh.newrhjg.api.IframeProject;
import com.lxh.newrhjg.api.Inewcommon;
import com.lxh.newrhjg.entity.FrameMenu;
import com.lxh.newrhjg.entity.FrameMember;
import com.lxh.rhjg.active.api.*;
import com.lxh.rhjg.circle.api.ICircle;
import com.lxh.rhjg.circle.api.SMART_PHOTO;
import com.lxh.rhjg.common.util.Common;
import com.lxh.rhjg.common.util.HttpClient;
import com.lxh.rhjg.entity.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.*;

import static org.apache.logging.log4j.core.util.NameUtil.md5;

@RestController
@RequestMapping("/ProjectController")
public class ProjectController {
    @Autowired
    IProject iProject;
    @Autowired
    IPeople iPeople;
    @Autowired
    ICircle icircle;
    @Autowired
    ILuck iLuck;
    @Autowired
    IContract iContract;
    @Autowired
    Icommon icommon;
    @Autowired
    Inewcommon inewcommon;
    @Autowired
    IframeProject iframeProject;
    /*
     * 首页数据
     */
    @RequestMapping(value = "/GetMemberList", method = RequestMethod.POST)
    public String GetMemberList(@RequestBody String params) throws IOException {
        JSONObject rJsonObject = new JSONObject();
        try{
            List<FrameMember> list =iframeProject.findMemberList();
            rJsonObject.put("code", "200");
            rJsonObject.put("list", list);
        }catch (Exception err){
            System.out.println(err);
        }
        return rJsonObject.toJSONString();
    }
    @RequestMapping(value = "/GetMenuList", method = RequestMethod.POST)
    public String GetMenuList(@RequestBody String params) throws IOException {
        JSONObject rJsonObject = new JSONObject();
        try{
            List<FrameMenu> list =iframeProject.findMenuList();
            rJsonObject.put("code", "200");
            rJsonObject.put("list", list);
        }catch (Exception err){
            System.out.println(err);
        }
        return rJsonObject.toJSONString();
    }

    @RequestMapping(value = "/GetBanner", method = RequestMethod.POST)
    public String GetBanner(@RequestBody String params) throws IOException {
        JSONObject rJsonObject = new JSONObject();
        JSONObject jsonObject = JSONObject.parseObject(params);
        jsonObject=JSONObject.parseObject(jsonObject.getString("param"));
        String uid = jsonObject.get("uid").toString();
        List<SMART_PHOTO> photoList = icircle.GetCircleDetail(" and ITEM_TYPE='07' AND IS_DELETE='0' ORDER BY DATATIME DESC, IMG_ORDER ASC");
        List<JSONObject> list = iLuck.GetBannerLuckyBag();
        List<JSONObject> objectList = iPeople.GetBannerUserinfo();

        //增加用户信息是否维护判断
        int is_matain = 0;
        if ("15144180088-1".equals(uid)) {
            //未登录
            is_matain = 1;
        } else {
            SMART_PEOPLE_EXT smartPeopleExt = iPeople.GetSmartPeopleExt("USER_ID", uid);
            is_matain = Integer.parseInt(smartPeopleExt.getIS_MATAIN());
        }
        rJsonObject.put("code", "200");
        rJsonObject.put("is_matain", is_matain);
        rJsonObject.put("subjects", photoList);
        rJsonObject.put("data", objectList);
        rJsonObject.put("lucky", list);
        return rJsonObject.toJSONString();
    }

    //评价项目
    @RequestMapping(value = "/JudgePeople", method = RequestMethod.POST)
    public String JudgePeople(@RequestBody String params) throws IOException {
        JSONObject rJsonObject = new JSONObject();
        JSONObject jsonObject = JSONObject.parseObject(params);
        jsonObject=JSONObject.parseObject(jsonObject.getString("param"));
        String pid = jsonObject.get("pid").toString();
        String key1 = jsonObject.get("key1").toString();
        String key = jsonObject.get("key").toString();
        String key2 = jsonObject.get("key2").toString();
        try {
            iProject.Commonupdateproject(" STATUS='04',ZHILIANG='" + key + "',SUDU='" + key1 + "',TAIDU='" + key2 + "'", " PROJECT_NUM='" + pid + "' AND STATUS='03'");
            rJsonObject.put("code", "200");
        } catch (Exception e) {
            rJsonObject.put("code", "400");
        }
        return rJsonObject.toJSONString();
    }
    /*
     * 选择中标人员
     */
    @RequestMapping(value = "/ChooseUser", method = RequestMethod.POST)
    public String ChooseUser(@RequestBody String params) throws IOException {
        JSONObject rJsonObject = new JSONObject();
        JSONObject jsonObject = JSONObject.parseObject(params);
        jsonObject=JSONObject.parseObject(jsonObject.getString("param"));
        String fid = jsonObject.get("fid").toString();
        String uid = jsonObject.get("uid").toString();
        try {
            iProject.Commonupdateproject("STATUS='01'", " PROJECT_NUM='" + fid + "'");
            iProject.Commonupdateproject("STATUS='06'", " PROJECT_NUM='" + fid + "'  AND USER_ID IN (SELECT USER_ID FROM SMART_PEOPLE WHERE USER_ID = '" + uid + "' )");
            iProject.Commonupdateproject("STATUS='05'", " PROJECT_NUM='" + fid + "'  AND USER_ID NOT IN (SELECT USER_ID FROM SMART_PEOPLE WHERE USER_ID = '" + uid + "' )");
            rJsonObject.put("code", "200");
        } catch (Exception e) {
            rJsonObject.put("code", "400");
        }
        return rJsonObject.toJSONString();
    }
    /*
     * 获取项目详细
     */
    @RequestMapping(value = "/GetProjectDetail001", method = RequestMethod.POST)
    public String GetProjectDetail001(@RequestBody String params) throws IOException {
        JSONObject rJsonObject = new JSONObject();
        JSONObject jsonObject = JSONObject.parseObject(params);
        jsonObject=JSONObject.parseObject(jsonObject.getString("param"));
        String fid = jsonObject.get("fid").toString();
        String uid = jsonObject.get("custid").toString();
        List<ProjectDetail> detailList=iProject.GetProjectDetail001(fid,uid);
        //获取保证金
         SMART_PEOPLE smartPeople=iPeople.findPeople("USER_ID",uid);
        double money=Double.valueOf(smartPeople.getMONEY());

        //增加中标人的信息
        List<UserProjectDetail> list=iProject.GetZBUserDetail(fid);
        rJsonObject.put("code", "200");
        rJsonObject.put("zhong", list);
        rJsonObject.put("account", money);
        rJsonObject.put("results", detailList);
        return rJsonObject.toJSONString();
    }
    /*
     * 获取项目详细
     */
    @RequestMapping(value = "/GetProjectDetail", method = RequestMethod.POST)
    public String GetProjectDetail(@RequestBody String params) throws IOException {
        JSONObject rJsonObject = new JSONObject();
        JSONObject jsonObject = JSONObject.parseObject(params);
        jsonObject=JSONObject.parseObject(jsonObject.getString("param"));
        String fid = jsonObject.get("fid").toString();
        String uid = jsonObject.get("custid").toString();
        List<ProjectDetail1>  list=iProject.GetProjectDetail(fid,uid);
        List<UserProjectDetail1> detail1List=iProject.GetZBUserDetail1(fid);
        rJsonObject.put("code", "200");
        rJsonObject.put("bidding", detail1List);
        rJsonObject.put("results", list);
        return rJsonObject.toJSONString();
    }
    /*
     * 更新项目的支付状态
     */
    @RequestMapping(value = "/UpdatePayStatus", method = RequestMethod.POST)
    public String UpdatePayStatus(@RequestBody String params) throws IOException {
        JSONObject rJsonObject = new JSONObject();
        JSONObject jsonObject = JSONObject.parseObject(params);
        jsonObject=JSONObject.parseObject(jsonObject.getString("param"));
        String fid = jsonObject.get("fid").toString();
        String uid = jsonObject.get("uid").toString();
        String amt = jsonObject.get("amt").toString();
        try{
            //下一环节签合同
            iProject.Commonupdateproject("PAY_STATUS='01',PAY_AMT='"+amt+"',STATUS='02' ","PROJECT_NUM='"+fid+"' AND CUST_ID='"+uid+"'");
            rJsonObject.put("code", "200");
        }catch (Exception e){
            rJsonObject.put("code", "400");
        }
        return rJsonObject.toJSONString();
    }
    /*
     * 签订合同
     */
    @RequestMapping(value = "/SignContract", method = RequestMethod.POST)
    public String SignContract(@RequestBody String params) throws IOException {
        JSONObject rJsonObject = new JSONObject();
        JSONObject jsonObject = JSONObject.parseObject(params);
        jsonObject=JSONObject.parseObject(jsonObject.getString("param"));
        String pid = jsonObject.get("pid").toString();
        String begin = jsonObject.get("begin").toString();
        String end = jsonObject.get("end").toString();
        String count = jsonObject.get("count").toString();
        String amt = jsonObject.get("amt").toString();
        String txt = jsonObject.get("txt").toString();
        String customer = jsonObject.get("customer").toString();
        String date1 = jsonObject.get("date1").toString();
        String receiver = jsonObject.get("receiver").toString();
        String date2 = jsonObject.get("date2").toString();
        SimpleDateFormat sDateFormat=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String datatime=sDateFormat.format(new Date());
        String guid=UUID.randomUUID().toString();
        SMART_CONTRACT smartContract=new SMART_CONTRACT();
        smartContract.setGUID(guid);
        smartContract.setPROJECT_NUM(pid);
        smartContract.setUSER_ID(null);
        smartContract.setCONTRACT_AMT(amt);
        smartContract.setBEGIN_DATE(begin);
        smartContract.setEND_DATE(end);
        smartContract.setCOUNT_DAYS(null);
        smartContract.setCONTRACT_DESC(txt);
        smartContract.setCUSTOMER(customer);
        smartContract.setSIGN_DATE1(date1);
        smartContract.setRECEIVER(receiver);
        smartContract.setSIGN_DATE2(date2);
        smartContract.setSTATUS("00");
        smartContract.setDATATIME1(datatime);
        smartContract.setDATATIME2(datatime);
        try{
        //插入数据
        iContract.InsertContract(smartContract);
        //更新项目的状态
        iProject.Commonupdateproject("STATUS='03'"," PROJECT_NUM='"+pid+"'");
            rJsonObject.put("code","200");
        }catch (Exception e){
            rJsonObject.put("code","400");
        }
       return  rJsonObject.toJSONString();
    }
    /*
     * 签订合同
     */
    @RequestMapping(value = "/SignContract001", method = RequestMethod.POST)
    public String SignContract001(@RequestBody String params) throws IOException {
        JSONObject rJsonObject = new JSONObject();
        JSONObject jsonObject = JSONObject.parseObject(params);
        jsonObject=JSONObject.parseObject(jsonObject.getString("param"));
        String pid = jsonObject.get("pid").toString();
        SimpleDateFormat sDateFormat=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String datatime=sDateFormat.format(new Date());
        try{
          iContract.CommonupdateContract("STATUS='02',DATATIME2='"+datatime+"'","PROJECT_NUM='"+pid+"' ");
            rJsonObject.put("code","200");
        }catch (Exception e){
            rJsonObject.put("code","400");
        }
        return  rJsonObject.toJSONString();
    }
    /*
     * 签订合同
     */
    @RequestMapping(value = "/CheckContract", method = RequestMethod.POST)
    public String CheckContract(@RequestBody String params) throws IOException {
        JSONObject rJsonObject = new JSONObject();
        JSONObject jsonObject = JSONObject.parseObject(params);
        jsonObject=JSONObject.parseObject(jsonObject.getString("param"));
        String pid = jsonObject.get("pid").toString();
        try{
           List<SMART_CONTRACT>  list=iContract.findSmartConTract("PROJECT_NUM='"+pid+"'");
            rJsonObject.put("code","200");
            rJsonObject.put("results",list);
        }catch (Exception e){
            rJsonObject.put("code","400");
        }
        return  rJsonObject.toJSONString();
    }
    /*
     * 获取用户详细数据
     */
    @RequestMapping(value = "/TongJiProject", method = RequestMethod.POST)
    public String TongJiProject(@RequestBody String params) throws IOException {
        JSONObject rJsonObject = new JSONObject();
        JSONObject jsonObject = JSONObject.parseObject(params);
        jsonObject=JSONObject.parseObject(jsonObject.getString("param"));
        String pid = jsonObject.get("pid").toString();
        try{
            JSONObject object=iProject.TongJiProject();
            rJsonObject.put("code","200");
            rJsonObject.put("results",object);
        }catch (Exception e){
            rJsonObject.put("code","400");
        }
        return  rJsonObject.toJSONString();
    }
    /*
     * 新增项目
     */
    @RequestMapping(value = "/AddProject", method= RequestMethod.POST)
    public String AddProject(@RequestBody String params) throws IOException {
        JSONObject rJsonObject=new JSONObject();
        JSONObject jsonObject= JSONObject.parseObject(params);
        jsonObject=JSONObject.parseObject(jsonObject.getString("param"));
        String link_man=jsonObject.get("man").toString();
        String link_tel=jsonObject.get("tel").toString();
        String project_amt=jsonObject.get("amt").toString();
        String project_size=jsonObject.get("size").toString();
        String finish_date=jsonObject.get("finish").toString();
        String project_desc=jsonObject.get("desc").toString();
        String sheng=jsonObject.get("sheng").toString();
        String shi=jsonObject.get("shi").toString();
        String xian=jsonObject.get("xian").toString();
        String uid=jsonObject.get("uid").toString();
        String classify=jsonObject.get("classify").toString();
        SimpleDateFormat sDateFormat=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String datatime=sDateFormat.format(new Date());
        String tuijian=jsonObject.get("tuijian").toString();
        String used=jsonObject.get("used").toString();
        String professional=jsonObject.get("professional").toString();
        String name=jsonObject.get("name").toString();

        //新增项2018.8.20
        String type=jsonObject.get("type").toString();
        String requirement=jsonObject.get("requirement").toString();
        String chailv=jsonObject.get("chailv").toString();
        String chizhu=jsonObject.get("chizhu").toString();
        String biaoclassify=jsonObject.get("biaoclassify").toString();
        String zhiliang=jsonObject.get("zhiliang").toString();
        //增加是否可议价
        String YiJia=jsonObject.get("YiJia").toString();
        String PayType=jsonObject.get("PayType").toString();
        String ListNum=jsonObject.get("ListNum").toString();
        String Unit=jsonObject.get("Unit").toString();
        String productguid=jsonObject.get("productguid").toString();
        Common common=new Common();
        String project_num=GetRuleStr("PROJECT_NUM");
        SMART_PROJECT smartProject=new SMART_PROJECT();
        smartProject.setPROJECT_NUM(project_num);
        smartProject.setCUST_ID(uid);
        smartProject.setPROJECT_CLASSIFY(classify);
        smartProject.setPROJECT_USE(used);
        smartProject.setLINK_MAN(link_man);
        smartProject.setLINK_TEL(link_tel);
        smartProject.setPROJECT_AMT(project_amt);
        smartProject.setMREGIE_ID(sheng);
        smartProject.setCREGIE_ID(shi);
        smartProject.setQREGIE_ID(xian);
        smartProject.setPROJECT_SIZE(project_size);
        smartProject.setFINISH_DATE(finish_date);
        smartProject.setPROJECT_DESC(project_desc);
        smartProject.setSTATUS("00");
        smartProject.setDATATIME(datatime);
        smartProject.setTUIJIAN(tuijian);
        smartProject.setPROFESSIONAL(professional);
        smartProject.setPROFESSION_NAME(name);
        smartProject.setPROJECT_FLAG(type);
        smartProject.setPROJECT_NEED(requirement);
        smartProject.setIS_TRAVEL(chailv);
        smartProject.setIS_EAT(chizhu);
        smartProject.setBID_CLASSIFY(biaoclassify);
        smartProject.setQUALITY_NEED(zhiliang);
        smartProject.setYIJIA(YiJia);
        smartProject.setPAY_TYPE(PayType);
        smartProject.setLIST_NUM(ListNum);
        smartProject.setPROJECT_SIZE_TYPE(Unit);
        smartProject.setProductguid(productguid);
        try{
        iProject.InsertProject(smartProject);
            rJsonObject.put("code","200");
        }catch (Exception e){
            rJsonObject.put("code","400");
        }
      return rJsonObject.toJSONString();
    }
    /*
     * 修改项目
     */
    @RequestMapping(value = "/UpdateProject", method= RequestMethod.POST)
    public String UpdateProject(@RequestBody String params) throws IOException {
        //获取参数
        JSONObject rJsonObject=new JSONObject();
        JSONObject jsonObject= JSONObject.parseObject(params);
        jsonObject=JSONObject.parseObject(jsonObject.getString("param"));
        String pid=jsonObject.get("pid").toString();
        String man=jsonObject.get("man").toString();
        String tel=jsonObject.get("tel").toString();
        String amt=jsonObject.get("amt").toString();
        String desc=jsonObject.get("desc").toString();
        SMART_PROJECT smartProject=new SMART_PROJECT();
        smartProject.setLINK_TEL(tel);
        smartProject.setLINK_MAN(man);
        smartProject.setPROJECT_AMT(amt);
        smartProject.setPROJECT_DESC(desc);
        smartProject.setPROJECT_NUM(pid);

          try{
              iProject.updateproject(smartProject);
              rJsonObject.put("code","200");
          }catch (Exception e){
              rJsonObject.put("code","400");
          }
          return rJsonObject.toJSONString();
    }

    /*
     * 修改项目
     */
    @RequestMapping(value = "/UpdateProject001", method= RequestMethod.POST)
    public String UpdateProject001(@RequestBody String params) throws IOException {
        //获取参数
        JSONObject rJsonObject=new JSONObject();
        JSONObject jsonObject= JSONObject.parseObject(params);
        jsonObject=JSONObject.parseObject(jsonObject.getString("param"));
        String pid=jsonObject.get("pid").toString();
        String man=jsonObject.get("man").toString();
        String tel=jsonObject.get("tel").toString();
        String amt=jsonObject.get("amt").toString();
        String desc=jsonObject.get("desc").toString();
        String size=jsonObject.get("size").toString();
        String date1=jsonObject.get("date1").toString();
        String zhuanxiang=jsonObject.get("zhuanxiang").toString();
        SimpleDateFormat sDateFormat=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String datatime=sDateFormat.format(new Date());

        String label1=jsonObject.get("label1").toString();
        String label2=jsonObject.get("label2").toString();
        String label3=jsonObject.get("label3").toString();

        SMART_PROJECT smartProject=new SMART_PROJECT();
        smartProject.setLINK_TEL(tel);
        smartProject.setLINK_MAN(man);
        smartProject.setPROJECT_AMT(amt);
        smartProject.setPROJECT_DESC(desc);
        smartProject.setPROJECT_NUM(pid);
        smartProject.setDATATIME(datatime);
        smartProject.setPROJECT_SIZE(size);
        smartProject.setFINISH_DATE(date1);
        smartProject.setPROJECT_TYPE(zhuanxiang);
        smartProject.setLABEL_1(label1);
        smartProject.setLABEL_2(label2);
        smartProject.setLABEL_3(label3);
        try{
            iProject.updateproject(smartProject);
            rJsonObject.put("code","200");
        }catch (Exception e){
            rJsonObject.put("code","400");
        }
        return rJsonObject.toJSONString();
    }

    /*
     * 删除项目
     */
    @RequestMapping(value = "/DeleteProject", method= RequestMethod.POST)
    public String DeleteProject(@RequestBody String params) throws IOException {
        //获取参数
        JSONObject rJsonObject=new JSONObject();
        JSONObject jsonObject= JSONObject.parseObject(params);
        jsonObject=JSONObject.parseObject(jsonObject.getString("param"));
        String pid=jsonObject.get("pid").toString();
        SMART_PROJECT project=new SMART_PROJECT();
        project.setPROJECT_NUM(pid);
        try{
            iProject.deleteproject(project);
            rJsonObject.put("code","200");
        }catch (Exception e){
            rJsonObject.put("code","400");
        }
        return rJsonObject.toJSONString();
    }

    /*
     * 新增投标
     */
    @RequestMapping(value = "/AddProjectUser", method= RequestMethod.POST)
    public String AddProjectUser(@RequestBody String params) throws IOException {
        //获取参数
        JSONObject rJsonObject=new JSONObject();
        JSONObject jsonObject= JSONObject.parseObject(params);
        jsonObject=JSONObject.parseObject(jsonObject.getString("param"));
        String uid=jsonObject.get("uid").toString();
        String Guid= UUID.randomUUID().toString();
        String link_man=jsonObject.get("man").toString();
        String link_tel=jsonObject.get("tel").toString();
        String project_amt=jsonObject.get("amt").toString();
        String project_desc=jsonObject.get("desc").toString();
        String project_num=jsonObject.get("fid").toString();
        SimpleDateFormat sDateFormat=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String datatime=sDateFormat.format(new Date());
       SMART_PEOPLE smartPeople=iPeople.findPeople("USER_ID",uid);
        String money=smartPeople.getNICK_NAME();
        SMART_PROJECT_USER projectUser=new SMART_PROJECT_USER();
        projectUser.setGUID(Guid);
        projectUser.setPROJECT_NUM(project_num);
        projectUser.setUSER_ID(uid);
        projectUser.setLINK_MAN(link_man);
        projectUser.setLINK_TEL(link_tel);
        projectUser.setPROJECT_AMT(project_amt);
        projectUser.setPROJECT_DESC(project_desc);
        projectUser.setDATATIME(datatime);
        projectUser.setNICK_NAME(money);
        try {
            iPeople.insertProjectUser(projectUser);
            rJsonObject.put("code","200");
        }catch (Exception e){
            rJsonObject.put("code","400");
        }
       return  rJsonObject.toJSONString();
    }
    /*
     *
     * 取消投标
     */
    @RequestMapping(value = "/DelProjectUser", method= RequestMethod.POST)
    public String DelProjectUser(@RequestBody String params) throws IOException {
       //获取参数
        JSONObject rJsonObject=new JSONObject();
        JSONObject jsonObject= JSONObject.parseObject(params);
        jsonObject=JSONObject.parseObject(jsonObject.getString("param"));
        String uid=jsonObject.get("uid").toString();
        String project_num=jsonObject.get("fid").toString();
        SMART_PROJECT project=iProject.findProject("PROJECT_NUM",project_num);
      String status=  project.getSTATUS();
        //未选择中标人，可以取消
      if("00".equals(status)){
          try{
          iPeople.deleteProjectUser(project_num,uid);
              rJsonObject.put("code","200");
          }catch (Exception e){
              rJsonObject.put("code","400");
          }
      }else{
          rJsonObject.put("code","100");
      }
        return  rJsonObject.toJSONString();
    }

    /*
     * 获取商家服务详情
     */
    @RequestMapping(value = "/getProjectDetail", method = RequestMethod.POST)
    public String getProjectDetail(@RequestBody String params) {
        JSONObject rJsonObject = new JSONObject();
        JSONObject jsonObject = JSONObject.parseObject(params);
        jsonObject = JSONObject.parseObject(jsonObject.getString("param"));

        SimpleDateFormat sDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String datatime = sDateFormat.format(new Date());
        String rowguid = jsonObject.get("rowguid").toString();//商家标识
        int pagesize = Integer.parseInt(jsonObject.get("pagesize").toString());
        int pagenum = Integer.parseInt(jsonObject.get("pagenum").toString());
        try {
            Map<String, Object> map = new HashMap<>();
            map.put("PROJECT_NUM=", rowguid);
            List<HashMap<String, Object>> list = inewcommon.findlist("smart_project", "*", map, "", "", pagenum, pagesize);
            int count=inewcommon.findlist("smart_project",map,"");
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
     * 获取商家服务详情
     */
    @RequestMapping(value = "/getUserProject", method = RequestMethod.POST)
    public String getUserProject(@RequestBody String params) {
        JSONObject rJsonObject = new JSONObject();
        JSONObject jsonObject = JSONObject.parseObject(params);
        jsonObject = JSONObject.parseObject(jsonObject.getString("param"));

        SimpleDateFormat sDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String datatime = sDateFormat.format(new Date());
        String userguid = jsonObject.get("userguid").toString();//商家标识
        String STATUS = jsonObject.get("status").toString();//商家标识
        int pagesize = Integer.parseInt(jsonObject.get("pagesize").toString());
        int pagenum = Integer.parseInt(jsonObject.get("pagenum").toString());
        try {
            Map<String, Object> map = new HashMap<>();
            map.put("CUST_ID=", userguid);
            if(!"".equals(STATUS)){
                map.put("STATUS=", STATUS);
            }
            List<HashMap<String, Object>> list = inewcommon.findlist("smart_project", "*", map, "", "", pagenum, pagesize);
            int count=inewcommon.findlist("smart_project",map,"");
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
     * 获取项目列表
     */
    @RequestMapping(value = "/GetProject", method= RequestMethod.POST)
    public String GetProject(@RequestBody String params) throws IOException {
        //获取参数
        JSONObject rJsonObject=new JSONObject();
        JSONObject jsonObject= JSONObject.parseObject(params);
        jsonObject=JSONObject.parseObject(jsonObject.getString("param"));
        String city=jsonObject.get("city").toString();
        String type=jsonObject.get("itemtype").toString();
        String uid=jsonObject.get("uid").toString();
        String keyword=jsonObject.get("keyword").toString();
        String start=jsonObject.get("start").toString();
        String count=jsonObject.get("count").toString();
        int length=Integer.parseInt(start)+Integer.parseInt(count);
        //增加排序
        String sort=jsonObject.get("sort").toString();
        //增加筛选条件
        String area=jsonObject.get("area").toString();
        String major=jsonObject.get("major").toString();
        String sql="SELECT A.PROJECT_NUM,IFNULL(B.DICT_VALUE,'算量') PROJECT_CLASSIFY, A.PROJECT_AMT,A.MREGIE_ID," +
                "CASE WHEN CREGIE_ID ='' THEN '未知地区' ELSE CREGIE_ID END CREGIE_ID,SUBSTR(A.DATATIME,1,10) FINISH_DATE," +
                "A.PROJECT_DESC,A.SCAN_NUM,C.DICT_VALUE STATUS,A.PROJECT_TYPE, A.STATUS STATUS_VALUE,A.PROFESSIONAL,A.PROFESSION_NAME," +
                "CASE WHEN (A.TUIJIAN IS NULL OR A.TUIJIAN='' OR A.TUIJIAN='15144180088') THEN '0' ELSE '1' END TUIJIAN," +
                "A.TUIJIAN TJ,IFNULL(A.LABEL_1,'') LABEL_1 ,IFNULL(A.LABEL_2,'') LABEL_2,IFNULL(A.LABEL_3,'') LABEL_3," +
                "IFNULL(D.PROJECT_NUM,'1') JUDGE_BUTTON FROM SMART_PROJECT A " +
                "LEFT JOIN SMART_DICT B ON A.PROJECT_NEED = B.DICT_ID AND B.DICT_CODE='RHJG_PROJECT_NEED' " +
                "LEFT JOIN SMART_DICT C ON A.STATUS = C.DICT_ID AND C.DICT_CODE='RHJG_STATUS' " +
                "LEFT JOIN SMART_JUDGE D ON A.PROJECT_NUM = D.PROJECT_NUM " +
                " WHERE 1=1 ";
        //根据type确认查询条件
        switch (type){
            case "MY":
                sql+=" AND A.CUST_ID = '$uid' ";
                break;
            case "TOU":
                sql+=" AND A.PROJECT_NUM IN(SELECT PROJECT_NUM FROM SMART_PROJECT_USER WHERE USER_ID='$uid' )";
                break;
            case "ZHONG":
                sql+=" AND A.PROJECT_NUM IN(SELECT PROJECT_NUM FROM SMART_PROJECT_USER WHERE USER_ID='$uid' AND STATUS='06' ) ";
                break;
            case "ALL":
            {
                switch (keyword){
                    case "TAB1":
                        break;
                    case "TAB2":
                        sql+=" AND A.PROJECT_AMT <= 1500 AND LABEL_1 NOT LIKE '%低价单%'";
                        break;
                    case "TAB3":
                        sql+=" AND LABEL_1 LIKE '%低价单%'";
                        break;
                        default: break;
                }
                if(!area.equals("全国") && !area.equals("'地区'")){
                    sql+=" AND CREGIE_ID LIKE '%$area%' ";
                }

                if(!major.equals("00")){
                    sql+=" AND PROFESSIONAL LIKE '%$major%'";
                }
            }
               break;
            case "TUI":
                sql+=" AND STATUS > '01' AND TUIJIAN = '$uid'";
                break;
            case "GUAN":
                //用户管理的项目列表
                if(!"".equals(keyword)){
                    sql+=" AND A.PROJECT_NUM LIKE '%$keyword%'";
                }
                break;
            case "FINISH":
                    sql+=" AND A.PROJECT_NUM IN (SELECT DISTINCT PROJECT_NUM FROM SMART_PROJECT_USER WHERE STATUS='06'  AND USER_ID='$uid' )";
                break;
            case "SEARCH":
            {
                switch (keyword){
                    case "招标中的项目":
                        sql+=" AND A.STATUS = '00'";
                        break;
                    case "1000元-3000元项目":
                        sql+=" AND A.PROJECT_AMT BETWEEN '1000'  AND '3000' ";
                        break;
                    case "土建专业项目":
                        sql+=" AND A.PROFESSION LIKE '%01%'";
                        break;
                        default:
                        {
                            switch (uid){
                                case  "00": //全部
                                    sql+=" AND (A.PROFESSION_NAME LIKE '%$keyword%' OR A.MREGIE_ID LIKE '%$keyword%' OR A.CREGIE_ID LIKE '%$keyword%' OR A.QREGIE_ID LIKE '%$keyword%' )";
                                    break;
                                case  "01": //地区
                                    sql+=" AND (A.MREGIE_ID LIKE '%$keyword%' OR A.CREGIE_ID LIKE '%$keyword%' OR A.QREGIE_ID LIKE '%$keyword%')";
                                    break;
                                case  "02": //价格
                                    sql+=" AND A.PROJECT_AMT >= '$keyword'";
                                    break;
                                case  "03": //专业
                                    sql+=" AND A.PROFESSION_NAME LIKE '%$keyword%'";
                                    break;
                                    default:break;
                            }
                        }
                        break;
                }
            }
                break;
            default:
                break;
        }
        //增加首页排序功能
        switch (sort){
            case  "00":
                sql+="  ORDER BY DATATIME DESC LIMIT $start,$length ";
                break;
            case  "01"://01：正在服务中
                sql+="  AND A.STATUS='03' ORDER BY A.STATUS ASC,CAST(PROJECT_AMT AS DECIMAL) DESC, DATATIME DESC LIMIT $start,$length  ";
                break;
            case  "02"://02：项目已完工
                sql+="  AND A.STATUS='04' ORDER BY A.STATUS ASC,CAST(PROJECT_AMT AS DECIMAL) ASC, DATATIME DESC LIMIT $start,$length";
                break;
            case  "03":
                sql+="  ORDER BY A.STATUS ASC, DATATIME DESC LIMIT $start,$length  ";
                break;
                default:
                 sql+="  ORDER BY DATATIME DESC LIMIT $start,$length";
                 break;
        }
            return  rJsonObject.toJSONString();

    }
    public  void SubscribeProject(String amt,String city,String profession){
        List<SMART_SUBSCRIBE> list=iProject.SubscribeProject("STATUS='02' AND BEGIN_AMT >= '"+amt+"' AND END_AMT < '"+amt+"' AND MREGIE_ID='"+city+"'" +
             "   AND PROFESSION LIKE '%"+profession+"%' ");
        //循环发送短信
        String txt = "【融汇精工】：尊敬的客户，平台有您订阅的项目发布啦，请及时登录小程序查看。感谢您的信任，祝您生活愉快。";
       for(SMART_SUBSCRIBE subscribe:list){
         String  uid=subscribe.getUserId();
           SendMessage(uid,txt);
       }
    }
    public String  GetRuleStr(String ruleid){
        String returnvalue="";
        SMART_RULE smartRule=icommon.findSmartrule("rule_id",ruleid);
        if(smartRule!=null){
            String id=smartRule.getRuleValue();
            if(id.equals("")||id==null){
                id="1000000";
            }
            int num=Integer.parseInt(id)+1;
            smartRule.setRuleValue(String.valueOf(num));
            //更新最大编号
            icommon.upSmartrule(smartRule);
            returnvalue=smartRule.getPrefix()+String.valueOf(num);
        }
        return  returnvalue;
    }
    public void  SendMessage(String uid,String txt){

        String smsapi = "http://api.smsbao.com/";
        String user = "szgf"; //短信平台帐号
        String pass = md5("shihoufeng2012"); //短信平台密码
        String phone = uid;//要发送短信的手机号码
        String sendurl = "";
        try {
            sendurl = smsapi+"sms?u="+user+"&p="+pass+"&m="+phone+"&c="+ URLEncoder.encode(txt,"utf-8");
            String result=HttpClient.doPost(sendurl,"") ;
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

}
