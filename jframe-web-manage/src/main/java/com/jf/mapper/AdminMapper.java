package com.jf.mapper;

import com.jf.database.model.manage.Admin;
import com.jf.database.model.custom.BaseVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * AdminMapper Interface
 * @author rick
 * @version 1.0
 */
public interface AdminMapper {

	List<Admin> findByCondition(BaseVo baseVo);

    Admin findById(Integer id);

	int findCountByKey(BaseVo baseVo);

	Admin findByNameAndPwd(@Param("loginName") String loginName, @Param("password") String password);

	Admin findRightsById(Integer id);

	int insert(Admin bean);

	int update(Admin bean);

    int delete(Integer id);

}
