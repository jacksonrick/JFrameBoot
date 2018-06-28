/********************
 * @Author jfxu
 * <p> need:jquery.js </p>
 * <p> plugins:layer.js/dropzone.js/toastr.js </p>
 ********************/

/*******************************************************************************
 * Ajax (JSON)
 * Ajax.ajax({ 
 * 	url:'',
 * 	params:JSON.stringify($('#defaultForm').serializeObject()),
 * 	success:function(data){ } 
 * })
 ******************************************************************************/
Ajax = {
    ajax: function (options) {
        var defaults = {
            async: true,
            dataType: 'json',
            url: null,
            params: null,
            success: null,
            error: null
        };
        var config = $.extend(defaults, options);
        if (config.url) {
            var index = openLayerLoading();
            $.ajax({
                type: "POST",
                async: config.async,
                // contentType : 'application/json',
                url: config.url,
                data: config.params,
                dataType: config.dataType,
                success: function (data) {
                    layer.close(index);
                    if (data.code == 99) { // nologin
                        showMsg(data.msg, 2, function () {
                            jumpUrl("admin/login");
                        });
                        return;
                    }
                    if (data.code == 100) { // noauthority
                        showMsg(data.msg, 2);
                        return;
                    }
                    if (config.success) {
                        config.success(data);
                    }
                },
                error: function (er) {
                    layer.close(index);
                    showMsg('Request Error', 2);
                }
            });
        } else {
            showMsg('Request Url Is Error', 2);
        }
    }
};
// json参数值：JSON.stringify($('#defaultForm').serializeObject())
$.fn.serializeObject = function () {
    var o = {};
    var a = this.serializeArray();
    $.each(a, function () {
        if (o[this.name]) {
            if (!o[this.name].push) {
                o[this.name] = [o[this.name]];
            }
            o[this.name].push(this.value || '');
        } else {
            o[this.name] = this.value || '';
        }
    });
    return o;
};

var parseParam = function (param) {
    var paramStr = "";
    $.each(param, function (k, v) {
        if (v == null) {
            v = "";
        }
        paramStr += k + "=" + encodeURIComponent(v) + "&";
    });
    return paramStr.substring(0, paramStr.length - 1);
};

/**
 * DataTables Configuration
 * Created by xujunfei on 2017/8/12.
 */
