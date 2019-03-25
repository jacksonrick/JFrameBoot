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

        .form-control {
            display: inline;
        }

        .rbtn {
            z-index: 9999;
            position: absolute;
            top: 9px;
            right: 0px;
            width: 175px;
        }
    </style>
</head>
<body>

<div class="container">
    <#include "header.ftl">

    <h1 style="margin-top: 30px;">
        <span id="info-id">id</span>
        <div class="btn-group pull-right">
            <button type="button" class="btn btn-info btn-sm">Settings</button>
            <button type="button" class="btn btn-info btn-sm dropdown-toggle" data-toggle="dropdown">
                <span class="caret"></span>
            </button>
            <ul class="dropdown-menu">
                <#--<li role="separator" class="divider"></li>-->
                <li><a href="javascript:;" id="refresh">Refresh Cloud Configs</a></li>
            </ul>
        </div>
    </h1>
    <h4>
        <small>version:</small>
        <small id="info-ver">ver</small>
        <small>desc:</small>
        <small id="info-desc">desc</small>
    </h4>

    <ul class="nav nav-tabs" style="margin-top: 10px;margin-bottom: -1px;">
        <li data-id="1" class="active"><a href="javascript:;">health</a></li>
        <li data-id="2"><a href="javascript:;">env</a></li>
        <li data-id="3"><a href="javascript:;">dump</a></li>
        <li data-id="4"><a href="javascript:;">metrics</a></li>
        <li data-id="5"><a href="javascript:;">trace</a></li>
        <li data-id="6"><a href="javascript:;">logfile</a></li>
        <li data-id=""><a href="javascript:;">developing...</a></li>
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
                    <h4 class="modal-title" id="title">Message</h4>
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
    var monitor = "http://" + getQueryString("instance") + "/monitor";
    $(function () {
        if (!/(http|https):\/\/[\w\-_]+(\.[\w\-_]+)+([\w\-\.,@?^=%&:/~\+#]*[\w\-\@?^=%&/~\+#])?/.test(monitor)) {
            alert("Error url!");
            return;
        }
        getInfo();
        getHealth();

        $(".nav-tabs>li").click(function () {
            $("#content").html('');
            var t = $(this);
            t.addClass("active").siblings("li").removeClass("active");
            var id = t.data("id");
            if (id == "") return;
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
                    getLog();
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
                html += '<div class="panel panel-success col-md-7" style="margin-left: 20px;padding: 0px;">'
                        + '<div class="panel-heading">status</div><div class="panel-body">'
                        + lablest(data.status) + '</div></div>';

                $.each(data.details, function (k, v) {
                    html += '<div class="panel panel-success col-md-7" style="margin-left: 20px;padding: 0px;">';
                    var bodys = '<div class="panel-heading">' + k + '</div><div class="panel-body">';
                    bodys += lablest(v.status ? v.status : v);
                    if (k == "discoveryComposite") {
                        bodys += '<p class="text-muted"><b class="text-info">discoveryComposite STATUS: </b>' + lable(v.status) + '</p>';
                        bodys += '<p class="text-muted"><b class="text-info">discoveryClient STATUS: </b>' + lable(v.details.discoveryClient.status) + '</p>';
                        bodys += '<p class="text-muted"><b class="text-info">discoveryClient SERVs: </b>' + v.details.discoveryClient.details.services.join(", ") + '</p>';
                        bodys += '<p class="text-muted"><b class="text-info">eureka STATUS: </b>' + lable(v.details.eureka.status) + '</p>';
                        var aa = new Array();
                        $.each(v.details.eureka.details.applications, function (kk, vv) {
                            aa.push(kk + "[ " + vv + " ]");
                        });
                        bodys += '<p class="text-muted"><b class="text-info">eureka APPs: </b>' + aa.join(", ") + '</p>';
                    } else if (k == "jms") {
                        if (v.status == "DOWN") {
                            bodys += '<p class="text-muted"><b class="text-info">error: </b>' + v.details.error + '</p>';
                        } else {
                            bodys += '<p class="text-muted"><b class="text-info">provider: </b>' + v.details.provider + '</p>';
                        }
                    } else if (k == "rabbit") {
                        if (v.status == "DOWN") {
                            bodys += '<p class="text-muted"><b class="text-info">error: </b>' + v.details.error + '</p>';
                        } else {
                            bodys += '<p class="text-muted"><b class="text-info">version: </b>' + v.details.version + '</p>';
                        }
                    } else if (k == "mail") {
                        bodys += '<p class="text-muted"><b class="text-info">location: </b>' + v.details.location + '</p>';
                        if (v.status == "DOWN") {
                            bodys += '<p class="text-muted"><b class="text-info">error: </b>' + v.details.error + '</p>';
                        }
                    } else if (k == "diskSpace") {
                        bodys += '<p class="text-muted"><b class="text-info">free: </b>' + disk(v.details.free) + '</p>';
                        bodys += '<p class="text-muted"><b class="text-info">total: </b>' + disk(v.details.total) + '</p>';
                        bodys += '<p class="text-muted"><b class="text-info">threshold: </b>' + disk(v.details.threshold) + '</p>';
                    } else if (k == "redis") {
                        if (v.status == "DOWN") {
                            bodys += '<p class="text-muted"><b class="text-info">error: </b>' + v.details.error + '</p>';
                        } else {
                            bodys += '<p class="text-muted"><b class="text-info">version: </b>' + v.details.version + '</p>';
                        }
                    } else if (k == "db") {
                        bodys += '<p class="text-muted"><b class="text-info">database: </b>' + v.details.database + '</p>';
                        bodys += '<p class="text-muted"><b class="text-info">hello: </b>' + v.details.hello + '</p>';
                    } else if (k == "refreshScope") {

                    } else if (k == "hystrix") {

                    } else if (k == "configServer") {
                        if (v.status == "UNKNOWN") {
                            bodys += '<p class="text-muted"><b class="text-info">error: </b>' + v.details.error + '</p>';
                        } else {
                            bodys += '<p class="text-muted"><b class="text-info">propertySources: </b>' + v.details.propertySources.join(",") + '</p>';
                        }
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
                var html = '<h5>activeProfiles: ';
                $.each(data.activeProfiles, function (k, v) {
                    html += '<span class="label label-default">' + v + '</span>   '
                });
                $("#content").append('</h5>' + html);

                $.each(data.propertySources, function (k, v) {
                    var html = '<div class="panel panel-success">';
                    var bodys = '<div class="panel-heading">' + v.name + '</div><div class="panel-body"><table class="table table-hover table-bordered table-striped"><tbody>';
                    $.each(v.properties, function (k2, v2) {
                        //if (k2 == 0) k2 = k;
                        bodys += '<tr><td>' + k2 + '</td><td>' + v2.value + '</td></tr>';
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
            operate: "/threaddump",
            callback: function (data) {
                var html = '<ul class="list-group">';
                $.each(data.threads, function (k, v) {
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
                var metrics = {
                    jvm: new Array(),
                    system: new Array(),
                    process: new Array(),
                    tomcat: new Array(),
                    logback: new Array(),
                    http: new Array(),
                    other: new Array()
                };
                $.each(data.names, function (kk, k) {
                    var v = '<a href="javascript:;" class="label label-info get">get</a>';
                    if (k.indexOf("jvm") > -1) {
                        metrics.jvm.push({key: k, value: v});
                    }
                    else if (k.indexOf("system") > -1) {
                        metrics.system.push({key: k, value: v});
                    }
                    else if (k.indexOf("process") > -1) {
                        metrics.process.push({key: k, value: v});
                    }
                    else if (k.indexOf("tomcat") > -1) {
                        metrics.tomcat.push({key: k, value: v});
                    }
                    else if (k.indexOf("logback") > -1) {
                        metrics.logback.push({key: k, value: v});
                    }
                    else if (k.indexOf("http") > -1) {
                        metrics.http.push({key: k, value: v});
                    }
                    else {
                        metrics.other.push({key: k, value: v});
                    }
                });

                var t = "";
                $.each(metrics, function (k, v) {
                    var html = '<ul class="list-group">';
                    $.each(v, function (k2, v2) {
                        var bodys = '<li class="list-group-item"><b>' + v2.key + '</b><span class="pull-right">' + v2.value + '</span></li>'
                        html += bodys;
                    });
                    t += html + '</ul>';
                });
                $("#content").append(t);

                $(".get").click(function () {
                    var t = $(this);
                    var v = t.parent().prev('b').html();
                    Ajax.ajax({
                        operate: "/metrics/" + v,
                        type: 1,
                        callback: function (data) {
                            var h = "";
                            $.each(data.measurements, function (k, v) {
                                h += v.statistic + ": " + v.value + "  ";
                            })
                            t.html(h);
                        }
                    });
                });
            }
        });
    }

    function getTrace() {
        Ajax.ajax({
            operate: "/httptrace",
            callback: function (data) {
                var html = '<ul class="list-group">';
                $.each(data.traces, function (k, v) {
                    var requests = '';
                    $.each(v.request, function (k1, v1) {
                        if (k1 == "headers") {
                            $.each(v1, function (k2, v2) {
                                requests += k2 + ': ' + v2 + '<br>';
                            });
                        } else {
                            requests += k1 + ': ' + v1 + '<br>';
                        }
                    });

                    var responses = '';
                    $.each(v.response, function (k2, v2) {
                        if (k2 == "headers") {
                            $.each(v2, function (k3, v3) {
                                responses += k3 + ': ' + v3 + '<br>';
                            });
                        } else {
                            responses += k2 + ': ' + v2 + '<br>';
                        }
                    });
                    var bodys = '<li class="list-group-item">' + v.request.uri + '  <small> ' + v.timestamp + '</small>' +
                            ' <span class="pull-right rbtn"><span class="label label-default">' + v.request.method + '</span>' +
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

    function getLog() {
        Ajax.ajax({
            operate: "/logfile",
            callback: function (data) {
                $("#content").html(data.data);
            }
        });
    }

    Ajax = {
        info: function () {
            $.ajax({
                url: "/info?&monitor=" + monitor + "/info",
                type: "post",
                contentType: 'application/json',
                dataType: "json",
                success: function (data) {
                    if (data.code == 0) {
                        var json = $.parseJSON(data.data);
                        $("#info-id").html(json.app.name);
                        $("#info-ver").html(json.app.version);
                        $("#info-desc").html(json.app.description);
                    } else {
                        alert(data.msg);
                    }
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
                    if (config.type != 1) {
                        $("#content").html('');
                    }
                    if (data.code == 0) {
                        if (config.operate == "/logfile") {
                            config.callback(data);
                        } else {
                            var json = $.parseJSON(data.data);
                            console.log(json);
                            if (json.error) {
                                alert(json.message);
                            } else {
                                config.callback(json);
                            }
                        }
                    } else {
                        alert(data.msg);
                    }
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
                    if (data.code == 0) {
                        var json = $.parseJSON(data.data);
                        if (json.length > 0) {
                            $("#modal-content").html(json.join('<br>') + " changed.");
                        } else {
                            $("#modal-content").html("no keys changed.");
                        }
                        $("#title").html("Success");
                        $("#modal").modal('show');
                    } else {
                        $("#title").html("Message");
                        alert(data.msg);
                    }
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