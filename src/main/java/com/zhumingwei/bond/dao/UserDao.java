package com.zhumingwei.bond.dao;

import com.zhumingwei.bond.dao.base.BaseDao;
import com.zhumingwei.bond.entity.User;
import com.zhumingwei.bond.tool.LogUtil;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.apache.ibatis.annotations.UpdateProvider;
import org.springframework.stereotype.Component;

@Component
public interface UserDao extends BaseDao<User> {
    String tablename = "user";



    class UserSqlBuilder extends BaseProvider {


    }
}
