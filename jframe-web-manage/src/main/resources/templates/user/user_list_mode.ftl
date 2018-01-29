<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <title></title>
<#include "include.ftl"/>
</head>

<body class="gray-bg">

<div class="ibox-content">
    <div class="row">
        <div class="col-sm-8">
            <form class="form-inline" method="post" id="queryForm">
                <label> 昵称/邮箱/手机号:<input type="text" name="keywords" class="form-control input-sm"></label>
                <label> 注册时间:
                    <input type="text" class="form-control input-sm" id="startDate" name="startDate" placeholder="开始时间">
                    <input type="text" class="form-control input-sm" id="endDate" name="endDate" placeholder="结束时间">
                </label>
                <button type="button" id="search" class="btn btn-sm btn-info"><i class="fa fa-search"></i></button>
            </form>
        </div>
        <div class="col-sm-4 text-right">
            <button class="btn btn-sm btn-success" id="confirm">确定选择</button>
            <button class="btn btn-sm btn-warning" id="closeWin">关闭</button>
        </div>
    </div>
    <table id="table" class="table table-striped table-bordered table-hover" width="100%"></table>
</div>

<script type="text/javascript">
    $(function () {
        datePicker('#startDate,#endDate', "yyyy-mm-dd");

        var index = parent.layer.getFrameIndex(window.name);
        $("#closeWin").click(function () {
            parent.layer.close(index);
        });

        var tables;
        var columns = [
            CONSTANT.COLUMN.CHECKALL,
            {title: "ID", data: "id"},
            {title: "昵称", data: "nickname", defaultContent: "--"},
            {title: "手机号", data: "phone", defaultContent: "--"},
            {title: "邮箱", data: "email", defaultContent: "--", orderable: false},
            {title: "真实姓名", data: "realname", defaultContent: "--", orderable: false},
            {title: "是否冻结", data: "isDelete", render: CONSTANT.RENDER.BOOLEAN}
        ]
        var $table = $('#table');
        tables = $table.DataTable($.extend(true, {}, CONSTANT.DEFAULT_OPTION, {
            columns: columns,
            ajax: function (data, callback, settings) {
                CONSTANT.AJAX("/admin/user/userListData", [[1, "u.id"], [2, "u.nickname"], [3, "u.phone"]], data, callback, settings);
            },
            columnDefs: [CONSTANT.BUTTON.CHECKBOXS],
            drawCallback: function (settings) {
                $(":checkbox[name='check-all']").prop("checked", false);
            }
        }));

        $table.on("change", ":checkbox", function () {
            if ($(this).is("[name='check-all']")) {// 全选
                $(":checkbox", $table).prop("checked", $(this).prop("checked"));
            } else {// 一般复选
                var checkbox = $("tbody :checkbox", $table);
                $(":checkbox[name='check-all']", $table).prop('checked', checkbox.length == checkbox.filter(':checked').length);
            }
        });
        $("#search").on("click", function () {
            tables.draw();
        });
        $("#confirm").click(function () {
            var arrItemId = [];
            $("tbody :checkbox:checked", $table).each(function (i) {
                var item = tables.row($(this).closest('tr')).data();
                arrItemId.push(item);
            });
            if (arrItemId.length > 0) {
                parent.dealUser(arrItemId);
                parent.layer.close(index);
            }
        });
    });
    // parent method:dealUser(obj[])
</script>
</body>
</html>