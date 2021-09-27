package com.neu.ele.dao;

import com.neu.ele.po.Food;
import java.util.List;

/**
 * @author 林逸科技
 * 食品相关操作接口
 */
public interface FoodDao {
    // 列出所有的食品的接口
    public List<Food> listFoodByBusinessId(Integer businessId);
    // 根据 Food 对象添加食品的方法
    public boolean saveFood(Food food);
    // 根据食品的ID查询食品的详细信息
    public Food getFoodById(Integer foodId);
    // 根据 Food 对象 更新食品信息
    public boolean updateFood(Food food);
    // 根据 Food ID删除食品
    public boolean removeFood(Integer foodId);
}
