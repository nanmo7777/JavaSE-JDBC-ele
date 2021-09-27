package com.neu.ele.dao;
import com.neu.ele.po.Admin;

/**
 * @author 林逸科技
 * 管理员相关接口类
 */
public interface AdminDao {
    // 管理员凭账号密码登录的方法
    public Admin getAdminByNameByPass(String adminName, String password);
}
