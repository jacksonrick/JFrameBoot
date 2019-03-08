<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <title>百度Echarts案例2</title>
    <script type="text/javascript" src="/static/library/jquery/jquery-1.11.2.min.js"></script>
    <script type="text/javascript" src="/static/library/plugins/echarts/echarts.common.min.js"></script>
</head>

<body>
<div style="margin-bottom: 50px;">
    <button onclick="a()">Line</button>
    <button onclick="b()">Pie</button>
    <button onclick="c()">Bar</button>
</div>
<div style="padding: 10px;">
    <div id="echarts" style="height: 600px;"></div>
</div>


<script type="text/javascript">

    function a() {
        var myChart = echarts.init(document.getElementById('echarts'));
        myChart.showLoading({
            text: '正在努力的读取数据中...'
        });
        $.ajax({
            type: "post",
            url: "testEcharts1.do",
            data: null,
            dataType: "text",
            success: function (result) {
                //转换为Object对象
                var obj = eval("(" + result + ")");
                myChart.setOption(obj);
                myChart.hideLoading();
            }
        });
    }

    function b() {
        var myChart = echarts.init(document.getElementById('echarts'));
        myChart.showLoading({
            text: '正在努力的读取数据中...'
        });
        $.ajax({
            type: "post",
            url: "testEcharts2.do",
            data: null,
            dataType: "text",
            success: function (result) {
                //转换为Object对象
                var obj = eval("(" + result + ")");
                myChart.setOption(obj);
                myChart.hideLoading();
            }
        });
    }

    function c() {
        var myChart = echarts.init(document.getElementById('echarts'));
        myChart.showLoading({
            text: '正在努力的读取数据中...'
        });
        $.ajax({
            type: "post",
            url: "testEcharts3.do",
            data: null,
            dataType: "text",
            success: function (result) {
                //转换为Object对象
                var obj = eval("(" + result + ")");
                myChart.setOption(obj);
                myChart.hideLoading();
            }
        });
    }
</script>

</body>
</html>