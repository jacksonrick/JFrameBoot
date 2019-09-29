<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <title>Layer弹窗插件</title>
    <link href="/static/common/css/bootstrap.min.css" rel="stylesheet" type="text/css"/>
    <style type="text/css">
        .div2 {
            display: none;
        }

        body {
            background-color: black;
        }
    </style>
</head>

<body>

<h4>skin:'layer-ext-espresso'</h4>
<div class="div1">
    <button class="btn btn-primary" id="btn0">Msg</button>
    <button class="btn btn-primary" id="btn1">提示框</button>
    <button class="btn btn-primary" id="btn2">内容框</button>
    <button class="btn btn-primary" id="btn3">Tips</button>
    <button class="btn btn-primary" id="btn4">加载</button>
    <button class="btn btn-primary" id="btn41">加载2</button>
    <button class="btn btn-primary" id="btn5">confirm</button>
    <button class="btn btn-primary" id="btn6">prompt</button>
</div>
<div class="div2">
    <h5>wqe5weq6546qw4e6</h5>
    <h5>wqe5weq6546qw4e6</h5>
    <h5>wqe5weq6546qw4e6</h5>
    <h5>wqe5weq6546qw4e6</h5>
    <h5>wqe5weq6546qw4e6</h5>
    <h5>wqe5weq6546qw4e6</h5>
    <h5>wqe5weq6546qw4e6</h5>
    <h5>wqe5weq6546qw4e6</h5>
    <h5>wqe5weq6546qw4e6</h5>
    <h5>wqe5weq6546qw4e6</h5>
    <h5>wqe5weq6546qw4e6</h5>
    <h5>wqe5weq6546qw4e6</h5>
    <h5>wqe5weq6546qw4e6</h5>
    <h5>wqe5weq6546qw4e6</h5>
    <h5>wqe5weq6546qw4e6</h5>
    <h5>wqe5weq6546qw4e6</h5>
    <h5>wqe5weq6546qw4e6</h5>
    <h5>wqe5weq6546qw4e6</h5>
    <h5>wqe5weq6546qw4e6</h5>
    <h5>wqe5weq6546qw4e6</h5>
    <h5>wqe5weq6546qw4e6</h5>
    <h5>wqe5weq6546qw4e6</h5>
    <h5>wqe5weq6546qw4e6</h5>
    <h5>wqe5weq6546qw4e6</h5>
    <h5>wqe5weq6546qw4e6</h5>
    <h5>wqe5weq6546qw4e6</h5>
    <h5>wqe5weq6546qw4e6</h5>
    <h5>wqe5weq6546qw4e6</h5>
    <button class="btn btn-primary" id="btn11">父窗口提示信息</button>
    <button class="btn btn-primary" id="btn12">父窗口刷新</button>
    <button class="btn btn-primary" id="btn13">关闭</button>
</div>

<script type="text/javascript" src="/static/library/jquery/jquery-1.11.2.min.js"></script>
<script type="text/javascript" src="/static/library/plugins/layer/layer.js"></script>
<script type="text/javascript">
    $(function () {
        layer.config({
            shift: 2,
            moveType: 1
        });

        $("#btn0").click(function () {
            layer.msg("123", {time: 100000})
        });

        $("#btn1").click(function () {
            layer.alert("hello!!!", {
                icon: 1
            }, function () {
                layer.msg("Hi,Hi,Hi,Hi,Hi...", {time: 2000, icon: 6});
            });
        });
        var index;
        $("#btn2").click(function () {
            index = layer.open({
                type: 1,
                shade: [0.5, '#393D49'],
                shadeClose: true,
                title: '详情',
                area: ['600px'],
                content: $(".div2")
            });
        });

        $("#btn3").click(function () {
            layer.tips('我是另外一个tips，只不过我长得跟之前那位稍有些不一样。', '#btn2', {
                tips: [1, '#3595CC'],
                time: 4000
            });
        });

        $("#btn4").click(function () {
            layer.msg('加载中...', {icon: 10, offset: 0, time: 20000}, function () {
                layer.msg("您的网络好像有点问题...");
            });
        });

        $("#btn41").click(function () {
            var index = layer.load(2, {shade: [0.1, '#000'], time: 20000});
            setTimeout(function () {
                layer.close(index);
            }, 30000);
        });

        $("#btn11").click(function () {
            parent.layer.msg('Hi, man', {shade: 0.3, time: 1000})
        });
        $("#btn12").click(function () {
            parent.location.reload();
        });
        $("#btn13").click(function () {
            layer.close(index);
        });

        $("#btn5").click(function () {
            layer.confirm('确定要..吗？', {
                btn: ['确定', '取消']
            }, function () {
                layer.msg("确定");
            });
        });

        layer.use('extend/layer.ext.js', function () {
            layer.ext = function () {
                layer.prompt({})
            };
        });
        $("#btn6").click(function () {
            layer.prompt({
                title: '输入号码',
                formType: 0
            }, function (val1) {
                layer.prompt({title: '再输入一次', formType: 0}, function (val2, index) {
                    if (val1 != val2) {
                        layer.msg("输入不一样", {icon: 1});
                    } else {
                        layer.msg("输入一样");
                    }
                });
            });
        });
    })
</script>
</body>
</html>