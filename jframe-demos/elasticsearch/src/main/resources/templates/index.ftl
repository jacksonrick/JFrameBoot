<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Index</title>
    <link rel="stylesheet" href="/static/bootstrap.min.css" type="text/css">
    <script type="text/javascript" src="/static/jquery-2.1.1.min.js"></script>
    <style type="text/css">
        em {
            color: red;
            font-weight: bolder;
        }

        #keywords {
            width: 300px;
            float: left;
        }

        #search {
            width: 100px;
            float: left;
            margin-left: 10px;
        }

        .search-main {
            margin-top: 10px;
        }

        .search-main span{
            margin-right: 3px;
        }
    </style>
</head>
<body>

<div class="container">
    <div style="height: 80px;margin-top: 20px;">
        <input type="text" class="form-control" placeholder="请输入关键字" id="keywords">
        <button type="button" id="search" class="form-control">Search</button>
    </div>

    <div id="content">

    </div>
</div>

<script>
    $(function () {
        $("#search").click(function () {
            var keywords = $("#keywords").val();
            if (keywords == "") {
                return;
            }

            $.ajax({
                type: "POST",
                url: "/searchAll",
                data: {"keyword": keywords},
                dataType: "json",
                success: function (data) {
                    var html = '';
                    $.each(data.data, function (k, v) {
                        html += '<div class="search-main"><div><span>' + v.id + '</span><a href="#">' + v.title + '</a></div><div>' + v.content + '</div></div>';
                    });
                    $("#content").html(html);
                }
            });
        });
    });
</script>

</body>
</html>
