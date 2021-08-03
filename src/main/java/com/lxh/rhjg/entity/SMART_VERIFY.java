package com.lxh.rhjg.entity;

public class SMART_VERIFY {
    private String GUID;
    private String USER_ID;
    private String VERIFY_CODE;
    private String DATATIME;

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

    public String getVERIFY_CODE() {
        return VERIFY_CODE;
    }

    public void setVERIFY_CODE(String VERIFY_CODE) {
        this.VERIFY_CODE = VERIFY_CODE;
    }

    public String getDATATIME() {
        return DATATIME;
    }

    public void setDATATIME(String DATATIME) {
        this.DATATIME = DATATIME;
    }
}
