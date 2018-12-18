<table border="1">
    <thead>
    <tr>
        <th>昵称</th>
        <th>手机</th>
        <th>余额</th>
        <th>创建时间</th>
    </tr>
    </thead>
    <tbody>
    <#list list as v>
        <tr>
            <td>${v.nickname}</td>
            <td>${v.phone}</td>
            <td>${v.money}</td>
            <td>${v.create_time}</td>
        </tr>
    </#list>
    </tbody>
</table>
