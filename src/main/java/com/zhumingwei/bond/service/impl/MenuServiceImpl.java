package com.zhumingwei.bond.service.impl;

import com.zhumingwei.bond.dao.MenuDao;
import com.zhumingwei.bond.entity.MenuDetail;
import com.zhumingwei.bond.entity.MenuSteps;
import com.zhumingwei.bond.entity.MenuThings;
import com.zhumingwei.bond.entity.base.ListModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author zhumingwei
 * @date 2018/9/4 上午9:58
 */
@Component
public class MenuServiceImpl {
    @Autowired
    MenuDao menuDao;

    public ListModel<MenuDetail> getMenuDetailList(int cursor, int limit) {
        ListModel<MenuDetail> listModel = new ListModel<MenuDetail>();
        List<MenuDetail> items = menuDao.getMenuDetailList(cursor, limit);

        for (MenuDetail md:items){

            md.menuSteps = getMenuSteps(md.id);
            md.menuThings= getMenuThings(md.id);

        }
        listModel.setItems(items);
        listModel.setNextCursor(items.get(items.size() - 1).id);
        listModel.setTotalCount(menuDao.getCount());
        return listModel;
    }

    private List<MenuThings> getMenuThings(int detailId) {
        return menuDao.getMenuThings(detailId);
    }

    private List<MenuSteps> getMenuSteps(int detailId) {
        return menuDao.getMenuSteps(detailId);
    }
}
