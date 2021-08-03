package com.lxh.newrhjg.impl;


import com.lxh.newrhjg.api.IProductPrice;
import com.lxh.newrhjg.entity.FrameAttchinfo;
import com.lxh.newrhjg.entity.ProductPrice;
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
public class ProductPriceImpl implements IProductPrice {
    @Override
    public int insert(ProductPrice record) {
        int flag=0;
        Connection conn = JdbcUtils.getConn();
        //SQL语句
        String sql = "INSERT INTO product_price(productguid,rowguid,price,dw,content)" +
                " VALUES(?,?,?,?,?) ";
        try {
            PreparedStatement preparedStatement=conn.prepareStatement(sql);
            preparedStatement.setString(1,record.getProductguid());
            preparedStatement.setString(2,record.getRowguid());
            preparedStatement.setDouble(3,record.getPrice());
            preparedStatement.setString(4,record.getDw());
            preparedStatement.setString(5,record.getContent());
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
    public int update(ProductPrice record) {
        int flag=0;
        Connection conn = JdbcUtils.getConn();
        //SQL语句
        String sql = "update product_price set price=?,dw=?,content=?  where rowguid=?";
        try {
            PreparedStatement preparedStatement=conn.prepareStatement(sql);
            preparedStatement.setDouble(1,record.getPrice());
            preparedStatement.setString(2,record.getDw());
            preparedStatement.setString(3,record.getContent());
            preparedStatement.setString(4,record.getRowguid());
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
    public int delete(ProductPrice record) {
        int flag=0;
        Connection conn = JdbcUtils.getConn();
        //SQL语句
        String sql = "delete from  product_price  where rowguid=?";
        try {
            PreparedStatement preparedStatement=conn.prepareStatement(sql);
            preparedStatement.setString(1,record.getRowguid());
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
    public ProductPrice find(Map<String, Object> map) {
        ProductPrice record=null;
        Connection conn = JdbcUtils.getConn();
        //SQL语句
        String sql = "SELECT * FROM product_price WHERE 1=1  ";
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
                record=new ProductPrice();
                record.setProductguid(resultSet.getString("productguid"));
                record.setRowguid(resultSet.getString("rowguid"));
                record.setPrice(resultSet.getFloat("price"));
                record.setDw(resultSet.getString("dw"));
                record.setContent(resultSet.getString("content"));
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
