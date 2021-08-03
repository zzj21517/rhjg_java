package com.lxh.newrhjg.api;

import com.lxh.newrhjg.entity.ProductPrice;

import java.io.Serializable;
import java.util.Map;

public interface IProductPrice extends Serializable {
    public int insert(ProductPrice record);
    public int update(ProductPrice record);
    public int delete(ProductPrice record);
    public ProductPrice find(Map<String, Object> map);
}
