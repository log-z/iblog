<%@ page import="java.util.List" %>
<%@ page import="com.log.blog.entity.Article" %>
<%@ page pageEncoding="utf-8" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>${keyword == null ? "博客主页" : "包含“%s”的文章".formatted(keyword)}</title>
</head>
<body>
    <h1>${keyword == null ? "博客主页" : "包含“%s”的文章".formatted(keyword)}</h1>
    <ul>
        <%@ include file="include/inc-header-actions.jsp" %>
    </ul>
    <form action="${pageContext.request.contextPath}/s">
        <input name="keyword" value="${keyword}">
        <input type="submit" value="搜索">
    </form>
    <table>
        <tr>
            <th>标题</th>
            <th>创建时间</th>
        </tr>
        <% for(Article article : (List<Article>) request.getAttribute("articles")) { %>
        <tr>
            <td>
                <a href="${pageContext.request.contextPath}/article/<%= article.getArticleId() %>">
                    <%= article.getTitle() %>
                </a>
            </td>
            <td><%= article.getCreateTime() %></td>
        </tr>
        <% } %>
    </table>
</body>
</html>
