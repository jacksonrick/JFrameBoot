package com.jf.sdk.fdfs.proto.tracker;

import com.jf.sdk.fdfs.proto.AbstractFdfsCommand;
import com.jf.sdk.fdfs.proto.FdfsResponse;
import com.jf.sdk.fdfs.proto.tracker.internal.TrackerDeleteStorageRequest;

/**
 * 移除存储服务器命令
 * 
 * @author tobato
 *
 */
public class TrackerDeleteStorageCommand extends AbstractFdfsCommand<Void> {

    public TrackerDeleteStorageCommand(String groupName, String storageIpAddr) {
        super.request = new TrackerDeleteStorageRequest(groupName, storageIpAddr);
        super.response = new FdfsResponse<Void>() {
            // default response
        };
    }

}
