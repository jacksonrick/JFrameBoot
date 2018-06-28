package com.jf.database.mapper;

import com.jf.database.model.User;
import com.jf.database.model.custom.IdText;

import java.util.List;

/**
 * Test Interface
 * @date 2017年05月09日 上午 11:06:55
 * @author jfxu
 */
public interface TestMapper {

	List<IdText> findAll();

	User findUserById(Integer id);

	User findUserById2(Integer id);

	User findUserById3(Integer id);

	int insertUser(User user);

	int insertUser2(User user);

	int insertUser3(User user);

}