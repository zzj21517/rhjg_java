package com.lxh.rhjg.judge.api;

import com.lxh.rhjg.entity.SMART_JUDGE;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

public interface IJudge extends Serializable {
    public List<SMART_JUDGE> findList(Map<String ,Object> map);
    public void insert(SMART_JUDGE smartJudge);
}
