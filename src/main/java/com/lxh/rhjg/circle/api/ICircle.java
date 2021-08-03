package com.lxh.rhjg.circle.api;

import com.alibaba.fastjson.JSONObject;
import com.lxh.rhjg.active.api.SMART_LUCKY;

import java.io.Serializable;
import java.util.List;

    public interface ICircle extends Serializable {
        public List<SMART_PHOTO> getSMARTPHOTO(int start,int length);
        public List<SMART_PHOTO> GetCircleDetail(String condition);
    }
