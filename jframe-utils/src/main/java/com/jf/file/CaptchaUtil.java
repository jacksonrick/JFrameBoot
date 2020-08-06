package com.jf.file;

import com.jf.entity.CaptchaConfig;
import com.jf.math.RandomUtil;
import org.apache.commons.lang3.tuple.ImmutablePair;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.CubicCurve2D;
import java.awt.geom.QuadCurve2D;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Base64;

/**
 * Created with IntelliJ IDEA.
 * Description: 图形验证码工具类
 * User: xujunfei
 * Date: 2020-06-19
 * Time: 09:54
 */
public class CaptchaUtil {

    // 常用颜色
    public static final int[][] COLOR = {{0, 135, 255}, {51, 153, 51}, {255, 102, 102}, {255, 153, 0}, {153, 102, 0}, {153, 102, 153}, {51, 153, 153}, {102, 102, 255}, {0, 102, 204}, {204, 51, 51}, {0, 153, 204}, {0, 51, 102}};
    // 验证码文本类型
    public static final int TYPE_DEFAULT = 1;  // 字母数字混合
    public static final int TYPE_ONLY_NUMBER = 2;  // 纯数字
    public static final int TYPE_ONLY_CHAR = 3;  // 纯字母
    public static final int TYPE_ONLY_UPPER = 4;  // 纯大写字母
    public static final int TYPE_ONLY_LOWER = 5;  // 纯小写字母
    public static final int TYPE_NUM_AND_UPPER = 6;  // 数字大写字母

    protected static final int NUM_MAX_INDEX = 8;  // 数字的最大索引，不包括最大值
    protected static final int CHAR_MIN_INDEX = NUM_MAX_INDEX;  // 字符的最小索引，包括最小值
    protected static final int CHAR_MAX_INDEX = RandomUtil.ALPHA.length;  // 字符的最大索引，不包括最大值
    protected static final int UPPER_MIN_INDEX = CHAR_MIN_INDEX;  // 大写字符最小索引
    protected static final int UPPER_MAX_INDEX = UPPER_MIN_INDEX + 23;  // 大写字符最大索引
    protected static final int LOWER_MIN_INDEX = UPPER_MAX_INDEX;  // 小写字母最小索引
    protected static final int LOWER_MAX_INDEX = CHAR_MAX_INDEX;  // 小写字母最大索引

    public static final CaptchaConfig DEFAULT_CONFIG = new CaptchaConfig();

    /**
     * 获取base64类型的验证码
     *
     * @return <验证码明文, base64编码>
     */
    public static ImmutablePair<String, String> toBase64() {
        return toBase64(DEFAULT_CONFIG);
    }

    /**
     * 获取base64类型的验证码
     *
     * @param captchaConfig
     * @return <验证码明文, base64编码>
     */
    public static ImmutablePair<String, String> toBase64(CaptchaConfig captchaConfig) {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        toStream(out, captchaConfig);
        return ImmutablePair.of("", "data:image/png;base64," + Base64.getEncoder().encodeToString(out.toByteArray()));
    }

    /**
     * 获取 输出流 验证码 (png格式)
     * <p>注意：调用此方法不能直接返回cookie，需要先获取验证码文本</p>
     *
     * @param out
     * @return
     */
    public static void toStream(OutputStream out) {
        toStream(out, DEFAULT_CONFIG);
    }

    /**
     * 获取 输出流 验证码 (png格式)
     * <p>注意：调用此方法不能直接返回cookie，需要先获取验证码文本</p>
     *
     * @param out
     * @param config
     */
    public static void toStream(OutputStream out, CaptchaConfig config) {
        char[] chars = getTextChars(config);
        toStream(chars, out, DEFAULT_CONFIG);
    }

    /**
     * 获取 输出流 验证码 (png格式)
     *
     * @param chars
     * @param out
     */
    public static void toStream(char[] chars, OutputStream out) {
        toStream(chars, out, DEFAULT_CONFIG);
    }

    /**
     * 获取 输出流 验证码 (png格式)
     *
     * @param inChars
     * @param out
     * @param captchaConfig
     * @return 验证码明文
     */
    public static void toStream(char[] chars, OutputStream out, CaptchaConfig config) {
        try {
            BufferedImage bi = new BufferedImage(config.width, config.height, BufferedImage.TYPE_INT_RGB);
            Graphics2D g2d = (Graphics2D) bi.getGraphics();
            // 填充背景
            g2d.setColor(Color.WHITE);
            g2d.fillRect(0, 0, config.width, config.height);
            // 抗锯齿
            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            // 画干扰圆
            drawOval(2, g2d, config);
            // 画干扰线
            g2d.setStroke(new BasicStroke(2f, BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL));
            drawBesselLine(1, g2d, config);
            // 画字符串
            g2d.setFont(new Font("Default", Font.PLAIN, config.fontSize));
            FontMetrics fontMetrics = g2d.getFontMetrics();
            int fW = config.width / chars.length;  // 每一个字符所占的宽度
            int fSp = (fW - (int) fontMetrics.getStringBounds("W", g2d).getWidth()) / 2;  // 字符的左右边距
            for (int i = 0; i < chars.length; i++) {
                g2d.setColor(color());
                int fY = config.height - ((config.height - (int) fontMetrics.getStringBounds(String.valueOf(chars[i]), g2d).getHeight()) >> 1);  // 文字的纵坐标
                g2d.drawString(String.valueOf(chars[i]), i * fW + fSp + 3, fY - 3);
            }
            g2d.dispose();
            ImageIO.write(bi, "png", out);
            out.flush();
        } catch (IOException e) {
            throw new RuntimeException("图形验证码生成失败：" + e.getMessage(), e);
        } finally {
            try {
                out.close();
            } catch (IOException e) {
                throw new RuntimeException(e.getMessage(), e);
            }
        }
    }


