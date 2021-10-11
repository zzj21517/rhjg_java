package com.lxh.newrhjg.api;

import com.lxh.newrhjg.entity.*;

import java.io.Serializable;
import java.util.Map;
import java.util.List;

public interface IframePeople extends Serializable {
    public int insert(FramePeople record);
    public int update(FramePeople record);
    public int delete(FramePeople record);
    public int insertEnjoy(FramePeopleEnjoy record);
    public int deleteEnjoy(String userGuid);
    public int isLogin(String phone,String pwd);
    public FramePeople findPeople(String fieldname, String fieldvalue);
    public FramePeople find(Map<String, Object> map);

    public int insertCustom(FramePeopleCustom record);
    public int updateCustom(FramePeopleCustom record);
    public FramePeopleCustom findCustom(String fieldname,String fieldValue);

    public int insertEngineerPerson(FramePeopleEngineerPerson record);
    public  int updateEngineerPerson(FramePeopleEngineerPerson record);
    public FramePeopleEngineerPerson findEngineerPerson(String fieldname,String fieldValue);

    public int insertEngineerTeam(FramePeopleEngineerTeam record);
    public int updateEngineerTeam(FramePeopleEngineerTeam record);
    public FramePeopleEngineerTeam findEngineerTeam(String fieldname,String fieldvalue);
}
