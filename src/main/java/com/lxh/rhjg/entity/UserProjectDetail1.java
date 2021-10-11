package com.lxh.rhjg.entity;

public class UserProjectDetail1 {
    private String nickName;
    private String rowguid;
    private String avatarUrl;
    private int userFlag;
    private int engineerType;
    private int finishNum;
    private String STATUS;

    public String getSTATUS() {
        return STATUS;
    }

    public void setSTATUS(String STATUS) {
        this.STATUS = STATUS;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getRowguid() {
        return rowguid;
    }

    public void setRowguid(String rowguid) {
        this.rowguid = rowguid;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }

    public int getUserFlag() {
        return userFlag;
    }

    public void setUserFlag(int userFlag) {
        this.userFlag = userFlag;
    }

    public int getEngineerType() {
        return engineerType;
    }

    public void setEngineerType(int engineerType) {
        this.engineerType = engineerType;
    }

    public int getFinishNum() {
        return finishNum;
    }

    public void setFinishNum(int finishNum) {
        this.finishNum = finishNum;
    }

    public String getDATATIME() {
        return DATATIME;
    }

    public void setDATATIME(String DATATIME) {
        this.DATATIME = DATATIME;
    }

    public String getPROJECT_AMT() {
        return PROJECT_AMT;
    }

    public void setPROJECT_AMT(String PROJECT_AMT) {
        this.PROJECT_AMT = PROJECT_AMT;
    }

    public Double getDeposit() {
        return deposit;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public void setDeposit(Double deposit) {
        this.deposit = deposit;
    }

    private  String DATATIME;
    private String PROJECT_AMT;
    private Double deposit;
    private int level;
}
