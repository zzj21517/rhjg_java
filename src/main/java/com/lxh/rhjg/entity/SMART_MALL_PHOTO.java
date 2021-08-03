package com.lxh.rhjg.entity;

public class SMART_MALL_PHOTO {
    private  String guid;
    private  String forn_Guid;
    private  String user_Id;
    private  String img_Path;
    private  String item_Type;
    private  String dateTime;
    private  int order;

    public String getGuid() {
        return guid;
    }

    public void setGuid(String guid) {
        this.guid = guid;
    }

    public String getForn_Guid() {
        return forn_Guid;
    }

    public void setForn_Guid(String forn_Guid) {
        this.forn_Guid = forn_Guid;
    }

    public String getUser_Id() {
        return user_Id;
    }

    public void setUser_Id(String user_Id) {
        this.user_Id = user_Id;
    }

    public String getImg_Path() {
        return img_Path;
    }

    public void setImg_Path(String img_Path) {
        this.img_Path = img_Path;
    }

    public String getItem_Type() {
        return item_Type;
    }

    public void setItem_Type(String item_Type) {
        this.item_Type = item_Type;
    }

    public String getDateTime() {
        return dateTime;
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }

    public int getOrder() {
        return order;
    }

    public void setOrder(int order) {
        this.order = order;
    }
}
