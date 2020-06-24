<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <title>保存文章${successful ? "成功" : "失败"}</title>
    <meta http-equiv="refresh" content="1;URL=${pageContext.request.contextPath}/${requestScope.currentUser.userId}" />
</head>
<body>
    <h1>保存文章${successful ? "成功" : "失败"}</h1>
    <p>将1秒后自动跳转到个人主页...</p>
</body>
</html>
