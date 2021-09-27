package com.neu.ele.tools;

import java.sql.*;

/**
 * @author 林逸科技
 * 该类用于Java链接mysql 返回数据库链接对象 关闭(释放)资源
 */
public class JDBCTool {

    // mysql 驱动程序名称
    private static final String DB_DRIVER = "com.mysql.cj.jdbc.Driver";
    // mysql 地址
    private static final String DB_HOST = "192.168.23.130";
    // mysql 端口号
    private static final String DB_PORT = "3306";
    // mysql 数据库名称
    private static final String DB_NAME = "ele";
    // mysql 用户名
    private static final String DB_USER = "ele";
    // mysql 密码
    private static final String DB_PWD = "ele";
    // mysql 最终url
    private static final String DB_URL = "jdbc:mysql://" + DB_HOST + ":" + DB_PORT + "/" + DB_NAME + "?useUnicode=true&characterEncoding=utf8";
    // 数据库链接对象
    private static Connection conn;

    /**
     * 方法名: getConnection
     * 参数: 无参数
     * 修饰符: static
     * 范围修饰符: public
     * 返回值类型: Connection
     * 调用该方法后,会返回数据库链接对象
     */
    public static Connection getConnection(){
        try {
            // 注册数据库驱动
            Class.forName(DB_DRIVER);
            // 创建数据库链接对象
            conn = DriverManager.getConnection(DB_URL,DB_USER,DB_PWD);
        } catch (ClassNotFoundException e) {
            // 异常处理
            System.out.println("数据库驱动注册异常");
        } catch (SQLException throwables) {
            // 异常处理
            System.out.println("数据库链接异常");
        }
        return conn;
    }

    /**
     *  方法名 close
     *  参数: ResultSet rs , PreparedStatement ps , Connection conn
     *  修饰符: static
     *  范围修饰符: public
     *  返回值类型: 无返回值
     *  该方法用于关闭(释放) ResultSet rs , PreparedStatement ps , Connection conn
     */
    public static void close(ResultSet rs , PreparedStatement ps , Connection conn){
        if (conn != null){
            try {
                conn.close();
            } catch (SQLException e) {
                System.out.println("Connection 关闭异常");
            }
        }
        if (rs != null){
            try {
                rs.close();
            } catch (SQLException e) {
                System.out.println("ResultSet 关闭异常");
            }
        }
        if (ps != null){
            try {
                ps.close();
            } catch (SQLException e) {
                System.out.println("PreparedStatement 关闭异常");
            }
        }
    }

    /**
     *  方法名 close
     *  参数: PreparedStatement ps , Connection conn
     *  修饰符: static
     *  范围修饰符: public
     *  返回值类型: 无返回值
     *  该方法用于关闭(释放) PreparedStatement ps , Connection conn
     */
    public static void close(PreparedStatement ps , Connection conn){
        if (conn != null){
            try {
                conn.close();
            } catch (SQLException e) {
                System.out.println("Connection 关闭异常");
            }
        }
        if (ps != null){
            try {
                ps.close();
            } catch (SQLException e) {
                System.out.println("PreparedStatement 关闭异常");
            }
        }
    }
}
