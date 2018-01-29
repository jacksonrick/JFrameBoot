<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <title></title>
<#include "include.ftl"/>
</head>

<body class="gray-bg">

<div class="row">
    <div class="col-sm-12">
        <div class="ibox float-e-margins">
            <div class="ibox-content">
                <div class="row">
                    <div class="col-sm-12">
                        <form action="/admin/system/logList" class="form-inline" method="post" id="queryForm">
                            <label>操作人：<input class="form-control input-sm" name="logUser" value="${logUser }"></label>
                            <label>操作日期：
                                <input type="text" class="form-control input-sm" id="startDate" name="startDate" placeholder="操作日期" value="${startDate }">
                            </label>
                            <button class="btn btn-warning btn-sm" type="submit">搜索</button>
                        </form>
                    </div>
                </div>
                <div class="alert alert-warning mb5">当前日志记录总数：<b>${logCount }</b>
                    <button class="btn btn-warning btn-sm backup" mon="1">备份并删除一个月之前的日志</button>
                    <button class="btn btn-warning btn-sm backup" mon="2">备份并删除两个月之前的日志</button>
                    <button class="btn btn-warning btn-sm backup" mon="3">备份并删除三个月之前的日志</button>
                </div>
                <div class="alert alert-warning mb5"><i class="fa fa-star"></i>由于日志数据较多，请定期清理旧日志（清理三个月之前的日志）；旧日志请查看导出文件；注意：点击备份后，数据文件将下载到本地。
                </div>
                <table class="table table-striped table-bordered table-hover">
                    <thead>
                    <tr role="row">
                        <th>序号</th>
                        <th>操作人</th>
                        <th>备注</th>
                        <th>参数</th>
                        <th>操作日期</th>
                        <th>操作IP</th>
                    </tr>
                    </thead>
                    <tbody id="dataList">
                    <#list pageInfo.list as v>
                    <tr>
                        <td>${v_index }</td>
                        <td>${v.logUser }</td>
                        <td>${v.logRemark }</td>
                        <td>${v.logParams }</td>
                        <td>${v.logCreateTime?string('yyyy-MM-dd HH:mm:ss') }</td>
                        <td>${v.logIp }</td>
                    </tr>
                    </#list>
                    </tbody>
                </table>
                <div class="row">
                    <div class="col-sm-4">

                    </div>
                    <div class="col-sm-8">
                        <div class="pagination-ul">
                        <@pagination form="queryForm" data=pageInfo> </@pagination>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>

<script type="text/javascript">
    $(function () {
        datePicker('#startDate', "yyyy-mm-dd");

        $(".backup").click(function () {
            var mon = $(this).attr("mon");
            var idx = layer.confirm('确定要执行此操作吗？操作不可恢复', {btn: ['确定', '取消'], icon: 3}, function () {
                location.href = "/admin/system/backupLog?monthAgo=" + mon;
                layer.close(idx);
            });
        });
    });
</script>
</body>
</html>