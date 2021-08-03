package com.lxh.rhjg.active.impl;

import com.alibaba.fastjson.JSONObject;
import com.lxh.rhjg.active.api.*;
import com.lxh.rhjg.entity.*;
import com.lxh.test.common.JdbcUtils;
import com.mysql.jdbc.JDBC42ResultSet;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
@Service
public class PeopleImpl implements IPeople {
    private ResultSet resultSet;

    @Override
    public SMART_PEOPLE findPeople(String fieldname, String fieldvalue) {
        SMART_PEOPLE smartPeople=null;
        Connection conn = JdbcUtils.getConn("oldurl","oldusername","oldpassword");
        //SQL语句
        String sql = "select * From  SMART_PEOPLE where "+fieldname+"=?";
        try {
            PreparedStatement preparedStatement=conn.prepareStatement(sql);
            preparedStatement.setString(1,fieldvalue);
            //执行语句，得到结果集
            ResultSet resultSet=preparedStatement.executeQuery();
            while (resultSet.next()) {
                smartPeople=new SMART_PEOPLE();
                //这里只查询的密码
                smartPeople.setNICK_NAME(resultSet.getString("NICK_NAME"));
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
        return smartPeople;
    }
    @Override
    public void insertProjectUser(SMART_PROJECT_USER projectUser){
        Connection conn = JdbcUtils.getConn();
        //SQL语句
        String sql = "INSERT INTO SMART_PROJECT_USER(GUID,PROJECT_NUM, USER_ID,LINK_MAN,LINK_TEL,PROJECT_AMT, PROJECT_DESC,DATATIME,NICK_NAME)"+
        "VALUES(?,?,?,?,?,?,?,?,?) ";
        try {
            PreparedStatement preparedStatement=conn.prepareStatement(sql);
            preparedStatement.setString(1,projectUser.getGUID());
            preparedStatement.setString(2,projectUser.getPROJECT_NUM());
            preparedStatement.setString(3,projectUser.getUSER_ID());
            preparedStatement.setString(4,projectUser.getLINK_MAN());
            preparedStatement.setString(5,projectUser.getLINK_TEL());
            preparedStatement.setString(6,projectUser.getPROJECT_AMT());
            preparedStatement.setString(7,projectUser.getPROJECT_DESC());
            preparedStatement.setString(8,projectUser.getDATATIME());
            preparedStatement.setString(9,projectUser.getNICK_NAME());
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
    public void insertUser(SMART_PEOPLE smartPeople){
        Connection conn = JdbcUtils.getConn("oldurl","oldusername","oldpassword");
        //SQL语句
        String sql = "INSERT INTO SMART_PEOPLE(GUID,USER_ID,NICK_NAME,SEX,PASS_WORD,DATATIME,MREGIE_ID," +
                "            CREGIE_ID,QREGIE_ID,PROFESSION,MONEY,LEVEL,USER_TYPE,CONTENT,MEMBER_TIME,REGISTER_TIME,IMG_PATH)"+
                "VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?) ";
        try {
            PreparedStatement preparedStatement=conn.prepareStatement(sql);
            preparedStatement.setString(1,smartPeople.getGUID());
            preparedStatement.setString(2,smartPeople.getUSER_ID());
            preparedStatement.setString(3,smartPeople.getNICK_NAME());
            preparedStatement.setString(4,smartPeople.getSEX());
            preparedStatement.setString(5,smartPeople.getPASS_WORD());
            preparedStatement.setString(6,smartPeople.getDATATIME());
            preparedStatement.setString(7,smartPeople.getMREGIE_ID());
            preparedStatement.setString(8,smartPeople.getCREGIE_ID());
            preparedStatement.setString(9,smartPeople.getQREGIE_ID());
            preparedStatement.setString(10,smartPeople.getPROFESSION());
            preparedStatement.setString(11,smartPeople.getMONEY());
            preparedStatement.setString(12,smartPeople.getLEVEL());
            preparedStatement.setString(13,smartPeople.getUSER_TYPE());
            preparedStatement.setString(14,smartPeople.getCONTENT());
            preparedStatement.setString(15,smartPeople.getMEMBER_TIME());
            preparedStatement.setString(16,smartPeople.getREGISTER_TIME());
            preparedStatement.setString(17,smartPeople.getIMG_PATH());
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
    @Override
    public void insertPeopleExt(SMART_PEOPLE_EXT smartPeopleExt){
        Connection conn = JdbcUtils.getConn();
        //SQL语句
        String sql = "INSERT INTO SMART_PEOPLE_EXT(USER_ID,GAME_POINT,SHORT_CODE,DATATIME,INVITE_MAN,TEAM_TYPE)" +
                " values(?,?,?,?,?,?)";
        try {
            PreparedStatement preparedStatement=conn.prepareStatement(sql);
            preparedStatement.setString(1,smartPeopleExt.getUSER_ID());
            preparedStatement.setString(2,smartPeopleExt.getGAME_POINT());
            preparedStatement.setString(3,smartPeopleExt.getSHORT_CODE());
            preparedStatement.setString(4,smartPeopleExt.getDATATIME());
            preparedStatement.setString(5,smartPeopleExt.getINVITE_MAN());
            preparedStatement.setString(6,smartPeopleExt.getTEAM_TYPE());
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
    @Override
    public void insertPointDetail(SMART_POINT_DETAIL smartPointDetail){
        Connection conn = JdbcUtils.getConn();
        //SQL语句
        String sql = "INSERT INTO SMART_POINT_DETAIL(GUID,USER_ID,JIFEN_POINT,JIFEN_TYPE,DATATIME)" +
                " values(?,?,?,?,?)";
        try {
            PreparedStatement preparedStatement=conn.prepareStatement(sql);
            preparedStatement.setString(1,smartPointDetail.getGUID());
            preparedStatement.setString(2,smartPointDetail.getUSER_ID());
            preparedStatement.setString(3,smartPointDetail.getJIFEN_POINT());
            preparedStatement.setString(4,smartPointDetail.getJIFEN_TYPE());
            preparedStatement.setString(5,smartPointDetail.getDATATIME());
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
    @Override
    public void insertLink(SMART_LINK smartLink){
        Connection conn = JdbcUtils.getConn();
        //SQL语句
        String sql = "INSERT INTO smart_link(GUID,LINK_MAN,LINK_TEL,LINK_WX,LINK_TYPE,DATATIME,LINK_CONTENT,PROFESSION,LINK_SOFT,TEAM_TYPE,LINK_CITY)" +
                " values(?,?,?,?,?,?,?,?,?,?,?)";
        try {
            PreparedStatement preparedStatement=conn.prepareStatement(sql);
            preparedStatement.setString(1,smartLink.getGUID());
            preparedStatement.setString(2,smartLink.getLINK_MAN());
            preparedStatement.setString(3,smartLink.getLINK_TEL());
            preparedStatement.setString(4,smartLink.getLINK_WX());
            preparedStatement.setString(5,smartLink.getLINK_TYPE());
            preparedStatement.setString(6,smartLink.getDATATIME());
            preparedStatement.setString(7,smartLink.getLINK_CONTENT());
            preparedStatement.setString(8,smartLink.getPROFESSION());
            preparedStatement.setString(9,smartLink.getLINK_SOFT());
            preparedStatement.setString(10,smartLink.getTEAM_TYPE());
            preparedStatement.setString(11,smartLink.getLINK_CITY());

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
    @Override
    public void deleteProjectUser(String project_num, String uid){
        Connection conn = JdbcUtils.getConn();
        //SQL语句
        String sql = "delete from  SMART_PROJECT_USER where PROJECT_NUM=? and USER_ID=?";
        try {
            PreparedStatement preparedStatement=conn.prepareStatement(sql);
            preparedStatement.setString(1,project_num);
            preparedStatement.setString(2,uid);

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
    @Override
    public List<JSONObject> GetBannerUserinfo(){
        JSONObject jsonObject=null;
        List<JSONObject> list=new ArrayList<JSONObject>();
        Connection conn = JdbcUtils.getConn();
        //SQL语句
        String sql = "SELECT A.GUID,A.NICK_NAME USER_ID,D.DICT_VALUE PROFESSION,A.CREGIE_ID,  " +
                "CASE WHEN A.LEVEL='00' THEN '/image/dazhong.png'  " +
                " WHEN A.LEVEL='01' AND LEVEL_TYPE='01' THEN '/image/nian_huangjin.png'  " +
                " WHEN A.LEVEL='01' AND LEVEL_TYPE !='01' THEN '/image/huangjin.png'  " +
                " WHEN A.LEVEL='02' AND LEVEL_TYPE='01' THEN '/image/nian_zuanshi.png'  " +
                " WHEN A.LEVEL='02' AND LEVEL_TYPE !='01' THEN '/image/zuanshi.png'  " +
                " ELSE '/image/dazhong.png' END USER_LEVEL,  " +
                " CASE WHEN A.USER_TYPE='01' AND A.USER_TYPE_FLAG='00' THEN '/image/yue_man.png'  " +
                " WHEN A.USER_TYPE='01' AND A.USER_TYPE_FLAG='01' THEN '/image/ji_man.png'  " +
                " WHEN A.USER_TYPE='01' AND A.USER_TYPE_FLAG='02' THEN '/image/nian_man.png'  " +
                " ELSE '' END USER_TYPE_IMG,  " +
                "G.DICT_VALUE RECOMM_NAME,  " +
                "A.USER_ID NICK_NAME,  " +
                "CASE WHEN C.FINISH_NUM IS NULL THEN 0 ELSE C.FINISH_NUM END FINISH_NUM,  " +
                "A.USER_TYPE,A.MONEY,  " +
                "CASE WHEN A.IMG_PATH IS NULL THEN 'https://www.ronghuijinggong.com/Uploads/MyPhoto/2017-01-01/people.jpg'  " +
                "ELSE A.IMG_PATH END IMG_PATH,  " +
                "F.DICT_VALUE LEVEL_NAME  " +
                "FROM SMART_PEOPLE A  " +
                "LEFT JOIN SMART_DICT D ON A.PROFESSION=D.DICT_ID AND D.DICT_CODE='RHJG_PROFESSION'  " +
                "LEFT JOIN SMART_DICT F ON A.LEVEL = F.DICT_ID AND F.DICT_CODE='RHJG_LEVEL'  " +
                "LEFT JOIN ( SELECT USER_ID,COUNT(1) FINISH_NUM FROM SMART_PROJECT_USER WHERE STATUS='06' GROUP BY USER_ID) C ON A.USER_ID=C.USER_ID  " +
                "LEFT JOIN SMART_PHOTO E ON A.USER_ID=E.SHORT_CODE AND E.ITEM_TYPE='00'   " +
                "LEFT JOIN SMART_DICT G ON A.USER_TYPE_FLAG = G.DICT_ID AND G.DICT_CODE='RHJG_RECOM_VALUE'  " +
                "WHERE 1=1 AND USER_TYPE='01'  " +
                "AND  A.USER_ID IN('18035687121','15295643271','13521835227','15252343123') ORDER BY LAST_SIGN ASC";
        try {
            PreparedStatement preparedStatement=conn.prepareStatement(sql);
            //执行语句，得到结果集
            ResultSet resultSet=preparedStatement.executeQuery();
            while (resultSet.next()) {
                jsonObject=new JSONObject();
                //这里只查询的密码
                jsonObject.put("GUID",resultSet.getString("GUID"));
                jsonObject.put("USER_ID",resultSet.getString("USER_ID"));
                jsonObject.put("PROFESSION",resultSet.getString("PROFESSION"));
                jsonObject.put("CREGIE_ID",resultSet.getString("CREGIE_ID"));
                jsonObject.put("USER_LEVEL",resultSet.getString("USER_LEVEL"));
                jsonObject.put("USER_TYPE_IMG",resultSet.getString("USER_TYPE_IMG"));
                jsonObject.put("RECOMM_NAME",resultSet.getString("RECOMM_NAME"));
                jsonObject.put("NICK_NAME",resultSet.getString("NICK_NAME"));
                jsonObject.put("FINISH_NUM",resultSet.getString("FINISH_NUM"));
                jsonObject.put("USER_TYPE",resultSet.getString("USER_TYPE"));
                jsonObject.put("MONEY",resultSet.getString("MONEY"));
                jsonObject.put("IMG_PATH",resultSet.getString("IMG_PATH"));
                jsonObject.put("LEVEL_NAME",resultSet.getString("LEVEL_NAME"));
                list.add(jsonObject);
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
    public SMART_PEOPLE_EXT GetSmartPeopleExt(String fieldname, String fieldvalue){
        SMART_PEOPLE_EXT smartPeople=null;
        Connection conn = JdbcUtils.getConn();
        //SQL语句
        String sql = "select * From  SMART_PEOPLE_EXT where "+fieldname+"=?";
        try {
            PreparedStatement preparedStatement=conn.prepareStatement(sql);
            preparedStatement.setString(1,fieldvalue);
            //执行语句，得到结果集
            ResultSet resultSet=preparedStatement.executeQuery();
            while (resultSet.next()) {
                smartPeople=new SMART_PEOPLE_EXT();
                //这里只查询的密码
                smartPeople.setSHORT_CODE(resultSet.getString("SHORT_CODE"));
                smartPeople.setDATATIME(resultSet.getString("DATATIME"));
                smartPeople.setINVITE_MAN(resultSet.getString("INVITE_MAN"));
                smartPeople.setOPEN_ID(resultSet.getString("OPEN_ID"));
                smartPeople.setIS_MATAIN(resultSet.getString("IS_MATAIN"));
                smartPeople.setBIRTHDAY(resultSet.getString("BIRTHDAY"));
                smartPeople.setUSE_SOFT(resultSet.getString("USE_SOFT"));
                smartPeople.setWORK_YEAR(resultSet.getString("WORK_YEAR"));
                smartPeople.setNOUSE_ACCOUNT(resultSet.getString("NOUSE_ACCOUNT"));
                smartPeople.setJIFEN_POINT(resultSet.getString("JIFEN_POINT"));
                smartPeople.setREDBAG_ACCOUNT(resultSet.getString("REDBAG_ACCOUNT"));
                smartPeople.setTEAM_TYPE(resultSet.getString("TEAM_TYPE"));
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
        return smartPeople;
    }
    public List<SMART_PEOPLE_EXT> GetSmartPeopleExtlist(String Condition){
        List<SMART_PEOPLE_EXT> list=new ArrayList<>();
        SMART_PEOPLE_EXT smartPeople=null;
        Connection conn = JdbcUtils.getConn();
        //SQL语句
        String sql = "select * From  SMART_PEOPLE_EXT where 1=1 "+Condition;
        try {
            PreparedStatement preparedStatement=conn.prepareStatement(sql);
            //执行语句，得到结果集
            ResultSet resultSet=preparedStatement.executeQuery();
            while (resultSet.next()) {
                smartPeople=new SMART_PEOPLE_EXT();
                //这里只查询的密码
                smartPeople.setSHORT_CODE(resultSet.getString("SHORT_CODE"));
                smartPeople.setDATATIME(resultSet.getString("DATATIME"));
                smartPeople.setINVITE_MAN(resultSet.getString("INVITE_MAN"));
                smartPeople.setOPEN_ID(resultSet.getString("OPEN_ID"));
                smartPeople.setIS_MATAIN(resultSet.getString("IS_MATAIN"));
                smartPeople.setBIRTHDAY(resultSet.getString("BIRTHDAY"));
                smartPeople.setUSE_SOFT(resultSet.getString("USE_SOFT"));
                smartPeople.setWORK_YEAR(resultSet.getString("WORK_YEAR"));
                smartPeople.setNOUSE_ACCOUNT(resultSet.getString("NOUSE_ACCOUNT"));
                smartPeople.setJIFEN_POINT(resultSet.getString("JIFEN_POINT"));
                smartPeople.setREDBAG_ACCOUNT(resultSet.getString("REDBAG_ACCOUNT"));
                smartPeople.setTEAM_TYPE(resultSet.getString("TEAM_TYPE"));
                list.add(smartPeople);
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
    public  List<JSONObject> GetLeftCount(String pid){
        JSONObject jsonObject=null;
        List<JSONObject> list=new ArrayList<JSONObject>();
        Connection conn = JdbcUtils.getConn();
        //SQL语句
        String sql = "SELECT CASE WHEN DATEDIFF(SUBSTR(CURDATE(),1,10),SUBSTR(MEMBER_TIME,1,10)) > 30 THEN (4 - USED_COUNT) ELSE (TOTAL_COUNT-USED_COUNT) END LEFT_COUNT FROM SMART_USER_INFO WHERE USER_ID=? ";
        try {
            PreparedStatement preparedStatement=conn.prepareStatement(sql);
            preparedStatement.setString(1,pid);
            //执行语句，得到结果集
            ResultSet resultSet=preparedStatement.executeQuery();
            while (resultSet.next()) {
                jsonObject=new JSONObject();
                jsonObject.put("LEFT_COUNT",resultSet.getString("LEFT_COUNT"));
                list.add(jsonObject);
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
    public  List<PeopleDetail> GetPeopleDetail(String uid){
        PeopleDetail peopleDetail=null;
        List<PeopleDetail> list=new ArrayList<PeopleDetail>();
        Connection conn = JdbcUtils.getConn();
        //SQL语句
        String sql = "SELECT A.GUID,A.NICK_NAME,A.PROFESSION,B.DICT_VALUE LEVEL,C.DICT_VALUE USER_TYPE,A.DATATIME," +
                "A.MREGIE_ID,A.CREGIE_ID,A.QREGIE_ID,A.MONEY,A.CONTENT,CASE WHEN D.FINISHED IS NULL THEN 0 ELSE D.FINISHED END FINISHED," +
                "E.ZHILIANG,E.SUDU,E.TAIDU FROM SMART_PEOPLE A  LEFT JOIN SMART_DICT B ON A.LEVEL=B.DICT_ID AND B.DICT_CODE='RHJG_LEVEL' " +
                "LEFT JOIN SMART_DICT C ON A.USER_TYPE = C.DICT_ID AND C.DICT_CODE='RHJG_USER_TYPE' " +
                "LEFT JOIN (SELECT USER_ID, COUNT(1) FINISHED FROM SMART_PROJECT_USER WHERE STATUS='06' GROUP BY USER_ID) D " +
                "ON A.USER_ID=D.USER_ID LEFT JOIN SMART_USER_INFO E ON A.USER_ID=E.USER_ID WHERE A.GUID=? ";
        try {
            PreparedStatement preparedStatement=conn.prepareStatement(sql);
            preparedStatement.setString(1,uid);
            //执行语句，得到结果集
            ResultSet resultSet=preparedStatement.executeQuery();
            while (resultSet.next()) {
                peopleDetail=new PeopleDetail();
                peopleDetail.setGUID(resultSet.getString("GUID"));
                peopleDetail.setNICK_NAME(resultSet.getString("NICK_NAME"));
                peopleDetail.setPROFESSION(resultSet.getString("PROFESSION"));
                peopleDetail.setLEVEL(resultSet.getString("LEVEL"));
                peopleDetail.setUSER_TYPE(resultSet.getString("USER_TYPE"));
                peopleDetail.setDATATIME(resultSet.getString("DATATIME"));
                peopleDetail.setMREGIE_ID(resultSet.getString("MREGIE_ID"));
                peopleDetail.setCREGIE_ID(resultSet.getString("CREGIE_ID"));
                peopleDetail.setQREGIE_ID(resultSet.getString("QREGIE_ID"));
                peopleDetail.setMONEY(resultSet.getString("MONEY"));
                peopleDetail.setCONTENT(resultSet.getString("CONTENT"));
                peopleDetail.setFINISHED(resultSet.getString("FINISHED"));
                peopleDetail.setZHILIANG(resultSet.getString("ZHILIANG"));
                peopleDetail.setSUDU(resultSet.getString("SUDU"));
                peopleDetail.setTAIDU(resultSet.getString("TAIDU"));
                list.add(peopleDetail);
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
    public  List<NewPeopleDetail1> GetNewPeopleDetail1(String uid){
        NewPeopleDetail1 newPeopleDetail1=null;
        List<NewPeopleDetail1> list=new ArrayList<NewPeopleDetail1>();
        Connection conn = JdbcUtils.getConn();
        //SQL语句
        String sql = "SELECT A.GUID,A.NICK_NAME,A.PROFESSION,B.DICT_VALUE LEVEL,C.DICT_VALUE USER_TYPE,SUBSTR(A.REGISTER_TIME,1,10) DATATIME," +
                "A.MREGIE_ID,A.CREGIE_ID,A.QREGIE_ID,A.MONEY,A.CONTENT,CASE WHEN D.FINISHED IS NULL THEN 0 ELSE D.FINISHED END FINISHED," +
                "E.ZHILIANG,E.SUDU,E.TAIDU,A.IMG_PATH,F.SHORT_CODE,D.PROJECT_AMT " +
                "FROM SMART_PEOPLE A LEFT JOIN SMART_DICT B ON A.LEVEL=B.DICT_ID AND B.DICT_CODE='RHJG_LEVEL' LEFT JOIN SMART_DICT C ON A.USER_TYPE = C.DICT_ID AND C.DICT_CODE='RHJG_USER_TYPE' " +
                "LEFT JOIN (SELECT USER_ID, COUNT(1) FINISHED,IFNULL(SUM(PROJECT_AMT),0) PROJECT_AMT FROM SMART_PROJECT_USER WHERE STATUS='06' GROUP BY USER_ID) D " +
                "ON A.USER_ID=D.USER_ID LEFT JOIN SMART_USER_INFO E ON A.USER_ID=E.USER_ID LEFT JOIN SMART_PEOPLE_EXT F ON A.USER_ID=F.USER_ID WHERE A.USER_ID=?";
        try {
            PreparedStatement preparedStatement=conn.prepareStatement(sql);
            preparedStatement.setString(1,uid);
            //执行语句，得到结果集
            ResultSet resultSet=preparedStatement.executeQuery();
            while (resultSet.next()) {
                newPeopleDetail1=new NewPeopleDetail1();
                newPeopleDetail1.setGUID(resultSet.getString("GUID"));
                newPeopleDetail1.setNICK_NAME(resultSet.getString("NICK_NAME"));
                newPeopleDetail1.setPROFESSION(resultSet.getString("PROFESSION"));
                newPeopleDetail1.setLEVEL(resultSet.getString("LEVEL"));
                newPeopleDetail1.setUSER_TYPE(resultSet.getString("USER_TYPE"));
                newPeopleDetail1.setDATATIME(resultSet.getString("DATATIME"));
                newPeopleDetail1.setMREGIE_ID(resultSet.getString("MREGIE_ID"));
                newPeopleDetail1.setCREGIE_ID(resultSet.getString("CREGIE_ID"));
                newPeopleDetail1.setQREGIE_ID(resultSet.getString("QREGIE_ID"));
                newPeopleDetail1.setMONEY(resultSet.getString("MONEY"));
                newPeopleDetail1.setCONTENT(resultSet.getString("CONTENT"));
                newPeopleDetail1.setFINISHED(resultSet.getString("FINISHED"));
                newPeopleDetail1.setZHILIANG(resultSet.getString("ZHILIANG"));
                newPeopleDetail1.setSUDU(resultSet.getString("SUDU"));
                newPeopleDetail1.setTAIDU(resultSet.getString("TAIDU"));
                newPeopleDetail1.setIMG_PATH(resultSet.getString("IMG_PATH"));
                newPeopleDetail1.setSHORT_CODE(resultSet.getString("SHORT_CODE"));
                newPeopleDetail1.setPROJECT_AMT(resultSet.getString("PROJECT_AMT"));
                list.add(newPeopleDetail1);
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
    public  List<HashMap<String,Object>> GetNewPeopleDetail2(String uid){
        HashMap<String,Object> map=null;
        List<HashMap<String,Object>> list=new ArrayList<HashMap<String,Object>>();
        Connection conn = JdbcUtils.getConn();
        //SQL语句
        String sql = "SELECT C.DICT_VALUE CLASSIFY,COUNT(1) CLASSIFY_VALUE FROM SMART_PROJECT A,SMART_PROJECT_USER B,SMART_DICT C WHERE A.PROJECT_NUM=B.PROJECT_NUM " +
                "AND A.PROJECT_FLAG=C.DICT_ID AND C.DICT_CODE='RHJG_PROJECT_TYPE_00' AND B.STATUS='06' AND B.USER_ID =?  GROUP BY C.DICT_VALUE";
        try {
            PreparedStatement preparedStatement=conn.prepareStatement(sql);
            preparedStatement.setString(1,uid);
            //执行语句，得到结果集
            ResultSet resultSet=preparedStatement.executeQuery();
            while (resultSet.next()) {
                map=new HashMap<>();
                map.put("CLASSIFY",resultSet.getString("CLASSIFY"));
                map.put("CLASSIFY_VALUE",resultSet.getString("CLASSIFY_VALUE"));
                list.add(map);
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
    public  List<HashMap<String,Object>> GetNewPeopleDetail3(String uid){
        HashMap<String,Object> map=null;
        List<HashMap<String,Object>> list=new ArrayList<HashMap<String,Object>>();
        Connection conn = JdbcUtils.getConn();
        //SQL语句
        String sql = "SELECT A.PROJECT_NUM,B.DICT_VALUE PROJECT_CLASSIFY, A.PROJECT_AMT,A.MREGIE_ID,A.CREGIE_ID,SUBSTR(A.DATATIME,1,10) FINISH_DATE, " +
                "A.PROJECT_DESC,A.SCAN_NUM,C.DICT_VALUE STATUS,A.PROJECT_TYPE, A.STATUS STATUS_VALUE,A.PROFESSIONAL,A.PROFESSION_NAME, " +
                "CASE WHEN (A.TUIJIAN IS NULL OR A.TUIJIAN='' OR A.TUIJIAN='15144180088') THEN '0' ELSE '1' END TUIJIAN," +
                "A.TUIJIAN TJ,IFNULL(A.LABEL_1,'') LABEL_1 ,IFNULL(A.LABEL_2,'') LABEL_2,IFNULL(A.LABEL_3,'') LABEL_3 " +
                "FROM SMART_PROJECT A LEFT JOIN SMART_DICT B ON A.PROJECT_USE = B.DICT_ID AND B.DICT_CODE='RHJG_CLASSIFY' " +
                "LEFT JOIN SMART_DICT C ON A.STATUS = C.DICT_ID AND C.DICT_CODE='RHJG_STATUS' " +
                "WHERE 1=1 AND A.PROJECT_NUM IN (SELECT DISTINCT PROJECT_NUM FROM SMART_PROJECT_USER WHERE STATUS='06' AND USER_ID=? )";
        try {
            PreparedStatement preparedStatement=conn.prepareStatement(sql);
            preparedStatement.setString(1,uid);
            //执行语句，得到结果集
            ResultSet resultSet=preparedStatement.executeQuery();
            while (resultSet.next()) {
                map=new HashMap<>();
                map.put("PROJECT_NUM",resultSet.getString("PROJECT_NUM"));
                map.put("PROJECT_CLASSIFY",resultSet.getString("PROJECT_CLASSIFY"));
                map.put("PROJECT_AMT",resultSet.getString("PROJECT_AMT"));
                map.put("MREGIE_ID",resultSet.getString("MREGIE_ID"));
                map.put("CREGIE_ID",resultSet.getString("CREGIE_ID"));
                map.put("FINISH_DATE",resultSet.getString("FINISH_DATE"));
                map.put("PROJECT_DESC",resultSet.getString("PROJECT_DESC"));
                map.put("SCAN_NUM",resultSet.getString("SCAN_NUM"));
                map.put("STATUS",resultSet.getString("STATUS"));
                map.put("PROJECT_TYPE",resultSet.getString("PROJECT_TYPE"));
                map.put("STATUS_VALUE",resultSet.getString("STATUS_VALUE"));
                map.put("PROFESSIONAL",resultSet.getString("PROFESSIONAL"));
                map.put("TUIJIAN",resultSet.getString("TUIJIAN"));
                map.put("TJ",resultSet.getString("TJ"));
                map.put("LABEL_1",resultSet.getString("LABEL_1"));
                map.put("LABEL_2",resultSet.getString("LABEL_2"));
                map.put("LABEL_3",resultSet.getString("LABEL_3"));
                list.add(map);
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
    public  List<HashMap<String,Object>> GetNewPeopleDetail4(String uid){
        HashMap<String,Object> map=null;
        List<HashMap<String,Object>> list=new ArrayList<HashMap<String,Object>>();
        Connection conn = JdbcUtils.getConn();
        //SQL语句
        String sql = "SELECT SUBSTR(B.DATATIME1,1,10) DATATIME1,B.CONTENT,A.NICK_NAME,A.CREGIE_ID FROM SMART_PEOPLE A,SMART_JUDGE B WHERE B.JUDGE_USER=A.USER_ID AND B.CUST_ID=?";
        try {
            PreparedStatement preparedStatement=conn.prepareStatement(sql);
            preparedStatement.setString(1,uid);
            //执行语句，得到结果集
            ResultSet resultSet=preparedStatement.executeQuery();
            while (resultSet.next()) {
                map=new HashMap<>();
                map.put("DATATIME1",resultSet.getString("DATATIME1"));
                map.put("CONTENT",resultSet.getString("CONTENT"));
                map.put("NICK_NAME",resultSet.getString("NICK_NAME"));
                map.put("CREGIE_ID",resultSet.getString("CREGIE_ID"));
                list.add(map);
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
    public void updateCommon(String table, String sqlfield, String sqlwhere){
        Connection conn = JdbcUtils.getConn();
        //SQL语句
        String sql = "update "+table+" set "+sqlfield+" where 1=1  "+sqlwhere;
        try {
            PreparedStatement preparedStatement=conn.prepareStatement(sql);
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
    @Override
    public List<SMART_VERIFY> getVerify(String sqlwhere, Map<String ,Object> map){
        List<SMART_VERIFY> list=new ArrayList<SMART_VERIFY>();
        SMART_VERIFY smartVerify=new SMART_VERIFY();
        Connection conn = JdbcUtils.getConn();
        //SQL语句
        String sql = "SELECT * FROM SMART_VERIFY WHERE 1=1 "+sqlwhere+" ";
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
                smartVerify=new SMART_VERIFY();
                smartVerify.setGUID(resultSet.getString("GUID"));
                smartVerify.setDATATIME(resultSet.getString("DATATIME"));
                smartVerify.setUSER_ID(resultSet.getString("USER_ID"));
                smartVerify.setVERIFY_CODE(resultSet.getString("VERIFY_CODE"));
                list.add(smartVerify);
            }
        } catch (SQLException e1) {
           // e1.printStackTrace();
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
    public  List<HashMap<String,Object>> GetUserInfo(String uid){
        HashMap<String,Object> map=null;
        List<HashMap<String,Object>> list=new ArrayList<HashMap<String,Object>>();
        Connection conn = JdbcUtils.getConn();
        //SQL语句
        String sql = "SELECT A.MONEY,B.DICT_VALUE,A.LEVEL LEVEL_VALUE,NICK_NAME,A.USER_ID,A.NICK_NAME,A.CONTENT,A.USER_TYPE,A.LEVEL, " +
                "D.DICT_VALUE LEVEL_IMAGE,A.TOTAL_COUNT,A.MEMBER_TIME,DATEDIFF(CURDATE(),LAST_SIGN_DATE) IS_SIGN,CONTINUE_SIGN,A.IMG_PATH," +
                "A.LEVEL_TYPE,A.RECOMMEND_TIME,A.USER_TYPE_FLAG,A.PROFESSION,C.BIRTHDAY,C.USE_SOFT,C.WORK_YEAR,A.MREGIE_ID,A.CREGIE_ID,A.QREGIE_ID," +
                "C.JIFEN_POINT,C.NOUSE_ACCOUNT,CASE WHEN C.JIFEN_POINT BETWEEN 0 AND 100 THEN 'Lv0' WHEN C.JIFEN_POINT BETWEEN 101 AND 200 THEN 'Lv1' " +
                "WHEN C.JIFEN_POINT BETWEEN 201 AND 300 THEN 'Lv2' WHEN C.JIFEN_POINT BETWEEN 301 AND 500 THEN 'Lv3' " +
                "WHEN C.JIFEN_POINT BETWEEN 501 AND 700 THEN 'Lv4' WHEN C.JIFEN_POINT BETWEEN 701 AND 900 THEN 'Lv5' " +
                "WHEN C.JIFEN_POINT BETWEEN 901 AND 1400 THEN 'Lv6' WHEN C.JIFEN_POINT BETWEEN 1401 AND 1800 THEN 'Lv7' " +
                "WHEN C.JIFEN_POINT BETWEEN 1801 AND 2200 THEN 'Lv8' WHEN C.JIFEN_POINT BETWEEN 2201 AND 3000 THEN 'Lv9' " +
                "WHEN C.JIFEN_POINT BETWEEN 3001 AND 4000 THEN 'Lv10' WHEN C.JIFEN_POINT BETWEEN 4001 AND 10000000000 THEN 'SuperVip' " +
                "ELSE 'Lv0' END JIFEN_LEVEL FROM SMART_PEOPLE A  LEFT JOIN SMART_DICT B ON A.LEVEL=B.DICT_ID AND B.DICT_CODE='RHJG_LEVEL' " +
                "LEFT JOIN SMART_PEOPLE_EXT C ON A.USER_ID=C.USER_ID LEFT JOIN SMART_DICT D ON A.LEVEL=D.DICT_ID AND D.DICT_CODE='RHJG_LEVEL_IMAGE' " +
                "WHERE A.USER_ID=? ";
        try {
            PreparedStatement preparedStatement=conn.prepareStatement(sql);
            preparedStatement.setString(1,uid);
            //执行语句，得到结果集
            ResultSet resultSet=preparedStatement.executeQuery();
            while (resultSet.next()) {
                map=new HashMap<>();
                map.put("MONEY",resultSet.getString("MONEY"));
                map.put("DICT_VALUE",resultSet.getString("DICT_VALUE"));
                map.put("LEVEL_VALUE",resultSet.getString("LEVEL_VALUE"));
                map.put("NICK_NAME",resultSet.getString("NICK_NAME"));
                map.put("USER_ID",resultSet.getString("USER_ID"));
             /*   map.put("NICK_NAME",resultSet.getString("NICK_NAME"));*/
                map.put("CONTENT",resultSet.getString("CONTENT"));
                map.put("USER_TYPE",resultSet.getString("USER_TYPE"));
                map.put("LEVEL",resultSet.getString("LEVEL"));
                map.put("LEVEL_IMAGE",resultSet.getString("LEVEL_IMAGE"));
                map.put("TOTAL_COUNT",resultSet.getString("TOTAL_COUNT"));
                map.put("MEMBER_TIME",resultSet.getString("MEMBER_TIME"));
                map.put("IS_SIGN",resultSet.getString("IS_SIGN"));
                map.put("CONTINUE_SIGN",resultSet.getString("CONTINUE_SIGN"));
                map.put("IMG_PATH",resultSet.getString("IMG_PATH"));
                map.put("LEVEL_TYPE",resultSet.getString("LEVEL_TYPE"));
                map.put("RECOMMEND_TIME",resultSet.getString("RECOMMEND_TIME"));
                map.put("USER_TYPE_FLAG",resultSet.getString("USER_TYPE_FLAG"));
                map.put("PROFESSION",resultSet.getString("PROFESSION"));
                map.put("BIRTHDAY",resultSet.getString("BIRTHDAY"));
                map.put("USE_SOFT",resultSet.getString("USE_SOFT"));
                map.put("WORK_YEAR",resultSet.getString("WORK_YEAR"));
                map.put("MREGIE_ID",resultSet.getString("MREGIE_ID"));
                map.put("CREGIE_ID",resultSet.getString("CREGIE_ID"));
                map.put("QREGIE_ID",resultSet.getString("QREGIE_ID"));
                map.put("JIFEN_POINT",resultSet.getString("JIFEN_POINT"));
                map.put("JIFEN_LEVEL",resultSet.getString("JIFEN_LEVEL"));
                list.add(map);
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
        return  list;
   }
    public  List<HashMap<String,Object>> AuthenMan(String uid,String true_pwd){
        HashMap<String,Object> map=null;
        List<HashMap<String,Object>> list=new ArrayList<HashMap<String,Object>>();
        Connection conn = JdbcUtils.getConn();
        //SQL语句
        String sql = "SELECT (TOTAL_COUNT-USED_COUNT) LEFT_COUNT,LEVEL,MONEY,B.DICT_VALUE LEVEL_NAME, " +
                "CASE WHEN A.LEVEL='00' THEN '/image/dazhong.png' WHEN A.LEVEL='01' AND LEVEL_TYPE='01' THEN '/image/nian_huangjin.png' " +
                "WHEN A.LEVEL='01' AND LEVEL_TYPE !='01' THEN '/image/huangjin.png' WHEN A.LEVEL='02' AND LEVEL_TYPE='01' THEN '/image/nian_zuanshi.png' " +
                "WHEN A.LEVEL='02' AND LEVEL_TYPE !='01' THEN '/image/zuanshi.png' ELSE '/image/dazhong.png' END LEVEL_IMAGE, " +
                "A.NICK_NAME,ADMIN,IMG_PATH FROM SMART_USER_INFO A LEFT JOIN SMART_DICT B ON A.LEVEL=B.DICT_ID AND B.DICT_CODE='RHJG_LEVEL' " +
                "WHERE USER_ID=? AND PASS_WORD=? ";
        try {
            PreparedStatement preparedStatement=conn.prepareStatement(sql);
            preparedStatement.setString(1,uid);
            preparedStatement.setString(2,true_pwd);
            //执行语句，得到结果集
            ResultSet resultSet=preparedStatement.executeQuery();
            while (resultSet.next()) {
                map=new HashMap<>();
                map.put("LEFT_COUNT",resultSet.getString("LEFT_COUNT"));
                map.put("LEVEL",resultSet.getString("LEVEL"));
                map.put("MONEY",resultSet.getString("MONEY"));
                map.put("LEVEL_NAME",resultSet.getString("LEVEL_NAME"));
                map.put("LEVEL_IMAGE",resultSet.getString("LEVEL_IMAGE"));
                map.put("NICK_NAME",resultSet.getString("NICK_NAME"));
                map.put("ADMIN",resultSet.getString("ADMIN"));
                map.put("IMG_PATH",resultSet.getString("IMG_PATH"));
                list.add(map);
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
        return  list;
    }

    public  List<HashMap<String,Object>> GetTongJi(Map<String ,Object> map){
        List<HashMap<String,Object>> list=new ArrayList<HashMap<String,Object>>();
        HashMap<String,Object> objectMap=new HashMap<>();
        Connection conn = JdbcUtils.getConn();
        //SQL语句
        String sql = "SELECT COUNT(1) TOTAL_USERS,SUM(CASE WHEN (SUBSTR(REGISTER_TIME,1,10) = SUBSTR(NOW(),1,10)) THEN 1 ELSE 0 END) NEW_USERS FROM SMART_PEOPLE WHERE 1=1  ";
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
                objectMap=new HashMap<>();
                objectMap.put("TOTAL_USERS",resultSet.getString("TOTAL_USERS"));
                objectMap.put("NEW_USERS",resultSet.getString("NEW_USERS"));
                list.add(objectMap);
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
    public  List<HashMap<String,Object>> GetDays(Map<String ,Object> map){
        List<HashMap<String,Object>> list=new ArrayList<HashMap<String,Object>>();
        HashMap<String,Object> objectMap=new HashMap<>();
        Connection conn = JdbcUtils.getConn();
        //SQL语句
        String sql = "SELECT DATEDIFF(SUBSTR(CURDATE(),1,10),SUBSTR(MAX(DATATIME),1,10)) DAYS FROM SMART_TRADE  where 1=1  ";
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
                objectMap=new HashMap<>();
                objectMap.put("DAYS",resultSet.getString("DAYS"));
                list.add(objectMap);
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
    public  List<SMART_PROJECT_USER> GetProjectUser(Map<String ,Object> map){
        List<SMART_PROJECT_USER> list=new ArrayList<SMART_PROJECT_USER>();
        SMART_PROJECT_USER smartProjectUser=new SMART_PROJECT_USER();
        Connection conn = JdbcUtils.getConn();
        //SQL语句
        String sql = "SELECT * FROM SMART_PROJECT_USER WHERE 1=1  ";
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
                smartProjectUser=new SMART_PROJECT_USER();
                smartProjectUser.setGUID(resultSet.getString("GUID"));
                smartProjectUser.setUSER_ID(resultSet.getString("USER_ID"));
                list.add(smartProjectUser);
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
    public  List<HashMap<String,Object>> findUserinfo(String uid){
        List<HashMap<String,Object>> list=new ArrayList<HashMap<String,Object>>();
        HashMap<String,Object> map=new HashMap<>();
        Connection conn = JdbcUtils.getConn();
        //SQL语句
        String sql = "SELECT * FROM SMART_USER_INFO WHERE USER_ID=? ";
        try {
            PreparedStatement preparedStatement=conn.prepareStatement(sql);
            preparedStatement.setString(1,uid);
            //执行语句，得到结果集
            ResultSet resultSet=preparedStatement.executeQuery();
            ResultSetMetaData rsmd = resultSet.getMetaData();
            int columnCount =rsmd.getColumnCount();
            while (resultSet.next()) {
                rsmd= resultSet.getMetaData();
                map=new HashMap<String,Object>();
                    for(int i=1;i<=columnCount;i++){
                        map.put(rsmd.getColumnName(i),resultSet.getString(rsmd.getColumnName(i)));
                    }
                list.add(map);
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
