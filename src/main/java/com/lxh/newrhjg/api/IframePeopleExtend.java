package com.lxh.newrhjg.api;
import com.lxh.newrhjg.entity.FramePeopleExtendinfo;

import java.io.Serializable;
import java.util.Map;

public interface IframePeopleExtend extends Serializable {
    public int insert(FramePeopleExtendinfo record);
    public int update(FramePeopleExtendinfo record);
    public int delete(FramePeopleExtendinfo record);
    public FramePeopleExtendinfo find(Map<String, Object> map);
}
