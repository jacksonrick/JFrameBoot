package com.jf.fdfs;

import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;

/**
 * Created by xujunfei on 2017/6/7.
 */
public class FDFSUtil {

    /**
     * 添加图片水印
     *
     * @param file
     * @param ext
     * @return
     */
    public static byte[] waterMark(MultipartFile file, String ext) {
        String logoFile = "/logo.png";
        byte[] b = null;
        try {
            // 读取原图片信息
            Image srcImg = ImageIO.read(file.getInputStream());
            int width = srcImg.getWidth(null);
            int height = srcImg.getHeight(null);
            // 加水印
            BufferedImage bufImg = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
            Graphics2D g = bufImg.createGraphics();
            g.drawImage(srcImg, 0, 0, width, height, null);
            // Logo
            Image imageLogo = ImageIO.read(FDFSUtil.class.getResourceAsStream(logoFile));
            int width1 = imageLogo.getWidth(null);
            int height1 = imageLogo.getHeight(null);
            int widthDiff = width - width1;
            int heightDiff = height - height1;
            int x = 10;
            int y = 10;
            if (x > widthDiff) {
                x = widthDiff;
            }
            if (y > heightDiff) {
                y = heightDiff;
            }

            g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_ATOP, 0.8F));
            g.drawImage(imageLogo, x, y, null);
            g.dispose();
            // 输出图片
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            ImageIO.write(bufImg, ext, out);
            b = out.toByteArray();
            out.flush();
            out.close();
            return b;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return b;
    }

    /**
     * 添加文字水印
     *
     * @param file
     * @param content
     * @param ext
     * @return
     */
    public static byte[] waterMark(MultipartFile file, String content, String ext) {
        byte[] b = null;
        try {
            // 读取原图片信息
            Image srcImg = ImageIO.read(file.getInputStream());
            int width = srcImg.getWidth(null);
            int height = srcImg.getHeight(null);
            // 加水印
            BufferedImage bufImg = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
            Graphics2D g = bufImg.createGraphics();
            g.drawImage(srcImg, 0, 0, width, height, null);

            Font font = new Font("Default", Font.PLAIN, 50);
            g.setColor(Color.WHITE);
            g.setFont(font);
            int x = width - getWatermarkLength(content, g) - 3;
            int y = height - 3;
            g.drawString(content, x, y);
            g.dispose();
            // 输出图片
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            ImageIO.write(bufImg, ext, out);
            b = out.toByteArray();
            out.flush();
            out.close();
            return b;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return b;
    }

    private static int getWatermarkLength(String waterMarkContent, Graphics2D g) {
        return g.getFontMetrics(g.getFont()).charsWidth(waterMarkContent.toCharArray(), 0, waterMarkContent.length());
    }

}
