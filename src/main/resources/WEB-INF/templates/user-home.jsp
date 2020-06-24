<%@ page import="java.util.List" %>
<%@ page import="com.log.blog.entity.Article" %>
<%@ page pageEncoding="utf-8" %>

<%
    boolean editable = false;
    Object o = request.getAttribute("editable");
    if (o instanceof Boolean) editable = (Boolean) o;
%>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>${targetUser.userName}</title>
</head>
<body>
    <h1>${targetUser.userName}</h1>
    <ul>
        <%@ include file="include/inc-header-actions.jsp" %>
    </ul>
    <table>
        <tr>
            <th>标题</th>
            <th>创建时间</th>
            <% if (editable) { %>
            <th>操作</th>
            <% } %>
        </tr>
        <% for(Article article : (List<Article>) request.getAttribute("articles")) { %>
        <tr>
            <td>
                <a href="${pageContext.request.contextPath}/article/<%= article.getArticleId() %>">
                    <%= article.getTitle() %>
                </a>
            </td>
            <td><%= article.getCreateTime() %></td>
            <td>
                <% if (editable) { %>
                <a href="${pageContext.request.contextPath}/edit/<%= article.getArticleId() %>">[编辑]</a>
                <% } %>
            </td>
        </tr>
        <% } %>
    </table>
</body>
</html>
