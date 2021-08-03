package com.lxh.rhjg.subscribe.Impl;

import com.lxh.rhjg.active.api.SMART_SUBSCRIBE;
import com.lxh.rhjg.entity.SMART_SIGNDAY;
import com.lxh.rhjg.subscribe.api.ISubscribe;
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

@Component
@Service
public class SubscribeImpl implements ISubscribe {
    @Override
    public void insertSubscribe(SMART_SUBSCRIBE smartSubscribe) {
        Connection conn = JdbcUtils.getConn();
        //SQL语句
        String sql = "INSERT INTO SMART_SUBSCRIBE(GUID,USER_ID,PROFESSION,SREGIE_ID,MREGIE_ID,CREGIE_ID,AMT_TYPE," +
                " BEGIN_AMT,END_AMT,STATUS,DATATIME)" +
                " VALUES(?,?,?,?,?,?,?,?,?,?,?) ";
        try {
            PreparedStatement preparedStatement=conn.prepareStatement(sql);
            preparedStatement.setString(1,smartSubscribe.getGuid());
            preparedStatement.setString(2,smartSubscribe.getUserId());
            preparedStatement.setString(3,smartSubscribe.getProfession());
            preparedStatement.setString(4,smartSubscribe.getSregieId());
            preparedStatement.setString(5,smartSubscribe.getMregieId());
            preparedStatement.setString(6,smartSubscribe.getCregieId());
            preparedStatement.setString(7,smartSubscribe.getAmtType());
            preparedStatement.setString(8,smartSubscribe.getBeginAmt());
            preparedStatement.setString(9,smartSubscribe.getEndAmt());
            preparedStatement.setString(10,smartSubscribe.getStatus());
            preparedStatement.setString(11,smartSubscribe.getDatatime());
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
    public void insertsignday(SMART_SIGNDAY smartSignday){
        Connection conn = JdbcUtils.getConn();
        //SQL语句
        String sql = "INSERT INTO SMART_SIGNDAY(GUID,USER_ID,LAST_SIGN_DATE,CONTINUE_DAY,DATATIME) " +
                " VALUES(?,?,?,?,?) ";
        try {
            PreparedStatement preparedStatement=conn.prepareStatement(sql);
            preparedStatement.setString(1,smartSignday.getGUID());
            preparedStatement.setString(2,smartSignday.getUSER_ID());
            preparedStatement.setString(3,smartSignday.getLAST_SIGN_DATE());
            preparedStatement.setString(4,smartSignday.getCONTINUE_DAY());
            preparedStatement.setString(5,smartSignday.getDATATIME());
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
    public  List<HashMap<String,Object>> getLastSign(String condition){
        HashMap<String,Object> map=null;
        List<HashMap<String,Object>> list=new ArrayList<HashMap<String,Object>>();
        Connection conn = JdbcUtils.getConn();
        //SQL语句
        String sql = "SELECT DATEDIFF(CURDATE(),LAST_SIGN_DATE) LAST_SIGN_DATE,CONTINUE_SIGN,LAST_SIGN_DATE SIGN_DATE " +
                " FROM SMART_USER_INFO WHERE 1=1 and "+condition;
        try {
            PreparedStatement preparedStatement=conn.prepareStatement(sql);
            //执行语句，得到结果集
            ResultSet resultSet=preparedStatement.executeQuery();
            while (resultSet.next()) {
                map=new HashMap<>();
                map.put("LAST_SIGN_DATE",resultSet.getString("LAST_SIGN_DATE"));
                map.put("CONTINUE_SIGN",resultSet.getString("CONTINUE_SIGN"));
                map.put("SIGN_DATE",resultSet.getString("SIGN_DATE"));
                list.add(map);
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
    public  List<HashMap<String,Object>> getCard(String condition){
        HashMap<String,Object> map=null;
        List<HashMap<String,Object>> list=new ArrayList<HashMap<String,Object>>();
        Connection conn = JdbcUtils.getConn();
        //SQL语句
        String sql = "SELECT *  FROM SMART_CARD WHERE 1=1 and "+condition;
        try {
            PreparedStatement preparedStatement=conn.prepareStatement(sql);
            //执行语句，得到结果集
            ResultSet resultSet=preparedStatement.executeQuery();
            while (resultSet.next()) {
                map=new HashMap<>();
                map.put("GUID",resultSet.getString("GUID"));
                map.put("USER_ID",resultSet.getString("USER_ID"));
                map.put("DATATIME",resultSet.getString("DATATIME"));
                map.put("DATE_START",resultSet.getString("DATE_START"));
                map.put("DATE_END",resultSet.getString("DATE_END"));
                map.put("STATUS",resultSet.getString("STATUS"));
                map.put("CARD_AMT",resultSet.getString("CARD_AMT"));
                list.add(map);
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
