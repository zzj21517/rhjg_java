package com.lxh.rhjg.photo.Impl;

import com.lxh.rhjg.circle.api.ICircle;
import com.lxh.rhjg.circle.api.SMART_PHOTO;
import com.lxh.rhjg.photo.api.IPhoto;
import com.lxh.test.common.JdbcUtils;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Component
@Service
public class PhotoImpl implements IPhoto {
    @Override
    public void deletePhoto(String shortcode){
        Connection conn = JdbcUtils.getConn();
        //SQL语句
        String sql = "DELETE FROM SMART_PHOTO WHERE SHORT_CODE=? AND ITEM_TYPE='00'";
        try {
            PreparedStatement preparedStatement=conn.prepareStatement(sql);
            preparedStatement.setString(1,shortcode);
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
