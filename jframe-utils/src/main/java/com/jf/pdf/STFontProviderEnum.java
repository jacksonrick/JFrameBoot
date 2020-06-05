package com.jf.pdf;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontProvider;
import com.itextpdf.text.pdf.BaseFont;

/**
 * Created with IntelliJ IDEA.
 * Description: PDF字体 示例见ViewPDF
 * User: xujunfei
 * Date: 2018-12-25
 * Time: 16:16
 */
public enum STFontProviderEnum implements FontProvider {

    INSTANCE;

    private STFontProviderEnum provider;

    private STFontProviderEnum() {
        provider = this;
    }

    public FontProvider get() {
        return provider;
    }

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

}
