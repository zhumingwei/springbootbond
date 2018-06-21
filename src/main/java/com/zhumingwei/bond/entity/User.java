package com.zhumingwei.bond.entity;

import com.zhumingwei.bond.BondConstant;
import com.zhumingwei.bond.dao.base.config.anotation.ID;
import com.zhumingwei.bond.dao.base.config.anotation.Table;


@Table(name = "user")
public class User extends BaseEntity {

    @ID
    private int id;
    private String nickname;
    private String avatar;
    private int user_level;
    private int user_state;
    private int cid;//公司id
    private int is_delete;

    public String getAvatar() {
        if (avatar == null){
            avatar = BondConstant.INSTANCE.getDEFAULT_AVATAR();
        }
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public int getIs_delete() {
        return is_delete;
    }

    public void setIs_delete(int is_delete) {
        this.is_delete = is_delete;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
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
                ", nickname='" + nickname + '\'' +
                ", avatar='" + avatar + '\'' +
                ", user_level=" + user_level +
                ", user_state=" + user_state +
                ", cid=" + cid +
                ", is_delete=" + is_delete +
                "} ";
    }
}
