<%@ page pageEncoding="utf-8" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>用户登陆</title>
    <script src="${pageContext.request.contextPath}/static/js/crypto-js.js"></script>
    <script src="${pageContext.request.contextPath}/static/js/secure.js"></script>
</head>
<body>
    <h1>用户登陆</h1>
    <p>${msg}</p>
    <form method="post">
        <label>邮箱: <input name="userEmail" type="email"></label><br>
        <label>密码: <input name="userPassword" type="password"></label><br>
        <input type="submit" value="登陆">
        <a href="${pageContext.request.contextPath}/register">[注册]</a>
    </form>
</body>
</html>
