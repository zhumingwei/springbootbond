package com.zhumingwei.bond.service.impl;

import com.zhumingwei.bond.entity.User;
import com.zhumingwei.bond.service.UserService;
import com.zhumingwei.bond.tool.LogUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserServiceImplTest {

    @Autowired
    UserService userService;

    @Test
    public void add() {

    }

    @Test
    public void deleteById() {
    }

    @Test
    public void updateUser() {
    }

    @Test
    public void updateDateUser() {
    }

    @Test
    public void queryById() {
        User u = userService.queryById(101);
        LogUtil.loge(u);
    }
}