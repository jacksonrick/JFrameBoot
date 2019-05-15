package com.jf.sdk.fdfs.proto.tracker;

import com.jf.sdk.fdfs.domain.StorageNodeInfo;
import com.jf.sdk.fdfs.proto.AbstractFdfsCommand;
import com.jf.sdk.fdfs.proto.FdfsResponse;
import com.jf.sdk.fdfs.proto.tracker.internal.TrackerGetFetchStorageRequest;

/**
 * 获取源服务器
 * 
 * @author tobato
 *
 */
public class TrackerGetFetchStorageCommand extends AbstractFdfsCommand<StorageNodeInfo> {

    public TrackerGetFetchStorageCommand(String groupName, String path, boolean toUpdate) {
        super.request = new TrackerGetFetchStorageRequest(groupName, path, toUpdate);
        super.response = new FdfsResponse<StorageNodeInfo>() {
            // default response
        };
    }

}
