<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <title>打印插件</title>
</head>

<body>

<button onclick="p()">打印</button>
<div id="content" style="color: red;">
    <h1>dasdq1213</h1>
    <p>dasdqweqwejio</p>
    <p>dasdqweqwejio</p>
    <p>dasdqweqwejio</p>
</div>

<h3>dasidqweuqd</h3>

<script type="text/javascript" src="/static/library/jquery/jquery-1.11.2.min.js"></script>
<script type="text/javascript" src="http://code.jquery.com/jquery-migrate-1.1.0.js"></script><!-- opera -->
<script type="text/javascript" src="/static/library/plugins/print/jquery.jqprint-0.3.js"></script>
<script type="text/javascript">
    function p() {
        $("#content").jqprint();
    }
</script>
</body>
</html>