package com.lxh.rhjg.game.api;

import com.lxh.rhjg.entity.SMART_GAME;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public interface IGame extends Serializable {
    public List<HashMap<String,Object>> getGameCount();
    public List<HashMap<String,Object>> getBombCount(String userId);
    public List<SMART_GAME> findList(Map<String ,Object> map);
    public void insert(SMART_GAME smartGame);
    public int getCount(String Conditon);
}
