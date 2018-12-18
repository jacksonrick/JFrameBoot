package com.jf.pdf;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateExceptionHandler;

import java.io.File;
import java.io.StringWriter;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: xujunfei
 * Date: 2018-12-17
 * Time: 17:34
 */
public class PdfUtil {

    /**
     * 从模板导出html
     *
     * @param baseDir
     * @param fileName
     * @param globalMap
     * @return
     */
    public static String loadFtlHtml(File baseDir, String fileName, Map globalMap) {
        if (baseDir == null || !baseDir.isDirectory() || globalMap == null || fileName == null || "".equals(fileName)) {
            throw new IllegalArgumentException("参数不完整");
        }

        Configuration cfg = new Configuration(Configuration.VERSION_2_3_22);
        try {
            cfg.setDirectoryForTemplateLoading(baseDir);
            cfg.setDefaultEncoding("UTF-8");
            cfg.setTemplateExceptionHandler(TemplateExceptionHandler.RETHROW_HANDLER);
            cfg.setClassicCompatible(true);
            Template temp = cfg.getTemplate(fileName);

            StringWriter stringWriter = new StringWriter();
            temp.process(globalMap, stringWriter);

            return stringWriter.toString();
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("加载文档异常");
        }
    }

}
