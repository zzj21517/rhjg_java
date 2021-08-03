package com.lxh.rhjg.mall.impl;
import com.lxh.newrhjg.entity.FrameFollow;
import com.lxh.rhjg.entity.*;
import com.lxh.rhjg.mall.api.IMall;
import com.lxh.test.common.JdbcUtils;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import java.sql.*;
import java.util.Map;

@Component
@Service
public class MallImpl implements IMall{
    @Override
    public int insert(SMART_REQUIREMENT record) {
        int flag=0;
        Connection conn = JdbcUtils.getConn();
        //SQL语句
        String sql = "INSERT INTO SMART_REQUIREMENT(Guid,User_Id,Rquirement,DateTime)" +
                " VALUES(?,?,?,?) ";
        try {
            PreparedStatement preparedStatement=conn.prepareStatement(sql);
            preparedStatement.setString(1,record.getGuid());
            preparedStatement.setString(2,record.getUser_id());
            preparedStatement.setString(3,record.getRequirement());
            preparedStatement.setString(4,record.getDatetime());
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
    public int update(SMART_REQUIREMENT record){
        int flag=0;
        Connection conn = JdbcUtils.getConn();
        //SQL语句
        String sql = "update  SMART_REQUIREMENT set User_Id=?,Rquirement=? where Guid=?";
        try {
            PreparedStatement preparedStatement=conn.prepareStatement(sql);
            preparedStatement.setString(1,record.getUser_id());
            preparedStatement.setString(2,record.getRequirement());
            preparedStatement.setString(3,record.getGuid());
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
    public int insertmallbase(SMART_MALL_BASE record){
        int flag=0;
        Connection conn = JdbcUtils.getConn();
        //SQL语句
        String sql = "INSERT INTO SMART_MALL_BASE(Guid,User_Id,Mall_Type,Mall_Addr,Mall_Img,Mall_Desc,Mall_Mark,DateTime)" +
                " VALUES(?,?,?,?,?,?,?,?) ";
        try {
            PreparedStatement preparedStatement=conn.prepareStatement(sql);
            preparedStatement.setString(1,record.getGuid());
            preparedStatement.setString(2,record.getUser_Id());
            preparedStatement.setString(3,record.getMall_Type());
            preparedStatement.setString(4,record.getMall_Addr());
            preparedStatement.setString(5,record.getMall_Img());
            preparedStatement.setString(6,record.getMall_Desc());
            preparedStatement.setString(7,record.getMall_Mark());
            preparedStatement.setString(8,record.getDateTime());
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
    public int updatemallbase(SMART_MALL_BASE record){
        int flag=0;
        Connection conn = JdbcUtils.getConn();
        //SQL语句
        String sql = "update SMART_MALL_BASE set User_Id=?,Mall_Type=?,Mall_Addr=?,Mall_Img=?,Mall_Desc=?,Mall_Mark=?  where Guid=?";
        try {
            PreparedStatement preparedStatement=conn.prepareStatement(sql);
            preparedStatement.setString(1,record.getUser_Id());
            preparedStatement.setString(2,record.getMall_Type());
            preparedStatement.setString(3,record.getMall_Addr());
            preparedStatement.setString(4,record.getMall_Img());
            preparedStatement.setString(5,record.getMall_Desc());
            preparedStatement.setString(6,record.getMall_Mark());
            preparedStatement.setString(7,record.getGuid());
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
    public int insertmallcase(SMART_MALL_CASE record){
        int flag=0;
        Connection conn = JdbcUtils.getConn();
        //SQL语句
        String sql = "INSERT INTO SMART_MALL_CASE(Guid,User_Id,Case_Name,Case_Price,Case_Detail,Case_Scan,DateTime,clientGuid)" +
                " VALUES(?,?,?,?,?,?,?,?) ";
        try {
            PreparedStatement preparedStatement=conn.prepareStatement(sql);
            preparedStatement.setString(1,record.getGuid());
            preparedStatement.setString(2,record.getUser_Id());
            preparedStatement.setString(3,record.getCase_Name());
            preparedStatement.setString(4,record.getCase_Price());
            preparedStatement.setString(5,record.getCase_Detail());
            preparedStatement.setInt(6,record.getCase_Scan());
            preparedStatement.setString(7,record.getDateTime());
            preparedStatement.setString(8,record.getClientGuid());
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
    public int updatemallcase(SMART_MALL_CASE record){
        int flag=0;
        Connection conn = JdbcUtils.getConn();
        //SQL语句
        String sql = "update SMART_MALL_CASE set User_Id=?,Case_Name=?,Case_Price=?,Case_Detail=?,Case_Scan=?  where Guid=?";
        try {
            PreparedStatement preparedStatement=conn.prepareStatement(sql);
            preparedStatement.setString(1,record.getUser_Id());
            preparedStatement.setString(2,record.getCase_Name());
            preparedStatement.setString(3,record.getCase_Price());
            preparedStatement.setString(4,record.getCase_Detail());
            preparedStatement.setInt(5,record.getCase_Scan());
            preparedStatement.setString(6,record.getGuid());
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
    public int insertmallphoto(SMART_MALL_PHOTO record){
        int flag=0;
        Connection conn = JdbcUtils.getConn();
        //SQL语句
        String sql = "INSERT INTO SMART_MALL_PHOTO(Guid,Forn_Guid,User_Id,Img_Path,Item_Type,DateTime,`Order`)" +
                " VALUES(?,?,?,?,?,?,?)";
        try {
            PreparedStatement preparedStatement=conn.prepareStatement(sql);
            preparedStatement.setString(1,record.getGuid());
            preparedStatement.setString(2,record.getForn_Guid());
            preparedStatement.setString(3,record.getUser_Id());
            preparedStatement.setString(4,record.getImg_Path());
            preparedStatement.setString(5,record.getItem_Type());
            preparedStatement.setString(6,record.getDateTime());
            preparedStatement.setInt(7,record.getOrder());
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
    public int updatemallphoto(SMART_MALL_PHOTO record){
        int flag=0;
        Connection conn = JdbcUtils.getConn();
        //SQL语句
        String sql = "update SMART_MALL_PHOTO set User_Id=?,Img_Path=?,Item_Type=?,`Order`=? where Guid=?";
        try {
            PreparedStatement preparedStatement=conn.prepareStatement(sql);
            preparedStatement.setString(1,record.getUser_Id());
            preparedStatement.setString(2,record.getImg_Path());
            preparedStatement.setString(3,record.getItem_Type());
            preparedStatement.setInt(4,record.getOrder());
            preparedStatement.setString(5,record.getGuid());
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
    public int insertmallorg(SMART_MALL_ORG record){
        int flag=0;
        Connection conn = JdbcUtils.getConn();
        //SQL语句
        String sql = "INSERT INTO SMART_MALL_ORG(Guid,User_Id,AboutUS,ComanyName,CreateDate,ComanySize,ComanyAddress,ComanyQualify)" +
                " VALUES(?,?,?,?,?,?,?,?)";
        try {
            PreparedStatement preparedStatement=conn.prepareStatement(sql);
            preparedStatement.setString(1,record.getGuid());
            preparedStatement.setString(2,record.getUser_ID());
            preparedStatement.setString(3,record.getAboutUS());
            preparedStatement.setString(4,record.getComanyName());
            preparedStatement.setString(5,record.getCreateDate());
            preparedStatement.setString(6,record.getComanySize());
            preparedStatement.setString(7,record.getComanyAddress());
            preparedStatement.setString(8,record.getComanyQualify());
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
    public int updatemallorg(SMART_MALL_ORG record){
        int flag=0;
        Connection conn = JdbcUtils.getConn();
        //SQL语句
        String sql = "update SMART_MALL_ORG set User_Id=?,AboutUS=?,ComanyName=?,CreateDate=? ,ComanySize=?,ComanyAddress=?,ComanyQualify=? where Guid=?";
        try {
            PreparedStatement preparedStatement=conn.prepareStatement(sql);
            preparedStatement.setString(1,record.getUser_ID());
            preparedStatement.setString(2,record.getAboutUS());
            preparedStatement.setString(3,record.getComanyName());
            preparedStatement.setString(4,record.getCreateDate());
            preparedStatement.setString(5,record.getComanySize());
            preparedStatement.setString(6,record.getComanyAddress());
            preparedStatement.setString(7,record.getComanyQualify());
            preparedStatement.setString(8,record.getGuid());
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
    public int deletemallcase(String rowGuid){
        int flag=0;
        Connection conn = JdbcUtils.getConn();
        //SQL语句
        String sql = "delete from  smart_mall_case  where Guid=?";
        try {
            PreparedStatement preparedStatement=conn.prepareStatement(sql);
            preparedStatement.setString(1,rowGuid);
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
    public SMART_MALL_CASE findCase(Map<String, Object> map) {
        SMART_MALL_CASE record=null;
        Connection conn = JdbcUtils.getConn();
        //SQL语句
        String sql = "SELECT * FROM SMART_MALL_CASE WHERE 1=1  ";
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
                record=new SMART_MALL_CASE();
                record.setUser_Id(resultSet.getString("User_ID"));
                record.setCase_Name(resultSet.getString("Case_Name"));
                record.setCase_Price(resultSet.getString("Case_Price"));
                record.setCase_Detail(resultSet.getString("Case_Detail"));
                record.setCase_Scan(resultSet.getInt("Case_Scan"));
                record.setGuid(resultSet.getString("Guid"));
                record.setClientGuid(resultSet.getString("ClientGuid"));
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
