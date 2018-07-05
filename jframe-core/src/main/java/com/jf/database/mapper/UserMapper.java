package com.jf.database.mapper;

import com.jf.database.model.User;
import com.jf.database.model.custom.BaseVo;
import com.jf.database.model.custom.IdText;
import com.jf.database.model.excel.UserModel;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * UserMapper Interface
 *
 * @author rick
 */
public interface UserMapper {

    List<User> findByCondition(BaseVo baseVo);

    User findById(Integer id);

    User findSimpleById(Integer id);

    int findCountByKey(@Param("key") String key, @Param("val") String val);

    User findByNameAndPwd(@Param("account") String account, @Param("password") String password);

    Object findFieleByUserId(@Param("userId") Integer userId, @Param("field") String field);

    List<IdText> findUserLikePhone(String phone);

    List<UserModel> findForExcel(BaseVo baseVo);

    int insert(User bean);

    int update(User bean);

    int delete(Integer id);

    int deleteBatch(@Param("ids") Integer[] ids);

}
