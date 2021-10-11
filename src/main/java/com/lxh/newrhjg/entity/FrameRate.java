package com.lxh.newrhjg.entity;

public class FrameRate {
    private int rowId;
    private String rowGuid;
    private String projectNum;
    private String rateGuid;

    public String getRowGuid() {
        return rowGuid;
    }

    public void setRowGuid(String rowGuid) {
        this.rowGuid = rowGuid;
    }

    private String ratedGuid;
    private String createTime;
    private int completeRate;
    private int qualityRate;

    public int getRowId() {
        return rowId;
    }

    public void setRowId(int rowId) {
        this.rowId = rowId;
    }

    public String getProjectNum() {
        return projectNum;
    }

    public void setProjectNum(String projectNum) {
        this.projectNum = projectNum;
    }

    public String getRateGuid() {
        return rateGuid;
    }

    public void setRateGuid(String rateGuid) {
        this.rateGuid = rateGuid;
    }

    public String getRatedGuid() {
        return ratedGuid;
    }

    public void setRatedGuid(String ratedGuid) {
        this.ratedGuid = ratedGuid;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public int getCompleteRate() {
        return completeRate;
    }

    public void setCompleteRate(int completeRate) {
        this.completeRate = completeRate;
    }

    public int getQualityRate() {
        return qualityRate;
    }

    public void setQualityRate(int qualityRate) {
        this.qualityRate = qualityRate;
    }

    public int getServiceAttiduteRate() {
        return serviceAttiduteRate;
    }

    public void setServiceAttiduteRate(int serviceAttiduteRate) {
        this.serviceAttiduteRate = serviceAttiduteRate;
    }

    public int getCooperationRate() {
        return cooperationRate;
    }

    public void setCooperationRate(int cooperationRate) {
        this.cooperationRate = cooperationRate;
    }

    public int getTimelyRate() {
        return timelyRate;
    }

    public void setTimelyRate(int timelyRate) {
        this.timelyRate = timelyRate;
    }

    public int getUploadRate() {
        return uploadRate;
    }

    public void setUploadRate(int uploadRate) {
        this.uploadRate = uploadRate;
    }

    private int serviceAttiduteRate;
    private int cooperationRate;
    private int timelyRate;
    private int uploadRate;
}
