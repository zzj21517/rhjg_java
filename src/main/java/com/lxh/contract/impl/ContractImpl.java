package com.lxh.contract.impl;

import com.lxh.contract.api.IContract;
import com.lxh.rhjg.active.api.Icommon;
import com.lxh.rhjg.active.api.SMART_RULE;
import com.lxh.rhjg.entity.SMART_CONTRACT;
import com.lxh.test.common.JdbcUtils;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Component
@Service
public class ContractImpl implements IContract {
    @Override
    public void InsertContract(SMART_CONTRACT smartContract){
        Connection conn = JdbcUtils.getConn();
        //SQL语句
        String sql = "INSERT INTO SMART_CONTRACT(GUID,PROJECT_NUM,CONTRACT_AMT,BEGIN_DATE,END_DATE,CONTRACT_DESC,CUSTOMER,SIGN_DATE1,STATUS,DATATIME1,RECEIVER,SIGN_DATE2,DATATIME2)" +
                " VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?)";
        try {
            PreparedStatement preparedStatement=conn.prepareStatement(sql);
            preparedStatement.setString(1,smartContract.getGUID());
            preparedStatement.setString(2,smartContract.getPROJECT_NUM());
            preparedStatement.setString(3,smartContract.getCONTRACT_AMT());
            preparedStatement.setString(4,smartContract.getBEGIN_DATE());
            preparedStatement.setString(5,smartContract.getEND_DATE());
            preparedStatement.setString(6,smartContract.getCONTRACT_DESC());
            preparedStatement.setString(7,smartContract.getCUSTOMER());
            preparedStatement.setString(8,smartContract.getSIGN_DATE1());
            preparedStatement.setString(9,smartContract.getSTATUS());
            preparedStatement.setString(10,smartContract.getDATATIME1());
            preparedStatement.setString(11,smartContract.getRECEIVER());
            preparedStatement.setString(12,smartContract.getSIGN_DATE2());
            preparedStatement.setString(13,smartContract.getDATATIME2());
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
    @Override
    public void CommonupdateContract(String upsql, String Condition){
        Connection conn = JdbcUtils.getConn();
        //SQL语句
        String sql = "update  SMART_CONTRACT set "+upsql+" WHERE "+Condition;
        try {
            PreparedStatement preparedStatement=conn.prepareStatement(sql);
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
    @Override
    public List<SMART_CONTRACT> findSmartConTract(String Conditon){
        SMART_CONTRACT contract=null;
        List<SMART_CONTRACT> list=new ArrayList<SMART_CONTRACT>();
        Connection conn = JdbcUtils.getConn();
        //SQL语句
        String sql = "SELECT * FROM SMART_CONTRACT WHERE 1=1 "+Conditon;
        try {
            PreparedStatement preparedStatement=conn.prepareStatement(sql);
            //执行语句，得到结果集
            ResultSet resultSet=preparedStatement.executeQuery();
            while (resultSet.next()) {
                contract=new SMART_CONTRACT();
                contract.setGUID(resultSet.getString("Guid"));
                contract.setPROJECT_NUM(resultSet.getString("Guid"));
                contract.setUSER_ID(resultSet.getString("USER_ID"));
                contract.setCONTRACT_AMT(resultSet.getString("CONTRACT_AMT"));
                contract.setBEGIN_DATE(resultSet.getString("BEGIN_DATE"));
                contract.setEND_DATE(resultSet.getString("END_DATE"));
                contract.setCOUNT_DAYS(resultSet.getString("COUNT_DAYS"));
                contract.setCONTRACT_DESC(resultSet.getString("CONTRACT_DESC"));
                contract.setCUSTOMER(resultSet.getString("CUSTOMER"));
                contract.setSIGN_DATE1(resultSet.getString("SIGN_DATE1"));
                contract.setRECEIVER(resultSet.getString("RECEIVER"));
                contract.setSIGN_DATE2(resultSet.getString("SIGN_DATE2"));
                contract.setSTATUS(resultSet.getString("STATUS"));
                contract.setDATATIME1(resultSet.getString("DATATIME1"));
                contract.setDATATIME2(resultSet.getString("DATATIME2"));
                list.add(contract);
            }
        } catch (SQLException e1) {
            e1.printStackTrace();
        }finally {
            try {
                conn.close();//关闭连接
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return list;
    }

}