var CONSTANT = {
    DEFAULT_OPTION: { // DataTables初始化选项
        language: {
            "sProcessing": "处理中...",
            "sLengthMenu": "每页 _MENU_ 项",
            "sZeroRecords": "没有匹配结果",
            "sInfo": "当前显示第 _START_ 至 _END_ 项，共 _TOTAL_ 项。",
            "sInfoEmpty": "当前显示第 0 至 0 项，共 0 项",
            "sInfoFiltered": "(由 _MAX_ 项结果过滤)",
            "sInfoPostFix": "",
            "sSearch": "搜索:",
            "sUrl": "",
            "sEmptyTable": "表中数据为空",
            "sLoadingRecords": "载入中...",
            "sInfoThousands": ",",
            "oPaginate": {
                "sFirst": "首页",
                "sPrevious": "上一页",
                "sNext": "下一页",
                "sLast": "末页",
                "sJump": "跳转"
            },
            "oAria": {
                "sSortAscending": ": 以升序排列此列",
                "sSortDescending": ": 以降序排列此列"
            }
        },
        serverSide: true,// 分页，取数据等等的都放到服务端去
        processing: true,// 载入数据的时候是否显示“载入中”
        ordering: true, // 排序
        order: [],// 取消默认排序查询,否则第一列会出现小箭头
        pagingType: "full_numbers",// 分页样式
        autoWidth: false,// 自动列宽
        searching: false,// 禁用datatables搜索
        lengthChange: false,// 是否可以修改页面显示行数
        pageLength: 20,  // 首次加载的数据条数
        //stateSave: true,// 保持翻页状态，和comTable.fnDraw(false);结合使用
        //select: true // 可多选
    },
    AJAX: function (url, orders, data, callback, settings) {
        // 排序字段，与columns对应，index从0开始
        // orders为数组，格式[[index,name],...]
        var sort = CONSTANT.GETCOLUMN(data.order[0], orders);
        var param = {
            pageNo: data.start / 20 + 1,
            pageSort: sort
        };
        var formData = $("#queryForm").serializeArray();// 把form里面的数据序列化成数组
        formData.forEach(function (e) {
            param[e.name] = e.value;
        });
        $.ajax({
            type: "POST",
            url: url,
            data: param,    // 传入已封装的参数
            dataType: "json",
            success: function (result) {
                if (result.code && result.code != 0) {
                    alert(result.msg);
                    return;
                }
                // 返回JSON数据必须包含 total,list
                setTimeout(function () {
                    var returnData = {};
                    returnData.draw = data.draw;// 这里直接自行返回了draw计数器
                    returnData.recordsTotal = result.total;
                    returnData.recordsFiltered = result.total;// 后台不实现过滤功能，每次查询均视作全部结果
                    returnData.pageSize = result.pageSize;
                    returnData.data = result.list;
                    callback(returnData);
                }, 500);
            },
            error: function (XMLHttpRequest, textStatus, errorThrown) {
                alert("查询失败");
            }
        });
    },
    COLUMN: {
        CHECKALL: { // 复选框单元格-全选
            title: "<div class='checkbox checkbox-inline'><input type='checkbox' name='check-all'><label></label></div>",
            data: null,
            orderable: false,
            width: '25px'
        },
        SEQ: { // 自增序号
            title: "序号",
            data: null,
            orderable: false,
            width: '30px'
        }
    },
    BUTTON: {// 操作按钮
        CHECKBOXS: {
            targets: 0,
            defaultContent: "<div class='checkbox checkbox-inline'><input type='checkbox'><label></label></div>"
        },
        // 圆形按钮样式 btn btn-info btn-sm btn-circle btn-enable
        EDIT: function (url) {
            return "<a class='label label-info btn-edit' title='详情' data-open='modal' data-width='800px' data-height='500px' href='" + url + "'>详情</a>";
        },
        ENABLE: function () {
            return "<a class='label label-info btn-enable' title='启用'>启用</a>";
        },
        DISABLE: function () {
            return "<a class='label label-warning btn-enable' title='禁用'>禁用</a>";
        },
        DELETE: function () {
            return "<a class='label label-danger btn-del' title='删除'>删除</a>";
        }
    },
    RENDER: {// 转换器
        BASE: function (data, enu) { // 获取数据键值
            var res = "--";
            $.each(enu, function (k, v) {
                if (v[0] == data) {
                    res = v[1];
                    return false;
                }
            });
            return res;
        },
        AVATAR: function (data, type, full, callback) {
            if (data != null) {
                return "<img width='50px' height='50px' data-open='img' src='" + data + "'/>"
            }
            return "--"
        },
        BOOLEAN: function (data, type, full, callback) {
            if (data) {
                return "是"
            }
            return "否"
        },
        ENUM: {
            // enums变量为enums.js
            // 使用到常量，需引用 $.getScript("/static/common/js/enums.js");
            STATE: function (data) {
                return CONSTANT.RENDER.BASE(data, enums.STATE);
            },
            TYPE: function (data) {
                return CONSTANT.RENDER.BASE(data, enums.TYPE);
            }
        }
    },
    GETCOLUMN: function (order, array) { // 获取排序列名
        var sort = "";
        if (order && order.column >= 0) {
            $.each(array, function (k, v) {
                if (order.column == v[0]) {
                    sort = v[1] + " " + order.dir.toUpperCase();
                    return false;
                }
            });
        }
        return sort;
    }
};

/**
 * toast
 * @param type 1-success 2-error
 * @param title
 * @param content
 */
