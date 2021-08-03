package com.lxh.newrhjg.entity;

public class ProductPrice {
    private  int row_id;
    private  String rowguid;
    private  String productguid;
    private  float price;
    private   String dw;
    private String content;

    public int getRow_id() {
        return row_id;
    }

    public void setRow_id(int row_id) {
        this.row_id = row_id;
    }

    public String getRowguid() {
        return rowguid;
    }

    public void setRowguid(String rowguid) {
        this.rowguid = rowguid;
    }

    public String getProductguid() {
        return productguid;
    }

    public void setProductguid(String productguid) {
        this.productguid = productguid;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public String getDw() {
        return dw;
    }

    public void setDw(String dw) {
        this.dw = dw;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
