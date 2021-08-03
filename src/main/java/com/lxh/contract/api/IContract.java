package com.lxh.contract.api;

import com.lxh.rhjg.active.api.SMART_RULE;
import com.lxh.rhjg.entity.SMART_CONTRACT;

import java.io.Serializable;
import java.util.List;

public  interface IContract extends Serializable {
    public void InsertContract(SMART_CONTRACT smartContract);
    public void CommonupdateContract(String upsql,String Condition);
    public List<SMART_CONTRACT> findSmartConTract(String Conditon);

}
