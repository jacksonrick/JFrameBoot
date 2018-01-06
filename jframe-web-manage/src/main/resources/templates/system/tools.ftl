<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <title></title>
<#include "include.ftl"/>
</head>

<body class="gray-bg">

<ul class="nav nav-tabs" role="tablist">
    <li role="presentation" class="active"><a href="#form" aria-controls="form" role="tab" data-toggle="tab">表单构建</a></li>
    <li role="presentation"><a href="#interface" aria-controls="interface" role="tab" data-toggle="tab">APP接口测试</a></li>
    <li role="presentation"><a href="#sql" aria-controls="sql" role="tab" data-toggle="tab">SQL编辑器</a></li>
    <li role="presentation"><a href="#js" aria-controls="js" role="tab" data-toggle="tab">工具网站</a></li>
</ul>
<div class="tab-content">
    <div role="tabpanel" class="tab-pane fade in active" id="form">
        <div class="wrapper-content">
            <div class="row">
                <div class="col-sm-5">
                    <div class="ibox float-e-margins">
                        <div class="ibox-title">
                            <h5>元素</h5>
                        </div>
                        <div class="ibox-content">
                            <div class="alert alert-info">
                                拖拽左侧的表单元素到右侧区域，即可生成相应的HTML代码！
                            </div>
                            <form role="form" class="form-horizontal m-t">
                                <div class="form-group draggable">
                                    <label class="col-sm-3 control-label">纯文本：</label>

                                    <div class="col-sm-9">
                                        <p class="form-control-static">这里是纯文字信息</p>
                                    </div>
                                </div>
                                <div class="form-group draggable">
                                    <label class="col-sm-3 control-label">文本框：</label>
                                    <div class="col-sm-9">
                                        <input type="text" name="" class="form-control" placeholder="请输入文本">
                                        <span class="help-block m-b-none">说明文字</span>
                                    </div>
                                </div>
                                <div class="form-group draggable">
                                    <label class="col-sm-3 control-label">密码框：</label>
                                    <div class="col-sm-9">
                                        <input type="password" class="form-control" name="password" placeholder="请输入密码">
                                    </div>
                                </div>
                                <div class="form-group draggable">
                                    <label class="col-sm-3 control-label">下拉列表：</label>
                                    <div class="col-sm-9">
                                        <select class="form-control" name="">
                                            <option>选项 1</option>
                                            <option>选项 2</option>
                                            <option>选项 3</option>
                                            <option>选项 4</option>
                                        </select>
                                    </div>
                                </div>
                                <div class="form-group draggable">
                                    <label class="col-sm-3 control-label">单选框2：</label>
                                    <div class="col-sm-9">
                                        <div class="radio radio-inline">
                                            <input type="radio" name="radio1" id="radio1" value="option1" checked="">
                                            <label for="radio1">文本 01</label>
                                        </div>
                                        <div class="radio radio-inline">
                                            <input type="radio" name="radio1" id="radio2" value="option2">
                                            <label for="radio2">文本 02</label>
                                        </div>
                                    </div>
                                </div>
                                <div class="form-group draggable">
                                    <label class="col-sm-3 control-label">复选框：</label>
                                    <div class="col-sm-9">
                                        <div class="checkbox">
                                            <input id="checkbox1" type="checkbox">
                                            <label for="checkbox1">Default</label>
                                        </div>
                                        <div class="checkbox checkbox-primary">
                                            <input id="checkbox2" type="checkbox" checked="">
                                            <label for="checkbox2">Primary</label>
                                        </div>
                                        <div class="checkbox checkbox-success">
                                            <input id="checkbox3" type="checkbox">
                                            <label for="checkbox3">Success</label>
                                        </div>
                                    </div>
                                </div>
                                <div class="form-group draggable">
                                    <label class="col-sm-3 control-label">地址选择：</label>
                                    <div class="col-sm-9">
                                        <input id="city-picker" name="address" value="340000/340100/340102/340102001" style="width: 100%"/>
                                    </div>
                                </div>
                                <div class="hr-line-dashed"></div>
                                <div class="form-group draggable">
                                    <div class="col-sm-12 col-sm-offset-3">
                                        <button class="btn btn-info" type="button">保存内容</button>
                                        <button class="btn btn-default" type="button">取消</button>
                                    </div>
                                </div>
                            </form>
                            <div class="clearfix"></div>
                        </div>
                    </div>
                </div>
                <div class="col-sm-7">
                    <div class="ibox float-e-margins">
                        <div class="ibox-title">
                            <h5>拖拽左侧表单元素到此区域</h5>
                            <div class="ibox-tools">请选择显示的列数：
                                <select id="n-columns">
                                    <option value="1">显示1列</option>
                                    <option value="2">显示2列</option>
                                </select>
                            </div>
                        </div>

                        <div class="ibox-content">
                            <div class="row form-body form-horizontal m-t">
                                <div class="col-md-12 droppable sortable">
                                </div>
                                <div class="col-md-6 droppable sortable" style="display: none;">
                                </div>
                                <div class="col-md-6 droppable sortable" style="display: none;">
                                </div>
                            </div>
                            <button type="button" class="btn btn-warning" data-clipboard-text="testing" id="copy-to-clipboard">复制代码</button>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
    <div role="tabpanel" class="tab-pane fade" id="interface">
        <div class="wrapper-content">
            <div class="ibox float-e-margins">
                <div class="ibox-title">
                    <h5>服务器接口测试</h5>
                </div>
                <div class="alert alert-warning mb0">
                    <i class="fa fa-exclamation-circle"></i>返回数据为Json格式
                </div>
                <div class="ibox-content">
                    <div class="row">
                        <div class="col-md-6">
                            <div class="input-group"><span class="input-group-addon"><i class="fa fa-globe"></i></span>
                                <input id="requestUrl" type="text" placeholder="请求地址" class="form-control" value="/testJson">
                            </div>
                        </div>
                        <div class="col-md-6">
                            <button id="request" class="btn btn-sm btn-danger">请求</button>
                            <button id="addParam" class="btn btn-sm btn-info">添加参数</button>
                        </div>
                    </div>
                    <div>
                        <div class="row mt15">
                            <div class="col-md-2"><input type="text" class="form-control" value="auth_code" disabled="disabled"></div>
                            <div class="col-md-3"><input type="text" class="form-control" value="" name="auth" placeholder="auth_code">
                            </div>
                        </div>
                    </div>
                    <div id="requestParam">
                        <div class="row request-param mt15">
                            <div class="col-md-2"><input type="text" class="form-control" value="" name="key" placeholder="key"></div>
                            <div class="col-md-3"><input type="text" class="form-control" value="" name="value" placeholder="value"></div>
                            <div class="col-md-1"><a class="btn-del-param"><i class="fa fa-times"></i></a></div>
                        </div>
                    </div>
                    <div class="mt20">
                        <textarea class="json-code form-control" id="editor"></textarea>
                    </div>
                </div>
            </div>
        </div>
    </div>
    <div role="tabpanel" class="tab-pane fade" id="sql">
        <div class="wrapper-content">
            <div class="alert alert-warning mb0">
                <p><i class="fa fa-exclamation-circle"></i>该功能仅开发人员使用，请谨慎操作。</p>
            </div>
            <div class="panel-group" id="accordion" role="tablist" aria-multiselectable="true">
                <div class="panel panel-default">
                    <div class="panel-heading" role="tab" id="headingOne">
                        <h4 class="panel-title">
                            <a role="button" data-toggle="collapse" data-parent="#accordion" href="#collapseOne" aria-expanded="true"
                               aria-controls="collapseOne"> SQL脚本编辑语句 </a>
                        </h4>
                    </div>
                    <div id="collapseOne" class="panel-collapse collapse" role="tabpanel" aria-labelledby="headingOne">
                        <div class="panel-body">
                            <textarea class="sql-text form-control" id="updateSql"></textarea>
                            <button class="btn btn-danger" id="executeUpdate"><i class="fa fa-caret-right"></i>Execute</button>
                            <p>&nbsp;</p>
                            <p class="text-danger" id="updateInfo"></p>
                        </div>
                    </div>
                </div>

                <div class="panel panel-default">
                    <div class="panel-heading" role="tab" id="headingTwo">
                        <h4 class="panel-title">
                            <a role="button" data-toggle="collapse" data-parent="#accordion" href="#collapseTwo" aria-expanded="true"
                               aria-controls="collapseTwo"> SQL脚本查询语句 </a>
                        </h4>
                    </div>
                    <div id="collapseTwo" class="panel-collapse collapse in" role="tabpanel" aria-labelledby="headingTwo">
                        <div class="panel-body">
                            <textarea class="sql-text form-control" id="querySql"></textarea>
                            <button class="btn btn-danger" id="executeQuery"><i class="fa fa-caret-right"></i>Execute</button>
                            <p>&nbsp;</p>
                            <p class="text-danger" id="queryInfo"></p>

                            <div style="clear: both;padding-top: 15px;overflow-x: scroll;">
                                <table id="simple-table" class="table table-striped table-bordered table-hover">
                                    <thead id="theadid">
                                    <tr id="columnList">
                                        <th>字段</th>
                                    </tr>
                                    </thead>

                                    <tbody id="valuelist">
                                    <tr class='center'>
                                        <td>数据显示区</td>
                                    </tr>
                                    </tbody>
                                </table>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
    <div role="tabpanel" class="tab-pane fade" id="js">
        <div class="wrapper-content">
            <div class="panel panel-success">
                <div class="panel-heading">
                    <h3 class="panel-title">图标</h3>
                </div>
                <div class="panel-body">
                    <a class="btn btn-sm btn-info btn-tools" data-url="http://at.alicdn.com/t/font_374397_f02glb18zvdeu3di.css">IconFont图标库[jframe]</a>
                    <a class="btn btn-sm btn-info btn-tools" data-url="/admin/system/icons">FontAwesome图标库</a>
                    <span class="help-block">Icon-font会定期更新，URL也会更新</span>
                </div>
            </div>

            <div class="panel panel-success">
                <div class="panel-heading">
                    <h3 class="panel-title">JSON</h3>
                </div>
                <div class="panel-body">
                    <a class="btn btn-sm btn-warning btn-tools" data-url="http://www.bejson.com#JSONValidate">Json验证器</a>
                    <a class="btn btn-sm btn-warning btn-tools" data-url="http://www.bejson.com/json2javapojo#basic-addon1">Json转实体类</a>
                </div>
            </div>

            <div class="panel panel-success">
                <div class="panel-heading">
                    <h3 class="panel-title">格式化</h3>
                </div>
                <div class="panel-body">
                    <a class="btn btn-sm btn-warning btn-tools" data-url="http://www.bejson.com/otherformat/css/#content">CSS格式化</a>
                    <a class="btn btn-sm btn-warning btn-tools" data-url="http://www.bejson.com/jshtml_format/">JS格式化</a>
                    <a class="btn btn-sm btn-warning btn-tools" data-url="http://www.bejson.com/convert/unicode_chinese/">unicode互转</a>
                </div>
            </div>

            <div class="panel panel-success">
                <div class="panel-heading">
                    <h3 class="panel-title">工具</h3>
                </div>
                <div class="panel-body">
                    <a class="btn btn-sm btn-warning btn-tools" data-url="http://www.bootcss.com/p/bootstrap-form-builder/">第三方表单构建工具</a>
                    <a class="btn btn-sm btn-warning btn-tools" data-url="http://www.bejson.com/convert/unix#basic-addon1">Unix时间戳转换</a>
                    <a class="btn btn-sm btn-warning btn-tools" data-url="http://www.bejson.com/othertools/regex#textSour">正则表达式测试</a>
                    <a class="btn btn-sm btn-warning btn-tools" data-url="http://www.bejson.com/othertools/regex_create/">正则表达式生成</a>
                    <a class="btn btn-sm btn-warning btn-tools" data-url="http://www.bejson.com/convert/map/">经纬度转换</a>
                </div>
            </div>

            <div class="panel panel-success">
                <div class="panel-heading">
                    <h3 class="panel-title">图片</h3>
                </div>
                <div class="panel-body">
                    <a class="btn btn-sm btn-warning btn-tools" data-url="http://www.bejson.com/convert/qrcode#encode-text">二维码解码和生成</a>
                    <a class="btn btn-sm btn-warning btn-tools" data-url="http://www.bejson.com/ui/compress_img/">图片压缩</a>
                    <a class="btn btn-sm btn-warning btn-tools" data-url="http://www.bejson.com/ui/icomaker/">ICO转换</a>
                    <a class="btn btn-sm btn-warning btn-tools" data-url="http://www.bejson.com/ui/borderradius/">生成圆角图片</a>
                </div>
            </div>
        </div>
    </div>
