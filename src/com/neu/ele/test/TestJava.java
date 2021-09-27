package com.neu.ele.test;

import com.neu.ele.dao.BusinessDao;
import com.neu.ele.dao.impl.BusinessDaoImpl;
import com.neu.ele.po.Business;
import org.junit.Test;

import java.util.List;

public class TestJava {
    @Test
    public void TestBus(){
        BusinessDao bd = new BusinessDaoImpl();
        /*int id = bdi.saveBusiness("万家饺子");
        System.out.println(id);*/
        List<Business> list = bd.listBusiness(null,null);
        for (Business l : list
             ) {
                System.out.println(l);
        }
    }
}
