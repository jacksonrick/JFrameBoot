<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Ajax表单</title>
    <link rel="stylesheet" href="/static/library/plugins/toastr/toastr.min.css">
    <link rel="stylesheet" href="/static/common/css/bootstrap.min.css">
    <link rel="stylesheet" href="/static/library/plugins/switch/switch.min.css">
    <script type="text/javascript" src="/static/library/jquery/jquery-1.8.3.min.js"></script>
    <script type="text/javascript" src="/static/library/plugins/layer/layer.js"></script>
    <script type="text/javascript" src="/static/library/plugins/toastr/toastr.min.js"></script>
    <script type="text/javascript" src="/static/common/js/global.js"></script>
    <script type="text/javascript" src="/static/library/plugins/switch/switch.min.js"></script>
</head>
<body>

<div class="container">
    <form id="defaultForm" method="post" class="form-horizontal">
        <div class="form-group">
            <label class="col-lg-3 control-label">Username</label>
            <div class="col-lg-5">
                <input type="text" class="form-control" name="username" id="username" value="feifei"/>
            </div>
        </div>
        <div class="form-group">
            <label class="col-lg-3 control-label">Createtime</label>
            <div class="col-lg-5">
                <input type="text" class="form-control" name="createtime" value="2016-8-20 15:20:56"/>
            </div>
        </div>
        <div class="form-group">
            <label class="col-lg-3 control-label">Gender</label>
            <div class="col-lg-5">
                <div class="radio">
                    <label>
                        <input type="radio" name="gender" value="1" checked="checked"/> Male
                    </label>
                </div>
                <div class="radio">
                    <label>
                        <input type="radio" name="gender" value="2"/> Female
                    </label>
                </div>
            </div>
        </div>
        <div class="form-group">
            <label class="col-lg-3 control-label">Active</label>
            <div class="col-lg-5">
                <input type="checkbox" class="checkbox" checked name="active"/>
            </div>
        </div>

        <div class="form-group">
            <div class="col-lg-9 col-lg-offset-3">
                <button type="button" class="btn btn-primary" name="signup" id="btn">Sign up</button>
            </div>
        </div>
    </form>
</div>

<button type="button" class="btn btn-primary" onclick="toast(1,'Title','Content')">Toast Success</button>
<button type="button" class="btn btn-primary" onclick="toast(2,'','Content')">Toast Error</button>

<script type="text/javascript">
    $(function () {
        $("#btn").click(function () {
            Ajax.ajax({
                url: 'getJson',
                params: {
                    'json': JSON.stringify($('#defaultForm').serializeObject())
                },
                success: function (data) {
                    console.log(data);
                    toast(1, '', 'Request Success!');
                }
            });
        });

        new Switchery(document.querySelector('.checkbox'));

    });
</script>
</body>
</html>