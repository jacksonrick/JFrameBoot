<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <title>自定义JSTL标签</title>
</head>

<body>

<div class="container">
    <h3>分页控件测试【已过时】</h3>
    <form action="pager.do" method="get" id="userListForm">
        <input type="text" class="form-control" name="name" value="${name }" placeholder="用户名"/>
        <input type="text" class="form-control" name="regDate" id="datetimepicker" value="${regDate }" placeholder="注册时间"/>
        <button type="submit" class="btn btn-primary">查询</button>
    </form>
    <table class="table">
        <thead>
        <tr>
            <td>序号</td>
            <td>用户</td>
            <td>手机</td>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${pagination.list }" var="v" varStatus="vs">
            <tr>
                <td>${vs.index+1 }</td>
                <td>${v.username }</td>
                <td>${v.phone }</td>
            </tr>
        </c:forEach>
        </tbody>
    </table>

    <mytag:Pagination pagination="${pagination}" queryForm="userListForm"/>
</div>

<script type="text/javascript" src="/static/library/bootstrap/bootstrap-datetimepicker.min.js"></script>
<script type="text/javascript">
    $(function () {
        datePicker('#datetimepicker', "yyyy-mm-dd");
    })
</script>
</body>
</html>