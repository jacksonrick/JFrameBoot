<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8"/>
    <title>Index</title>
    <link type="text/css" href="/static/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>
<div class="container">
    <#include "header.ftl">

    <h1 style="margin-top: 30px;">Config Server</h1>
    <a class="label label-danger">Refresh all config client</a>
    <a class="label label-primary">Add new key/value</a>

    <table class="table table-hover table-bordered table-striped" style="margin-top: 20px;">
        <thead>
        <tr>
            <th>name</th>
            <th>infos</th>
        </tr>
        </thead>
        <tbody>

        </tbody>
    </table>

    <#include "footer.ftl">
</div>

<script src="/static/js/jquery-2.1.1.min.js"></script>
<script>
    $(function () {

    });
</script>
</body>
</html>