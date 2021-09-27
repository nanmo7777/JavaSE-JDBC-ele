package com.neu.ele.dao;

import com.neu.ele.po.Business;

import java.util.List;

/**
 * @author 林逸科技
 * 商家相关操作接口
 */
public interface BusinessDao {
    // 添加商家方法的接口
    public int saveBusiness(String businessName);
    // 查询商家的方法
    public List<Business> listBusiness(String businessName, String businessAddress);
    // 删除商家的方法
    public boolean removeBusiness(int businessId);
    // 根据商家ID和商家密码查询商家的方法
    public Business getBusinessByIdByPass(Integer businessId,String password);
    // 根据商家ID查询商家的方法
    public Business getBusinessById(Integer businessId);
    // 根据商家对象更新商家的方法
    public boolean updateBusiness(Business business);
    // 根据商家ID更新商家密码的方法
    public boolean updateBusinessByPassword(Integer businessId,String password);
}
