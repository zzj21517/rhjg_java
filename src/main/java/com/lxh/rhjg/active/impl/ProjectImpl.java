package com.lxh.rhjg.active.impl;

import com.alibaba.fastjson.JSONObject;
import com.lxh.rhjg.active.api.IProject;
import com.lxh.rhjg.active.api.SMART_PROJECT;
import com.lxh.rhjg.active.api.SMART_SUBSCRIBE;
import com.lxh.rhjg.entity.*;
import com.lxh.test.common.JdbcUtils;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Component
@Service
public class ProjectImpl implements IProject {
    @Override
    public SMART_PROJECT findProject(String fieldname, String fieldvalue){
        SMART_PROJECT smart_project=null;
        Connection conn = JdbcUtils.getConn();
        //SQL语句
        String sql = "select * From  SMART_PROJECT where "+fieldname+"=?";
        try {
            PreparedStatement preparedStatement=conn.prepareStatement(sql);
            preparedStatement.setString(1,fieldvalue);
            //执行语句，得到结果集
         ResultSet resultSet=preparedStatement.executeQuery();
            while (resultSet.next()) {
                smart_project=new SMART_PROJECT();
                //这里只查询的密码
                smart_project.setBID_CLASSIFY(resultSet.getString("BID_CLASSIFY"));
                smart_project.setCREGIE_ID(resultSet.getString("CREGIE_ID"));
                smart_project.setCUST_ID(resultSet.getString("CUST_ID"));
                smart_project.setDATATIME(resultSet.getString("DATATIME"));
                smart_project.setDEAL_AMT(resultSet.getString("DEAL_AMT"));
                smart_project.setFILE(resultSet.getString("FILE"));
                smart_project.setFINISH_DATE(resultSet.getString("FINISH_DATE"));
                smart_project.setIS_EAT(resultSet.getString("IS_EAT"));
                smart_project.setIS_SEND(resultSet.getString("IS_SEND"));
                smart_project.setIS_TRAVEL(resultSet.getString("IS_TRAVEL"));
                smart_project.setLABEL_1(resultSet.getString("LABEL_1"));
                smart_project.setLABEL_2(resultSet.getString("LABEL_2"));
                smart_project.setLABEL_3(resultSet.getString("LABEL_3"));
                smart_project.setLINK_MAN(resultSet.getString("LINK_MAN"));
                smart_project.setLINK_TEL(resultSet.getString("LINK_TEL"));
                smart_project.setLIST_NUM(resultSet.getString("LIST_NUM"));
                smart_project.setMREGIE_ID(resultSet.getString("MREGIE_ID"));
                smart_project.setPAY_AMT(resultSet.getString("PAY_AMT"));
                smart_project.setPAY_STATUS(resultSet.getString("PAY_STATUS"));
                smart_project.setPAY_STATUS10(resultSet.getString("PAY_STATUS10"));
                smart_project.setPAY_STATUS20(resultSet.getString("PAY_STATUS20"));
                smart_project.setPAY_TYPE(resultSet.getString("PAY_TYPE"));
                smart_project.setPROFESSION_NAME(resultSet.getString("PROFESSION_NAME"));
                smart_project.setYIJIA(resultSet.getString("YIJIA"));
                smart_project.setQUALITY_NEED(resultSet.getString("QUALITY_NEED"));
                smart_project.setPROJECT_NEED(resultSet.getString("PROJECT_NEED"));
                smart_project.setPROJECT_FLAG(resultSet.getString("PROJECT_FLAG"));
                smart_project.setPROFESSIONAL(resultSet.getString("PROFESSIONAL"));
                smart_project.setTAIDU(resultSet.getString("TAIDU"));
                smart_project.setSUDU(resultSet.getString("SUDU"));
                smart_project.setZHILIANG(resultSet.getString("ZHILIANG"));
                smart_project.setTUIJIAN(resultSet.getString("TUIJIAN"));
                smart_project.setPROJECT_TYPE(resultSet.getString("PROJECT_TYPE"));
                smart_project.setPROJECT_DESC(resultSet.getString("PROJECT_DESC"));
                smart_project.setSCAN_NUM(resultSet.getString("SCAN_NUM"));
                smart_project.setPROJECT_SIZE(resultSet.getString("PROJECT_SIZE"));
                smart_project.setPROJECT_AMT(resultSet.getString("PROJECT_AMT"));
                smart_project.setQREGIE_ID(resultSet.getString("QREGIE_ID"));
                smart_project.setPROJECT_USE(resultSet.getString("PROJECT_USE"));
                smart_project.setPROJECT_SIZE_TYPE(resultSet.getString("PROJECT_SIZE_TYPE"));
                smart_project.setPROJECT_CLASSIFY(resultSet.getString("PROJECT_CLASSIFY"));
                smart_project.setPROJECT_NUM(resultSet.getString("PROJECT_NUM"));
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
        return smart_project;
    }
    //插入项目
    @Override
    public int InsertProject(SMART_PROJECT smartProject){
        int n=0;
        Connection conn = JdbcUtils.getConn();
        //SQL语句
        String sql = "INSERT INTO SMART_PROJECT(PROJECT_NUM, CUST_ID, PROJECT_CLASSIFY,PROJECT_USE, LINK_MAN, LINK_TEL, PROJECT_AMT, "+
        "MREGIE_ID, CREGIE_ID, QREGIE_ID, PROJECT_SIZE, FINISH_DATE, PROJECT_DESC, STATUS, DATATIME,TUIJIAN,PROFESSIONAL,"+
        "PROFESSION_NAME,PROJECT_FLAG,PROJECT_NEED,IS_TRAVEL,IS_EAT,BID_CLASSIFY,QUALITY_NEED,YIJIA,PAY_TYPE,LIST_NUM,PROJECT_SIZE_TYPE,productguid)"+
        " VALUES(?,?,?,?,?,?,?,?,?,?,?, ?,?,?,?,?,?,?,?,?, ?,?,?,?,?,?,?,?,?) ";
        try {
            PreparedStatement preparedStatement=conn.prepareStatement(sql);
            preparedStatement.setString(1,smartProject.getPROJECT_NUM());
            preparedStatement.setString(2,smartProject.getCUST_ID());
            preparedStatement.setString(3,smartProject.getPROJECT_CLASSIFY());
            preparedStatement.setString(4,smartProject.getPROJECT_USE());
            preparedStatement.setString(5,smartProject.getLINK_MAN());
            preparedStatement.setString(6,smartProject.getLINK_TEL());
            preparedStatement.setString(7,smartProject.getPROJECT_AMT());
            preparedStatement.setString(8,smartProject.getMREGIE_ID());
            preparedStatement.setString(9,smartProject.getCREGIE_ID());
            preparedStatement.setString(10,smartProject.getQREGIE_ID());
            preparedStatement.setString(11,smartProject.getPROJECT_SIZE());
            preparedStatement.setString(12,smartProject.getFINISH_DATE());
            preparedStatement.setString(13,smartProject.getPROJECT_DESC());
            preparedStatement.setString(14,smartProject.getSTATUS());
            preparedStatement.setString(15,smartProject.getDATATIME());
            preparedStatement.setString(16,smartProject.getTUIJIAN());
            preparedStatement.setString(17,smartProject.getPROFESSIONAL());
            preparedStatement.setString(18,smartProject.getPROFESSION_NAME());
            preparedStatement.setString(19,smartProject.getPROJECT_FLAG());
            preparedStatement.setString(20,smartProject.getPROJECT_NEED());
            preparedStatement.setString(21,smartProject.getIS_TRAVEL());
            preparedStatement.setString(22,smartProject.getIS_EAT());
            preparedStatement.setString(23,smartProject.getBID_CLASSIFY());
            preparedStatement.setString(24,smartProject.getQUALITY_NEED());
            preparedStatement.setString(25,smartProject.getYIJIA());
            preparedStatement.setString(26,smartProject.getPAY_TYPE());
            preparedStatement.setString(27,smartProject.getLIST_NUM());
            preparedStatement.setString(28,smartProject.getPROJECT_SIZE_TYPE());
            preparedStatement.setString(29,smartProject.getProductguid());
            //执行语句，得到结果集
            preparedStatement.execute();
            n=1;
        } catch (SQLException e1) {
            e1.printStackTrace();
        }finally {
            try {
                conn.close();//关闭连接
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return n;
    }
    //插入项目
    @Override
    public int InsertoldProject(SMART_PROJECT smartProject){
        int n=0;
        Connection conn = JdbcUtils.getConn("oldurl","oldusername","oldpassword");
        //SQL语句
        String sql = "INSERT INTO SMART_PROJECT(PROJECT_NUM, CUST_ID, PROJECT_CLASSIFY,PROJECT_USE, LINK_MAN, LINK_TEL, PROJECT_AMT, "+
                "MREGIE_ID, CREGIE_ID, QREGIE_ID, PROJECT_SIZE, FINISH_DATE, PROJECT_DESC, STATUS, DATATIME,TUIJIAN,PROFESSIONAL,"+
                "PROFESSION_NAME,PROJECT_FLAG,PROJECT_NEED,IS_TRAVEL,IS_EAT,BID_CLASSIFY,QUALITY_NEED,YIJIA,PAY_TYPE,LIST_NUM,PROJECT_SIZE_TYPE)"+
                " VALUES(?,?,?,?,?,?,?,?,?,?,?, ?,?,?,?,?,?,?,?,?, ?,?,?,?,?,?,?,?) ";
        try {
            PreparedStatement preparedStatement=conn.prepareStatement(sql);
            preparedStatement.setString(1,smartProject.getPROJECT_NUM());
            preparedStatement.setString(2,smartProject.getCUST_ID());
            preparedStatement.setString(3,smartProject.getPROJECT_CLASSIFY());
            preparedStatement.setString(4,smartProject.getPROJECT_USE());
            preparedStatement.setString(5,smartProject.getLINK_MAN());
            preparedStatement.setString(6,smartProject.getLINK_TEL());
            preparedStatement.setString(7,smartProject.getPROJECT_AMT());
            preparedStatement.setString(8,smartProject.getMREGIE_ID());
            preparedStatement.setString(9,smartProject.getCREGIE_ID());
            preparedStatement.setString(10,smartProject.getQREGIE_ID());
            preparedStatement.setString(11,smartProject.getPROJECT_SIZE());
            preparedStatement.setString(12,smartProject.getFINISH_DATE());
            preparedStatement.setString(13,smartProject.getPROJECT_DESC());
            preparedStatement.setString(14,smartProject.getSTATUS());
            preparedStatement.setString(15,smartProject.getDATATIME());
            preparedStatement.setString(16,smartProject.getTUIJIAN());
            preparedStatement.setString(17,smartProject.getPROFESSIONAL());
            preparedStatement.setString(18,smartProject.getPROFESSION_NAME());
            preparedStatement.setString(19,smartProject.getPROJECT_FLAG());
            preparedStatement.setString(20,smartProject.getPROJECT_NEED());
            preparedStatement.setString(21,smartProject.getIS_TRAVEL());
            preparedStatement.setString(22,smartProject.getIS_EAT());
            preparedStatement.setString(23,smartProject.getBID_CLASSIFY());
            preparedStatement.setString(24,smartProject.getQUALITY_NEED());
            preparedStatement.setString(25,smartProject.getYIJIA());
            preparedStatement.setString(26,smartProject.getPAY_TYPE());
            preparedStatement.setString(27,smartProject.getLIST_NUM());
            preparedStatement.setString(28,smartProject.getPROJECT_SIZE_TYPE());
            //执行语句，得到结果集
            preparedStatement.execute();
            n=1;
        } catch (SQLException e1) {
            e1.printStackTrace();
        }finally {
            try {
                conn.close();//关闭连接
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return n;
    }
    @Override
    public void insertMark(SMART_MARK smartMark){
        Connection conn = JdbcUtils.getConn();
        //SQL语句
        String sql = "INSERT INTO SMART_MARK(GUID,USER_ID,WORK_ID,FORM_ID,STATUS,DATATIME,MARK_CONTENT,PERCENT)"+
                " VALUES(?,?,?,?,?,?,?,?) ";
        try {
            PreparedStatement preparedStatement=conn.prepareStatement(sql);
            preparedStatement.setString(1,smartMark.getGUID());
            preparedStatement.setString(2,smartMark.getUSER_ID());
            preparedStatement.setString(3,smartMark.getWORK_ID());
            preparedStatement.setString(4,smartMark.getFORM_ID());
            preparedStatement.setString(5,smartMark.getSTATUS());
            preparedStatement.setString(6,smartMark.getDATATIME());
            preparedStatement.setString(7,smartMark.getMARK_CONTENT());
            preparedStatement.setString(8,smartMark.getPERCENT());
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
    public void updateproject(SMART_PROJECT smartProject)
    {
        Connection conn = JdbcUtils.getConn();
        //SQL语句
        String sql = "update  SMART_PROJECT set LINK_MAN=?,LINK_TEL=?,PROJECT_AMT=?,PROJECT_DESC=?,productguid=? WHERE PROJECT_NUM=? AND STATUS IN('00' , '01')  ";
        try {
            PreparedStatement preparedStatement=conn.prepareStatement(sql);
            preparedStatement.setString(1,smartProject.getLINK_MAN());
            preparedStatement.setString(2,smartProject.getLINK_TEL());
            preparedStatement.setString(3,smartProject.getPROJECT_AMT());
            preparedStatement.setString(4,smartProject.getPROJECT_DESC());
            preparedStatement.setString(5,smartProject.getProductguid());
            preparedStatement.setString(6,smartProject.getPROJECT_NUM());
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
    public void Commonupdateproject(String upsql, String Condition)
    {
        Connection conn = JdbcUtils.getConn();
        //SQL语句
        String sql = "update  SMART_PROJECT set "+upsql+" WHERE "+Condition;
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
    public void updateproject001(SMART_PROJECT smartProject)
    {
        Connection conn = JdbcUtils.getConn();
        //SQL语句
        String sql = "UPDATE SMART_PROJECT SET LINK_MAN =?, LINK_TEL =?, PROJECT_AMT =?,PROJECT_DESC =?, DATATIME=?,PROJECT_SIZE=?,FINISH_DATE=?,"+
                " PROJECT_TYPE=?,LABEL_1=?,LABEL_2=?,LABEL_3=?  WHERE PROJECT_NUM=?";
        try {
            PreparedStatement preparedStatement=conn.prepareStatement(sql);
            preparedStatement.setString(1,smartProject.getLINK_MAN());
            preparedStatement.setString(2,smartProject.getLINK_TEL());
            preparedStatement.setString(3,smartProject.getPROJECT_AMT());
            preparedStatement.setString(4,smartProject.getPROJECT_DESC());
            preparedStatement.setString(5,smartProject.getDATATIME());
            preparedStatement.setString(6,smartProject.getPROJECT_SIZE());
            preparedStatement.setString(6,smartProject.getFINISH_DATE());
            preparedStatement.setString(6,smartProject.getPROJECT_TYPE());
            preparedStatement.setString(6, smartProject.getLABEL_1());
            preparedStatement.setString(6, smartProject.getLABEL_2());
            preparedStatement.setString(6, smartProject.getLABEL_3());
            preparedStatement.setString(6, smartProject.getPROJECT_NUM());
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
    public List<SMART_SUBSCRIBE> SubscribeProject(String Condition){
        Connection conn = JdbcUtils.getConn();
        List<SMART_SUBSCRIBE> list=new ArrayList<>();
        SMART_SUBSCRIBE smartSubscribe=null;
        //SQL语句
        String sql = " SELECT * FROM SMART_SUBSCRIBE WHERE 1=1 "+Condition;
        try {
            PreparedStatement preparedStatement=conn.prepareStatement(sql);
            //执行语句，得到结果集
            ResultSet resultSet=preparedStatement.executeQuery();
            while (resultSet.next()) {
                smartSubscribe=new SMART_SUBSCRIBE();
                smartSubscribe.setUserId(resultSet.getString("user_id").toString());
                smartSubscribe.setUserId(resultSet.getString("user_id").toString());
                smartSubscribe.setUserId(resultSet.getString("user_id").toString());
                smartSubscribe.setUserId(resultSet.getString("user_id").toString());
                smartSubscribe.setUserId(resultSet.getString("user_id").toString());
                smartSubscribe.setUserId(resultSet.getString("user_id").toString());
                list.add(smartSubscribe);
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
    public void deleteproject(SMART_PROJECT smartProject){
        Connection conn = JdbcUtils.getConn();
        //SQL语句
        String sql = "delete from SMART_PROJECT WHERE PROJECT_NUM=?";
        try {
            PreparedStatement preparedStatement=conn.prepareStatement(sql);
            preparedStatement.setString(1, smartProject.getPROJECT_NUM());
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
    public List<ProjectDetail> GetProjectDetail001(String fid,String uid){
        Connection conn = JdbcUtils.getConn();
        List<ProjectDetail> list=new ArrayList<>();
        ProjectDetail projectDetail=null;
        //SQL语句
        String sql = "SELECT A.PROJECT_NUM, A.LINK_MAN,A.LINK_TEL,A.PROJECT_AMT,A.PROJECT_SIZE,A.MREGIE_ID,A.CREGIE_ID,A.QREGIE_ID,A.FINISH_DATE, " +
                "A.PROJECT_DESC,B.DICT_VALUE,C.DICT_VALUE STATUS,A.STATUS STATUS_VALUE,D.DICT_VALUE PAY_STATUS,A.PAY_AMT," +
                "CASE WHEN (SELECT COUNT(*) FROM SMART_PROJECT_USER WHERE PROJECT_NUM=? AND USER_ID=?)= '0' THEN '0' ELSE '1' END IS_COLLECT," +
                "IFNULL(E.PROJECT_AMT,'') T_AMT,IFNULL(E.DATATIME,'') T_TIME,IFNULL(E.PROJECT_DESC,'') T_DESC," +
                "IFNULL(F.DICT_VALUE,'') T_PAY_STATUS,IFNULL(E.PAY_AMT,'') T_PAY_AMT,IFNULL(G.DICT_VALUE,'') T_STATUS,IFNULL(E.NICK_NAME,'') NICK_NAME " +
                "FROM SMART_PROJECT A LEFT JOIN SMART_DICT B ON A.PROJECT_CLASSIFY=B.DICT_ID AND B.DICT_CODE='PROJECT_CLASSIFY' " +
                "LEFT JOIN SMART_DICT C ON A.STATUS=C.DICT_ID AND C.DICT_CODE='RHJG_STATUS' " +
                "LEFT JOIN SMART_DICT D ON A.PAY_STATUS=D.DICT_ID AND D.DICT_CODE='RHJG_PAY_STATUS' " +
                "LEFT JOIN SMART_PROJECT_USER E ON A.PROJECT_NUM = E.PROJECT_NUM AND E.STATUS='06' " +
                "LEFT JOIN SMART_DICT F ON E.PAY_STATUS = F.DICT_ID AND F.DICT_CODE='RHJG_PAY_STATUS' " +
                "LEFT JOIN SMART_DICT G ON E.STATUS=G.DICT_ID AND G.DICT_CODE='RHJG_STATUS' WHERE A.PROJECT_NUM=?";
        try {
            PreparedStatement preparedStatement=conn.prepareStatement(sql);
            preparedStatement.setString(1,fid);
            preparedStatement.setString(2, uid);
            preparedStatement.setString(3,fid);
            //执行语句，得到结果集
            ResultSet resultSet=preparedStatement.executeQuery();
            while (resultSet.next()) {
                projectDetail=new ProjectDetail();
                projectDetail.setPROJECT_NUM(resultSet.getString("PROJECT_NUM").toString());
                projectDetail.setLINK_MAN(resultSet.getString("LINK_MAN").toString());
                projectDetail.setLINK_TEL(resultSet.getString("LINK_TEL").toString());
                projectDetail.setPROJECT_AMT(resultSet.getString("PROJECT_AMT").toString());
                projectDetail.setPROJECT_SIZE(resultSet.getString("PROJECT_SIZE").toString());
                projectDetail.setMREGIE_ID(resultSet.getString("MREGIE_ID").toString());
                projectDetail.setCREGIE_ID(resultSet.getString("CREGIE_ID").toString());
                projectDetail.setQREGIE_ID(resultSet.getString("QREGIE_ID").toString());
                projectDetail.setFINISH_DATE(resultSet.getString("FINISH_DATE").toString());
                projectDetail.setPROJECT_DESC(resultSet.getString("PROJECT_DESC").toString());
                projectDetail.setDICT_VALUE(resultSet.getString("DICT_VALUE").toString());
                projectDetail.setSTATUS(resultSet.getString("STATUS").toString());
                projectDetail.setSTATUS_VALUE(resultSet.getString("STATUS_VALUE").toString());
                projectDetail.setPAY_STATUS(resultSet.getString("PAY_STATUS").toString());
                projectDetail.setPAY_AMT(resultSet.getString("PAY_AMT").toString());
                projectDetail.setIS_COLLECT(resultSet.getString("IS_COLLECT").toString());
                projectDetail.setT_AMT(resultSet.getString("T_AMT").toString());
                projectDetail.setT_TIME(resultSet.getString("T_TIME").toString());
                projectDetail.setT_DESC(resultSet.getString("T_DESC").toString());
                projectDetail.setT_PAY_STATUS(resultSet.getString("T_PAY_STATUS").toString());
                projectDetail.setT_PAY_AMT(resultSet.getString("T_PAY_AMT").toString());
                projectDetail.setT_STATUS(resultSet.getString("T_STATUS").toString());
                projectDetail.setNICK_NAME(resultSet.getString("NICK_NAME").toString());
                list.add(projectDetail);
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
    public List<ProjectDetail1> GetProjectDetail(String fid, String uid){
        Connection conn = JdbcUtils.getConn();
        List<ProjectDetail1> list=new ArrayList<>();
        ProjectDetail1 projectDetail=null;
        //SQL语句
        String sql = "SELECT A.PROJECT_NUM, A.LINK_MAN,A.LINK_TEL,A.PROJECT_AMT,A.PROJECT_SIZE,A.MREGIE_ID,A.CREGIE_ID,A.QREGIE_ID,A.FINISH_DATE,A.PROJECT_DESC, " +
                "B.DICT_VALUE LEIBIE,C.DICT_VALUE STATUS,A.STATUS STATUS_VALUE,D.DICT_VALUE PAY_STATUS,A.PROJECT_TYPE,PAY_STATUS PAY_STATUS_VALUE, " +
                "IFNULL(A.LABEL_1,'') LABEL_1,IFNULL(A.LABEL_2,'') LABEL_2,IFNULL(A.LABEL_3,'') LABEL_3, " +
                "YIJIA,E.DICT_VALUE YONGTU,F.DICT_VALUE XUQIU,G.DICT_VALUE YIJIA_NAME,A.PROFESSION_NAME,A.PROJECT_USE, " +
                "H.DICT_VALUE TRAVEL,I.DICT_VALUE EAT,J.DICT_VALUE PAY_TYPE_NAME,PAY_TYPE PAY_TYPE_VALUE,K.DICT_VALUE LIST_NUM, " +
                "CASE WHEN (SELECT COUNT(*) FROM SMART_PROJECT_USER WHERE PROJECT_NUM=? AND USER_ID=?)= '0' THEN '0' ELSE '1' END IS_COLLECT " +
                "FROM SMART_PROJECT ALEFT JOIN SMART_DICT B ON A.PROJECT_CLASSIFY=B.DICT_ID AND B.DICT_CODE='RHJG_PROJECT_TYPE_00'  " +
                "LEFT JOIN SMART_DICT C ON A.STATUS=C.DICT_ID AND C.DICT_CODE='RHJG_STATUS' LEFT JOIN SMART_DICT D ON A.PAY_STATUS=D.DICT_ID AND D.DICT_CODE='RHJG_PAY_STATUS' " +
                "LEFT JOIN SMART_DICT E ON A.PROJECT_USE=E.DICT_ID AND E.DICT_CODE='RHJG_PROJECT_USING' LEFT JOIN SMART_DICT F ON A.PROJECT_NEED=F.DICT_ID AND F.DICT_CODE='RHJG_PROJECT_NEED' " +
                "LEFT JOIN SMART_DICT G ON A.YIJIA=G.DICT_ID AND G.DICT_CODE='RHJG_YIJIA' LEFT JOIN SMART_DICT H ON A.IS_TRAVEL=H.DICT_ID AND H.DICT_CODE='RHJG_TRAVEL_FEE' " +
                "LEFT JOIN SMART_DICT I ON A.IS_EAT=I.DICT_ID AND I.DICT_CODE='RHJG_EATING_FEE' LEFT JOIN SMART_DICT J ON A.PAY_TYPE = J.DICT_ID AND J.DICT_CODE='RHJG_PAY_TYPE' " +
                "LEFT JOIN SMART_DICT K ON A.PROJECT_SIZE_TYPE=K.DICT_ID AND K.DICT_CODE='RHJG_UNIT' WHERE A.PROJECT_NUM=? ";
        try {
            PreparedStatement preparedStatement=conn.prepareStatement(sql);
            preparedStatement.setString(1,fid);
            preparedStatement.setString(2, uid);
            preparedStatement.setString(3,fid);
            //执行语句，得到结果集
            ResultSet resultSet=preparedStatement.executeQuery();
            while (resultSet.next()) {
                projectDetail=new ProjectDetail1();
                projectDetail.setPROJECT_NUM(resultSet.getString("PROJECT_NUM").toString());
                projectDetail.setLINK_MAN(resultSet.getString("LINK_MAN").toString());
                projectDetail.setLINK_TEL(resultSet.getString("LINK_TEL").toString());
                projectDetail.setPROJECT_AMT(resultSet.getString("LINK_TEL").toString());
                projectDetail.setPROJECT_SIZE(resultSet.getString("LINK_TEL").toString());
                projectDetail.setMREGIE_ID(resultSet.getString("LINK_TEL").toString());
                projectDetail.setCREGIE_ID(resultSet.getString("LINK_TEL").toString());
                projectDetail.setQREGIE_ID(resultSet.getString("LINK_TEL").toString());
                projectDetail.setFINISH_DATE(resultSet.getString("LINK_TEL").toString());
                projectDetail.setPROJECT_DESC(resultSet.getString("PROJECT_DESC").toString());
                projectDetail.setLEIBIE(resultSet.getString("LEIBIE").toString());
                projectDetail.setSTATUS(resultSet.getString("STATUS").toString());
                projectDetail.setSTATUS_VALUE(resultSet.getString("STATUS_VALUE").toString());
                projectDetail.setPAY_STATUS(resultSet.getString("PAY_STATUS").toString());
                projectDetail.setPROJECT_TYPE(resultSet.getString("PROJECT_TYPE").toString());
                projectDetail.setPAY_STATUS_VALUE(resultSet.getString("PAY_STATUS_VALUE").toString());
                projectDetail.setLABEL_1(resultSet.getString("LABEL_1").toString());
                projectDetail.setLABEL_2(resultSet.getString("LABEL_2").toString());
                projectDetail.setLABEL_3(resultSet.getString("LABEL_3").toString());
                projectDetail.setYIJIA(resultSet.getString("YIJIA").toString());
                projectDetail.setYONGTU(resultSet.getString("YONGTU").toString());
                projectDetail.setXUQIU(resultSet.getString("XUQIU").toString());
                projectDetail.setYIJIA_NAME(resultSet.getString("YIJIA_NAME").toString());
                projectDetail.setPROFESSION_NAME(resultSet.getString("PROFESSION_NAME").toString());
                projectDetail.setPROJECT_USE(resultSet.getString("PROJECT_USE").toString());
                projectDetail.setTRAVEL(resultSet.getString("TRAVEL").toString());
                projectDetail.setEAT(resultSet.getString("EAT").toString());
                projectDetail.setPAY_TYPE_NAME(resultSet.getString("PAY_TYPE_NAME").toString());
                projectDetail.setPAY_TYPE_VALUE(resultSet.getString("PAY_TYPE_VALUE").toString());
                projectDetail.setLIST_NUM(resultSet.getString("LIST_NUM").toString());
                projectDetail.setIS_COLLECT(resultSet.getString("IS_COLLECT").toString());
                list.add(projectDetail);
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
    public List<UserProjectDetail> GetZBUserDetail(String fid){
        Connection conn = JdbcUtils.getConn();
        List<UserProjectDetail> list=new ArrayList<>();
        UserProjectDetail projectDetail=null;
        //SQL语句
        String sql = "SELECT B.GUID,B.NICK_NAME,B.MREGIE_ID,B.CREGIE_ID,B.IMG_PATH,A.PROJECT_AMT " +
                "FROM SMART_PROJECT_USER A,SMART_PEOPLE B WHERE A.USER_ID=B.USER_ID AND A.STATUS='06' AND A.PROJECT_NUM=?";
        try {
            PreparedStatement preparedStatement=conn.prepareStatement(sql);
            preparedStatement.setString(1,fid);
            //执行语句，得到结果集
            ResultSet resultSet=preparedStatement.executeQuery();
            while (resultSet.next()) {
                projectDetail=new UserProjectDetail();
                projectDetail.setGUID(resultSet.getString("GUID").toString());
                projectDetail.setNICK_NAME(resultSet.getString("NICK_NAME").toString());
                projectDetail.setMREGIE_ID(resultSet.getString("MREGIE_ID").toString());
                projectDetail.setCREGIE_ID(resultSet.getString("CREGIE_ID").toString());
                projectDetail.setIMG_PATH(resultSet.getString("IMG_PATH").toString());
                projectDetail.setPROJECT_AMT(resultSet.getString("PROJECT_AMT").toString());
                list.add(projectDetail);
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
    public List<UserProjectDetail1> GetZBUserDetail1(String fid){
        Connection conn = JdbcUtils.getConn();
        List<UserProjectDetail1> list=new ArrayList<>();
        UserProjectDetail1 projectDetail=null;
        //SQL语句
        String sql = "SELECT B.USER_ID,B.GUID,B.NICK_NAME,B.LEVEL,A.PROJECT_AMT,B.MREGIE_ID,B.CREGIE_ID,SUBSTR(A.DATATIME,1,10) DATATIME," +
                "B.IMG_PATH,C.DICT_VALUE USER_LEVEL,B.USER_TYPE,E.DICT_VALUE USER_TYPR_FLAG,D.FINISH_NUM," +
                "B.MONEY,B.LEVEL_TYPE,F.DICT_VALUE LEVEL_TYPE FROM SMART_PROJECT_USER A  LEFT JOIN SMART_PEOPLE B ON A.USER_ID=B.USER_ID " +
                "LEFT JOIN SMART_DICT C ON B.LEVEL=C.DICT_ID AND C.DICT_CODE='RHJG_LEVEL' LEFT JOIN SMART_COMPLETE_TASK D ON A.USER_ID=D.USER_ID " +
                "LEFT JOIN SMART_DICT E ON B.USER_TYPE_FLAG=E.DICT_ID AND E.DICT_CODE='RHJG_USER_TYPE_IMAGE' LEFT JOIN SMART_DICT F ON B.LEVEL_TYPE=F.DICT_ID AND F.DICT_CODE='RHJG_USER_VIP' " +
                "WHERE PROJECT_NUM=? AND A.STATUS='06'";
        try {
            PreparedStatement preparedStatement=conn.prepareStatement(sql);
            preparedStatement.setString(1,fid);
            //执行语句，得到结果集
            ResultSet resultSet=preparedStatement.executeQuery();
            while (resultSet.next()) {
                projectDetail=new UserProjectDetail1();
                projectDetail.setUSER_ID(resultSet.getString("USER_ID").toString());
                projectDetail.setGUID(resultSet.getString("GUID").toString());
                projectDetail.setNICK_NAME(resultSet.getString("NICK_NAME").toString());
                projectDetail.setLEVEL(resultSet.getString("LEVEL").toString());
                projectDetail.setPROJECT_AMT(resultSet.getString("PROJECT_AMT").toString());
                projectDetail.setMREGIE_ID(resultSet.getString("MREGIE_ID").toString());
                projectDetail.setCREGIE_ID(resultSet.getString("CREGIE_ID").toString());
                projectDetail.setDATATIME(resultSet.getString("DATATIME").toString());
                projectDetail.setIMG_PATH(resultSet.getString("IMG_PATH").toString());
                projectDetail.setUSER_LEVEL(resultSet.getString("USER_LEVEL").toString());
                projectDetail.setUSER_TYPE(resultSet.getString("USER_TYPE").toString());
                projectDetail.setUSER_TYPR_FLAG(resultSet.getString("USER_TYPR_FLAG").toString());
                projectDetail.setFINISH_NUM(resultSet.getString("FINISH_NUM").toString());
                projectDetail.setMONEY(resultSet.getString("MONEY").toString());
                projectDetail.setLEVEL_TYPE(resultSet.getString("LEVEL_TYPE").toString());
                list.add(projectDetail);
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
    public JSONObject TongJiProject(){
        Connection conn = JdbcUtils.getConn();
        JSONObject jsonObject=null;
        //SQL语句
        String sql = "SELECT COUNT(1) TOTAL_PROJECT,SUM(CASE WHEN (SUBSTR(DATATIME,1,10) = SUBSTR(NOW(),1,10)) THEN 1 ELSE 0 END) NEW_PROJECT FROM SMART_PROJECT ";
        try {
            PreparedStatement preparedStatement=conn.prepareStatement(sql);
            //执行语句，得到结果集
            ResultSet resultSet=preparedStatement.executeQuery();
            while (resultSet.next()) {
                jsonObject=new JSONObject();
                jsonObject.put("TOTAL_PROJECT",resultSet.getString("TOTAL_PROJECT").toString());
                jsonObject.put("NEW_PROJECT",resultSet.getString("NEW_PROJECT").toString());
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
        return jsonObject;
    }
    public List<HashMap<String,Object>> getremark1(String workId){
        Connection conn = JdbcUtils.getConn();
        List<HashMap<String,Object>>list=new ArrayList<>();
        HashMap<String,Object> record=null;
        //SQL语句
        String sql = "SELECT DATATIME,'项目初始中标节点' MARK_CONTENT,'0' PERCENT FROM SMART_PROJECT_USER " +
                " WHERE PROJECT_NUM=? AND STATUS='06' UNION ALL SELECT DATATIME,MARK_CONTENT,PERCENT FROM SMART_MARK WHERE WORK_ID=? ORDER BY DATATIME ASC";
        try {
            PreparedStatement preparedStatement=conn.prepareStatement(sql);
            preparedStatement.setString(1,workId);
            preparedStatement.setString(2,workId);
            //执行语句，得到结果集
            ResultSet resultSet=preparedStatement.executeQuery();
            while (resultSet.next()) {
                record=new HashMap<String,Object>();
                record.put("DATATIME",resultSet.getString("DATATIME").toString());
                record.put("MARK_CONTENT",resultSet.getString("MARK_CONTENT").toString());
                record.put("PERCENT",resultSet.getString("PERCENT").toString());
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
    public List<HashMap<String,Object>> getremark2(String workId){
        Connection conn = JdbcUtils.getConn();
        List<HashMap<String,Object>>list=new ArrayList<>();
        HashMap<String,Object> record=null;
        //SQL语句
        String sql = "SELECT PROJECT_AMT,PAY_STATUS,PAY_STATUS10,PAY_STATUS20,J.DICT_VALUE PAY_TYPE_NAME,PAY_TYPE PAY_TYPE_VALUE " +
                " FROM SMART_PROJECT A LEFT JOIN SMART_DICT J ON A.PAY_TYPE = J.DICT_ID AND J.DICT_CODE='RHJG_PAY_TYPE' WHERE PROJECT_NUM=?";
        try {
            PreparedStatement preparedStatement=conn.prepareStatement(sql);
            preparedStatement.setString(1,workId);
            //执行语句，得到结果集
            ResultSet resultSet=preparedStatement.executeQuery();
            while (resultSet.next()) {
                record=new HashMap<String,Object>();
                record.put("PROJECT_AMT",resultSet.getString("PROJECT_AMT").toString());
                record.put("PAY_STATUS",resultSet.getString("PAY_STATUS").toString());
                record.put("PAY_STATUS10",resultSet.getString("PAY_STATUS10").toString());
                record.put("PAY_STATUS20",resultSet.getString("PAY_STATUS20").toString());
                record.put("PAY_TYPE_NAME",resultSet.getString("PAY_TYPE_NAME").toString());
                record.put("PAY_TYPE_VALUE",resultSet.getString("PAY_TYPE_VALUE").toString());
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
