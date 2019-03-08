package com.jf.controller.view;

import com.itextpdf.text.Document;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.tool.xml.XMLWorkerHelper;
import com.jf.pdf.STFontProviderEnum;
import freemarker.template.Template;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayInputStream;
import java.nio.charset.Charset;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: xujunfei
 * Date: 2018-12-17
 * Time: 16:06
 */
@Component
public class ViewPDF extends AbstractItextView {

    @Autowired
    private FreeMarkerConfigurer freeMarkerConfigurer;

    protected void buildPdfDocument(Map<String, Object> map, Document document, PdfWriter pdfWriter, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws Exception {
        String ftl = String.valueOf(map.get("ftl"));
        if (ftl == null || ftl == "") {
            throw new RuntimeException("文档名不能为空");
        }
        // 获取模板文件
        Template tpl = freeMarkerConfigurer.getConfiguration().getTemplate(ftl);
        // 生成HTML
        String html = FreeMarkerTemplateUtils.processTemplateIntoString(tpl, map);
        XMLWorkerHelper.getInstance().parseXHtml(pdfWriter, document, new ByteArrayInputStream(html.getBytes()), Charset.forName("UTF-8"), STFontProviderEnum.INSTANCE.get());
    }

}

