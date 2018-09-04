package com.zhumingwei.bond.dao;

import com.zhumingwei.bond.entity.MenuDetail;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.junit.Assert.*;

/**
 * @author zhumingwei
 * @date 2018/9/4 上午9:39
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class MenuDaoTest {
    @Autowired
    MenuDao menuDao;
    @Test
    public void getMenuDetailList() {
        List<MenuDetail> data= menuDao.getMenuDetailList(10,10);
        for (MenuDetail d:data){
            System.out.println(d.toString());
        }
    }
}