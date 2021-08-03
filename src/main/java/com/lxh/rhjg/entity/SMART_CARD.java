package com.lxh.rhjg.entity;

public class SMART_CARD {
    private  String GUID;
    private  String USER_ID;
    private  String DATATIME;
    private  String DATE_START;
    private  String DATE_END;
    private  String STATUS;
    private  String CARD_AMT;

    public String getGUID() {
        return GUID;
    }

    public void setGUID(String GUID) {
        this.GUID = GUID;
    }

    public String getUSER_ID() {
        return USER_ID;
    }

    public void setUSER_ID(String USER_ID) {
        this.USER_ID = USER_ID;
    }

    public String getDATATIME() {
        return DATATIME;
    }

    public void setDATATIME(String DATATIME) {
        this.DATATIME = DATATIME;
    }

    public String getDATE_START() {
        return DATE_START;
    }

    public void setDATE_START(String DATE_START) {
        this.DATE_START = DATE_START;
    }

    public String getDATE_END() {
        return DATE_END;
    }

    public void setDATE_END(String DATE_END) {
        this.DATE_END = DATE_END;
    }

    public String getSTATUS() {
        return STATUS;
    }

    public void setSTATUS(String STATUS) {
        this.STATUS = STATUS;
    }

    public String getCARD_AMT() {
        return CARD_AMT;
    }

    public void setCARD_AMT(String CARD_AMT) {
        this.CARD_AMT = CARD_AMT;
    }
}
