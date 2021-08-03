package com.lxh.test.impl;

import com.lxh.test.api.IfindSomething;
import com.lxh.test.common.JdbcUtils;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

@Component
@Service
public class FindSomethingImpl  implements IfindSomething {
    @Override
    public String getSomething(){
        //SQL语句
        String sql = "select * from frame_user";
        Connection conn = JdbcUtils.getConn();
        Statement stmt=null;
        ResultSet ret = null;
        String password=null;
        try {
            stmt = conn.createStatement();
            //执行语句，得到结果集
            ret = stmt.executeQuery(sql);
            while (ret.next()) {
                //这里只查询的密码
                password = ret.getString(1);
            }
            ret.close();
            conn.close();//关闭连接
        } catch (SQLException e1) {
            e1.printStackTrace();
        }
        return password;

    }
}
