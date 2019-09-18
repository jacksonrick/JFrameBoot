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
                <div class="row">
                    <div class="col-sm-12">
                        <form action="/admin/system/adminList" method="post" class="form-inline" id="queryForm">
                            <label>用户名/真实姓名/手机号:<input type="text" name="keywords" value="${keywords }" class="form-control input-sm"></label>
                            <label>组:
                                <select class="form-control input-sm" name="roleId">
                                    <option value="" ${(roleId == null)?string('selected','')}>全部</option>
                                <#list roles as role>
                                    <option value="${role.id }" ${(role.id == roleId)?string('selected','')}>${role.name }</option>
                                </#list>
                                </select>
                            </label>
                            <button type="submit" class="btn btn-sm btn-info"><i class="fa fa-search"></i></button>
                        </form>
                    </div>
                </div>
                <table class="table table-striped table-bordered table-hover">
                    <thead>
                    <tr role="row">
                        <th>序号</th>
                        <th>用户名</th>
                        <th>所属组</th>
                        <th>真实姓名</th>
                        <th>手机号</th>
                        <th>注册时间</th>
                        <th>上次登录时间</th>
                        <th>上次登录IP</th>
                        <th>状态</th>
                        <th>操作</th>
                    </tr>
                    </thead>
                    <tbody>
                    <#list pageInfo.list as v>
                    <tr>
                        <td>${v_index }</td>
                        <td>${v.loginName }</td>
                        <td>
                            <#if v.role != null>
                                ${v.role.deleted?string('<s>','')} ${v.role.name } ${v.role.deleted?string('<s>','')}
                            <#else>
                                --
                            </#if>
                        </td>
                        <td>${v.realname }</td>
                        <td>${v.phone }</td>
                        <td>${v.createTime?string('yyyy-MM-dd HH:mm:ss')}</td>
                        <td>${(v.lastLoginTime?string('yyyy-MM-dd HH:mm:ss'))!'--'}</td>
                        <td>${v.lastLoginIp }</td>
                        <td><i class="fa ${v.deleted?string('fa-times','fa-check')}"></i></td>
                        <td>
                            <#if v.role.flag == 0>
                                <a class="btn btn-sm btn-circle btn-warning" disabled="disabled" title="不可编辑"
                                   data-toggle="tooltip" data-placement="top"><i class="fa fa-pencil-square"></i></a>
                            <#else>
                                <a class="btn btn-sm btn-circle btn-warning" title="编辑" href="/admin/system/adminDetail?adminId=${v.id }"
                                   data-open="modal" data-width="768px" data-height="400px" data-toggle="tooltip" data-placement="top"><i class="fa fa-pencil-square"></i></a>
                                <#if v.deleted>
                                    <a class="btn btn-sm btn-circle btn-warning btn-enable" title="启用" data-id="${v.id }"
                                       data-toggle="tooltip" data-placement="top"><i class="fa fa-check"></i></a>
                                <#else>
                                    <a class="btn btn-sm btn-circle btn-danger btn-enable" title="禁用" data-id="${v.id }"
                                       data-toggle="tooltip" data-placement="top"><i class="fa fa-times"></i></a>
                                </#if>
                                <a class="btn btn-sm btn-circle btn-success" title="编辑管理员权限" data-toggle="tooltip" data-placement="top"
                                   onclick="getModules(${v.id },'${v.loginName }')"><i class="fa fa-legal"></i></a>
                            </#if>
                        </td>
                    </tr>
                    </#list>
                    </tbody>
                </table>
                <div class="row">
                    <div class="col-sm-4">
                        <a class="btn btn-sm btn-info" title="新增管理员" data-open="modal" data-width="800px" data-height="400px"
                           href="/admin/system/adminDetail"><i class="fa fa-plus"></i>新增管理员</a>
                    </div>
                    <div class="col-sm-8">
                        <div class="pagination-ul">
                        <@pagination form="queryForm" data=pageInfo> </@pagination>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>

<div class="layer-win layer-tree">
    <ul id="tree" class="ztree"></ul>
    <input type="hidden" id="aid" value="">
    <a class="btn btn-sm btn-success btn-block btn-rounded" id="btn-permit">对该管理员授权</a>
    <a class="btn-link" onclick="selectAllNodes()" style="position: absolute; top: 10px; right: 10px;">全选/全不选</a>
</div>

<script type="text/javascript" src="/static/library/plugins/zTree/jquery.ztree.all.min.js"></script>
<script type="text/javascript">
    $(function () {
        $(".btn-enable").click(function () {
            var title = $(this).attr("title") == "" ? $(this).attr("data-original-title") : $(this).attr("title");
            layerConfirm('确定要' + title + '该管理员吗', "/admin/system/adminEnable?adminId=" + $(this).attr('data-id'));
        });

        $("#btn-permit").click(function () {
            var str = "";
            var treeObj = $.fn.zTree.getZTreeObj("tree");
            var nodes = treeObj.getCheckedNodes(true);
            for (var i = 0; i < nodes.length; i++) {
                str += nodes[i].id + ",";
            }
            var aid = $("#aid").val();
            if (aid == "") {
                toast(2, "", "未选择管理员");
                return;
            }
            Ajax.ajax({
                url: '/admin/system/permit',
                params: {adminId: aid, params: str},
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
    var zTree = "";
    var setting = {
        check: {
            chkboxType: {"Y": "ps", "N": "ps"},// 勾选checkbox对于父子节点的关联关系
            chkStyle: "checkbox",
            enable: true // 是否复选框
        },
        data: {
            simpleData: {
                enable: true
            }
        }
    };

    function getModules(adminId, adminName) {
        $("#aid").val(adminId);
        Ajax.ajax({
            url: '/admin/system/permits',
            params: {"adminId": adminId},
            success: function (data) {
                zTree = $.fn.zTree.init($("#tree"), setting, data);
                zTree.expandAll(true);
                zindex = openLayerContent('编辑权限【' + adminName + '】', "450px", "500px", $(".layer-tree"));
            }
        });
    }

    var checked = true;
    function selectAllNodes() {
        zTree.checkAllNodes(checked);
        checked = !checked;
    }
</script>
</body>
</html>