<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8"/>
    <title>Info</title>
    <link type="text/css" href="/static/css/bootstrap.min.css" rel="stylesheet">
    <style type="text/css">
        #content .panel-body, .popover-content {
            overflow: scroll;
        }
    </style>
</head>
<body>

<div class="container">
    <#include "header.ftl">

    <h1 style="margin-top: 30px;" id="info-id">id</h1>
    <h4>
        <small>version:</small>
        <small id="info-ver">ver</small>
        <small>desc:</small>
        <small id="info-desc">desc</small>
    </h4>
    <a class="label label-danger" id="refresh">Refresh cloud config</a>

    <ul class="nav nav-tabs" style="margin-top: 10px;margin-bottom: -1px;">
        <li data-id="1" class="active"><a href="javascript:;">health</a></li>
        <li data-id="2"><a href="javascript:;">env</a></li>
        <li data-id="3"><a href="javascript:;">dump</a></li>
        <li data-id="4"><a href="javascript:;">metrics</a></li>
        <li data-id="5"><a href="javascript:;">trace</a></li>
        <li data-id="6"><a href="javascript:;">features</a></li>
    </ul>

    <div class="panel panel-default" style="border-top-left-radius: unset;border-top-right-radius: unset;">
        <div class="panel-body" id="content">加载中...</div>
    </div>

    <div class="modal fade bs-example-modal-sm" tabindex="-1" role="dialog" id="modal">
        <div class="modal-dialog modal-sm" role="document">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span>
                    </button>
                    <h4 class="modal-title">Message</h4>
                </div>
                <div class="modal-body" id="modal-content">
                </div>
            </div>
        </div>
    </div>

    <#include "footer.ftl">
</div>

