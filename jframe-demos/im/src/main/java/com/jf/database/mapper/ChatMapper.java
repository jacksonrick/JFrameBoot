package com.jf.database.mapper;

import com.jf.database.model.Chat;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * ChatMapper Interface
 *
 * @author jfxu
 * @date 2020年07月30日 下午 16:36:38
 */
public interface ChatMapper {

    List<Chat> findByUserId(Long userId);

    Chat findChatByUidAndOid(@Param("userId") Long userId, @Param("otherId") Long otherId);

    Integer findChatNoByUidAndOid(@Param("userId") Long userId, @Param("otherId") Long otherId);

    Long findOidByChatNoAndUid(@Param("chatNo") Integer chatNo, @Param("userId") Long userId);

    Long findUidByChatNoAndOid(@Param("chatNo") Integer chatNo, @Param("otherId") Long otherId);

    Integer findMaxChatNo();

    int insert(Chat chat);

    int update(@Param("chatNo") Integer chatNo, @Param("msgId") Long msgId);

}