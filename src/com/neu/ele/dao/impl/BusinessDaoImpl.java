package com.neu.ele.dao.impl;

import com.neu.ele.dao.BusinessDao;
import com.neu.ele.po.Business;
import com.neu.ele.tools.JDBCTool;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author 林逸科技
 * 商家相关接口的实现类
 */
public class BusinessDaoImpl implements BusinessDao {
    // 数据库链接对象
    private Connection conn = null;
    // sql 执行器
    private PreparedStatement ps = null;
    // 结果集对象
    private ResultSet rs = null;

    /**
     * 方法名: saveBusiness
     * 修饰符:无修饰符
     * 范围修饰符: public
     * 返回值类型: int
     * 参数: String businessName(商家名称)
     * 添加一个商家,并返回商家ID,如果商家ID为空时返回-1(添加错误)
     */
    @Override
    public int saveBusiness(String businessName) {
        // 定义初始商家ID
        int businessId = -1;
        // 写SQL语句
        String sql = "insert into business(businessName,password) values(?,'123456')";
        // 获取数据库链接对象
        conn = JDBCTool.getConnection();
        try {
            // 获取 SQL 执行器,并设置返回自增长列值
            ps = conn.prepareStatement(sql,PreparedStatement.RETURN_GENERATED_KEYS);
            // 设置 SQL 语句中的参数
            ps.setString(1,businessName);
            // 执行SQL语句
            ps.executeUpdate();
            // 获取自增长列值
            rs = ps.getGeneratedKeys();
            // 如果结果集不为空
            if (rs.next()){
                // 在结果集内取得商家ID(businessId)
                businessId = rs.getInt(1);
            }
        } catch (SQLException e) {
            // 异常处理
            System.out.println("sql 执行器获取异常");
        } finally {
            // 关闭各对象
            JDBCTool.close(rs,ps,conn);
        }
        // 返回商家ID
        return businessId;
    }

    /**
     * 方法名: listBusiness
     * 修饰符:无修饰符
     * 范围修饰符: public
     * 返回值类型: List<Business>(商家列表)
     * 参数: String businessName(商家名), String businessAddress(商家地址)
     * 取得某地的某个商家,返回值为集合,因为考虑到某地有多个相同的商家
     */
    @Override
    public List<Business> listBusiness(String businessName, String businessAddress) {
        // 创建商家列表
        List<Business> arr = new ArrayList<>();
        // 取得数据库连接对象
        conn = JDBCTool.getConnection();
        // 写 sql 语句
        StringBuffer sql = new StringBuffer("select * from business where 1=1 ");
        // 如果传进来的参数不为空
        if (businessName!=null&&!businessName.equals("")){
            // 将模糊查询添加到 sql 语句
            sql.append(" and businessName like '%" + businessName + "%'");
        }
        if (businessAddress!=null&&!businessAddress.equals("")){
            // 将模糊查询条件2添加到 sql 语句
            sql.append(" and businessAddress like '%" + businessAddress + "%'");
        }
        try {
            // 加载 SQL 执行器
            ps = conn.prepareStatement(sql.toString());
            // 取得结果集
            rs = ps.executeQuery();
            // 判断结果集不为空
            while (rs.next()){
                // 构造 Business 对象
                Business business = new Business();
                // 取得并设置 Business 对象的商家ID
                business.setBusinessId(rs.getInt("businessId"));
                // 取得并设置 Business 对象的商家密码
                business.setPassword(rs.getString("password"));
                // 取得并设置 Business 对象的商家名
                business.setBusinessName(rs.getString("businessName"));
                // 取得并设置 Business 对象的商家地址
                business.setBusinessAddress(rs.getString("businessAddress"));
                // 取得并设置 Business 对象的商家介绍
                business.setBusinessExplain(rs.getString("businessExplain"));
                // 取得并设置 Business 对象的商家起送费
                business.setStarPrice(rs.getDouble("starPrice"));
                // 取得并设置 Business 对象的商家配送费
                business.setDeliveryPrice(rs.getDouble("deliveryPrice"));
                // 将 Business 对象添加到集合内
                arr.add(business);
            }
        } catch (SQLException e) {
            // 异常处理
            System.out.println("sql 执行器获取异常");
        }finally {
            // 关闭各对象
            JDBCTool.close(rs,ps,conn);
        }
        // 返回商家集合
        return arr;
    }

