package com.lxh.rhjg.trade.Impl;

import com.lxh.rhjg.entity.SMART_TIP;
import com.lxh.rhjg.entity.SMART_TRADE;
import com.lxh.rhjg.trade.api.ITrade;
import com.lxh.test.common.JdbcUtils;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Component
@Service
public class TradeImpl implements ITrade {
    @Override
    public int insertTrade(SMART_TRADE smartTrade) {
        int flag = 0;
        Connection conn = JdbcUtils.getConn();
        //SQL语句
        String sql = "INSERT INTO SMART_TRADE(GUID,TRADE_NUM,TRADE_AMT,CONTENT,STATUS,USER_ID,ITEM_TYPE,DATATIME,PROJECT_NUM,PAY_STATUS,PAY_TIME,EXPIRE_TIME,COUPONID)" +
                " VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?)";
        try {
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setString(1, smartTrade.getGUID());
            preparedStatement.setString(2, smartTrade.getTRADE_NUM());
            preparedStatement.setString(3, smartTrade.getTRADE_AMT());
            preparedStatement.setString(4, smartTrade.getCONTENT());
            preparedStatement.setString(5, smartTrade.getSTATUS());
            preparedStatement.setString(6, smartTrade.getUSER_ID());
            preparedStatement.setString(7, smartTrade.getITEM_TYPE());
            preparedStatement.setString(8, smartTrade.getDATATIME());
            preparedStatement.setString(9, smartTrade.getPROJECT_NUM());
            preparedStatement.setInt(10, smartTrade.getPAY_STATUS());
            preparedStatement.setString(11, smartTrade.getPAY_TIME());
            preparedStatement.setString(12,smartTrade.getExPIRE_TIME());
            preparedStatement.setString(13,smartTrade.getCOUPONID());
            //执行语句，得到结果集
            preparedStatement.execute();
            flag = 1;
        } catch (SQLException e1) {
            e1.printStackTrace();
        } finally {
            try {
                conn.close();//关闭连接
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return flag;
    }


    @Override
    public int updateTrade(SMART_TRADE smartTrade) {
        int flag = 0;
        Connection conn = JdbcUtils.getConn();
        //SQL语句
        String sql = "update SMART_TRADE set TRADE_NUM=?,TRADE_AMT=?,CONTENT=?,STATUS=?,USER_ID=?,ITEM_TYPE=?,DATATIME=?,PROJECT_NUM=?,PAY_STATUS=?,PAY_TIME=?,EXPIRE_TIME=?,COUPONID=? where GUID=? ";
        try {
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setString(1, smartTrade.getTRADE_NUM());
            preparedStatement.setString(2, smartTrade.getTRADE_AMT());
            preparedStatement.setString(3, smartTrade.getCONTENT());
            preparedStatement.setString(4, smartTrade.getSTATUS());
            preparedStatement.setString(5, smartTrade.getUSER_ID());
            preparedStatement.setString(6, smartTrade.getITEM_TYPE());
            preparedStatement.setString(7, smartTrade.getDATATIME());
            preparedStatement.setString(8, smartTrade.getPROJECT_NUM());
            preparedStatement.setInt(9, smartTrade.getPAY_STATUS());
            preparedStatement.setString(10, smartTrade.getPAY_TIME());
            preparedStatement.setString(11,smartTrade.getExPIRE_TIME());
            preparedStatement.setString(12,smartTrade.getCOUPONID());
            preparedStatement.setString(13, smartTrade.getGUID());
            //执行语句，得到结果集
            preparedStatement.execute();
            flag = 1;
        } catch (SQLException e1) {
            e1.printStackTrace();
        } finally {
            try {
                conn.close();//关闭连接
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return flag;
    }



    @Override
    public List<SMART_TRADE> findTrade(String condition) {
        System.out.println(condition);
        List<SMART_TRADE> list = new ArrayList<SMART_TRADE>();
        Connection conn = JdbcUtils.getConn();
        //SQL语句
        String sql = "select * From  SMART_TRADE WHERE "+condition;
        try {
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            //执行语句，得到结果集
            ResultSet resultSet = preparedStatement.executeQuery();
            System.out.println(resultSet);
            while (resultSet.next()) {
                SMART_TRADE record = new SMART_TRADE();
                System.out.println(resultSet.getString("GUID"));
                record.setGUID(resultSet.getString("GUID"));
                record.setPROJECT_NUM(resultSet.getString("PROJECT_NUM"));
                record.setTRADE_NUM(resultSet.getString("TRADE_NUM"));
                record.setTRADE_AMT(resultSet.getString("TRADE_AMT"));
                record.setCONTENT(resultSet.getString("CONTENT"));
                record.setSTATUS(resultSet.getString("STATUS"));
                record.setDATATIME(resultSet.getString("DATATIME"));
                record.setITEM_TYPE(resultSet.getString("ITEM_TYPE"));
                record.setUSER_ID(resultSet.getString("USER_ID"));
                record.setPAY_STATUS(resultSet.getInt("PAY_STATUS"));
                record.setPAY_TIME(resultSet.getString("PAY_TIME"));
                record.setExPIRE_TIME(resultSet.getString("EXPIRE_TIME"));
                record.setCOUPONID(resultSet.getString("COUPONID"));
                list.add(record);
            }

        } catch (SQLException e1) {
            e1.printStackTrace();
        } finally {
            try {
                conn.close();//关闭连接
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return list;
    }

    @Override
    public List<SMART_TRADE> find(Map<String, Object> map) {
        List<SMART_TIP> list = new ArrayList<SMART_TIP>();
        SMART_TIP record = new SMART_TIP();
        Connection conn = JdbcUtils.getConn();
        //SQL语句
        String sql = "SELECT * FROM SMART_TIP WHERE 1=1  ";
        for (String key : map.keySet()) {//keySet获取map集合key的集合  然后在遍历key即可
            sql += " and " + key + "=? ";
        }
        try {
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            int n = 1;
            for (String key : map.keySet()) {
                preparedStatement.setString(n, map.get(key).toString());
                n++;
            }
            //执行语句，得到结果集
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                record = new SMART_TIP();
                record.setGUID(resultSet.getString("GUID"));
                record.setUSER_ID(resultSet.getString("USER_ID"));
                record.setTITLE(resultSet.getString("TITLE"));
                record.setCONTENT(resultSet.getString("CONTENT"));
                record.setDATATIME(resultSet.getString("DATATIME"));
                list.add(record);
            }
        } catch (SQLException e1) {
            e1.printStackTrace();
        } finally {
            try {
                conn.close();//关闭连接
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return null;
    }
}
