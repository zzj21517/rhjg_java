package com.lxh.rhjg.judge.Impl;

import com.lxh.rhjg.active.api.Icommon;
import com.lxh.rhjg.active.api.SMART_PROJECT_USER;
import com.lxh.rhjg.active.api.SMART_RULE;
import com.lxh.rhjg.entity.SMART_JUDGE;
import com.lxh.rhjg.judge.api.IJudge;
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
public class JudgeImpl implements IJudge {

    @Override
    public List<SMART_JUDGE> findList(Map<String, Object> map) {
        List<SMART_JUDGE> list=new ArrayList<SMART_JUDGE>();
        SMART_JUDGE record=new SMART_JUDGE();
        Connection conn = JdbcUtils.getConn();
        //SQL语句
        String sql = "SELECT * FROM SMART_JUDGE WHERE 1=1  ";
        for(String key:map.keySet()){//keySet获取map集合key的集合  然后在遍历key即可
            sql+=" and "+key+"=? ";
        }
        try {
            PreparedStatement preparedStatement=conn.prepareStatement(sql);
            int n=1;
            for(String key:map.keySet()){
                preparedStatement.setString(n, map.get(key).toString());
            }
            //执行语句，得到结果集
            ResultSet resultSet=preparedStatement.executeQuery();
            while (resultSet.next()) {
                record=new SMART_JUDGE();
                record.setGUID(resultSet.getString("GUID"));
                record.setCUST_ID(resultSet.getString("CUST_ID"));
                record.setPROJECT_NUM(resultSet.getString("PROJECT_NUM"));
                record.setDATATIME1(resultSet.getString("DATATIME1"));
                record.setJUDGE_USER(resultSet.getString("JUDGE_USER"));
                record.setCONTENT(resultSet.getString("CONTENT"));
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
    @Override
    public void insert(SMART_JUDGE smartJudge){
        Connection conn = JdbcUtils.getConn();
        //SQL语句
        String sql = "INSERT INTO SMART_JUDGE(GUID,CUST_ID,PROJECT_NUM,DATATIME1,JUDGE_USER,CONTENT)"+
                "VALUES(?,?,?,?,?,?) ";
        try {
            PreparedStatement preparedStatement=conn.prepareStatement(sql);
            preparedStatement.setString(1,smartJudge.getGUID());
            preparedStatement.setString(2,smartJudge.getCUST_ID());
            preparedStatement.setString(3,smartJudge.getPROJECT_NUM());
            preparedStatement.setString(4,smartJudge.getDATATIME1());
            preparedStatement.setString(5,smartJudge.getJUDGE_USER());
            preparedStatement.setString(6,smartJudge.getCONTENT());
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
