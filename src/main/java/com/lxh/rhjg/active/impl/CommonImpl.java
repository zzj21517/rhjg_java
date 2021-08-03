package com.lxh.rhjg.active.impl;

import com.lxh.rhjg.active.api.Icommon;
import com.lxh.rhjg.active.api.SMART_RULE;
import com.lxh.rhjg.entity.SMART_ERRORLOG;
import com.lxh.rhjg.entity.SMART_SSID;
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
public class CommonImpl implements Icommon {
    @Override
    public SMART_RULE findSmartrule(String fieldname, String fieldvalue){
        SMART_RULE smartRule=null;
        Connection conn = JdbcUtils.getConn();
        //SQL语句
        String sql = "select * From  SMART_RULE where "+fieldname+"=?";
        try {
            PreparedStatement preparedStatement=conn.prepareStatement(sql);
            preparedStatement.setString(1,fieldvalue);
            //执行语句，得到结果集
            ResultSet resultSet=preparedStatement.executeQuery();
            while (resultSet.next()) {
                //
                smartRule=new SMART_RULE();
                smartRule.setRuleId(resultSet.getString("RULE_ID"));
                smartRule.setRuleValue(resultSet.getString("RULE_VALUE"));
                smartRule.setPrefix(resultSet.getString("PREFIX"));
                smartRule.setRuleDesc(resultSet.getString("RULE_DESC"));
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
        return smartRule;
    }
    public SMART_SSID findSmartssid(String fieldname, String fieldvalue){
        SMART_SSID smartSsid=null;
        Connection conn = JdbcUtils.getConn();
        //SQL语句
        String sql = "select * From  SMART_SSID where "+fieldname+"=?";
        try {
            PreparedStatement preparedStatement=conn.prepareStatement(sql);
            preparedStatement.setString(1,fieldvalue);
            //执行语句，得到结果集
            ResultSet resultSet=preparedStatement.executeQuery();
            while (resultSet.next()) {
                //
                smartSsid=new SMART_SSID();
                smartSsid.setGUID(resultSet.getString("GUID"));
                smartSsid.setURL_CODE(resultSet.getString("URL_CODE"));
                smartSsid.setURL_NAME(resultSet.getString("URL_NAME"));
                smartSsid.setSSID_VALUE(resultSet.getString("SSID_VALUE"));
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
        return smartSsid;
    }
    @Override
    public void insertSmartrule(SMART_RULE smartRule) {

    }
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
    public void upSmartrule(SMART_RULE smartRule){
        Connection conn = JdbcUtils.getConn();
        //SQL语句
        String sql = "update SMART_RULE set RULE_VALUE=? where RULE_ID=?";
        try {
            PreparedStatement preparedStatement=conn.prepareStatement(sql);
            preparedStatement.setString(1,smartRule.getRuleValue());
            preparedStatement.setString(2,smartRule.getRuleId());
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
    public List<HashMap<String, Object>> findlist(String table, String sqlfield, Map<String,Object> map,String conditon,String order) {
        List<HashMap<String,Object>> list=new ArrayList<HashMap<String,Object>>();
        HashMap<String,Object> maps=new HashMap<>();
        Connection conn = JdbcUtils.getConn();
        //SQL语句
        String sql = "SELECT "+sqlfield+" FROM "+table+" WHERE 1=1  "+conditon;
        for(String key:map.keySet()){//keySet获取map集合key的集合  然后在遍历key即可
            sql+=" and "+key+"? ";
        }
        sql+=order;
        try {
            PreparedStatement preparedStatement=conn.prepareStatement(sql);
            int n=1;//新增参数
            for(String key:map.keySet()){
                preparedStatement.setString(n, map.get(key).toString());
                n++;
            }
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
}
