package com.zhumingwei.bond.service;

import com.zhumingwei.bond.entity.Account;
import com.zhumingwei.bond.entity.User;

public interface UserService {
    int add(User user);

    int deleteById(User user);

    //修改用户信息
    int updateUser(User user);

    //获取用户信息
    User queryById(int id);

    //注册
    int register(Account account);
    //登录获取简单基本数据
    Account getAccountByPnumPwd(String pnum, String pwd);
    Account getAccountBypnum(String pnum);
    Account getAccountByuid(int uid);
    //查询账户信息（）
    Account getAccount(int id);
    //注销账户（解绑uid）
//    int deleteAccount(Account account);

    boolean checkRegisterData(Account account);

    int updatePassword(Account account);

}
