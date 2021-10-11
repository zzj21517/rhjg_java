package com.lxh.newrhjg.impl;


import com.alibaba.fastjson.JSONObject;
import com.lxh.newrhjg.api.IframeProject;
import com.lxh.newrhjg.entity.*;
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
    public FrameRate findRate(String condition){
        FrameRate record=null;
        Connection conn = JdbcUtils.getConn();
        //SQL语句
        String sql = "select * From  Smart_Rate where "+condition;
        try {
            PreparedStatement preparedStatement=conn.prepareStatement(sql);
            //执行语句，得到结果集
            ResultSet resultSet=preparedStatement.executeQuery();
            while (resultSet.next()) {
                record=new FrameRate();
                record.setRowId(resultSet.getInt("rowId"));
                record.setRowGuid(resultSet.getString("rowGuid"));
                record.setProjectNum(resultSet.getString("projectNum"));
                record.setRateGuid(resultSet.getString("rateGuid"));
                record.setRatedGuid(resultSet.getString("ratedGuid"));
                record.setCreateTime(resultSet.getString("createTime"));
                record.setCompleteRate(resultSet.getInt("completeRate"));
                record.setQualityRate(resultSet.getInt("qualityRate"));
                record.setServiceAttiduteRate(resultSet.getInt("serviceAttiduteRate"));
                record.setCooperationRate(resultSet.getInt("cooperationRate"));
                record.setTimelyRate(resultSet.getInt("timelyRate"));
                record.setUploadRate(resultSet.getInt("uploadRate"));
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

    @Override
    public int updateRate(FrameRate record) {
        int flag=0;
        Connection conn = JdbcUtils.getConn();
        //SQL语句
        String sql = "update Smart_Rate set completeRate=?,qualityRate=?,serviceAttiduteRate=?,cooperationRate=?,timelyRate=?,uploadRate=? where rowGuid=?";
        try {
            PreparedStatement preparedStatement=conn.prepareStatement(sql);
            preparedStatement.setInt(1,record.getCompleteRate());
            preparedStatement.setInt(2,record.getQualityRate());
            preparedStatement.setInt(3,record.getServiceAttiduteRate());
            preparedStatement.setInt(4,record.getCooperationRate());
            preparedStatement.setInt(5,record.getTimelyRate());
            preparedStatement.setInt(6,record.getUploadRate());
            preparedStatement.setString(7,record.getRowGuid());
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
    public int insertRate(FrameRate record) {
        int flag=0;
        Connection conn = JdbcUtils.getConn();
        //SQL语句
        String sql = "INSERT INTO Smart_Rate(projectNum,rateGuid,ratedGuid,createTime,completeRate,qualityRate,serviceAttiduteRate,cooperationRate,timelyRate,uploadRate,rowGuid)" +
                " VALUES(?,?,?,?,?,?,?,?,?,?,?) ";
        try {
            PreparedStatement preparedStatement=conn.prepareStatement(sql);
            preparedStatement.setString(1,record.getProjectNum());
            preparedStatement.setString(2,record.getRateGuid());
            preparedStatement.setString(3,record.getRatedGuid());
            preparedStatement.setString(4,record.getCreateTime());
            preparedStatement.setInt(5,record.getCompleteRate());
            preparedStatement.setInt(6,record.getQualityRate());
            preparedStatement.setInt(7,record.getServiceAttiduteRate());
            preparedStatement.setInt(8,record.getCooperationRate());
            preparedStatement.setInt(9,record.getTimelyRate());
            preparedStatement.setInt(10,record.getUploadRate());
            preparedStatement.setString(11,record.getRowGuid());
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
    public int updateCoupon(FrameCoupon record) {
        int flag=0;
        Connection conn = JdbcUtils.getConn();
        //SQL语句
        String sql = "update Frame_Coupon set userGuid=?,couponAmount=?,couponName=?,createTime=?,expireTime=?,couponStatus=?,couponFrom=?,userFlag=?,projectNum=? where rowGuid=?";
        try {
            PreparedStatement preparedStatement=conn.prepareStatement(sql);
            preparedStatement.setString(1,record.getUserGuid());
            preparedStatement.setInt(2,record.getCouponAmount());
            preparedStatement.setString(3,record.getCouponName());
            preparedStatement.setString(4,record.getCreateTime());
            preparedStatement.setString(5,record.getExpireTime());
            preparedStatement.setInt(6,record.getCouponStatus());
            preparedStatement.setString(7,record.getCouponFrom());
            preparedStatement.setInt(8,record.getUserFlag());
            preparedStatement.setString(9,record.getProjectNum());
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
    public int insertCoupon(FrameCoupon record) {
        int flag=0;
        Connection conn = JdbcUtils.getConn();
        //SQL语句
        String sql = "INSERT INTO Frame_Coupon(rowGuid,userGuid,couponAmount,couponName,createTime,expireTime,couponStatus,couponFrom,userFlag,giveGuid)" +
                " VALUES(?,?,?,?,?,?,?,?,?,?) ";
        try {
            PreparedStatement preparedStatement=conn.prepareStatement(sql);
            preparedStatement.setString(1,record.getRowGuid());
            preparedStatement.setString(2,record.getUserGuid());
            preparedStatement.setInt(3,record.getCouponAmount());
            preparedStatement.setString(4,record.getCouponName());
            preparedStatement.setString(5,record.getCreateTime());
            preparedStatement.setString(6,record.getExpireTime());
            preparedStatement.setInt(7,record.getCouponStatus());
            preparedStatement.setString(8,record.getCouponFrom());
            preparedStatement.setInt(9,record.getUserFlag());
            preparedStatement.setString(10,record.getGiveGuid());
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
    public List<FrameCoupon> findCouponList(String condition){
        List<FrameCoupon> frameCoupons=new ArrayList<>();
        Connection conn = JdbcUtils.getConn();
        //SQL语句
        String sql = "select * From  Frame_Coupon where "+condition;
        try {
            PreparedStatement preparedStatement=conn.prepareStatement(sql);
            //执行语句，得到结果集
            ResultSet resultSet=preparedStatement.executeQuery();
            while (resultSet.next()) {
                FrameCoupon record=new FrameCoupon();
                record.setRowId(resultSet.getInt("rowId"));
                record.setRowGuid(resultSet.getString("rowGuid"));
                record.setUserGuid(resultSet.getString("userGuid"));
                record.setCouponAmount(resultSet.getInt("couponAmount"));
                record.setCouponName(resultSet.getString("couponName"));
                record.setExpireTime(resultSet.getString("expireTime"));
                record.setCreateTime(resultSet.getString("createTime"));
                record.setCouponStatus(resultSet.getInt("couponStatus"));
                record.setCouponFrom(resultSet.getString("couponFrom"));
                record.setUserFlag(resultSet.getInt("userFlag"));
                record.setProjectNum(resultSet.getString("projectNum"));
                record.setGiveGuid(resultSet.getString("giveGuid"));
                frameCoupons.add(record);
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
        return frameCoupons;
    }

    @Override
    public List<FrameCouponGive> findCouponGiveList(String condition){
        List<FrameCouponGive> frameCouponGives=new ArrayList<>();
        Connection conn = JdbcUtils.getConn();
        //SQL语句
        String sql = "select * From  Frame_Coupon_Give where "+condition;
        System.out.println(sql);
        try {
            PreparedStatement preparedStatement=conn.prepareStatement(sql);
            //执行语句，得到结果集
            ResultSet resultSet=preparedStatement.executeQuery();
            while (resultSet.next()) {
                FrameCouponGive record=new FrameCouponGive();
                record.setRowId(resultSet.getInt("rowId"));
                record.setRowGuid(resultSet.getString("rowGuid"));
                record.setCouponAmount(resultSet.getInt("couponAmount"));
                record.setCouponName(resultSet.getString("couponName"));
                record.setExpireTime(resultSet.getString("expireTime"));
                record.setCreateTime(resultSet.getString("createTime"));
                record.setUserFlag(resultSet.getInt("userFlag"));
                record.setOnOff(resultSet.getInt("onOff"));
                frameCouponGives.add(record);
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
        return frameCouponGives;
    }

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
                record.setPrice(resultSet.getDouble("price"));
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

