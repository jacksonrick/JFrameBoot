package com.jf.mapper;

import com.jf.database.model.manage.Msg;
import com.jf.database.model.custom.BaseVo;

import java.util.List;

/**
 * MsgMapper Interface
 * @date 2017年09月11日 上午 11:05:15
 * @author jfxu
 */
public interface MsgMapper {

	List<Msg> findByCondition(BaseVo baseVo);

	List<Msg> findByToday(Integer adminId);

	int insert(Msg bean);

	int delete(Integer id);

}