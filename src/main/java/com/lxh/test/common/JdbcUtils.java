package com.lxh.test.common;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class JdbcUtils {

    //通过上面的工具就可以获取到properties文件中的键值从而可以加载驱动 获取链接 从而 可以增删改查
    private static Connection conn = null;

    public static Connection getConn(){
        PropertiesUtil.loadFile("jdbc.properties");
        String driver = PropertiesUtil.getPropertyValue("driver");
        String url = PropertiesUtil.getPropertyValue("url");
        String username  = PropertiesUtil.getPropertyValue("username");
        String password = PropertiesUtil.getPropertyValue("password");
        try {
            Class.forName(driver);
            conn = DriverManager.getConnection(url,username,password);


        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
            close();
        }
        return conn;
    }
    public static Connection getConn(String url,String username,String  password){
        PropertiesUtil.loadFile("jdbc.properties");
        String driver = PropertiesUtil.getPropertyValue("driver");
         url = PropertiesUtil.getPropertyValue(url);
         username  = PropertiesUtil.getPropertyValue(username);
         password = PropertiesUtil.getPropertyValue(password);
        try {
            Class.forName(driver);
            conn = DriverManager.getConnection(url,username,password);


        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
            close();
        }
        return conn;
    }
    public static void close(){
        try {
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
