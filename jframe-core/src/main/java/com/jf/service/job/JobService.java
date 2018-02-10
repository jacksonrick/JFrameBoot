package com.jf.service.job;

import com.jf.system.LogManager;
import org.springframework.stereotype.Service;

/**
 * 任务计划Service
 *
 * @author rick
 */
@Service
public class JobService {

    public void test() {
        LogManager.info("任务计划测试", this.getClass());
    }

}
