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

    <h1 style="margin-top: 30px;">Registered clients</h1>
    <table class="table table-hover table-bordered table-striped">
        <thead>
        <tr>
            <th>name</th>
            <th>infos</th>
        </tr>
        </thead>
        <tbody>
        <#list applications as application>
        <tr>
            <td>${application.name}</td>
            <td>
                <#list application.instances as instance>
                    <div>
                        <span class="badge">${instance.status}</span>
                        <a href="${instance.healthCheckUrl}" target="_blank" title="${instance.appName}">${instance.hostName}
                            - ${instance.IPAddr}:${instance.port?string.computer}</a>
                        <a class="label label-danger" href="/infos?instance=${instance.IPAddr}:${instance.port?string.computer}" target="_self">>_Operate</a>
                    </div>
                </#list>
            </td>
        </tr>
        </#list>
        </tbody>
    </table>

    <#include "footer.ftl">
</div>
</body>
</html>