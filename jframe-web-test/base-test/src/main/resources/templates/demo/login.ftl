<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <title>登录</title>
</head>

<body>
<h1>登录</h1>
<form name="form" action="/login" method="post">
    <input type="text" name="username" value="cloud">
    <input type="text" name="password" value="cloud1234">
    <input type="submit" value="Login">
    <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}">
</form>
</body>
</html>