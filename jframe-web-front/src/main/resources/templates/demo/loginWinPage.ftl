<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <title>登录</title>
<#include "demo/include.ftl"/>
    <link href="/static/common/css/bootstrap.min.css" type="text/css" rel="stylesheet">
</head>

<body>

<div class="container login">
    <form class="form-horizontal" method="post" id="login-form-win">
        <div class="form-group">
            <label class="col-sm-3 control-label">登录账号</label>
            <div class="col-sm-7">
                <input type="text" class="form-control" name="account" placeholder="邮箱、手机号、用户名" autofocus="autofocus">
            </div>
        </div>
        <div class="form-group">
            <label class="col-sm-3 control-label">密码</label>
            <div class="col-sm-7">
                <input type="password" class="form-control" name="passwd" placeholder="输入密码">
            </div>
        </div>
        <div class="form-group">
            <div class="col-sm-offset-3 col-sm-6">
                <button type="button" class="btn btn-info btn-w" id="sub">登录</button>
            </div>
        </div>
    </form>
</div>

<script type="text/javascript" src="/static/library/jquery/jquery.form.js"></script>
<script>
    $(function () {
        $("#sub").click(function () {
            Ajax.ajax({
                url: "/login",
                params: $("#login-form-win").serialize(),
                success: function (data) {
                    alert(data.msg);
                }
            })
        });
    });
</script>
</body>
</html>