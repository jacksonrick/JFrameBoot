<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8"/>
    <title>Bootstrap datetimepicker</title>
    <link href="/static/common/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>

<div class="container">
    <label> 注册时间:
        <input type="text" class="form-control input-sm" id="startDate" name="startDate" placeholder="开始时间">
        <input type="text" class="form-control input-sm" id="endDate" name="endDate" placeholder="结束时间">
    </label>
</div>

<script type="text/javascript" src="/static/library/jquery/jquery-2.1.1.min.js"></script>
<script type="text/javascript" src="/static/common/js/global.js"></script>
<script>
    $(function () {
        datePicker('#startDate,#endDate', "yyyy-mm-dd", function () {
            console.log("over!!!")
        });
    });
</script>
</body>
</html>