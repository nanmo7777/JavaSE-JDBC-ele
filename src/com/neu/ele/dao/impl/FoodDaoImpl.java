package com.neu.ele.dao.impl;

import com.neu.ele.dao.FoodDao;
import com.neu.ele.po.Food;
import com.neu.ele.tools.JDBCTool;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author 林逸科技
 * 食品相关接口的实现类
 */
public class FoodDaoImpl implements FoodDao {
    // 声明数据库链接对象
    private Connection conn = null;
    // 声明 SQL 执行器对象
    private PreparedStatement ps = null;
    // 声明结果集对象
    private ResultSet rs = null;

    /**
     * 方法名: listFoodByBusinessId
     * 修饰符:无修饰符
     * 范围修饰符: public
     * 返回值类型: List<Food>(食品列表)
     * 参数: Integer businessId(商家ID)
     * 根据商家ID查询商家所有的食品
     */
    @Override
    public List<Food> listFoodByBusinessId(Integer businessId) {
        // 声明食品列表
        List<Food> arr = new ArrayList<>();
        // 取得数据库链接对象
        conn = JDBCTool.getConnection();
        // 写 SQL 语句
        String sql = "select * from food where businessId=?";
        try {
            // 取得 SQL 执行器
            ps = conn.prepareStatement(sql);
            // 设置 SQL 语句中的参数
            ps.setInt(1,businessId);
            // 执行 SQL 语句并取得结果集
            rs = ps.executeQuery();
            // 判断结果集不为空
            while (rs.next()){
                // new 一个 Food 对象
                Food food = new Food();
                // 设置 Food 对象的 foodId 属性
                food.setFoodId(rs.getInt("foodId"));
                // 设置 Food 对象的 foodName 属性
                food.setFoodName(rs.getString("foodName"));
                // 设置 Food 对象的 foodExplain 属性
                food.setFoodExplain(rs.getString("foodExplain"));
                // 设置 Food 对象的 foodPrice 属性
                food.setFoodPrice(rs.getDouble("foodPrice"));
                // 设置 Food 对象的 businessId 属性
                food.setBusinessId(rs.getInt("businessId"));
                // 将赋完值的 Food 对象添加到食品列表
                arr.add(food);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            // 关闭各项
            JDBCTool.close(rs,ps,conn);
        }
        // 返回 Food 列表
        return arr;
    }

    /**
     * 方法名: saveFood
     * 修饰符:无修饰符
     * 范围修饰符: public
     * 返回值类型: boolean(成功/失败)
     * 参数: Food food(Food 对象)
     * 根据 Food 对象添加食品
     */
    @Override
    public boolean saveFood(Food food) {
        // 声明是否添加成功的标志,默认为false(失败)
        boolean flag = false;
        // 取得数据库链接对象
        conn = JDBCTool.getConnection();
        // 写 SQL 语句
        String sql = "insert into food values(null,?,?,?,?)";
        try {
            // 取得 SQL 执行器
            ps = conn.prepareStatement(sql);
            // 设置 SQL 语句中的参数
            ps.setString(1, food.getFoodName());
            // 设置 SQL 语句中的参数
            ps.setString(2, food.getFoodExplain());
            // 设置 SQL 语句中的参数
            ps.setDouble(3, food.getFoodPrice());
            // 设置 SQL 语句中的参数
            ps.setInt(4, food.getBusinessId());
            // 执行 SQL 语句,并取得执行后的返回结果(影响的行数),一般返回值>0即可说明执行(添加成功)
            int index = ps.executeUpdate();
            // 判断是否成功
            if (index>0){
                // 将是否成功标志设置为true
                flag = true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            // 关闭各对象
            JDBCTool.close(rs,ps,conn);
        }
        // 返回是否成功的标志
        return flag;
    }

    /**
     * 方法名: getFoodById
     * 修饰符:无修饰符
     * 范围修饰符: public
     * 返回值类型: boolean(成功/失败)
     * 参数: Integer foodId(食品ID)
     * 根据 Food IO 查询食品详细信息
     */
    @Override
    public Food getFoodById(Integer foodId) {
        // 声明食品对象
        Food food = null;
        // 取得数据库链接对象
        conn = JDBCTool.getConnection();
        // 写 SQL 语句
        String sql = "select * from food where foodId=?";
        try {
            // 获取 SQL 执行器
            ps = conn.prepareStatement(sql);
            // 设置 SQL 语句中的参数
            ps.setInt(1,foodId);
            // 执行 SQL 语句并取得结果集
            rs = ps.executeQuery();
            // 判断结果集不为空
            if (rs.next()){
                // new 一个 Food 对象
                food = new Food();
                // 设置 Food 对象的 foodId 属性
                food.setFoodId(rs.getInt("foodId"));
                // 设置 Food 对象的 foodName 属性
                food.setFoodName(rs.getString("foodName"));
                // 设置 Food 对象的 foodExplain 属性
                food.setFoodExplain(rs.getString("foodExplain"));
                // 设置 Food 对象的 foodPrice 属性
                food.setFoodPrice(rs.getDouble("foodPrice"));
                // 设置 Food 对象的 businessId 属性
                food.setBusinessId(rs.getInt("businessId"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            // 关闭各对象
            JDBCTool.close(rs,ps,conn);
        }
        // 返回(查询到的) Food 对象
        return food;
    }

    /**
     * 方法名: updateFood
     * 修饰符:无修饰符
     * 范围修饰符: public
     * 返回值类型: boolean(成功/失败)
     * 参数: Food food(食品对象)
     * 根据 Food 对象 更新食品信息
     */
    @Override
    public boolean updateFood(Food food) {
        // 声明是否添加成功的标志,默认为false(失败)
        boolean flag = false;
        // 取得数据库链接对象
        conn = JDBCTool.getConnection();
        // 写 SQL 语句
        String sql = "update food set foodName=?,foodExplain=?,foodPrice=? where foodId=?";
        try {
            // 取得 SQL 执行器
            ps = conn.prepareStatement(sql);
            // 设置 SQL 语句中的参数
            ps.setString(1, food.getFoodName());
            // 设置 SQL 语句中的参数
            ps.setString(2, food.getFoodExplain());
            // 设置 SQL 语句中的参数
            ps.setDouble(3, food.getFoodPrice());
            // 设置 SQL 语句中的参数
            ps.setInt(4, food.getFoodId());
            // 执行 SQL 语句,并取得执行后的返回结果(影响的行数),一般返回值>0即可说明执行(添加成功)
            int index = ps.executeUpdate();
            // 判断是否成功
            if (index>0){
                // 将是否成功标志设置为true
                flag = true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            // 关闭各对象
            JDBCTool.close(rs,ps,conn);
        }
        // 返回是否成功的标志
        return flag;
    }

    /**
     * 方法名: removeFood
     * 修饰符:无修饰符
     * 范围修饰符: public
     * 返回值类型: boolean(成功/失败)
     * 参数: Integer foodId(食品ID)
     * 根据 Food ID 删除食品信息
     */
    @Override
    public boolean removeFood(Integer foodId) {
        // 声明是否添加成功的标志,默认为false(失败)
        boolean flag = false;
        // 取得数据库链接对象
        conn = JDBCTool.getConnection();
        // 写 SQL 语句
        String sql = "delete from food where foodId=?";
        try {
            // 取得 SQL 执行器
            ps = conn.prepareStatement(sql);
            // 设置 SQL 语句中的参数
            ps.setInt(1,foodId);
            // 执行 SQL 语句,并取得执行后的返回结果(影响的行数),一般返回值>0即可说明执行(添加成功)
            int index = ps.executeUpdate();
            // 判断是否成功
            if (index>0){
                // 将是否成功标志设置为true
                flag = true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            // 关闭各对象
            JDBCTool.close(rs,ps,conn);
        }
        // 返回是否成功的标志
        return flag;
    }
}
