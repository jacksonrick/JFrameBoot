package com.jf.database.mapper;

import com.jf.database.model.Token;

/**
 * Test Interface
 * @date 2017年05月09日 上午 11:06:55
 * @author jfxu
 */
public interface TokenMapper {

    Token findByUid(String uid);

    Token findByToken(String token);

    int insert(Token token);

    int update(Token token);

    int delete(String uid);

}