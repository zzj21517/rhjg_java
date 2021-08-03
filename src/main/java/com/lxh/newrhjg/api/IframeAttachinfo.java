package com.lxh.newrhjg.api;

import com.lxh.newrhjg.entity.FrameAttchinfo;

import java.io.Serializable;
import java.util.Map;

public interface IframeAttachinfo  extends Serializable {
    public int insert(FrameAttchinfo record);
    public int update(FrameAttchinfo record);
    public int delete(FrameAttchinfo record);
    public FrameAttchinfo find(Map<String, Object> map);
}
