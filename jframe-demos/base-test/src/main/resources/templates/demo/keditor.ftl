<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <title>kindeditor富文本编辑器</title>
</head>

<body>

<textarea id="editor" name="content" class="form-control" style="width:100%;height:500px;"></textarea>
<div class="word-count">字数统计：<span id="wc"></span></div>

<script src="/static/library/jquery/jquery-2.1.1.min.js"></script>
<script src="/static/library/plugins/kindeditor/kindeditor-min.js"></script>
<script type="text/javascript">
    $(function () {
        var editor;
        KindEditor.ready(function (K) {
            editor = K.create('textarea[name="content"]', {
                resizeType: 1,
                allowPreviewEmoticons: false,
                allowImageUpload: true,
                uploadJson: '<%=basePath%>uploadwithKE.do',
                afterBlur: function () {
                    this.sync();
                },
                afterChange: function () {
                    K('#wc').html(this.count('text'));
                }
            });
        });
    })
</script>
</body>
</html>