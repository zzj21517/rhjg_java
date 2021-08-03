package com.lxh.rhjg.point.Impl;
import com.lxh.rhjg.entity.SMART_POINT_RECORD;
import com.lxh.rhjg.point.api.IPoint;
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
public class PointImpl  implements IPoint {
    @Override
    public List<SMART_POINT_RECORD> findList(Map<String, Object> map) {
        SMART_POINT_RECORD record=null;
        List<SMART_POINT_RECORD> list=new ArrayList<SMART_POINT_RECORD>();
        Connection conn = JdbcUtils.getConn();
        //SQL语句
        String sql = "SELECT * FROM SMART_POINT_RECORD WHERE 1=1 ";
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
                record=new SMART_POINT_RECORD();
                record.setGUID(resultSet.getString("GUID"));
                record.setSHORT_CODE(resultSet.getString("USER_ID"));
                record.setSHARE_NAME(resultSet.getString("AWARD_ID"));
                record.setSHARE_IMAGE(resultSet.getString("AWARD_NAME"));
                record.setSHARE_ID(resultSet.getString("WX_NAME"));
                record.setDATATIME(resultSet.getString("WX_IMG"));
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
    public void insert(SMART_POINT_RECORD smartPointRecord) {
        Connection conn = JdbcUtils.getConn();
        //SQL语句
        String sql = "INSERT INTO SMART_POINT_RECORD(GUID,SHORT_CODE,SHARE_ID,SHARE_NAME,SHARE_IMAGE,DATATIME)"+
                " VALUES(?,?,?,?,?,?) ";
        try {
            PreparedStatement preparedStatement=conn.prepareStatement(sql);
            preparedStatement.setString(1,smartPointRecord.getGUID());
            preparedStatement.setString(2,smartPointRecord.getSHORT_CODE());
            preparedStatement.setString(3,smartPointRecord.getSHARE_ID());
            preparedStatement.setString(4,smartPointRecord.getSHARE_IMAGE());
            preparedStatement.setString(5,smartPointRecord.getSHARE_IMAGE());
            preparedStatement.setString(6,smartPointRecord.getDATATIME());
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
