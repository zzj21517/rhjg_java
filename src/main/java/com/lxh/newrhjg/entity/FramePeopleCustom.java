package com.lxh.newrhjg.entity;

public class FramePeopleCustom {
    private  int rowId;
    private  String rowGuid;
    private  String userGuid;
    private  String customTypes;
    private  int customFundAge;
    private int customMemberNum;


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

    public String getUserGuid() {
        return userGuid;
    }

    public void setUserGuid(String userGuid) {
        this.userGuid = userGuid;
    }

    public String getCustomTypes() {
        return customTypes;
    }

    public void setCustomTypes(String customTypes) {
        this.customTypes = customTypes;
    }

    public int getCustomFundAge() {
        return customFundAge;
    }

    public void setCustomFundAge(int customFundAge) {
        this.customFundAge = customFundAge;
    }

    public int getCustomMemberNum() {
        return customMemberNum;
    }

    public void setCustomMemberNum(int customMemberNum) {
        this.customMemberNum = customMemberNum;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getModifyTime() {
        return modifyTime;
    }

    public void setModifyTime(String modifyTime) {
        this.modifyTime = modifyTime;
    }

    private String createTime;
    private String modifyTime;
}
