package com.lxh.newrhjg.api;
import com.lxh.newrhjg.entity.FramepeopleGoodat;

import java.io.Serializable;
import java.util.Map;

public interface IframePeopleGoodAt extends Serializable {
    public int insert(FramepeopleGoodat record);
    public int update(FramepeopleGoodat record);
    public int delete(FramepeopleGoodat record);
    public FramepeopleGoodat find(Map<String, Object> map);
    public int deleteByUser(String userGuid) ;
}
