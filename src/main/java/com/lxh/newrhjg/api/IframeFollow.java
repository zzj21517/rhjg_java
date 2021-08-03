package com.lxh.newrhjg.api;

import com.lxh.newrhjg.entity.FrameFollow;

import java.io.Serializable;
import java.util.Map;

public interface IframeFollow extends Serializable {
    public int insert(FrameFollow record);
    public int update(FrameFollow record);
    public int delete(FrameFollow record);
    public FrameFollow find(Map<String, Object> map);
}
