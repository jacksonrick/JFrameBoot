<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <title></title>
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
                                    <option value="${role.id }" ${(role.id == roleId)?string('selected','')}>${role.roleName }</option>
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
                        <td>${v.adminName }</td>
                        <td>${v.role.isDelete?string('<s>','')} ${v.role.roleName } ${v.role.isDelete?string('<s>','')}</td>
                        <td>${v.adminRealname }</td>
                        <td>${v.adminPhone }</td>
                        <td>${v.adminCreateTime?string('yyyy-MM-dd HH:mm:ss')}</td>
                        <td>${(v.adminLoginTime?string('yyyy-MM-dd HH:mm:ss'))!'--'}</td>
                        <td>${v.adminLoginIp }</td>
                        <td><i class="fa ${v.isDelete?string('fa-times','fa-check')}"></i></td>
                        <td>
                            <#if v.adminFlag == 1>
                                <a class="btn btn-sm btn-circle btn-warning" title="编辑" href="/admin/system/adminDetail?adminId=${v.id }"
                                   data-open="modal" data-width="800px" data-height="400px" data-toggle="tooltip" data-placement="top"><i class="fa fa-pencil-square"></i></a>
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

<script type="text/javascript">
    $(function () {
        $(".btn-enable").click(function () {
            var title = $(this).attr("title") == "" ? $(this).attr("data-original-title") : $(this).attr("title");
            layerConfirm('确定要' + title + '该管理员吗', "/admin/system/adminEnable?adminId=" + $(this).attr('data-id'));
        });
    });
</script>
</body>
</html>