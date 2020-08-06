package com.jf.database.mapper;

import com.jf.database.model.User;

/**
 * UserMapper Interface
 *
 * @date 2020年07月30日 上午 10:42:59
 * @author jfxu
 */
public interface UserMapper {

	String findNameById(Long id);

    User findById(Long id);
}