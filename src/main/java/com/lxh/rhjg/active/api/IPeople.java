package com.lxh.rhjg.active.api;

import com.alibaba.fastjson.JSONObject;
import com.lxh.rhjg.entity.*;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public  interface IPeople extends Serializable {
    public SMART_PEOPLE findPeople(String fieldname, String fieldvalue);
     public void insertProjectUser(SMART_PROJECT_USER projectUser);
    public void updateProjectUser(SMART_PROJECT_USER projectUser);
    public void insertUser(SMART_PEOPLE smartPeople);
    public void insertPeopleExt(SMART_PEOPLE_EXT smartPeopleExt);
    public void deleteProjectUser(String project_num,String uid);
    public List<JSONObject> GetBannerUserinfo();
    public SMART_PEOPLE_EXT GetSmartPeopleExt(String fieldname, String fieldvalue);
    public  List<JSONObject> GetLeftCount(String pid);
    public  List<PeopleDetail> GetPeopleDetail(String uid);
    public  List<NewPeopleDetail1> GetNewPeopleDetail1(String uid);
    public  List<HashMap<String,Object>> GetNewPeopleDetail2(String uid);
    public  List<HashMap<String,Object>> GetNewPeopleDetail3(String uid);
    public  List<HashMap<String,Object>> GetNewPeopleDetail4(String uid);
    public void updateCommon(String table,String sqlfield,String sqlwhere);
    public List<SMART_VERIFY> getVerify(String sqlwhere, Map<String ,Object> map);
    public  List<HashMap<String,Object>> GetUserInfo(String uid);
    public  List<HashMap<String,Object>> AuthenMan(String uid,String true_pwd);
    public  List<HashMap<String,Object>> GetTongJi(Map<String ,Object> map);
    public  List<HashMap<String,Object>> GetDays(Map<String ,Object> map);
    public  List<SMART_PROJECT_USER> GetProjectUser(Map<String ,Object> map);
    public void insertPointDetail(SMART_POINT_DETAIL smartPointDetail);
    public List<SMART_PEOPLE_EXT> GetSmartPeopleExtlist(String Condition);
    public  List<HashMap<String,Object>> findUserinfo(String uid);
    public void insertLink(SMART_LINK smartLink);
}
