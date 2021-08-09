package com.lxh.newrhjg.entity;

import org.joda.time.DateTime;

public class FrameMenu {
    private int id;
    private String rowGuid;
    private String label;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getRowGuid() {
        return rowGuid;
    }

    public void setRowGuid(String rowGuid) {
        this.rowGuid = rowGuid;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public int getParentId() {
        return parentId;
    }

    public void setParentId(int parentId) {
        this.parentId = parentId;
    }

    public int getMenuId() {
        return menuId;
    }

    public void setMenuId(int menuId) {
        this.menuId = menuId;
    }

    public DateTime getModifyTime() {
        return modifyTime;
    }

    public void setModifyTime(DateTime modifyTime) {
        this.modifyTime = modifyTime;
    }

    public DateTime getCreateTime() {
        return createTime;
    }

    public void setCreateTime(DateTime createTime) {
        this.createTime = createTime;
    }

    private int parentId;
    private int menuId;
    private DateTime modifyTime;
    private DateTime createTime;
}
