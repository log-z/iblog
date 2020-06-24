<%@ page import="com.log.blog.entity.User" %>
<%@ page import="java.util.List" %>
<%@ page pageEncoding="utf-8" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>管理用户</title>
</head>
<body>
    <h1>管理用户</h1>
    <table>
        <tr>
            <th>ID</th>
            <th>名称</th>
            <th>邮箱</th>
            <th>操作</th>
        </tr>
        <% for(User user : (List<User>) request.getAttribute("users")) { %>
        <tr>
            <td><%= user.getUserId() %></td>
            <td><%= user.getUserName() %></td>
            <td><%= user.getUserEmail() %></td>
            <td>
                <a href="${pageContext.request.contextPath}/<%= user.getUserId() %>">[查看]</a>
                <a href="${pageContext.request.contextPath}/admin/delete-user?userId=<%= user.getUserId() %>">[删除]</a>
            </td>
        </tr>
        <% } %>
    </table>
</body>
</html>
