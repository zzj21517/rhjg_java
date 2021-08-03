package com.lxh.newrhjg.entity;

public class FrameCollect {
    private  int row_id;
    private  String rowGuid;
    private  String userGuid;
    private  String storeGuid;
    private  String evalTime;
    public int getRow_id() {
        return row_id;
    }

    public void setRow_id(int row_id) {
        this.row_id = row_id;
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

    public String getStoreGuid() {
        return storeGuid;
    }

    public void setStoreGuid(String storeGuid) {
        this.storeGuid = storeGuid;
    }

    public String getEvalTime() {
        return evalTime;
    }

    public void setEvalTime(String evalTime) {
        this.evalTime = evalTime;
    }
}
