package com.jf.sdk.fdfs.proto.storage;

import com.jf.sdk.fdfs.proto.AbstractFdfsCommand;
import com.jf.sdk.fdfs.proto.FdfsResponse;
import com.jf.sdk.fdfs.proto.storage.internal.StorageDeleteFileRequest;

/**
 * 文件删除命令
 * 
 * @author tobato
 *
 */
public class StorageDeleteFileCommand extends AbstractFdfsCommand<Void> {

    /**
     * 文件删除命令
     * 
     * @param storeIndex 存储节点
     * @param inputStream 输入流
     * @param fileExtName 文件扩展名
     * @param size 文件大小
     * @param isAppenderFile 是否添加模式
     */
    public StorageDeleteFileCommand(String groupName, String path) {
        super();
        this.request = new StorageDeleteFileRequest(groupName, path);
        // 输出响应
        this.response = new FdfsResponse<Void>() {
            // default response
        };
    }

}
