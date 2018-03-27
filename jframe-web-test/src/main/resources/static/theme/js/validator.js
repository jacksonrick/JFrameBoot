/**
 * js 验证器 create by rick version v1.5
 */

/*
 * common regExp
 * 手机号码：/^1[3|4|5|7|8][0-9]\d{8}$/
 * 身份证号码：/^(\d{15}$|^\d{18}$|^\d{17}(\d|X|x))$/
 * 小数（保留两位）：/^(\d+)(\.?)(\d{0,2})$/
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

$(function() {

	$('#login-form').bootstrapValidator({
		fields : {
			account : {
				validators : {
					notEmpty : {
						message : '不能为空'
					},
					regexp: {
                        regexp: /^[A-Za-z0-9]+$/,
                        message: '仅英文数字'
                    }
				}
			},
			passwd : {
				validators : {
					notEmpty : {
						message : '不能为空'
					}
				}
			}
		}
	}).on('success.form.bv', function(e) {
		
		//var a=$("input[name='images']").val();
		//if(a==""){alert("images不能为空");return false;}
		
		e.preventDefault();
		$(this).ajaxSubmit({
			dataType : "json",
			clearForm : true,
			success : function(data) {
				if (data.code == 1) {
					alert(1);
				}
			},
			error : function() {
				alert(-1);
			}
		});
		return false;
	});
	
	$('#login-form-win').bootstrapValidator({
		fields : {
			account : {
				validators : {
					notEmpty : {
						message : '不能为空'
					}
				}
			},
			passwd : {
				validators : {
					notEmpty : {
						message : '不能为空'
					}
				}
			}
		}
	}).on('success.form.bv', function(e) {
		e.preventDefault();
		$(this).ajaxSubmit({
			dataType : "json",
			clearForm : true,
			success : function(data) {
				if (data.code == 1) {
					parent.location.reload();
				}
			},
			error : function() {
				alert(-1);
			}
		});
		return false;
	});

});