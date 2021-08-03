package com.lxh.rhjg.verifycode.api;

import com.lxh.rhjg.entity.SMART_CONTRACT;
import com.lxh.rhjg.entity.SMART_VERIFY;

import java.io.Serializable;
import java.util.List;

public  interface IVerifycode extends Serializable {
    public void InsertVerifycode(SMART_VERIFY smartVerify);

}