function toast(type, title, content) {
    toastr.options = {
        "closeButton": true,
        "debug": false,
        "progressBar": true,
        "positionClass": "toast-top-center",
        "showDuration": "100",
        "hideDuration": "1000",
        "timeOut": "4000",
        "extendedTimeOut": "1000"
    };

    if (type == 1) { // success
        toastr.success(title, content);
    } else if (type == 2) { // error
        toastr.error(title, content);
    }
}

/**
 * layer询问框
 * @param title
 * @param url
 * @param param {"key":"value"}
 */
function layerConfirm(title, url, func) {
    if (func == null) {
        func = function () {
            location.reload();
        }
    }
    layer.confirm(title, {btn: ['确定', '取消'], icon: 3}, function () {
        Ajax.ajax({
            url: url,
            params: null,
            success: function (data) {
                if (data.code == 0) {
                    showMsg(data.msg, 1, func);
                } else {
                    showMsg(data.msg, 2);
                }
            }
        });
    });
}

/**
 * 显示提示层
 * @param msg
 * @param type 1-success 2-fail
 * @param func 结束后回调
 */
function showMsg(msg, type, func) {
    if (func) {
        layer.msg(msg, {
            icon: type,
            time: 2000
        }, func);
    } else {
        layer.msg(msg, {
            icon: type,
            time: 2000
        });
    }
}

/**
 * 打开layer url
 * @returns index
 */
function openLayerUrl(title, width, height, url, maxmin) {
    var index = layer.open({
        type: 2,
        title: title,
        closeBtn: 1,
        shift: 5,
        shade: [0.5, '#393D49'],
        shadeClose: false,
        maxmin: maxmin,
        area: [width, height],
        content: url
    });
    return index;
}

/**
 * 打开HTML窗口【对象】
 * @param title
 * @param width
 * @param height
 * @param $obj 对象，如：$(".layer-edit")
 * @returns
 */
function openLayerContent(title, width, height, $obj) {
    var index = layer.open({
        type: 1,
        title: title,
        shade: [0.5, '#393D49'],
        area: [width, height],
        content: $obj,
        cancel: function (index) {
            layer.close(index);
        }
    });
    return index;
}

/**
 * 打开HTML窗口【HTML】
 * @param html HTML代码
 * @param width/height
 * @param func 成功
 */
function openLayerHTML(html, width, height, func) {
    var index = layer.open({
        type: 1,
        title: false,
        area: [width, height],
        closeBtn: 1, //1-有关闭按钮 0-无
        shadeClose: true, //是否有遮罩层 true/false
        content: html,
        success: func
    });
    return index;
}

/**
 * 加载动画
 * @returns index
 */
function openLayerLoading() {
    var index = layer.load(2, {
        shade: [0.1, '#000'],
        time: 20000
    });
    return index;
}

/**
 * ajax success
 * @returns index
 */
function ajaxBack(data) {
    var index = parent.layer.getFrameIndex(window.name);
    if (data.code == 0) {
        showMsg(data.msg, 1, function () {
            parent.layer.close(index);
            parent.reload();
        });
    } else {
        showMsg(data.msg, 2);
    }
    return index;
}

/**
 * ajax success close&reload
 * @returns index
 */
function ajaxBackCloseAndReload() {
    var index = parent.layer.getFrameIndex(window.name);
    parent.layer.close(index);
    parent.reload();
    return index;
}

/**
 * 重置表单
 */
function resetForm(id) {
    $(id)[0].reset();
    $(id).find("input[type=hidden]").val('');
}

/**
 * 校验字段长度
 * @param sel
 * @param min
 * @param max
 * @returns {Boolean} true-未通过验证
 */
function checkString(sel, min, max) {
    var obj = $(sel);
    if (typeof (obj) == "undefined") {
        return true;
    }
    if (obj.val().length < min || obj.val().length > max) {
        obj.focus();
        return true;
    }
    return false;
}

