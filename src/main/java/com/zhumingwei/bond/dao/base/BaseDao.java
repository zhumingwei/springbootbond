package com.zhumingwei.bond.dao.base;

import com.zhumingwei.bond.dao.base.config.EntityColumn;
import com.zhumingwei.bond.dao.base.config.EntityTable;
import com.zhumingwei.bond.tool.LogUtil;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.binding.MapperMethod;

import java.util.ArrayList;
import java.util.List;

import static com.zhumingwei.bond.dao.base.config.EntityTableCache.getEntityTable;


public interface BaseDao<T> {

    @SelectProvider(type = BaseProvider.class, method = "queryById")
    T queryById(T t);

    @InsertProvider(type = BaseProvider.class, method = "add")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int add( T t);

    @UpdateProvider(type = BaseProvider.class, method = "update")
    int update(T t);

    @DeleteProvider(type = BaseProvider.class, method = "deleteById")
    int deleteById(T t);

    class BaseProvider {


        public String deleteById(Object o) {
            EntityTable entityTable = getEntityTable(o.getClass());
            StringBuilder builder = new StringBuilder();
            builder.append("delete from ");
            builder.append(entityTable.name);
            builder.append(" where ");
            builder.append(entityTable.entityClassPKColumns.get(0).getColumn());
            builder.append(" = ");
            builder.append(sqlClause("=#{", "}", entityTable.entityClassPKColumns.get(0).getColumn()));
            return builder.toString();
        }

        public String queryById(Object o) {
            EntityTable entityTable = getEntityTable(o.getClass());
            return new StringBuilder().append("select * from ")
                    .append(entityTable.name)
                    .append(" where ")
                    .append(entityTable.entityClassPKColumns.get(0).getColumn())
                    .append(" = ")
                    .append(sqlClause("#{", "}", entityTable.entityClassPKColumns.get(0).getColumn())).toString();
        }

        public String add(Object o) {
//            MapperMethod.ParamMap
            EntityTable entityTable = getEntityTable(o.getClass());

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

        public String update(Object o) {
            EntityTable entityTable = getEntityTable(o.getClass());
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("update ");
            stringBuilder.append(entityTable.name);
            stringBuilder.append(" set ");
            List<String> columns = getallValues(entityTable);
            List<String> values = new ArrayList<>();
            List<String> whitelist = getUpdateWhiteList();
            for (String column : columns) {
                if (whitelist.contains(column)){
                    continue;
                }
                StringBuilder sb = new StringBuilder();
                sb.append(column);
                sb.append(sqlClause("=#{", "}", column));
                values.add(sb.toString());
            }
            sqlClause(stringBuilder, "", "", values, ",");

            return stringBuilder
                    .append(" where ")
                    .append(entityTable.entityClassPKColumns.get(0).getColumn())
                    .append(" = #{")
                    .append(entityTable.entityClassPKColumns.get(0).getColumn())
                    .append("}").toString();
        }

        public List<String> getUpdateWhiteList(){
            ArrayList list= new ArrayList<String>();
            list.add("createby");
            list.add("createdate");
            return list;
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


