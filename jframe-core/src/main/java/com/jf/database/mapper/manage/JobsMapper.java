package com.jf.database.mapper.manage;

import com.jf.database.model.manage.Jobs;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: xujunfei
 * Date: 2018-07-06
 * Time: 11:31
 */
public interface JobsMapper {

    List<Jobs> findJobAndTriggerDetails();

    Integer findCountByName(String name);

}
