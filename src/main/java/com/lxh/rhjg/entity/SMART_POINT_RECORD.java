package com.lxh.rhjg.entity;

public class SMART_POINT_RECORD {
    private  String GUID;
    private  String SHORT_CODE;
    private  String SHARE_ID;
    private  String SHARE_NAME;
    private  String SHARE_IMAGE;
    private  String DATATIME;

    public SMART_POINT_RECORD() {
    }

    public String getGUID() {
        return GUID;
    }

    public void setGUID(String GUID) {
        this.GUID = GUID;
    }

    public String getSHORT_CODE() {
        return SHORT_CODE;
    }

    public void setSHORT_CODE(String SHORT_CODE) {
        this.SHORT_CODE = SHORT_CODE;
    }

    public String getSHARE_ID() {
        return SHARE_ID;
    }

    public void setSHARE_ID(String SHARE_ID) {
        this.SHARE_ID = SHARE_ID;
    }

    public String getSHARE_NAME() {
        return SHARE_NAME;
    }

    public void setSHARE_NAME(String SHARE_NAME) {
        this.SHARE_NAME = SHARE_NAME;
    }

    public String getSHARE_IMAGE() {
        return SHARE_IMAGE;
    }

    public void setSHARE_IMAGE(String SHARE_IMAGE) {
        this.SHARE_IMAGE = SHARE_IMAGE;
    }

    public String getDATATIME() {
        return DATATIME;
    }

    public void setDATATIME(String DATATIME) {
        this.DATATIME = DATATIME;
    }
}
