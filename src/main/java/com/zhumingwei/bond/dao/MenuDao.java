package com.zhumingwei.bond.dao;

import com.zhumingwei.bond.dao.base.BaseDao;
import com.zhumingwei.bond.entity.MenuDetail;
import com.zhumingwei.bond.entity.MenuSteps;
import com.zhumingwei.bond.entity.MenuThings;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;

import java.util.List;

/**
 * @author zhumingwei
 * @date 2018/9/3 下午2:02
 */
@Component
public interface MenuDao extends BaseDao<MenuDetail> {
    String tablename = "menudetail";

    @Select("select * from "+tablename+ " order by id limit #{cursor},#{limit}")
    public List<MenuDetail> getMenuDetailList(@Param("cursor") int cursor, @Param("limit")int limit);

    @Select("select COUNT(1) from menudetail")
    public long getCount();

    @Select("select * from menuthings where menudetailId = #{detailId}")
    List<MenuThings> getMenuThings(int detailId);

    @Select("select * from menusteps where menudetailId = #{detailId}")
    List<MenuSteps> getMenuSteps(int detailId);
}
