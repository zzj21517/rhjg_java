package com.lxh.rhjg.active.api;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.lxh.rhjg.entity.SMART_TRADE;

import java.io.Serializable;
import java.util.List;

public interface ILuck extends Serializable {
   public void insetLuckbag(SMART_LUCKY luckBag);
   public SMART_LUCKY findLuckbag(String fieldname,String fieldvalue);
   public List<SMART_LUCKY> GetLuckyBag(String uid,int start, int length);
   public JSONObject GetLuckyBagTotal(String uid);
   public JSONObject GetSmartShare(String openId,String uid);
   public JSONObject GetSmartSharebyUid(String uid);
   public void insertSmartShare(JSONObject jsonObject);
   public List<JSONObject> GetBannerLuckyBag();
   public void insettrade(SMART_TRADE smartTrade);

}
