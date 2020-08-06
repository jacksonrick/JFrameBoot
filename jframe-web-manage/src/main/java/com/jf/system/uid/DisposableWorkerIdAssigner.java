package com.jf.system.uid;

import com.jf.entity.WorkerNodeEntity;
import com.jf.uid.WorkerIdAssigner;
import com.jf.uid.util.NetUtils;
import org.apache.commons.lang3.RandomUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Resource;

/**
 * Represents an implementation of {@link WorkerIdAssigner},
 * the worker id will be discarded after assigned to the UidGenerator
 *
 * @author yutianbao
 */
public class DisposableWorkerIdAssigner implements WorkerIdAssigner {
    private static final Logger LOGGER = LoggerFactory.getLogger(DisposableWorkerIdAssigner.class);

    /*@Resource
    private Mapper mapper;*/

    /**
     * Assign worker id base on database.<p>
     * If there is host name & port in the environment, we considered that the node runs in Docker container<br>
     * Otherwise, the node runs on an actual machine.
     *
     * @return assigned worker id
     */
    public long assignWorkerId() {
        WorkerNodeEntity workerNodeEntity = new WorkerNodeEntity();
        workerNodeEntity.setHostname(NetUtils.getLocalAddress());
        workerNodeEntity.setPort(System.currentTimeMillis() + "-" + RandomUtils.nextInt(1000, 100000));
        //mapper.addWorkerNode(workerNodeEntity);

        LOGGER.info("Add worker node:" + workerNodeEntity);
        return workerNodeEntity.getId();
    }

    /**
     * mybatis & sql
     <insert id="addWorkerNode" parameterType="com.jf.system.uid.entity.WorkerNodeEntity" useGeneratedKeys="true" keyProperty="id">
        INSERT INTO worker_node (hostname,port) VALUES (#{hostname}, #{port})
     </insert>

     // postgresql
     CREATE TABLE worker_node (
         id int8 NOT NULL GENERATED BY DEFAULT AS IDENTITY,
         hostname varchar(255) NOT NULL,
         port varchar(255) NOT NULL,
         crtdate timestamp(3) DEFAULT now(),
         CONSTRAINT worker_node_pkey PRIMARY KEY (id)
     )
     */
}


