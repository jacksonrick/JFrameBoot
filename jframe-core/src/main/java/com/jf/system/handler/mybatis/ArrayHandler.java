package com.jf.system.handler.mybatis;

import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created with IntelliJ IDEA.
 * Description: mybatis处理数组[String[]]类型
 * User: xujunfei
 * Date: 2018-06-15
 * Time: 18:32
 */
public class ArrayHandler extends BaseTypeHandler<String[]> {

    private final String splitChar = ",";

    @Override
    public void setNonNullParameter(PreparedStatement ps, int i, String[] obj, JdbcType jdbcType) throws SQLException {
        ps.setString(i, StringUtils.join(obj, splitChar));
    }

    @Override
    public String[] getNullableResult(ResultSet rs, String columnName) throws SQLException {
        return StringUtils.split(rs.getString(columnName), splitChar);
    }

    @Override
    public String[] getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
        return StringUtils.split(rs.getString(columnIndex), splitChar);
    }

    @Override
    public String[] getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
        return StringUtils.split(cs.getString(columnIndex), splitChar);
    }
}
