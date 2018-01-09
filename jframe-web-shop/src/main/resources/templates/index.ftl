<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta name="renderer" content="webkit">
    <title>商家管理中心</title>
<#include "include.ftl"/>
</head>

<body class="fixed-sidebar full-height-layout gray-bg">
<div id="wrapper">
    <nav class="navbar-default navbar-static-side" role="navigation">
        <div class="nav-close">
            <i class="fa fa-times-circle"></i>
        </div>
        <div class="sidebar-collapse">
            <ul class="nav" id="side-menu">
                <li class="nav-header">
                    <div class="profile-element">
                        <span><img alt="image" class="img-circle img-thumbnail" src="/static/theme/images/avatar.jpg" width="60px"/></span>
                        <a>
							<span class="clear"> <span class="block m-t-xs">
								<strong class="font-bold">Hello! </strong></span>
								<span class="text-muted text-xs block">${user.nickname }</span>
							</span>
                        </a>
                    </div>
                    <div class="logo-element">Shop</div>
                </li>

                <li class="animated fadeInLeft">
                    <a> <i class="fa fa-user f14"></i> <span class="nav-label">模块1</span></a>
                    <ul class="nav nav-second-level">
                        <li class="animated fadeInDown">
                            <a class="J_menuItem" href="/"><i class="fa fa-user"></i>子模块1</a>
                            <a class="J_menuItem" href="/"><i class="fa fa-user"></i>子模块2</a>
                        </li>
                    </ul>
                </li>

            <#--<#list modules as module>
                <#if module.modFlag == 1>
                    <li class="animated fadeInLeft">
                        <a> <i class="${module.modIcon } f14"></i> <span class="nav-label">${module.modName }</span></a>
                        <ul class="nav nav-second-level">
                            <#assign parent = module.id />
                            <#list modules as module2>
                                <#if module2.modFlag ==2 && module2.parentId == parent>
                                    <li class="animated fadeInDown">
                                        <a class="J_menuItem" href="${module2.modPath}"><i class="${module2.modIcon }"></i>${module2.modName }</a>
                                    </li>
                                </#if>
                            </#list>
                        </ul>
                    </li>
                </#if>
            </#list>-->

            </ul>
        </div>
    </nav>
    <div id="page-wrapper" class="gray-bg dashbard-1">
        <div class="row border-bottom">
            <nav class="navbar navbar-static-top mb0" role="navigation">
                <div class="navbar-header">
                    <a class="minimalize-styl-2 btn btn-info navbar-minimalize" title="收起菜单" data-toggle="tooltip" data-placement="bottom"><i class="fa fa-bars"></i></a>
                    <a class="minimalize-styl-2 btn btn-info" id="goBack" title="返回上一级" data-toggle="tooltip" data-placement="bottom"><i class="fa fa-arrow-left"></i></a>
                </div>
                <ul class="nav navbar-top-links navbar-right">
                    <li class="dropdown">
                        <a class="dropdown-toggle" data-toggle="dropdown" title="设置">
                            <i class="fa fa-cogs fa-lg"></i>
                        </a>
                        <ul class="dropdown-menu m-t-xs">
                            <li><a href="javascript:;" id="changePwd"><i class="fa fa-lock"></i>修改密码</a></li>
                            <li><a href="javascript:;" class="logout"><i class="fa fa-sign-out"></i>安全退出</a></li>
                            <li><a href="/"><i class="fa fa-home"></i>网站首页</a></li>
                        </ul>
                    </li>
                </ul>
            </nav>
        </div>
        <div class="row content-tabs">
            <button class="roll-nav roll-left J_tabLeft" title="上一个选项卡">
                <i class="fa fa-backward"></i>
            </button>
            <!-- tabs -->
            <nav class="page-tabs J_menuTabs">
                <div class="page-tabs-content">
                    <a href="javascript:;" class="active J_menuTab" data-id="/shop/home">控制台<i class="b-re fa fa-repeat"></i></a>
                </div>
            </nav>
            <button class="roll-nav roll-right J_tabRight" title="下一个选项卡">
                <i class="fa fa-forward"></i>
            </button>
            <button class="roll-nav roll-right J_tabClose dropdown-toggle" data-toggle="dropdown">
                关闭操作<span class="caret"></span>
            </button>
            <ul class="dropdown-menu dropdown-menu-right mr60">
                <li class="J_tabCloseAll"><a>关闭全部选项卡</a></li>
                <li class="J_tabCloseOther"><a>关闭其他选项卡</a></li>
            </ul>
            <a href="javascript:;" class="roll-nav roll-right J_tabExit logout"><i class="fa fa-sign-out"></i> 退出</a>
        </div>
        <div class="row J_mainContent" id="content-main">
            <iframe class="J_iframe" name="iframe0" width="100%" height="100%" data-id="/shop/home" src="/shop/home" frameborder="0" seamless></iframe>
        </div>
    </div>
</div>

<script type="text/javascript">
    $(function () {
        $("#changePwd").click(function () {
            openLayerUrl('修改密码', "400px", "380px", "/shop/pwd", false);
        });

        $(".logout").click(function () {
            layer.confirm('确定退出吗', {btn: ['确定', '取消'], icon: 3}, function () {
                jumpUrl("/shop/logout");
            });
        });
    });
</script>
</body>
</html>