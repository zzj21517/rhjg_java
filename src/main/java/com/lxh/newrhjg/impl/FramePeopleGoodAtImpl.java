package com.lxh.newrhjg.impl;


import com.lxh.newrhjg.api.IframePeopleGoodAt;
import com.lxh.newrhjg.entity.FramePeopleExtendinfo;
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
public class FramePeopleGoodAtImpl implements IframePeopleGoodAt {
    @Override
    public int insert(FramepeopleGoodat record) {
        int flag=0;
        Connection conn = JdbcUtils.getConn();
        //SQL语句
        String sql = "INSERT INTO frame_people_goodat(rowGuid,userGuid,goodAt,price,goodAtChina,remark,productIcon,productName,isSJ,area,addtime)" +
                " VALUES(?,?,?,?,?,?,?,?,?,?,?) ";
        try {
            PreparedStatement preparedStatement=conn.prepareStatement(sql);
            preparedStatement.setString(1,record.getRowGuid());
            preparedStatement.setString(2,record.getUserGuid());
            preparedStatement.setString(3,record.getGoodAt());
            preparedStatement.setString(4,record.getPrice());
            preparedStatement.setString(5,record.getGoodAtChina());
            preparedStatement.setString(6,record.getRemark());
            preparedStatement.setString(7,record.getProductIcon());
            preparedStatement.setString(8,record.getProductName());
            preparedStatement.setString(9,record.getIsSJ());
            preparedStatement.setString(10,record.getArea());
            preparedStatement.setString(11,record.getAddtime());
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
    public int update(FramepeopleGoodat record) {
        int flag=0;
        Connection conn = JdbcUtils.getConn();
        //SQL语句
        String sql = "update frame_people_goodat set userGuid=?,goodAt=?,price=?,goodAtChina=?,remark=?,productIcon=?,productName=?,isSJ=?,area=?,addtime=? where rowguid=?";
        try {
            PreparedStatement preparedStatement=conn.prepareStatement(sql);
            preparedStatement.setString(1,record.getUserGuid());
            preparedStatement.setString(2,record.getGoodAt());
            preparedStatement.setString(3,record.getPrice());
            preparedStatement.setString(4,record.getGoodAtChina());
            preparedStatement.setString(5,record.getRemark());
            preparedStatement.setString(6,record.getProductIcon());
            preparedStatement.setString(7,record.getProductName());
            preparedStatement.setString(8,record.getIsSJ());
            preparedStatement.setString(9,record.getArea());
            preparedStatement.setString(10,record.getAddtime());
            preparedStatement.setString(11,record.getRowGuid());
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
    public int deleteByUser(String userGuid) {
        int flag=0;
        Connection conn = JdbcUtils.getConn();
        //SQL语句
        String sql = "delete from  frame_people_goodat  where userGuid=?";
        try {
            PreparedStatement preparedStatement=conn.prepareStatement(sql);
            preparedStatement.setString(1,userGuid);
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
    public int delete(FramepeopleGoodat record) {
        int flag=0;
        Connection conn = JdbcUtils.getConn();
        //SQL语句
        String sql = "delete from  frame_people_goodat  where rowguid=?";
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
    public FramepeopleGoodat find(Map<String, Object> map) {
        FramepeopleGoodat record=null;
        Connection conn = JdbcUtils.getConn();
        //SQL语句
        String sql = "SELECT * FROM frame_people_goodat WHERE 1=1  ";
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
                record=new FramepeopleGoodat();
                record.setRow_id(resultSet.getInt("row_id"));
                record.setRowGuid(resultSet.getString("RowGuid"));
                record.setUserGuid(resultSet.getString("userGuid"));
                record.setGoodAt(resultSet.getString("goodAt"));
                record.setPrice(resultSet.getString("price"));
                record.setGoodAtChina(resultSet.getString("goodAtChina"));
                record.setRemark(resultSet.getString("Remark"));
                record.setProductIcon(resultSet.getString("ProductIcon"));
                record.setProductName(resultSet.getString("ProductName"));
                record.setIsSJ(resultSet.getString("IsSJ"));
                record.setArea(resultSet.getString("Area"));
                record.setAddtime(resultSet.getString("Addtime"));
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
