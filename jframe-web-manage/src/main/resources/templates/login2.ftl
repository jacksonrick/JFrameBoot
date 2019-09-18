<!DOCTYPE html>
<html>
<head lang="en">
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>登录</title>
    <script type="text/javascript">if (window != top) {parent.location.reload();}</script>
    <#include "include.ftl"/>
</head>

<body class="gray-bg" style="background-image: url('/static/theme/images/login/2.jpg'); background-size: cover;">

<div class="middle-box text-center">
    <div>
        <h1 class="logo-name"> 平台管理中心 </h1>
        <h5>version：${version}</h5>

        <form style="opacity: .8;" role="form" method="post" id="loginForm">
            <div class="form-group">
                <div class="input-group">
                    <span class="input-group-addon"><i class="fa fa-user"></i></span>
                    <input type="text" class="form-control" placeholder="用户名" name="username" value="admin">
                </div>
            </div>
            <div class="form-group">
                <div class="input-group">
                    <span class="input-group-addon"><i class="fa fa-key"></i></span>
                    <input type="password" class="form-control" placeholder="密码" name="password" value="">
                </div>
            </div>
            <div class="form-group">
                <div id="captcha"><p id="loading">验证码加载中...</p></div>
            </div>
            <button type="submit" id="submit" class="btn btn-danger block full-width">登 录</button>
            <h5><a href="/">网站首页</a></h5>
        </form>
    </div>
</div>

<script src="/static/common/js/gt.js"></script>
<script>
    $(function () {
        var captcha;
        var handler = function (captchaObj) {
            captcha = captchaObj;
            $("#submit").click(function (e) {
                var result = captchaObj.getValidate();
                if (!result) {
                    layer.msg("请先完成验证", {time: 1000, icon: 2, offset: 0});
                    e.preventDefault();
                }
            });
            $("#loading").hide();
            captchaObj.appendTo("#captcha");
        };
        $.ajax({
            url: "/startCaptcha?t=" + (new Date()).getTime(),
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

        $('#loginForm').bootstrapValidator({
            fields: {
                username: {
                    validators: {
                        notEmpty: {
                            message: '账号不能为空'
                        }
                    }
                },
                password: {
                    validators: {
                        notEmpty: {
                            message: '密码不能为空'
                        }
                    }
                }
                /*,
                validNum: {
                    validators: {
                        notEmpty: {
                            message: '验证码不能为空'
                        }
                    }
                }*/
            }
        }).on('success.form.bv', function (e) {
            e.preventDefault();
            var $form = $(e.target);
            Ajax.ajax({
                url: '/admin/dologin',
                params: $form.serialize(),
                success: function (data) {
                    if (data.code == 0) {
                        layer.msg(data.msg, {time: 1000, icon: 1, offset: 0}, function () {
                            jumpUrl("/admin/index");
                        });
                    } else {
                        layer.msg(data.msg, {icon: 2, offset: 0});
                        captcha.reset();
                    }
                }
            });
        });
    });
</script>
</body>
</html>