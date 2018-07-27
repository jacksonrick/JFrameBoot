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
            line-height: 23px;
            overflow: hidden;
            text-align: center;
            text-overflow: ellipsis;
        }

        .folder {
            background: url("/static/theme/images/file/folder.png") no-repeat;
        }

        .picture {
            background: url("/static/theme/images/file/picture.png") no-repeat;
        }

        .text {
            background: url("/static/theme/images/file/text.png") no-repeat;
        }

        .video {
            background: url("/static/theme/images/file/video.png") no-repeat;
        }

        .rar {
            background: url("/static/theme/images/file/rar.png") no-repeat;
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
            <h5>文件管理</h5>
            <ol class="breadcrumb">
                <li><a title="/" href="javascript:;" onclick="openDirectory(this.title,1)">static</a></li>
            </ol>
        </div>
        <div class="alert alert-warning mb0">
            <i class="fa fa-exclamation-circle"></i>文件管理只查看站点静态目录(/static/...)，双击查看。
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

<div class="layer-win" style="width: 600px;height:500px;overflow: scroll;">
    <img src="" id="layer-img">
</div>

<script type="text/javascript">
    $(function () {
        Ajax.ajax({
            url: "/admin/system/getDirectory",
            params: {"path": "/"},
            success: function (res) {
                if (res.code == 0) {
                    var str = "";
                    $.each(res.data, function (key, val) {
                        str += '<li title="' + val["path"] + '" type="' + val["type"] + '" ondblclick="openDirectory(this.title,1)"><div class="ft '
                                + val["type"] + '"></div><div class="fn">' + val["name"] + '</div></li>';
                    });
                    $(".dataList").html(str);
                } else {
                    showMsg(res.msg, 2);
                }
            }
        });
    });

    function openDirectory(path, isFloder) {
        var index = layer.load(2, {shade: [0.1, '#000'], time: 20000});
        var t = $(this);
        Ajax.ajax({
            url: "/admin/system/getDirectory",
            params: {"path": path},
            success: function (res) {
                if (res.code == 0) {
                    var str = "";
                    $.each(res.data, function (key, val) {
                        if (val["type"] == "folder") {
                            str += '<li title="' + val["path"] + '" type="' + val["type"] + '" ondblclick="openDirectory(this.title,1)"><div class="ft '
                                    + val["type"] + '"></div><div class="fn">' + val["name"] + '</div></li>';
                        } else if (val["type"] == "picture") {
                            str += '<li title="' + val["path"] + '" type="' + val["type"] + '" ondblclick="openPicture(this.title)"><div class="ft '
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
        var str = '<li><a title="/" href="javascript:;" onclick="openDirectory(this.title,1)">static</a></li>';
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

    function openPicture(file) {
        var src = '/static/' + file + '';
        $("#layer-img").attr("src", src);
        openLayerContent("图片预览", 500, 500, $(".layer-win"));
    }

    function downLoad(file) {
        window.open("/static" + file);
    }
</script>
</body>
</html>