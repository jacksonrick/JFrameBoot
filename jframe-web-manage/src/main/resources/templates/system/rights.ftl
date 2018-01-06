<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <title></title>
    <link href="/static/library/plugins/zTree/metroStyle/metroStyle.css" rel="stylesheet">
<#include "include.ftl"/>
</head>

<body class="gray-bg">
<div class="row">
    <div class="col-sm-12">
        <div class="ibox float-e-margins">
            <div class="ibox-content">
                <div class="alert alert-warning">
                    <p><i class="fa fa-exclamation-circle"></i>超级管理员和技术管理员为系统内置，不可编辑；为保证系统安全，仅技术管理拥有【系统管理】所有权限。</p>
                    <p><i class="fa fa-exclamation-circle"></i>授权说明：选择子模块，对应的父模块也会被选择；取消授权只需将模块反勾选即可。</p>
                </div>
                <table class="table table-striped table-bordered table-hover">
                    <thead>
                    <tr role="row">
                        <th>序号</th>
                        <th>组名称</th>
                        <th>状态</th>
                        <th>编辑</th>
                        <th>操作</th>
                    </tr>
                    </thead>
                    <tbody>
                    <#list roles as v>
                    <tr>
                        <td>${v_index }</td>
                        <td>${v.roleName }</td>
                        <td><i class="fa ${v.isDelete?string('fa-times','fa-check')}"></i></td>
                        <td>
                            <#if v.roleFlag == 1>
                                <a class="btn btn-sm btn-circle btn-warning" title="编辑" onclick="edit(${v.id},'${v.roleName }')"
                                   data-toggle="tooltip" data-placement="top"><i class="fa fa-pencil-square"></i></a>
                                <#if v.isDelete>
                                    <a class="btn btn-sm btn-circle btn-warning btn-enable" title="启用" data-id="${v.id }"
                                       data-toggle="tooltip" data-placement="top"><i class="fa fa-check"></i></a>
                                <#else>
                                    <a class="btn btn-sm btn-circle btn-danger btn-enable" title="禁用" data-id="${v.id }"
                                       data-toggle="tooltip" data-placement="top"><i class="fa fa-times"></i></a>
                                </#if>
                            <#else>
                                <a class="btn btn-sm btn-circle btn-warning" disabled="disabled" title="不可编辑"
                                   data-toggle="tooltip" data-placement="top"><i class="fa fa-pencil-square"></i></a>
                            </#if>
                        </td>
                        <td>
                            <a class="btn btn-sm btn-circle btn-success" title="编辑权限" data-toggle="tooltip" data-placement="top"
                               onclick="getModules(${v.id },'${v.roleName }')"><i class="fa fa-pencil-square"></i></a>
                        </td>
                    </tr>
                    </#list>
                    </tbody>
                </table>
                <div class="row">
                    <div class="col-sm-4">
                        <a class="btn btn-sm btn-info" id="btn-add"><i class="fa fa-plus"></i>新增组</a>
                    </div>
                </div>

            </div>
        </div>
    </div>
</div>

<div class="layer-win layer-edit">
    <form class="form-horizontal" id="roleForm">
        <div class="form-group">
            <label class="col-sm-3 control-label">组名：</label>
            <div class="col-sm-9">
                <input type="text" name="roleName" id="roleName" class="form-control" placeholder="组名">
            </div>
        </div>
        <div class="form-group">
            <div class="col-sm-offset-3 col-sm-6">
                <input type="hidden" name="roleId" id="roleId">
                <button type="button" class="btn btn-primary" id="btn-edit">确定</button>
            </div>
        </div>
    </form>
</div>

<div class="layer-win layer-tree">
    <ul id="tree" class="ztree"></ul>
    <input type="hidden" id="rid" value="">
    <a class="btn btn-sm btn-success btn-block btn-rounded" id="btn-permit">对该组授权</a>
</div>

<script type="text/javascript" src="/static/library/plugins/zTree/jquery.ztree.all.min.js"></script>
<script type="text/javascript">
    function edit(id, name) {
        $('#roleName').val(name);
        $('#roleId').val(id);
        openLayerContent('编辑组', "300px", "180px", $(".layer-edit"));
    }

    $(function () {
        $(".btn-enable").click(function () {
            var title = $(this).attr("title") == "" ? $(this).attr("data-original-title") : $(this).attr("title");
            layerConfirm('确定要' + title + '该组吗', "/admin/system/roleEnable?roleId=" + $(this).attr('data-id'));
        });

        $("#btn-edit").click(function () {
            if (checkString("#roleName", 2, 6)) {
                toast(2, "", "组名不能为空且在2-6个字符");
                return;
            }
            Ajax.ajax({
                url: '/admin/system/roleEdit',
                params: $("#roleForm").serialize(),
                success: function (data) {
                    if (data.code == 0) {
                        showMsg(data.msg, 1, function () {
                            location.reload();
                        });
                    } else {
                        showMsg(data.msg, 2);
                    }
                }
            });
        });

        $("#btn-add").click(function () {
            $('#roleName').val("");
            $('#roleId').val("");
            openLayerContent('新增组', "300px", "180px", $(".layer-edit"));
        });

        $("#btn-permit").click(function () {
            var str = "";
            var treeObj = $.fn.zTree.getZTreeObj("tree");
            var nodes = treeObj.getCheckedNodes(true);
            for (var i = 0; i < nodes.length; i++) {
                str += nodes[i].id + ",";
            }
            var rid = $("#rid").val();
            if (rid == "") {
                toast(2, "", "未选择组");
                return;
            }
            Ajax.ajax({
                url: '/admin/system/permit',
                params: {roleId: rid, params: str},
                success: function (data) {
                    if (data.code == 0) {
                        showMsg(data.msg, 1, function () {
                            layer.close(zindex);
                        });
                    } else {
                        showMsg(data.msg, 2);
                    }
                }
            });
        });
    });

    var zindex;
    var tree = "";
    var setting = {
        check: {
            chkboxType: {"Y": "p", "N": "ps"},// 勾选checkbox对于父子节点的关联关系
            chkStyle: "checkbox",
            enable: true // 是否复选框
        },
        data: {
            simpleData: {
                enable: true
            }
        }
    };

    function getModules(roleId, roleName) {
        $("#rid").val(roleId);
        Ajax.ajax({
            url: '/admin/system/rolePerm',
            params: {"roleId": roleId},
            success: function (data) {
                tree = $.fn.zTree.init($("#tree"), setting, data);
                tree.expandAll(true);
                zindex = openLayerContent('编辑权限【' + roleName + '】', "450px", "500px", $(".layer-tree"));
            }
        });
    }
</script>
</body>
</html>