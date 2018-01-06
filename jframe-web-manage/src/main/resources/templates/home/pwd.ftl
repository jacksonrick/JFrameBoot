<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <title></title>
    <#include "include.ftl"/>
</head>

<body>
<div class="ibox-content">
    <form class="form-horizontal" id="changePwdForm">
        <div class="form-group">
            <label class="col-sm-3 control-label">原密码：</label>
            <div class="col-sm-9">
                <input type="password" name="oldPwd" class="form-control" placeholder="请输入原密码">
            </div>
        </div>
        <div class="form-group">
            <label class="col-sm-3 control-label">新密码：</label>
            <div class="col-sm-9">
                <input type="password" name="newPass" class="form-control" placeholder="请输入新密码">
            </div>
        </div>
        <div class="form-group">
            <label class="col-sm-3 control-label">重复密码：</label>
            <div class="col-sm-9">
                <input type="password" name="newPass2" class="form-control" placeholder="请再输入一次密码">
            </div>
        </div>
        <div class="form-group">
            <div class="col-sm-offset-3 col-sm-3">
                <button type="submit" class="btn btn-warning btn-block">确定</button>
            </div>
        </div>
    </form>
</div>

<script>
    $(function () {
        $("#changePwdForm").bootstrapValidator({
            fields: {
                oldPwd: {
                    validators: {
                        notEmpty: {
                            message: '旧密码不能为空'
                        }
                    }
                },
                newPass: {
                    validators: {
                        notEmpty: {
                            message: '新密码不能为空'
                        },
                        stringLength: {
                            min: 6,
                            max: 16,
                            message: '6~16个字符'
                        }
                    }
                },
                newPass2: {
                    validators: {
                        notEmpty: {
                            message: '重复密码不能为空'
                        },
                        identical: {
                            field: 'newPass',
                            message: '两次输入的密码不一致'
                        }
                    }
                }
            }
        }).on('success.form.bv', function (e) {
            e.preventDefault();
            var $form = $(e.target);
            Ajax.ajax({
                url: '/admin/changePwd',
                params: $form.serialize(),
                success: function (data) {
                    if (data.code == 0) {
                        showMsg(data.msg, 1, function () {
                            jumpUrl("/admin/logout")
                        });
                    } else {
                        showMsg(data.msg, 2);
                    }
                }
            });
        });
    });
</script>
</body>
</html>