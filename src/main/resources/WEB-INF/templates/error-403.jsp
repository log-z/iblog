<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <title>请登陆后再访问</title>
    <meta http-equiv="refresh" content="1;URL=${pageContext.request.contextPath}${refreshPath == null ? "/login" : refreshPath}" />
</head>
<body>
    <h1>请登陆后再访问</h1>
    <p>将1秒后自动跳转到登陆页面...</p>
</body>
</html>
