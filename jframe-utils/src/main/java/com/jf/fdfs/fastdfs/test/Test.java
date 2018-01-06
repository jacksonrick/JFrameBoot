/**
 * Copyright (C) 2008 Happy Fish / YuQing
 * <p>
 * FastDFS Java Client may be copied only under the terms of the GNU Lesser
 * General Public License (LGPL).
 * Please visit the FastDFS Home Page http://www.csource.org/ for more detail.
 **/

package com.jf.fdfs.fastdfs.test;

import com.jf.fdfs.common.NameValuePair;
import com.jf.fdfs.fastdfs.*;

/**
 * client test
 *
 * @author Happy Fish / YuQing
 * @version Version 1.18
 */
public class Test {
    private Test() {
    }

    /**
     * entry point
     *
     * @param args comand arguments
     *             <ul><li>args[0]: config filename</li></ul>
     *             <ul><li>args[1]: local filename to upload</li></ul>
     */
    public static void main(String args[]) {
        System.out.println("java.version=" + System.getProperty("java.version"));

        String local_filename = "/Users/xujunfei/Desktop/Picture/zb/606e20a4462309f70f37f2cd770e0cf3d6cad6bd.jpg";

        try {
            ClientGlobal.init();
            System.out.println("network_timeout=" + ClientGlobal.network_timeout + "ms");
            System.out.println("charset=" + ClientGlobal.charset);

            TrackerClient tracker = new TrackerClient();
            TrackerServer trackerServer = tracker.getConnection();
            StorageServer storageServer = null;
            StorageClient1 client = new StorageClient1(trackerServer, storageServer);

            NameValuePair[] metaList = new NameValuePair[1];
            metaList[0] = new NameValuePair("fileName", local_filename);
            String fileId = client.upload_file1(local_filename, null, metaList);
            System.out.println("upload success. file id is: " + fileId);

            int i = 0;
            while (i++ < 10) {
                byte[] result = client.download_file1(fileId);
                System.out.println(i + ", download result is: " + result.length);
            }

            trackerServer.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
