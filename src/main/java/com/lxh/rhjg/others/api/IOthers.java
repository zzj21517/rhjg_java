package com.lxh.rhjg.others.api;

import com.lxh.rhjg.entity.SMART_CO;
import com.lxh.rhjg.entity.SMART_GAME;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public interface IOthers extends Serializable {
    public List<SMART_GAME> findList(Map<String, Object> map);
    public void insert(SMART_CO smartCo);
}
