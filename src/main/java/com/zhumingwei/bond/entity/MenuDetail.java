package com.zhumingwei.bond.entity;

import com.zhumingwei.bond.dao.base.config.anotation.ID;
import com.zhumingwei.bond.dao.base.config.anotation.NotColumn;
import com.zhumingwei.bond.entity.base.BaseEntity;

import java.util.List;

/**
 * @author zhumingwei
 * @date 2018/9/3 下午2:06
 */
public class MenuDetail extends BaseEntity {
    @ID
    public int id;
    public String name;
    public String url;
    public String title;
    public String imgurl;
    public String desc;
    @NotColumn
    public List<MenuSteps> menuSteps;
    @NotColumn
    public List<MenuThings> menuThings;


    @Override
    public String toString() {
        return "MenuDetail{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", title='" + title + '\'' +
                ", imgurl='" + imgurl + '\'' +
                ", desc='" + desc + '\'' +

                "} ";
    }
}
