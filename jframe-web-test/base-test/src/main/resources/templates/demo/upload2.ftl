<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta name="renderer" content="webkit">
    <title>dropzone</title>
    <link rel="stylesheet" type="text/css" href="/static/common/css/bootstrap.min.css">
    <link type="text/css" href="/static/library/plugins/dropzone/dropzone.css" rel="stylesheet"/>
</head>

<body>

<div class="drop-main">
    <div id="dropzone">
        <form class="dropzone dz-clickable" id="uploads" method="post" enctype="multipart/form-data">
            <div class="dz-message">
                拖拽文件到此可以上传或者直接点击蓝色虚线框处<br>
            </div>
        </form>
    </div>
</div>
<button class="btn btn-info" id="btnUp">上传</button>

<div class="drop-main">
    <div id="dropzone">
        <form class="dropzone dz-clickable" id="uploads2" method="post" enctype="multipart/form-data">
            <div class="dz-message">
                拖拽文件到此可以上传或者直接点击蓝色虚线框处<br>
            </div>
        </form>
    </div>
</div>
<button class="btn btn-info" id="btnUp2">续传</button>

<script type="text/javascript" src="/static/library/jquery/jquery-1.11.2.min.js"></script>
<script type="text/javascript" src="/static/library/plugins/dropzone/dropzone.js"></script>
<script>
    $(function () {
        Dropzone.autoDiscover = false;
        var myDropzone = new Dropzone("#uploads", {
            url: "/uploadAppend?append=0",
            autoProcessQueue: false,
            paramName: "file",
            maxFiles: 5,
            maxFilesize: 1024,
            addRemoveLinks: true
        });

        var myDropzone2;
        myDropzone.on("success", function (data) {
            var ret = data.xhr.responseText;
            console.log(ret)
            var path = $.parseJSON(ret).url;

            myDropzone2 = new Dropzone("#uploads2", {
                url: "/uploadAppend?append=1&path=" + path,
                autoProcessQueue: false,
                paramName: "file",
                maxFiles: 5,
                maxFilesize: 1024,
                addRemoveLinks: true
            });
        });

        $("#btnUp").click(function () {
            myDropzone.processQueue();
        });

        $("#btnUp2").click(function () {
            myDropzone2.processQueue();
        });
    });
</script>
</body>
</html>