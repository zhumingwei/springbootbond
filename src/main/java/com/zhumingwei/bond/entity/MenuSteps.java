package com.zhumingwei.bond.entity;

import com.zhumingwei.bond.dao.base.config.anotation.ID;

/**
 * @author zhumingwei
 * @date 2018/9/3 下午2:08
 */
public class MenuSteps {
    @ID
    int id;
    String menudetailId;
    int step;
    String detail;
}
