package com.lxh.rhjg.active.api;


import com.alibaba.fastjson.JSONObject;
import com.lxh.rhjg.entity.*;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;

public interface IProject extends Serializable {
   public SMART_PROJECT findProject(String fieldname, String fieldvalue);
   //插入项目
   public int InsertProject(SMART_PROJECT smartProject);
   public void updateproject(SMART_PROJECT smartProject);
   public void Commonupdateproject(String upsql,String Condition);
   public int InsertoldProject(SMART_PROJECT smartProject);
   public void updateproject001(SMART_PROJECT smartProject);
   public void deleteproject(SMART_PROJECT smartProject);
   public List<SMART_SUBSCRIBE> SubscribeProject(String Condition);
   public List<ProjectDetail> GetProjectDetail001(String fid,String uid);
   public List<ProjectDetail1> GetProjectDetail(String fid, String uid);
   public List<UserProjectDetail> GetZBUserDetail(String fid);
   public List<UserProjectDetail1> GetZBUserDetail1(String fid);
   public JSONObject TongJiProject();
   public void insertMark(SMART_MARK smartMark);
   public List<HashMap<String,Object>> getremark1(String workId);
   public List<HashMap<String,Object>> getremark2(String workId);
}