</div>

<script type="text/javascript" src="/static/library/jquery/jquery-ui.min.js"></script>
<script type="text/javascript" src="/static/library/plugins/beautifyhtml/beautifyhtml.js"></script>
<script type="text/javascript">
    $(document).ready(function () {
        // 注意：此插件和jquery-ui不兼容，会存在一些问题
        cityPicker("#city-picker", "district", "code");

        $(".btn-tools").click(function () {
            var name = $(this).attr("title");
            var url = $(this).attr("data-url");
            var index = openLayerUrl(name, document.body.scrollWidth - 100 + "px", "600px", url, true);
            layer.full(index);
        });

        setup_draggable();

        $("#n-columns").on("change", function () {
            var v = $(this).val();
            if (v === "1") {
                var $col = $('.form-body .col-md-12').toggle(true);
                $('.form-body .col-md-6 .draggable').each(function (i, el) {
                    $(this).remove().appendTo($col);
                });
                $('.form-body .col-md-6').toggle(false);
            } else {
                var $col = $('.form-body .col-md-6').toggle(true);
                $(".form-body .col-md-12 .draggable").each(function (i, el) {
                    $(this).remove().appendTo(i % 2 ? $col[1] : $col[0]);
                });
                $('.form-body .col-md-12').toggle(false);
            }
        });

        $("#copy-to-clipboard").on("click", function () {
            var $copy = $(".form-body").clone().appendTo(document.body);
            $copy.find(".tools, :hidden").remove();
            $.each(["draggable", "droppable", "sortable", "dropped", "ui-sortable", "ui-draggable", "ui-droppable", "form-body"], function (i, c) {
                $copy.find("." + c).removeClass(c).removeAttr("style");
            });
            var html = html_beautify($copy.html());
            $copy.remove();

            $modal = get_modal(html).modal("show");
            $modal.find(".btn").remove();
            $modal.find(".modal-title").html("复制HTML代码");
            $modal.find(":input:first").select().focus();

            return false;
        })

    });

    var setup_draggable = function () {
        $(".draggable").draggable({
            appendTo: "body",
            helper: "clone"
        });
        $(".droppable").droppable({
            accept: ".draggable",
            helper: "clone",
            hoverClass: "droppable-active",
            drop: function (event, ui) {
                $(".empty-form").remove();
                var $orig = $(ui.draggable);
                if (!$(ui.draggable).hasClass("dropped")) {
                    var $el = $orig.clone().addClass("dropped").css({
                        "position": "static",
                        "left": null,
                        "right": null
                    }).appendTo(this);

                    var id = $orig.find(":input").attr("id");
                    if (id) {
                        id = id.split("-").slice(0, -1).join("-") + "-" + (parseInt(id.split("-").slice(-1)[0]) + 1);
                        $orig.find(":input").attr("id", id);
                        $orig.find("label").attr("for", id);
                    }
                    // tools
                    $('<p class="tools col-sm-12 col-sm-offset-3">\
					<a class="edit-link">编辑HTML<a> | \
					<a class="remove-link">移除</a></p>').appendTo($el);
                } else {
                    if ($(this)[0] != $orig.parent()[0]) {
                        var $el = $orig.clone().css({
                            "position": "static",
                            "left": null,
                            "right": null
                        }).appendTo(this);
                        $orig.remove();
                    }
                }
            }
        }).sortable();
    };

    var get_modal = function (content) {
        var modal = $('<div class="modal" style="overflow: auto;" tabindex="-1">\
		<div class="modal-dialog">\
			<div class="modal-content">\
				<div class="modal-header">\
					<a type="button" class="close"\
						data-dismiss="modal" aria-hidden="true">&times;</a>\
					<h4 class="modal-title">编辑HTML</h4>\
				</div>\
				<div class="modal-body ui-front">\
					<textarea class="form-control" \
						style="min-height: 200px; margin-bottom: 10px;\
						font-family: Monaco, Fixed">' + content + '</textarea>\
					<button class="btn btn-success">更新HTML</button>\
				</div>\
			</div>\
		</div>\
		</div>').appendTo(document.body);

        return modal;
    };

    $(document).on("click", ".edit-link", function (ev) {
        var $el = $(this).parent().parent();
        var $el_copy = $el.clone();

        var $edit_btn = $el_copy.find(".edit-link").parent().remove();

        var $modal = get_modal(html_beautify($el_copy.html())).modal("show");
        $modal.find(":input:first").focus();
        $modal.find(".btn-success").click(function (ev2) {
            var html = $modal.find("textarea").val();
            if (!html) {
                $el.remove();
            } else {
                $el.html(html);
                $edit_btn.appendTo($el);
            }
            $modal.modal("hide");
            return false;
        })
    });

    $(document).on("click", ".remove-link", function (ev) {
        $(this).parent().parent().remove();
    });
