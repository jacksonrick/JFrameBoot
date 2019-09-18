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
                <div class="row">
                    <div class="col-md-7 col-sm-7 col-xs-7">
                        <div class="input-group">
                            <span class="input-group-addon"><i class="fa fa-bars"></i></span>
                            <input type="text" class="form-control" id="validNum" name="validNum" placeholder="验证码" value="">
                        </div>
                    </div>
                    <div class="col-md-5 col-sm-5 col-xs-5">
                        <img src="/getValidCode" onclick="changeNum(this)" id="validImg">
                    </div>
                </div>
            </div>
            <button type="submit" class="btn btn-danger block full-width">登 录</button>
            <h5><a href="/">网站首页</a></h5>
        </form>
    </div>
</div>

<script>
    $(function () {
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
                },
                validNum: {
                    validators: {
                        notEmpty: {
                            message: '验证码不能为空'
                        }
                    }
                }
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
                        $("#validImg").attr("src", "/getValidCode?" + Math.floor(Math.random() * 10000));
                    }
                }
            });
        });
    });
</script>
</body>
</html>