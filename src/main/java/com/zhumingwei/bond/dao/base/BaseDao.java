package com.zhumingwei.bond.dao.base;

import com.zhumingwei.bond.dao.UserDao;
import com.zhumingwei.bond.dao.base.config.EntityColumn;
import com.zhumingwei.bond.dao.base.config.EntityTable;
import com.zhumingwei.bond.tool.LogUtil;
import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.annotations.UpdateProvider;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import static com.zhumingwei.bond.dao.base.config.EntityTableCache.getEntityTable;


public interface BaseDao<T> {

    @SelectProvider(type = BaseProvider.class, method = "queryByPrimary")
    T queryByPrimary(Object uid);

    @InsertProvider(type = BaseProvider.class, method = "add")
    int add(T t);


    class BaseProvider<T> {
        //必须重写
        public  Class<T> getClazz(){
            return null;
        }

        public String queryByPrimary(Object primary) {
            EntityTable entityTable = getEntityTable(getClass());
            return new StringBuilder().append("select * from ")
                    .append(entityTable.name)
                    .append(" where ")
                    .append(entityTable.entityClassPKColumns.get(0).getColumn())
                    .append(" = ")
                    .append(primary).toString();
        }

        public String add(T t) {
            EntityTable entityTable = getEntityTable(getClass());

            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("insert into ");
            stringBuilder.append(entityTable.name);

            List<String> columns = getallValues(entityTable);
            sqlClause(stringBuilder, " (", ") values ", columns, ",");
            List<String> values = new ArrayList<>();

            for (String column : columns) {
                values.add(sqlClause(" #{", "}", column));
            }
            sqlClause(stringBuilder, "(", ")", values, ",");
            LogUtil.loge(stringBuilder.toString());
            return stringBuilder.toString();
        }

        public List<String> getallValues(EntityTable entityTable) {
            List<String> list = new ArrayList<>();
            for (EntityColumn column : entityTable.entityClassColumns) {
                list.add(column.getColumn());
            }
            return list;
        }

        private void sqlClause(StringBuilder builder, String open, String close, List<String> parts, String conjunction) {
            builder.append(open);

            for (int i = 0; i < parts.size(); i++) {
                builder.append(parts.get(i));
                if (i != parts.size() - 1) {
                    builder.append(conjunction);
                }
            }
            builder.append(close);
        }

        private String sqlClause(String open, String close, String part) {
            StringBuilder sb = new StringBuilder();
            sb.append(open);
            sb.append(part);
            sb.append(close);
            return sb.toString();

        }


    }

}


