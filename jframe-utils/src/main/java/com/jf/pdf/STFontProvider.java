package com.jf.pdf;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontProvider;
import com.itextpdf.text.pdf.BaseFont;

/**
 * Created with IntelliJ IDEA.
 * Description: 字体
 * User: xujunfei
 * Date: 2018-12-17
 * Time: 17:34
 */
public class STFontProvider implements FontProvider {

    public boolean isRegistered(String s) {
        return true;
    }

    public Font getFont(String s, String s1, boolean b, float v, int i, BaseColor baseColor) {
        try {
            BaseFont bf = BaseFont.createFont("STSongStd-Light", "UniGB-UCS2-H", BaseFont.NOT_EMBEDDED);
            return new Font(bf, 12, Font.NORMAL);
        } catch (Exception e) {
            throw new RuntimeException("字体不存在");
        }
    }

    private STFontProvider() {
    }

    private static STFontProvider provider = new STFontProvider();

    public static STFontProvider getInstance() {
        return provider;
    }
}
