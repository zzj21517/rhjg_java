package com.lxh.newrhjg.impl;

import com.lxh.newrhjg.api.IframePeople;
import com.lxh.newrhjg.entity.FramePeople;
import com.lxh.newrhjg.entity.FramePeopleEnjoy;
import com.lxh.newrhjg.entity.FramePeopleExtendinfo;
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
public class FramePeopleImpl implements IframePeople {
    @Override
    public int insert(FramePeople record) {
        int flag=0;
        Connection conn = JdbcUtils.getConn();
        //SQL语句
        String sql = "INSERT INTO Frame_People(rowGuid,phone,usertype,dealNum,dealMoney,iconurl,familiar,areaPro,subTool,isDS,isPro,superDes,registerTime,password,FamiliarChina,openId,userFlag)" +
                " VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?) ";
        try {
            PreparedStatement preparedStatement=conn.prepareStatement(sql);
            preparedStatement.setString(1,record.getRowGuid());
            preparedStatement.setString(2,record.getPhone());
            preparedStatement.setString(3,record.getUsertype());
            preparedStatement.setInt(4,record.getDealNum());
            preparedStatement.setDouble(5,record.getDealMoney());
            preparedStatement.setString(6,record.getIconurl());
            preparedStatement.setString(7,record.getFamiliar());
            preparedStatement.setString(8,record.getAreaPro());
            preparedStatement.setString(9,record.getSubTool());
            preparedStatement.setString(10,record.getIsDS());
            preparedStatement.setInt(11,record.getIsPro());
            preparedStatement.setString(12,record.getSuperDes());
            preparedStatement.setString(13,record.getRegisterTime());
            preparedStatement.setString(14,record.getPassword());
            preparedStatement.setString(15,record.getFamiliarChina());
            preparedStatement.setString(16,record.getOpenId());
            preparedStatement.setInt(17,record.getUserFlag());
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
    public int update(FramePeople record) {
        int flag=0;
        Connection conn = JdbcUtils.getConn();
        //SQL语句
        String sql = "update Frame_People set phone=?,usertype=?,dealNum=?,dealMoney=?,iconurl=?,familiar=?,areaPro=?,subTool=?,isDS=?,isPro=?,superDes=?,password=?,familiarChina=?,openId=?,nickName=?,avatarUrl=?,gender=?,country=?,province=?,city=?,userFlag=? where rowguid=?";
        try {
            PreparedStatement preparedStatement=conn.prepareStatement(sql);
            preparedStatement.setString(1,record.getPhone());
            preparedStatement.setString(2,record.getUsertype());
            preparedStatement.setInt(3,record.getDealNum());
            preparedStatement.setDouble(4,record.getDealMoney());
            preparedStatement.setString(5,record.getIconurl());
            preparedStatement.setString(6,record.getFamiliar());
            preparedStatement.setString(7,record.getAreaPro());
            preparedStatement.setString(8,record.getSubTool());
            preparedStatement.setString(9,record.getIsDS());
            preparedStatement.setInt(10,record.getIsPro());
            preparedStatement.setString(11,record.getSuperDes());
            preparedStatement.setString(12,record.getPassword());
            preparedStatement.setString(13,record.getFamiliarChina());
            preparedStatement.setString(14,record.getOpenId());
            preparedStatement.setString(15,record.getNickName());
            preparedStatement.setString(16,record.getAvatarUrl());
            preparedStatement.setString(17,record.getGender());
            preparedStatement.setString(18,record.getCountry());
            preparedStatement.setString(19,record.getProvince());
            preparedStatement.setString(20,record.getCity());
            preparedStatement.setInt(21,record.getUserFlag());
            preparedStatement.setString(22,record.getRowGuid());
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
    public int delete(FramePeople record) {
        int flag=0;
        Connection conn = JdbcUtils.getConn();
        //SQL语句
        String sql = "delete from  Frame_People  where rowguid=?";
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
    public FramePeople findPeople(String fieldname, String fieldvalue){
        FramePeople record=null;
        Connection conn = JdbcUtils.getConn();
        //SQL语句
        String sql = "select * From  Frame_People where "+fieldname+"=?";
        try {
            PreparedStatement preparedStatement=conn.prepareStatement(sql);
            preparedStatement.setString(1,fieldvalue);
            //执行语句，得到结果集
            ResultSet resultSet=preparedStatement.executeQuery();
            while (resultSet.next()) {
                record=new FramePeople();
                record.setRow_id(resultSet.getInt("row_id"));
                record.setRowGuid(resultSet.getString("rowguid"));
                record.setPhone(resultSet.getString("phone"));
              //  record.setPassword(resultSet.getString("passWord"));
                record.setUsertype(resultSet.getString("usertype"));
        /*        record.setDealNum(resultSet.getInt("dealNum"));
                record.setDealMoney(resultSet.getDouble("dealMoney"));*/
                record.setIconurl(resultSet.getString("iconurl"));
             /*   record.setFamiliar(resultSet.getString("familiar"));*/
                record.setAreaPro(resultSet.getString("areaPro"));
       /*         record.setSubTool(resultSet.getString("subTool"));*/
                record.setIsDS(resultSet.getString("isDS"));
                record.setIsPro(resultSet.getInt("isPro"));
                record.setSuperDes(resultSet.getString("superDes"));
                record.setRegisterTime(resultSet.getString("registerTime"));
              /*  record.setFamiliarChina(resultSet.getString("familiarChina"));*/
                record.setOpenId(resultSet.getString("openId"));
                record.setNickName(resultSet.getString("nickName"));
                record.setGender(resultSet.getString("gender"));
                record.setAvatarUrl(resultSet.getString("avatarUrl"));
                record.setCountry(resultSet.getString("country"));
                record.setProvince(resultSet.getString("province"));
                record.setCity(resultSet.getString("city"));
             /*   record.setScore(resultSet.getString("Score"));*/
    /*            record.setFinishPer(resultSet.getString("finishPer"));*/
                record.setCustom(resultSet.getString("custom"));
                record.setOpenId(resultSet.getString("openid"));
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
    public int insertEnjoy(FramePeopleEnjoy record){
        int flag=0;
        Connection conn = JdbcUtils.getConn();
        //SQL语句
        String sql = "INSERT INTO frame_people_enjoy(rowGuid,userGuid,enjoyType,enjoyTypeChina)" +
                " VALUES(?,?,?,?) ";
        try {
            PreparedStatement preparedStatement=conn.prepareStatement(sql);
            preparedStatement.setString(1,record.getRowGuid());
            preparedStatement.setString(2,record.getUserGuid());
            preparedStatement.setString(3,record.getEnjoyType());
            preparedStatement.setString(4,record.getEnjoyTypeChina());
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
    public int deleteEnjoy(String userGuid){
        int flag=0;
        Connection conn = JdbcUtils.getConn();
        //SQL语句
        String sql = "delete from  frame_people_enjoy  where userguid=?";
        try {
            PreparedStatement preparedStatement=conn.prepareStatement(sql);
            preparedStatement.setString(1,userGuid);
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
    public int isLogin(String phone,String pwd){
        int flag=0;
        Connection conn = JdbcUtils.getConn();
        //SQL语句
        String sql = "select count(1) as valunum from  frame_people  where phone=? and password=?";
        try {
            PreparedStatement preparedStatement=conn.prepareStatement(sql);
            preparedStatement.setString(1,phone);
            preparedStatement.setString(1,pwd);
            //执行语句，得到结果集
            //执行语句，得到结果集
            ResultSet resultSet=preparedStatement.executeQuery();
            while (resultSet.next()) {
                flag=resultSet.getInt("valunum");
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
        return flag;
    }
    public FramePeople find(Map<String, Object> map){
        FramePeople record=null;
        Connection conn = JdbcUtils.getConn();
        //SQL语句
        String sql = "SELECT * FROM Frame_People WHERE 1=1  ";
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
                record=new FramePeople();
                //这里只查询的密码
                record.setRow_id(resultSet.getInt("row_id"));
                record.setRowGuid(resultSet.getString("rowguid"));
                record.setPhone(resultSet.getString("phone"));
               // record.setPassword(resultSet.getString("passWord"));
                record.setUsertype(resultSet.getString("usertype"));
            /*    record.setDealNum(resultSet.getInt("dealNum"));
                record.setDealMoney(resultSet.getDouble("dealMoney"));*/
                record.setIconurl(resultSet.getString("iconurl"));
  /*              record.setFamiliar(resultSet.getString("familiar"));
                record.setFamiliarChina(resultSet.getString("familiarChina"));*/
                record.setAreaPro(resultSet.getString("areaPro"));
          /*      record.setSubTool(resultSet.getString("subTool"));*/
                record.setIsDS(resultSet.getString("isDS"));
                record.setIsPro(resultSet.getInt("isPro"));
                record.setSuperDes(resultSet.getString("superDes"));
                record.setRegisterTime(resultSet.getString("registerTime"));
                record.setOpenId(resultSet.getString("openId"));
                record.setNickName(resultSet.getString("nickName"));
       /*         record.setScore(resultSet.getString("Score"));
                record.setFinishPer(resultSet.getString("finishPer"));*/
                record.setCustom(resultSet.getString("custom"));
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
