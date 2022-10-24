<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ page import="com.just.news.entiry.News" %>
<%@ page import="com.just.news.entiry.Topic" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>修改主题--管理后台</title>
<link href="static/css/admin.css" rel="stylesheet" type="text/css" />
</head>
<body>
<%@include file="console_element/top.jsp" %>
<div id="main">
  <%@include file="console_element/left.html" %>
  <%
    News news = (News) request.getAttribute("news");
  %>
  <div id="opt_area">
    <h1 id="opt_type"> 修改新闻： </h1>
    <form action="news.do?opr=update" method="post" enctype="multipart/form-data">
      <p>
        <label> 主题 </label>
        <select name="ntid">
          <option value="1">选择</option>
          <%
            List<Topic> topics = (List<Topic>) request.getAttribute("topics");
            for(Topic topic:topics) {
              if(topic!=null&&topic.getTid()==news.getNtid()) {
          %>
          <option value='<%=topic.getTid()%>' selected> <%=topic.getTname()%> </option>
          <%
            } else {
          %>
          <option value='<%=topic.getTid()%>'> <%=topic.getTname()%> </option>
          <%
            }}
          %>
        </select>
      </p>
      <p>
        <label> 标题 </label>
        <input type="hidden" name="nid" value="<%=news.getNid()%>"/>
        <input name="ntitle" type="text" class="opt_input" value="<%=news.getNtitle()%>" />
      </p>
      <p>
        <label> 作者 </label>
        <input name="nauthor" type="text" class="opt_input"  value="<%=news.getNauthor()%>"/>
      </p>
      <p>
        <label> 摘要 </label>
        <textarea name="nsummary" cols="40" rows="3"><%=news.getNsummary()%></textarea>
      </p>
      <p>
        <label> 内容 </label>
        <textarea name="ncontent" cols="70" rows="10" ><%=news.getNcontent()%></textarea>
      </p>
      <p>
        <label> 上传图片 </label>
        <input name="file" type="file" class="opt_input" />
        <img width="100" height="100" src="http://localhost/static/<%=news.getNpicpath()%>"/>
      </p>
      <input name="action" type="hidden" value="addnews"/>
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
