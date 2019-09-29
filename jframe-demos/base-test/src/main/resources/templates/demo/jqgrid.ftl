<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <title>jqGrid表格</title>
    <link href="/static/common/css/bootstrap.min.css" rel="stylesheet">
    <link href="/static/library/plugins/jqGrid/ui.jqgrid.css" rel="stylesheet">
    <script type="text/javascript" src="/static/library/jquery/jquery-1.8.3.min.js"></script>
    <script type="text/javascript" src="/static/library/plugins/jqGrid/jquery.jqGrid.js"></script>
</head>

<body>

<table id="list"></table>
<div id="pager"></div>

<script type="text/javascript">
    $(function () {
        pageInit();
    });

    function pageInit() {
        jQuery("#list").jqGrid({
            url: 'pagerJson.do',
            datatype: "json",
            colNames: ['username', 'phone', 'createDate'],
            colModel: [
                {name: 'username', index: 'username', width: 55},
                {name: 'phone', index: 'phone', width: 90},
                {name: 'createDate', index: 'createDate', width: 100}
            ],
            rowNum: 10,
            rowList: [10, 20, 30],
            pager: '#pager',
            sortname: 'id',
            mtype: "post",
            viewrecords: true,
            sortorder: "desc",
            caption: "JSON 实例"
        });
        jQuery("#list").jqGrid('navGrid', '#pager', {edit: false, add: false, del: false});
    }
</script>
</body>
</html>