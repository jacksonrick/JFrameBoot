package com.jf.common;

import com.jf.date.DateUtil;
import com.jf.entity.CaptchaConfig;
import com.jf.entity.UploadRet;
import com.jf.file.CaptchaUtil;
import com.jf.sdk.fdfs.domain.StorePath;
import com.jf.sdk.fdfs.service.AppendFileStorageClient;
import com.jf.sdk.fdfs.service.FastFileStorageClient;
import com.jf.string.StringUtil;
import com.jf.system.conf.IConstant;
import com.jf.system.conf.SysConfig;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * 通用工具类控制器
 * <p> 验证码、编辑器、文件上传 </p>
 *
 * @author rick
 * @version 2.0
 */
@Controller
public class CommonController {

    @Autowired
    private SysConfig sysConfig;

    @Autowired(required = false)
    private FastFileStorageClient storageClient;
    @Autowired(required = false)
    private AppendFileStorageClient appendFileStorageClient;

    /**
     * 错误页面
     *
     * @return
     */
    @GetMapping("/error/{path}")
    public String error(@PathVariable("path") String path) {
        return "error/" + path;
    }

    /**
     * 获取验证码
     * <p> sessionName:Constant.SESSION_RAND </p>
     *
     * @throws IOException
     */
    @GetMapping("/getValidCode")
    public void getValidCode(HttpServletRequest request, HttpServletResponse response) throws IOException {
        CaptchaConfig config = new CaptchaConfig(6, 100, 35);
        char[] chars = CaptchaUtil.getTextChars(config);
        request.getSession().setAttribute(IConstant.SESSION_RAND, new String(chars));
        // 输出流方式的验证码，需要先获取文本，再输出
        CaptchaUtil.toStream(chars, response.getOutputStream(), config);
    }

    /**
     * 获取base64验证码
     *
     * @param request
     * @return
     */
    @GetMapping("/getValidCodeStr")
    @ResponseBody
    public String getValidCodeStr(HttpServletRequest request) {
        // 默认配置：CaptchaUtil.DEFAULT_CONFIG
        ImmutablePair<String, String> pair = CaptchaUtil.toBase64();
        // pair.getKey() 验证码明文，可以存放在数据库如redis
        request.getSession().setAttribute(IConstant.SESSION_RAND, pair.getKey());
        return pair.getValue();
    }

    /**
     * kindEditor上传图片
     *
     * @param file
     * @param request
     * @return
     * @throws IOException
     */
    @PostMapping("/uploadwithKE")
    @ResponseBody
    public UploadRet uploadwithKE(@RequestParam("imgFile") MultipartFile file, HttpServletRequest request) throws IOException {
        // 文件限制
        long imgSize = sysConfig.getUpload().getImgSize() * 1024 * 1024;
        if (file.getSize() > imgSize) {
            return new UploadRet(1, "", "单个文件最大" + sysConfig.getUpload().getImgSize() + "M");
        }
        // 文件后缀
        String suffix = StringUtil.getFileType(file.getOriginalFilename());
        if (!Arrays.asList(sysConfig.getUpload().getImgType()).contains(suffix.toLowerCase())) {
            return new UploadRet(1, "", "文件格式不支持");
        }

        if (!sysConfig.getUpload().getFdfs()) {
            String basePathFormat = DateUtil.getYearAndMonth(false);
            String uploadPath = sysConfig.getStaticPath() + "upload/" + basePathFormat;
            String filename = StringUtil.randomFilename(file.getOriginalFilename());
            // String realPath = request.getSession().getServletContext().getRealPath("/" + uploadPath + basePathFormat);
            File filePath = new File(uploadPath);
            if (!filePath.exists()) {
                filePath.mkdirs();
            }
            FileUtils.copyInputStreamToFile(file.getInputStream(), new File(filePath, filename));
            return new UploadRet(0, sysConfig.getStaticHost() + "static/upload/" + basePathFormat + "/" + filename, "SUCCESS");
        } else {
            // 添加水印：FDFSUtil.waterMark(file.getInputStream(), suffix)
            StorePath storePath = storageClient.uploadFile(file.getBytes(), suffix);
            String filePath = sysConfig.getFdfsNginx() + storePath.getFullPath();
            return new UploadRet(0, filePath, "SUCCESS");
        }
    }

