package com.jf.service.system;

import com.github.pagehelper.PageInfo;
import com.jf.mapper.LogMapper;
import com.jf.database.model.manage.Admin;
import com.jf.database.model.manage.Log;
import com.jf.system.conf.IConstant;
import com.jf.system.db.DbSQLExecutor;
import org.springframework.stereotype.Service;
import org.springframework.web.util.WebUtils;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 系统Service
 *
 * @author rick
 */
@Service
public class SystemService {

    @Resource
    private LogMapper logMapper;

    @Resource
    private DbSQLExecutor dbSQLExecutor;

    /**
     * 管理操作日志
     *
     * @param request
     * @param remark
     * @param params
     */
    public void addAdminLog(HttpServletRequest request, String remark, String params) {
        Object object = WebUtils.getSessionAttribute(request, IConstant.SESSION_ADMIN);
        Admin admin = null;
        if (object != null) {
            admin = (Admin) object;
        } else {
            return;
        }
        this.addLog(admin.getLoginName(), remark, request.getRemoteAddr(), params);
    }


    /**
     * 新增日志
     *
     * @param operator
     * @param remark
     * @param ip
     * @param params
     * @return
     */
    private int addLog(String operator, String remark, String ip, String params) {
        Log log = new Log();
        log.setOperator(operator);
        log.setRemark(remark);
        log.setIp(ip);
        log.setParams(params);
        return logMapper.insert(log);
    }

    /**
     * 分页查询日志
     *
     * @param condition
     * @return
     */
    public PageInfo findLogByPage(Log condition) {
        condition.setPageSort("id DESC");
        condition.setPage(true);
        List<Log> list = logMapper.findByCondition(condition);
        return new PageInfo(list);
    }

    /**
     * 查询日志总数
     *
     * @return
     */
    public int findLogCount() {
        return logMapper.findCountAll();
    }

    /**
     * 查询旧日志
     *
     * @param monthAgo
     * @return
     */
    public List<Map<String, Object>> findOldLog(Integer monthAgo) {
        return logMapper.findOldLog(monthAgo);
    }

    /**
     * 删除旧日志
     *
     * @param monthAgo
     * @return
     */
    public int deleteOldLog(Integer monthAgo) {
        return logMapper.deleteOldLog(monthAgo);
    }

    /**************************************************/

    /**
     * 动态读取数据记录
     *
     * @param sql 查询语句
     * @return
     * @throws SQLException
     * @throws ClassNotFoundException
     */
    public Object[] executeQuery(String sql) throws Exception {
        List<String> columnList = new ArrayList<String>(); // 存放字段名
        List<List<Object>> dataList = new ArrayList<List<Object>>(); // 存放数据(从数据库读出来的一条条的数据)
        Statement stmt = null;
        ResultSet rs = null;
        Connection conn = null;
        conn = dbSQLExecutor.getConn();
        stmt = conn.createStatement();
        rs = stmt.executeQuery(sql);
        columnList = dbSQLExecutor.getFieldLsit(conn, sql);
        while (rs.next()) {
            List<Object> onedataList = new ArrayList<Object>(); // 存放每条记录里面每个字段的值
            for (int i = 1; i < columnList.size() + 1; i++) {
                onedataList.add(rs.getObject(i));
            }
            dataList.add(onedataList); // 把每条记录放list中
        }
        Object[] arrOb = {columnList, dataList};
        conn.close();
        return arrOb;
    }

    /**
     * 执行 INSERT、UPDATE 或 DELETE
     *
     * @param sql 语句
     * @return
     * @throws SQLException
     * @throws ClassNotFoundException
     */
    public void executeUpdate(String sql) throws ClassNotFoundException, SQLException {
        Statement stmt = null;
        Connection conn = null;
        conn = dbSQLExecutor.getConn();
        stmt = conn.createStatement();
        stmt.executeUpdate(sql);
        conn.close();
    }

}
