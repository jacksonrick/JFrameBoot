<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <title></title>
    <#include "include.ftl"/>
    <style type="text/css">
        .form-control {
            display: inline;
        }
    </style>
</head>

<body>
<div class="ibox float-e-margins">
    <div class="ibox-content">
        <h3 style="margin-top: 30px;">Jenkins自动化部署</h3>
        <h5>
            Jenkins服务器IP：<input class="form-control" type="text" id="serverIP" value="http://120.79.56.97:9999" style="width: 200px"/>
            工程名称：<input class="form-control" type="text" id="serverProj" value="JFrame" style="width: 80px"/>
            <br><br>
            用户名/密码：
            <input class="form-control" type="text" id="username" value="zhigui" style="width: 100px"/>
            <input class="form-control" type="password" id="password" value="zhigui1688," style="width: 100px"/>

            <button class="btn btn-danger" id="start">开始自动部署</button>
            <button class="btn btn-info" onclick="getInfo()"><i class="fa fa-refresh"></i></button>
        </h5>

        <div id="content" style="margin-top: 20px">加载中...</div>
    </div>
</div>


<script>
    $(function () {
        getInfo();

        $("#start").click(function () {
            Ajax.ajax({
                operate: "/job/" + $("#serverProj").val() + "/build",
                type: "post",
                callback: function (data) {
                    if (data.code == 0) {
                        toast(1, data.msg, "请等待3~5分钟");
                        $("#start").attr("disabled", "disabled");
                    } else {
                        toast(2, data.msg, "请检查参数");
                    }
                }
            });
        });
    });

    function getInfo() {
        Ajax.ajax({
            operate: "/job/" + $("#serverProj").val() + "/api/json?pretty=true",
            type: "get",
            callback: function (data) {
                if (data.code == 0) {
                    var json = $.parseJSON(data.data);
                    var html = "";
                    var img = $("#serverIP").val() + "/static/0d7a2cb1/images/16x16/" + json.color + ".gif";
                    var st = "已完成";
                    if (json.color == "blue_anime") {
                        st = "正在进行";
                    }
                    html += "<p><b>当前状态：</b><img src='" + img + "'/> " + st + "</p>";
                    html += "<p><b>全名：</b>" + json.fullName + "</p>";
                    html += "<p><b>地址：</b>" + json.url + "</p>";
                    html += "<p><b>类：</b>" + json._class + "</p>";
                    html += "<p><b>当前编号：</b>" + json.lastBuild.number + "</p>";
                    html += "<p><b>最近一次成功部署：</b>" + json.lastSuccessfulBuild.number + "</p>";
                    html += "<p><b>最近一次失败部署：</b>" + json.lastUnsuccessfulBuild.number + "</p>";
                    html += "<p><b>模块：</b><br>";
                    var modules = "";
                    $.each(json.modules, function (k, v) {
                        modules += v.name + "<br>";
                    });
                    html += modules + "</p>"
                    $("#content").html(html);
                } else if (data.code == -1) {
                    openLayerHTML(data.data, "500px", "400px");
                }
                else {
                    alert(data.msg);
                }
            }
        });
    }

    Ajax = {
        ajax: function (config) {
            var serverIP = $("#serverIP").val();
            if (serverProj == "") {
                alert("服务器IP不能为空");
                return;
            }
            if ($("#serverProj").val() == "") {
                alert("工程名称不能为空");
                return;
            }
            $.ajax({
                url: "/admin/system/jenkins_opt?auth=" + $("#username").val() + ":" + $("#password").val() + "&type=" + config.type + "&ip=" + serverIP + config.operate,
                type: "post",
                contentType: 'application/json',
                dataType: "json",
                success: function (data) {
                    config.callback(data);
                }
            });
        }
    }
</script>
</body>
</html>