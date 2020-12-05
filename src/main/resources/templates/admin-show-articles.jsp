<%@ page import="java.util.List" %>
<%@ page import="com.log.blog.entity.Article" %>
<%@ page pageEncoding="utf-8" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>管理文章</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/static/css/components.css">
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

    <div class="pagination" page-size="${range.num}" page-offset="${range.offset}" total="${articlesCount}"></div>

    <!--  scripts  -->
    <script src="${pageContext.request.contextPath}/static/js/basic.js"></script>
    <script src="${pageContext.request.contextPath}/static/js/utils.js"></script>
    <script src="${pageContext.request.contextPath}/static/js/components.js"></script>
</body>
</html>
