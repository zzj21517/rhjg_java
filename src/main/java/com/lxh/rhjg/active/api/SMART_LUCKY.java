package com.lxh.rhjg.active.api;
import java.util.Date;

/**
 *
 * 
 * @作者 xjj
 * @version [版本号, 2018-11-22 16:04:01]
 */
public class SMART_LUCKY  {
    private   String Guid;
    //唯一标识
    public String getGuid() {
        return Guid;
    }
    public void setGuid(String Guid) {
        this.Guid = Guid;
    }
    //用户标识
    private   String USER_ID;
    public String getUSER_ID() {
        return USER_ID;
    }
    public void setUSER_ID(String USER_ID) {
        this.USER_ID = USER_ID;
    }
    //项目名称
    private   String PROJECT_NUM;
    public String getPROJECT_NUM() {
        return PROJECT_NUM;
    }
    public void setPROJECT_NUM(String PROJECT_NUM) {
        this.PROJECT_NUM = PROJECT_NUM;
    }
    //项目名称
    private   String LUCKY_AMT;
    public String getLUCKY_AMT() {
        return LUCKY_AMT;
    }
    public void setLUCKY_AMT(String LUCKY_AMT) {
        this.LUCKY_AMT = LUCKY_AMT;
    }

    private   String DATA_TIME;
    public String getDATA_TIME() {
        return DATA_TIME;
    }
    public void setDATA_TIME(String DATA_TIME) {
        this.DATA_TIME = DATA_TIME;
    }

    private   String STATUS;
    public String getSTATUS() {
        return STATUS;
    }
    public void setSTATUS(String STATUS) {
        this.STATUS = STATUS;
    }
}
