package com.lxh.newrhjg.impl;


import com.lxh.newrhjg.api.IframeAttachinfo;
import com.lxh.newrhjg.api.IframePeopleGoodAt;
import com.lxh.newrhjg.entity.FrameAttchinfo;
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
public class FrameAttachinfoImpl implements IframeAttachinfo {
    @Override
    public int insert(FrameAttchinfo record) {
        int flag=0;
        Connection conn = JdbcUtils.getConn();
        //SQL语句
        String sql = "INSERT INTO frame_attachinfo(attachguid,clientguid,filepath,filename,filesize,filetype,uploadtime)" +
                " VALUES(?,?,?,?,?,?,?) ";
        try {
            PreparedStatement preparedStatement=conn.prepareStatement(sql);
            preparedStatement.setString(1,record.getAttachguid());
            preparedStatement.setString(2,record.getClientguid());
            preparedStatement.setString(3,record.getFilepath());
            preparedStatement.setString(4,record.getFilename());
            preparedStatement.setString(5,record.getFilesize());
            preparedStatement.setString(6,record.getFiletype());
            preparedStatement.setString(7,record.getUploadtime());
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
    public int update(FrameAttchinfo record) {
        int flag=0;
        Connection conn = JdbcUtils.getConn();
        //SQL语句
        String sql = "update frame_attachinfo set clientguid=?,filepath=?,filename=?,filesize=?,filetype=?,uploadtime=? where attachguid=?";
        try {
            PreparedStatement preparedStatement=conn.prepareStatement(sql);
            preparedStatement.setString(1,record.getClientguid());
            preparedStatement.setString(2,record.getFilepath());
            preparedStatement.setString(3,record.getFilename());
            preparedStatement.setString(4,record.getFilesize());
            preparedStatement.setString(5,record.getFiletype());
            preparedStatement.setString(6,record.getUploadtime());
            preparedStatement.setString(7,record.getAttachguid());
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
    public int delete(FrameAttchinfo record) {
        int flag=0;
        Connection conn = JdbcUtils.getConn();
        //SQL语句
        String sql = "delete from  frame_attachinfo  where attachguid=?";
        try {
            PreparedStatement preparedStatement=conn.prepareStatement(sql);
            preparedStatement.setString(1,record.getAttachguid());
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
    public FrameAttchinfo find(Map<String, Object> map) {
        FrameAttchinfo record=null;
        Connection conn = JdbcUtils.getConn();
        //SQL语句
        String sql = "SELECT * FROM frame_attachinfo WHERE 1=1  ";
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
                record=new FrameAttchinfo();
                record.setAttachguid(resultSet.getString("attachguid"));
                record.setClientguid(resultSet.getString("clientguid"));
                record.setFilepath(resultSet.getString("filepath"));
                record.setFilename(resultSet.getString("filename"));
                record.setFilesize(resultSet.getString("filesize"));
                record.setFiletype(resultSet.getString("filetype"));
                record.setUploadtime(resultSet.getString("uploadtime"));
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
