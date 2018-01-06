function NavToggle() {
    $(".navbar-minimalize").trigger("click")
}

function SmoothlyMenu() {
    $("body").hasClass("mini-navbar") ? $("body").hasClass("fixed-sidebar") ? ($("#side-menu").hide(), setTimeout(function () {
        $("#side-menu").fadeIn(500)
    }, 300)) : $("#side-menu").removeAttr("style") : ($("#side-menu").hide(), setTimeout(function () {
        $("#side-menu").fadeIn(500)
    }, 100))
}

layer.config({moveType: 1}), $(document).ready(function () {
    autoHeight();

    function a() {
        var a = $("body > #wrapper").height() - 61;
        $(".sidebard-panel").css("min-height", a + "px")
    }

    // metisMenu
    $("#side-menu").metisMenu(),
        $(".sidebar-container").slimScroll({height: "100%", railOpacity: .4, wheelStep: 10}),
        $(function () {
            // slimScroll
            $(".sidebar-collapse").slimScroll({height: "100%", railOpacity: .9, alwaysVisible: !1})
        }), $(".navbar-minimalize").click(function () {
        $("body").toggleClass("mini-navbar"), SmoothlyMenu()
    }), a(), $(window).bind("load resize click scroll", function () {
        $("body").hasClass("body-small") || a()
    }), $(window).scroll(function () {
        $(window).scrollTop() > 0 && !$("body").hasClass("fixed-nav") ? $("#right-sidebar").addClass("sidebar-top") : $("#right-sidebar").removeClass("sidebar-top")
    }), $(".full-height-scroll").slimScroll({height: "100%"}), $("#side-menu>li").click(function () {
        $("body").hasClass("mini-navbar") && NavToggle()
    }), $("#side-menu>li li a").click(function () {
        $(window).width() < 769 && NavToggle()
    }), $(".nav-close").click(NavToggle)
}), $(window).bind("load resize", function () {
    $(this).width() < 769 && ($("body").addClass("mini-navbar"), $(".navbar-static-side").fadeIn());
}), $(window).bind("resize", function () {
    autoHeight();
});

function autoHeight() {
    //$("#wrapper").css("height",$(document).height());
    //$("#page-wrapper").css("height",$(document).height());
    $("#content-main").css("height", $(window).height() - 107);
}