<script src="/static/js/jquery-2.1.1.min.js"></script>
<script src="/static/js/bootstrap.min.js"></script>
<script>
    var monitor = getQueryString("instance");
    monitor = monitor.substring(0, monitor.lastIndexOf('/'));
    $(function () {
        getInfo();
        getHealth();

        $(".nav-tabs>li").click(function () {
            $("#content").html('');
            var t = $(this);
            t.addClass("active").siblings("li").removeClass("active");
            var id = t.data("id");
            switch (id) {
                case 1:
                    getHealth();
                    break;
                case 2:
                    getEnv();
                    break;
                case 3:
                    getDump();
                    break;
                case 4:
                    getMetrics();
                    break;
                case 5:
                    getTrace();
                    break;
                case 6:
                    getFeatures();
                    break;
                default:
                    alert("error");
            }
        });

        $("#refresh").click(function () {
            Ajax.refresh();
        });

    });

    function getInfo() {
        Ajax.info();
    }

    function getHealth() {
        Ajax.ajax({
            operate: "/health",
            callback: function (data) {
                var html = '<div class="row">';
                $.each(data, function (k, v) {
                    if (k == "description") {
                        return true;
                    }
                    html += '<div class="panel panel-success col-md-7" style="margin-left: 20px;padding: 0px;">';
                    var bodys = '<div class="panel-heading">' + k + '</div><div class="panel-body">';
                    bodys += lablest(v.status ? v.status : v);
                    if (k == "status") {
                        bodys += '<p class="text-muted"><b class="text-info">description: </b>' + data.description + '</p>';
                    } else if (k == "discoveryComposite") {
                        bodys += '<p class="text-muted"><b class="text-info">description: </b>' + v.description + '</p>';
                        bodys += '<p class="text-muted"><b class="text-info">discoveryClient STATUS: </b>' + lable(v.discoveryClient.status) + '</p>';
                        bodys += '<p class="text-muted"><b class="text-info">discoveryClient-services: </b>' + v.discoveryClient.services + '</p>';
                        bodys += '<p class="text-muted"><b class="text-info">eureka STATUS: </b>' + lable(v.eureka.status) + '</p>';
                    } else if (k == "jms") {
                        if (v.status == "DOWN") {
                            bodys += '<p class="text-muted"><b class="text-info">error: </b>' + v.error + '</p>';
                        } else {
                            bodys += '<p class="text-muted"><b class="text-info">provider: </b>' + v.provider + '</p>';
                        }
                    } else if (k == "mail") {
                        bodys += '<p class="text-muted"><b class="text-info">location: </b>' + v.location + '</p>';
                        if (v.status == "DOWN") {
                            bodys += '<p class="text-muted"><b class="text-info">error: </b>' + v.error + '</p>';
                        }
                    } else if (k == "diskSpace") {
                        bodys += '<p class="text-muted"><b class="text-info">free: </b>' + disk(v.free) + '</p>';
                        bodys += '<p class="text-muted"><b class="text-info">total: </b>' + disk(v.total) + '</p>';
                        bodys += '<p class="text-muted"><b class="text-info">threshold: </b>' + disk(v.threshold) + '</p>';
                    } else if (k == "redis") {
                        if (v.status == "DOWN") {
                            bodys += '<p class="text-muted"><b class="text-info">error: </b>' + v.error + '</p>';
                        } else {
                            bodys += '<p class="text-muted"><b class="text-info">version: </b>' + v.version + '</p>';
                        }
                    } else if (k == "db") {
                        bodys += '<p class="text-muted"><b class="text-info">database: </b>' + v.database + '</p>';
                        bodys += '<p class="text-muted"><b class="text-info">hello: </b>' + v.hello + '</p>';
                    } else if (k == "refreshScope") {

                    } else if (k == "hystrix") {

                    } else if (k == "configServer") {
                        bodys += '<p class="text-muted"><b class="text-info">propertySources: </b>' + v.propertySources.join(",") + '</p>';
                    }
                    bodys += '</div>';
                    html += bodys + '</div>';
                });
                $("#content").append(html + '</div>');
            }
        });
    }

    function getEnv() {
        Ajax.ajax({
            operate: "/env",
            callback: function (data) {
                $.each(data, function (k, v) {
                    var html = '<div class="panel panel-success">';
                    var bodys = '<div class="panel-heading">' + k + '</div><div class="panel-body"><table class="table table-hover table-bordered table-striped"><tbody>';
                    $.each(v, function (k2, v2) {
                        if (k2 == 0) k2 = k;
                        bodys += '<tr><td>' + k2 + '</td><td>' + v2 + '</td></tr>';
                    });
                    bodys += '</tbody></table></div>';
                    html += bodys + '</div>';
                    $("#content").append(html);
                });
            }
        });
    }

    function getDump() {
        Ajax.ajax({
            operate: "/dump",
            callback: function (data) {
                var html = '<ul class="list-group">';
                $.each(data, function (k, v) {
                    var bodys = '<li class="list-group-item">' + v.threadName +
                            ' <span class="pull-right"><span class="label label-default">' + v.threadState + '</span>' +
                            ' <a tabindex="0" class="label label-info pop" data-toggle="popover" title="Details" data-content="lockName: ' + v.lockName + '<br>threadId: ' + v.threadId + '<br>waitedCount: ' + v.waitedCount + '<br>blockedCount: ' + v.blockedCount + '<br>suspended: ' + v.suspended + '<br>inNative: ' + v.inNative + '">Details</a></span></li>'
                    html += bodys;
                });
                $("#content").append(html + '</ul>');
                $('.pop').popover({
                    html: true,
                    trigger: 'click',
                    placement: 'left'
                });
            }
        });
    }

    function getMetrics() {
        Ajax.ajax({
            operate: "/metrics",
            callback: function (data) {
                var html = '<ul class="list-group">';
                $.each(data, function (k, v) {
                    var bodys = '<li class="list-group-item">' + k + ': ' + v + '</li>'
                    html += bodys;
                });
                $("#content").append(html + '</ul>');
            }
        });
    }

    function getTrace() {
        Ajax.ajax({
            operate: "/trace",
            callback: function (data) {
                var html = '<ul class="list-group">';
                $.each(data, function (k, v) {
                    var requests = '';
                    $.each(v.info.headers.request, function (k1, v1) {
                        requests += k1 + ': ' + v1 + '<br>';
                    });
                    var responses = '';
                    $.each(v.info.headers.response, function (k2, v2) {
                        responses += k2 + ': ' + v2 + '<br>';
                    });
                    var bodys = '<li class="list-group-item">' + v.info.path + ' <small>' + v.timestamp + '</small>' +
                            ' <span class="pull-right"><span class="label label-default">' + v.info.method + '</span>' +
                            ' <a tabindex="0" class="label label-info pop" data-toggle="popover" title="request" data-content="' + requests + '">request</a>' +
                            ' <a tabindex="0" class="label label-info pop" data-toggle="popover" title="response" data-content="' + responses + '">response</a></span></li>'
                    html += bodys;
                });
                $("#content").append(html + '</ul>');
                $('.pop').popover({
                    html: true,
                    trigger: 'click',
                    placement: 'left'
                });
            }
        });
    }

    function getFeatures() {
        Ajax.ajax({
            operate: "/features",
            callback: function (data) {
                var table = '<table class="table table-hover table-bordered table-striped"><thead><tr><th>name</th><th>type</th><th>version</th></tr></thead><tbody>';
                $.each(data.enabled, function (k, v) {
                    table += '<tr><td>' + v.name + '</td><td>' + v.type + '</td><td>' + v.version + '</td></tr>';
                });
                table += '</tbody></table>';
                $("#content").append(table);
            }
        });
    }

    Ajax = {
        info: function () {
            $.ajax({
                url: "/info?monitor=" + monitor + "/info",
                type: "post",
                contentType: 'application/json',
                dataType: "json",
                success: function (data) {
                    $("#info-id").html(data.app.name);
                    $("#info-ver").html(data.app.version);
                    $("#info-desc").html(data.app.description);
                }
            });
        },
        ajax: function (config) {
            $.ajax({
                url: "/info?monitor=" + monitor + config.operate,
                type: "post",
                contentType: 'application/json',
                dataType: "json",
                success: function (data) {
                    $("#content").html('');
                    config.callback(data);
                }
            });
        },
        refresh: function () {
            $.ajax({
                url: "/info/refresh?monitor=" + monitor + "/refresh",
                type: "post",
                contentType: 'application/json',
                dataType: "json",
                success: function (data) {
                    if (data.length > 0) {
                        $("#modal-content").html(data.join('<br>'));
                    } else {
                        $("#modal-content").html("no keys changed");
                    }
                    $("#modal").modal('show');
                }
            });
        }
    }

    function lable(str) {
        if (str == "UP") {
            return '<span class="label label-success">' + str + '</span>'
        } else {
            return '<span class="label label-danger">' + str + '</span>'
        }
    }

    function lablest(str) {
        if (str == "UP") {
            return '<p><b>STATUS: </b><span class="label label-success">' + str + '</span></p>'
        } else {
            return '<p><b>STATUS: </b><span class="label label-danger">' + str + '</span></p>'
        }
    }

    function disk(val) {
        return (val / 1000 / 1000 / 1000).toFixed(2) + "GB";
    }

    function getQueryString(name) {
        var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)");
        var r = window.location.search.substr(1).match(reg);
        if (r != null)
            return unescape(r[2]);
        return null;
    }
</script>
</body>
</html>