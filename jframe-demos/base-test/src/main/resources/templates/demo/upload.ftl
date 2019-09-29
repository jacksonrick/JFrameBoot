<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta name="renderer" content="webkit">
    <title>自定义的APP上传插件</title>
    <link rel="stylesheet" type="text/css" href="/static/library/plugins/webuploader/webuploader.css">
</head>

<body>
<div id="avatar" class="list"><a id="avatarBtn"></a></div>
<div id="avatar2" class="list"><a id="avatarBtn2"></a></div>


<script src="/static/library/jquery/jquery-2.1.1.min.js"></script>
<script src="/static/library/plugins/layer/mobile/layer.js"></script>
<script type="text/javascript" src="/static/library/plugins/webuploader/webuploader.js"></script>
<script type="text/javascript" src="/static/common/js/upload-app.js"></script>
<script>
    $(function () {
        $("#avatarBtn").AppUploader({
            id: "avatar",
            limit: 1
        });
        $("#avatarBtn2").AppUploader({
            id: "avatar2",
            limit: 2
        });
    });
</script>
</body>
</html>