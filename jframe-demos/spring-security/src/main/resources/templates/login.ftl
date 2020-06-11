<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8"/>
    <title>登陆</title>
</head>
<body>
<form action="/dologin" method="post">
    <input type="text" name="username">
    <input type="password" name="password">

    <br>
    <label><input name="remember-me" type="checkbox" value="true">下次自动登录</label>
    <br>
    <button type="submit">登陆</button>
    <br>
    <b style="color: red">${msg!''}</b>
</form>
</body>
</html>