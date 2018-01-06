<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <title>地址选择器</title>
</head>

<body>
<div class="container">
    <form action="" class="form-horizontal">
        <div class="form-group">
            <label class="col-sm-2 control-label">Addr：</label>
            <div class="col-md-5">
                <input id="city-picker" class="form-control" readonly type="text" value="" data-toggle="city-picker" placeholder="选择省市区">
            </div>
        </div>
    </form>
</div>
<script src="/static/common/js/global.js"></script>
<script type="text/javascript">
    $(function () {
        cityPicker("#city-picker", "district", "code");
    })
</script>
</body>
</html>