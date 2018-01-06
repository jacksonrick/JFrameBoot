package com.jf.page;

import com.github.pagehelper.PageInfo;

public class DoTag {

    public static String tag(PageInfo page, String queryForm) {
        // bootstrap分页样式类
        String divClass = "pagination pull-right";

        StringBuffer strBuf = new StringBuffer();
        strBuf.append("<script type=\"text/javascript\">");

        //表单提交
        strBuf.append("var qForm = document.forms['").append(queryForm)
                .append("'];var hd = document.createElement('input'); hd.type='hidden'; hd.name='pageNo';").append("qForm.appendChild(hd);")
                .append("function toPage(pageNo){")
                .append("if(pageNo=='' || pageNo<=0){return;} qForm.pageNo.value=pageNo; qForm.submit();}</script>");

        strBuf.append("<ul class=\"").append(divClass).append("\">");

        // 页数
        strBuf.append("<li><a>(第").append(page.getPageNum()).append("/").append(page.getPages()).append("页").append(" 共").append(page.getTotal()).append("条)</a></li>");

        // 上一页
        if (page.getPageNum() == 1) {
            strBuf.append(" <li class=\"disabled\"><a>首页</a></li> ");
            strBuf.append(" <li class=\"disabled\"><a>").append(" 上一页 </a></li> ");
        } else {
            strBuf.append(" <li><a href=\"javascript:toPage(1)\">首页</a></li> ");
            strBuf.append(" <li><a href=\"javascript:toPage(").append(page.getPageNum() - 1).append(")\"> 上一页 </a></li> ");
        }

        int[] nums = page.getNavigatepageNums();
        // 列表项目
        for (int i = 0; i < nums.length; i++) {
            if (page.getPageNum() == nums[i]) {
                strBuf.append("<li class=\"active\"><a>").append(nums[i]).append("</a></li>");
            } else {
                strBuf.append("<li><a href=\"javascript:toPage(").append(nums[i]).append(")\">").append(nums[i]).append("</a></li>");
            }
        }

        // 下一页
        // 总页数等于当前页 或者 总页数等于0
        if (page.getPageNum() == page.getPages() || page.getPages() <= 0) {
            strBuf.append(" <li class=\"disabled\"><a>").append(" 下一页 </a></li> ");
            strBuf.append(" <li class=\"disabled\"><a>末页</a></li> ");
        } else {
            strBuf.append(" <li><a href=\"javascript:toPage(").append(page.getPageNum() + 1).append(")\"> 下一页 </a></li> ");
            strBuf.append(" <li><a href=\"javascript:toPage(").append(page.getPages()).append(")\">末页</a></li> ");
        }

        // 页码跳转
        if (page.getPages() > 1) {
            strBuf.append("<style>.pagination a.jump{width: 100px;}"
                    + ".pagination input{width: 65%;height: 100%;position: absolute;left: 0px;top: 0px;border: none;text-align: center;}"
                    + ".pagination button{width: 35%;height: 100%;position: absolute;right: 0px;top: 0px;padding: 0px;font-size: 12px;border: none;border-left: solid 1px #ccc;}</style>");
            strBuf.append(
                    "<li><a class=\"jump\">Jump<input type=\"text\" value=\"" + page.getPageNum() + "\" id=\"goPageNo\" class=\"form-control\" "
                            + "onkeyup=\"javascript:if(event.keyCode==13){toPage(document.getElementById('goPageNo').value)}\">"
                            + "<button class=\"form-control\" onclick=\"toPage(document.getElementById('goPageNo').value)\">GO</button></a></li>");
        }
        strBuf.append("</ul>");
        return strBuf.toString();
    }

}
