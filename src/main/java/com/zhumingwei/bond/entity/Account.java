package com.zhumingwei.bond.entity;

import com.zhumingwei.bond.dao.base.config.anotation.ID;
import com.zhumingwei.bond.dao.base.config.anotation.NotColumn;

/**
 * @author zhumingwei
 * @date 2018/6/17 上午12:20
 */
public class Account extends BaseEntity {
    @ID
    private int id;
    private String password;
    private String phonenumber;
    private int uid;
    @NotColumn
    private User userdetail;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }


    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhonenumber() {
        return phonenumber;
    }

    public void setPhonenumber(String phonenumber) {
        this.phonenumber = phonenumber;
    }

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public User getUserdetail() {
        return userdetail;
    }

    public void setUserdetail(User userdetail) {
        this.userdetail = userdetail;
    }

    @Override
    public String toString() {
        return "Account{" +
                "id=" + id +
                ", password='" + password + '\'' +
                ", phonenumber='" + phonenumber + '\'' +
                ", uid=" + uid +
                ", userdetail=" + userdetail +
                "} " + super.toString();
    }
}
