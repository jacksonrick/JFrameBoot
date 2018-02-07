<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <title></title>
    <link type="text/css" href="/static/library/plugins/dropzone/dropzone.css" rel="stylesheet"/>
    <link type="text/css" href="/static/library/plugins/select2/select2.min.css" rel="stylesheet">
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
                <div class="col-sm-6">
                    <div class="ibox float-e-margins">
                        <div class="ibox-title">
                            <h5>元素</h5>
                        </div>
                        <div class="ibox-content" id="elem">
                            <form role="form" class="form-horizontal m-t">
                                <div class="form-group">
                                    <label class="col-sm-3 control-label">纯文本：</label>
                                    <div class="col-sm-9">
                                        <p class="form-control-static">这里是纯文字信息</p>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label class="col-sm-3 control-label">文本框：</label>
                                    <div class="col-sm-9">
                                        <input type="text" name="" class="form-control" placeholder="请输入文本" value="11">
                                        <span class="help-block m-b-none">说明文字</span>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label class="col-sm-3 control-label">密码框：</label>
                                    <div class="col-sm-9">
                                        <input type="password" class="form-control" name="password" placeholder="请输入密码" value="11">
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label class="col-sm-3 control-label">下拉列表：</label>
                                    <div class="col-sm-9">
                                        <select class="form-control" name="">
                                            <option value>--请选择--</option>
                                            <option value="1">选项 1</option>
                                            <option value="2">选项 2</option>
                                            <option value="3">选项 3</option>
                                            <option value="4">选项 4</option>
                                        </select>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label class="col-sm-3 control-label">单选框：</label>
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
                                <div class="form-group">
                                    <label class="col-sm-3 control-label">复选框：</label>
                                    <div class="col-sm-9">
                                        <div class="checkbox checkbox-inline">
                                            <input id="checkbox1" type="checkbox" checked>
                                            <label for="checkbox1">Default</label>
                                        </div>
                                        <div class="checkbox checkbox-inline">
                                            <input id="checkbox2" type="checkbox">
                                            <label for="checkbox2">Primary</label>
                                        </div>
                                        <div class="checkbox checkbox-inline">
                                            <input id="checkbox3" type="checkbox">
                                            <label for="checkbox3">Success</label>
                                        </div>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label class="col-sm-3 control-label">复选框2：</label>
                                    <div class="col-sm-9">
                                        <div class="checkbox checkbox-info">
                                            <input id="checkbox11" type="checkbox">
                                            <label for="checkbox11">Default</label>
                                        </div>
                                        <div class="checkbox checkbox-primary">
                                            <input id="checkbox22" type="checkbox" checked>
                                            <label for="checkbox22">Primary</label>
                                        </div>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label class="col-sm-3 control-label">地址选择：</label>
                                    <div class="col-sm-9">
                                        <input id="city-picker" name="address" value="340000/340100/340102/340102001" style="width: 100%"/>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label class="col-sm-3 control-label">头像：</label>
                                    <div class="col-sm-9">
                                        <a class="img-area-btn" id="avatar"></a>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label class="col-sm-3 control-label">时间：</label>
                                    <div class="col-sm-9">
                                        <input type="text" id="birthday" name="birthday" value="" class="form-control" placeholder="生日">
                                        <input type="text" id="birthday2" name="birthday" value="" class="form-control" placeholder="生日2">
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label class="col-sm-3 control-label">Select2：</label>
                                    <div class="col-sm-9">
                                        <select class="form-control" id="sel_menu" style="width: 100%;">
                                            <optgroup label="系统设置">
                                                <option value="1">用户管理</option>
                                                <option value="2">角色管理</option>
                                                <option value="3">部门管理</option>
                                                <option value="4">菜单管理</option>
                                            </optgroup>
                                            <optgroup label="订单管理">
                                                <option value="5">订单查询</option>
                                                <option value="6">订单导入</option>
                                                <option value="7">订单删除</option>
                                                <option value="8">订单撤销</option>
                                            </optgroup>
                                        </select>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label class="col-sm-3 control-label">编辑器：</label>
                                    <div class="col-sm-9">
                                        <textarea name="content" style="width: 100%;height: 100px;"></textarea>
                                        <div class="word-count">字数统计：<span id="wc"></span></div>
                                    </div>
                                </div>

                                <div class="hr-line-dashed"></div>
                                <div class="form-group">
                                    <div class="col-sm-12 col-sm-offset-3">
                                        <button class="btn btn-info" type="button">提交</button>
                                        <button class="btn btn-default" type="button">取消</button>
                                    </div>
                                </div>
                            </form>
                        </div>
                    </div>
                </div>
                <div class="col-sm-6">
                    <div class="ibox float-e-margins">
                        <div class="ibox-title">
                            <h5>代码区</h5>
                        </div>
                        <div class="ibox-content" style="overflow: scroll;">
                            <textarea id="code" class="form-control" style="min-height: 800px; margin-bottom: 10px;font-family: Monaco, Fixed; width: 150%;"></textarea>
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
                    <a class="btn btn-sm btn-info btn-tools" data-url="http://fontawesome.dashgame.com/">FontAwesome图标库</a>
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

<script type="text/javascript" src="/static/library/plugins/beautifyhtml/beautifyhtml.js"></script>
<script type="text/javascript" src="/static/library/plugins/dropzone/dropzone.js"></script>
<script type="text/javascript" src="/static/library/plugins/select2/select2.min.js"></script>
<script type="text/javascript" src="/static/library/plugins/kindeditor/kindeditor-min.js"></script>
<script type="text/javascript">
    $(document).ready(function () {
        $("#code").val(html_beautify($("#elem").html()))

        // 注意：此插件和jquery-ui不兼容，会存在一些问题，请忽略
        cityPicker("#city-picker", "district", "code");
        // 上传头像
        $("#avatar").Uploader({
            limit: 1,
            default: '',
            type: 1
        });
        // Select2
        $("#sel_menu").select2({
            allowClear: true,
            multiple: true,
            maximumSelectionLength: 10
        });
        // 时间
        datePicker("#birthday", "yyyy-mm-dd");
        datePicker("#birthday2", "yyyy-mm-dd hh:ii");
        // 编辑器
        var editor;
        KindEditor.ready(function (K) {
            editor = K.create('textarea[name="content"]', {
                resizeType: 1,
                allowPreviewEmoticons: false,
                allowImageUpload: true,
                uploadJson: '/uploadwithKE.do',
                afterBlur: function () {
                    this.sync();
                },
                afterChange: function () {
                    K('#wc').html(this.count('text'));
                }
            });
        });

        $(".btn-tools").click(function () {
            var name = $(this).attr("title");
            var url = $(this).attr("data-url");
            var index = openLayerUrl(name, document.body.scrollWidth - 100 + "px", "600px", url, true);
            layer.full(index);
        });
    });
</script>

<script type="text/javascript">
    $(function () {
        $("#requestUrl").val(window.location.protocol + "//" + window.location.host + "/");

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