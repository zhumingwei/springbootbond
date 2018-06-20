package com.zhumingwei.bond.dao;

import com.zhumingwei.bond.dao.base.config.resolve.DefaultEntityResolve;
import com.zhumingwei.bond.entity.User;
import com.zhumingwei.bond.tool.LogUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserDaoTest {
    @Autowired
    UserDao userDao;

    @Test
    public void add() {
        User user = new User();
        user.setCid(0);
        user.setNickname("朱大福");
        user.setUser_level(999);
        user.setUser_state(0);
        user.setCreatedate(user.now());
        user.setUpdatedate(user.now());
        userDao.add(user);
    }

    @Test
    public void setCreateUser(){

    }

//    @Test
//    public void deleteById() {
//        userDao.deleteById(101L);
//    }

    @Test
    public void updateUser() {
        User user = new User();
        user.setId(102);
        user.setCid(0);
        user.setNickname("朱大福123");
        user.setUser_level(9);
        user.setUser_state(1);
        user.setUpdatedate(user.now());
        user.setUpdateby(101);
        userDao.update(user);
    }

    @Test
    public void updateDateUser() {
    }

    @Test
    public void queryById() {
//        User user = userDao.queryByPrimary(101);
//        LogUtil.loge(user);
    }

    @Test
    public void releve(){
        new DefaultEntityResolve().resolveEntity(User.class);
    }
}