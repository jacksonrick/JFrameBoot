package com.jf.mapper;

import com.jf.model.custom.BaseVo;
import com.jf.model.User;
import com.jf.model.custom.IdText;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * UserMapper Interface
 *
 * @author rick
 */
public interface UserMapper {

    List<User> findByCondition(BaseVo baseVo);

    User findById(Long id);

    User findSimpleById(Long id);

    int findCountByKey(@Param("key") String key, @Param("val") String val);

    User findByNameAndPwd(@Param("account") String account, @Param("password") String password);

    Object findFieleByUserId(@Param("userId") Long userId, @Param("field") String field);

    List<IdText> findUserLikePhone(String phone);

    int insert(User bean);

    int update(User bean);

    int delete(Long id);

    int deleteBatch(@Param("ids") Long[] ids);

}
