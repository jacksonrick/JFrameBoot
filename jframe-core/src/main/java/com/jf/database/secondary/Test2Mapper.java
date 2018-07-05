package com.jf.database.secondary;

import com.jf.database.model.User;
import com.jf.database.model.custom.BaseVo;

import java.util.List;

/**
 * UserMapper Interface
 *
 * @author rick
 */
public interface Test2Mapper {

    List<User> findByCondition(BaseVo baseVo);

    User findById(Integer id);

    int insert(User bean);

    int update(User bean);

    int delete(Integer id);

}
