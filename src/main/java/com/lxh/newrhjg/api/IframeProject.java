package com.lxh.newrhjg.api;

import com.lxh.newrhjg.entity.FrameMenu;

import java.io.Serializable;
import java.util.List;

public interface IframeProject extends Serializable {
    public List<FrameMenu> findMenuList();
}