<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <title>Fancybox</title>
    <link href="/static/common/css/bootstrap.min.css" rel="stylesheet">
    <link href="/static/library/plugins/fancybox/jquery.fancybox.css" rel="stylesheet">
    <script type="text/javascript" src="/static/library/jquery/jquery-1.8.3.min.js"></script>
    <script type="text/javascript" src="/static/library/plugins/fancybox/jquery.fancybox.js"></script>
</head>

<body>

<a data-fancybox-group="gallery" href="/static/upload/images/201609/001.jpg" class="img-area">
    <img src="/static/upload/images/201609/001.jpg" width="60" height="60" class="img-thumbnail"/>
</a>
<a data-fancybox-group="gallery" href="/static/upload/images/201609/002.jpg" class="img-area">
    <img src="/static/upload/images/201609/002.jpg" width="60" height="60" class="img-thumbnail"/>
</a>

<script type="text/javascript">
    $(function () {
        $("a[data-fancybox-group=gallery]").fancybox();
    });
</script>
</body>
</html>