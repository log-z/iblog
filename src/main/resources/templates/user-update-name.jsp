<%@ page pageEncoding="utf-8" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>修改用户名</title>
</head>
<body>
    <h1>用户注册</h1>
    <p>${msg}</p>
    <form:form modelAttribute="form" method="post">
        <label>姓名: <form:input path="name" maxlength="20" required="required" /></label>
        <form:errors path="name" delimiter="; " />
        <br>
        <input type="submit" value="修改">
    </form:form>
</body>
</html>
