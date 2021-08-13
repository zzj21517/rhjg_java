package com.lxh.newrhjg.api;

import com.lxh.newrhjg.entity.FrameMenu;
import com.lxh.newrhjg.entity.FrameApply;
import com.lxh.newrhjg.entity.FrameMember;

import java.io.Serializable;
import java.util.List;

public interface IframeProject extends Serializable {
    public List<FrameMenu> findMenuList();
    public int insertApply(FrameApply record);
    public List<FrameMember> findMemberList();
}