    /**
     * 方法名: removeBusiness
     * 修饰符:无修饰符
     * 范围修饰符: public
     * 返回值类型: boolean(成功或失败)
     * 参数: int businessId(商家ID)
     * 通过商家ID删除某个商家(较危险,注意控制使用权限)
     */
    @Override
    public boolean removeBusiness(int businessId) {
        // 创建是否添加成功的标志,默认为 false(失败)
        boolean flag = false;
        // 取得数据库链接对象
        conn = JDBCTool.getConnection();
        // 写根据商家ID删除食品的 SQL 语句,因为存在外键的关系,删除商家前应先删除外键(食品)
        String delFootSql = "delete from food where businessId=?";
        // 写根据商家ID删除商家的 SQL 语句
        String delBusinessSql = "delete from business where businessId=?";
        try {
            // 开启一个事务(关闭自动提交)
            conn.setAutoCommit(false);
            // 加载 SQL 执行器(删除食品)
            ps = conn.prepareStatement(delFootSql);
            // 设置 SQL 语句中的参数
            ps.setInt(1,businessId);
            // 执行 SQL 语句
            ps.executeUpdate();
            // 加载 SQL 执行器(删除商家)
            ps = conn.prepareStatement(delBusinessSql);
            // 设置 SQL 语句中的参数
            ps.setInt(1,businessId);
            // 执行 SQL 语句,并取得执行后的返回结果(影响的行数),一般返回值>0即可说明执行(删除成功)
            int index = ps.executeUpdate();
            // 手动提交
            conn.commit();
            if (index>0){
                flag = true;
            }
        } catch (SQLException e) {
            try {
                // 异常处理,如果删除失败,尝试执行回滚
                conn.rollback();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            e.printStackTrace();
        }
        // 返回是否删除成功的标志
        return flag;
    }


    /**
     * 方法名: getBusinessByIdByPass
     * 修饰符:无修饰符
     * 范围修饰符: public
     * 返回值类型: Business(商家对象)
     * 参数: Integer businessId, String password(商家ID和密码)
     * 通过商家ID和密码查询某个商家(当商家各属性值为null时,说明查询失败)
     */
    @Override
    public Business getBusinessByIdByPass(Integer businessId, String password) {
        // 声明一个 Business 对象
        Business business = null;
        // 写 SQL 语句
        String sql = "select * from business where businessId=? and password=?";
        // 获取数据库链接对象
        conn = JDBCTool.getConnection();
        try {
            // 获取 SQL 执行器
            ps = conn.prepareStatement(sql);
            // 设置 SQL 语句中的参数
            ps.setInt(1,businessId);
            ps.setString(2,password);
            // 取得结果集
            rs = ps.executeQuery();
            // 判断结果集不为空
            if (rs.next()){
                // new 一个 Business 对象
                business = new Business();
                // 取得并设置 Business 对象的 ID
                business.setBusinessId(rs.getInt("businessId"));
                // 取得并设置 Business 对象的 密码
                business.setPassword(rs.getString("password"));
                // 取得并设置 Business 对象的 商家名
                business.setBusinessName(rs.getString("businessName"));
                // 取得并设置 Business 对象的 商家地址
                business.setBusinessAddress(rs.getString("businessAddress"));
                // 取得并设置 Business 对象的 商家介绍
                business.setBusinessExplain(rs.getString("businessExplain"));
                // 取得并设置 Business 对象的 起送费
                business.setStarPrice(rs.getDouble("starPrice"));
                // 取得并设置 Business 对象的 配送费
                business.setDeliveryPrice(rs.getDouble("deliveryPrice"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            // 关闭各项
            JDBCTool.close(rs, ps, conn);
        }
        // 返回 Business 对象
        return business;
    }
    /**
     * 方法名: getBusinessById
     * 修饰符:无修饰符
     * 范围修饰符: public
     * 返回值类型: Business(商家对象)
     * 参数: Integer businessId(商家ID)
     * 通过商家ID查询某个商家(当商家各属性值为null时,说明查询失败)
     */
    @Override
    public Business getBusinessById(Integer businessId) {
        // 声明一个 Business 对象
        Business business = null;
        // 写 SQL 语句
        String sql = "select * from business where businessId=?";
        // 获取数据库链接对象
        conn = JDBCTool.getConnection();
        try {
            // 取得 SQL 执行器
            ps = conn.prepareStatement(sql);
            // 设置 SQL 语句中的参数
            ps.setInt(1,businessId);
            // 取得结果集
            rs = ps.executeQuery();
            // 判断结果集不为空
            if (rs.next()){
                // new 一个 Business 对象
                business = new Business();
                // 取得并设置 Business 对象的 ID
                business.setBusinessId(rs.getInt("businessId"));
                // 取得并设置 Business 对象的 密码
                business.setPassword(rs.getString("password"));
                // 取得并设置 Business 对象的 商家名
                business.setBusinessName(rs.getString("businessName"));
                // 取得并设置 Business 对象的 商家地址
                business.setBusinessAddress(rs.getString("businessAddress"));
                // 取得并设置 Business 对象的 商家介绍
                business.setBusinessExplain(rs.getString("businessExplain"));
                // 取得并设置 Business 对象的 起送费
                business.setStarPrice(rs.getDouble("starPrice"));
                // 取得并设置 Business 对象的 配送费
                business.setDeliveryPrice(rs.getDouble("deliveryPrice"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            // 关闭各项
            JDBCTool.close(rs,ps,conn);
        }
        // 返回 Business 对象
        return business;
    }

    /**
     * 方法名: updateBusiness
     * 修饰符:无修饰符
     * 范围修饰符: public
     * 返回值类型: boolean(成功或失败)
     * 参数: Business business(商家对象)
     * 通过商家对象来更新某个商家的信息(成功返回true,反之false)
     */
    @Override
    public boolean updateBusiness(Business business) {
        // 创建是否更新成功的标志,默认为 false(失败)
        boolean flag = false;
        // 写 SQL 语句
        String sql = "update business set businessName=?,businessAddress=?,businessExplain=?,starPrice=?,deliveryPrice=? where businessId=?";
        // 获取数据库链接对象
        conn = JDBCTool.getConnection();
        try {
            // 获取 SQL 执行器
            ps = conn.prepareStatement(sql);
            // 设置 SQL 语句中的变量
            ps.setString(1, business.getBusinessName());
            // 设置 SQL 语句中的变量
            ps.setString(2, business.getBusinessAddress());
            // 设置 SQL 语句中的变量
            ps.setString(3, business.getBusinessExplain());
            // 设置 SQL 语句中的变量
            ps.setDouble(4, business.getStarPrice());
            // 设置 SQL 语句中的变量
            ps.setDouble(5, business.getDeliveryPrice());
            // 设置 SQL 语句中的变量
            ps.setInt(6, business.getBusinessId());
            // 执行 SQL 语句,并取得执行后的返回结果(影响的行数),一般返回值>0即可说明执行(修改成功)
            int index = ps.executeUpdate();
            if (index>0){
                // 如果大于0说明执行成功,将是否执行成功的标志(flag)修改为true
                flag = true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            // 关闭各项
            JDBCTool.close(rs,ps,conn);
        }
        // 返回是否执行成功的标志
        return flag;
    }

    /**
     * 方法名: updateBusinessByPassword
     * 修饰符:无修饰符
     * 范围修饰符: public
     * 返回值类型: boolean(成功或失败)
     * 参数: Integer businessId, String password(商家ID和密码)
     * 通过商家ID来更新商家密码(成功返回true,反之false)
     */
    @Override
    public boolean updateBusinessByPassword(Integer businessId, String password) {
        // 创建是否更新成功的标志,默认为 false(失败)
        boolean flag = false;
        // 写 SQL 语句
        String sql = "update business set password=? where businessId=?";
        // 获取数据库链接对象
        conn = JDBCTool.getConnection();
        try {
            // 取得 SQL 执行器
            ps = conn.prepareStatement(sql);
            // 设置 SQL 语句中的参数
            ps.setString(1, password);
            // 设置 SQL 语句中的参数
            ps.setInt(2, businessId);
            // 执行 SQL 语句,并取得执行后的返回结果(影响的行数),一般返回值>0即可说明执行(修改成功)
            int index = ps.executeUpdate();
            if (index>0){
                // 如果大于0说明执行成功,将是否执行成功的标志(flag)修改为true
                flag = true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            // 关闭各项
            JDBCTool.close(rs,ps,conn);
        }
        // 返回是否执行成功的标志
        return flag;
    }
}
