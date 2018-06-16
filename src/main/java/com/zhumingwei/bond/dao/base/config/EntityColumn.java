package com.zhumingwei.bond.dao.base.config;

/**
 * @author zhumingwei
 * @date 2018/6/16 下午7:29
 */
public class EntityColumn {
    private String column;
    private EntityTable table;
    private Class<?> javaType;

    public String getColumn() {
        return column;
    }

    public void setColumn(String column) {
        this.column = column;
    }

    public EntityTable getTable() {
        return table;
    }

    public void setTable(EntityTable table) {
        this.table = table;
    }

    public Class<?> getJavaType() {
        return javaType;
    }

    public void setJavaType(Class<?> javaType) {
        this.javaType = javaType;
    }
}
