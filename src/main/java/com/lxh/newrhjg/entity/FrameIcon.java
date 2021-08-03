package com.lxh.newrhjg.entity;

public class FrameIcon {
    private  int row_id;
    private  String rowGuid;
    private  String iconThem;
    private  String iconName;
    private  String iconurl;
    private  String Iconlinkurl;
    private  int ordernum;

    public String getIconlinkurl() {
        return Iconlinkurl;
    }

    public void setIconlinkurl(String iconlinkurl) {
        Iconlinkurl = iconlinkurl;
    }

    public String getIconurl() {
        return iconurl;
    }

    public void setIconurl(String iconurl) {
        this.iconurl = iconurl;
    }

    public String getIconName() {
        return iconName;
    }

    public void setIconName(String iconName) {
        this.iconName = iconName;
    }

    public String getIconThem() {
        return iconThem;
    }

    public void setIconThem(String iconThem) {
        this.iconThem = iconThem;
    }

    public String getRowGuid() {
        return rowGuid;
    }

    public void setRowGuid(String rowGuid) {
        this.rowGuid = rowGuid;
    }

    public int getRow_id() {
        return row_id;
    }

    public void setRow_id(int row_id) {
        this.row_id = row_id;
    }

    public int getOrdernum() {
        return ordernum;
    }

    public void setOrdernum(int ordernum) {
        this.ordernum = ordernum;
    }
}
