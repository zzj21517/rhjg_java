package com.lxh.rhjg.others.impl;

import com.lxh.rhjg.entity.SMART_CO;
import com.lxh.rhjg.entity.SMART_GAME;
import com.lxh.rhjg.game.api.IGame;
import com.lxh.rhjg.others.api.IOthers;
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
public class OthersImpl implements IOthers {

    @Override
    public void insert(SMART_CO record) {
        Connection conn = JdbcUtils.getConn();
        //SQL语句
        String sql = "INSERT INTO SMART_CO(UUID,CO_NUM,USER_ID,QTY_SUM,AMT_SUM,STATUS,DATATIME10)"+
                " VALUES(?,?,?,?,?,?,?) ";
        try {
            PreparedStatement preparedStatement=conn.prepareStatement(sql);
            preparedStatement.setString(1,record.getUUID());
            preparedStatement.setString(2,record.getCO_NUM());
            preparedStatement.setString(3,record.getUSER_ID());
            preparedStatement.setString(4,record.getQTY_SUM());
            preparedStatement.setString(5,record.getAMT_SUM());
            preparedStatement.setString(6,record.getSTATUS());
            preparedStatement.setString(7,record.getDATATIME10());
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
    public int getCount(String Conditon){
        int count=0;
        Connection conn = JdbcUtils.getConn();
        //SQL语句
        String sql = "SELECT COUNT(*) VALUENUM FROM SMART_GAME WHERE 1=1 "+Conditon;
        try {
            PreparedStatement preparedStatement=conn.prepareStatement(sql);
            //执行语句，得到结果集
            ResultSet resultSet=preparedStatement.executeQuery();
            while (resultSet.next()) {
                count=resultSet.getInt("VALUENUM");
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
        return count;
    }
    public List<SMART_GAME> findList(Map<String ,Object> map){
        SMART_GAME smartGame=null;
        List<SMART_GAME> list=new ArrayList<SMART_GAME>();
        Connection conn = JdbcUtils.getConn();
        //SQL语句
        String sql = "SELECT * FROM SMART_GAME WHERE 1=1 ";
        for(String key:map.keySet()){//keySet获取map集合key的集合  然后在遍历key即可
            sql+=" and "+key+"? ";
        }
        try {
            PreparedStatement preparedStatement=conn.prepareStatement(sql);
            int n=1;
            for(String key:map.keySet()){
                preparedStatement.setString(n, map.get(key).toString());
                n++;
            }
            //执行语句，得到结果集
            ResultSet resultSet=preparedStatement.executeQuery();
            while (resultSet.next()) {
                smartGame=new SMART_GAME();
                smartGame.setGUID(resultSet.getString("GUID"));
                smartGame.setUSER_ID(resultSet.getString("USER_ID"));
                smartGame.setAWARD_ID(resultSet.getString("AWARD_ID"));
                smartGame.setAWARD_NAME(resultSet.getString("AWARD_NAME"));
                smartGame.setWX_NAME(resultSet.getString("WX_NAME"));
                smartGame.setWX_IMG(resultSet.getString("WX_IMG"));
                smartGame.setDATATIME(resultSet.getString("DATATIME"));
                smartGame.setSTATUS(resultSet.getString("STATUS"));
                smartGame.setITEM_TYPE(resultSet.getString("ITEM_TYPE"));
                list.add(smartGame);
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
