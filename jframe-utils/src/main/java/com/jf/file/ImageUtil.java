package com.jf.file;

import com.jf.exception.SysException;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;

/**
 * 图片工具类
 * Created by xujunfei on 2017/6/7.
 */
public class ImageUtil {

    /*public static void main(String[] args) {
        File file = new File("/Users/xujunfei/Downloads/static/test/2.png");
        File out = new File("/Users/xujunfei/Downloads/static/test/2-1.png");
        InputStream is = null;
        try {
            is = new FileInputStream(file);
            FileUtils.copyInputStreamToFile(new ByteArrayInputStream(ImageUtil.waterMark(is, "JFrame", "png")), out);
        } catch (Exception e) {

        }
    }*/

    /**
     * 添加图片水印
     *
     * @param stream
     * @param ext
     * @return
     */
    public static byte[] waterMark(InputStream stream, String ext) {
        byte[] b = null;
        try {
            // 读取原图片信息
            Image srcImg = ImageIO.read(stream);
            int width = srcImg.getWidth(null);
            int height = srcImg.getHeight(null);
            // 加水印
            BufferedImage bufImg = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
            Graphics2D g = bufImg.createGraphics();
            g.drawImage(srcImg, 0, 0, width, height, null);
            // Logo
            Image imageLogo = ImageIO.read(ImageUtil.class.getClassLoader().getResourceAsStream("logo.png"));
            int width1 = imageLogo.getWidth(null);
            int height1 = imageLogo.getHeight(null);

            // 最小
            if (width <= 150) width1 = 40;
            if (height <= 150) height1 = 16;

            int x = width - width1 - 5;
            int y = height - height1 - 5;

            g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_ATOP, 0.8F));
            g.drawImage(imageLogo, x, y, width1, height1, null);
            g.dispose();
            // 输出图片
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            ImageIO.write(bufImg, ext, out);
            b = out.toByteArray();
            out.flush();
            out.close();
            return b;
        } catch (Exception e) {
            throw new SysException(e.getMessage(), e);
        }
    }

    /**
     * 添加文字水印
     *
     * @param stream
     * @param content
     * @param ext
     * @return
     */
    public static byte[] waterMark(InputStream stream, String content, String ext) {
        byte[] b = null;
        try {
            // 读取原图片信息
            Image srcImg = ImageIO.read(stream);
            int width = srcImg.getWidth(null);
            int height = srcImg.getHeight(null);
            // 加水印
            BufferedImage bufImg = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
            Graphics2D g = bufImg.createGraphics();
            g.drawImage(srcImg, 0, 0, width, height, null);

            Font font = new Font("Default", Font.PLAIN, 50);
            g.setColor(Color.WHITE);
            g.setFont(font);
            int x = width - getWatermarkLength(content, g) - 8;
            int y = height - 8;
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
            throw new SysException(e.getMessage(), e);
        }
    }

    private static int getWatermarkLength(String waterMarkContent, Graphics2D g) {
        return g.getFontMetrics(g.getFont()).charsWidth(waterMarkContent.toCharArray(), 0, waterMarkContent.length());
    }

}
