package com.jf.system.uid;

import com.jf.uid.WorkerIdAssigner;
import com.jf.uid.impl.CachedUidGenerator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: xujunfei
 * Date: 2020-06-23
 * Time: 10:10
 */
@Configuration
public class CachedUidGeneratorConfig {

    @Bean
    public WorkerIdAssigner workerIdAssigner() {
        return new SingleWorkerIdAssigner();
    }

    @Bean
    public CachedUidGenerator cachedUidGenerator(WorkerIdAssigner workerIdAssigner) {
        CachedUidGenerator generator = new CachedUidGenerator();
        generator.setWorkerIdAssigner(workerIdAssigner);
        //generator.setTimeBits(29);
        //generator.setEpochStr();
        generator.setBoostPower(3);
        generator.setScheduleInterval(60L);
        return generator;
    }

}
