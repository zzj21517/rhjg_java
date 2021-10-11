package com.lxh.newrhjg.entity;

public class FrameCoupon {
    private  int rowId;
    private String rowGuid;
    private int couponAmount;
    private String couponName;
    private String createTime;
    private String couponFrom;
    private int userFlag;

    public String getCouponFrom() {
        return couponFrom;
    }

    public void setCouponFrom(String couponFrom) {
        this.couponFrom = couponFrom;
    }

    public int getUserFlag() {
        return userFlag;
    }

    public void setUserFlag(int userFlag) {
        this.userFlag = userFlag;
    }

    public int getRowId() {
        return rowId;
    }

    public void setRowId(int rowId) {
        this.rowId = rowId;
    }

    public String getRowGuid() {
        return rowGuid;
    }

    public void setRowGuid(String rowGuid) {
        this.rowGuid = rowGuid;
    }

    public int getCouponAmount() {
        return couponAmount;
    }

    public void setCouponAmount(int couponAmount) {
        this.couponAmount = couponAmount;
    }

    public String getCouponName() {
        return couponName;
    }

    public void setCouponName(String couponName) {
        this.couponName = couponName;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getExpireTime() {
        return expireTime;
    }

    public void setExpireTime(String expireTime) {
        this.expireTime = expireTime;
    }

    public int getCouponStatus() {
        return couponStatus;
    }

    public void setCouponStatus(int couponStatus) {
        this.couponStatus = couponStatus;
    }

    public String getUserGuid() {
        return userGuid;
    }

    public void setUserGuid(String userGuid) {
        this.userGuid = userGuid;
    }

    private String expireTime;
    private int couponStatus;
    private String userGuid;

    public String getProjectNum() {
        return projectNum;
    }

    public String getGiveGuid() {
        return giveGuid;
    }

    public void setGiveGuid(String giveGuid) {
        this.giveGuid = giveGuid;
    }

    public void setProjectNum(String projectNum) {
        this.projectNum = projectNum;
    }

    private String projectNum;
    private String giveGuid;
}
