<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <title>地址选择器</title>
    <link rel="stylesheet" href="/static/common/css/bootstrap.min.css" type="text/css">
</head>

<body>
<div class="container">
    <form action="" class="form-horizontal">
        <div class="form-group">
            <label class="col-sm-2 control-label">Addr：</label>
            <div class="col-md-10">
                <input style="width: 100%" id="city-picker" class="form-control" readonly type="text" value="" data-toggle="city-picker" placeholder="选择省市区">
            </div>
        </div>
    </form>
</div>

<input id="tp">

<br>
<button id="btn">BTN</button>
<script src="/static/library/jquery/jquery-2.1.1.min.js"></script>
<script src="/static/common/js/global.js"></script>
<script type="text/javascript">
    $(function () {
        cityPicker("#city-picker", "street", "code");

        $("#btn").click(function () {
            alert($("#city-picker").val())
        });

        datePicker("#tp", "yyyy-mm", function () {
            alert(1)
        });
    });
</script>
</body>
</html>