function checkPhone(sel) {
    return checkRE(sel, /^1[3|4|5|7|8][0-9]\d{8}$/);
}

function checkNumber(sel) {
    return checkRE(sel, /^(0|[1-9][0-9]*)$/);
}

function checkMoney(sel) {
    return checkRE(sel, /^(\d+)(\.?)(\d{0,2})$/);
}

function checkUrl(sel) {
    return checkRE(sel, /[a-zA-z]+:\/\/[^\s]*/);
}

/**
 * 正则校验
 * @param selector 选择器
 * @param reExp 正则表达式
 *        num:/^[1-9]\d*$/
 *        email:/^([a-zA-Z0-9_\.\-])+\@(([a-zA-Z0-9\-])+\.)+([a-zA-Z0-9]{2,4})+$/
 *        money:/^(\d+)(\.?)(\d{0,2})$/
 *        phone:/^1[3|4|5|7|8][0-9]\d{8}$/
 *        idcard:/^(\d{15}$|^\d{18}$|^\d{17}(\d|X|x))$/
 * @returns {Boolean} true-未通过
 */
function checkRE(sel, reExp) {
    var obj = $(sel);
    if (!reExp.test(obj.val())) {
        obj.focus();
        return true;
    }
    return false;
}


/**
 * 地址联动选择器 cityPicker("#city-picker", "district", "code");
 * @param id
 * @param level city|district|street
 * @param type address|code
 * @param func onClose callback
 */
function cityPicker(id, level, type, func) {
    $.ajaxSetup({cache: true});
    addCSS("/static/library/plugins/city-picker/city-picker.css");
    // 如仅省市区，则引用city-picker.data.js
    $.getScript("/static/library/plugins/city-picker/city-picker.data.all.js", function () {
        $.getScript("/static/library/plugins/city-picker/city-picker.js", function () {
            var $citypicker = $(id);
            $citypicker.citypicker({
                func: func,
                level: level,
                type: type
            });
        });
    });
}

//view    4       3       2           1
//format  yyyy    yyyy-mm yyyy-mm-dd  yyyy-mm-dd hh:ii

/**
 * 时间选择器 datePicker("#datetimepicker", "yyyy-mm-dd")
 * @param id
 * @param format
 * @param hide onHide callback
 */
function datePicker(ids, format, hide) {
    $.ajaxSetup({cache: true});
    $.getScript("/static/library/bootstrap/bootstrap-datetimepicker.min.js",
        function () {
            var view = 2; // default `yyyy-mm-dd`
            if (format == "yyyy") {
                view = 4;
            } else if (format == "yyyy-mm") {
                view = 3;
            } else if (format == "yyyy-mm-dd") {
                view = 2;
            } else if (format == "yyyy-mm-dd hh:ii") {
                view = 1;
            }
            $(ids).datetimepicker({
                language: 'zh-CN',
                minView: view,
                startView: view,
                format: format,
                todayBtn: true,
                autoclose: true
            }).on('hide', function (e) {
                e.preventDefault();
                e.stopPropagation();
                if (ids.indexOf("startDate") > 0 && ids.indexOf("endDate") > 0) {
                    // var time = e.date; // 会产生时区问题
                    var id = e.currentTarget.id;
                    if ("startDate" == id) {
                        $("#endDate").datetimepicker("setStartDate", $("#startDate").val());
                    } else if ("endDate" == id) {
                        $("#startDate").datetimepicker("setEndDate", $("#endDate").val());
                    }
                }
                if (hide) hide();
            });
        }
    );
}

/**
 * 加载css文件
 * @param file css文件路径
 */
function addCSS(file) {
    var link = document.createElement('link');
    link.type = 'text/css';
    link.rel = 'stylesheet';
    link.href = file;
    document.getElementsByTagName("head")[0].appendChild(link);
}

/**
 * URL跳转
 * @param url
 */
