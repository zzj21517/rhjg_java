package com.lxh.rhjg.circle.api;

/**
 *
 * 
 * @作者 xjj
 * @version [版本号, 2018-11-22 16:04:01]
 */
public class SMART_PHOTO {
    private String GUID;
    private String SHORT_CODE;
    private String IMG_PATH;
    private String IMG_DESC;
    private String IMG_ORDER;
    private String IS_DELETE;
    private String DATATIME;
    private String ITEM_TYPE;

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

    public String getIMG_PATH() {
        return IMG_PATH;
    }

    public void setIMG_PATH(String IMG_PATH) {
        this.IMG_PATH = IMG_PATH;
    }

    public String getIMG_DESC() {
        return IMG_DESC;
    }

    public void setIMG_DESC(String IMG_DESC) {
        this.IMG_DESC = IMG_DESC;
    }

    public String getIMG_ORDER() {
        return IMG_ORDER;
    }

    public void setIMG_ORDER(String IMG_ORDER) {
        this.IMG_ORDER = IMG_ORDER;
    }

    public String getIS_DELETE() {
        return IS_DELETE;
    }

    public void setIS_DELETE(String IS_DELETE) {
        this.IS_DELETE = IS_DELETE;
    }

    public String getDATATIME() {
        return DATATIME;
    }

    public void setDATATIME(String DATATIME) {
        this.DATATIME = DATATIME;
    }

    public String getITEM_TYPE() {
        return ITEM_TYPE;
    }

    public void setITEM_TYPE(String ITEM_TYPE) {
        this.ITEM_TYPE = ITEM_TYPE;
    }
}
