package com.lxh.rhjg.tip.Impl;

import com.lxh.rhjg.tip.api.ITip;
import com.lxh.rhjg.entity.SMART_TIP;
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
public class TipImpl implements ITip {
    @Override
    public void inserttip(SMART_TIP smartTip) {
        Connection conn = JdbcUtils.getConn();
        //SQL语句
        String sql = "INSERT INTO SMART_TIP(GUID,USER_ID,TITLE,CONTENT,DATATIME)" +
                " VALUES(?,?,?,?,?) ";
        try {
            PreparedStatement preparedStatement=conn.prepareStatement(sql);
            preparedStatement.setString(1,smartTip.getGUID());
            preparedStatement.setString(2,smartTip.getUSER_ID());
            preparedStatement.setString(3,smartTip.getTITLE());
            preparedStatement.setString(4,smartTip.getCONTENT());
            preparedStatement.setString(5,smartTip.getDATATIME());
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
    public List<SMART_TIP> find(Map<String,Object> map){
        List<SMART_TIP> list=new ArrayList<SMART_TIP>();
        SMART_TIP record=new SMART_TIP();
        Connection conn = JdbcUtils.getConn();
        //SQL语句
        String sql = "SELECT * FROM SMART_TIP WHERE 1=1  ";
        for(String key:map.keySet()){//keySet获取map集合key的集合  然后在遍历key即可
            sql+=" and "+key+"=? ";
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
                record=new SMART_TIP();
                record.setGUID(resultSet.getString("GUID"));
                record.setUSER_ID(resultSet.getString("USER_ID"));
                record.setTITLE(resultSet.getString("TITLE"));
                record.setCONTENT(resultSet.getString("CONTENT"));
                record.setDATATIME(resultSet.getString("DATATIME"));
                list.add(record);
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
