<%@ page pageEncoding="utf-8" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>用户注册</title>
    <script src="${pageContext.request.contextPath}/static/js/crypto-js.js"></script>
    <script src="${pageContext.request.contextPath}/static/js/secure.js"></script>
</head>
<body>
    <h1>用户注册</h1>
    <p>${msg}</p>
    <form method="post">
        <label>姓名: <input name="userName"></label><br>
        <label>邮箱: <input name="userEmail" type="email"></label><br>
        <label>密码: <input name="userPassword" type="password"></label><br>
        <label>再次输入密码: <input name="userPasswordAgain" type="password"></label><br>
        <input type="submit" value="注册">
        <a href="${pageContext.request.contextPath}/login">[登陆]</a>
    </form>
</body>
</html>
