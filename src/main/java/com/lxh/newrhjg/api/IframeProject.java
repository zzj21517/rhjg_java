package com.lxh.newrhjg.api;

import com.lxh.newrhjg.entity.*;

import java.io.Serializable;
import java.util.List;

public interface IframeProject extends Serializable {
    public List<FrameMenu> findMenuList();
    public int insertApply(FrameApply record);
    public List<FrameMember> findMemberList();
    public List<FrameCoupon> findCouponList(String condition);
    public int insertCoupon(FrameCoupon record);
    public int updateCoupon(FrameCoupon record);
    public int insertRate(FrameRate record);
    public int updateRate(FrameRate record);
    public FrameRate findRate(String condition);
    public List<FrameCouponGive> findCouponGiveList(String condition);
}