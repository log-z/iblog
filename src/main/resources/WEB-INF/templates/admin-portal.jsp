<%@ page pageEncoding="utf-8" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>管理员后台</title>
    <script src="${pageContext.request.contextPath}/static/js/crypto-js.js"></script>
    <script src="${pageContext.request.contextPath}/static/js/secure.js"></script>
</head>
<body>
    <h1>管理员后台</h1>
    <h2>信息</h2>
    <ul>
        <li>ID：${requestScope.currentAdmin.adminId}</li>
        <li>姓名：${requestScope.currentAdmin.adminName}</li>
        <li>邮箱：${requestScope.currentAdmin.adminEmail}</li>
    </ul>
    <h2>管理</h2>
    <ul>
        <li><a href="${pageContext.request.contextPath}/admin/users">管理用户</a></li>
        <li><a href="${pageContext.request.contextPath}/admin/articles">管理文章</a></li>
        <li><a href="${pageContext.request.contextPath}/admin/logout">退出后台</a></li>
    </ul>
</body>
</html>
