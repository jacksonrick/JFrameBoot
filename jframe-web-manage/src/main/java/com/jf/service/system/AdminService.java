package com.jf.service.system;

import com.github.pagehelper.PageInfo;
import com.jf.encrypt.PasswordUtil;
import com.jf.database.mapper.manage.AdminMapper;
import com.jf.database.mapper.manage.MsgMapper;
import com.jf.database.model.manage.Admin;
import com.jf.database.model.manage.Msg;
import com.jf.string.StringUtil;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * 管理员Service
 *
 * @author rick
 */
@Service
@Transactional
public class AdminService {

    @Resource
    private AdminMapper adminMapper;
    @Resource
    private MsgMapper msgMapper;

    /**
     * 分页查询
     *
     * @param condition
     * @return
     */
    public PageInfo findAdminByPage(Admin condition) {
        condition.setPageSort("a.id ASC");
        condition.setPage(true);
        List<Admin> list = adminMapper.findByCondition(condition);
        return new PageInfo(list);
    }

    /**
     * 按id查询
     *
     * @param id
     * @return
     */
    public Admin findAdminById(Integer id) {
        return adminMapper.findById(id);
    }

    /**
     * 按用户名和密码查询
     *
     * @param username
     * @param password
     * @return
     */
    public Admin findAdminByNameAndPwd(String username, String password) {
        return adminMapper.findByNameAndPwd(username, PasswordUtil.MD5Encode(password));
    }

    /**
     * 用户名查询总数
     *
     * @param adminName
     * @return
     */
    public int findAdminCountByName(String adminName) {
        return adminMapper.findCountByKey(new Admin(adminName));
    }

    /**************************************************/

    /**
     * 登录
     *
     * @param username
     * @param password
     * @return
     */
    public Admin login(String username, String password, String ip) {
        Admin admin = this.findAdminByNameAndPwd(username, password);
        if (admin == null) {
            return null;
        }
        this.updateLogin(admin.getId(), ip);
        return admin;
    }

    /***
     * 新增管理员
     *
     * @param admin
     * @return
     */
    public int insertAdmin(Admin admin) {
        if (StringUtil.isNotBlank(admin.getAdminPassword())) {
            admin.setAdminPassword(PasswordUtil.MD5Encode(admin.getAdminPassword()));
        } else {
            // 默认密码
            admin.setAdminPassword(PasswordUtil.MD5Encode("123456"));
        }
        return adminMapper.insert(admin);
    }

    /**
     * 更新管理员
     *
     * @param admin
     * @return
     */
    public int updateAdmin(Admin admin) {
        if (StringUtil.isNotBlank(admin.getAdminPassword())) {
            admin.setAdminPassword(PasswordUtil.MD5Encode(admin.getAdminPassword()));
        }
        return adminMapper.update(admin);
    }

    /**
     * 更新登录时间和ip
     *
     * @param adminId
     * @param ip
     * @return
     */
    private int updateLogin(Integer adminId, String ip) {
        Admin admin = new Admin(adminId);
        admin.setAdminLoginTime(new Date());
        admin.setAdminLoginIp(ip);
        return adminMapper.update(admin);
    }

    /**
     * 更新账户密码
     *
     * @param adminId
     * @param password
     * @return
     */
    public int updatePassword(Integer adminId, String password) {
        Admin admin = new Admin(adminId);
        admin.setAdminPassword(PasswordUtil.MD5Encode(password));
        return adminMapper.update(admin);
    }

    /**
     * 启用或禁用管理员
     *
     * @param adminId
     * @return
     */
    public int deleteAdmin(Integer adminId) {
        return adminMapper.delete(adminId);
    }

    /**
     * 删除权限
     *
     * @param adminId
     * @return
     */
    public int deleteRights(Integer adminId) {
        Admin admin = new Admin(adminId);
        admin.setAdminRights("");
        return adminMapper.update(admin);
    }

    /**
     * 查看今日消息
     *
     * @param adminId
     * @return
     */
    public List<Msg> findByTodayMsg(Integer adminId) {
        return msgMapper.findByToday(adminId);
    }

    /**
     * 新增消息
     *
     * @param adminId
     * @param content
     */
    public void addMsg(Integer adminId, String content) {
        Msg msg = new Msg();
        msg.setAdminId(adminId);
        msg.setContent(content);
        msgMapper.insert(msg);
    }

}
