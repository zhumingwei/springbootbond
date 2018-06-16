package com.zhumingwei.bond.service.impl;

import com.zhumingwei.bond.dao.UserDao;
import com.zhumingwei.bond.entity.User;
import com.zhumingwei.bond.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class UserServiceImpl implements UserService {

    @Autowired
    UserDao userDao;

    @Override
    public int add(User user, long bywho) {
        int i = userDao.add(user);
        userDao.setCreateUser(user.getId(),bywho);
        return i;
    }

    @Override
    public int deleteById(long id, long bywho) {
        int i = userDao.deleteById(id);
        userDao.updateDateUser(id,bywho);
        return i;
    }

    @Override
    public int updateUser(User user, long bywho) {
        int i = userDao.updateUser(user);
        userDao.updateDateUser(user.getId(),bywho);
        return i;
    }

    @Override
    public User queryById(long id) {
        return userDao.queryByPrimary(id);
    }
}
