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
                <span class="note">最多<span class="span-color" id="limit"> 3 </span> 个文件，每个文件最多<span class="span-color"> 3 </span>MB</span>
            </div>
        </form>
    </div>
</div>

<form class="dropzone" id="uploads" method="post" enctype="multipart/form-data"></form>

<button class="btn btn-info" id="btnUp">上传</button>
<button class="btn btn-info" id="btnGet">获取</button>

<script type="text/javascript" src="/static/library/jquery/jquery-1.11.2.min.js"></script>
<script type="text/javascript" src="/static/library/plugins/dropzone/dropzone.js"></script>
<script type="text/javascript" src="/static/library/plugins/layer/layer.js"></script>
<script type="text/javascript" src="/static/common/js/upload.js"></script>
</body>
</html>