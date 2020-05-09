/**
 * Webapp Global JS
 * Created by rick on 2016-09-09.
 * need: layer-mobile/layer.js
 */

/**
 * Ajax封装
 * @type {{ajax: ajax}}
 */
Ajax = {
    ajax: function (options) {
        var defaults = {
            async: true,
            dataType: 'json',
            url: null,
            params: null,
            success: null,
            error: null
        }
        var config = $.extend(defaults, options);
        if (config.url) {
            //var index = layer.open({type: 2});
            $.ajax({
                type: "POST",
                async: config.async,
                // contentType : 'application/json',
                url: config.url,
                data: config.params,
                dataType: config.dataType,
                headers: {
                    "appkey": "00056cb1d74435076a4c15a490798df9",
                    "Req-Type": "APP"
                },
                success: function (data) {
                    //layer.close(index);
                    if (config.success) {
                        config.success(data);
                    }
                },
                error: function (er) {
                    //layer.close(index);
                    openDialog('Request Error');
                }
            });
        } else {
            openDialog('Request Url Is Error');
        }
    },
    sel: function (options) {
        var config = {
            id: options.id,
            type: options.type,
            url: options.url,
            callback: options.callback
        }
        if (config.url) {
            if (config.type == 1 || config.type == 2) {
                $(config.id).on("click", function () {
                    Ajax.sel_deal(config);
                });
            }
            if (config.type == 3) {
                Ajax.sel_deal(config);
            }
        }
    },
    sel_deal: function (config) {
        var html = "";
        Ajax.ajax({
            url: config.url,
            params: null,
            success: function (data) {
                html += '<div class="sel-main">';
                $.each(data, function (k, v) {
                    html += '<a class="sel" data-id="' + v.id + '" data-text="' + v.name + '">' + v.name + '</a>';
                });
                html += '<div style="clear: left"></div> </div>';
                var index = layer.open({
                    title: ["<button class='sel-btn-l'>取消</button><button class='sel-btn-r'>确定</button>"],
                    content: html
                });

                if (config.type == 1 || config.type == 3) {
                    $("body").on("click", ".sel", function () {
                        $(".sel").removeClass("active");
                        var t = $(this);
                        t.addClass("active");
                    });
                }
                if (config.type == 2) {
                    $("body").on("click", ".sel", function () {
                        var t = $(this);
                        if (t.hasClass("active")) {
                            t.removeClass("active");
                        } else {
                            t.addClass("active");
                        }
                    });
                }

                var json = new Array;
                $("body").on("click", ".sel-btn-l", function () {
                    layer.close(index);
                });
                $("body").on("click", ".sel-btn-r", function () {
                    if (config.type == 1 || config.type == 3) {
                        var t = $(".sel.active");
                        json = {
                            id: t.attr("data-id"),
                            text: t.attr("data-text")
                        }
                    }
                    if (config.type == 2) {
                        $.each($(".sel.active"), function (k, v) {
                            json.push({
                                id: $(v).attr("data-id"),
                                text: $(v).attr("data-text")
                            });
                        });
                    }

                    $("body").off("click", ".sel");
                    $("body").off("click", ".sel-btn-l");
                    $("body").off("click", ".sel-btn-r");
                    //layer.close(index);
                    config.callback(json, data);
                });
            }
        });
    },
    remove: function (json, id) {
        for (var i = 0; i < json.length; i++) {
            if (json[i].id == id) {
                json.splice(json.indexOf(json[i]), 1);
            }
        }
        return json;
    },
    unique: function (json) {
        var sum = json.length;
        for (var i = 0; i < sum; i++) {
            for (var j = 0; j < sum; j++) {
                if (i != j) {
                    if (json[i].id == json[j].id) {
                        json.splice(json.indexOf(json[i]), 1);
                    }
                    sum--;
                }
            }
        }
        return json;
    }
}


/**
 * 显示对话框
 * @param msg
 */
function openDialog(msg) {
    layer.open({
        content: msg,
        btn: '关闭'
    });
}

/**
 * 显示提示信息
 * @param msg
 * @param func
 */
function openMsg(msg, func) {
    layer.open({
        content: msg,
        skin: 'msg',
        time: 2,
        end: function () {
            if (func) {
                func()
            }
        }
    });
}

/**
 * 显示确认框
 * @param msg
 * @param func
 */
function openConfirm(msg, func) {
    layer.open({
        content: msg,
        btn: ['确定', '取消'],
        yes: function (index) {
            func();
            layer.close(index);
        }
    });
}

/**
 * 跳转url
 * @param url
 */
function jumpUrl(url) {
    location.href = url;
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
    return "";
}

/**
 * 获取Cookie值
 * @param name
 * @returns
 */
function getCookie(name) {
    var val = $.cookie(name);
    if (val != null) {
        return val;
    }
    return "";
}

function ifAndroid() {
    var device = getCookie("device");
    if (device == "android") {
        return true;
    }
    return false;
}

function ifIOS() {
    var device = getCookie("device");
    if (device == "ios") {
        return true;
    }
    return false;
}

function setupWebViewJavascriptBridge(callback) {
    if (window.WebViewJavascriptBridge) {
        return callback(WebViewJavascriptBridge);
    }
    if (window.WVJBCallbacks) {
        return window.WVJBCallbacks.push(callback);
    }
    window.WVJBCallbacks = [callback];
    var WVJBIframe = document.createElement('iframe');
    WVJBIframe.style.display = 'none';
    WVJBIframe.src = 'wvjbscheme://__BRIDGE_LOADED__';
    document.documentElement.appendChild(WVJBIframe);
    setTimeout(function () {
        document.documentElement.removeChild(WVJBIframe)
    }, 0)
}

// layui
/*layui.use('laytpl', function () {
    var laytpl = layui.laytpl;
    var getTpl = tpl.innerHTML;
    laytpl(getTpl).render(data.data.list, function (html) {
        $("#pages").append(html);
    });
});*/
