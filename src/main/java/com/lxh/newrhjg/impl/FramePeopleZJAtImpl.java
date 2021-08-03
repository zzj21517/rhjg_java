package com.lxh.newrhjg.impl;


import com.lxh.newrhjg.api.IframePeopleZJ;
import com.lxh.newrhjg.entity.FramePeopleZj;
import com.lxh.newrhjg.entity.FramepeopleGoodat;
import com.lxh.test.common.JdbcUtils;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;

@Component
@Service
public class FramePeopleZJAtImpl implements IframePeopleZJ {
    @Override
    public int insert(FramePeopleZj record) {
        int flag=0;
        Connection conn = JdbcUtils.getConn();
        //SQL语句
        String sql = "INSERT INTO frame_people_zj(rowGuid,userGuid,zjName,zjCert)" +
                " VALUES(?,?,?,?) ";
        try {
            PreparedStatement preparedStatement=conn.prepareStatement(sql);
            preparedStatement.setString(1,record.getRowGuid());
            preparedStatement.setString(2,record.getUserGuid());
            preparedStatement.setString(3,record.getZjName());
            preparedStatement.setString(4,record.getZjCert());
            //执行语句，得到结果集
            preparedStatement.execute();
            flag=1;
        } catch (SQLException e1) {
            e1.printStackTrace();
        }finally {
            try {
                conn.close();//关闭连接
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return flag;
    }

    @Override
    public int update(FramePeopleZj record) {
        int flag=0;
        Connection conn = JdbcUtils.getConn();
        //SQL语句
        String sql = "update frame_people_zj set userGuid=?,zjName=?,zjCert=? where rowguid=?";
        try {
            PreparedStatement preparedStatement=conn.prepareStatement(sql);
            preparedStatement.setString(1,record.getUserGuid());
            preparedStatement.setString(2,record.getZjName());
            preparedStatement.setString(3,record.getZjCert());
            preparedStatement.setString(4,record.getRowGuid());
            //执行语句，得到结果集
            preparedStatement.execute();
            flag=1;
        } catch (SQLException e1) {
            e1.printStackTrace();
        }finally {
            try {
                conn.close();//关闭连接
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return flag;
    }

    @Override
    public int delete(FramePeopleZj record) {
        int flag=0;
        Connection conn = JdbcUtils.getConn();
        //SQL语句
        String sql = "delete from  frame_people_zj  where rowguid=?";
        try {
            PreparedStatement preparedStatement=conn.prepareStatement(sql);
            preparedStatement.setString(1,record.getRowGuid());
            //执行语句，得到结果集
            preparedStatement.execute();
            flag=1;
        } catch (SQLException e1) {
            e1.printStackTrace();
        }finally {
            try {
                conn.close();//关闭连接
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return flag;
    }
    @Override
    public FramePeopleZj find(Map<String, Object> map) {
        FramePeopleZj record=null;
        Connection conn = JdbcUtils.getConn();
        //SQL语句
        String sql = "SELECT * FROM frame_people_zj WHERE 1=1  ";
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
                record=new FramePeopleZj();
                record.setRow_id(resultSet.getInt("row_id"));
                record.setUserGuid(resultSet.getString("userGuid"));
                record.setZjName(resultSet.getString("zjName"));
                record.setZjCert(resultSet.getString("zjCert"));
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
        return record;
    }

}
