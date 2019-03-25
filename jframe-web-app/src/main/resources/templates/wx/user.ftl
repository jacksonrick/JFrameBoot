<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8"/>
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Title</title>
    <script src="/static/theme/js/jquery.min.js"></script>
</head>
<body>

用户信息 <span id="exp"></span>
<div>openid: <span id="openid"></span></div>
<div>nickname: <span id="nickname"></span></div>
<div>province: <span id="province"></span></div>
<div>headimgurl: <img src="" id="headimgurl"></div>

<script type="text/javascript">
    $.ajax({
        type: "get",
        async: true,
        url: "/userinfo",
        dataType: "json",
        success: function (data) {
            console.log(data);

            if (data.errcode) {
                $("#exp").html("<a href='/index?a=1'>登录已过期，请重新授权</a>");
            } else {
                $("#openid").html(data.data.openid);
                $("#nickname").html(data.data.nickname);
                $("#province").html(data.data.province);
                $("#headimgurl").attr("src", data.data.headimgurl);
            }
        }
    });
</script>
</body>
</html>