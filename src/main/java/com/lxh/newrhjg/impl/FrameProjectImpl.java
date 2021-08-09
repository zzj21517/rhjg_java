package com.lxh.newrhjg.impl;


import com.alibaba.fastjson.JSONObject;
import com.lxh.newrhjg.api.IframeProject;
import com.lxh.newrhjg.entity.FrameMenu;
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
    }
}

