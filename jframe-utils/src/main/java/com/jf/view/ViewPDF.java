package com.jf.view;

import com.lowagie.text.*;
import com.lowagie.text.pdf.BaseFont;
import com.lowagie.text.pdf.PdfWriter;
import org.springframework.web.servlet.view.document.AbstractPdfView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;

/**
 * 生成PDF视图(表格)
 * <p>1.map{header:列属性,headerVal:表头名称,pname:pdf名称}</p>
 * <p>2.支持String,Integer,Date类型需要列属性包含*Date*或*time*字样</p>
 * <p>3.SpringMvc用法:return new ModelAndView(new ViewPDF(), model);</p>
 * <p>注意：查询的list和header、headerVal的数目要相同，否则字段不对应</p>
 *
 * @author rick
 * @version 2.0
 */
public class ViewPDF extends AbstractPdfView {

    @SuppressWarnings("rawtypes")
    @Override
    protected void buildPdfDocument(Map<String, Object> map, Document document, PdfWriter pdfWriter,
                                    HttpServletRequest request,
                                    HttpServletResponse response) throws Exception {
        String pdfName = map.get("pname") + ".pdf";
        // 设置response方式,使执行此controller时候自动出现下载页面
        response.setContentType("APPLICATION/OCTET-STREAM");
        response.setHeader("Content-Disposition", "attachment; filename=" + URLEncoder.encode(pdfName, "UTF-8"));

        // List<Object>类型
        List list = (List) map.get("list");
        // 列属性
        String[] hstr = (String[]) map.get("header");
        // 表头名称
        String[] hval = (String[]) map.get("headerVal");
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

		/*
         * // 显示中文 BaseFont bfChinese =
		 * BaseFont.createFont("STSong-Light","UniGB-UCS2-H",
		 * BaseFont.NOT_EMBEDDED);
		 */
        // 设置字体
        // String path =
        // ContextLoader.getCurrentWebApplicationContext().getServletContext().getRealPath("/");
        BaseFont bf = BaseFont.createFont(this.getClass().getResource("/simsun.ttf").toString(), BaseFont.IDENTITY_H,
                BaseFont.EMBEDDED);
        Font FontChinese = new Font(bf, 12, Font.NORMAL);

        Paragraph para = new Paragraph((String) map.get("pname"), FontChinese);
        para.setAlignment(Element.ALIGN_CENTER);
        document.add(para);

        Table table = new Table(hstr.length);// 建立一个pdf表格
        table.setBorder(1);

        // 表头
        Cell cell = null;
        for (int i = 0; i < hstr.length; i++) {
            // 添加单元格
            cell = new Cell(new Phrase(hval[i], FontChinese));
            // 对齐方式
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell.setVerticalAlignment(Element.ALIGN_CENTER);
            cell.setHeader(true);
            table.addCell(cell);
        }
        table.endHeaders(); // 结束表头

        // 数据
        for (Object object : list) {
            Map val = (Map) object;
            for (int i = 0; i < hstr.length; i++) {
                Object obj = val.get(hstr[i]);
                String value = "";
                if (obj != null) {
                    String type = obj.getClass().getName();
                    if (type.endsWith("Date") || type.endsWith("Timestamp")) {
                        value += sdf.format(obj);
                    } else {
                        value += obj + "";
                    }
                }
                Cell cell2 = new Cell(new Phrase(value, FontChinese));
                cell2.setHorizontalAlignment(Element.ALIGN_CENTER);
                cell2.setVerticalAlignment(Element.ALIGN_CENTER);
                table.addCell(cell2);
            }
        }

        document.add(table);
    }

}
