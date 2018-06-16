package com.zhumingwei.bond.entity;

import com.zhumingwei.bond.dao.base.config.anotation.ID;
import com.zhumingwei.bond.dao.base.config.anotation.Table;

@Table(name = "user")
public class User extends BaseEntity{
    @ID
    private long id;
    private String realname;
    private int user_level;
    private int user_state;
    private int cid;//公司id

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getRealname() {
        return realname;
    }

    public void setRealname(String realname) {
        this.realname = realname;
    }

    public int getUser_level() {
        return user_level;
    }

    public void setUser_level(int user_level) {
        this.user_level = user_level;
    }

    public int getUser_state() {
        return user_state;
    }

    public void setUser_state(int user_state) {
        this.user_state = user_state;
    }

    public int getCid() {
        return cid;
    }

    public void setCid(int cid) {
        this.cid = cid;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", realname='" + realname + '\'' +
                ", user_level=" + user_level +
                ", user_state=" + user_state +
                ", cid=" + cid +
                '}';
    }
}
