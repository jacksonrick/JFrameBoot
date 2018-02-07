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
            <h5>地址管理</h5>
        </div>
        <div class="alert alert-warning mb0">
            <p><i class="fa fa-exclamation-circle"></i>右键可编辑或添加地址，不可添加一级；二、三、四级分类可拖动。</p>
            <p><i class="fa fa-exclamation-circle"></i>系统内置的地址选择器使用的是JS文件，<a id="genAddr" href="/admin/system/addrGenc">点我生成</a>。</p>
        </div>
        <div class="ibox-content">
            <div class="row">
                <div class="col-md-8">
                    <ul id="tree" class="ztree"></ul>
                </div>
            </div>
        </div>
    </div>
</div>

<div id="rMenu">
    <ul>
        <li id="m_add" onclick="addTreeNode();"><i class="fa fa-plus"></i>添加地址</li>
        <li id="m_edit" onclick="editTreeNode();"><i class="fa fa-pencil-square"></i>编辑地址</li>
        <li id="m_del" onclick="delTreeNode();"><i class="fa fa-pencil-square"></i>删除地址</li>
    </ul>
</div>

<div class="layer-win">
    <form class="form-horizontal" id="form">
        <div class="form-group">
            <label class="col-sm-4 control-label">ID：</label>
            <div class="col-sm-8">
                <p class="form-control-static" id="addrId"></p>
            </div>
        </div>
        <div class="form-group">
            <label class="col-sm-4 control-label">地址：</label>
            <div class="col-sm-8">
                <input type="text" id="name" name="name" class="form-control" placeholder="地址">
            </div>
        </div>
        <div class="form-group">
            <div class="col-sm-4 col-sm-offset-4">
                <input type="hidden" id="id" name="id" value="">
                <input type="hidden" id="parent" name="parent" value="">
                <input type="hidden" id="level" name="level" value="">
                <button class="btn btn-info" type="button" id="btn-edit">保存</button>
            </div>
        </div>
    </form>
</div>

<script type="text/javascript" src="/static/library/plugins/zTree/jquery.ztree.all.min.js"></script>
<script type="text/javascript">
    var zTree, rMenu;
    var setting = {
        async: {
            enable: true,
            url: "/admin/system/getAddr",
            autoParam: ["id"]
        },
        data: {
            simpleData: { // 数据绑定
                enable: true,
                idKey: "id",
                pIdKey: "pId",
                rootPId: 0
            }
        },
        edit: {
            enable: true,
            showRemoveBtn: false,
            showRenameBtn: false,
            drag: {
                isCopy: false,
                isMove: true,
                prev: false,
                inner: true,
                next: false
            }
        },
        view: { // 关闭双击展开
            dblClickExpand: false
        },
        callback: { // 事件
            onRightClick: zTreeOnRightClick,
            beforeDrag: beforeDrag,
            beforeDrop: beforeDrop,
            onDrop: onDrop
        }
    };

    function beforeDrag(treeId, treeNodes) {
        var l = treeNodes.length;
        if (l != 1) {
            toast(2, "", "一次只能拖动一个分类");
            return false;
        }
        if (treeNodes[0].level == 0) { //一级不可拖动
            return false;
        }
        return true;
    }

    function beforeDrop(treeId, treeNodes, targetNode, moveType) {
        if (treeNodes[0].level == targetNode.level) {
            toast(2, "", "不可拖动到同级分类下");
            return false;
        }
        return targetNode ? targetNode.drop !== false : true;
    }

    /* 拖动编辑 */
    function onDrop(event, treeId, treeNodes, targetNode, moveType, isCopy) {
        if (targetNode) {
            var parentId = targetNode.pId;
            var id = treeNodes[0].id;
            Ajax.ajax({
                url: "/admin/system/addrEdit",
                params: {"parent": parentId, "id": id},
                success: function (data) {
                    if (data.code != 0) {
                        showMsg(data.msg, 2);
                    }
                }
            });
        }
    }

    function zTreeOnRightClick(event, treeId, treeNode) {
        if (treeNode && !treeNode.noR) {
            var level = treeNode.level;
            if (level == 0 || level == 1 || level == 2) {
                // 只新增二三四级分类 [treeNode.pId或treeNode.level]
                $("#m_add").show();
            } else {
                $("#m_add").hide();
            }
            if (level == 0) {
                $("#m_del").hide();
            } else {
                $("#m_del").show();
            }
            zTree.selectNode(treeNode); // 选中该节点
            showRMenu(event.clientX, event.clientY + $(document).scrollTop()); // 显示右键菜单
        }
    }

    function showRMenu(x, y) {
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

    var index;

    function addTreeNode() {
        hideRMenu();
        if (zTree.getSelectedNodes()[0]) { // 获取选择的节点属性
            $("#id").val('');
            $("#name").val('');
            $("#addrId").html(zTree.getSelectedNodes()[0].id);
            $("#parent").val(zTree.getSelectedNodes()[0].id);
            $("#level").val(zTree.getSelectedNodes()[0].level + 1);
            index = openLayerContent('新增地址', '350px', '250px', $(".layer-win"));
        }
    }

    function editTreeNode() {
        hideRMenu();
        if (zTree.getSelectedNodes()[0]) { // 获取选择的节点属性
            $("#id").val(zTree.getSelectedNodes()[0].id);
            $("#name").val(zTree.getSelectedNodes()[0].name);
            $("#addrId").html(zTree.getSelectedNodes()[0].id);
            $("#parent").val('');
            index = openLayerContent('编辑地址', '350px', '250px', $(".layer-win"));
        }
    }

    function delTreeNode() {
        layer.confirm("确定要删除该地址吗", {btn: ['确定', '取消'], icon: 3}, function () {
            var nodes = zTree.getSelectedNodes(),
                    treeNode = nodes[0];
            if (nodes.length == 0) {
                return;
            }
            if (treeNode.level == 0) {
                return;
            }
            hideRMenu();
            zTree.removeNode(treeNode, true);
            Ajax.ajax({
                url: "/admin/system/addrDel",
                params: {id: treeNode.id},
                success: function (data) {
                    if (data.code == 0) {
                        showMsg(data.msg, 1);
                    } else {
                        showMsg(data.msg, 2);
                    }
                }
            });
        });
    };

    $(function () {
        $("#genAddr").click(function () {
            layer.msg('生成中，时间会比较长，生成完毕会自动下载', {
                icon: 16,
                shade: 0.4,
                time: 60000,
                shadeClose: true
            });
        });

        zTree = $.fn.zTree.init($("#tree"), setting);
        rMenu = $("#rMenu");

        $("#btn-edit").click(function () {
            if ($("#name").val() == "") {
                toast(2, "", "名称不能为空");
                return;
            }
            Ajax.ajax({
                url: "/admin/system/addrEdit",
                params: $("#form").serialize(),
                success: function (data) {
                    if (data.code == 0) {
                        showMsg(data.msg, 1, function () {
                            layer.close(index);
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
