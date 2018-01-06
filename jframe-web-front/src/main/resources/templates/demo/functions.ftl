<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <title>Freemarker标签和自定义标签</title>
</head>

<body>
格式化手机号码：${convert("phone","17730215423")}
<br>格式化身份证号：${convert("idcard","6215581302002881824")}

<#assign price = 38.921/>
<br>格式化金额：${price} | ${price?string} | ${price?string.number} | #{price;m2}
<#assign times = "2017-12-10 10:10:59"?datetime("yyyy-MM-dd HH:mm") />
<br>格式化时间：${times?string("yyyy-MM-dd HH:mm:ss zzzz")} | ${times?string("yyyy-MM-dd")}

<#assign foo = true />
<br>布尔型：${foo?string("是","否")}
<br>布尔型：${foo2!true?string("是","否")}

<#assign name = "hello" />
<br>内建函数：${name?upper_case}

<br>空判断：${(something?string('yyyy-MM-dd HH:mm:ss'))!'--'} |
${(id == null)?string('null','')}

<br><#--<#assign foo3 = true />-->
<#if foo3>
    是
<#else>
    否
</#if>
</body>
</html>