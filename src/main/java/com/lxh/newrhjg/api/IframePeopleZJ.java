package com.lxh.newrhjg.api;
import com.lxh.newrhjg.entity.FramePeopleZj;

import java.io.Serializable;
import java.util.Map;

public interface IframePeopleZJ extends Serializable {
    public int insert(FramePeopleZj record);
    public int update(FramePeopleZj record);
    public int delete(FramePeopleZj record);
    public FramePeopleZj find(Map<String, Object> map);
}
