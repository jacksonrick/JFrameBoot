<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <title>HTML5定位</title>
    <script type="text/javascript" src="/static/library/jquery/jquery-1.11.2.min.js"></script>
</head>

<body>

<div id="baidu_geo"></div>

<script type="text/javascript">
    if (navigator.geolocation) {
        navigator.geolocation.getCurrentPosition(locationSuccess,
                locationError, {
                    // 指示浏览器获取高精度的位置，默认为false
                    enableHighAccuracy: true,
                    // 指定获取地理位置的超时时间，默认不限时，单位为毫秒
                    timeout: 5000,
                    // 最长有效期，在重复获取地理位置时，此参数指定多久再次获取位置
                    maximumAge: 3000
                });
    } else {
        alert("Your browser does not support Geolocation!");
    }

    function locationError(error) {
        switch (error.code) {
            case error.TIMEOUT:
                showError("A timeout occured! Please try again!");
                break;
            case error.POSITION_UNAVAILABLE:
                showError('We can\'t detect your location. Sorry!');
                break;
            case error.PERMISSION_DENIED:
                showError('Please allow geolocation access for this to work.');
                break;
            case error.UNKNOWN_ERROR:
                showError('An unknown error occured!');
                break;
        }
    }

    function locationSuccess(position) {
        var coords = position.coords;
        showPosition(coords.latitude, coords.longitude);
    }

    function showPosition(latitude, longitude) {
        var latlon = latitude + ',' + longitude;
        //baidu
        var url = "http://api.map.baidu.com/geocoder/v2/?ak=C93b5178d7a8ebdb830b9b557abce78b&callback=renderReverse&location="
                + latlon + "&output=json&pois=0";
        $.ajax({
            type: "GET",
            dataType: "jsonp",
            url: url,
            beforeSend: function () {
                $("#baidu_geo").html('正在定位...');
            },
            success: function (json) {
                if (json.status == 0) {
                    $("#baidu_geo").html(
                            latitude + ',' + longitude + '<br/>'
                            + json.result.formatted_address);
                }
            },
            error: function (XMLHttpRequest, textStatus, errorThrown) {
                $("#baidu_geo").html(latlon + "地址位置获取失败");
            }
        });
    }

    function showError(msg) {
        alert(msg);
    }
</script>
</body>
</html>