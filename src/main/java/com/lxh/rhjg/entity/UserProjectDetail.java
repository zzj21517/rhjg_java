package com.lxh.rhjg.entity;

public class UserProjectDetail {
    private  String GUID;
    private  String NICK_NAME;
    private  String MREGIE_ID;
    private  String CREGIE_ID;
    private  String IMG_PATH;
    private  String PROJECT_AMT;

    public String getENGINEER_AMT() {
        return ENGINEER_AMT;
    }

    public void setENGINEER_AMT(String ENGINEER_AMT) {
        this.ENGINEER_AMT = ENGINEER_AMT;
    }

    private  String ENGINEER_AMT;
    public String getGUID() {
        return GUID;
    }

    public void setGUID(String GUID) {
        this.GUID = GUID;
    }

    public String getNICK_NAME() {
        return NICK_NAME;
    }

    public void setNICK_NAME(String NICK_NAME) {
        this.NICK_NAME = NICK_NAME;
    }

    public String getMREGIE_ID() {
        return MREGIE_ID;
    }

    public void setMREGIE_ID(String MREGIE_ID) {
        this.MREGIE_ID = MREGIE_ID;
    }

    public String getCREGIE_ID() {
        return CREGIE_ID;
    }

    public void setCREGIE_ID(String CREGIE_ID) {
        this.CREGIE_ID = CREGIE_ID;
    }

    public String getIMG_PATH() {
        return IMG_PATH;
    }

    public void setIMG_PATH(String IMG_PATH) {
        this.IMG_PATH = IMG_PATH;
    }

    public String getPROJECT_AMT() {
        return PROJECT_AMT;
    }

    public void setPROJECT_AMT(String PROJECT_AMT) {
        this.PROJECT_AMT = PROJECT_AMT;
    }
}
