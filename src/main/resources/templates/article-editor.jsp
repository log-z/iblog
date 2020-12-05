<%@ page pageEncoding="utf-8" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>编辑 - ${form.title}</title>
</head>
<body>
    <h1>文章编辑器</h1>
    <form:form modelAttribute="form" method="post" action="/update/${articleId}" enctype="multipart/form-data">
        <label>
            标题：<form:errors path="title" delimiter="; " /><br>
            <form:input path="title" maxlength="40" />
        </label><br>
        <label>
            正文：<form:errors path="content" delimiter="; " /><br>
            <form:textarea path="content" rows="20" maxlength="1000" />
        </label><br>
        <label>
            图片：<br>
            <input name="image" type="file"><br>
        </label><br>
        <input type="submit" value="保存">
    </form:form>
</body>
</html>
