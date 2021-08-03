package com.lxh.rhjg.game.Impl;

import com.lxh.rhjg.entity.SMART_GAME;
import com.lxh.rhjg.game.api.IGame;
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
public class GameImpl implements IGame {


    @Override
    public List<HashMap<String, Object>> getGameCount() {
        HashMap<String,Object> map=null;
        List<HashMap<String,Object>> list=new ArrayList<HashMap<String,Object>>();
        Connection conn = JdbcUtils.getConn();
        //SQL语句
        String sql = "SELECT IFNULL(SUM(CASE WHEN AWARD_ID='1' THEN 1 ELSE 0 END),0) GONGZAI,IFNULL(SUM(CASE WHEN AWARD_ID='2' THEN 1 ELSE 0 END),0) HUANGJIN," +
                " IFNULL(SUM(CASE WHEN AWARD_ID='3' THEN 1 ELSE 0 END),0) ZUANSHI,IFNULL(SUM(CASE WHEN AWARD_ID='4' THEN 1 ELSE 0 END),0) FEE_3," +
                " IFNULL(SUM(CASE WHEN AWARD_ID='5' THEN 1 ELSE 0 END),0) FEE_1 FROM SMART_GAME WHERE ITEM_TYPE='0' AND STATUS='02' ";
        try {
            PreparedStatement preparedStatement=conn.prepareStatement(sql);
            //执行语句，得到结果集
            ResultSet resultSet=preparedStatement.executeQuery();
            while (resultSet.next()) {
                map=new HashMap<>();
                map.put("GONGZAI",resultSet.getString("GONGZAI"));
                map.put("HUANGJIN",resultSet.getString("HUANGJIN"));
                map.put("ZUANSHI",resultSet.getString("ZUANSHI"));
                map.put("FEE_3",resultSet.getString("FEE_3"));
                map.put("FEE_1",resultSet.getString("FEE_1"));
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
    @Override
    public List<HashMap<String,Object>> getBombCount(String userId){
        HashMap<String,Object> map=null;
        List<HashMap<String,Object>> list=new ArrayList<HashMap<String,Object>>();
        Connection conn = JdbcUtils.getConn();
        //SQL语句
        String sql = "SELECT IFNULL(SUM(CASE WHEN ITEM_TYPE = '1'  THEN 1 ELSE 0 END),0) TYPE1,IFNULL(SUM(CASE WHEN ITEM_TYPE = '2'  THEN 1 ELSE 0 END),0) TYPE2," +
                "IFNULL(SUM(CASE WHEN ITEM_TYPE = '3'  THEN 1 ELSE 0 END),0) TYPE3 FROM SMART_GAME WHERE USER_ID=? ";
        try {
            PreparedStatement preparedStatement=conn.prepareStatement(sql);
            preparedStatement.setString(1,userId);
            //执行语句，得到结果集
            ResultSet resultSet=preparedStatement.executeQuery();
            while (resultSet.next()) {
                map=new HashMap<>();
                map.put("TYPE1",resultSet.getString("TYPE1"));
                map.put("TYPE2",resultSet.getString("TYPE2"));
                map.put("TYPE3",resultSet.getString("TYPE3"));
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
    @Override
    public void insert(SMART_GAME smartGame) {
        Connection conn = JdbcUtils.getConn();
        //SQL语句
        String sql = "INSERT INTO SMART_GAME(GUID,USER_ID,AWARD_ID,AWARD_NAME,WX_NAME,WX_IMG,DATATIME,STATUS,ITEM_TYPE)"+
                " VALUES(?,?,?,?,?,?,?,?,?) ";
        try {
            PreparedStatement preparedStatement=conn.prepareStatement(sql);
            preparedStatement.setString(1,smartGame.getGUID());
            preparedStatement.setString(2,smartGame.getUSER_ID());
            preparedStatement.setString(3,smartGame.getAWARD_ID());
            preparedStatement.setString(4,smartGame.getAWARD_NAME());
            preparedStatement.setString(5,smartGame.getWX_NAME());
            preparedStatement.setString(6,smartGame.getWX_IMG());
            preparedStatement.setString(7,smartGame.getDATATIME());
            preparedStatement.setString(8,smartGame.getSTATUS());
            preparedStatement.setString(9,smartGame.getITEM_TYPE());
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
