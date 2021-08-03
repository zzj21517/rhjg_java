package com.lxh.rhjg.active.api;

/**
 *
 * 
 * @作者 xjj
 * @version [版本号, 2018-11-22 16:04:01]
 */
public class SMART_SUBSCRIBE {
    private   String guid;
    private   String userId;   
    
    private   String profession;

    private   String sregieId;
    
    private   String mregieId;
    private   String cregieId;  

    private   String amtType;   
    private   String beginAmt;  

    private   String endAmt;    
    
    private   String status;
    private   String datatime;

    public String getGuid() {
        return guid;
    }

    public void setGuid(String guid) {
        this.guid = guid;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getProfession() {
        return profession;
    }

    public void setProfession(String profession) {
        this.profession = profession;
    }

    public String getSregieId() {
        return sregieId;
    }

    public void setSregieId(String sregieId) {
        this.sregieId = sregieId;
    }

    public String getMregieId() {
        return mregieId;
    }

    public void setMregieId(String mregieId) {
        this.mregieId = mregieId;
    }

    public String getCregieId() {
        return cregieId;
    }

    public void setCregieId(String cregieId) {
        this.cregieId = cregieId;
    }

    public String getAmtType() {
        return amtType;
    }

    public void setAmtType(String amtType) {
        this.amtType = amtType;
    }

    public String getBeginAmt() {
        return beginAmt;
    }

    public void setBeginAmt(String beginAmt) {
        this.beginAmt = beginAmt;
    }

    public String getEndAmt() {
        return endAmt;
    }

    public void setEndAmt(String endAmt) {
        this.endAmt = endAmt;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDatatime() {
        return datatime;
    }

    public void setDatatime(String datatime) {
        this.datatime = datatime;
    }
}
