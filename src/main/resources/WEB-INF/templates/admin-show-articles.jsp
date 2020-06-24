<%@ page import="java.util.List" %>
<%@ page import="com.log.blog.entity.Article" %>
<%@ page pageEncoding="utf-8" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>管理文章</title>
</head>
<body>
    <h1>管理文章</h1>
    <table>
        <tr>
            <th>ID</th>
            <th>标题</th>
            <th>作者ID</th>
            <th>创建时间</th>
        </tr>
        <% for(Article article : (List<Article>) request.getAttribute("articles")) { %>
        <tr>
            <td><%= article.getArticleId() %></td>
            <td><%= article.getTitle() %></td>
            <td><%= article.getAuthorId() %></td>
            <td><%= article.getCreateTime() %></td>
            <td>
                <a href="${pageContext.request.contextPath}/article/<%= article.getArticleId() %>">[查看]</a>
                <a href="${pageContext.request.contextPath}/admin/delete-article?articleId=<%= article.getArticleId() %>">[删除]</a>
            </td>
        </tr>
        <% } %>
    </table>
</body>
</html>
