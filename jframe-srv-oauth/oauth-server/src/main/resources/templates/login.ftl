<!DOCTYPE html>
<html>

<head>
    <meta charset="utf-8">
    <title>OAuth2.0统一登录</title>
    <link rel="stylesheet" href="/static/css/style.css"/>
</head>
<body>
<div class="wrap login_wrap">
    <div class="content">
        <div class="logo"></div>
        <div class="login_box">

            <div class="login_form">
                <div class="login_title">
                    OAuth2.0统一登录
                </div>
                <form action="/authentication/form" method="post" onsubmit="return checkForm()">
                    <div class="form_text_ipt">
                        <input name="username" type="text" placeholder="用户名" value="">
                    </div>

                    <div class="form_text_ipt">
                        <input name="password" type="password" placeholder="密码" value="">
                    </div>

                    <div class="form_text_ipt">
                        <input name="verify" type="text" placeholder="验证码" class="valid">
                        <img src="/valid" onclick="changeNum(this)" style="margin-top: 2px;">
                    </div>

                    <#--<div class="form_check_ipt">
                        <label><input name="remember-me" type="checkbox"> 下次自动登录</label>
                    </div>-->

                    <div class="form_btn">
                        <button type="submit">登录</button>
                        <div><font color="red">${msg!''}</font></div>
                    </div>
                    <#--<div class="form_reg_btn">
                        <span>还没有帐号？</span><a href="/">马上注册</a>
                        <a class="forget" href="/">忘记密码?</a>
                    </div>-->
                </form>
            </div>
        </div>
    </div>
</div>


<script src="/static/js/jquery.min.js" type="application/javascript"></script>
<script type="application/javascript">
    function checkForm() {
        var username = $("input[name='username']").val();
        var password = $("input[name='password']").val();
        var verify = $("input[name='verify']").val();
        if (username == "") {
            alert("用户名不能为空");
            return false;
        }
        if (password == "") {
            alert("密码不能为空");
            return false;
        }
        if (verify == "") {
            alert("验证码不能为空");
            return false;
        }
        return true;
    }

    function changeNum(obj) {
        var num = Math.floor(Math.random() * 10000);
        obj.src = "/valid?" + num;
    }
</script>
</body>
</html>
