<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>DataTables</title>
    <link rel="stylesheet" href="/static/common/css/bootstrap.min.css">
    <link rel="stylesheet" href="/static/library/plugins/dataTables/css/dataTables.bootstrap.min.css">
</head>
<body>

<h3>更详细的用户见manage案例</h3>
<div class="container">
    <div class="row">
        <div class="col-md-6">
            <form class="form-inline" method="post" id="queryForm">
                <label> 用户名/手机号:<input type="text" name="keywords" class="form-control input-sm"></label>
                <button type="button" id="search" class="btn btn-sm btn-info"><i class="fa fa-search"></i></button>
            </form>
        </div>
        <div class="col-md-6 text-right">
            <button type="button" class="btn btn-primary" id="btn-add"><i class="fa fa-plus"></i> 添加</button>
            <button type="button" class="btn btn-danger" id="btn-del"><i class="fa fa-remove"></i> 批量删除</button>
        </div>
    </div>

    <table id="table" class="table table-striped table-bordered table-hover" width="100%"></table>
</div>
<script type="text/javascript" src="/static/library/jquery/jquery-2.1.1.min.js"></script>
<script type="text/javascript" src="/static/library/plugins/dataTables/js/jquery.dataTables.min.js"></script>
<script type="text/javascript" src="/static/library/plugins/dataTables/js/dataTables.select.min.js"></script>
<script type="text/javascript" src="/static/library/plugins/dataTables/js/dataTables.bootstrap.min.js"></script>
<script type="text/javascript" src="/static/common/js/dt.js"></script>
<script type="text/javascript">
    $(function () {
        var columns = [
            CONSTANT.COLUMN.CHECKBOX,
            {title: "ID", data: "id"},
            {title: "昵称", data: "nickname", defaultContent: "--"},
            {title: "手机号", data: "phone", defaultContent: "--"},
            {title: "成长值", data: "growth", orderable: false, visible: false},
            {title: "等级", data: "level", orderable: false, visible: false},
            {title: "积分", data: "point", orderable: false, visible: false},
            {title: "账户余额", data: "money", orderable: false, visible: false},
            {title: "头像", data: "avatar", orderable: false, render: CONSTANT.RENDER.AVATAR},
            {title: "注册时间", data: "createTime", defaultContent: "--"},
            {title: "是否冻结", data: "isDelete", render: CONSTANT.RENDER.BOOLEAN},
            {title: "操作", data: null, orderable: false}
        ]
        var $table = $('#table');
        var tables = $table.DataTable($.extend(true, {}, CONSTANT.DEFAULT_OPTION, {
            columns: columns,
            ajax: function (data, callback, settings) {
                // 排序字段，与columns对应，index从0开始
                var sort = CONSTANT.GETCOLUMN(data.order[0], [[1, "id"], [2, "nickname"], [3, "phone"], [9, "create_time"], [10, "is_delete"]]);
                var param = {
                    pageNo: data.start / 10 + 1,
                    pageSort: sort
                };
                var formData = $("#queryForm").serializeArray();// 把form里面的数据序列化成数组
                formData.forEach(function (e) {
                    param[e.name] = e.value;
                });
                $.ajax({
                    type: "POST",
                    url: "users.do",
                    data: param,    // 传入已封装的参数
                    dataType: "json",
                    success: function (result) {
                        if (result.code && result.code != 0) {
                            alert(result.msg);
                        }
                        setTimeout(function () {
                            var returnData = {};
                            returnData.draw = data.draw;// 这里直接自行返回了draw计数器
                            returnData.recordsTotal = result.total;
                            returnData.recordsFiltered = result.total;// 后台不实现过滤功能，每次查询均视作全部结果
                            returnData.data = result.list;
                            callback(returnData);
                        }, 500);
                    },
                    error: function (XMLHttpRequest, textStatus, errorThrown) {
                        alert("查询失败");
                    }
                });
            },
            columnDefs: [
                CONSTANT.BUTTON.CHECKBOXS,
                CONSTANT.BUTTON.EDITANDDEL
            ],
            drawCallback: function (settings) {// 在每次table被draw完后回调函数
                //console.log(settings);
                /*var api = this.api();
                 //获取到本页开始的条数
                 var startIndex = api.context[0]._iDisplayStart;
                 api.column(1).nodes().each(function (cell, i) {
                 cell.innerHTML = startIndex + i + 1;
                 });*/
            }
        })); // .api()

        $("#search").on("click", function () {
            tables.draw();// 查询后不需要保持分页状态，回首页
        });

        // 行点击事件
        /*$("tbody", $table).on("click", "tr", function (event) {
         $(this).addClass("active").siblings().removeClass("active");
         // 获取该行对应的数据
         var item = tables.row($(this).closest('tr')).data();
         console.log(item);
         });*/

        /*$('#table tbody').on('click', 'tr', function () {
         $(this).toggleClass('selected');
         });*/

        $("#btn-del").click(function () {// 批量删除
            var arrItemId = [];
            $("tbody :checkbox:checked", $table).each(function (i) {
                var item = tables.row($(this).closest('tr')).data();
                arrItemId.push(item.id);
                $(this).closest('tr').remove(); // 删除
            });
            console.log(arrItemId);
        });

        // 事件绑定
        $table.on("change", ":checkbox", function () {
            if ($(this).is("[name='check-all']")) {
                // 全选
                $(":checkbox", $table).prop("checked", $(this).prop("checked"));
            } else {
                // 一般复选
                var checkbox = $("tbody :checkbox", $table);
                $(":checkbox[name='check-all']", $table).prop('checked', checkbox.length == checkbox.filter(':checked').length);
            }
        }).on("click", ".btn-edit", function () {
            // 点击编辑按钮
            var item = tables.row($(this).closest('tr')).data();
            $(this).closest('tr').addClass("active").siblings().removeClass("active");
            console.log(item);
        }).on("click", ".btn-del", function () {
            // 点击删除按钮
            var item = tables.row($(this).closest('tr')).data();
            $(this).closest('tr').addClass("active").siblings().removeClass("active");
            console.log(item.id);
            $(this).closest('tr').remove();
        });
    });
</script>
</body>
</html>