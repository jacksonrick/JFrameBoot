<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8"/>
    <title>Title</title>
    <script src="/static/theme/js/jquery.min.js"></script>
</head>

<body>
<h3>请使用微信扫码</h3>
<img src="/qrimg?type=bind" width="200px" height="200px">
<p id="msg" style="color: red;"></p>

<script type="text/javascript">
    var count = 0; // 延迟
    var timeid = setInterval("check()", 3000); // 每3秒查询一次

    function check() {
        console.log(count);

        if (count > 0) {
            $.ajax({
                type: "get",
                async: true,
                url: "/checkqr",
                dataType: "json",
                success: function (data) {
                    console.log(data);
                    if (data.code == 0) {
                        location.href = data.data;
                    } else if (data.code == 1) {
                        $("#msg").html("扫码中");
                    } else if (data.code == 2) {
                        $("#msg").html("已扫码，请确认");
                    } else if (data.code == 3) {
                        $("#msg").html("取消操作");
                        window.clearInterval(timeid);
                    } else if (data.code == 4) {
                        $("#msg").html("确认状态错误");
                        window.clearInterval(timeid);
                    } else if (data.code == 5) {
                        $("#msg").html('请刷新二维码');
                        window.clearInterval(timeid);
                    }
                }
            });
        }

        if (count == 20) { // 60s过期
            window.clearInterval(timeid);
            $("#msg").html('超时[60s]请刷新二维码');
        }
        count++;
    }
</script>
</body>
</html>