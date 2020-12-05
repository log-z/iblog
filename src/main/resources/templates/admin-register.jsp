<%@ page pageEncoding="utf-8" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>管理员注册</title>
    <script src="${pageContext.request.contextPath}/static/js/crypto-js.js"></script>
    <script src="${pageContext.request.contextPath}/static/js/secure.js"></script>
</head>
<body>
    <h1>管理员注册</h1>
    <p>${adminRegisterForm.adminEmail == null ? "" : "注册失败。"}</p>
    <form:form modelAttribute="adminRegisterForm" method="post">
        <label>姓名: <form:input path="adminName" maxlength="20" required="required" /></label>
        <form:errors path="adminName" delimiter="; " />
        <br>
        <label>邮箱: <form:input path="adminEmail" type="email" maxlength="320" required="required" /></label>
        <form:errors path="adminEmail" delimiter=", " />
        <br>
        <label>密码: <input name="adminPassword" type="password" minlength="8" required></label>
        <form:errors path="adminPassword" delimiter="; " />
        <br>
        <label>再次输入密码: <input name="adminPasswordAgain" type="password" minlength="8" required></label>
        <form:errors path="adminPasswordAgain" delimiter="; " />
        <br>
        <input type="submit" value="注册">
        <a href="${pageContext.request.contextPath}/admin/login">[登陆]</a>
    </form:form>
</body>
</html>
