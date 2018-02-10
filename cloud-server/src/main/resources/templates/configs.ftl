<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8"/>
    <title>Index</title>
    <link type="text/css" href="/static/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>
<div class="container">
    <#include "header.ftl">

    <h1 style="margin-top: 30px;">Config Server</h1>
    <div id="content"></div>

    <#include "footer.ftl">
</div>

<script src="/static/js/jquery-2.1.1.min.js"></script>
<script>
    $(function () {
        var configs = ["sys-dev", "sys-pro"];
        $.each(configs, function (k, v) {
            $.ajax({
                async: false,
                url: "/cfg/" + v + ".json",
                type: "get",
                contentType: 'application/json',
                dataType: "json",
                success: function (data) {
                    var table = '<h3 style="margin-top: 20px;">' + v + '.properties</h3><table class="table table-hover table-bordered table-striped" style="width: 50%;"><thead><tr><th>key</th><th>value</th></tr></thead><tbody>';
                    $.each(data, function (k2, v2) {
                        table += '<tr><td>' + k2 + '</td><td>' + v2 + '</td></tr>';
                    });
                    table += '</tbody></table>';
                    $("#content").append(table);
                }
            });
        });

    });
</script>
</body>
</html>