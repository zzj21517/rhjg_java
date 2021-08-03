package com.lxh.rhjg.verifycode.impl;

import com.alibaba.fastjson.JSONObject;
import com.lxh.rhjg.active.api.IPeople;
import com.lxh.rhjg.active.api.SMART_PEOPLE;
import com.lxh.rhjg.active.api.SMART_PEOPLE_EXT;
import com.lxh.rhjg.active.api.SMART_PROJECT_USER;
import com.lxh.rhjg.entity.NewPeopleDetail1;
import com.lxh.rhjg.entity.PeopleDetail;
import com.lxh.rhjg.entity.SMART_VERIFY;
import com.lxh.rhjg.verifycode.api.IVerifycode;
import com.lxh.test.common.JdbcUtils;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
@Service
public class VerifyImpl implements IVerifycode {


    @Override
    public void InsertVerifycode(SMART_VERIFY smartVerify) {
        Connection conn = JdbcUtils.getConn();
        //SQL语句
        String sql = "INSERT INTO SMART_VERIFY(GUID,USER_ID,VERIFY_CODE,DATATIME)" +
                " VALUES(?,?,?,?) ";
        try {
            PreparedStatement preparedStatement=conn.prepareStatement(sql);
            preparedStatement.setString(1,smartVerify.getGUID());
            preparedStatement.setString(2,smartVerify.getUSER_ID());
            preparedStatement.setString(3,smartVerify.getVERIFY_CODE());
            preparedStatement.setString(4,smartVerify.getDATATIME());
            //执行语句，得到结果集
            preparedStatement.execute();
        } catch (SQLException e1) {
            e1.printStackTrace();
        }finally {
            try {
                conn.close();//关闭连接
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
