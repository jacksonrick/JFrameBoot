package com.jf.system.uid;

import com.jf.uid.WorkerIdAssigner;
import org.springframework.stereotype.Service;

/**
 * Created with IntelliJ IDEA.
 * Description: 单点服务workerID
 * User: xujunfei
 * Date: 2020-06-23
 * Time: 10:10
 */
@Service
public class SingleWorkerIdAssigner implements WorkerIdAssigner {

    public long assignWorkerId() {
        return 1l;
    }

}
