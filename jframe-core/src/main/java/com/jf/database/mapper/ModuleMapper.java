package com.jf.database.mapper;

import com.jf.database.model.custom.BaseVo;
import com.jf.database.model.Module;

import java.util.List;

/**
 * ModuleMapper Interface
 * @author rick
 * @version
 */
public interface ModuleMapper {

	List<Module> findByCondition(BaseVo baseVo);

	Integer findIdByPath(String path);

	int update(Module bean);

	int insert(Module bean);

	int delete(Integer id);

}
