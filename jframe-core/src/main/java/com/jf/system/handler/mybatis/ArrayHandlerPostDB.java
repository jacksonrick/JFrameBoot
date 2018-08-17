package com.jf.system.handler.mybatis;

import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.TypeException;

import java.sql.*;

/**
 * Created with IntelliJ IDEA.
 * Description: mybatis处理数组类型[Postgresql]
 * User: xujunfei
 * Date: 2018-06-15
 * Time: 18:32
 */
public class ArrayHandlerPostDB extends BaseTypeHandler<Object[]> {

    private static final String TYPE_NAME_VARCHAR = "varchar";
    private static final String TYPE_NAME_INTEGER = "integer";
    private static final String TYPE_NAME_BOOLEAN = "boolean";
    private static final String TYPE_NAME_NUMERIC = "numeric";

    @Override
    public void setNonNullParameter(PreparedStatement ps, int i, Object[] obj, JdbcType jdbcType) throws SQLException {
        String typeName = null;
        if (obj instanceof Integer[]) {
            typeName = TYPE_NAME_INTEGER;
        } else if (obj instanceof String[]) {
            typeName = TYPE_NAME_VARCHAR;
        } else if (obj instanceof Boolean[]) {
            typeName = TYPE_NAME_BOOLEAN;
        } else if (obj instanceof Double[]) {
            typeName = TYPE_NAME_NUMERIC;
        }

        if (typeName == null) {
            throw new TypeException("ArrayTypeHandler parameter typeName error, your type is " + obj.getClass().getName());
        }

        Connection conn = ps.getConnection();
        Array array = conn.createArrayOf(typeName, obj);
        ps.setArray(i, array);
    }

    @Override
    public Object[] getNullableResult(ResultSet rs, String columnName) throws SQLException {
        return getArray(rs.getArray(columnName));
    }

    @Override
    public Object[] getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
        return getArray(rs.getArray(columnIndex));
    }

    @Override
    public Object[] getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
        return getArray(cs.getArray(columnIndex));
    }

    private Object[] getArray(Array array) {
        if (array == null) {
            return null;
        }
        try {
            return (Object[]) array.getArray();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
