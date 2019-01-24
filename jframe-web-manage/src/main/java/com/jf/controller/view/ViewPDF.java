package com.jf.controller.view;

import com.itextpdf.text.Document;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.tool.xml.XMLWorkerHelper;
import com.jf.pdf.PdfUtil;
import com.jf.pdf.STFontProviderEnum;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: xujunfei
 * Date: 2018-12-17
 * Time: 16:06
 */
public class ViewPDF extends AbstractItextView {

    protected void buildPdfDocument(Map<String, Object> map, Document document, PdfWriter pdfWriter, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws Exception {
        String ftl = String.valueOf(map.get("ftl"));
        if (ftl == null || ftl == "") {
            throw new RuntimeException("文档名不能为空");
        }
        URL fileResource = ViewPDF.class.getResource("/templates");
        String html = PdfUtil.loadFtlHtml(new File(fileResource.getFile()), ftl, map);

        XMLWorkerHelper.getInstance().parseXHtml(pdfWriter, document, new ByteArrayInputStream(html.getBytes()), Charset.forName("UTF-8"), STFontProviderEnum.INSTANCE.get());
    }

}
