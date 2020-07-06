<%@ page import="com.log.blog.entity.User" %>
<%@ page import="java.util.List" %>
<%@ page pageEncoding="utf-8" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>管理用户</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/static/css/components.css">
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

    <div class="pagination" page-size="${range.num}" page-offset="${range.offset}" total="${usersCount}"></div>

    <!--  scripts  -->
    <script src="${pageContext.request.contextPath}/static/js/basic.js"></script>
    <script src="${pageContext.request.contextPath}/static/js/utils.js"></script>
    <script src="${pageContext.request.contextPath}/static/js/components.js"></script>
</body>
</html>
