<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <title>日志监控WebSocket</title>
    <style type="text/css">
        body {
            background-color: black;
            color: white;
            font-family: 'Consolas';
        }
    </style>
</head>

<body>
<h1>【已弃用】</h1>

<%
//String filePath = "/opt/webserver/logs/";
String date=request.getParameter("date");
String name="demo";
if(date!=null && !"".equals(date)){
name="demo."+date+".log";
}
String filePath="D:/Developer/Java/apache-tomcat-6.0/logs/"+name;

BufferedReader reader=null;
try{
reader=new BufferedReader(new FileReader(filePath));
String line=null;
while((line=reader.readLine())!=null){
%>
<%=line + "<br/>"%>
<%
}
}catch(Exception e){
throw e;
}finally{
reader.close();
}
%>

</body>
</html>