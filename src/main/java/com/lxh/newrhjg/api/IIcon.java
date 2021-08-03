package com.lxh.newrhjg.api;
import com.lxh.newrhjg.entity.FrameIcon;

import java.io.Serializable;

public interface IIcon extends Serializable {
    public int insert(FrameIcon record);
    public int update(FrameIcon record);
    public int delete(FrameIcon record);
}
