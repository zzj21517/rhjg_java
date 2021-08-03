package com.lxh.rhjg.active.api;

import com.lxh.rhjg.entity.SMART_ERRORLOG;
import com.lxh.rhjg.entity.SMART_SSID;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public  interface Icommon extends Serializable {
    public SMART_RULE findSmartrule(String fieldname,String fieldvalue);
    public SMART_SSID findSmartssid(String fieldname, String fieldvalue);
    public void insertSmartrule(SMART_RULE smartRule);
     public void upSmartrule(SMART_RULE smartRule);
    public void deleteCommon(String table,String sqlwhere);
    public void updateCommon(String table,String sqlfield,String sqlwhere);
    public List<HashMap<String, Object>> findlist(String table, String sqlfield, Map<String,Object> map,String conditon,String order);
    public void insertlog(SMART_ERRORLOG record);
    }
