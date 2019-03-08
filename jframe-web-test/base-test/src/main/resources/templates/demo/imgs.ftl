<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <title>商品详情页大图轮播</title>
    <link href="/static/library/plugins/imgslide/imgslide.css" rel="stylesheet" type="text/css"/>
</head>

<body>

<div class="imgs-main" id="imgs">
    <div class="imgs-big" id="ban_big">
        <div class="imgs-prev" id="prev"></div>
        <div class="imgs-next" id="next"></div>
        <ul>
            <li><a href="javascript:;"><img src="static/theme/default/images/b1.jpg"/></a></li>
            <li><a href="javascript:;"><img src="static/theme/default/images/b2.jpg"/></a></li>
            <li><a href="javascript:;"><img src="static/theme/default/images/b3.jpg"/></a></li>
            <li><a href="javascript:;"><img src="static/theme/default/images/b1.jpg"/></a></li>
            <li><a href="javascript:;"><img src="static/theme/default/images/b2.jpg"/></a></li>
            <li><a href="javascript:;"><img src="static/theme/default/images/b3.jpg"/></a></li>
        </ul>
    </div>
    <div class="imgs-small">
        <div class="imgs-prev-s" id="prev_s"></div>
        <div class="imgs-num" id="ban_small">
            <ul>
                <li><a href="javascript:;"><img src="static/theme/default/images/b1.jpg"/></a></li>
                <li><a href="javascript:;"><img src="static/theme/default/images/b2.jpg"/></a></li>
                <li><a href="javascript:;"><img src="static/theme/default/images/b3.jpg"/></a></li>
                <li><a href="javascript:;"><img src="static/theme/default/images/b1.jpg"/></a></li>
                <li><a href="javascript:;"><img src="static/theme/default/images/b2.jpg"/></a></li>
                <li><a href="javascript:;"><img src="static/theme/default/images/b3.jpg"/></a></li>
            </ul>
        </div>
        <div class="imgs-next-s" id="next_s"></div>
        <div class="clearfix"></div>
    </div>
</div>
<div class="mhc"></div>
<div class="popup" id="popup">
    <div class="popup-close"><img src="static/theme/default/images/close-big.png" width="40" height="40" title="关闭"></div>
    <div class="popup-cont" id="popup_pic">
        <div class="imgs-prev" id="prev2"></div>
        <div class="imgs-next" id="next2"></div>
        <ul>
            <li><a href="javascript:;"><img src="static/theme/default/images/b1.jpg"/></a></li>
            <li><a href="javascript:;"><img src="static/theme/default/images/b2.jpg"/></a></li>
            <li><a href="javascript:;"><img src="static/theme/default/images/b3.jpg"/></a></li>
            <li><a href="javascript:;"><img src="static/theme/default/images/b1.jpg"/></a></li>
            <li><a href="javascript:;"><img src="static/theme/default/images/b2.jpg"/></a></li>
            <li><a href="javascript:;"><img src="static/theme/default/images/b3.jpg"/></a></li>
        </ul>
    </div>
</div>

<script type="text/javascript" src="/static/library/jquery/jquery-2.1.1.min.js"></script>
<script type="text/javascript" src="/static/library/plugins/imgslide/imgslide.js"></script>
<script type="text/javascript">
    jq('#imgs').imgslide({
        box: "#imgs",
        pic: "#ban_big",
        pnum: "#ban_small",
        prev: "#prev",
        next: "#next",
        prev_btn: "#prev_s",
        next_btn: "#next_s",
        min_picnum: 4,
        pop_up: true,
        pop_div: "#popup",
        pop_pic: "#popup_pic",
        pop_xx: ".popup-close",
        pop_prev: "#prev2",
        pop_next: "#next2",
        mhc: ".mhc"
    });
</script>
</body>
</html>