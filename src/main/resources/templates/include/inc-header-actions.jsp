<%@ page import="com.log.blog.entity.User" %>
<%@ page contentType="text/html;charset=UTF-8" %>

<li><a href="${pageContext.request.contextPath}/">博客主页</a></li>
<% if (request.getAttribute("currentUser") instanceof User) { %>
<li><a href="${pageContext.request.contextPath}/edit/">写文章</a></li>
<li><a href="${pageContext.request.contextPath}/${requestScope.currentUser.userId}">个人主页</a></li>
<li><a href="${pageContext.request.contextPath}/${requestScope.currentUser.userId}/info">个人信息</a></li>
<li><a href="${pageContext.request.contextPath}/logout">退出登陆</a></li>
<% } else { %>
<li><a href="${pageContext.request.contextPath}/login">登陆</a></li>
<li><a href="${pageContext.request.contextPath}/register">注册</a></li>
<% } %>
