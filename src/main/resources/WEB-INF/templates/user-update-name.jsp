<%@ page pageEncoding="utf-8" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>修改用户名</title>
</head>
<body>
    <h1>用户注册</h1>
    <p>${msg}</p>
    <form method="post">
        <label>姓名: <input name="userName" value="${requestScope.currentUser.userName}"></label>
        <input type="submit" value="修改">
    </form>
</body>
</html>
