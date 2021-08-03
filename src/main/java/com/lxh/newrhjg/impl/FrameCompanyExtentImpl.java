package com.lxh.newrhjg.impl;


import com.lxh.newrhjg.api.IframeCompanyExtendinfo;
import com.lxh.newrhjg.api.IframePeopleZJ;
import com.lxh.newrhjg.entity.FrameCompanyExtendinfo;
import com.lxh.newrhjg.entity.FramePeopleZj;
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
public class FrameCompanyExtentImpl implements IframeCompanyExtendinfo {
    @Override
    public int insert(FrameCompanyExtendinfo record) {
        int flag=0;
        Connection conn = JdbcUtils.getConn();
        //SQL语句
        String sql = "INSERT INTO frame_company_extendinfo(rowGuid,userGuid,isYZ,memberNum,license,aboutUS,comanyName,createDate,comanyAddress,comanyQualify)" +
                " VALUES(?,?,?,?,?,?,?,?,?,?) ";
        try {
            PreparedStatement preparedStatement=conn.prepareStatement(sql);
            preparedStatement.setString(1,record.getRowGuid());
            preparedStatement.setString(2,record.getUserGuid());
            preparedStatement.setString(3,record.getIsYZ());
            preparedStatement.setInt(4,record.getMemberNum());
            preparedStatement.setString(5,record.getLicense());
            preparedStatement.setString(6,record.getAboutUS());
            preparedStatement.setString(7,record.getComanyName());
            preparedStatement.setString(8,record.getCreateDate());
            preparedStatement.setString(9,record.getComanyAddress());
            preparedStatement.setString(10,record.getComanyQualify());
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
    public int update(FrameCompanyExtendinfo record) {
        int flag=0;
        Connection conn = JdbcUtils.getConn();
        //SQL语句
        String sql = "update frame_company_extendinfo set userGuid=?,isYZ=?,memberNum=?,license=?,aboutUS=?,comanyName=?,createDate=?,comanyAddress=?,comanyQualify=? where rowguid=?";
        try {
            PreparedStatement preparedStatement=conn.prepareStatement(sql);
            preparedStatement.setString(1,record.getUserGuid());
            preparedStatement.setString(2,record.getIsYZ());
            preparedStatement.setInt(3,record.getMemberNum());
            preparedStatement.setString(4,record.getLicense());
            preparedStatement.setString(5,record.getAboutUS());
            preparedStatement.setString(6,record.getComanyName());
            preparedStatement.setString(7,record.getCreateDate());
            preparedStatement.setString(8,record.getComanyAddress());
            preparedStatement.setString(9,record.getComanyQualify());
            preparedStatement.setString(10,record.getRowGuid());
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
    public int delete(FrameCompanyExtendinfo record) {
        int flag=0;
        Connection conn = JdbcUtils.getConn();
        //SQL语句
        String sql = "delete from  frame_company_extendinfo  where rowguid=?";
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
    public FrameCompanyExtendinfo find(Map<String, Object> map) {
        FrameCompanyExtendinfo record=null;
        Connection conn = JdbcUtils.getConn();
        //SQL语句
        String sql = "SELECT * FROM frame_company_extendinfo WHERE 1=1  ";
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
                record=new FrameCompanyExtendinfo();
                record.setRow_id(resultSet.getInt("row_id"));
                record.setRowGuid(resultSet.getString("rowGuid"));
                record.setUserGuid(resultSet.getString("userGuid"));
                record.setLicense(resultSet.getString("License"));
                record.setIsYZ(resultSet.getString("IsYZ"));
                record.setMemberNum(resultSet.getInt("MemberNum"));

                record.setAboutUS(resultSet.getString("aboutUS"));
                record.setComanyName(resultSet.getString("comanyName"));
                record.setCreateDate(resultSet.getString("createDate"));
                record.setComanyAddress(resultSet.getString("comanyAddress"));
                record.setComanyQualify(resultSet.getString("comanyQualify"));
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
