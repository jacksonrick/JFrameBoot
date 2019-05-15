package com.jf.sdk.fdfs.service;

import com.jf.sdk.fdfs.domain.MetaData;
import com.jf.sdk.fdfs.domain.StorePath;

import java.io.InputStream;
import java.util.Set;

/**
 * 面向普通应用的文件操作接口封装
 * 
 * @author tobato
 *
 */
public interface FastFileStorageClient extends GenerateStorageClient {


  
     StorePath uploadFile(byte[] content, String fileExtName);
  
    /**
     * 上传一般文件
     * 
     * @param inputStream
     * @param fileSize
     * @param fileExtName
     * @param metaDataSet
     * @return
     */
    StorePath uploadFile(InputStream inputStream, long fileSize, String fileExtName, Set<MetaData> metaDataSet);

    /**
     * 删除文件
     * 
     * @param filePath 文件路径(groupName/path)
     */
    void deleteFile(String filePath);

}
