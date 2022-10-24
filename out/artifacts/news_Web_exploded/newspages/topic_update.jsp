<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ page import="com.just.news.entiry.Topic" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>修改主题--管理后台</title>
<link href="../static/css/admin.css" rel="stylesheet" type="text/css" />
<script type="text/javascript">
	function check(){
		var tname = document.getElementById("tname");

		if(tname.value == ""){
			alert("请输入主题名称！！");
			tname.focus();
			return false;
		}		
		return true;
	}
</script>
</head>
<body>
<%
  Topic topic = (Topic) request.getAttribute("topic");
%>
<%@include file="console_element/top.jsp" %>
<div id="main">
  <%@include file="console_element/left.html" %>
  <div id="opt_area">
    <h1 id="opt_type">
      修改主题：
    </h1>
    <form action="topic.do?opr=update" method="post" onsubmit="return check()">
      <p>
        <label> 主题名称 </label>
        <input type="hidden" name="tid" value="<%=topic.getTid() %>" />
        <input name="tname" type="text" class="opt_input" id="tname" value="<%=topic.getTname() %>"/>
      </p>
      <input name="action" type="hidden" value="addtopic"/>
      <input type="submit" value="提交" class="opt_sub" />
      <input type="reset" value="重置" class="opt_sub" />
    </form>
  </div>
</div>
<div id="footer">
  <%@include file="console_element/bottom.html" %>
</div>
</body>
</html>
