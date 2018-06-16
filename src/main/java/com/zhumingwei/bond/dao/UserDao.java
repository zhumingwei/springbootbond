package com.zhumingwei.bond.dao;

import com.zhumingwei.bond.dao.base.BaseDao;
import com.zhumingwei.bond.entity.User;
import com.zhumingwei.bond.tool.LogUtil;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.mapping.MappedStatement;
import org.springframework.stereotype.Component;

@Mapper
@Component
public interface UserDao extends BaseDao<User> {
    String tablename = "user";

//    @InsertProvider(type = UserSqlBuilder.class, method = "addUser")
//    int add(User user);

    @UpdateProvider(type = UserSqlBuilder.class, method = "setCreateUser")
    int setCreateUser(long uid, long byid);

    @Update("update " + tablename + " set is_delete=true  where id = #{id}")
    int deleteById(long id);

    @Update("update " + tablename + " set realname=#{realname},user_level=#{user_level},user_state=#{user_state},cid=#{cid} where id=#{id}")
    public int updateUser(User user);

    @UpdateProvider(type = UserSqlBuilder.class, method = "updateDateUser")
    public int updateDateUser(long uid, long byuid);

//    @Select("select * from " + tablename + " where id = #{id}")
//    @SelectProvider(type = UserSqlBuilder.class, method = "queryById")
//    User queryById(Object uid);


    class UserSqlBuilder extends BaseProvider<User> {

        public String queryById(Object o) {
            return "select * from " + tablename + " where id = " +o;
//            return new SQL().SELECT("*").FROM(tablename).WHERE("id="+id).toString();
        }

        public String addUser(User user) {
            StringBuffer stringBuffer = new StringBuffer();
            stringBuffer.append("insert into " + tablename + " (realname,user_level,user_state,cid) values");
            stringBuffer.append("(");
            stringBuffer.append("\'");
            stringBuffer.append(user.getRealname());
            stringBuffer.append("\'");
            stringBuffer.append(",");
            stringBuffer.append(user.getUser_level()).append(",");
            stringBuffer.append(user.getUser_state()).append(",");
            stringBuffer.append(user.getCid());
            stringBuffer.append(")");
            LogUtil.loge(stringBuffer.toString());
            return stringBuffer.toString();
        }

        public String updateDateUser(long uid, long byuid) {
            StringBuffer stringBuffer = new StringBuffer("update " + tablename + " set updatedate=NOW()");
            if (byuid != 0) {
                stringBuffer.append(",updateby=" + byuid);
            }
            stringBuffer.append(" where id=" + uid);
            LogUtil.loge(stringBuffer.toString());
            return stringBuffer.toString();
        }

        public String setCreateUser(long uid, long byuid) {
            StringBuffer stringBuffer = new StringBuffer("update " + tablename + " set ");
            if (byuid != 0) {
                stringBuffer.append("createby=" + byuid);
                stringBuffer.append(",updateby=" + byuid);
            }
            stringBuffer.append(" where id=" + uid);
            LogUtil.loge(stringBuffer.toString());
            return stringBuffer.toString();
        }

        @Override
        public Class<User> getClazz() {
            return User.class;
        }
    }
}
