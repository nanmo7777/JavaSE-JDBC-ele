package com.neu.ele.dao.impl;

import com.neu.ele.dao.AdminDao;
import com.neu.ele.po.Admin;
import com.neu.ele.tools.JDBCTool;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AdminDaoImpl implements AdminDao {
    // 数据库链接对象
    private Connection conn = null;
    // sql 执行器
    private PreparedStatement ps = null;
    // 结果集对象
    private ResultSet rs = null;

    /**
     * 方法名: getAdminByNameByPass
     * 修饰符:无修饰符
     * 范围修饰符: public
     * 返回值类型: Admin
     * 参数: String adminName, String password(管理员账号密码)
     * 管理员凭账号密码登录,返回 Admin 对象,各项为空时证明登录失败
     */
    @Override
    public Admin getAdminByNameByPass(String adminName, String password) {
        // 声明一个 Admin 对象
        Admin admin = null;
        // 写 SQL 语句
        String sql = "select * from admin where adminName=? and password=?";
        // 取得数据库链接对象
        conn = JDBCTool.getConnection();
        try {
            // 获取 SQL 执行器
            ps = conn.prepareStatement(sql);
            // 设置 SQL 语句中的参数
            ps.setString(1, adminName);
            // 设置 SQL 语句中的参数
            ps.setString(2, password);
            // 执行 SQL 语句并取得结果集
            rs = ps.executeQuery();
            // 判断结果集不为空
            if (rs.next()){
                // 构造一个 Admin 对象
                admin = new Admin();
                // 设置 Admin 对象的 ID(ID是从结果集中取出来的)
                admin.setAdminId(rs.getInt("adminId"));
                // 设置 Admin 对象的 用户名(用户名是从结果集中取出来的)
                admin.setAdminName(rs.getString("adminName"));
                // 设置 Admin 对象的 密码(密码是从结果集中取出来的)
                admin.setPassword(rs.getString("password"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            //关闭各项
            JDBCTool.close(rs,ps,conn);
        }
        // 返回 Admin 对象
        return admin;
    }
}
