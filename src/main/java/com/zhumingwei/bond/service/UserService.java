package com.zhumingwei.bond.service;

import com.zhumingwei.bond.entity.User;

public interface UserService {
    int add(User user,long bywho);

    int deleteById(long id,long bywho);

    int updateUser(User user,long bywho);

    User queryById(long id);
}
