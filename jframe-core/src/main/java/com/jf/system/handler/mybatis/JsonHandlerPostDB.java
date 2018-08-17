package com.jf.system.handler.mybatis;

import com.jf.json.JacksonUtil;
import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;
//import org.postgresql.util.PGobject;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created with IntelliJ IDEA.
 * Description: mybatis处理JSON类型[Postgresql]
 * <pre>
 *     Insert/Update: #{extend, typeHandler=com.jf.system.handler.mybatis.JsonTypeHandler, javaType=extend}
 *     ResultMap: property="extend" column="extend" typeHandler="com.jf.system.handler.mybatis.JsonHandler" javaType="extend"
 * </pre>
 * User: xujunfei
 * Date: 2018-06-26
 * Time: 16:50
 */
public class JsonHandlerPostDB<T extends Object> extends BaseTypeHandler<T> {

    private Class<T> clazz;

    public JsonHandlerPostDB(Class<T> clazz) {
        if (clazz == null)
            throw new IllegalArgumentException("Type argument cannot be null");
        this.clazz = clazz;
    }

    //private static final PGobject pGobject = new PGobject();

    @Override
    public void setNonNullParameter(PreparedStatement ps, int i, T obj, JdbcType jdbcType) throws SQLException {
        //pGobject.setType("jsonb");
        //pGobject.setValue(JacksonUtil.objectToJson(obj));
        //ps.setObject(i, pGobject);
    }

    @Override
    public T getNullableResult(ResultSet rs, String columnName) throws SQLException {
        return JacksonUtil.jsonToBean(rs.getString(columnName), clazz);
    }

    @Override
    public T getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
        return JacksonUtil.jsonToBean(rs.getString(columnIndex), clazz);
    }

    @Override
    public T getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
        return JacksonUtil.jsonToBean(cs.getString(columnIndex), clazz);
    }
}
