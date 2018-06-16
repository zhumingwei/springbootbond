package com.zhumingwei.bond.dao.base.config.resolve;

import com.zhumingwei.bond.dao.base.config.EntityTable;

/**
 * 解析实体类接口
 *
 * @author liuzh
 */
public interface EntityResolve {

    /**
     * 解析类为 EntityTable
     *
     * @param entityClass
     * @return
     */
    EntityTable resolveEntity(Class<?> entityClass);

}
