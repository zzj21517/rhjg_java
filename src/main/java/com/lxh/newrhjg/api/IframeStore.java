package com.lxh.newrhjg.api;

import com.lxh.newrhjg.entity.Frameserch;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

public interface IframeStore extends Serializable {
    public int insert(Frameserch record);
    public int update(Frameserch record);
    public int delete(Frameserch record);
    public Frameserch find(Map<String, Object> map);
    public List<Frameserch> findTop(Map<String, Object> map);
}
