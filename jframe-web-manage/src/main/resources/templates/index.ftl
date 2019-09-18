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

<body class="body fixed-sidebar full-height-layout gray-bg">
<div id="wrapper">
    <nav class="navbar-default navbar-static-side" role="navigation">
        <div class="nav-close">
            <i class="fa fa-times-circle"></i>
        </div>
        <div class="sidebar-collapse">
            <ul class="nav" id="side-menu">
                <!-- admin -->
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
                    <#if module.flag == 1>
                        <li>
                            <a> <i class="${module.iconName } f14"></i> <span class="nav-label">${module.name }</span></a>
                            <ul class="nav nav-second-level">
                                <#assign parent = module.id />
                                <#list modules as module2>
                                    <#if module2.flag ==2 && module2.parentId == parent>
                                        <li><a class="J_menuItem" href="${module2.action}"><i class="${module2.iconName }"></i>${module2.name }</a></li>
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
                    <!-- 自动化运维 -->
                    <li class="dropdown">
                        <a class="dropdown-toggle tp" data-toggle="dropdown" title="自动化运维">
                            <i class="fa fa-tachometer fa-lg"></i>
                        </a>
                        <ul class="dropdown-menu m-t-xs">
                            <li onclick="newTab('Jenkins部署', '/admin/system/jenkins')"><a><i class="fa fa-sitemap"></i>Jenkins部署</a></li>
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
                            <#if msgs?? && (msgs?size > 0)>
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
                            <#else>
                                <div class="text-muted text-center">暂无消息</div>
                            </#if>

                            <li>
                                <div class="text-center link-block">
                                    <a class="J_menuItem" href="admin/msgList">
                                        <i class="fa fa-envelope"></i> <strong> 查看所有消息</strong>
                                    </a>
                                </div>
                            </li>
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

                    <li class="dropdown">
                        <a class="dropdown-toggle tp right-sidebar-toggle" data-toggle="dropdown" title="主题">
                            <i class="fa fa-tasks fa-lg"></i>
                        </a>
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
            <ul class="dropdown-menu dropdown-menu-right">
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

<div id="right-sidebar" style="display: none;">
    <div class="sidebar-container">
        <ul class="nav nav-tabs navs-3">
            <li class="active">
                <a data-toggle="tab" href="#tab-1">
                    <i class="fa fa-gear"></i> 主题
                </a>
            </li>
            <li class=""><a data-toggle="tab" href="#tab-2">
                通知
            </a>
            </li>
            <li><a data-toggle="tab" href="#tab-3">
                项目进度
            </a>
            </li>
        </ul>

        <div class="tab-content">
            <div id="tab-1" class="tab-pane active">
                <div class="sidebar-title">
                    <h3><i class="fa fa-comments-o"></i> 主题设置</h3>
                    <small><i class="fa fa-tim"></i> 你可以从这里选择和预览主题的布局和样式，这些设置会被保存在本地，下次打开的时候会直接应用这些设置。</small>
                </div>
                <div class="skin-setttings">
                    <div class="title">主题设置</div>
                    <div class="setings-item">
                        <span>收起左侧菜单</span>
                        <div class="switch">
                            <div class="onoffswitch">
                                <input type="checkbox" name="collapsemenu" class="onoffswitch-checkbox" id="collapsemenu">
                                <label class="onoffswitch-label" for="collapsemenu">
                                    <span class="onoffswitch-inner"></span>
                                    <span class="onoffswitch-switch"></span>
                                </label>
                            </div>
                        </div>
                    </div>
                    <div class="title">皮肤选择</div>
                    <div class="setings-item default-skin nb">
                        <span class="skin-name ">
                         <a href="#" class="s-skin-0">默认皮肤</a>
                        </span>
                    </div>
                    <div class="setings-item blue-skin nb">
                        <span class="skin-name ">
                        <a href="#" class="s-skin-1">蓝色主题</a>
                        </span>
                    </div>
                    <div class="setings-item yellow-skin nb">
                        <span class="skin-name ">
                        <a href="#" class="s-skin-3">橙色主题</a>
                        </span>
                    </div>
                </div>
            </div>
            <div id="tab-2" class="tab-pane">
                <div class="sidebar-title">
                    <h3><i class="fa fa-comments-o"></i> 最新通知</h3>
                    <small><i class="fa fa-tim"></i> 您当前有1条未读信息</small>
                </div>

                <div>
                    <div class="sidebar-message">
                        <a href="#">
                            <div class="pull-left text-center">
                                <img alt="image" class="img-circle message-avatar" src="/static/theme/images/avatar.jpg">
                                <div class="m-t-xs">
                                    <i class="fa fa-star text-warning"></i>
                                    <i class="fa fa-star text-warning"></i>
                                </div>
                            </div>
                            <div class="media-body">
                                未读消息。。。
                                <br>
                                <small class="text-muted">今天 14:21</small>
                            </div>
                        </a>
                    </div>
                </div>
            </div>
            <div id="tab-3" class="tab-pane">
                <div class="sidebar-title">
                    <h3><i class="fa fa-cube"></i> 最新任务</h3>
                    <small><i class="fa fa-tim"></i> 您当前有1个任务，0个已完成</small>
                </div>

                <ul class="sidebar-list">
                    <li>
                        <a href="#">
                            <div class="small pull-right m-t-xs">9小时以后</div>
                            <h4>市场调研</h4> 按要求接收教材；
                            <div class="small">已完成： 22%</div>
                            <div class="progress progress-mini">
                                <div style="width: 22%;" class="progress-bar progress-bar-warning"></div>
                            </div>
                            <div class="small text-muted m-t-xs">项目截止： 4:00 - 2015.10.01</div>
                        </a>
                    </li>
                </ul>
            </div>
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