    /**
     * Ueditor上传
     *
     * @param action
     * @param file
     * @param request
     * @return
     * @throws IOException
     */
    @PostMapping("/uploadwithUE")
    @ResponseBody
    public Map<String, Object> uploadwithUE(String action, @RequestParam(name = "imgFile", required = false) MultipartFile file, HttpServletRequest request) throws IOException {
        Map<String, Object> map = new HashMap();
        if ("sysConfig".equals(action)) {
            map.put("imageActionName", "uploadimage");
            map.put("imageFieldName", "imgFile");
            map.put("imageAllowFiles", new String[]{".png", ".jpg", ".jpeg"});
            map.put("imageMaxSize", "2048000");
            map.put("imagePath", "");
            map.put("imageUrlPrefix", "");
            return map;
        }

        if (!sysConfig.getUpload().getFdfs()) {
            String basePathFormat = DateUtil.getYearAndMonth(false);
            String uploadPath = sysConfig.getStaticPath() + "upload/" + basePathFormat;
            String filename = StringUtil.randomFilename(file.getOriginalFilename());
            File filePath = new File(uploadPath);
            if (!filePath.exists()) {
                filePath.mkdirs();
            }
            FileUtils.copyInputStreamToFile(file.getInputStream(), new File(filePath, filename));
            map.put("url", sysConfig.getStaticHost() + "static/upload/" + basePathFormat + "/" + filename);
        } else {
            // 文件后缀
            String suffix = StringUtil.getFileType(file.getOriginalFilename());
            StorePath storePath = storageClient.uploadFile(file.getBytes(), suffix);
            String filePath = sysConfig.getFdfsNginx() + storePath.getFullPath();
            map.put("url", filePath);
        }
        map.put("state", "SUCCESS");
        return map;
    }

    /**
     * 上传
     *
     * @param file
     * @param t       1-图片 2-文件
     * @param request
     * @return
     * @throws IOException
     */
    @PostMapping("/upload")
    @ResponseBody
    public UploadRet upload(@RequestParam("file") MultipartFile file, Integer t, HttpServletRequest request) throws IOException {
        // 文件后缀
        String suffix = StringUtil.getFileType(file.getOriginalFilename());
        String[] type;
        long size;

        if (t == null) t = 1;
        if (t == 2) { // 文件
            type = sysConfig.getUpload().getFileType();
            size = sysConfig.getUpload().getFileSize();
        } else { //图片
            type = sysConfig.getUpload().getImgType();
            size = sysConfig.getUpload().getImgSize();
        }
        // 文件大小
        if (file.getSize() > size * 1024 * 1024) {
            return new UploadRet(1, "", "单个文件最大" + size + "MB");
        }
        // 文件格式
        if (!Arrays.asList(type).contains(suffix.toLowerCase())) {
            return new UploadRet(1, "", "文件格式不支持");
        }

        if (!sysConfig.getUpload().getFdfs()) {
            String basePathFormat = DateUtil.getYearAndMonth(false);
            String dirPath = "upload/" + basePathFormat;
            if (t == 2) { // 文件
                dirPath = "upload/file/" + basePathFormat;
            }
            String uploadPath = sysConfig.getStaticPath() + dirPath;
            String filename = StringUtil.randomFilename(file.getOriginalFilename());
            // String realPath = request.getSession().getServletContext().getRealPath("/" + uploadPath + basePathFormat);
            File filePath = new File(uploadPath);
            if (!filePath.exists()) {
                filePath.mkdirs();
            }
            FileUtils.copyInputStreamToFile(file.getInputStream(), new File(filePath, filename));
            // 添加水印
            // FileUtils.copyInputStreamToFile(new ByteArrayInputStream(FDFSUtil.waterMark(file.getInputStream(), suffix)), new File(filePath, filename));
            return new UploadRet(0, sysConfig.getStaticHost() + "static/" + dirPath + "/" + filename, "SUCCESS");
        } else {
            // 添加水印：FDFSUtil.waterMark(file.getInputStream(), suffix)
            StorePath storePath = storageClient.uploadFile(file.getBytes(), suffix);
            String filePath = sysConfig.getFdfsNginx() + storePath.getFullPath();
            return new UploadRet(0, filePath, "SUCCESS");
        }
    }

    /**
     * 断点续传【测试】
     * <pre>文件上传前需要进行分片</pre>
     *
     * @param file    文件域
     * @param append  是否追加
     * @param path    返回路径，用于追加文件
     * @param request
     * @return
     * @throws IOException
     */
    @PostMapping("/uploadAppend")
    @ResponseBody
    public UploadRet uploadAppend(@RequestParam("file") MultipartFile file, Boolean append,
                                  String path, HttpServletRequest request) throws IOException {
        String suffix = StringUtil.getFileType(file.getOriginalFilename());
        if (append) {
            appendFileStorageClient.appendFile("group1", path, file.getInputStream(), file.getSize());
            return new UploadRet(0, path, "APPEND");
        } else {
            StorePath store = appendFileStorageClient.uploadAppenderFile("group1", file.getInputStream(),
                    file.getSize(), suffix);
            return new UploadRet(0, store.getPath(), "SUCCESS");
        }
    }

}
