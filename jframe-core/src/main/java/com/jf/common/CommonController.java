package com.jf.common;

import com.jf.date.DateUtil;
import com.jf.entity.UploadRet;
import com.jf.string.StringUtil;
import com.jf.system.conf.SysConfig;
import com.luhuiguo.fastdfs.domain.StorePath;
import com.luhuiguo.fastdfs.service.FastFileStorageClient;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

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
    private SysConfig config;
    @Autowired(required = false)
    private FastFileStorageClient storageClient;

    /**
     * 错误页面
     *
     * @return
     */
    @RequestMapping("/error/{path}")
    public String error(@PathVariable("path") String path) {
        return "error/" + path;
    }

    /**
     * 获取验证码
     * <p> sessionName:Constant.SESSION_RAND </p>
     * <p> 6位数字 </p>
     *
     * @throws IOException
     */
    @RequestMapping("/getValidCode")
    public void getValidCode(HttpServletRequest request, HttpServletResponse response) {
        try {
            BufferedImage bi = new BufferedImage(100, 35, BufferedImage.TYPE_INT_RGB);
            Graphics g = bi.getGraphics();
            Color c = new Color(127, 255, 212);
            g.setColor(c);
            int width = 100;
            int height = 35;
            g.fillRect(0, 0, width, height);
            g.setFont(new Font("Default", Font.PLAIN, 24));
            Random random = new Random();
            // g.setColor(Color.BLACK);
            int red = 0, green = 0, blue = 0;
            for (int i = 0; i < 20; i++) {
                int xs = random.nextInt(width);
                int ys = random.nextInt(height);
                int xe = xs + random.nextInt(width / 8);
                int ye = ys + random.nextInt(height / 8);
                red = random.nextInt(255);
                green = random.nextInt(255);
                blue = random.nextInt(255);
                g.setColor(new Color(red, green, blue));
                g.drawLine(xs, ys, xe, ye);
            }

            char[] ch = "0123456789".toCharArray();
            int len = ch.length, index;
            StringBuffer sb = new StringBuffer();
            // 验证码位数：5
            for (int i = 0; i < 5; i++) {
                Graphics2D gg = (Graphics2D) g.create();
                gg.translate((i * 15) + 14, 20);
                gg.rotate(random.nextInt(60) * Math.PI / 180);

                index = random.nextInt(len);
                gg.setColor(new Color(random.nextInt(100), random.nextInt(180), random.nextInt(255)));
                gg.drawString(ch[index] + "", 0, 0);
                sb.append(ch[index]);
            }
            request.getSession().setAttribute(SysConfig.SESSION_RAND, sb.toString());
            ImageIO.write(bi, "JPG", response.getOutputStream());
            g.dispose();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * kindEditor上传图片
     *
     * @param file
     * @param request
     * @return
     * @throws IOException
     */
    @RequestMapping(value = "/uploadwithKE", method = RequestMethod.POST)
    @ResponseBody
    public UploadRet uploadwithKE(@RequestParam("imgFile") MultipartFile file, HttpServletRequest request) throws IOException {
        // 文件限制
        long imgSize = config.getUpload().getImgSize() * 1024 * 1024;
        if (file.getSize() > imgSize) {
            return new UploadRet(1, "", "单个文件最大" + config.getUpload().getImgSize() + "M");
        }
        // 文件后缀
        String suffix = StringUtil.getFileType(file.getOriginalFilename());
        if (!Arrays.asList(config.getUpload().getImgType()).contains(suffix.toLowerCase())) {
            return new UploadRet(1, "", "文件格式不支持");
        }

        if (!config.getUpload().getFdfs()) {
            String basePathFormat = DateUtil.getYearAndMonth(false);
            String uploadPath = config.getStaticPath() + "upload/" + basePathFormat;
            String filename = StringUtil.randomFilename(file.getOriginalFilename());
            // String realPath = request.getSession().getServletContext().getRealPath("/" + uploadPath + basePathFormat);
            File filePath = new File(uploadPath);
            if (!filePath.exists()) {
                filePath.mkdirs();
            }
            FileUtils.copyInputStreamToFile(file.getInputStream(), new File(filePath, filename));
            return new UploadRet(0, config.getStaticHost() + "static/upload/" + basePathFormat + "/" + filename, "SUCCESS");
        } else {
            // 添加水印：FDFSUtil.waterMark(file.getInputStream(), suffix)
            StorePath storePath = storageClient.uploadFile(file.getBytes(), suffix);
            String filePath = config.getFdfsNginx() + storePath.getFullPath();
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
    @RequestMapping(value = "/uploadwithUE")
    @ResponseBody
    public Map<String, Object> uploadwithUE(String action, @RequestParam(name = "imgFile", required = false) MultipartFile file, HttpServletRequest request) throws IOException {
        Map<String, Object> map = new HashMap();
        if ("config".equals(action)) {
            map.put("imageActionName", "uploadimage");
            map.put("imageFieldName", "imgFile");
            map.put("imageAllowFiles", new String[]{".png", ".jpg", ".jpeg"});
            map.put("imageMaxSize", "2048000");
            map.put("imagePath", "");
            map.put("imageUrlPrefix", "");
            return map;
        }

        if (!config.getUpload().getFdfs()) {
            String basePathFormat = DateUtil.getYearAndMonth(false);
            String uploadPath = config.getStaticPath() + "upload/" + basePathFormat;
            String filename = StringUtil.randomFilename(file.getOriginalFilename());
            File filePath = new File(uploadPath);
            if (!filePath.exists()) {
                filePath.mkdirs();
            }
            FileUtils.copyInputStreamToFile(file.getInputStream(), new File(filePath, filename));
            map.put("url", config.getStaticHost() + "static/upload/" + basePathFormat + "/" + filename);
        } else {
            // 文件后缀
            String suffix = StringUtil.getFileType(file.getOriginalFilename());
            StorePath storePath = storageClient.uploadFile(file.getBytes(), suffix);
            String filePath = config.getFdfsNginx() + storePath.getFullPath();
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
    @RequestMapping(value = "/upload", method = RequestMethod.POST)
    @ResponseBody
    public UploadRet upload(@RequestParam("file") MultipartFile file, Integer t, HttpServletRequest request) throws IOException {
        // 文件后缀
        String suffix = StringUtil.getFileType(file.getOriginalFilename());
        String[] type;
        long size;

        if (t == null) t = 1;
        if (t == 2) { // 文件
            type = config.getUpload().getFileType();
            size = config.getUpload().getFileSize();
        } else { //图片
            type = config.getUpload().getImgType();
            size = config.getUpload().getImgSize();
        }
        // 文件大小
        if (file.getSize() > size * 1024 * 1024) {
            return new UploadRet(1, "", "单个文件最大" + size + "MB");
        }
        // 文件格式
        if (!Arrays.asList(type).contains(suffix.toLowerCase())) {
            return new UploadRet(1, "", "文件格式不支持");
        }

        if (!config.getUpload().getFdfs()) {
            String basePathFormat = DateUtil.getYearAndMonth(false);
            String dirPath = "upload/" + basePathFormat;
            if (t == 2) { // 文件
                dirPath = "upload/file/" + basePathFormat;
            }
            String uploadPath = config.getStaticPath() + dirPath;
            String filename = StringUtil.randomFilename(file.getOriginalFilename());
            // String realPath = request.getSession().getServletContext().getRealPath("/" + uploadPath + basePathFormat);
            File filePath = new File(uploadPath);
            if (!filePath.exists()) {
                filePath.mkdirs();
            }
            FileUtils.copyInputStreamToFile(file.getInputStream(), new File(filePath, filename));
            // 添加水印
            // FileUtils.copyInputStreamToFile(new ByteArrayInputStream(FDFSUtil.waterMark(file.getInputStream(), suffix)), new File(filePath, filename));
            return new UploadRet(0, config.getStaticHost() + "static/" + dirPath + "/" + filename, "SUCCESS");
        } else {
            // 添加水印：FDFSUtil.waterMark(file.getInputStream(), suffix)
            StorePath storePath = storageClient.uploadFile(file.getBytes(), suffix);
            String filePath = config.getFdfsNginx() + storePath.getFullPath();
            return new UploadRet(0, filePath, "SUCCESS");
        }
    }

}
