<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <title></title>
    <link type="text/css" href="/static/library/plugins/dropzone/dropzone.css" rel="stylesheet"/>
<#include "include.ftl"/>
</head>

<body>
<div class="ibox-content">
    <form class="form-horizontal" id="userEditForm">
        <div class="form-group">
            <label class="col-sm-2 control-label">昵称：</label>
            <div class="col-sm-9">
                <input type="text" name="nickname" value="${user.nickname }" class="form-control" placeholder="昵称">
            </div>
        </div>
        <div class="form-group">
            <label class="col-sm-2 control-label">头像：</label>
            <div class="col-sm-9">
                <a class="img-area-btn" id="avatar"></a>
            </div>
        </div>
        <div class="form-group">
            <label class="col-sm-2 control-label">邮箱：</label>
            <div class="col-sm-9">
                <input type="text" name="email" value="${user.email }" class="form-control" placeholder="邮箱">
            </div>
        </div>
        <div class="form-group">
            <label class="col-sm-2 control-label">手机号：</label>
            <div class="col-sm-9">
                <input type="text" name="phone" value="${user.phone }" class="form-control" placeholder="手机号">
            </div>
        </div>
        <div class="form-group">
            <label class="col-sm-2 control-label">登录密码：</label>
            <div class="col-sm-9">
                <input type="text" name="password" class="form-control" placeholder="登录密码">
                <span class="help-block">留空表示不修改密码</span>
            </div>
        </div>
        <div class="form-group">
            <label class="col-sm-2 control-label">真实姓名：</label>
            <div class="col-sm-9">
                <input type="text" name="realname" value="${user.realname }" class="form-control" placeholder="真实姓名">
            </div>
        </div>
        <div class="form-group">
            <label class="col-sm-2 control-label">身份证号：</label>
            <div class="col-sm-9">
                <input type="text" name="idcard" value="${user.idcard }" class="form-control" placeholder="身份证号">
            </div>
        </div>
        <div class="form-group">
            <label class="col-sm-2 control-label">账号：</label>
            <div class="col-sm-9">
                <div class="radio radio-info radio-inline">
                    <input type="radio" name="isDelete" id="state1" value="0" ${(user.isDelete)!false?string('','checked') }>
                    <label for="state1">正常</label>
                </div>
                <div class="radio radio-info radio-inline">
                    <input type="radio" name="isDelete" id="state2" value="1" ${(user.isDelete)!false?string('checked','') }>
                    <label for="state2">禁用</label>
                </div>
            </div>
        </div>
        <div class="form-group">
            <label class="col-sm-2 control-label">性别：</label>
            <div class="col-sm-9">
                <div class="radio radio-info radio-inline">
                    <input type="radio" name="gender" id="gender1" value="1" ${(user.gender)!true?string('checked','') }>
                    <label for="gender1">男</label>
                </div>
                <div class="radio radio-info radio-inline">
                    <input type="radio" name="gender" id="gender2" value="0" ${(user.gender)!true?string('','checked') }>
                    <label for="gender2">女</label>
                </div>
            </div>
        </div>
        <div class="form-group">
            <label class="col-sm-2 control-label">住址：</label>
            <div class="col-sm-9">
                <input type="text" id="city-picker" name="address" value="${user.address }" class="form-control" placeholder="住址">
            </div>
        </div>
        <div class="form-group">
            <label class="col-sm-2 control-label">生日：</label>
            <div class="col-sm-9">
                <input type="text" id="birthday" name="birthday" value="${user.birthday }" class="form-control" placeholder="生日">
            </div>
        </div>
        <div class="form-group">
            <label class="col-sm-2 control-label">其他信息：</label>
            <div class="col-sm-9">
                <p class="form-control-static">注册时间：${(user.createTime?string('yyyy-MM-dd HH:mm:ss'))!'--' }</p>
                <p class="form-control-static">最近登陆时间：${(user.lastLoginTime?string('yyyy-MM-dd HH:mm:ss'))!'--' }</p>
            </div>
        </div>
        <div class="form-group">
            <div class="col-sm-offset-2 col-sm-3">
                <input type="hidden" name="id" value="${user.id }">
                <button type="submit" class="btn btn-info btn-block">保存</button>
            </div>
        </div>
    </form>
</div>

<script type="text/javascript" src="/static/library/plugins/dropzone/dropzone.js"></script>
<script type="text/javascript">
    $(function () {
        datePicker("#birthday", "yyyy-mm-dd", function () {
            $('#userEditForm').bootstrapValidator('revalidateField', 'birthday');
        });
        cityPicker("#city-picker", "district", "code", function () {
            $('#userEditForm').bootstrapValidator('revalidateField', 'address');
        });

        $("#avatar").Uploader({
            limit: 1,
            default: '${user.avatar}',
            type: 1,
            callback: function (o, data) {
                $('#userEditForm').bootstrapValidator('revalidateField', 'avatar');
            }
        });

        $("#userEditForm").bootstrapValidator({
            excluded: [],
            fields: {
                nickname: {
                    validators: {
                        notEmpty: {
                            message: '用户名不能为空'
                        },
                        stringLength: {
                            min: 2,
                            max: 16,
                            message: '用户名为2-16个字符'
                        }
                    }
                },
                email: {
                    validators: {
                        stringLength: {
                            max: 32,
                            message: '邮箱最多32个字符'
                        },
                        emailAddress: {
                            message: '请输入真实有效的邮箱地址'
                        }
                    }
                },
                phone: {
                    validators: {
                        regexp: {
                            regexp: /^1[3|4|5|7|8][0-9]\d{8}$/,
                            message: '手机号码格式不正确'
                        }
                    }
                },
                password: {
                    validators: {
                        stringLength: {
                            min: 6,
                            max: 16,
                            message: '密码长度为6-16个字符'
                        }
                    }
                },
                realname: {
                    validators: {
                        stringLength: {
                            max: 16,
                            message: '真实姓名最多16个字符'
                        }
                    }
                },
                idcard: {
                    validators: {
                        regexp: {
                            regexp: /^(\d{15}$|^\d{18}$|^\d{17}(\d|X|x))$/,
                            message: '身份证号格式不正确'
                        }
                    }
                },
                birthday: {
                    validators: {
                        notEmpty: {
                            message: '生日不能为空'
                        }
                    }
                },
                address: {
                    validators: {
                        notEmpty: {
                            message: '地址不能为空'
                        }
                    }
                },
                avatar: {
                    validators: {
                        notEmpty: {
                            message: '请上传头像'
                        }
                    }
                }
            }
        }).on('success.form.bv', function (e) {
            e.preventDefault();
            var $form = $(e.target);
            Ajax.ajax({
                url: '/admin/user/userEdit',
                params: $form.serialize(),
                success: function (data) {
                    ajaxBack(data);
                }
            });
        });
    });
</script>
</body>
</html>