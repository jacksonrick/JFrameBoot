<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta name="renderer" content="webkit">
    <title>自定义的上传插件</title>
<#include "demo/include.ftl"/>
    <link type="text/css" href="/static/library/plugins/dropzone/dropzone.css" rel="stylesheet"/>
    <script type="text/javascript" src="/static/library/plugins/dropzone/dropzone.js"></script>
</head>

<body>

<div class="form-group">
    <a class="img-area-btn" id="avatar"></a>
</div>
<div class="form-group">
    <a class="img-area-btn" id="file"></a>
</div>

<div class="form-group">
    <a class="img-area-btn" id="image"></a>
</div>

<script>
    $("#avatar").Uploader({
        limit: 2,
        default: '',
        type: 1
    });

    $("#file").Uploader({
        limit: 1,
        default: '',
        type: 2
    });

    $("#image").click(function () {
        var t = $(this);
        $.Uploader2({
            callback: function (data) {
                t.html(data);
            }
        });
    });
</script>
</body>
</html>
