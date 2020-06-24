<%@ page import="com.log.blog.entity.Article" %>
<%@ page pageEncoding="utf-8" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>${article.title}</title>
</head>
<body>
    <h1>${article.title}</h1>
    <div>${author.userName} | ${article.createTime}</div>
    <p>${article.content}</p>
    <%
        Object article = request.getAttribute("article");
        if (article instanceof Article && ((Article) article).getImage() != null) {
    %>
    <img src="${pageContext.request.contextPath}/article/image/${article.image}" alt="${article.title}">
    <% }%>
</body>
</html>
