package com.jf.sdk.fdfs.proto.storage;

import com.jf.sdk.fdfs.proto.AbstractFdfsCommand;
import com.jf.sdk.fdfs.proto.FdfsResponse;
import com.jf.sdk.fdfs.proto.storage.internal.StorageTruncateRequest;

/**
 * 文件Truncate命令
 * 
 * @author tobato
 *
 */
public class StorageTruncateCommand extends AbstractFdfsCommand<Void> {

    /**
     * 文件Truncate命令
     * 
     * @param groupName
     * @param path
     */
    public StorageTruncateCommand(String path, long fileSize) {
        super();
        this.request = new StorageTruncateRequest(path, fileSize);
        // 输出响应
        this.response = new FdfsResponse<Void>() {
            // default response
        };
    }

}
