package com.zhumingwei.bond.dao.base.config;

import com.zhumingwei.bond.dao.base.config.resolve.DefaultEntityResolve;
import com.zhumingwei.bond.dao.base.config.resolve.EntityResolve;

import java.util.HashMap;

/**
 * @author zhumingwei
 * @date 2018/6/16 下午5:09
 */
public class EntityTableCache {
    public static EntityResolve entityResolve= new DefaultEntityResolve();
    public static HashMap<Class<?>,EntityTable> tableCache = new HashMap<>();

    public static EntityTable getEntityTable(Class<?> c){
        EntityTable entityTable = tableCache.get(c);
        if (entityTable == null) {
            entityTable = entityResolve.resolveEntity(c);
            tableCache.put(c,entityTable);
        }
        return entityTable;
    }

}
