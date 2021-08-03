package com.lxh.newrhjg.api;
import com.lxh.newrhjg.entity.FramePeopleEval;

import java.io.Serializable;
import java.util.Map;

public interface IframePeopleEval extends Serializable {
    public int insert(FramePeopleEval record);
    public int update(FramePeopleEval record);
    public int delete(FramePeopleEval record);
    public FramePeopleEval find(Map<String, Object> map);

}
