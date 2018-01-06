package com.jf.mapper;

import java.util.List;
import com.jf.model.Msg;
import com.jf.model.custom.BaseVo;

/**
 * MsgMapper Interface
 * @date 2017年09月11日 上午 11:05:15
 * @author jfxu
 */
public interface MsgMapper {

	List<Msg> findByCondition(BaseVo baseVo);

	List<Msg> findByToday(Long adminId);

	int insert(Msg bean);

	int delete(Long id);

}