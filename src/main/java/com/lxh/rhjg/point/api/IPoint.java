package com.lxh.rhjg.point.api;

import com.lxh.rhjg.entity.SMART_GAME;
import com.lxh.rhjg.entity.SMART_POINT_RECORD;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public interface IPoint extends Serializable {
    public List<SMART_POINT_RECORD> findList(Map<String, Object> map);
    public void insert(SMART_POINT_RECORD smartGame);
}
