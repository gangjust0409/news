<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ page import="com.just.news.constant.UserConstant" %>
<%@ page import="com.just.news.entiry.User" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<base href="<%=basePath%>" />
<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
<meta http-equiv="description" content="This is my page">
<title>新闻发布系统管理后台</title>
<link href=".././static/css/admin.css" rel="stylesheet" type="text/css" />
<div id="header">
  <div id="welcome">欢迎使用新闻管理系统！</div>
  <div id="nav">
    <div id="logo"><img src=".././static/images/logo.jpg" alt="新闻中国" /></div>
    <div id="a_b01"><img src=".././static/images/a_b01.gif" alt="" /></div>
  </div>
</div>
<div id="admin_bar">
  <%
    User user = (User) request.getSession().getAttribute(UserConstant.CURRENT_LOGIN_USER);
  %>
  <div id="status">管理员：<%=user.getUname() %> 登录  &#160;&#160;&#160;&#160; <a href="login.do?opr=logout">login out</a></div>
  <div id="channel"> </div>
</div>
</head>
<body>
