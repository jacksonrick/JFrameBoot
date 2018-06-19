package com.jf.service.system;

import com.jf.database.mapper.AdminMapper;
import com.jf.database.mapper.ModuleMapper;
import com.jf.database.mapper.RoleMapper;
import com.jf.database.model.Admin;
import com.jf.database.model.Module;
import com.jf.database.model.Role;
import com.jf.string.StringUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

/**
 * 模块和角色Service
 *
 * @author rick
 */
@Service
@Transactional
public class ModuleService {

    @Resource
    private ModuleMapper moduleMapper;
    @Resource
    private RoleMapper roleMapper;
    @Resource
    private AdminMapper adminMapper;

    /**
     * 查询所有模块
     *
     * @return
     */
    public List<Module> findModuleAll() {
        return moduleMapper.findByCondition(new Module());
    }

    /**
     * @param flag
     * @param name
     * @param parentId
     * @param path
     * @param icon
     * @return
     */
    public int insertModule(Integer flag, String name, Integer parentId, String path, String icon) {
        Module module = new Module();
        module.setParentId(parentId);
        module.setModName(name);
        module.setModFlag(flag);
        module.setModPath(path);
        module.setModIcon(icon);
        return moduleMapper.insert(module);
    }

    /**
     * @param id
     * @param name
     * @param path
     * @param icon
     * @return
     */
    public int updateModule(Integer id, String name, String path, String icon) {
        Module module = new Module(id);
        module.setModName(name);
        module.setModPath(path);
        module.setModIcon(icon);
        return moduleMapper.update(module);
    }

    /**
     * @param id
     * @return
     */
    public int deleteModule(Integer id) {
        return moduleMapper.delete(id);
    }

    /**************************************************/

    /**
     * 查询所有组
     *
     * @return
     */
    public List<Role> findRoleList() {
        return roleMapper.findAll();
    }

    /**
     * 新增组
     *
     * @param roleName
     * @return
     */
    public int insertRole(String roleName) {
        Role role = new Role();
        role.setRoleName(roleName);
        return roleMapper.insert(role);
    }

    /**
     * 更新组
     *
     * @param roleId
     * @param roleName
     * @return
     */
    public int updateRole(Long roleId, String roleName) {
        Role role = new Role(roleId);
        role.setRoleName(roleName);
        return roleMapper.update(role);
    }

    /**
     * 禁用/启用组
     *
     * @param roleId
     * @return
     */
    public int deleteRole(Long roleId) {
        return roleMapper.delete(roleId);
    }

    /**
     * 删除权限
     *
     * @param roleId
     * @return
     */
    public int deleteRights(Long roleId) {
        Role role = new Role(roleId);
        // 权限置空
        role.setRoleRights("");
        return roleMapper.update(role);
    }

    /**
     * 组授权
     *
     * @param roleId
     * @param rights
     * @return
     */
    public int permitRole(Long roleId, String[] rights) {
        Role role = new Role(roleId);
        role.setRoleRights(StringUtils.join(rights, ","));
        return roleMapper.update(role);
    }

    /**
     * 管理员授权
     *
     * @param adminId
     * @param rights
     * @return
     */
    public int permitAdmin(Long adminId, String[] rights) {
        Admin admin = new Admin(adminId);
        admin.setAdminRights(StringUtils.join(rights, ","));
        return adminMapper.update(admin);
    }

    /**
     * 查询所有可用模块
     *
     * @param roleId
     * @param adminId
     * @return
     * @author rick
     * @date 2016年7月22日 上午11:56:18
     */
    public List<Module> findModuleByRoleOrAdmin(Long roleId, Long adminId) {
        Module module = new Module();
        module.setFlags(new Integer[]{1, 2, 3});
        if (roleId != null) { // 组
            module.setRoleId(roleId);
        } else if (adminId != null) { // 用户
            module.setAdminId(adminId);
        } else {
            return null;
        }
        List<Module> list = moduleMapper.findByCondition(module);
        if (!list.isEmpty()) {
            return list;
        }
        return null;
    }

    /**
     * 查询菜单模块
     *
     * @param adminId
     * @return
     */
    public List<Module> findModules(Long adminId) {
        Admin admin = adminMapper.findRightsById(adminId);
        // 管理员权限和组权限合并（去重）
        Set<String> set = new TreeSet<String>();
        if (StringUtil.isNotBlank(admin.getAdminRights())) {
            String[] arr = admin.getAdminRights().split(",");
            set.addAll(Arrays.asList(arr));
        }
        if (admin.getRole() != null && StringUtil.isNotBlank(admin.getRole().getRoleRights())) {
            String[] arr = admin.getRole().getRoleRights().split(",");
            set.addAll(Arrays.asList(arr));
        }

        Module module = new Module();
        module.setFlags(new Integer[]{1, 2});
        module.setIds(StringUtils.join(set.toArray(), ","));
        List<Module> list = moduleMapper.findByCondition(module);
        if (!list.isEmpty()) {
            return list;
        }
        return null;
    }

    /**
     * 检查是否有权限
     *
     * @param adminId
     * @param uri
     * @return
     * @author rick
     * @date 2016年7月22日 下午1:49:51
     */
    public boolean checkHavingRight(Long adminId, String uri) {
        if (StringUtil.isBlank(uri)) {
            return false;
        }

        // 查询管理员及组的权限
        Admin admin = adminMapper.findRightsById(adminId);
        if (admin == null) {
            return false;
        }
        if (admin.getRole() != null && admin.getRole().getRoleFlag() == 0) { // 内置管理组开放全部权限
            return true;
        }
        // URI对应的模块id
        Integer moduleId = moduleMapper.findIdByPath(uri);
        if (moduleId == null) {
            return false;
        }

        // 管理员权限和组权限合并（去重）
        Set<String> set = new TreeSet<String>();
        if (StringUtil.isNotBlank(admin.getAdminRights())) {
            String[] arr = admin.getAdminRights().split(",");
            set.addAll(Arrays.asList(arr));
        }
        if (admin.getRole() != null && StringUtil.isNotBlank(admin.getRole().getRoleRights())) {
            String[] arr = admin.getRole().getRoleRights().split(",");
            set.addAll(Arrays.asList(arr));
        }
        // 如果模块id存在权限集合中(set)
        String modId = String.valueOf(moduleId);
        for (String str : set) {
            if (modId.equals(str)) {
                return true;
            }
        }
        return false;
    }

}
