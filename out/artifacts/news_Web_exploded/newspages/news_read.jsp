<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ page import="com.just.news.entiry.News" %>
<%@ page import="com.just.news.entiry.Comments" %>
<%@ page import="com.just.news.utils.JustObjectUtil" %>
<%@ page import="java.text.SimpleDateFormat" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>新闻中国</title>
<link href="http://localhost/static/css/read.css" rel="stylesheet" type="text/css" />
<script type="text/javascript">
	  		function check(){
	  			var cauthor = document.getElementById("cauthor");
	  			var content = document.getElementById("ccontent");
	  			if(cauthor.value == ""){
	  				alert("用户名不能为空！！");
	  				return false;
	  			}else if(content.value == ""){
	  				alert("评论内容不能为空！！");
	  				return false;
	  			}
	  			return true;
	  		}
	  	</script>
</head>
<%
  News news = (News) request.getAttribute("news");

%>
<body>
<div id="header">
  <div id="top_login">
    <form method="post" action="login.do?opr=login" style="display: inline-block;">
      <label> 登录名 </label>
      <input type="text" id="uname" value="" name="uname" class="login_input" />
      <label> 密&#160;&#160;码 </label>
      <input type="password" id="upwd" value="" name="upwd" class="login_input" />
      <input type="submit" class="login_sub" value="登录"/>
    </form>
    <label id="error"> </label>
    <a href="http://localhost:8080/news/index.jsp" class="login_link">返回首页</a> <img src="static/images/friend_logo.gif" alt="Google" id="friend_logo" /> </div>
  <div id="nav">
    <div id="logo"> <img src="static/images/logo.jpg" alt="新闻中国" /> </div>
    <div id="a_b01"> <img src="static/images/a_b01.gif" alt="" /> </div>
    <!--mainnav end-->
  </div>
</div>
<div id="container">
  <jsp:include page="../newspages/console_element/side.jsp"/>
  <div class="main">
    <div class="class_type"> <img src="static/images/class_type.gif" alt="新闻中心" /> </div>
    <div class="content" style="width: 953px">
      <ul class="classlist">
        <table width="80%" align="center">
          <tr width="100%">
            <td colspan="2" align="center"><%=news.getNtitle() %></td>
          </tr>
          <tr>
            <td colspan="2"><hr />
            </td>
          </tr>
          <tr>
            <td align="center">2009-10-28 01:03:51.0</td>
            <td align="left"><%=news.getNauthor() %> </td>
          </tr>
          <tr>
            <td colspan="2" align="center"></td>
          </tr>
          <tr>
            <td colspan="2"> <%=news.getNcontent() %></tr>
          <tr>
            <td colspan="2"><hr />
            </td>
          </tr>
        </table>
      </ul>
      <ul class="classlist">
        <table width="80%" align="center">
            <%
                List<Comments> comments = (List<Comments>) request.getAttribute("comments");
                System.out.println(comments);
                if(JustObjectUtil.isListNull(comments)) {
            %>
          <td colspan="6"> 暂无评论！ </td>
            <%}%>
          <tr>
            <td colspan="6"><hr />
            </td>
          </tr>
        </table>
      </ul>
      <ul class="classlist">
        <form action="comments.do?opr=add" method="post" onsubmit="return check()">
          <table width="80%" align="center">
            <tr>
              <td> 评 论 </td>
            </tr>
              <tr>
                  <td>评论人</td>
                  <td>评论内容</td>
                  <td>评论日期</td>
                  <td>IP</td>
              </tr>
              <%
                  SimpleDateFormat sdf = new SimpleDateFormat("yyyy-mm-dd hh:MM:ss");
                  for (Comments comment : comments) {
              %>
              <tr style="line-height: 25px;">
                  <td  style="font-size: 12px;font-family: '宋体';"> <%=comment.getCauthor() %></td>
                  <td><%=comment.getCcontent()%></td>
                  <td><%=sdf.format(comment.getCdate())%></td>
                  <td><%=comment.getCip()%></td>
              </tr>
              <%}%>
            <tr>
              <td> 用户名： </td>
              <td><input id="cauthor" name="cauthor" value="这家伙很懒什么也没留下"/>
                  <input type="hidden" name="nid" value="<%=news.getNid()%>">
                IP：
                <input name="cip" value="127.0.0.1"
											readonly="readonly"/>
              </td>
            </tr>
            <tr>
              <td colspan="2"><textarea name="ccontent" cols="70" rows="10"></textarea>
              </td>
            </tr>
            <td><input name="submit" value="发  表" type="submit"/>
              </td>
          </table>
        </form>
      </ul>
    </div>
  </div>
</div>
<%@include file="../index-elements/index_bottom.html"%>
</body>
<script type="text/javascript" src="static/js/jquery-3.6.1.min.js"></script>
<script type="text/javascript" src="static/js/es6-ajax-promise.js"></script>
</html>
