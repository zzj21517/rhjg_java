package com.lxh.newrhjg.impl;


import com.alibaba.fastjson.JSONObject;
import com.lxh.newrhjg.api.IframeProject;
import com.lxh.newrhjg.entity.FrameMenu;
import com.lxh.newrhjg.entity.FrameApply;
import com.lxh.newrhjg.entity.FramePeople;
import com.lxh.newrhjg.entity.FrameMember;
import com.lxh.test.common.JdbcUtils;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.json.JsonObject;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Component
@Service
public class FrameProjectImpl implements IframeProject {
    @Override
    public int insertApply(FrameApply record) {
        int flag=0;
        Connection conn = JdbcUtils.getConn();
        //SQL语句
        String sql = "INSERT INTO Frame_Apply(userName,phone,pid,cid,createTime)" +
                " VALUES(?,?,?,?,?) ";
        try {
            PreparedStatement preparedStatement=conn.prepareStatement(sql);
            preparedStatement.setString(1,record.getUserName());
            preparedStatement.setString(2,record.getPhone());
            preparedStatement.setInt(3,record.getPid());
            preparedStatement.setInt(4,record.getCid());
            preparedStatement.setString(5,record.getCreateTime());
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
    };
    @Override
    public List<FrameMenu> findMenuList(){
        List<FrameMenu> list= new ArrayList<FrameMenu>();
        Connection conn = JdbcUtils.getConn();
        //SQL语句
        String sql = "select * From  Frame_Menu";
        try {
            PreparedStatement preparedStatement=conn.prepareStatement(sql);
            //执行语句，得到结果集
            ResultSet resultSet=preparedStatement.executeQuery();
            while (resultSet.next()) {
                FrameMenu record=new FrameMenu();
                record.setId(resultSet.getInt("id"));
                record.setLabel(resultSet.getString("label"));
                record.setParentId(resultSet.getInt("parentId"));
                record.setMenuId(resultSet.getInt("menuId"));
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
    };
    @Override
    public List<FrameMember> findMemberList(){
        List<FrameMember> list= new ArrayList<FrameMember>();
        Connection conn = JdbcUtils.getConn();
        //SQL语句
        String sql = "select * From  Frame_Member";
        try {
            PreparedStatement preparedStatement=conn.prepareStatement(sql);
            //执行语句，得到结果集
            ResultSet resultSet=preparedStatement.executeQuery();
            while (resultSet.next()) {
                FrameMember record=new FrameMember();
                record.setId(resultSet.getInt("id"));
                record.setName(resultSet.getString("name"));
                record.setLevel(resultSet.getInt("level"));
                record.setPrice(resultSet.getInt("price"));
                record.setCut(resultSet.getInt("cut"));
                record.setQuantity(resultSet.getInt("quantity"));
                record.setPrivilege(resultSet.getString("privilege"));
                record.setCreateTime(resultSet.getString("createTime"));
                record.setTender(resultSet.getInt("tender"));
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

