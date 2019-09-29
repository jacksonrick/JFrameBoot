<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <title>仿支付宝6位支付密码</title>
    <style>
        * {
            padding: 0px;
            margin: 0px;
        }

        body {
            padding: 50px;
        }

        .passwd {
            position: relative;
            height: 32px;
            margin: 20px;
        }

        .passwd-num {
            position: absolute;
            top: 0px;
            left: 0px;
            width: 30px;
            height: 30px;
            border: solid 1px #ECECEC;
            font-size: 22px;
            vertical-align: middle;
            text-align: center;
            cursor: text;
            outline: none;
        }

        .pass1 {
            left: 0px;
            border-right: none;
        }

        .pass2 {
            left: 31px;
            border-right: none;
        }

        .pass3 {
            left: 61px;
            border-right: none;
        }

        .pass4 {
            left: 91px;
            border-right: none;
        }

        .pass5 {
            left: 121px;
            border-right: none;
        }

        .pass6 {
            left: 151px;
        }

        .passwd-num.active {
            -webkit-box-shadow: inset 0 0px 1px rgba(0, 0, 0, 0.08), 0 0 6px rgba(55, 138, 204, 0.8);
            box-shadow: inset 0 0px 1px rgba(0, 0, 0, 0.08), 0 0 6px rgba(55, 138, 204, 0.8);
            z-index: 999;
            border: solid 1px #66afe9;
        }
    </style>
</head>

<body>
<h3 style="margin: 10px;">请输入6位数支付密码</h3>
<div class="passwd">
    <input type="text" name="pwd" value="" class="passwd-num pass1" data-pw="">
    <input type="text" name="pwd" value="" class="passwd-num pass2" data-pw="">
    <input type="text" name="pwd" value="" class="passwd-num pass3" data-pw="">
    <input type="text" name="pwd" value="" class="passwd-num pass4" data-pw="">
    <input type="text" name="pwd" value="" class="passwd-num pass5" data-pw="">
    <input type="text" name="pwd" value="" class="passwd-num pass6" data-pw="">
</div>
<button type="button" id="btn">提交</button>

<script src="/static/library/jquery/jquery-1.11.2.min.js"></script>
<script>
    $(function () {

        var dom = $(".passwd-num");
        var num = 6;
        $(".passwd-num").focus(function () {
            $(this).val("");
            $(this).attr("data-pw", "");
            $(this).addClass("active");
        });
        $(".passwd-num").blur(function () {
            $(this).removeClass("active");
        });
        $(".passwd-num").keyup(function (e) {
            var code = e.keyCode;
            if ((code >= 48 && code <= 57) || (code >= 96 && code <= 105)) {
                var index = $(this).index();
                var val = $(this).val();
                if (val.length == 0) {
                    return;
                }
                if (val.length > 1) {
                    val = val.substr(0, 1);
                }
                $(this).attr("data-pw", val);
                $(this).val("●");
                dom.eq(index + 1).focus();
                dom.eq(index + 1).addClass("active");
                if (index < num) {
                    index++;
                }
                if (index == num) {
                    $(this).removeClass("active");
                    $(this).blur();
                }
            } else {
                $(this).val("");
            }
        });

        $("#btn").click(function () {
            var pwd = "";
            for (var i = 1; i <= num; i++) {
                pwd += $(".pass" + i).attr("data-pw");
            }
            alert("password:" + pwd + ",length:" + pwd.length);
            if (pwd.length != num) {
                alert("请输入密码！");
            }
        });

    });
</script>
</body>
</html>