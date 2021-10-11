package com.lxh.rhjg.entity;

public class SMART_TRADE {
    private  String GUID;
    private  String PROJECT_NUM;
    private  String TRADE_NUM;
    private  String TRADE_AMT;
    private  String CONTENT;
    private  String STATUS;
    private  String DATATIME;
    private  String PAY_TIME;
    private  String ITEM_TYPE;
    private  String USER_ID;
    private int PAY_STATUS;
    private String ExPIRE_TIME;
    private String COUPONID;

    public String getCOUPONID() {
        return COUPONID;
    }

    public void setCOUPONID(String COUPONID) {
        this.COUPONID = COUPONID;
    }

    public String getExPIRE_TIME() {
        return ExPIRE_TIME;
    }

    public void setExPIRE_TIME(String exPIRE_TIME) {
        ExPIRE_TIME = exPIRE_TIME;
    }

    public String getPAY_TIME() {
        return PAY_TIME;
    }

    public void setPAY_TIME(String PAY_TIME) {
        this.PAY_TIME = PAY_TIME;
    }

    public int getPAY_STATUS() {
        return PAY_STATUS;
    }

    public void setPAY_STATUS(int PAY_STATUS) {
        this.PAY_STATUS = PAY_STATUS;
    }

    public String getGUID() {
        return GUID;
    }

    public void setGUID(String GUID) {
        this.GUID = GUID;
    }

    public String getPROJECT_NUM() {
        return PROJECT_NUM;
    }

    public void setPROJECT_NUM(String PROJECT_NUM) {
        this.PROJECT_NUM = PROJECT_NUM;
    }

    public String getTRADE_NUM() {
        return TRADE_NUM;
    }

    public void setTRADE_NUM(String TRADE_NUM) {
        this.TRADE_NUM = TRADE_NUM;
    }

    public String getTRADE_AMT() {
        return TRADE_AMT;
    }

    public void setTRADE_AMT(String TRADE_AMT) {
        this.TRADE_AMT = TRADE_AMT;
    }

    public String getCONTENT() {
        return CONTENT;
    }

    public void setCONTENT(String CONTENT) {
        this.CONTENT = CONTENT;
    }

    public String getSTATUS() {
        return STATUS;
    }

    public void setSTATUS(String STATUS) {
        this.STATUS = STATUS;
    }

    public String getDATATIME() {
        return DATATIME;
    }

    public void setDATATIME(String DATATIME) {
        this.DATATIME = DATATIME;
    }

    public String getITEM_TYPE() {
        return ITEM_TYPE;
    }

    public void setITEM_TYPE(String ITEM_TYPE) {
        this.ITEM_TYPE = ITEM_TYPE;
    }

    public String getUSER_ID() {
        return USER_ID;
    }

    public void setUSER_ID(String USER_ID) {
        this.USER_ID = USER_ID;
    }
}
