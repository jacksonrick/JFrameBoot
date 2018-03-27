<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Select2插件</title>
    <link rel="stylesheet" href="/static/common/css/bootstrap.min.css">
    <link href="/static/library/plugins/select2/select2.min.css" rel="stylesheet">
</head>
<body>

<select id="sel_menu" multiple="multiple" class="form-control" style="width: 400px;">
    <optgroup label="系统设置">
        <option value="1">用户管理</option>
        <option value="2">角色管理</option>
        <option value="3">部门管理</option>
        <option value="4">菜单管理</option>
    </optgroup>
    <optgroup label="订单管理">
        <option value="5">订单查询</option>
        <option value="6">订单导入</option>
        <option value="7">订单删除</option>
        <option value="8">订单撤销</option>
    </optgroup>
    <optgroup label="基础数据">
        <option value="9">基础数据维护</option>
    </optgroup>
</select>

<br>
<br>

<select class="form-control" id="users" style="width: 400px;"></select>
<button class="btn btn-info" type="button" id="get">获取</button>

<script type="text/javascript" src="/static/library/jquery/jquery-2.1.1.min.js"></script>
<script type="text/javascript" src="/static/library/plugins/select2/select2.min.js"></script>
<script type="text/javascript">

    $(function () {
        $("#sel_menu").select2({
            tags: true,
            maximumSelectionLength: 3  //最多能够选择的个数
        });
    });

    $("#users").select2({
        allowClear: true,
        multiple: true,
        maximumSelectionLength: 10,
        ajax: {
            url: "/findUsers.do",
            dataType: 'json',
            delay: 500,
            data: function (params) {
                return {
                    phone: params.term
                };
            },
            processResults: function (data, page) {
                return {
                    results: data
                };
            }
        },
        escapeMarkup: function (markup) {
            return markup;
        },
        minimumInputLength: 1
    });

    $("#get").click(function () {
        alert($("#users").val())
    })
</script>
</body>
</html>