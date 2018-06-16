package com.zhumingwei.bond.dao.base.config.resolve;

import com.zhumingwei.bond.dao.base.config.EntityColumn;
import com.zhumingwei.bond.dao.base.config.EntityTable;
import com.zhumingwei.bond.dao.base.config.anotation.ID;
import com.zhumingwei.bond.dao.base.config.anotation.Table;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.*;

public class DefaultEntityResolve implements EntityResolve {
    @Override
    public EntityTable resolveEntity(Class<?> entityClass) {
        EntityTable entityTable = null;
        if (entityClass.isAnnotationPresent(Table.class)) {
            Table table = entityClass.getAnnotation(Table.class);
            if (!"".equals(table.name())) {
                entityTable = new EntityTable(entityClass);
                entityTable.setTable(table);
            }
        }
        if (entityTable == null) {
            entityTable = new EntityTable(entityClass);
            String tablename = entityClass.getSimpleName();
            entityTable.setName(tablename);
        }

        LinkedList<EntityColumn> entityClassColumns = new LinkedList<>();
        LinkedList<EntityColumn> entityClassPKColumns = new LinkedList<>();

        entityTable.setEntityClassColumns(entityClassColumns);
        entityTable.setEntityPKClassColumns(entityClassPKColumns);


        List<Field> fields = new ArrayList<>();
        _fileds(entityClass, fields);
        for (Field field : fields) {
            EntityColumn fieldName = field2EntityColumn(field);
            entityClassColumns.add(fieldName);
            if (field.isAnnotationPresent(ID.class)) {
                entityClassPKColumns.add(fieldName);
            }
        }
        return entityTable;
    }

    private EntityColumn field2EntityColumn(Field field) {
        EntityColumn column  = new EntityColumn();
        column.setColumn(field.getName().toLowerCase());
        column.setJavaType(field.getType());
        return column;
    }

    private void _fileds(Class<?> entityClass, List<Field> filenames) {
        Field[] fields = entityClass.getDeclaredFields();
        for (Field field : fields) {
            if (!Modifier.isStatic(field.getModifiers()) && !Modifier.isTransient(field.getModifiers())) {
                filenames.add(field);
            }
        }

        Class<?> superClass = entityClass.getSuperclass();
        if (superClass != null) {
            _fileds(superClass, filenames);
        }

    }
}
