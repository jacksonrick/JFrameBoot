package com.jf.service.job;

import com.jf.system.conf.LogManager;
import org.springframework.stereotype.Service;

/**
 * 任务计划Service
 *
 * @author rick
 */
@Service
public class JobService {

    public void test(String name) {
        LogManager.info(name + "任务计划测试", this.getClass());
    }

}
