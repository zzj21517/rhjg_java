package com.lxh.newrhjg.impl;

import com.alibaba.fastjson.JSONObject;
import com.lxh.newrhjg.api.IMssages;
import com.lxh.newrhjg.entity.FrameMessages;
import com.lxh.newrhjg.entity.FrameMessagesHistroy;
import com.lxh.rhjg.active.api.SMART_PEOPLE;
import com.lxh.rhjg.common.util.HttpClient;
import com.lxh.rhjg.common.util.MD5Utils;
import com.lxh.rhjg.entity.SMART_VERIFY;
import com.lxh.test.common.JdbcUtils;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.io.IOException;
import java.net.URLEncoder;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Component
@Service
public class MessagesImpl  implements IMssages {
    @Override
    public int insert(FrameMessages record) {
        int flag=0;
        Connection conn = JdbcUtils.getConn();
        //SQL语句
        String sql = "INSERT INTO frame_messages(rowGuid,titile,content,targetUser,type,sendTime,sendUser,targetuserphone)" +
                " VALUES(?,?,?,?,?,?,?,?) ";
        try {
            PreparedStatement preparedStatement=conn.prepareStatement(sql);
            preparedStatement.setString(1,record.getRowGuid());
            preparedStatement.setString(2,record.getTitile());
            preparedStatement.setString(3,record.getContent());
            preparedStatement.setString(4,record.getTargetUser());
            preparedStatement.setString(5,record.getType());
            preparedStatement.setString(6,record.getSendTime());
            preparedStatement.setString(7,record.getSendUser());
            preparedStatement.setString(8,record.getTargetuserphone());
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
    public int update(FrameMessages record) {
        int flag=0;
        Connection conn = JdbcUtils.getConn();
        //SQL语句
        String sql = "update frame_messages set titile=?,content=?,targetUser=?,type=?,sendTime=?,sendUser=?,targetuserphone=? where rowguid=?";
        try {
            PreparedStatement preparedStatement=conn.prepareStatement(sql);
            preparedStatement.setString(1,record.getTitile());
            preparedStatement.setString(2,record.getContent());
            preparedStatement.setString(3,record.getTargetUser());
            preparedStatement.setString(4,record.getType());
            preparedStatement.setString(5,record.getSendTime());
            preparedStatement.setString(6,record.getSendUser());
            preparedStatement.setString(7,record.getTargetuserphone());
            preparedStatement.setString(8,record.getRowGuid());
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
    public int delete(FrameMessages record) {
        int flag=0;
        Connection conn = JdbcUtils.getConn();
        //SQL语句
        String sql = "delete from  frame_messages  where row_id=?";
        try {
            PreparedStatement preparedStatement=conn.prepareStatement(sql);
            preparedStatement.setInt(1,record.getRow_id());
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
    public int removehistroy(FrameMessagesHistroy record) {
        int flag=0;
        Connection conn = JdbcUtils.getConn();
        //SQL语句
        String sql = "INSERT INTO frame_messages_histroy(row_id,rowGuid,titile,content,targetUser,type,sendTime,sendUser,targetuserphone,removeTime)" +
                " VALUES(?,?,?,?,?,?,?,?,?,?) ";
        try {
            PreparedStatement preparedStatement=conn.prepareStatement(sql);
            preparedStatement.setInt(1,record.getRow_id());
            preparedStatement.setString(2,record.getRowGuid());
            preparedStatement.setString(3,record.getTitile());
            preparedStatement.setString(4,record.getContent());
            preparedStatement.setString(5,record.getTargetUser());
            preparedStatement.setString(6,record.getType());
            preparedStatement.setString(7,record.getSendTime());
            preparedStatement.setString(8,record.getSendUser());
            preparedStatement.setString(9,record.getTargetuserphone());
            preparedStatement.setString(10,record.getRemoveTime());
            //执行语句，得到结果集
            preparedStatement.execute();
            FrameMessages messages=new FrameMessages();
            messages.setRow_id(record.getRow_id());
            delete(messages);
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
