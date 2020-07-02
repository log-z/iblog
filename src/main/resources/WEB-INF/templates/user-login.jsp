<%@ page pageEncoding="utf-8" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
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
    <p>${user.userEmail == null ? "" : "登陆失败。"}</p>
    <form:form modelAttribute="user" method="post">
        <label>邮箱: <form:input path="userEmail" type="email" maxlength="320" required="required" /></label>
        <form:errors path="userEmail" delimiter="; " />
        <br>
        <label>密码: <input name="userPassword" type="password" minlength="8" required></label>
        <form:errors path="userPassword" delimiter="; " />
        <br>
        <input type="submit" value="登陆">
        <a href="${pageContext.request.contextPath}/register">[注册]</a>
    </form:form>
</body>
</html>
