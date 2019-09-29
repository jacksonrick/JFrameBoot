<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8"/>
    <title>PDF导出测试</title>
</head>

<body>

<button onclick="downloadPdf()">将该页面导出到PDF</button>

<h2>用户数据</h2>
<table class="table">
    <thead>
    <tr>
        <td>序号</td>
        <td>用户</td>
        <td>手机</td>
        <td>注册时间</td>
        <td>状态</td>
    </tr>
    </thead>
    <tbody>
    <tr>
        <td>1</td>
        <td>feifei99</td>
        <td>21651</td>
        <td>2016-01-22 00:00</td>
        <td>正常</td>
    </tr>
    <tr>
        <td>2</td>
        <td>feifei99</td>
        <td>21651</td>
        <td>2016-01-22 00:00</td>
        <td>正常</td>
    </tr>
    <tr>
        <td>3</td>
        <td>feifei97</td>
        <td>21651</td>
        <td>2016-01-22 00:00</td>
        <td>正常</td>
    </tr>
    <tr>
        <td>4</td>
        <td>feifei97</td>
        <td>21651</td>
        <td>2016-01-22 00:00</td>
        <td>冻结</td>
    </tr>
    <tr>
        <td>5</td>
        <td>feifei90</td>
        <td>21651</td>
        <td>2016-01-22 00:00</td>
        <td>正常</td>
    </tr>
    <tr>
        <td>6</td>
        <td>feifei77</td>
        <td>21651</td>
        <td>2016-01-21 00:00</td>
        <td>正常</td>
    </tr>
    <tr>
        <td>7</td>
        <td>feifei77</td>
        <td>21651</td>
        <td>2016-01-21 00:00</td>
        <td>正常</td>
    </tr>
    <tr>
        <td>8</td>
        <td>feifei66</td>
        <td>21651</td>
        <td>2016-01-22 00:00</td>
        <td>正常</td>
    </tr>
    <tr>
        <td>9</td>
        <td>feifei66</td>
        <td>21651</td>
        <td>2016-01-22 00:00</td>
        <td>冻结</td>
    </tr>
    <tr>
        <td>10</td>
        <td>feifei56</td>
        <td>21651</td>
        <td>2016-01-28 00:00</td>
        <td>正常</td>
    </tr>
    </tbody>
</table>

<a id="dl" href="">下载</a>
<script src="/static/library/jquery/jquery-2.1.1.min.js"></script>
<script type="text/javascript">
    function downloadPdf() {
        var url = document.location.href;
        $.post("/htmltopdf", {url: url}, function (data) {
            if (data.code == 1) {
                $("#dl").attr("href", data.msg);
            } else {
                alert(data.msg);
            }
        }, "json")
    }
</script>
</body>

</html>