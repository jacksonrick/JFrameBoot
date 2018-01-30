package com.jf.cluster;

import com.jf.model.User;
import com.jf.model.custom.BaseVo;
import com.jf.model.custom.IdText;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * UserMapper Interface
 *
 * @author rick
 */
public interface User2Mapper {

    List<User> findByCondition(BaseVo baseVo);

    User findById(Long id);

    int insert(User bean);

    int update(User bean);

    int delete(Long id);

}
