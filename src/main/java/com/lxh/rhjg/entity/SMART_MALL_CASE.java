package com.lxh.rhjg.entity;

public class SMART_MALL_CASE {
    private  String guid;
    private  String user_Id;
    private  String case_Name;
    private  String case_Price;
    private  String case_Detail;
    private  int case_Scan;
    private  String dateTime;
    private String clientGuid;

    public String getGuid() {
        return guid;
    }

    public void setGuid(String guid) {
        this.guid = guid;
    }

    public String getUser_Id() {
        return user_Id;
    }

    public void setUser_Id(String user_Id) {
        this.user_Id = user_Id;
    }

    public String getCase_Name() {
        return case_Name;
    }

    public void setCase_Name(String case_Name) {
        this.case_Name = case_Name;
    }

    public String getCase_Price() {
        return case_Price;
    }

    public void setCase_Price(String case_Price) {
        this.case_Price = case_Price;
    }

    public String getCase_Detail() {
        return case_Detail;
    }

    public void setCase_Detail(String case_Detail) {
        this.case_Detail = case_Detail;
    }

    public int getCase_Scan() {
        return case_Scan;
    }

    public void setCase_Scan(int case_Scan) {
        this.case_Scan = case_Scan;
    }

    public String getDateTime() {
        return dateTime;
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }

    public String getClientGuid() {
        return clientGuid;
    }

    public void setClientGuid(String clientGuid) {
        this.clientGuid = clientGuid;
    }
}
