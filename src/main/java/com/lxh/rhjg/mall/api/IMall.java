package com.lxh.rhjg.mall.api;
import com.lxh.newrhjg.entity.FrameFollow;
import com.lxh.rhjg.entity.*;

import java.io.Serializable;
import java.util.List;
import java.util.Map;
public  interface IMall extends Serializable {
    public int insert(SMART_REQUIREMENT record);
    public int update(SMART_REQUIREMENT record);
    public int insertmallbase(SMART_MALL_BASE record);
    public int updatemallbase(SMART_MALL_BASE record);
    public int insertmallcase(SMART_MALL_CASE record);
    public int updatemallcase(SMART_MALL_CASE record);
    public int deletemallcase(String rowGuid);
    public SMART_MALL_CASE findCase(Map<String, Object> map);

    public int insertmallorg(SMART_MALL_ORG record);
    public int updatemallorg(SMART_MALL_ORG record);
    public int insertmallphoto(SMART_MALL_PHOTO record);
    public int updatemallphoto(SMART_MALL_PHOTO record);
}
