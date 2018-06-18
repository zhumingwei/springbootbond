package com.zhumingwei.bond.dao;

import com.zhumingwei.bond.dao.base.BaseDao;
import com.zhumingwei.bond.entity.Account;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author zhumingwei
 * @date 2018/6/17 上午12:19
 */
@Component
public interface AccountDao extends BaseDao<Account> {
    String tablename = "account";

    @Select("select * from " + tablename + " where phonenumber=#{pnum} and password=#{pwd}")
    Account getAccountByPnumPwd(@Param("pnum") String phonenumber, @Param("pwd") String pwd);

    @Select("select * from " + tablename + " where phonenumber=#{pnum}")
    Account getAccountByPnum(@Param("pnum") String phonenumber);

}
