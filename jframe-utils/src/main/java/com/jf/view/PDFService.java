package com.jf.view;

import com.jf.string.StringUtil;
import com.jf.system.PathUtil;
import com.lowagie.text.pdf.BaseFont;
import freemarker.template.Template;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;
import org.xhtmlrenderer.pdf.ITextFontResolver;
import org.xhtmlrenderer.pdf.ITextRenderer;

import java.io.*;
import java.util.Map;

/**
 * 将网页导出到PDF文件
 * <p>可用于协议、表格类</p>
 *
 * @author rick
 * @version 2.0
 */
@Component
public class PDFService {

    @Autowired
    private FreeMarkerConfigurer freeMarkerConfigurer;

    /**
     * 生成pdf文档
     *
     * @param ftl 模板文件
     * @param map 数据
     * @return
     */
    public String pdfCreate(String ftl, Map<String, Object> map, String staticPath) {
        try {
            // 存储目录
            String basePath = staticPath + "upload/pdf/";
            String fileName = StringUtil.randomFilename();
            String target = "/static/upload/pdf/";

            Template temp = freeMarkerConfigurer.getConfiguration().getTemplate("pdf/" + ftl + ".ftl");

			/* 创建数据模型 */
            // Map<String, Object> root = new HashMap<String, Object>();
            // root.put("name", "xujunfei");

			/* 将生成的内容写入html中 */
            File pd = new File(basePath);
            if (!pd.exists()) {
                pd.mkdir();
            }
            String htmlFile = basePath + fileName + ".html";
            File file = new File(htmlFile);
            if (!file.exists()) {
                file.createNewFile();
            }
            Writer out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file), "UTF-8"));
            temp.process(map, out);
            out.flush();

            String url = new File(htmlFile).toURI().toURL().toString();
            String outputFile = basePath + fileName + ".pdf";
            OutputStream os = new FileOutputStream(outputFile);

            ITextRenderer renderer = new ITextRenderer();
            // PDFEncryption pdfEncryption = new
            // PDFEncryption(null,null,PdfWriter.ALLOW_PRINTING);
            // renderer.setPDFEncryption(pdfEncryption); //只有打印权限的
            renderer.setDocument(url);

            // 解决中文问题
            ITextFontResolver fontResolver = renderer.getFontResolver();
            fontResolver.addFont(this.getClass().getResource("/simsun.ttf").toString(), BaseFont.IDENTITY_H, BaseFont.NOT_EMBEDDED);

            renderer.layout();
            renderer.createPDF(os);

            out.close();
            os.close();
            // 删除html文件
            file.delete();

            return target + fileName + ".pdf";
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

}
