package com.lxh.newrhjg.api;

import com.lxh.rhjg.active.api.SMART_RULE;
import com.lxh.rhjg.entity.SMART_ERRORLOG;
import com.lxh.rhjg.entity.SMART_SSID;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public  interface Inewcommon extends Serializable {
    public void insertlog(SMART_ERRORLOG record);
    public void deleteCommon(String table, String sqlwhere);
    public void updateCommon(String table, String sqlfield, String sqlwhere);
    public List<HashMap<String, Object>> findlist(String table, String sqlfield, Map<String, Object> map, String conditon, String order,int pagenum,int pagesize);
    public int  findlist(String table,  Map<String, Object> map, String conditon);
    public String getitemtext(String code,String itemvalue);
}
