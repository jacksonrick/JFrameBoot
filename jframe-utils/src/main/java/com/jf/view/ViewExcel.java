package com.jf.view;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.web.servlet.view.document.AbstractXlsView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;

/**
 * 生成Excel视图
 * <p>1.map{header:列属性,headerVal:表头名称,ename:表格名称}</p>
 * <p>2.支持String,Integer,Date类型需要列属性包含*Date*或*time*字样</p>
 * <p>3.SpringMvc用法:return new ModelAndView(new ViewExcel(), model);</p>
 * <p>注意：查询的list和header、headerVal的数目要相同，否则字段不对应</p>
 * @author rick
 * @version 2.0
 */
public class ViewExcel extends AbstractXlsView {

	@Override
	protected void buildExcelDocument(Map<String, Object> map, Workbook workbook, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		// 表名
		String excelName = map.get("ename") + ".xls";
		// 设置response方式,使执行此controller时候自动出现下载页面,而非直接使用excel打开
		response.setContentType("APPLICATION/OCTET-STREAM");
		response.setHeader("Content-Disposition", "attachment; filename=" + URLEncoder.encode(excelName, "UTF-8"));

		// List<Object>类型
		List<Map<String, Object>> list = (List<Map<String, Object>>) map.get("list");
		// 产生Excel表头
		Sheet sheet = workbook.createSheet("List");
		// 第0行
		Row header = sheet.createRow(0);

		// 列属性
		String[] hstr = (String[]) map.get("header");
		// 表头名称
		String[] hval = (String[]) map.get("headerVal");
		// 产生标题列
		for (int i = 0; i < hval.length; i++) {
			header.createCell(i).setCellValue(hval[i]);
		}

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

		// 填充数据
		int rowNum = 1;
		for (Object object : list) {
			Row row = sheet.createRow(rowNum++);

			Map<String, Object> val = (Map<String, Object>) object;
			for (int i = 0; i < hstr.length; i++) {
				Object obj = val.get(hstr[i]);
				String str = "";
				if (obj != null) {
					String type = obj.getClass().getName();
					if (type.endsWith("Date") || type.endsWith("Timestamp")) {
						str = sdf.format(obj);
					} else {
						str = obj + "";
					}
				}
				row.createCell(i).setCellValue(str);
			}
		}

        for (int i = 0; i < hval.length; i++) {
            // autosize
            sheet.autoSizeColumn((short)i);
        }
	}

}
