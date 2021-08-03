package com.lxh.newrhjg.entity;

public class FramePeopleEnjoy {
    private  int row_id;
    private  String rowGuid;
    private  String userGuid;
    private  String enjoyType;
    private  String enjoyTypeChina;

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

    public String getEnjoyType() {
        return enjoyType;
    }

    public void setEnjoyType(String enjoyType) {
        this.enjoyType = enjoyType;
    }

    public String getEnjoyTypeChina() {
        return enjoyTypeChina;
    }

    public void setEnjoyTypeChina(String enjoyTypeChina) {
        this.enjoyTypeChina = enjoyTypeChina;
    }
}
