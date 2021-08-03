package com.lxh.rhjg.reports.impl;

import com.lxh.rhjg.entity.SMART_REPORTS;
import com.lxh.rhjg.reports.api.IReports;
import com.lxh.test.common.JdbcUtils;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

@Component
@Service
public class ReportsImpl  implements IReports {
    @Override
    public void InsertReports(SMART_REPORTS smartReports) {
        Connection conn = JdbcUtils.getConn();
        //SQL语句
        String sql = "INSERT INTO smart_reports(GUID,TITLE,CONTENT,LINK_TEL,LINK_EMAIL,DATATIME,CUST_ID,STATUS,SOLVE_DESC)" +
                " VALUES(?,?,?,?,?,?,?,?,?) ";
        try {
            PreparedStatement preparedStatement=conn.prepareStatement(sql);
            preparedStatement.setString(1,smartReports.getGUID());
            preparedStatement.setString(2,smartReports.getTITLE());
            preparedStatement.setString(3,smartReports.getCONTENT());
            preparedStatement.setString(4,smartReports.getLINK_TEL());
            preparedStatement.setString(5,smartReports.getLINK_EMAIL());
            preparedStatement.setString(6,smartReports.getDATATIME());
            preparedStatement.setString(7,smartReports.getCUST_ID());
            preparedStatement.setString(8,smartReports.getSTATUS());
            preparedStatement.setString(9,smartReports.getSOLVE_DESC());
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
