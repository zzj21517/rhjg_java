package com.lxh.rhjg.trade.api;

import com.lxh.rhjg.entity.SMART_TIP;
import com.lxh.rhjg.entity.SMART_TRADE;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

public  interface ITrade extends Serializable {
    public int insertTrade(SMART_TRADE smartTrade);
    public  int updateTrade(SMART_TRADE smartTrade);
    public List<SMART_TRADE> findTrade(String condition);
    public List<SMART_TRADE> find(Map<String, Object> map);
}