    /**
     * 生成随机验证码
     *
     * @return
     */
    public static char[] getTextChars() {
        return getTextChars(DEFAULT_CONFIG);
    }

    /**
     * 生成随机验证码
     *
     * @return
     */
    public static char[] getTextChars(CaptchaConfig config) {
        char[] cs = new char[config.len];
        for (int i = 0; i < config.len; i++) {
            switch (config.charType) {
                case 2:
                    cs[i] = RandomUtil.alpha(NUM_MAX_INDEX);
                    break;
                case 3:
                    cs[i] = RandomUtil.alpha(CHAR_MIN_INDEX, CHAR_MAX_INDEX);
                    break;
                case 4:
                    cs[i] = RandomUtil.alpha(UPPER_MIN_INDEX, UPPER_MAX_INDEX);
                    break;
                case 5:
                    cs[i] = RandomUtil.alpha(LOWER_MIN_INDEX, LOWER_MAX_INDEX);
                    break;
                case 6:
                    cs[i] = RandomUtil.alpha(UPPER_MAX_INDEX);
                    break;
                default:
                    cs[i] = RandomUtil.alpha();
            }
        }
        return cs;
    }

    /**
     * 给定范围获得随机颜色
     *
     * @param fc
     * @param bc
     * @return
     */
    public static Color color(int fc, int bc) {
        if (fc > 255)
            fc = 255;
        if (bc > 255)
            bc = 255;
        int r = fc + RandomUtil.num(bc - fc);
        int g = fc + RandomUtil.num(bc - fc);
        int b = fc + RandomUtil.num(bc - fc);
        return new Color(r, g, b);
    }

    /**
     * @return
     */
    public static Color color() {
        int[] color = COLOR[RandomUtil.num(COLOR.length)];
        return new Color(color[0], color[1], color[2]);
    }

    /**
     * 随机画干扰线
     *
     * @param num 数量
     * @param g   Graphics2D
     */
    public static void drawLine(int num, Graphics2D g, CaptchaConfig config) {
        drawLine(num, null, g, config);
    }

    /**
     * 随机画干扰线
     *
     * @param num   数量
     * @param color 颜色
     * @param g     Graphics2D
     */
    public static void drawLine(int num, Color color, Graphics2D g, CaptchaConfig config) {
        for (int i = 0; i < num; i++) {
            g.setColor(color == null ? color() : color);
            int x1 = RandomUtil.num(-10, config.width - 10);
            int y1 = RandomUtil.num(5, config.height - 5);
            int x2 = RandomUtil.num(10, config.width + 10);
            int y2 = RandomUtil.num(2, config.height - 2);
            g.drawLine(x1, y1, x2, y2);
        }
    }

    /**
     * 随机画干扰圆
     *
     * @param num 数量
     * @param g   Graphics2D
     */
    public static void drawOval(int num, Graphics2D g, CaptchaConfig config) {
        drawOval(num, null, g, config);
    }

    /**
     * 随机画干扰圆
     *
     * @param num   数量
     * @param color 颜色
     * @param g     Graphics2D
     */
    public static void drawOval(int num, Color color, Graphics2D g, CaptchaConfig config) {
        for (int i = 0; i < num; i++) {
            g.setColor(color == null ? color() : color);
            int w = 5 + RandomUtil.num(10);
            g.drawOval(RandomUtil.num(config.width - 25), RandomUtil.num(config.height - 15), w, w);
        }
    }

    /**
     * 随机画贝塞尔曲线
     *
     * @param num 数量
     * @param g   Graphics2D
     */
    public static void drawBesselLine(int num, Graphics2D g, CaptchaConfig config) {
        drawBesselLine(num, null, g, config);
    }

    /**
     * 随机画贝塞尔曲线
     *
     * @param num   数量
     * @param color 颜色
     * @param g     Graphics2D
     */
    public static void drawBesselLine(int num, Color color, Graphics2D g, CaptchaConfig config) {
        for (int i = 0; i < num; i++) {
            g.setColor(color == null ? color() : color);
            int x1 = 5, y1 = RandomUtil.num(5, config.height / 2);
            int x2 = config.width - 5, y2 = RandomUtil.num(config.height / 2, config.height - 5);
            int ctrlx = RandomUtil.num(config.width / 4, config.width / 4 * 3), ctrly = RandomUtil.num(5, config.height - 5);
            if (RandomUtil.num(2) == 0) {
                int ty = y1;
                y1 = y2;
                y2 = ty;
            }
            if (RandomUtil.num(2) == 0) {  // 二阶贝塞尔曲线
                QuadCurve2D shape = new QuadCurve2D.Double();
                shape.setCurve(x1, y1, ctrlx, ctrly, x2, y2);
                g.draw(shape);
            } else {  // 三阶贝塞尔曲线
                int ctrlx1 = RandomUtil.num(config.width / 4, config.width / 4 * 3), ctrly1 = RandomUtil.num(5, config.height - 5);
                CubicCurve2D shape = new CubicCurve2D.Double(x1, y1, ctrlx, ctrly, ctrlx1, ctrly1, x2, y2);
                g.draw(shape);
            }
        }
    }

}
