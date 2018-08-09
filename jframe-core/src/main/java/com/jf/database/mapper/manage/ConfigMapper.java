package com.jf.database.mapper.manage;

import com.jf.database.model.manage.Config;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * ConfigMapper Interface
 *
 * @date 2018年06月22日 下午 12:03:38
 * @author jfxu
 */
public interface ConfigMapper {

	List<Config> findAll();

	String findByKey(String key);

	List<Config> findByKeys(@Param("keys") String[] keys);

	int insertBatch(@Param("map") Map map);

	int updateBatch(@Param("map") Map map);

	int update(Config bean);

	int delete();

}