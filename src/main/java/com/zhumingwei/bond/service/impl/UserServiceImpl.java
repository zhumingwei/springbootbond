package com.zhumingwei.bond.service.impl;

import com.zhumingwei.bond.dao.AccountDao;
import com.zhumingwei.bond.dao.UserDao;
import com.zhumingwei.bond.entity.Account;
import com.zhumingwei.bond.entity.User;
import com.zhumingwei.bond.service.UserService;
import com.zhumingwei.bond.tool.EncryptUtil;
import com.zhumingwei.bond.tool.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserDao userDao;

    @Autowired
    AccountDao accountDao;

    @Transactional
    @Override
    public int add(User user) {
        //新增用户
        user.setUpdatedate(user.now());
        user.setCreatedate(user.now());
        int i = userDao.add(user);
        return i;
    }

    @Transactional
    @Override
    public int deleteById(User user) {
        user.setUpdatedate(user.now());
        user.setIs_delete(User.DELETE_TRUE);
        int i = userDao.update(user);
        return i;
    }

    @Transactional
    @Override
    public int updateUser(User user) {
        user.setUpdatedate(user.now());
        int i = userDao.update(user);
        return i;
    }

    @Override
    public User queryById(int id) {
        User user = new User();
        user.setId(id);
        user = userDao.queryById(user);
        if (user.getIs_delete() == User.DELETE_TRUE) {
            return null;
        }
        return user;
    }

    @Transactional
    @Override
    public int register(Account account) {
        //注册必须有手机号
        //by不能为null
        if (!checkRegisterData(account)) {
            return 0;
        }
        Date now = account.now();
        account.getUserdetail().setCreatedate(now);
        account.getUserdetail().setUpdatedate(now);
        userDao.add(account.getUserdetail());
        account.setUid(account.getUserdetail().getId());
        account.setCreatedate(now);
        account.setUpdatedate(account.now());
        int i = accountDao.add(account);
        return i;
    }

    @Override
    public boolean checkRegisterData(Account account) {
        if (StringUtil.isEmpty(account.getPhonenumber())) {
            //没有手机号
            return false;
        } else if ( account.getUserdetail() == null ) {
            //各种参数不正确
            return false;
        } else {
            Account ac = accountDao.getAccountByPnum(account.getPhonenumber());
            if (ac != null) {
                //手机号 已经被注册
                return false;
            } else {
                return true;
            }
        }
    }


    @Override
    public int updatePassword(Account account) {
        //参数必须有id和password和updateby
        if (account.getId() == 0 || StringUtil.isEmpty(account.getPassword()) || account.getUpdateby() == 0) {
            return 0;
        }
        Account a = accountDao.queryById(account);
        if (a==null){
            return 0;
        }
        a.setUpdateby(account.getUpdateby());
        a.setPassword(EncryptUtil.getSaltMD5(account.getPassword()));
        return accountDao.update(a);
    }

    @Transactional
    @Override
    public Account getAccountByPnumPwd(String pnum, String pwd) {
        Account account = accountDao.getAccountByPnumPwd(pnum, EncryptUtil.getSaltMD5(pwd));
        if (account!=null) {
            User user = new User();
            user.setId(account.getUid());
            user = userDao.queryById(user);
            account.setUserdetail(user);
        }
        return account;
    }

    @Transactional
    @Override
    public Account getAccountBypnum(String pnum) {
        Account account = accountDao.getAccountByPnum(pnum);
        if (account!=null) {
            User user = new User();
            user.setId(account.getUid());
            user = userDao.queryById(user);
            account.setUserdetail(user);
        }
        return account;
    }

    @Override
    @Transactional
    public Account getAccountByuid(int uid) {
        Account account = accountDao.getAccountByUid(uid);
        if (account!=null) {
            User user = new User();
            user.setId(account.getUid());
            user = userDao.queryById(user);
            account.setUserdetail(user);
        }
        return account;
    }


    @Transactional
    @Override
    public Account getAccount(int cid) {
        Account account = new Account();
        account.setId(cid);
        account = accountDao.queryById(account);
        if (account == null) {
            return null;
        } else {
            User user = new User();
            user.setId(account.getUid());
            user = userDao.queryById(user);
            account.setUserdetail(user);
        }

        return account;
    }

}
