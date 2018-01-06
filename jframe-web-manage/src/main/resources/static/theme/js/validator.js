/**
* js 验证器
* create by rick
* version v1.5
*/
/*
 * common regExp
 * 手机号码：/^1[3|4|5|7|8][0-9]\d{8}$/
 * 身份证号码：/^(\d{15}$|^\d{18}$|^\d{17}(\d|X|x))$/
 * 小数（保留两位）：/^(\d+)(\.?)(\d{0,2})$/
 * 正整数：/^(0|[1-9][0-9]*)$/
 * 仅英文或数字：/^[A-Za-z0-9]+$/
 * 网址：/^(http(s)?:\/\/)?(www\.)?[\w-]+\.\w{2,4}(\/)?$/
 * 
 */
					/*
					callback: {
                        message: 'message',
                        callback: function(value, validator) {
                        	if((/[^A-Za-z0-9]/.test(value))){return false;}
                            return true;
                        }
                    }
                    */

$(function(){
	$('#loginForm').bootstrapValidator({
        fields: {
        	username:{
                validators: {
                    notEmpty: {
                        message: '账号不能为空'
                    }
                }
            },
            password:{
                validators: {
                    notEmpty: {
                        message: '密码不能为空'
                    }
                }
            },
            validNum:{
                validators: {
                    notEmpty: {
                        message: '验证码不能为空'
                    }
                }
            }
        }
    }).on('success.form.bv', function(e) {
		e.preventDefault();
		var $form = $(e.target);
		Ajax.ajax({
			url: 'admin/dologin.do',
			params: $form.serialize(),
			success: function(data) {
				if(data.code==0){
					layer.msg(data.msg,{time:1000,icon:1,offset:0},function(){jumpUrl("/admin/index.do");});
				}else{
					layer.msg(data.msg,{icon:2,offset:0});
					$("#validImg").attr("src","getValidCode.do?"+Math.floor(Math.random() * 10000));
				}
			}
		});
	});
	
	$("#changePwdForm").bootstrapValidator({
        fields: {
        	oldPwd:{
                validators: {
                    notEmpty: {
                        message: '旧密码不能为空'
                    }
                }
            },
            newPass:{
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
            newPass2:{
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
    }).on('success.form.bv', function(e) {
		e.preventDefault();
		var $form = $(e.target);
		Ajax.ajax({
			url: 'admin/changePwd.do',
			params: $form.serialize(),
			success: function(data) {
				if(data.code==0){
					showMsg(data.msg, 1, function(){jumpUrl("/admin/logout.do")});
				}else{
					showMsg(data.msg, 2);
				}
			}
		});
	});
	
});