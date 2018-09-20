<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <title></title>
    <style type="text/css">
        .dataList li {
            display: inline-block;
            margin: 10px;
            padding: 10px;
            float: left;
            width: 100px;
            border: solid 1px #ffffff;
        }

        .dataList li:hover {
            background: #eff3f9;
            border-color: #bcccde;
        }

        .ft {
            width: 80px;
            height: 84px;
        }

        .fn {
            color: #595c5f;
            height: 23px;
            line-height: 14px;
            font-size: 12px;
            text-align: center;
        }

        .text {
            background: url("/static/theme/images/file/text.png") no-repeat;
        }

        .folder {
            background: url("/static/theme/images/file/folder.png") no-repeat;
        }

        .unknow {
            background: url("/static/theme/images/file/unknow.png") no-repeat;
        }
    </style>
<#include "include.ftl"/>
</head>

<body class="gray-bg">

<div class="wrapper-content">
    <div class="ibox float-e-margins">
        <div class="ibox-title">
            <h5>系统日志</h5>
            <ol class="breadcrumb">
                <li><a title="/" href="javascript:;" onclick="openDirectory(this.title, 1)">logs</a></li>
            </ol>
            <div class="pull-right">
                SQL输出：
                <div class="radio radio-inline">
                    <input type="radio" name="level-sql" id="level-sql1" value="DEBUG" ${(level=="DEBUG")?string("checked","")}>
                    <label for="level-sql1">是</label>
                </div>
                <div class="radio radio-inline">
                    <input type="radio" name="level-sql" id="level-sql2" value="INFO" ${(level=="INFO")?string("checked","")}>
                    <label for="level-sql2">否</label>
                </div>
            </div>
        </div>
        <div class="ibox-content">
            <div class="row">
                <div id="data">
                    <ul class="dataList"></ul>
                </div>
            </div>
        </div>
    </div>
</div>

<script type="text/javascript">
    $(function () {
        Ajax.ajax({
            url: "/admin/system/getLogDirectory",
            params: {"path": "/"},
            success: function (res) {
                if (res.code == 0) {
                    var str = "";
                    $.each(res.data, function (key, val) {
                        if (val["type"] == "folder") {
                            str += '<li title="' + val["path"] + '" type="' + val["type"] + '" ondblclick="openDirectory(this.title,1)"><div class="ft '
                                    + val["type"] + '"></div><div class="fn">' + val["name"] + '</div></li>';
                        } else {
                            str += '<li title="' + val["path"] + '" type="' + val["type"] + '" ondblclick="downLoad(this.title)"><div class="ft '
                                    + val["type"] + '"></div><div class="fn">' + val["name"] + '</div></li>';
                        }
                    });
                    $(".dataList").html(str);
                } else {
                    showMsg(res.msg, 2);
                }
            }
        });

        $("input[name='level-sql']").click(function () {
            var level = $(this).val();
            Ajax.ajax({
                url: "/admin/system/sqlLevel",
                params: {"level": level},
                success: function (data) {
                    if (data.code == 0) {
                        showMsg("修改成功，请重新打开日志文件查看", 1);
                    }
                }
            });
        });
    });

    function openDirectory(path, isFloder) {
        var index = layer.load(2, {shade: [0.1, '#000'], time: 20000});
        var t = $(this);
        Ajax.ajax({
            url: "/admin/system/getLogDirectory",
            params: {"path": path},
            success: function (res) {
                if (res.code == 0) {
                    var str = "";
                    $.each(res.data, function (key, val) {
                        if (val["type"] == "folder") {
                            str += '<li title="' + val["path"] + '" type="' + val["type"] + '" ondblclick="openDirectory(this.title,1)"><div class="ft '
                                    + val["type"] + '"></div><div class="fn">' + val["name"] + '</div></li>';
                        } else {
                            str += '<li title="' + val["path"] + '" type="' + val["type"] + '" ondblclick="downLoad(this.title)"><div class="ft '
                                    + val["type"] + '"></div><div class="fn">' + val["name"] + '</div></li>';
                        }
                    });
                    layer.close(index);
                    $(".dataList").html(str);
                    if (isFloder == 1) {
                        createBread(path);
                    }
                } else {
                    layer.close(index);
                    showMsg(res.msg, 2);
                }
            }
        });
    }


    function createBread(path) {
        var arr = path.split("/");
        var str = '<li><a title="/" href="javascript:;" onclick="openDirectory(this.title,1)">logs</a></li>';
        for (var i = 0; i < arr.length; i++) {
            if (arr[i] != "") { //path
                str += '<li><a title="' + arr[i] + '" href="javascript:;" onclick="openSingleDirectory(this)">' + arr[i] + '</a></li>';
            }
        }
        $(".breadcrumb").html(str);
        $(".breadcrumb li:last").addClass("active").children("a").removeAttr("onclick");
    }

    function openSingleDirectory(obj) {
        var $dom = $(obj).parent("li").prevAll("li");
        var path = "";
        for (var i = $dom.length - 1; i > 0; i--) {
            path += $dom.eq(i - 1).children("a").html() + "/";
        }
        path += $(obj).html();
        openDirectory("/" + path, 1);
    }

    function downLoad(file) {
        var addr = window.location.protocol + "//" + window.location.host + "/admin/system/downLog?fileName=" + file;
        var index = openLayerUrl(file, "500px", "500px", addr, true);
        layer.full(index);
    }
</script>
</body>
</html>