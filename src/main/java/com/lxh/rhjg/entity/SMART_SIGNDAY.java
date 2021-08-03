package com.lxh.rhjg.entity;

public class SMART_SIGNDAY {
    private   String GUID;
    private  String USER_ID;
    private  String LAST_SIGN_DATE;
    private  String CONTINUE_DAY;
    private  String DATATIME;

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

    public String getLAST_SIGN_DATE() {
        return LAST_SIGN_DATE;
    }

    public void setLAST_SIGN_DATE(String LAST_SIGN_DATE) {
        this.LAST_SIGN_DATE = LAST_SIGN_DATE;
    }

    public String getCONTINUE_DAY() {
        return CONTINUE_DAY;
    }

    public void setCONTINUE_DAY(String CONTINUE_DAY) {
        this.CONTINUE_DAY = CONTINUE_DAY;
    }

    public String getDATATIME() {
        return DATATIME;
    }

    public void setDATATIME(String DATATIME) {
        this.DATATIME = DATATIME;
    }
}
