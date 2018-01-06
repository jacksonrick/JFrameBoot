<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <title>添加购物车特效</title>
    <style>
        * {
            padding: 0px;
            margin: 0px;
        }

        .header {
            height: 30px;
            border-bottom: solid 1px #CCCCCC;
        }

        .cart-con {
            float: right;
            margin-right: 300px;
            width: 100px;
            height: 30px;
            background-color: #00C1B3;
            text-align: center;
            line-height: 30px;
        }

        .item {
            width: 200px;
            height: 300px;
            border: solid 1px #CCCCCC;
            position: relative;
            margin: 300px auto;
        }

        .item-img {
            width: 200px;
            height: 160px;
        }

        .item-add {
            position: absolute;
            right: 10px;
            bottom: 10px;
            width: 50px;
            height: 50px;
        }
    </style>
</head>

<body>
<div class="header">
    <div class="cart-con">0</div>
</div>

<div class="item">
    <img class="item-img" src="/static/upload/images/201603/big_img10.jpg">
    <img class="item-add" src="/static/upload/images/201603/2c02bcq11448.189316.jpg">
</div>


<script src="/static/library/jquery/jquery-1.11.2.min.js"></script>
<script src="/static/library/jquery/jquery.easing.min.js"></script>
<script>
    function moveBox(obj) {
        var t = $(document).scrollTop();
        var img1 = $(obj).prev(".item-img");
        var img2 = img1.clone().css('opacity', '0.7');
        img2.css({
            'z-index': 999,
            'position': 'absolute',
            'top': img1.offset().top + 'px',
            'left': img1.offset().left + 'px',
        });
        $('body').append(img2);
        img2.animate({
            top: t + 'px',
            left: $('.cart-con').offset().left + 'px',
            width: '30px',
            height: '30px'
        }, {
            easing: 'easeInOutQuad',
            duration: 500
        }, "slow").fadeTo(0, 0.1).hide(0, function () {
            img2.remove();
        });

        $(".cart-con").text(parseInt($(".cart-con").text()) + 1);
    }

    $(".item-add").click(function () {
        var t = $(this);
        moveBox(t);
    });

</script>
</body>
</html>