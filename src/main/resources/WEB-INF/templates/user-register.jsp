<%@ page pageEncoding="utf-8" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
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
    <p>${userRegisterForm.userEmail == null ? "" : "注册失败。"}</p>
    <form:form modelAttribute="userRegisterForm" method="post">
        <label>姓名: <form:input path="userName" maxlength="20" required="required" /></label>
        <form:errors path="userName" delimiter="; " />
        <br>
        <label>邮箱: <form:input path="userEmail" type="email" maxlength="320" required="required" /></label>
        <form:errors path="userEmail" delimiter=", " />
        <br>
        <label>密码: <input name="userPassword" type="password" minlength="8" required></label>
        <form:errors path="userPassword" delimiter="; " />
        <br>
        <label>再次输入密码: <input name="userPasswordAgain" type="password" minlength="8" required></label>
        <form:errors path="userPasswordAgain" delimiter="; " />
        <br>
        <input type="submit" value="注册">
        <a href="${pageContext.request.contextPath}/login">[登陆]</a>
    </form:form>
</body>
</html>
