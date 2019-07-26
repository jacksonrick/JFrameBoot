package com.jf.sdk.oss;

import com.aliyun.oss.HttpMethod;
import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.model.GeneratePresignedUrlRequest;
import com.aliyun.oss.model.OSSObject;
import com.aliyun.oss.model.PutObjectResult;
import com.jf.commons.LogManager;
import com.jf.date.DateUtil;
import com.jf.string.StringUtil;

import java.io.*;
import java.net.URL;
import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * Description: 阿里云OSS操作工具
 * User: xujunfei
 * Date: 2019-07-24
 * Time: 14:09
 */
public class OSSUtil {

    private static String endpoint = "http://oss-cn-hangzhou.aliyuncs.com";
    private static String accessKeyId = "";
    private static String accessKeySecret = "";
    private static String bucketName = "myalioss-pri";

    /**
     * 上传文件
     *
     * @param path
     */
    public static String upload(String path) {
        OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);
        File file = new File(path);
        try (FileInputStream is = new FileInputStream(file)) {
            String filename = StringUtil.randomFilename(file.getName());
            PutObjectResult result = ossClient.putObject(bucketName, filename, is);
            System.out.println(result.getResponse());
            ossClient.shutdown();
            return filename;
        } catch (Exception e) {
            throw new RuntimeException("上传失败", e);
        }
    }

    /**
     * 获取临时外链
     *
     * @param filename
     * @return
     */
    public static String getLink(String filename) {
        OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);
        GeneratePresignedUrlRequest request = new GeneratePresignedUrlRequest(bucketName, filename, HttpMethod.GET);
        Date expire = DateUtil.dateAddDay(new Date(), 3);
        request.setExpiration(expire);
        URL url = ossClient.generatePresignedUrl(request);
        return url.toString();
    }

    /**
     * 下载文件
     *
     * @param filename
     * @param path
     */
    public static void download(String filename, String path) {
        OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);
        OSSObject ossObject = ossClient.getObject(bucketName, filename);
        try (FileOutputStream os = new FileOutputStream(new File(path + "/" + filename));
             InputStream is = ossObject.getObjectContent();) {
            byte[] bytes = new byte[1024];
            int len = 0;
            while ((len = is.read(bytes, 0, bytes.length)) != -1) {
                os.write(bytes, 0, bytes.length);
            }
            os.flush();
            ossClient.shutdown();
        } catch (Exception e) {
            throw new RuntimeException("下载失败", e);
        }
    }

}
