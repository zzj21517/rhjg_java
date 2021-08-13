package com.lxh.rhjg.active.impl;
import com.alibaba.fastjson.JSONObject;
import com.lxh.rhjg.active.api.ILuck;
import com.lxh.rhjg.active.api.SMART_LUCKY;
import com.lxh.rhjg.entity.SMART_TRADE;
import com.lxh.test.common.JdbcUtils;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Component
@Service
public class LuckImpl implements ILuck {
    @Override
    public SMART_LUCKY findLuckbag(String fieldname, String fieldvalue){
        SMART_LUCKY smart_lucky=null;
        Connection conn = JdbcUtils.getConn();
        //SQL语句
        String sql = "select * From  SMART_LUCKY where "+fieldname+"=?";
        try {
            PreparedStatement preparedStatement=conn.prepareStatement(sql);
            preparedStatement.setString(1,fieldvalue);
            //执行语句，得到结果集
         ResultSet resultSet=preparedStatement.executeQuery();
            while (resultSet.next()) {
                //
                smart_lucky=new SMART_LUCKY();
                smart_lucky.setGuid(resultSet.getString("Guid"));
                smart_lucky.setDATA_TIME(resultSet.getString("DATA_TIME"));
                smart_lucky.setLUCKY_AMT(resultSet.getString("LUCKY_AMT"));
                smart_lucky.setPROJECT_NUM(resultSet.getString("PROJECT_NUM"));
                smart_lucky.setSTATUS(resultSet.getString("STATUS"));
                smart_lucky.setUSER_ID(resultSet.getString("USER_ID"));
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
        return smart_lucky;
    }
    @Override
    public void insetLuckbag(SMART_LUCKY luckBag){
        Connection conn = JdbcUtils.getConn();
        //SQL语句
        String sql = "INSERT INTO SMART_LUCKY(GUID,USER_ID,PROJECT_NUM,LUCKY_AMT,DATA_TIME,STATUS) VALUES (?,?,?,?,?,?)";
        try {
            PreparedStatement preparedStatement=conn.prepareStatement(sql);
            preparedStatement.setString(1,luckBag.getGuid());
            preparedStatement.setString(2,luckBag.getUSER_ID());
            preparedStatement.setString(3,luckBag.getPROJECT_NUM());
            preparedStatement.setString(4,luckBag.getLUCKY_AMT());
            preparedStatement.setString(5,luckBag.getDATA_TIME());
            preparedStatement.setString(6,luckBag.getSTATUS());
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
    public void insettrade(SMART_TRADE smartTrade){
        Connection conn = JdbcUtils.getConn();
        //SQL语句
        String sql = "INSERT INTO SMART_TRADE(GUID,TRADE_NUM,TRADE_AMT,CONTENT,STATUS,USER_ID,ITEM_TYPE,DATATIME,PROJECT_NUM)" +
                " VALUES (?,?,?,?,?,?,?,?,?)";
        try {
            PreparedStatement preparedStatement=conn.prepareStatement(sql);
            preparedStatement.setString(1,smartTrade.getGUID());
            System.out.println(smartTrade.getTRADE_NUM());
            System.out.println(121);
            preparedStatement.setString(2,smartTrade.getTRADE_NUM());
            preparedStatement.setString(3,smartTrade.getTRADE_AMT());
            preparedStatement.setString(4,smartTrade.getCONTENT());
            preparedStatement.setString(5,smartTrade.getSTATUS());
            preparedStatement.setString(6,smartTrade.getUSER_ID());
            preparedStatement.setString(7,smartTrade.getITEM_TYPE());
            preparedStatement.setString(8,smartTrade.getDATATIME());
            preparedStatement.setString(9,smartTrade.getPROJECT_NUM());
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
    public List<SMART_LUCKY> GetLuckyBag(String uid, int start, int length){
        SMART_LUCKY smart_lucky=null;
        List<SMART_LUCKY> list=new ArrayList<SMART_LUCKY>();
        Connection conn = JdbcUtils.getConn();
        //SQL语句
        String sql = "SELECT A.GUID,A.PROJECT_NUM,A.LUCKY_AMT,A.DATA_TIME,B.DICT_VALUE,C.IMG_PATH " +
                " FROM SMART_LUCKY A,SMART_DICT B,SMART_USER_INFO C WHERE A.USER_ID=? AND A.STATUS=B.DICT_ID " +
                " AND B.DICT_CODE='RHJG_LUCKY_STATUS' AND A.USER_ID=C.USER_ID " +
                " ORDER BY DATA_TIME DESC LIMIT ?,?";
        try {
            PreparedStatement preparedStatement=conn.prepareStatement(sql);
            preparedStatement.setString(1,uid);
            preparedStatement.setInt(2,start);
            preparedStatement.setInt(3,length);
            //执行语句，得到结果集
            ResultSet resultSet=preparedStatement.executeQuery();
            while (resultSet.next()) {
                smart_lucky=new SMART_LUCKY();
                smart_lucky.setGuid(resultSet.getString("Guid"));
                smart_lucky.setDATA_TIME(resultSet.getString("DATA_TIME"));
                smart_lucky.setLUCKY_AMT(resultSet.getString("LUCKY_AMT"));
                smart_lucky.setPROJECT_NUM(resultSet.getString("PROJECT_NUM"));
                smart_lucky.setSTATUS(resultSet.getString("STATUS"));
                smart_lucky.setUSER_ID(resultSet.getString("USER_ID"));
                list.add(smart_lucky);
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
    public JSONObject GetLuckyBagTotal(String uid){
        JSONObject jsonObject=new JSONObject();
        Connection conn = JdbcUtils.getConn();
        //SQL语句
        String sql = "SELECT FORMAT(SUM(LUCKY_AMT),2) LUCKY_AMT_TOTAL,FORMAT(SUM(CASE WHEN STATUS='00' THEN LUCKY_AMT ELSE 0 END ),2) LUCKY_AMT_USEFUL" +
                "        FROM SMART_LUCKY WHERE USER_ID=?";
        try {
            PreparedStatement preparedStatement=conn.prepareStatement(sql);
            preparedStatement.setString(1,uid);
            //执行语句，得到结果集
            ResultSet resultSet=preparedStatement.executeQuery();
            while (resultSet.next()) {
                jsonObject=new JSONObject();
                jsonObject.put("LUCKY_AMT_TOTAL",resultSet.getString("LUCKY_AMT_TOTAL"));
                jsonObject.put("LUCKY_AMT_USEFUL",resultSet.getString("LUCKY_AMT_USEFUL"));
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
        return jsonObject;
    }
    @Override
    public JSONObject GetSmartShare(String openId, String uid){
        JSONObject jsonObject=new JSONObject();
        Connection conn = JdbcUtils.getConn();
        //SQL语句
        String sql = "SELECT * FROM SMART_SHARE WHERE SHARE_ID=? AND USER_ID=?";
        try {
            PreparedStatement preparedStatement=conn.prepareStatement(sql);
            preparedStatement.setString(1,openId);
            preparedStatement.setString(2,uid);
            //执行语句，得到结果集
            ResultSet resultSet=preparedStatement.executeQuery();
            while (resultSet.next()) {
                jsonObject=new JSONObject();
                jsonObject.put("guid",resultSet.getString("guid"));
                jsonObject.put("user_id",resultSet.getString("user_id"));
                jsonObject.put("share_id",resultSet.getString("share_id"));
                jsonObject.put("share_name",resultSet.getString("share_name"));
                jsonObject.put("share_image",resultSet.getString("share_image"));
                jsonObject.put("datatime",resultSet.getString("datatime"));
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
        return jsonObject;
    }
    @Override
    public JSONObject GetSmartSharebyUid(String uid){
        JSONObject jsonObject=new JSONObject();
        Connection conn = JdbcUtils.getConn();
        //SQL语句
        String sql = "SELECT * FROM SMART_SHARE WHERE  USER_ID=?";
        try {
            PreparedStatement preparedStatement=conn.prepareStatement(sql);
            preparedStatement.setString(1,uid);
            //执行语句，得到结果集
            ResultSet resultSet=preparedStatement.executeQuery();
            while (resultSet.next()) {
                jsonObject=new JSONObject();
                jsonObject.put("guid",resultSet.getString("guid"));
                jsonObject.put("user_id",resultSet.getString("user_id"));
                jsonObject.put("share_id",resultSet.getString("share_id"));
                jsonObject.put("share_name",resultSet.getString("share_name"));
                jsonObject.put("share_image",resultSet.getString("share_image"));
                jsonObject.put("datatime",resultSet.getString("datatime"));
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
        return jsonObject;
    }
    @Override
    public void insertSmartShare(JSONObject jsonObject){
        Connection conn = JdbcUtils.getConn();
        //SQL语句
        String sql = "INSERT INTO SMART_SHARE(GUID,USER_ID,SHARE_ID,SHARE_NAME,SHARE_IMAGE,DATATIME)" +
                " VALUES(?,?,?,?,?,?) ";
        try {
            PreparedStatement preparedStatement=conn.prepareStatement(sql);
            preparedStatement.setString(1,jsonObject.get("guid").toString());
            preparedStatement.setString(2,jsonObject.get("uid").toString());
            preparedStatement.setString(3,jsonObject.get("openId").toString());
            preparedStatement.setString(4,jsonObject.get("name").toString());
            preparedStatement.setString(5,jsonObject.get("image").toString());
            preparedStatement.setString(6,jsonObject.get("datatime").toString());
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
    public List<JSONObject> GetBannerLuckyBag(){
        JSONObject jsonObject=null;
        List<JSONObject> list=new ArrayList<JSONObject>();
        Connection conn = JdbcUtils.getConn();
        //SQL语句
        String sql = "SELECT SUBSTR(B.NICK_NAME,2) NICK_NAME,FORMAT(SUM(A.LUCKY_AMT),2) LUCKY_AMT  FROM SMART_LUCKY A,smart_people B WHERE A.USER_ID=B.USER_ID GROUP BY NICK_NAME ";
        try {
            PreparedStatement preparedStatement=conn.prepareStatement(sql);
            //执行语句，得到结果集
            ResultSet resultSet=preparedStatement.executeQuery();
            while (resultSet.next()) {
                jsonObject=new JSONObject();
                jsonObject.put("NICK_NAME",resultSet.getString("NICK_NAME"));
                jsonObject.put("LUCKY_AMT",resultSet.getString("LUCKY_AMT"));
                list.add(jsonObject);
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
