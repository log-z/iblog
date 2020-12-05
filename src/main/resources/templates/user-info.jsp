<%@ page pageEncoding="utf-8" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>个人信息</title>
    <script src="${pageContext.request.contextPath}/static/js/crypto-js.js"></script>
    <script src="${pageContext.request.contextPath}/static/js/secure.js"></script>
</head>
<body>
    <h1>个人信息</h1>
    <h2>信息</h2>
    <ul>
        <li>ID：${requestScope.currentUser.userId}</li>
        <li>姓名：${requestScope.currentUser.userName}</li>
        <li>邮箱：${requestScope.currentUser.userEmail}</li>
    </ul>
    <h2>管理</h2>
    <ul>
        <li><a href="${pageContext.request.contextPath}/${requestScope.currentUser.userId}/update-name">修改姓名</a></li>
        <li><a href="${pageContext.request.contextPath}/${requestScope.currentUser.userId}/update-password">修改密码</a></li>
    </ul>
</body>
</html>
