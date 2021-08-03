package com.lxh.newrhjg.impl;

import com.lxh.newrhjg.api.IframeStore;
import com.lxh.newrhjg.entity.FramePeople;
import com.lxh.newrhjg.entity.FramePeopleEnjoy;
import com.lxh.newrhjg.entity.Frameserch;
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
public class FrameStoreImpl implements IframeStore {
    @Override
    public int insert(Frameserch record) {
        int flag=0;
        Connection conn = JdbcUtils.getConn();
        //SQL语句
        String sql = "INSERT INTO frame_serch(serchinfo,insertdate)" +
                " VALUES(?,?) ";
        try {
            PreparedStatement preparedStatement=conn.prepareStatement(sql);
            preparedStatement.setString(1,record.getSerchinfo());
            preparedStatement.setString(2,record.getInsertdate());

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
    public int update(Frameserch record) {
        int flag=0;
        Connection conn = JdbcUtils.getConn();
        //SQL语句
        String sql = "update Frame_People set phone=?,usertype=?,dealNum=?,dealMoney=?,iconurl=?,familiar=?,areaPro=?,subTool=?,isDS=?,isPro=?,superDes=?,password=?,familiarChina=?,openId=?,nickName=? where rowguid=?";
        try {
            PreparedStatement preparedStatement=conn.prepareStatement(sql);
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
    public int delete(Frameserch record) {
        int flag=0;
        Connection conn = JdbcUtils.getConn();
        //SQL语句
        String sql = "delete from  frame_serch  where serchinfo=?";
        try {
            PreparedStatement preparedStatement=conn.prepareStatement(sql);
            preparedStatement.setString(1,record.getSerchinfo());
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

    public Frameserch find(Map<String, Object> map){
        Frameserch record=null;
        Connection conn = JdbcUtils.getConn();
        //SQL语句
        String sql = "SELECT * FROM frame_serch WHERE 1=1  ";
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
                record=new Frameserch();
                //这里只查询的密码
                record.setRow_id(resultSet.getInt("row_id"));
                record.setSerchinfo(resultSet.getString("Serchinfo"));
                record.setInsertdate(resultSet.getString("Insertdate"));
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
    public List<Frameserch> findTop(Map<String, Object> map){
        Frameserch record=null;
        List<Frameserch> list=new ArrayList<>();
        Connection conn = JdbcUtils.getConn();
        //SQL语句
        String sql = "select count(1) as nums,serchinfo from frame_serch group by  serchinfo order by count(1) desc LIMIT 10";
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
                record=new Frameserch();
                //这里只查询的密
                record.setSerchinfo(resultSet.getString("Serchinfo"));
                record.setRow_id(resultSet.getInt("nums"));
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
