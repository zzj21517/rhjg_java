package com.lxh.rhjg.circle.impl;

import com.lxh.rhjg.active.api.SMART_LUCKY;
import com.lxh.rhjg.circle.api.ICircle;
import com.lxh.rhjg.circle.api.SMART_PHOTO;
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
public class CircleImpl  implements ICircle{
    @Override
    public List<SMART_PHOTO> getSMARTPHOTO(int start, int length){
        SMART_PHOTO smartPhoto=null;
        List<SMART_PHOTO> list=new ArrayList<SMART_PHOTO>();
        Connection conn = JdbcUtils.getConn();
        //SQL语句
        String sql = "SELECT * FROM SMART_PHOTO WHERE ITEM_TYPE='06' ORDER BY DATATIME DESC, IMG_ORDER ASC LIMIT ?,?";
        try {
            PreparedStatement preparedStatement=conn.prepareStatement(sql);
            preparedStatement.setInt(1,start);
            preparedStatement.setInt(2,length);
            //执行语句，得到结果集
            ResultSet resultSet=preparedStatement.executeQuery();
            while (resultSet.next()) {
                smartPhoto=new SMART_PHOTO();
                smartPhoto.setGUID(resultSet.getString("Guid"));
                smartPhoto.setDATATIME(resultSet.getString("Datatime"));
                smartPhoto.setIMG_DESC(resultSet.getString("Img_Desc"));
                smartPhoto.setIMG_ORDER(resultSet.getString("Img_Order"));
                smartPhoto.setIMG_PATH(resultSet.getString("Img_Path"));
                smartPhoto.setIS_DELETE(resultSet.getString("Is_Delete"));
                smartPhoto.setITEM_TYPE(resultSet.getString("Item_Type"));
                smartPhoto.setSHORT_CODE(resultSet.getString("Short_Code"));
                list.add(smartPhoto);
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
    @Override
    public List<SMART_PHOTO> GetCircleDetail(String condition){
        SMART_PHOTO smartPhoto=null;
        List<SMART_PHOTO> list=new ArrayList<SMART_PHOTO>();
        Connection conn = JdbcUtils.getConn();
        //SQL语句
        String sql = "SELECT * FROM SMART_PHOTO WHERE 1=1 "+condition;
        try {
            PreparedStatement preparedStatement=conn.prepareStatement(sql);
            //执行语句，得到结果集
            ResultSet resultSet=preparedStatement.executeQuery();
            while (resultSet.next()) {
                smartPhoto=new SMART_PHOTO();
                smartPhoto.setGUID(resultSet.getString("Guid"));
                smartPhoto.setDATATIME(resultSet.getString("Datatime"));
                smartPhoto.setIMG_DESC(resultSet.getString("Img_Desc"));
                smartPhoto.setIMG_ORDER(resultSet.getString("Img_Order"));
                smartPhoto.setIMG_PATH(resultSet.getString("Img_Path"));
                smartPhoto.setIS_DELETE(resultSet.getString("Is_Delete"));
                smartPhoto.setITEM_TYPE(resultSet.getString("Item_Type"));
                smartPhoto.setSHORT_CODE(resultSet.getString("Short_Code"));
                list.add(smartPhoto);
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
