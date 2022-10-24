<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ page import="com.just.news.entiry.Topic" %>
<%@ page import="com.just.news.utils.JustObjectUtil" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" charset="UTF-8" />
<title>添加主题--管理后台</title>
<link href="static/css/admin.css" rel="stylesheet" type="text/css" />
</head>
<body>
<%@include file="console_element/top.jsp" %>
<div id="main">
  <%@include file="console_element/left.html" %>
  <div id="opt_area">
    <h1 id="opt_type"> 添加新闻： </h1>
    <form action="news.do?opr=add" method="post" accept-charset="UTF-8" enctype="multipart/form-data">
      <p>
        <label> 主题 </label>
        <select name="ntid">
          <option value="0">选择</option>
          <%
            List<Topic> topics = (List<Topic>) request.getAttribute("topics");
            if(!JustObjectUtil.isListNull(topics)) {
              for(Topic topic: topics) {
          %>
          <option value="<%=topic.getTid() %>"><%=topic.getTname() %></option>
          <%}}%>
        </select>
      </p>
      <p>
        <label> 标题 </label>
        <input name="ntitle" type="text" class="opt_input" />
      </p>
      <p>
        <label> 作者 </label>
        <input name="nauthor" type="text" class="opt_input" />
      </p>
      <p>
        <label> 摘要 </label>
        <textarea name="nsummary" cols="40" rows="3"></textarea>
      </p>
      <p>
        <label> 内容 </label>
        <textarea name="ncontent" cols="70" rows="10"></textarea>
      </p>
      <p>
        <label> 上传图片 </label>
        <input name="file" type="file" class="opt_input" />
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
