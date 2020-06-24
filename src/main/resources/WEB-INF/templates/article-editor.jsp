<%@ page pageEncoding="utf-8" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>编辑 - ${article.title}</title>
</head>
<body>
    <h1>文章编辑器</h1>
    <form method="post" action="/update/${article.articleId}" enctype="multipart/form-data">
        <label>
            标题：<br>
            <input name="title" value="${article.title}">
        </label><br>
        <label>
            正文：<br>
            <textarea name="content" rows="20">${article.content}</textarea>
        </label><br>
        <label>
            图片：<br>
            <input name="image" type="file"><br>
        </label><br>
        <input type="submit" value="保存">
    </form>
</body>
</html>
