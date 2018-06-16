package com.zhumingwei.bond.dao.base.config;

import com.zhumingwei.bond.dao.base.config.anotation.Table;
import com.zhumingwei.bond.dao.base.config.resolve.DefaultEntityResolve;
import com.zhumingwei.bond.dao.base.config.resolve.EntityResolve;

import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.Set;

/**
 * @author zhumingwei
 * @date 2018/6/16 下午3:48
 */
public class EntityTable {


    final Class<?> entityClass;
    public String name;
    public LinkedList<EntityColumn> entityClassColumns;
    public LinkedList<EntityColumn> entityClassPKColumns;



    public EntityTable(Class<?> entityClass) {
        this.entityClass = entityClass;
    }

    public void setTable(Table table) {
        name = table.name();
    }

    public void setName(String tablename) {
        name = tablename;
    }

    public void setEntityClassColumns(LinkedList<EntityColumn> columns) {
        entityClassColumns  = columns;
    }

    public void setEntityPKClassColumns(LinkedList<EntityColumn> columns) {
        entityClassPKColumns  = columns;
    }



}
