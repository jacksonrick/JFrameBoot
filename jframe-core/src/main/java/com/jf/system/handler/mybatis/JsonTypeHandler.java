package com.jf.system.handler.mybatis;

import com.jf.database.model.custom.Extend;
import com.jf.json.JSONUtils;
import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created with IntelliJ IDEA.
 * Description: mybatis处理JSON类型
 * User: xujunfei
 * Date: 2018-06-15
 * Time: 18:32
 */
public class JsonTypeHandler extends BaseTypeHandler<Extend> {

    @Override
    public void setNonNullParameter(PreparedStatement ps, int i, Extend obj, JdbcType jdbcType) throws SQLException {
        ps.setString(i, JSONUtils.toJSONString(obj));
    }

    @Override
    public Extend getNullableResult(ResultSet rs, String columnName) throws SQLException {
        return JSONUtils.toBean(rs.getString(columnName), Extend.class);
    }

    @Override
    public Extend getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
        return JSONUtils.toBean(rs.getString(columnIndex), Extend.class);
    }

    @Override
    public Extend getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
        return JSONUtils.toBean(cs.getString(columnIndex), Extend.class);
    }
}
