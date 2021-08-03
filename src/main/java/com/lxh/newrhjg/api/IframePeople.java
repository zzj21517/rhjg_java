package com.lxh.newrhjg.api;

import com.lxh.newrhjg.entity.FramePeople;
import com.lxh.newrhjg.entity.FramePeopleEnjoy;

import java.io.Serializable;
import java.util.Map;

public interface IframePeople extends Serializable {
    public int insert(FramePeople record);
    public int update(FramePeople record);
    public int delete(FramePeople record);
    public int insertEnjoy(FramePeopleEnjoy record);
    public int deleteEnjoy(String userGuid);
    public int isLogin(String phone,String pwd);
    public FramePeople findPeople(String fieldname, String fieldvalue);
    public FramePeople find(Map<String, Object> map);
}
