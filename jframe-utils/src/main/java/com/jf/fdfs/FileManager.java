package com.jf.fdfs;

import com.jf.fdfs.common.NameValuePair;
import com.jf.fdfs.fastdfs.*;

/**
 * Created by xujunfei on 2017/6/7.
 */
public class FileManager {

    private static final long serialVersionUID = 1L;
    private static TrackerClient trackerClient;
    private static TrackerServer trackerServer;
    private static StorageServer storageServer;
    private static StorageClient storageClient;

    static {
        try {
            ClientGlobal.init();
            trackerClient = new TrackerClient();
            trackerServer = trackerClient.getConnection();
            storageClient = new StorageClient(trackerServer, storageServer);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static String upload(FastDFSFile file, NameValuePair[] valuePairs) {
        String[] uploadResults = null;
        try {
            synchronized (storageClient) {
                uploadResults = storageClient.upload_file(file.getContent(), file.getExt(), valuePairs);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        String groupName = uploadResults[0];
        String remoteFileName = uploadResults[1];

        return ClientGlobal.nginx_server + "/" + groupName + "/" + remoteFileName;
    }

}
