<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta name="renderer" content="webkit">
    <title>带表情的编辑器</title>
    <link href="/static/library/plugins/editor/css/smohan.face.css" rel="stylesheet">
</head>

<body>

<div class="container">
    <div class="row">
        <div class="col-md-2 com-xs-2">评论</div>
        <div class="col-md-10 com-xs-10">
            <div id="Smohan_FaceBox">
                <textarea name="comment" id="Smohan_text" class="smohan_text"></textarea>
                <p id="fbox"><a href="javascript:void(0)" class="face" title="表情"></a></p>
                <a class="btn btn-info" id="reply" style="float: right;width: 100px;">回复</a>
            </div>
            <div id="Zones"></div>
        </div>
    </div>
    <div id="test"></div>
</div>

<script src="/static/library/plugins/editor/smohan.face.js"></script>
<script>
    $(function () {

        $("a.face").smohanfacebox({
            Event: "click",
            divid: "fbox",
            textid: "Smohan_text"
        });

        $("#reply").click(function () {
            $("#test").html($("#Smohan_text").val());
            $("#test").replaceface($("#test").html());
        })
    })
</script>
</body>
</html>
