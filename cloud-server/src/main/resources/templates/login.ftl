<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <title>Spring Cloud</title>
    <link rel="stylesheet" type="text/css" href="/static/css/bootstrap.min.css"/>
    <style type="text/css">
        .form-login {
            max-width: 300px;
            padding: 19px 29px 29px;
            margin: 0 auto 20px;
            background-color: #fff;
            border: 1px solid #e5e5e5;
            -webkit-border-radius: 5px;
            -moz-border-radius: 5px;
            border-radius: 5px;
            -webkit-box-shadow: 0 1px 2px rgba(0, 0, 0, .05);
            -moz-box-shadow: 0 1px 2px rgba(0, 0, 0, .05);
            box-shadow: 0 1px 2px rgba(0, 0, 0, .05);
        }

        .form-login .form-login-heading,
        .form-login .checkbox {
            margin-bottom: 10px;
        }

        .form-login input[type="text"],
        .form-login input[type="password"] {
            height: auto;
            margin-bottom: 15px;
            padding: 7px 9px;
        }

        .error-message {
            display: inherit;
            color: red;
            font-size: 12px;
            margin-bottom: 5px;
        }

    </style>
</head>

<body>
<div class="container">
    <#include "header.ftl">

    <form class="form-login" action="login" method="post">
        <h2 class="form-login-heading">Login</h2>
        <div id="login-controls" class="control-group">
            <input type="text" class="input-block-level" name="username" placeholder="Username">
            <input type="password" class="input-block-level" name="password" placeholder="Password">
            <#if msg??>
                <span class="help-inline error-message">${msg}</span>
            </#if>
        </div>
        <input type="submit" class="btn btn-success" value="Login"></input>
    </form>

    <#include "footer.ftl">
</div>

</body>
</html>