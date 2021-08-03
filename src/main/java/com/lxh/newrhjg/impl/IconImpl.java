package com.lxh.newrhjg.impl;

import com.lxh.newrhjg.api.IIcon;
import com.lxh.newrhjg.entity.FrameIcon;
import com.lxh.test.common.JdbcUtils;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

@Component
@Service
public class IconImpl implements IIcon {
    @Override
    public int insert(FrameIcon record) {
        int flag=0;
        Connection conn = JdbcUtils.getConn();
        //SQL语句
        String sql = "INSERT INTO frame_icon(rowGuid,iconThem,iconName,iconurl,Iconlinkurl,ordernum)" +
                " VALUES(?,?,?,?,?,?) ";
        try {
            PreparedStatement preparedStatement=conn.prepareStatement(sql);
            preparedStatement.setString(1,record.getRowGuid());
            preparedStatement.setString(2,record.getIconThem());
            preparedStatement.setString(3,record.getIconName());
            preparedStatement.setString(4,record.getIconurl());
            preparedStatement.setString(5,record.getIconlinkurl());
            preparedStatement.setInt(6,record.getOrdernum());
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
    public int update(FrameIcon record) {
        int flag=0;
        Connection conn = JdbcUtils.getConn();
        //SQL语句
        String sql = "update frame_icon set iconThem=?,iconName=?,iconurl=?,Iconlinkurl=?,ordernum=? where rowguid=?";
        try {
            PreparedStatement preparedStatement=conn.prepareStatement(sql);
            preparedStatement.setString(1,record.getIconThem());
            preparedStatement.setString(2,record.getIconName());
            preparedStatement.setString(3,record.getIconurl());
            preparedStatement.setString(4,record.getIconlinkurl());
            preparedStatement.setInt(5,record.getOrdernum());
            preparedStatement.setString(6,record.getRowGuid());
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
    public int delete(FrameIcon record) {
        int flag=0;
        Connection conn = JdbcUtils.getConn();
        //SQL语句
        String sql = "delete from  frame_icon  where rowguid=?";
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


}
