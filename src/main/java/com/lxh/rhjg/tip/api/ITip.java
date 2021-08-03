package com.lxh.rhjg.tip.api;

import com.lxh.rhjg.active.api.SMART_RULE;
import com.lxh.rhjg.entity.SMART_TIP;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

public  interface ITip extends Serializable {
    public void inserttip(SMART_TIP smartTip);
    public List<SMART_TIP> find(Map<String,Object> map);
}