function jumpUrl(url) {
    var referLink = document.createElement('a');
    referLink.href = url;
    document.body.appendChild(referLink);
    referLink.click();
}

/**
 * 更改验证码图片
 * @param obj this
 */
function changeNum(obj) {
    var num = Math.floor(Math.random() * 10000);
    obj.src = "/getValidCode?" + num;
}

/**
 * post提交
 * @param url request
 * @param params {name:value,name:value}
 * @returns anonymous
 */
function post(url, params) {
    var temp = document.createElement("form");
    temp.action = url;
    temp.method = "post";
    temp.style.display = "none";
    for (var x in params) {
        var opt = document.createElement("textarea");
        opt.name = x;
        opt.value = params[x];
        temp.appendChild(opt);
    }
    document.body.appendChild(temp);
    temp.submit();
    return temp;
}

/**
 * 获取url参数键值
 * @param name
 * @returns
 */
function getQueryString(name) {
    var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)");
    var r = window.location.search.substr(1).match(reg);
    if (r != null)
        return unescape(r[2]);
    return null;
}

/**
 * 获取浏览器信息
 * <p>var browser = getBrowserInfo() ;
 * var verinfo = (browser+"").replace(/[^0-9.]/ig,"");
 * $("#bwType").text(browser);
 * $("#bwVer").text(verinfo);</p>
 */
function getBrowserInfo() {
    var agent = navigator.userAgent.toLowerCase();
    var regStr_ie = /msie [\d.]+;/gi;
    var regStr_ff = /firefox\/[\d.]+/gi;
    var regStr_chrome = /chrome\/[\d.]+/gi;
    var regStr_saf = /safari\/[\d.]+/gi;
    // IE
    if (agent.indexOf("msie") > 0) {
        return agent.match(regStr_ie);
    }
    // firefox
    if (agent.indexOf("firefox") > 0) {
        return agent.match(regStr_ff);
    }
    // Chrome
    if (agent.indexOf("chrome") > 0) {
        return agent.match(regStr_chrome);
    }
    // Safari
    if (agent.indexOf("safari") > 0 && agent.indexOf("chrome") < 0) {
        return agent.match(regStr_saf);
    }
}

/**
 * 检查是否安装flash
 * @returns {Boolean} true已安装
 */
function checkFlash() {
    if ((navigator.userAgent.indexOf('MSIE') >= 0)
        && (navigator.userAgent.indexOf('Opera') < 0)) {
        var swf1 = new ActiveXObject('ShockwaveFlash.ShockwaveFlash');
        if (swf1) {
            return true;
        }
        return false;
    } else {
        var swf2 = navigator.plugins['Shockwave Flash'];
        if (swf2 != undefined) {
            return true;
        }
        return false;
    }
}