$(function () {
    /** *tabs start** */
    function t(t) {
        var e = 0;
        return $(t).each(function () {
            e += $(this).outerWidth(!0)
        }), e
    }

    function e(e) {
        var a = t($(e).prevAll()), i = t($(e).nextAll()), n = t($(".content-tabs").children().not(".J_menuTabs")),
            s = $(".content-tabs").outerWidth(!0) - n, r = 0;
        if ($(".page-tabs-content").outerWidth() < s) r = 0; else if (i <= s - $(e).outerWidth(!0) - $(e).next().outerWidth(!0)) {
            if (s - $(e).next().outerWidth(!0) > i) {
                r = a;
                for (var o = e; r - $(o).outerWidth() > $(".page-tabs-content").outerWidth() - s;) r -= $(o).prev().outerWidth(), o = $(o).prev()
            }
        } else a > s - $(e).outerWidth(!0) - $(e).prev().outerWidth(!0) && (r = a - $(e).prev().outerWidth(!0));
        $(".page-tabs-content").animate({marginLeft: 0 - r + "px"}, "fast")
    }

    function a() {
        var e = Math.abs(parseInt($(".page-tabs-content").css("margin-left"))),
            a = t($(".content-tabs").children().not(".J_menuTabs")), i = $(".content-tabs").outerWidth(!0) - a, n = 0;
        if ($(".page-tabs-content").width() < i) return !1;
        for (var s = $(".J_menuTab:first"), r = 0; r + $(s).outerWidth(!0) <= e;) r += $(s).outerWidth(!0), s = $(s).next();
        if (r = 0, t($(s).prevAll()) > i) {
            for (; r + $(s).outerWidth(!0) < i && s.length > 0;) r += $(s).outerWidth(!0), s = $(s).prev();
            n = t($(s).prevAll())
        }
        $(".page-tabs-content").animate({marginLeft: 0 - n + "px"}, "fast")
    }

    function i() {
        var e = Math.abs(parseInt($(".page-tabs-content").css("margin-left"))),
            a = t($(".content-tabs").children().not(".J_menuTabs")), i = $(".content-tabs").outerWidth(!0) - a, n = 0;
        if ($(".page-tabs-content").width() < i) return !1;
        for (var s = $(".J_menuTab:first"), r = 0; r + $(s).outerWidth(!0) <= e;) r += $(s).outerWidth(!0), s = $(s).next();
        for (r = 0; r + $(s).outerWidth(!0) < i && s.length > 0;) r += $(s).outerWidth(!0), s = $(s).next();
        n = t($(s).prevAll()), n > 0 && $(".page-tabs-content").animate({marginLeft: 0 - n + "px"}, "fast")
    }

    function n() {
        var t = $(this).attr("href"), a = $(this).data("index"), i = $.trim($(this).text()), n = !0;
        if (void 0 == t || 0 == $.trim(t).length) return !1;
        if ($(".J_menuTab").each(function () {
                return $(this).data("id") == t ? ($(this).hasClass("active") || ($(this).addClass("active").siblings(".J_menuTab").removeClass("active"), e(this), $(".J_mainContent .J_iframe").each(function () {
                    return $(this).data("id") == t ? ($(this).show().siblings(".J_iframe").hide(), !1) : void 0
                })), n = !1, !1) : void 0
            }), n) {
            var s = '<a href="javascript:;" class="active J_menuTab" data-id="' + t + '">' + i + ' <i class="b-re fa fa-repeat"></i> <i class="b-cl fa fa-times-circle"></i></a>';
            $(".J_menuTab").removeClass("active");
            var r = '<iframe class="J_iframe" name="iframe' + a + '" width="100%" height="100%" src="' + t + '?v=' + Math.floor(Math.random() * 10000) + '" frameborder="0" data-id="' + t + '" seamless></iframe>';
            $(".J_mainContent").find("iframe.J_iframe").hide().parents(".J_mainContent").append(r);
            var o = layer.load(0, {time: 5 * 1000});
            $(".J_mainContent iframe:visible").load(function () {
                layer.close(o)
            }), $(".J_menuTabs .page-tabs-content").append(s), e($(".J_menuTab.active"))
        }
        return !1
    }

    function s() {
        var t = $(this).parents(".J_menuTab").data("id"), a = $(this).parents(".J_menuTab").width();
        if ($(this).parents(".J_menuTab").hasClass("active")) {
            if ($(this).parents(".J_menuTab").next(".J_menuTab").size()) {
                var i = $(this).parents(".J_menuTab").next(".J_menuTab:eq(0)").data("id");
                $(this).parents(".J_menuTab").next(".J_menuTab:eq(0)").addClass("active"), $(".J_mainContent .J_iframe").each(function () {
                    return $(this).data("id") == i ? ($(this).show().siblings(".J_iframe").hide(), !1) : void 0
                });
                var n = parseInt($(".page-tabs-content").css("margin-left"));
                0 > n && $(".page-tabs-content").animate({marginLeft: n + a + "px"}, "fast"), $(this).parents(".J_menuTab").remove(), $(".J_mainContent .J_iframe").each(function () {
                    return $(this).data("id") == t ? ($(this).remove(), !1) : void 0
                })
            }
            if ($(this).parents(".J_menuTab").prev(".J_menuTab").size()) {
                var i = $(this).parents(".J_menuTab").prev(".J_menuTab:last").data("id");
                $(this).parents(".J_menuTab").prev(".J_menuTab:last").addClass("active"), $(".J_mainContent .J_iframe").each(function () {
                    return $(this).data("id") == i ? ($(this).show().siblings(".J_iframe").hide(), !1) : void 0
                }), $(this).parents(".J_menuTab").remove(), $(".J_mainContent .J_iframe").each(function () {
                    return $(this).data("id") == t ? ($(this).remove(), !1) : void 0
                })
            }
        } else $(this).parents(".J_menuTab").remove(), $(".J_mainContent .J_iframe").each(function () {
            return $(this).data("id") == t ? ($(this).remove(), !1) : void 0
        }), e($(".J_menuTab.active"));
        return !1
    }

    function r() {
        $(".page-tabs-content").children("[data-id]").not(":first").not(".active").each(function () {
            $('.J_iframe[data-id="' + $(this).data("id") + '"]').remove(), $(this).remove()
        }), $(".page-tabs-content").css("margin-left", "0")
    }

    function o() {
        e($(".J_menuTab.active"))
    }

    function d() {
        if (!$(this).hasClass("active")) {
            var t = $(this).data("id");
            $(".J_mainContent .J_iframe").each(function () {
                return $(this).data("id") == t ? ($(this).show().siblings(".J_iframe").hide(), !1) : void 0
            }), $(this).addClass("active").siblings(".J_menuTab").removeClass("active"), e(this)
        }
    }

    function c() {
        var t = $('.J_iframe[data-id="' + $(this).parent("a").data("id") + '"]'), e = t.attr("src"), a = layer.load();
        t.attr("src", e).load(function () {
            layer.close(a)
        })
    }

    $(".J_menuItem").each(function (t) {
        $(this).attr("data-index") || $(this).attr("data-index", t)
    }), $(".J_menuItem").on("click", n), $(".J_menuTabs").on("click", ".J_menuTab i.b-cl", s), $(".J_tabCloseOther").on("click", r), $(".J_tabShowActive").on("click", o), $(".J_menuTabs").on("click", ".J_menuTab", d), $(".J_menuTabs").on("click", ".J_menuTab i.b-re", c), $(".J_tabLeft").on("click", a), $(".J_tabRight").on("click", i), $(".J_tabCloseAll").on("click", function () {
        $(".page-tabs-content").children("[data-id]").not(":first").each(function () {
            $('.J_iframe[data-id="' + $(this).data("id") + '"]').remove(), $(this).remove()
        }), $(".page-tabs-content").children("[data-id]:first").each(function () {
            $('.J_iframe[data-id="' + $(this).data("id") + '"]').show(), $(this).addClass("active")
        }), $(".page-tabs-content").css("margin-left", "0")
    });


    if (!window.applicationCache) {
        showTopTips("您的浏览器不支持HTML5，为提升您的浏览体验，请使用新版Chrome或Firefox浏览器！");
    } else {
        if ((navigator.userAgent.indexOf('MSIE') >= 0)
            && (navigator.userAgent.indexOf('Opera') < 0)
            || navigator.userAgent.indexOf('NET') >= 0) {
            showTopTips("您使用的是IE浏览器，为提升您的浏览体验，请使用新版Chrome或Firefox浏览器！");
        }
    }

    $("body").on("click", "[data-open='modal']", function (event) {
        event.preventDefault();
        var width, height;
        if (typeof($(this).attr("data-width")) == "undefined") {
            width = document.body.clientWidth * 0.85;
            if (width < 768) {
                width = 800
            }
            width = width + "px";
        } else {
            width = $(this).attr("data-width");
        }
        if (typeof($(this).attr("data-height")) == "undefined") {
            height = $(window).height() * 0.9;
            height = height + "px";
        } else {
            height = $(this).attr("data-height");
        }
        var title = $(this).attr("title") == "" ? $(this).attr("data-original-title") : $(this).attr("title");
        openLayerUrl(title, width, height, $(this).attr("href"), true);
    });

    var imgs = {
        "title": "", //相册标题
        "id": '', //相册id
        "start": 0, //初始显示的图片序号，默认0
        "data": [   //相册包含的图片，数组格式
            {
                "alt": "图片",
                "pid": 0, //图片id
                "src": "", //原图地址
                "thumb": "" //缩略图地址
            }
        ]
    }
    $("body").on("click", "[data-open='img']", function (event) {
        event.preventDefault();
        imgs.data[0].src = $(this).attr("src");
        layer.photos({
            photos: imgs,
            anim: 0
        });
    });

    $("body").tooltip({
        placement: "top",
        selector: ".btn-circle",
        container: "html"
    });
    $('.dropdown-toggle').dropdown();

    $("#goBack").click(function () {
        history.back(-1);
    });
});

!(function ($) {
    $.Sort = function (settings) {
        var def = settings.default;
        var ps = $("input[name='pageSort']");
        var qf = $("#queryForm");
        if (ps.length < 1) {
            qf.append('<input type="hidden" value="' + def + '" name="pageSort">');
            ps = $("input[name='pageSort']");
        }

        var arr = ps.val().split(' ');
        if (arr.length == 2) {
            var dom = $('.sort[data-sort="' + arr[0] + '"]');
            if (arr[1] == "DESC") {
                dom.find('i').remove();
                dom.append('<i class="sort-desc"></i>');
            } else {
                dom.find('i').remove();
                dom.append('<i class="sort-asc"></i>');
            }
        }

        $.each($(".sort"), function (k, v) {
            var dom = $(v);
            var ch = dom.children("i");
            var sort = dom.attr('data-sort');
            dom.on("click", function () {
                if (ch.hasClass('sort-desc')) {
                    sort += " ASC";
                    ch.remove();
                    dom.append('<i class="sort-asc"></i>');
                } else {
                    sort += " DESC";
                    ch.remove();
                    dom.append('<i class="sort-desc"></i>');
                }
                ps.val(sort);
                qf.find("button[type='submit']").click();
            });
        });
    }
})(jQuery);