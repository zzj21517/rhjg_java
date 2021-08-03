package com.lxh.newrhjg.api;


import com.lxh.newrhjg.entity.FrameCompanyExtendinfo;

import java.io.Serializable;
import java.util.Map;

public interface IframeCompanyExtendinfo extends Serializable {
    public int insert(FrameCompanyExtendinfo record);
    public int update(FrameCompanyExtendinfo record);
    public int delete(FrameCompanyExtendinfo record);
    public FrameCompanyExtendinfo find(Map<String, Object> map);
}
