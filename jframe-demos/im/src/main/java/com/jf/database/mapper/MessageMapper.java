package com.jf.database.mapper;

import com.jf.database.model.BaseVo;
import com.jf.database.model.Message;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * MessageMapper Interface
 *
 * @author jfxu
 * @date 2020年07月30日 上午 10:42:59
 */
public interface MessageMapper {

	List<Message> findByCondition(BaseVo baseVo);

	int findUnReadMsgCount(Long userId);

	int insert(Message bean);

	int update(Long id);

	void update2(@Param("chatNo") Integer chatNo, @Param("toId") Long toId);
}