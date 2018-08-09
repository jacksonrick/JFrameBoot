<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta name="renderer" content="webkit">
    <title>后台管理中心</title>
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
								<strong class="font-bold">Hello! ${admin.adminName }</strong></span>
								<span class="text-muted text-xs block">${admin.role.roleName }</span>
							</span>
                        </a>
                    </div>
                    <div class="logo-element">Admin</div>
                </li>

                <#list modules as module>
                    <#if module.modFlag == 1>
                        <li class="animated fadeInLeft">
                            <a> <i class="${module.modIcon } f14"></i> <span class="nav-label">${module.modName }</span></a>
                            <ul class="nav nav-second-level">
                                <#assign parent = module.id />
                                <#list modules as module2>
                                    <#if module2.modFlag ==2 && module2.parentId == parent>
                                        <li class="animated fadeInDown">
                                            <a class="J_menuItem" href="${module2.modPath}"><i class="${module2.modIcon }"></i>${module2.modName }
                                            </a>
                                        </li>
                                    </#if>
                                </#list>
                            </ul>
                        </li>
                    </#if>
                </#list>

            </ul>
        </div>
    </nav>
    <div id="page-wrapper" class="gray-bg dashbard-1">
        <div class="row border-bottom">
            <nav class="navbar navbar-static-top mb0" role="navigation">
                <div class="navbar-header">
                    <a class="minimalize-styl-2 btn btn-default navbar-minimalize tp" title="收起菜单"><i class="fa fa-bars"></i></a>
                    <a class="minimalize-styl-2 btn btn-default tp" id="goBack" title="返回上一级"><i class="fa fa-arrow-left"></i></a>
                    <a class="minimalize-styl-2 btn btn-default tp" id="fullscreen" title="全屏"><i class="fa fa-tv"></i></a>
                <#--<a class="navbar-chat minimalize-styl-2 btn btn-warning" title="即时通讯" data-toggle="tooltip" data-placement="bottom"><i class="fa fa-comments"></i></a>-->
                </div>
                <ul class="nav navbar-top-links navbar-right">
                    <!-- 自动化部署 -->
                    <#if admin.role.roleFlag == 0>
                        <li class="dropdown">
                            <a class="tp" onclick="newTab('自动化部署', '/admin/system/jenkins')" title="自动化部署">
                                <i class="fa fa-sitemap fa-lg"></i>
                            </a>
                        </li>
                    </#if>
                    <!-- 监控 -->
                    <li class="dropdown">
                        <a class="dropdown-toggle tp" data-toggle="dropdown" title="监控">
                            <i class="fa fa-server fa-lg"></i>
                        </a>
                        <ul class="dropdown-menu m-t-xs">
                            <li onclick="newTab('云服务器监控', 'http://127.0.0.1:10000')"><a><i class="fa fa-server"></i>云服务器监控</a></li>
                            <li onclick="newTab('数据源监控', '/druid/index.html')"><a><i class="fa fa-database"></i>数据源监控</a></li>
                            <li onclick="newTab('Redis Cachecloud', 'http://192.168.24.200:20010')"><a><i class="fa fa-cloud"></i>Redis
                                Cachecloud</a></li>
                            <li onclick="newTab('ELK', 'http://192.168.24.200:5601')"><a><i class="fa fa-history"></i>ELK</a></li>
                        </ul>
                    </li>

                    <!-- 系统消息 -->
                    <li class="dropdown">
                        <a class="dropdown-toggle count-info tp" data-toggle="dropdown" title="系统消息">
                            <i class="fa fa-envelope fa-lg"></i> <span class="badge badge-info">${size }</span>
                        </a>
                        <ul class="dropdown-menu dropdown-messages">
                            <#list msgs as m>
                                <li class="m-t-xs">
                                    <div class="dropdown-messages-box">
                                        <a class="pull-left">
                                            <img alt="image" class="img-circle img-thumbnail" src="/static/theme/images/system.png">
                                        </a>
                                        <div class="media-body">
                                            <strong>系统</strong> ${m.content }
                                            <br>
                                            <small class="text-muted">${m.createTime?string('yyyy-MM-dd HH:mm:ss') }</small>
                                        </div>
                                    </div>
                                </li>
                                <li class="divider"></li>
                            </#list>
                        <#--<li>
                            <div class="text-center link-block">
                                <a class="J_menuItem" href="admin/msgList">
                                    <i class="fa fa-envelope"></i> <strong> 查看所有消息</strong>
                                </a>
                            </div>
                        </li>-->
                        </ul>
                    </li>
                    <!-- 更新日志 -->
                    <#--<li class="dropdown">
                        <a class="dropdown-toggle tp" data-toggle="dropdown" title="更新日志">
                            <i class="fa fa-cloud-upload fa-lg"></i>
                        </a>
                        <div class="dropdown-menu m-t-xs">
                            <div class="float-e-margins">
                                <div class="ibox-title">
                                    <h5>
                                        更新日志
                                        <small>&nbsp;当前版本:${version}</small>
                                    </h5>
                                </div>
                                <div class="ibox-content no-padding">
                                    <div class="panel-body" id="version">
                                        <div class="panel-group">${upgrade}</div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </li>-->
                    <!-- 设置 -->
                    <li class="dropdown">
                        <a class="dropdown-toggle tp" data-toggle="dropdown" title="设置">
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
                    <a href="javascript:;" class="active J_menuTab" data-id="/admin/home">控制台<i class="b-re fa fa-repeat"></i></a>
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
        <#--<a href="javascript:;" class="roll-nav roll-right J_tabExit logout"><i class="fa fa-sign-out"></i> 退出</a>-->
        </div>
        <div class="row J_mainContent" id="content-main">
            <iframe class="J_iframe" name="iframe_home" width="100%" height="100%" data-id="/admin/home" src="/admin/home" frameborder="0" seamless></iframe>
        </div>
    </div>
</div>

<script type="text/javascript">
    $(function () {
        $("#changePwd").click(function () {
            openLayerUrl('修改密码', "400px", "380px", "/admin/pwd", false);
        });

        $(".logout").click(function () {
            layer.confirm('确定退出吗', {btn: ['确定', '取消'], icon: 3}, function () {
                jumpUrl("/admin/logout");
            });
        });

        $(".navbar-header,.nav").tooltip({
            placement: "bottom",
            selector: ".tp",
            container: "html"
        });

        $("#fullscreen").click(function () {
            var docElm = document.documentElement;
            if (docElm.requestFullscreen) {
                docElm.requestFullscreen();
            }
            else if (docElm.mozRequestFullScreen) {
                docElm.mozRequestFullScreen();
            }
            else if (docElm.webkitRequestFullScreen) {
                docElm.webkitRequestFullScreen();
            }
            else if (elem.msRequestFullscreen) {
                elem.msRequestFullscreen();
            }
        });
    });
</script>
</body>
</html>