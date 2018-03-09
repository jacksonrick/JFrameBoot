package com.jf.file;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;

import javax.servlet.http.HttpServletResponse;
import java.io.OutputStream;
import java.util.Hashtable;

/**
 * Created with IntelliJ IDEA.
 * Description: 生成二维码
 * User: xujunfei
 * Date: 2018-03-08
 * Time: 12:44
 */
public class Qrcode {

    public static void write(String str, HttpServletResponse response) throws Exception {
        // 图片大小
        int width = 200;
        int height = 200;
        // 图片格式
        String format = "png";
        Hashtable hints = new Hashtable();
        hints.put(EncodeHintType.CHARACTER_SET, "utf-8");
        BitMatrix bitMatrix = new MultiFormatWriter().encode(str, BarcodeFormat.QR_CODE, width, height, hints);
        OutputStream out = null;
        out = response.getOutputStream();
        MatrixToImageWriter.writeToStream(bitMatrix, format, out);
        out.flush();
        out.close();
    }

}