</script>

<script type="text/javascript">
    $(function () {
        $("#request").click(function () {
            if (checkUrl("#requestUrl")) {
                showMsg("请输入正确的Url", 2);
                return;
            }
            var jsonArr = "";
            var rp = $(".request-param");
            for (var i = 0; i < rp.length; i++) {
                var key = rp.eq(i).find("input[name='key']").val();
                var value = rp.eq(i).find("input[name='value']").val();
                jsonArr += key + "=" + value + "&";
            }
            jsonArr = "?" + jsonArr.substr(0, jsonArr.length - 1);

            Ajax.ajax({
                url: $("#requestUrl").val() + jsonArr,
                params: {"auth": $("input[name='auth']").val()},
                success: function (data) {
                    $(".json-code").val(JSON.stringify(data, null, "\t"));
                }
            });
        });

        $("#addParam").click(function () {
            $("#requestParam").append($(".request-param").eq(0).clone());
        });

        $("#requestParam").delegate(".btn-del-param", "click", function () {
            if ($(".request-param").length == 1) {
                return;
            }
            $(this).parent(".col-md-1").parent(".request-param").remove();
        });

    });
</script>

<script type="text/javascript">
    $(function () {
        $("#executeUpdate").click(function () {
            if (checkString("#updateSql", 10, 1000)) {
                return;
            }
            Ajax.ajax({
                url: "/admin/system/executeUpdate",
                params: {"sql": $("#updateSql").val()},
                success: function (data) {
                    if (data.msg.code == 0) {
                        $("#updateInfo").html('执行成功，耗时：' + data.rTime + 's');
                    } else {
                        showMsg('查询失败，sql语句错误或非更新语句或查询数据库连接错误', 2);
                    }
                }
            });
        });

        $("#executeQuery").click(function () {
            if (checkString("#querySql", 10, 1000)) {
                return;
            }
            Ajax.ajax({
                url: "/admin/system/executeQuery",
                params: {"sql": $("#querySql").val()},
                success: function (data) {
                    if (data.msg.code == 0) {
                        $("#columnList").html('');
                        $("#valuelist").html('');
                        $.each(data.columnList, function (m, column) {
                            $("#columnList").append(
                                    '<th>' + column + '</th>'
                            );
                        });
                        var fhcount = 0;
                        $.each(data.dataList, function (n, fdata) {
                            var str1 = '<tr>';
                            var str2 = '';
                            $.each(fdata, function (f, fvalue) {
                                str2 += '<td>' + fvalue + '</td>';
                            });
                            var str3 = '</tr>';
                            $("#valuelist").append(str1 + str2 + str3);
                            fhcount++;
                        });
                        $("#queryInfo").html('查询成功，耗时：' + data.rTime + 's' + '，共 ' + fhcount + '条记录');
                    } else {
                        showMsg('查询失败，sql语句错误或非查询语句或查询数据库连接错误', 2);
                    }
                }
            });
        });
    });
</script>
</body>
</html>