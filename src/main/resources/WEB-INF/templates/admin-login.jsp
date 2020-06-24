<%@ page pageEncoding="utf-8" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>管理员登陆</title>
    <script src="${pageContext.request.contextPath}/static/js/crypto-js.js"></script>
    <script src="${pageContext.request.contextPath}/static/js/secure.js"></script>
</head>
<body>
    <h1>管理员登陆</h1>
    <p>${msg}</p>
    <form method="post">
        <label>邮箱: <input name="adminEmail" type="email"></label><br>
        <label>密码: <input name="adminPassword" type="password"></label><br>
        <input type="submit" value="登陆">
        <a href="${pageContext.request.contextPath}/admin/register">[注册]</a>
    </form>
</body>
</html>
