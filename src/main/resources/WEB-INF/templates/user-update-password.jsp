<%@ page pageEncoding="utf-8" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>修改用户名</title>
    <script src="${pageContext.request.contextPath}/static/js/crypto-js.js"></script>
    <script src="${pageContext.request.contextPath}/static/js/secure.js"></script>
</head>
<body>
    <h1>修改用户密码</h1>
    <p>${msg}</p>
    <form method="post">
        <label>旧密码: <input name="oldPassword" type="password"></label><br>
        <label>新密码: <input name="newPassword" type="password"></label><br>
        <input type="submit" value="修改">
    </form>
</body>
</html>
