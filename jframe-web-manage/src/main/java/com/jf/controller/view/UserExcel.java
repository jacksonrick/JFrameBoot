package com.jf.controller.view;

import com.jf.database.model.User;
import com.jf.date.DateUtil;
import com.jf.system.LogManager;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.web.servlet.view.document.AbstractXlsView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.net.URLEncoder;
import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: xujunfei
 * Date: 2017-11-08
 * Time: 10:41
 */
public class UserExcel extends AbstractXlsView {

    @Override
    protected void buildExcelDocument(Map<String, Object> map, Workbook workbook, HttpServletRequest request, HttpServletResponse response) {
        try {
            String excelName = "用户列表" + DateUtil.getCurrentTime(2) + ".xls";
            response.setContentType("APPLICATION/OCTET-STREAM");
            response.setHeader("Content-Disposition", "attachment; filename=" + URLEncoder.encode(excelName, "UTF-8"));

            List<User> list = (List<User>) map.get("list");
            Sheet sheet = workbook.createSheet("List");
            Row header = sheet.createRow(0);
            // 表头
            String[] hval = new String[]{"昵称", "手机号", "注册时间", "是否冻结"};
            // 总列数
            int length = hval.length;
            // 填充标题列
            for (int i = 0; i < length; i++) {
                header.createCell(i).setCellValue(hval[i]);
            }
            // 填充数据
            int rowNum = 1;
            for (User user : list) {
                Row row = sheet.createRow(rowNum++);
                row.createCell(0).setCellValue(user.getNickname());
                row.createCell(1).setCellValue(user.getPhone());
                row.createCell(2).setCellValue(DateUtil.dateToStr(user.getCreateTime()));
                row.createCell(3).setCellValue(user.getDeleted() ? "是" : "否");
            }
            for (int i = 0; i < length; i++) {
                sheet.autoSizeColumn((short) i);
            }
        } catch (Exception e) {
            LogManager.error("导出Excel错误", e);
        }
    }
}
