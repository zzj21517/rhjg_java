package com.lxh.newrhjg.impl;

import com.lxh.newrhjg.api.Inewcommon;
import com.lxh.newrhjg.entity.FramePeople;
import com.lxh.rhjg.entity.SMART_ERRORLOG;
import com.lxh.test.common.JdbcUtils;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
@Service
public class NewCommonImpl implements Inewcommon {

    @Override
    public void insertlog(SMART_ERRORLOG record){
        Connection conn = JdbcUtils.getConn();
        //SQL语句
        String sql = "INSERT INTO SMART_ERRORLOG(GUID,DATETIME,errorlog)" +
                " VALUES(?,?,?) ";
        try {
            PreparedStatement preparedStatement=conn.prepareStatement(sql);
            preparedStatement.setString(1,record.getGuid());
            preparedStatement.setString(2,record.getDatetime());
            preparedStatement.setString(3,record.getErrorlog());
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
    public void deleteCommon(String table, String sqlwhere){
        Connection conn = JdbcUtils.getConn();
        //SQL语句
        String sql = "delete "+table+" where 1=1 and "+sqlwhere;
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
    public void updateCommon(String table, String sqlfield, String sqlwhere){
        Connection conn = JdbcUtils.getConn();
        //SQL语句
        String sql = "update "+table+" set "+sqlfield+" where 1=1 "+sqlwhere;
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
    public List<HashMap<String, Object>> findlist(String table, String sqlfield, Map<String,Object> map,String conditon,String order,int pagenum,int pagesize) {
        List<HashMap<String,Object>> list=new ArrayList<HashMap<String,Object>>();
        HashMap<String,Object> maps=new HashMap<>();
        Connection conn = JdbcUtils.getConn();

        //SQL语句
        String sql = "SELECT "+sqlfield+" FROM "+table+" WHERE 1=1  "+conditon;
        if(map!=null){
        for(String key:map.keySet()){//keySet获取map集合key的集合  然后在遍历key即可
            sql+=" and "+key+"? ";
        }}
        sql+=order;
        sql+=" limit "+pagenum+","+pagesize;
        try {
            PreparedStatement preparedStatement=conn.prepareStatement(sql);
            int n=1;//新增参数
            if(map!=null){
            for(String key:map.keySet()){
                preparedStatement.setString(n, map.get(key).toString());
                n++;
            }}
            //执行语句，得到结果集
            ResultSet resultSet=preparedStatement.executeQuery();
            ResultSetMetaData rsmd = resultSet.getMetaData();
            //获取查询数据列数
            int columnCount =rsmd.getColumnCount();
            while (resultSet.next()) {
                rsmd= resultSet.getMetaData();
                maps=new HashMap<String,Object>();
                for(int i=1;i<=columnCount;i++){
                    //获取查询值遍历赋值
                    maps.put(rsmd.getColumnName(i),resultSet.getString(rsmd.getColumnName(i)));
                }
                list.add(maps);
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
    public String getitemtext(String code,String itemvalue){
        String resultvalue="";
        Connection conn = JdbcUtils.getConn();
        String sql = "select itemtext From frame_item a,frame_code  b where a.codeid=b.codeid  and a.codeid=? and a.itemvalue=?";
        try{
        PreparedStatement preparedStatement=conn.prepareStatement(sql);
        preparedStatement.setString(1,code);
        preparedStatement.setString(2,itemvalue);
            ResultSet resultSet=preparedStatement.executeQuery();
            while (resultSet.next()) {
                //这里只查询的密码
                resultvalue=resultSet.getString("itemtext");
            }
        }catch (SQLException e1) {
            e1.printStackTrace();
        }finally {
            try {
                conn.close();//关闭连接
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return  resultvalue;
    }
    public int  findlist(String table,  Map<String, Object> map, String conditon){
        Connection conn = JdbcUtils.getConn();
        int columnCount=0;
        //SQL语句
        String sql = "SELECT count(1) as columnCount FROM "+table+" WHERE 1=1  "+conditon;
        if(map!=null){
            for(String key:map.keySet()){//keySet获取map集合key的集合  然后在遍历key即可
                sql+=" and "+key+"? ";
            }}
        try {
            PreparedStatement preparedStatement=conn.prepareStatement(sql);
            int n=1;//新增参数
            if(map!=null){
                for(String key:map.keySet()){
                    preparedStatement.setString(n, map.get(key).toString());
                    n++;
                }}
            //执行语句，得到结果集
            ResultSet resultSet=preparedStatement.executeQuery();
            resultSet.next();
            columnCount = Integer.parseInt(resultSet.getString("columnCount").toString()) ; // 得到当前行号，即结果集记录数
        } catch (SQLException e1) {
            e1.printStackTrace();
        }finally {
            try {
                conn.close();//关闭连接
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return columnCount;
    }

}
