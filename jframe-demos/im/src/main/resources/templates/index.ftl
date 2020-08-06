<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8"/>
    <meta name="viewport" content="width=device-width,user-scalable=no,shrink-to-fit=no,initial-scale=1,maximum-scale=1,minimum-scale=1"/>
    <link rel="icon" href="/static/favicon.ico" type="image/x-icon">
    <title>Index</title>
</head>
<body>

<form action="/im/chat" method="get">
    <input type="text" name="loginId" value="" placeholder="登陆ID(必填)"> <br/>
    <input type="text" name="otherId" value="" placeholder="对方ID(选填)"> <br/>
    <button type="submit">登陆到聊天页</button>

    <p>在线用户
    <ul>
        <#list onlines as u>
            <li>${u.name}</li>
        </#list>
    </ul>
    </p>
</form>

</body>
</html>