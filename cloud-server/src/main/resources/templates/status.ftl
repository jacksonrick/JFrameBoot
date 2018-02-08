<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8"/>
    <title>SpringCloud Eureka Monitor</title>
    <link type="text/css" href="/static/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>
<div class="container">
    <#include "header.ftl">

    <h1 style="margin-top: 30px;">System Status</h1>
    <table class="table table-hover table-bordered table-striped">
        <tbody>
        <tr>
            <td>environment</td>
            <td>${environment}</td>
        </tr>
        <tr>
            <td>datacenter</td>
            <td>${datacenter}</td>
        </tr>
        <tr>
            <td>currentTime</td>
            <td>${currentTime}</td>
        </tr>
        <tr>
            <td>upTime</td>
            <td>${upTime}</td>
        </tr>
        </tbody>
    </table>

    <h1 style="margin-top: 30px;">General Info</h1>
    <table class="table table-hover table-bordered table-striped">
        <tbody>
        <tr>
            <td>appname</td>
            <td>${info.appname}</td>
        </tr>
        <tr>
            <td>namespace</td>
            <td>${info.namespace}</td>
        </tr>
        <tr>
            <td>ipAddress</td>
            <td>${info.ipAddress}:${info.nonSecurePort?string.computer} (${info.securePort})</td>
        </tr>
        <tr>
            <td>hostName</td>
            <td>${info.getHostName(true)}</td>
        </tr>
        <tr>
            <td>healthCheckUrl</td>
            <td>${info.healthCheckUrl}</td>
        </tr>
        <tr>
            <td>statusPageUrl</td>
            <td>${info.statusPageUrl}</td>
        </tr>
        <tr>
            <td>metadataMap</td>
            <td>
                <#list info.metadataMap?keys as key>
                    ${key} : ${info.metadataMap[key]!"--"}<br/>
                </#list>
            </td>
        </tr>
        <tr>
            <td>dataCenterName</td>
            <td>${info.dataCenterInfo.name}</td>
        </tr>
        <tr>
            <td>applicationStats</td>
            <td>
                 <#list statusInfo.applicationStats?keys as key>
                     ${key} : ${statusInfo.applicationStats[key]!"--"}<br/>
                 </#list>
            </td>
        </tr>
        <tr>
            <td>generalStats</td>
            <td>
                <#list statusInfo.generalStats?keys as key>
                    ${key} : ${statusInfo.generalStats[key]!"--"}<br/>
                </#list>
            </td>
        </tr>
        </tbody>
    </table>

    <h1 style="margin-top: 30px;">Instance Info</h1>
    <table class="table table-hover table-bordered table-striped">
        <tbody>
        <tr>
            <td>instanceId</td>
            <td>${info.instanceId}</td>
        </tr>
        <tr>
            <td>Instance status</td>
            <td>${instanceInfo.status}</td>
        </tr>
        <tr>
            <td>Instance IP</td>
            <td>${instanceInfo.IPAddr}</td>
        </tr>
        </tbody>
    </table>

    <h1 style="margin-top: 30px;">DS Replicas</h1>
    <table class="table table-hover table-bordered table-striped">
        <tbody>
            <#list nodes as node>
            <tr>
                <td>Server${node_index}</td>
                <td>${node.serviceUrl}</td>
            </tr>
            </#list>
        </tbody>
    </table>

    <#include "footer.ftl">
</div>
</body>
</html>