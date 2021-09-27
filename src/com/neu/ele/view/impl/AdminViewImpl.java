package com.neu.ele.view.impl;

import com.neu.ele.dao.AdminDao;
import com.neu.ele.dao.impl.AdminDaoImpl;
import com.neu.ele.po.Admin;
import com.neu.ele.view.AdminView;

import java.util.Scanner;

public class AdminViewImpl implements AdminView {
    private Scanner input = new Scanner(System.in);
    @Override
    public Admin login() {
        System.out.println("请输入管理员名称：");
        String adminName = input.next();
        System.out.println("请输入密码：");
        String password = input.next();
        AdminDao dao = new AdminDaoImpl();
        return dao.getAdminByNameByPass(adminName, password);
    }

}
