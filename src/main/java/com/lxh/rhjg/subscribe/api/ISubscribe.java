package com.lxh.rhjg.subscribe.api;

import com.lxh.rhjg.active.api.SMART_SUBSCRIBE;
import com.lxh.rhjg.entity.SMART_SIGNDAY;
import com.lxh.rhjg.entity.SMART_TIP;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public  interface ISubscribe extends Serializable {
    public void insertSubscribe(SMART_SUBSCRIBE subscribe);
    public int insertsignday(SMART_SIGNDAY smartSignday);
    public  List<HashMap<String,Object>> getLastSign(String condition);
    public  List<HashMap<String,Object>> getCard(String condition);
}
