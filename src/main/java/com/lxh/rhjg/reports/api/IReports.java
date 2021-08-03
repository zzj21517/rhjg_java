package com.lxh.rhjg.reports.api;


import com.alibaba.fastjson.JSONObject;
import com.lxh.rhjg.active.api.SMART_PROJECT;
import com.lxh.rhjg.active.api.SMART_SUBSCRIBE;
import com.lxh.rhjg.entity.*;

import java.io.Serializable;
import java.util.List;

public interface IReports extends Serializable {
   //插入报告
   public void InsertReports(SMART_REPORTS smartReports);
}
