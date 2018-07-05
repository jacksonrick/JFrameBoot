package com.jf.database.mapper;

import com.jf.database.model.User;
import com.jf.database.model.custom.BaseVo;
import com.jf.database.model.custom.IdText;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Test Interface
 * @date 2017年05月09日 上午 11:06:55
 * @author jfxu
 */
public interface TestMapper {

	List<User> findByCondition(BaseVo baseVo);

	User findById(Integer id);

	User findSimpleById(Integer id);

	int findCountByKey(@Param("key") String key, @Param("val") String val);

	User findByNameAndPwd(@Param("account") String account, @Param("password") String password);

	Object findFieleByUserId(@Param("userId") Integer userId, @Param("field") String field);

	List<IdText> findUserLikePhone(String phone);

	int insert(User bean);

	int update(User bean);

	int delete(Integer id);

	int deleteBatch(@Param("ids") Integer[] ids);



	List<IdText> findAll();

	User findUserById(Integer id);

	User findUserById2(Integer id);

	User findUserById3(Integer id);

	int insertUser(User user);

	int insertUser2(User user);

	int insertUser3(User user);

}