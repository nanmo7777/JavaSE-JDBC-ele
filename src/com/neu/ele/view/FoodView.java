package com.neu.ele.view;

import com.neu.ele.po.Food;

import java.util.List;

public interface FoodView {
    public List<Food> showFoodList(Integer businessId);
    public void saveFood(Integer businessId);
    public void updateFood(Integer businessId);
    public void removeFood(Integer businessId);
}