//判断用户浏览器及设备
(function getMobileDevice(window) {
    var ua = navigator.userAgent;
    var mobile = /AppleWebKit.*Mobile.*/.test(ua) || /AppleWebKit/.test(ua); // 是否为移动终端
    var ios = /\(i[^;]+;( U;)? CPU.+Mac OS X/.test(ua); // ios终端
    var android = /(Android);?[\s\/]+([\d.]+)?/.test(ua); // android终端或者uc浏览器
    var iphone = /iphone/i.test(ua); // iphone
    var iPad = /iPad/i.test(ua); // ipad
    var weixin = /micromessenger/i.test(ua); // weixin
    var chrome = /Chrome\/([\d.]+)/.test(ua) || /CriOS\/([\d.]+)/.test(ua); // Chrome
    var mozilla = ua.indexOf('Gecko') > -1 && ua.indexOf('KHTML') == -1; // 火狐内核
    var webkit = /AppleWebKit/i.test(ua); // 苹果、谷歌内核
    var opera = /Presto/i.test(ua); // opera内核
    var safari = /safari/i.test(ua) && !/Chrome/i.test(ua); // 苹果浏览器
    var msie = /msie/i.test(ua); // 微软
    window.device = {
        isMobile: mobile,
        isIos: ios,
        isAndroid: android,
        isIPhone: iphone,
        isIPad: iPad,
        isWeiXin: weixin,
        isChrome: chrome,
        isMozilla: mozilla,
        isWebkit: webkit,
        isOpera: opera,
        isSafari: safari,
        isMsie: msie
    }
})(window);

//console.log(device);

/**
 * 去除html标签
 * @param str
 * @returns
 */
function delHtmlTag(str) {
    return $.trim(str.replace(/<[^>]+>/g, ""));
}

/**
 * 打开新窗口
 * @param url
 * @param width
 * @param height
 */
function openWindow(url, width, height) {
    var iTop = (window.screen.availHeight - 30 - height) / 2; // 获得窗口的垂直位置;
    var iLeft = (window.screen.availWidth - 10 - width) / 2; // 获得窗口的水平位置;
    window.open(url, "name", "width=" + width + ",height=" + height + ",left="
        + iLeft + ",top=" + iTop + "");
}

/**
 * 获取图片的真实高宽
 * @param img
 * @returns {Array}
 */
function getNaturalImg(img) {
    var image = new Image();
    image.src = img.attr("src");
    var naturalWidth = image.width;
    var naturalHeight = image.height;
    return new Array([naturalWidth, naturalHeight]);
}

/**
 * 在顶部显示tips
 * @param tips
 */
function showTopTips(tips) {
    var html = "<div class='tips'><i class='tips-i'></i><span class='tips-span'>" + tips
        + "</span><i class='tips-close' onclick='hideTips()'></i></div>";
    $("body").append(html);
    $(".tips").slideDown();
}

function hideTips() {
    $(".tips").slideUp("fast", function () {
        $(".tips").remove();
    });
}

/**
 * 计算时间差（天）
 * @param
 */
function DateDiff(sDate1, sDate2) {
    var aDate, oDate1, oDate2, iDays;
    aDate = sDate1.split("-");
    oDate1 = new Date(aDate[1] + '-' + aDate[2] + '-' + aDate[0]);
    aDate = sDate2.split("-");
    oDate2 = new Date(aDate[1] + '-' + aDate[2] + '-' + aDate[0]);
    iDays = parseInt(Math.abs(oDate1 - oDate2) / 1000 / 60 / 60 / 24);
    return iDays;
}

/**
 * 格式化日期
 *
 * @param value
 * @param type 1-yyyy-MM-dd hh:mm:ss 2-yyyy-MM-dd
 * @returns
 */
function formatDate(value, type) {
    if (value == null || value == '') {
        return '';
    }
    var dt;
    if (value instanceof Date) {
        dt = value;
    } else {
        dt = new Date(value);
    }
    if (type == 1) {
        return dt.format("yyyy-MM-dd hh:mm:ss"); // 扩展的Date的format方法
    } else if (type == 2) {
        return dt.format("yyyy-MM-dd");
    }
}

// 格式化日期-扩展format方法
Date.prototype.format = function (format) {
    var o = {
        "M+": this.getMonth() + 1,
        // 月
        "d+": this.getDate(),
        // 天
        "h+": this.getHours(),
        // 时
        "m+": this.getMinutes(),
        // 分
        "s+": this.getSeconds(),
        // 秒
        "q+": Math.floor((this.getMonth() + 3) / 3),
        // 刻
        "S": this.getMilliseconds()
        // 毫秒 millisecond
    };
    if (/(y+)/.test(format))
        format = format.replace(RegExp.$1, (this.getFullYear() + "")
            .substr(4 - RegExp.$1.length));
    for (var k in o)
        if (new RegExp("(" + k + ")").test(format))
            format = format.replace(RegExp.$1, RegExp.$1.length == 1 ? o[k]
                : ("00" + o[k]).substr(("" + o[k]).length));
    return format;
};
