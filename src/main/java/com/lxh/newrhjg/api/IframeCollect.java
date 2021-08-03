package com.lxh.newrhjg.api;


import com.lxh.newrhjg.entity.FrameCollect;

import java.io.Serializable;
import java.util.Map;

public interface IframeCollect extends Serializable {
    public int insert(FrameCollect record);
    public int update(FrameCollect record);
    public int delete(FrameCollect record);
    public FrameCollect find(Map<String, Object> map);
}
