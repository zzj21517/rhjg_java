package com.lxh.newrhjg.api;

import com.lxh.newrhjg.entity.FrameMessages;
import com.lxh.newrhjg.entity.FrameMessagesHistroy;

import java.io.Serializable;

public interface IMssages  extends Serializable {
    public int insert(FrameMessages record);
    public int update(FrameMessages record);
    public int delete(FrameMessages record);
    public int removehistroy(FrameMessagesHistroy record);
}
