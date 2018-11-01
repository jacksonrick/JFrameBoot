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
                <form action="/authentication/form" method="post">
                    <div class="form_text_ipt">
                        <input name="username" type="text" placeholder="手机号" value="17730215423">
                    </div>

                    <div class="form_text_ipt">
                        <input name="password" type="password" placeholder="密码" value="">
                    </div>

                    <div class="form_text_ipt">
                        <input name="verify" type="password" placeholder="验证码" value="">
                    </div>

                    <div class="form_check_ipt">
                        <label><input name="remember-me" type="checkbox"> 下次自动登录</label>
                    </div>

                    <div class="form_btn">
                        <button type="submit">登录</button>
                        <div><font color="red">${msg!''}</font></div>
                    </div>
                    <div class="form_reg_btn">
                        <span>还没有帐号？</span><a href="/">马上注册</a>
                        <a class="forget" href="/">忘记密码?</a>
                    </div>
                </form>
            </div>
        </div>
    </div>
</div>
</body>
</html>
