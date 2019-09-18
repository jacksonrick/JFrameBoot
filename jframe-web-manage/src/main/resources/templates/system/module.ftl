<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <title></title>
    <link href="/static/library/plugins/zTree/metroStyle/metroStyle.css" rel="stylesheet">
    <#include "include.ftl"/>
</head>

<body class="gray-bg">
<div class="wrapper-content">
    <div class="ibox float-e-margins">
        <div class="ibox-title">
            <h5>模块管理</h5>
        </div>
        <div class="alert alert-warning mb0">
            <p><i class="fa fa-exclamation-circle"></i>右键可编辑或添加模块，一级模块不可操作。该功能仅开发人员使用。</p>
        </div>
        <div class="ibox-content">
            <div class="row">
                <div class="col-md-6">
                    <ul id="tree" class="ztree"></ul>
                </div>
                <div class="col-md-6" style="position: fixed;right: 10px;top: 140px;">
                    <div>
                        <div class="ibox-title">
                            <h5>添加/编辑模块</h5>
                        </div>
                        <div class="ibox-content">
                            <form class="form-horizontal" id="form">
                                <div class="form-group">
                                    <label class="col-sm-3 control-label">父模块：</label>
                                    <div class="col-sm-9">
                                        <p class="form-control-static" id="pname">--</p>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label class="col-sm-3 control-label">模块名：</label>
                                    <div class="col-sm-9">
                                        <input type="text" id="modName" name="name" class="form-control" placeholder="模块名">
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label class="col-sm-3 control-label">Action：</label>
                                    <div class="col-sm-9">
                                        <input type="text" id="action" name="action" class="form-control" placeholder="Action">
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label class="col-sm-3 control-label">图标名：</label>
                                    <div class="col-sm-9">
                                        <input type="text" id="icon" name="icon" class="form-control" placeholder="图标名">
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label class="col-sm-3 control-label">排序：</label>
                                    <div class="col-sm-9">
                                        <input type="text" id="sort" name="sort" class="form-control" placeholder="排序">
                                    </div>
                                </div>
                                <div class="form-group">
                                    <div class="col-sm-12 col-sm-offset-3">
                                        <input type="hidden" id="moduleId" name="moduleId" value>
                                        <input type="hidden" id="parentId" name="parentId" value>
                                        <input type="hidden" id="flag" name="flag" value>
                                        <button class="btn btn-info" type="button" id="btn-edit">保存</button>
                                    </div>
                                </div>
                            </form>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>

<div id="rMenu">
    <ul>
        <li id="m_add" onclick="addTreeNode();"><i class="fa fa-pencil-square"></i>添加模块</li>
        <li id="m_edit" onclick="editTreeNode();"><i class="fa fa-pencil-square"></i>编辑模块</li>
        <li id="m_del" onclick="delTreeNode();"><i class="fa fa-pencil-square"></i>删除模块</li>
    </ul>
</div>

<script type="text/javascript" src="/static/library/plugins/zTree/jquery.ztree.all.min.js"></script>
<script type="text/javascript">
    var zTree, rMenu;
    var setting = {
        data: {
            simpleData: { //数据绑定
                enable: true,
                idKey: "id",
                pIdKey: "pId",
                rootPId: 0,
                flag: "flag", // 新增属性
                action: "action",
                icon: "icon",
                sort: "sort"
            }
        },
        view: { // 关闭双击展开
            dblClickExpand: false
        },
        callback: { // 事件
            // onClick: zTreeOnClick,
            onRightClick: zTreeOnRightClick
        }
    };

    function zTreeOnRightClick(event, treeId, treeNode) {
        if (treeNode && !treeNode.noR) {
            if (treeNode.flag == 1) {
                // 一级模块不可编辑 [或treeNode.level]
                $("#m_edit,#m_del").hide();
                $("#m_add").show();
            }
            if (treeNode.flag == 2) {
                $("#m_edit,#m_add,#m_del").show();
            }
            if (treeNode.flag == 3) {
                $("#m_edit,#m_del").show();
                $("#m_add").hide();
            }
            zTree.selectNode(treeNode); // 选中该节点
            showRMenu("root", event.clientX, event.clientY + $(document).scrollTop()); // 显示右键菜单
        }
    }

    function showRMenu(type, x, y) {
        $("#rMenu ul").show();
        rMenu.css({"top": y + "px", "left": x + "px", "visibility": "visible"});
        $("body").bind("mousedown", onBodyMouseDown); // body事件
    }

    function hideRMenu() {
        if (rMenu) rMenu.css({"visibility": "hidden"});
        $("body").unbind("mousedown", onBodyMouseDown);
    }

    function onBodyMouseDown(event) {
        if (!(event.target.id == "rMenu" || $(event.target).parents("#rMenu").length > 0)) {
            rMenu.css({"visibility": "hidden"});
        }
    }

    function addTreeNode() {
        hideRMenu();
        if (zTree.getSelectedNodes()[0]) { // 获取选择的节点属性
            $("#pname").html(zTree.getSelectedNodes()[0].name);
            $("#moduleId").val('');
            $("#parentId").val(zTree.getSelectedNodes()[0].id);
            $("#modName").val('');
            $("#action").val('');
            $("#icon").val('');
            $("#sort").val('');
            $("#flag").val(zTree.getSelectedNodes()[0].flag + 1);
        }
    }

    function editTreeNode() {
        hideRMenu();
        if (zTree.getSelectedNodes()[0]) { //获取选择的节点属性
            $("#pname").html('--');
            $("#moduleId").val(zTree.getSelectedNodes()[0].id);
            $("#parentId").val('');
            $("#modName").val(zTree.getSelectedNodes()[0].name);
            $("#action").val(zTree.getSelectedNodes()[0].action);
            $("#icon").val(zTree.getSelectedNodes()[0].icon);
            $("#sort").val(zTree.getSelectedNodes()[0].sort);
            $("#flag").val('');
        }
    }

    function delTreeNode() {
        hideRMenu();
        if (zTree.getSelectedNodes()[0]) { //获取选择的节点属性
            var moduleId = zTree.getSelectedNodes()[0].id;
            layerConfirm('确定要删除该模块吗', "/admin/system/moduleDel?moduleId=" + moduleId, function () {
                getModule();
            });
        }
    }

    function getModule() {
        Ajax.ajax({
            url: "/admin/system/getAllModule",
            params: null,
            success: function (data) {
                zTree = $.fn.zTree.init($("#tree"), setting, data);
                zTree.expandAll(true);
                rMenu = $("#rMenu");
            }
        });
    }

    $(function () {
        getModule();

        $("#btn-edit").click(function () {
            if (checkString("#modName", 2, 8)) {
                toast(2, "", "模块名长度2-8");
                return;
            }
            if (checkString("#action", 5, 50)) {
                toast(2, "", "action长度5-50");
                return;
            }
            Ajax.ajax({
                url: "/admin/system/moduleEdit",
                params: $("#form").serialize(),
                success: function (data) {
                    if (data.code == 0) {
                        showMsg(data.msg, 1, function () {
                            // location.reload();
                            getModule();
                        });
                    } else {
                        showMsg(data.msg, 2);
                    }
                }
            });
        });
    });
</script>
</body>
</html>