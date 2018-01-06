package com.jf.service.system;

import com.jf.mapper.ModuleMapper;
import com.jf.mapper.RoleMapper;
import com.jf.model.Module;
import com.jf.model.Role;
import com.jf.string.StringUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

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
     * 新增权限
     *
     * @param roleId
     * @param rights
     * @return
     */
    public int permit(Long roleId, String[] rights) {
        Role role = new Role(roleId);
        role.setRoleRights(StringUtils.join(rights, ","));
        return roleMapper.update(role);
    }

    /**
     * 查询所有可用模块
     *
     * @param roleId
     * @param all    是否查询所有层级 [true-1,2,3 | false 1,2]
     * @return
     * @author rick
     * @date 2016年7月22日 上午11:56:18
     */
    public List<Module> findModuleByRole(Long roleId, Boolean all) {
        Integer[] flag = new Integer[]{1, 2};
        if (all) {
            flag = new Integer[]{1, 2, 3};
        }
        List<Module> list = moduleMapper.findByCondition(new Module(flag, roleId));
        if (!list.isEmpty()) {
            return list;
        }
        return null;
    }

    /**
     * 检查是否有权限
     *
     * @param roleId
     * @param uri
     * @return
     * @author rick
     * @date 2016年7月22日 下午1:49:51
     */
    public boolean checkHavingRight(Long roleId, String uri) {
        if (StringUtil.isBlank(uri)) {
            return false;
        }
        int size = roleMapper.checkRights(roleId, uri);
        return size > 0;
    }

}
