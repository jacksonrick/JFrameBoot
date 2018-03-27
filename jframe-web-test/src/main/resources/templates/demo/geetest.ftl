<!DOCTYPE html>
<html>
<head lang="en">
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>极验--登录</title>
</head>

<body>
<form id="shopLoginForm">
    <div id="captcha"></div>
    <button type="submit" id="submit" class="btn btn-danger">登 录</button>
</form>

<script src="/static/common/js/gt.js"></script>
<script>
    $(function () {
        var handler = function (captchaObj) {
            $("#submit").click(function (e) {
                var result = captchaObj.getValidate();
                console.log(result)
                if (!result) {
                    layer.msg("请先完成验证", {time: 1000, icon: 2, offset: 0});
                    e.preventDefault();
                }
            });
            captchaObj.appendTo("#captcha");
        };
        $.ajax({
            url: "/startCaptcha.do?t=" + (new Date()).getTime(),
            type: "get",
            dataType: "json",
            success: function (data) {
                initGeetest({
                    gt: data.gt,
                    challenge: data.challenge,
                    new_captcha: data.new_captcha,
                    offline: !data.success,
                    product: "float",
                    width: "100%"
                }, handler);
            }
        });
    })
</script>
</body>
</html>