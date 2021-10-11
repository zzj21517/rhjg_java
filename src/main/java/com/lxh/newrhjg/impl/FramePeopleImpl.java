package com.lxh.newrhjg.impl;

import com.lxh.newrhjg.api.IframePeople;
import com.lxh.newrhjg.entity.*;
import com.lxh.test.common.JdbcUtils;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Component
@Service
public class FramePeopleImpl implements IframePeople {

//    custom
    @Override
    public FramePeopleCustom findCustom(String fieldname, String fieldvalue){
        FramePeopleCustom record=null;
        Connection conn = JdbcUtils.getConn();
        //SQL语句
        String sql = "select * From  Frame_People_Custom where "+fieldname+"=?";
        try {
            PreparedStatement preparedStatement=conn.prepareStatement(sql);
            preparedStatement.setString(1,fieldvalue);
            //执行语句，得到结果集
            ResultSet resultSet=preparedStatement.executeQuery();
            while (resultSet.next()) {
                record=new FramePeopleCustom();
                record.setRowId(resultSet.getInt("rowId"));
                record.setRowGuid(resultSet.getString("rowGuid"));
                record.setUserGuid(resultSet.getString("userGuid"));
                record.setCustomTypes(resultSet.getString("customTypes"));
                record.setCustomFundAge(resultSet.getInt("customFundAge"));
                record.setCustomMemberNum(resultSet.getInt("customMemberNum"));
                record.setCreateTime(resultSet.getString("createTime"));
                record.setModifyTime(resultSet.getString("modifyTime"));
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
    public int insertCustom(FramePeopleCustom record) {
        int flag=0;
        Connection conn = JdbcUtils.getConn();
        //SQL语句
        String sql = "INSERT INTO Frame_People_Custom(rowGuid,userGuid,customTypes,customFundAge,customMemberNum,createTime,modifyTime)" +
                " VALUES(?,?,?,?,?,?,?) ";
        try {
            PreparedStatement preparedStatement=conn.prepareStatement(sql);
            System.out.println(record.getRowGuid());
            preparedStatement.setString(1,record.getRowGuid());
            preparedStatement.setString(2,record.getUserGuid());
            preparedStatement.setString(3,record.getCustomTypes());
            preparedStatement.setInt(4,record.getCustomFundAge());
            preparedStatement.setInt(5,record.getCustomMemberNum());
            preparedStatement.setString(6,record.getCreateTime());
            preparedStatement.setString(7,record.getModifyTime());
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
    public int updateCustom(FramePeopleCustom record) {
        int flag=0;
        Connection conn = JdbcUtils.getConn();
        //SQL语句
        String sql = "update Frame_People_Custom set customTypes=?,customFundAge=?,customMemberNum=?,modifyTime=? where rowGuid=?";
        try {
            PreparedStatement preparedStatement=conn.prepareStatement(sql);
            preparedStatement.setString(1,record.getCustomTypes());
            preparedStatement.setInt(2,record.getCustomFundAge());
            preparedStatement.setInt(3,record.getCustomMemberNum());
            preparedStatement.setString(4,record.getModifyTime());
            preparedStatement.setString(5,record.getRowGuid());
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

    //    engineerPerson
    @Override
    public FramePeopleEngineerPerson findEngineerPerson(String fieldname, String fieldvalue){
        FramePeopleEngineerPerson record=null;
        Connection conn = JdbcUtils.getConn();
        //SQL语句
        String sql = "select * From  Frame_People_Engineer_Person where "+fieldname+"=?";
        try {
            PreparedStatement preparedStatement=conn.prepareStatement(sql);
            preparedStatement.setString(1,fieldvalue);
            //执行语句，得到结果集
            ResultSet resultSet=preparedStatement.executeQuery();
            while (resultSet.next()) {
                record=new FramePeopleEngineerPerson();
                record.setRowId(resultSet.getInt("rowId"));
                record.setRowGuid(resultSet.getString("rowGuid"));
                record.setUserGuid(resultSet.getString("userGuid"));
                record.setEngineerName(resultSet.getString("engineerName"));
                record.setMajorTypes(resultSet.getString("majorTypes"));
                record.setCanDoArea(resultSet.getString("canDoArea"));
                record.setPartJob(resultSet.getString("partJob"));
                record.setCertImgs(resultSet.getString("certImgs"));
                record.setCanUploadElecBidding(resultSet.getInt("canUploadElecBidding"));
                record.setCanUseBim(resultSet.getInt("canUseBim"));
                record.setCanLocalCheck(resultSet.getInt("canLocalCheck"));
                record.setCanFieldCheck(resultSet.getInt("canFieldCheck"));
                record.setCanStamp(resultSet.getInt("canStamp"));
                record.setTechnologyLevel(resultSet.getInt("technologyLevel"));
                record.setWorkYears(resultSet.getInt("workYears"));
                record.setSpaceTime(resultSet.getInt("spaceTime"));
                record.setCompanyType(resultSet.getString("companyType"));
                record.setItemPricingSoftware(resultSet.getString("itemPricingSoftware"));
                record.setCalcuVolumeSoftware(resultSet.getString("calcuVolumeSoftware"));
                record.setProjectTypes(resultSet.getString("projectTypes"));
                record.setServiceTypes(resultSet.getString("serviceTypes"));
                record.setPurchaseTypes(resultSet.getString("purchaseTypes"));
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
    public FramePeopleEngineerTeam findEngineerTeam(String fieldname, String fieldvalue){
        FramePeopleEngineerTeam record=null;
        Connection conn = JdbcUtils.getConn();
        //SQL语句
        String sql = "select * From  Frame_People_Engineer_Team where "+fieldname+"=?";
        try {
            PreparedStatement preparedStatement=conn.prepareStatement(sql);
            preparedStatement.setString(1,fieldvalue);
            //执行语句，得到结果集
            ResultSet resultSet=preparedStatement.executeQuery();
            while (resultSet.next()) {
                record=new FramePeopleEngineerTeam();
                record.setRowId(resultSet.getInt("rowId"));
                record.setRowGuid(resultSet.getString("rowGuid"));
                record.setUserGuid(resultSet.getString("userGuid"));
                record.setEngineerName(resultSet.getString("engineerName"));
                record.setCanDoArea(resultSet.getString("canDoArea"));
                record.setCanUploadElecBidding(resultSet.getInt("canUploadElecBidding"));
                record.setCanUseBim(resultSet.getInt("canUseBim"));
                record.setCanLocalCheck(resultSet.getInt("canLocalCheck"));
                record.setCanFieldCheck(resultSet.getInt("canFieldCheck"));
                record.setTechnologyLevel(resultSet.getInt("technologyLevel"));
                record.setItemPricingSoftware(resultSet.getString("itemPricingSoftware"));
                record.setCalcuVolumeSoftware(resultSet.getString("calcuVolumeSoftware"));
                record.setMemberNum((resultSet.getInt("memberNum")));
                record.setTjNum(resultSet.getInt("tjNum"));
                record.setAzNum(resultSet.getInt("azNum"));
                record.setYlNum(resultSet.getInt("ylNum"));
                record.setJzxNum(resultSet.getInt("jzxNum"));
                record.setSzdlqlNum(resultSet.getInt("szdlqlNum"));
                record.setDlNum(resultSet.getInt("dlNum"));
                record.setDtNum(resultSet.getInt("dtNum"));
                record.setTlNum(resultSet.getInt("tlNum"));
                record.setTxNum(resultSet.getInt("txNum"));
                record.setTjjsbNum(resultSet.getInt("tjjsbNum"));
                record.setAzjsbNum(resultSet.getInt("azjsbNum"));
                record.setYljsbNum(resultSet.getInt("yljsbNum"));
                record.setJzxjsbNum(resultSet.getInt("jzxjsbNum"));
                record.setSzdlqljsbNum(resultSet.getInt("szdlqljsbNum"));
                record.setDljsbNum(resultSet.getInt("dljsbNum"));
                record.setDtjsbNum(resultSet.getInt("dtjsbNum"));
                record.setTljsbNum(resultSet.getInt("tljsbNum"));
                record.setTxjsbNum(resultSet.getInt("txjsbNum"));
                record.setWyfwNum(resultSet.getInt("wyfwNum"));
                record.setBafwNum(resultSet.getInt("bafwNum"));
                record.setBjfwNum(resultSet.getInt("bjfwNum"));
                record.setStcbfwNum(resultSet.getInt("stcbfwNum"));
                record.setLjqyfwNum(resultSet.getInt("ljqyfwNum"));
                record.setWlysfwNum(resultSet.getInt("wlysfwNum"));
                record.setSpzzfwNum(resultSet.getInt("spzzfwNum"));
                record.setGccgNum(resultSet.getInt("gccgNum"));
                record.setHwcgNum(resultSet.getInt("hwcgNum"));
                record.setFwcgNum(resultSet.getInt("fwcgNum"));
                record.setZfcgNum(resultSet.getInt("zfcgNum"));
                record.setJzxcswjNum(resultSet.getInt("jzxcswjNum"));
                record.setJzNum(resultSet.getInt("jzNum"));
                record.setQzNum(resultSet.getInt("qzNum"));
                record.setJzSpaceTime(resultSet.getInt("jzSpaceTime"));
                record.setQzSpaceTime(resultSet.getInt("qzSpaceTime"));
                record.setLessThan3WorkYears(resultSet.getInt("lessThan3WorkYears"));
                record.setMoreThan3WorkYears(resultSet.getInt("moreThan3WorkYears"));
                record.setMoreThan5WorkYears(resultSet.getInt("moreThan5WorkYears"));
                record.setBusinessCertImgs(resultSet.getString("businessCertImgs"));
                record.setIdCardCertImgs(resultSet.getString("idCardCertImgs"));
                record.setEngineerCertImgs(resultSet.getString("engineerCertImgs"));
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
    public int insertEngineerPerson(FramePeopleEngineerPerson record) {
        int flag=0;
        Connection conn = JdbcUtils.getConn();
        //SQL语句
        String sql = "INSERT INTO Frame_People_Engineer_Person(rowGuid,userGuid,engineerName,majorTypes,partJob,certImgs,canUploadElecBidding,canUseBim,canLocalCheck,canFieldCheck,canStamp,technologyLevel,workYears,spaceTime,companyType,itemPricingSoftware,calcuVolumeSoftware,projectTypes,serviceTypes,purchaseTypes,createTime,modifyTime,canDoArea)" +
                " VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?) ";
        try {
            PreparedStatement preparedStatement=conn.prepareStatement(sql);
            System.out.println(record.getRowGuid());
            preparedStatement.setString(1,record.getRowGuid());
            preparedStatement.setString(2,record.getUserGuid());
            preparedStatement.setString(3,record.getEngineerName());
            preparedStatement.setString(4,record.getMajorTypes());
            preparedStatement.setString(5,record.getPartJob());
            preparedStatement.setString(6,record.getCertImgs());
            preparedStatement.setInt(7,record.getCanUploadElecBidding());
            preparedStatement.setInt(8,record.getCanUseBim());
            preparedStatement.setInt(9,record.getCanLocalCheck());
            preparedStatement.setInt(10,record.getCanFieldCheck());
            preparedStatement.setInt(11,record.getCanStamp());
            preparedStatement.setInt(12,record.getTechnologyLevel());
            preparedStatement.setInt(13,record.getWorkYears());
            preparedStatement.setInt(14,record.getSpaceTime());
            preparedStatement.setString(15,record.getCompanyType());
            preparedStatement.setString(16,record.getItemPricingSoftware());
            preparedStatement.setString(17,record.getCalcuVolumeSoftware());
            preparedStatement.setString(18,record.getProjectTypes());
            preparedStatement.setString(19,record.getServiceTypes());
            preparedStatement.setString(20,record.getPurchaseTypes());
            preparedStatement.setString(21,record.getCreateTime());
            preparedStatement.setString(22,record.getModifyTime());
            preparedStatement.setString(23,record.getCanDoArea());
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
    public int insertEngineerTeam(FramePeopleEngineerTeam record) {
        int flag=0;
        Connection conn = JdbcUtils.getConn();
        //SQL语句
        String sql = "INSERT INTO Frame_People_Engineer_Team(rowGuid,userGuid,engineerName,memberNum,tjNum,azNum,ylNum,jzxNum,szdlqlNum,dlNum,dtNum,tlNum,txNum,tjjsbNum,azjsbNum,yljsbNum,jzxjsbNum,szdlqljsbNum,dljsbNum,dtjsbNum,tljsbNum,txjsbNum,wyfwNum,bafwNum,bjfwNum,stcbfwNum,ljqyfwNum,wlysfwNum,spzzfwNum,gccgNum,hwcgNum,fwcgNum,zfcgNum,jzxcswjNum,jznum,qzNum,jzSpaceTime,qzSpaceTime,lessThan3WorkYears,moreThan3WorkYears,moreThan5WorkYears,canDoArea,businessCertImgs,idCardCertImgs,engineerCertImgs,canUseBim,canUploadElecBidding,canLocalCheck,canFieldCheck,technologyLevel,itemPricingSoftware,calcuVolumeSoftware,createTime,modifyTime)" +
                " VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?) ";
        try {
            PreparedStatement preparedStatement=conn.prepareStatement(sql);
            System.out.println(record.getRowGuid());
            preparedStatement.setString(1,record.getRowGuid());
            preparedStatement.setString(2,record.getUserGuid());
            preparedStatement.setString(3,record.getEngineerName());
            preparedStatement.setInt(4,record.getMemberNum());
            preparedStatement.setInt(5,record.getTjNum());
            preparedStatement.setInt(6,record.getAzNum());
            preparedStatement.setInt(7,record.getYlNum());
            preparedStatement.setInt(8,record.getJzxNum());
            preparedStatement.setInt(9,record.getSzdlqlNum());
            preparedStatement.setInt(10,record.getDlNum());
            preparedStatement.setInt(11,record.getDtNum());
            preparedStatement.setInt(12,record.getTlNum());
            preparedStatement.setInt(13,record.getTxNum());
            preparedStatement.setInt(14,record.getTjjsbNum());
            preparedStatement.setInt(15,record.getAzjsbNum());
            preparedStatement.setInt(16,record.getYljsbNum());
            preparedStatement.setInt(17,record.getJzxjsbNum());
            preparedStatement.setInt(18,record.getSzdlqljsbNum());
            preparedStatement.setInt(19,record.getDljsbNum());
            preparedStatement.setInt(20,record.getDtjsbNum());
            preparedStatement.setInt(21,record.getTljsbNum());
            preparedStatement.setInt(22,record.getTxjsbNum());
            preparedStatement.setInt(23,record.getWyfwNum());
            preparedStatement.setInt(24,record.getBafwNum());
            preparedStatement.setInt(25,record.getBjfwNum());
            preparedStatement.setInt(26,record.getStcbfwNum());
            preparedStatement.setInt(27,record.getLjqyfwNum());
            preparedStatement.setInt(28,record.getWlysfwNum());
            preparedStatement.setInt(29,record.getSpzzfwNum());
            preparedStatement.setInt(30,record.getGccgNum());
            preparedStatement.setInt(31,record.getHwcgNum());
            preparedStatement.setInt(32,record.getFwcgNum());
            preparedStatement.setInt(33,record.getZfcgNum());
            preparedStatement.setInt(34,record.getJzxcswjNum());
            preparedStatement.setInt(35,record.getJzNum());
            preparedStatement.setInt(36,record.getQzNum());
            preparedStatement.setInt(37,record.getJzSpaceTime());
            preparedStatement.setInt(38,record.getQzSpaceTime());
            preparedStatement.setInt(39,record.getLessThan3WorkYears());
            preparedStatement.setInt(40,record.getMoreThan3WorkYears());
            preparedStatement.setInt(41,record.getMoreThan5WorkYears());
            preparedStatement.setString(42,record.getCanDoArea());
            preparedStatement.setString(43,record.getBusinessCertImgs());
            preparedStatement.setString(44,record.getIdCardCertImgs());
            preparedStatement.setString(45,record.getEngineerCertImgs());
            preparedStatement.setInt(46,record.getCanUseBim());
            preparedStatement.setInt(47,record.getCanUploadElecBidding());
            preparedStatement.setInt(48,record.getCanLocalCheck());
            preparedStatement.setInt(49,record.getCanFieldCheck());
            preparedStatement.setInt(50,record.getTechnologyLevel());
            preparedStatement.setString(51,record.getItemPricingSoftware());
            preparedStatement.setString(52,record.getCalcuVolumeSoftware());
            preparedStatement.setString(53,record.getCreateTime());
            preparedStatement.setString(54,record.getModifyTime());
            //执行语句，得到结果集
            preparedStatement.execute();
            flag=1;
        } catch (SQLException e1) {
            System.out.println(e1);
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
    public int updateEngineerPerson(FramePeopleEngineerPerson record) {
        int flag=0;
        Connection conn = JdbcUtils.getConn();
        //SQL语句
        String sql = "update Frame_People_Engineer_Person set engineerName=?,majorTypes=?,partJob=?,certImgs=?,canUploadElecBidding=?,canUseBim=?,canLocalCheck=?,canFieldCheck=?,canStamp=?,technologyLevel=?,workYears=?,spaceTime=?,companyType=?,itemPricingSoftware=?,calcuVolumeSoftware=?,projectTypes=?,serviceTypes=?,purchaseTypes=?,modifyTime=?,canDoArea=? where rowGuid=?";
        try {
            PreparedStatement preparedStatement=conn.prepareStatement(sql);
            preparedStatement.setString(1,record.getEngineerName());
            preparedStatement.setString(2,record.getMajorTypes());
            preparedStatement.setString(3,record.getPartJob());
            preparedStatement.setString(4,record.getCertImgs());
            preparedStatement.setInt(5,record.getCanUploadElecBidding());
            preparedStatement.setInt(6,record.getCanUseBim());
            preparedStatement.setInt(7,record.getCanLocalCheck());
            preparedStatement.setInt(8,record.getCanFieldCheck());
            preparedStatement.setInt(9,record.getCanStamp());
            preparedStatement.setInt(10,record.getTechnologyLevel());
            preparedStatement.setInt(11,record.getWorkYears());
            preparedStatement.setInt(12,record.getSpaceTime());
            preparedStatement.setString(13,record.getCompanyType());
            preparedStatement.setString(14,record.getItemPricingSoftware());
            preparedStatement.setString(15,record.getCalcuVolumeSoftware());
            preparedStatement.setString(16,record.getProjectTypes());
            preparedStatement.setString(17,record.getServiceTypes());
            preparedStatement.setString(18,record.getPurchaseTypes());
            preparedStatement.setString(19,record.getModifyTime());
            preparedStatement.setString(20,record.getCanDoArea());
            preparedStatement.setString(21,record.getRowGuid());
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
    public int updateEngineerTeam(FramePeopleEngineerTeam record) {
        int flag=0;
        Connection conn = JdbcUtils.getConn();
        //SQL语句
        String sql = "update Frame_People_Engineer_Team set engineerName=?,memberNum=?,tjNum=?,azNum=?,ylNum=?,jzxNum=?,szdlqlNum=?,dlNum=?,dtNum=?,tlNum=?,txNum=?,tjjsbNum=?,azjsbNum=?,yljsbNum=?,jzxjsbNum=?,szdlqljsbNum=?,dljsbNum=?,dtjsbNum=?,tljsbNum=?,txjsbNum=?,wyfwNum=?,bafwNum=?,bjfwNum=?,stcbfwNum=?,ljqyfwNum=?,wlysfwNum=?,spzzfwNum=?,gccgNum=?,hwcgNum=?,fwcgNum=?,zfcgNum=?,jzxcswjNum=?,jzNum=?,qzNum=?,jzSpaceTime=?,qzSpaceTime=?,lessThan3WorkYears=?,moreThan3WorkYears=?,moreThan5WorkYears=?,canDoArea=?,businessCertImgs=?,idCardCertImgs=?,engineerCertImgs=?,canUseBim=?,canUploadElecBidding=?,canLocalCheck=?,canFieldCheck=?,technologyLevel=?,itemPricingSoftware=?,calcuVolumeSoftware=?,modifyTime=? where rowGuid=?";
        try {
            PreparedStatement preparedStatement=conn.prepareStatement(sql);
            preparedStatement.setString(1,record.getEngineerName());
            preparedStatement.setInt(2,record.getMemberNum());
            preparedStatement.setInt(3,record.getTjNum());
            preparedStatement.setInt(4,record.getAzNum());
            preparedStatement.setInt(5,record.getYlNum());
            preparedStatement.setInt(6,record.getJzxNum());
            preparedStatement.setInt(7,record.getSzdlqlNum());
            preparedStatement.setInt(8,record.getDlNum());
            preparedStatement.setInt(9,record.getDtNum());
            preparedStatement.setInt(10,record.getTlNum());
            preparedStatement.setInt(11,record.getTxNum());
            preparedStatement.setInt(12,record.getTjjsbNum());
            preparedStatement.setInt(13,record.getAzjsbNum());
            preparedStatement.setInt(14,record.getYljsbNum());
            preparedStatement.setInt(15,record.getJzxjsbNum());
            preparedStatement.setInt(16,record.getSzdlqljsbNum());
            preparedStatement.setInt(17,record.getDljsbNum());
            preparedStatement.setInt(18,record.getDtjsbNum());
            preparedStatement.setInt(19,record.getTljsbNum());
            preparedStatement.setInt(20,record.getTxjsbNum());
            preparedStatement.setInt(21,record.getWyfwNum());
            preparedStatement.setInt(22,record.getBafwNum());
            preparedStatement.setInt(23,record.getBjfwNum());
            preparedStatement.setInt(24,record.getStcbfwNum());
            preparedStatement.setInt(25,record.getLjqyfwNum());
            preparedStatement.setInt(26,record.getWlysfwNum());
            preparedStatement.setInt(27,record.getSpzzfwNum());
            preparedStatement.setInt(28,record.getGccgNum());
            preparedStatement.setInt(29,record.getHwcgNum());
            preparedStatement.setInt(30,record.getFwcgNum());
            preparedStatement.setInt(31,record.getZfcgNum());
            preparedStatement.setInt(32,record.getJzxcswjNum());
            preparedStatement.setInt(33,record.getJzNum());
            preparedStatement.setInt(34,record.getQzNum());
            preparedStatement.setInt(35,record.getJzSpaceTime());
            preparedStatement.setInt(36,record.getQzSpaceTime());
            preparedStatement.setInt(37,record.getLessThan3WorkYears());
            preparedStatement.setInt(38,record.getMoreThan3WorkYears());
            preparedStatement.setInt(39,record.getMoreThan5WorkYears());
            preparedStatement.setString(40,record.getCanDoArea());
            preparedStatement.setString(41,record.getBusinessCertImgs());
            preparedStatement.setString(42,record.getIdCardCertImgs());
            preparedStatement.setString(43,record.getEngineerCertImgs());
            preparedStatement.setInt(44,record.getCanUseBim());
            preparedStatement.setInt(45,record.getCanUploadElecBidding());
            preparedStatement.setInt(46,record.getCanLocalCheck());
            preparedStatement.setInt(47,record.getCanFieldCheck());
            preparedStatement.setInt(48,record.getTechnologyLevel());
            preparedStatement.setString(49,record.getItemPricingSoftware());
            preparedStatement.setString(50,record.getCalcuVolumeSoftware());
            preparedStatement.setString(51,record.getModifyTime());
            preparedStatement.setString(52,record.getRowGuid());
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
    public int insert(FramePeople record) {
        int flag=0;
        Connection conn = JdbcUtils.getConn();
        //SQL语句
        String sql = "INSERT INTO Frame_People(rowGuid,phone,usertype,dealNum,dealMoney,iconurl,familiar,areaPro,subTool,isDS,isPro,superDes,registerTime,password,FamiliarChina,openId,userFlag,level,expireTime,reward,engineerType,parentGuid)" +
                " VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?) ";
        try {
            PreparedStatement preparedStatement=conn.prepareStatement(sql);
            System.out.println(record.getRowGuid());
            System.out.println(record.getUserFlag());
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
            preparedStatement.setInt(18,record.getLevel());
            preparedStatement.setString(19,record.getExpireTime());
            preparedStatement.setDouble(20,record.getReward());
            preparedStatement.setInt(21,record.getEngineerType());
            preparedStatement.setString(22,record.getParentGuid());
            //执行语句，得到结果集
            preparedStatement.execute();
            flag=1;
        } catch (SQLException e1) {
            System.out.println(e1);
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
        String sql = "update Frame_People set phone=?,usertype=?,dealNum=?,dealMoney=?,iconurl=?,familiar=?,areaPro=?,subTool=?,isDS=?,isPro=?,superDes=?,password=?,familiarChina=?,openId=?,nickName=?,avatarUrl=?,gender=?,country=?,province=?,city=?,userFlag=?,level=?,expireTime=?,reward=?,engineerType=?,finishNum=?,deposit=?,depositTime=?,remainingSum=?,isNewUser=?,engineerShareNewUserCouponCount=?,customShareNewUserCouponCount=?,customAddProjectCouponCount=?,shareCouponCount=?,customIntegralAmount=?,customLastSignInTime=?,engineerIntegralAmount=?,engineerLastSignInTime=?,customIntegralConvertAmount=?,engineerIntegralConvertAmount=? where rowguid=?";
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
            preparedStatement.setInt(22,record.getLevel());
            preparedStatement.setString(23,record.getExpireTime());
            preparedStatement.setDouble(24,record.getReward());
            preparedStatement.setInt(25,record.getEngineerType());
            preparedStatement.setInt(26,record.getFinishNum());
            preparedStatement.setDouble(27,record.getDeposit());
            preparedStatement.setString(28,record.getDepositTime());
            preparedStatement.setDouble(29,record.getRemainingSum());
            preparedStatement.setInt(30,record.getIsNewUser());
            preparedStatement.setInt(31,record.getEngineerShareNewUserCouponCount());
            preparedStatement.setInt(32,record.getCustomShareNewUserCouponCount());
            preparedStatement.setInt(33,record.getCustomAddProjectCouponCount());
            preparedStatement.setInt(34,record.getShareCouponCount());
            preparedStatement.setInt(35,record.getCustomIntegralAmount());
            preparedStatement.setString(36,record.getCustomLastSignInTime());
            preparedStatement.setInt(37,record.getEngineerIntegralAmount());
            preparedStatement.setString(38,record.getEngineerLastSignInTime());
            preparedStatement.setInt(39,record.getCustomIntegralConvertAmount());
            preparedStatement.setInt(40,record.getEngineerIntegralConvertAmount());
            preparedStatement.setString(41,record.getRowGuid());
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
                record.setLevel(resultSet.getInt("level"));
                record.setExpireTime(resultSet.getString("expireTime"));
                record.setReward(resultSet.getDouble("reward"));
                record.setFinishNum(resultSet.getInt("finishNum"));
                record.setDeposit(resultSet.getDouble("deposit"));
                record.setRemainingSum(resultSet.getDouble("remainingSum"));
                record.setDepositTime(resultSet.getString("depositTime"));
                record.setUserFlag(resultSet.getInt("userFlag"));
                record.setEngineerType(resultSet.getInt("engineerType"));
                record.setParentGuid(resultSet.getString("parentGuid"));
                record.setIsNewUser(resultSet.getInt("isNewUser"));
                record.setEngineerShareNewUserCouponCount(resultSet.getInt("engineerShareNewUserCouponCount"));
                record.setCustomAddProjectCouponCount(resultSet.getInt("customAddProjectCouponCount"));
                record.setCustomShareNewUserCouponCount(resultSet.getInt("customShareNewUserCouponCount"));
                record.setShareCouponCount(resultSet.getInt("shareCouponCount"));
                record.setCustomIntegralAmount(resultSet.getInt("customIntegralAmount"));
                record.setCustomLastSignInTime(resultSet.getString("customLastSignInTime"));
                record.setEngineerIntegralAmount(resultSet.getInt("engineerIntegralAmount"));
                record.setEngineerLastSignInTime(resultSet.getString("engineerLastSignInTime"));
                record.setCustomIntegralConvertAmount(resultSet.getInt("customIntegralConvertAmount"));
                record.setEngineerIntegralConvertAmount(resultSet.getInt("engineerIntegralConvertAmount"));
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
