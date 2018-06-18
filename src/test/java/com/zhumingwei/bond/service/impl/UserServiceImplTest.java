package com.zhumingwei.bond.service.impl;

import com.zhumingwei.bond.entity.Account;
import com.zhumingwei.bond.entity.User;
import com.zhumingwei.bond.service.UserService;
import com.zhumingwei.bond.tool.LogUtil;
import com.zhumingwei.bond.tool.EncryptUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserServiceImplTest {
    @Autowired
    UserService userService;

    @Test
    public void register() {
        User user = new User();
        user.setCid(0);
        user.setNickname("朱大福");
        user.setUser_level(999);
        user.setUser_state(0);
        user.setCreateby(101);
        user.setUpdateby(101);
        user.setAvatar("http://p9yjgmoug.bkt.clouddn.com/5000000025385-4KpbGt7bNRVkQcYvGRqV9VngvU_Z7rV_");

        Account account = new Account();
        account.setUserdetail(user);
        account.setPassword(EncryptUtil.getSaltMD5("zhumingwei"));
        account.setPhonenumber("17621791738");
        account.setCreateby(101);
        account.setUpdateby(101);
        userService.register(account);
    }


    @Test
    public void login() {
        Account account = userService.loginByPwd("17621791737","1234556");
        LogUtil.loge(account);
    }

    @Test
    public void updatePassword() {
        Account account = userService.loginByPwd("17621791737","zhumingwei");
        account.setPassword("1234556");
        userService.updatePassword(account);
    }

    @Test
    public void updateUser(){
        Account account = userService.loginByPwd("17621791737","1234556");
        User user = account.getUserdetail();
        user.setNickname("ye ggg");
        userService.updateUser(user);
    }

    

}