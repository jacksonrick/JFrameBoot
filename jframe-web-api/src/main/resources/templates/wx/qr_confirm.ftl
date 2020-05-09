<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8"/>
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Title</title>
    <style type="text/css">
        a {
            display: block;
            height: 50px;
            line-height: 50px;
            text-align: center;
            font-size: 16px;
            margin-top: 30px;
            background-color: blueviolet;
            color: #FFFFFF;
            text-underline: none;
        }
    </style>
</head>
<body>

<h3>您正在进行<b>${type}</b>操作</h3>

<a href="/qrconfirm?code=${code}&sessionid=${sessionid}&confirm=1">确认</a>
<a href="/qrconfirm?sessionid=${sessionid}&confirm=0">取消</a>

<p>${msg}</p>

</body>
</html>