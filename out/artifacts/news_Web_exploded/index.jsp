<%@ page language="java" import="java.util.*" pageEncoding="utf-8" %>
<%@ page import="com.just.news.utils.JustObjectUtil" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <title>新闻中国</title>
    <link href="static/css/main.css" rel="stylesheet" type="text/css"/>
    <script language="javascript">
        function check() {
            var login_username = document.getElementById("uname");
            var login_password = document.getElementById("upwd");
            if (login_username.value == "") {
                alert("用户名不能为空！请重新填入！");
                login_username.focus();
                return false;
            } else if (login_password.value == "") {
                alert("密码不能为空！请重新填入！");
                login_password.focus();
                return false;
            }
            return true;
        }

        function focusOnLogin() {
            var login_username = document.getElementById("uname");
            login_username.focus();
        }
    </script>
</head>
<body onload="focusOnLogin()">
<div id="header">
    <%
        request.getSession().removeAttribute("msg");
        String msg = (String) request.getSession().getAttribute("msg");
        if (!JustObjectUtil.isEmpty(msg)) {

    %>>
    <div style="color: red"><%= msg%>
    </div>

    <%
        }
    %>
    <div id="top_login">
        <form action="login.do?opr=login" method="post" onsubmit="return check()">
            <label> 登录名 </label>
            <input type="text" name="uname" id="uname" value="" class="login_input"/>
            <label> 密&#160;&#160;码 </label>
            <input type="password" name="upwd" id="upwd" value="" class="login_input"/>
            <input type="submit" class="login_sub" value="登录"/>
            <label id="error"> </label>
            <img src="static/images/friend_logo.gif" alt="Google" id="friend_logo"/>
        </form>
    </div>
    <div id="nav">
        <div id="logo"><img src="static/images/logo.jpg" alt="新闻中国"/></div>
        <div id="a_b01"><img src="static/images/a_b01.gif" alt=""/></div>
        <!--mainnav end-->
    </div>
</div>
<div id="container">
    <jsp:include page="newspages/console_element/side.jsp"/>
    <div class="main">
        <div class="class_type"><img src="static/images/class_type.gif" alt="新闻中心"/></div>
        <div class="content">
            <ul class="class_date" id="topic_box"></ul>
            <ul class="classlist" id="news_list_box"></ul>
        </div>
        <div class="picnews">
            <ul>
                <li><a href="#"><img src="static/images/Picture1.jpg" width="249" alt=""/> </a><a href="#">幻想中穿越时空</a>
                </li>
                <li><a href="#"><img src="static/images/Picture2.jpg" width="249" alt=""/> </a><a href="#">国庆多变的发型</a>
                </li>
                <li><a href="#"><img src="static/images/Picture3.jpg" width="249" alt=""/> </a><a href="#">新技术照亮都市</a>
                </li>
                <li><a href="#"><img src="static/images/Picture4.jpg" width="249" alt=""/> </a><a href="#">群星闪耀红地毯</a>
                </li>
            </ul>
        </div>
    </div>
</div>
<%@include file="index-elements/index_bottom.html" %>
</body>
<script type="text/javascript" src="./static/js/jquery-3.6.1.min.js"></script>
<script type="text/javascript" src="./static/js/es6-ajax-promise.js"></script>
<script type="text/javascript">
        //主题
    /**
     * <li id='class_month'><a href='#'><b> 房产 </b></a> <a href='#'><b> 家居 </b></a> <a href='#'><b> 旅游 </b></a>
     <a href='#'><b> 文化 </b></a> <a href='#'><b> 其他 </b></a></li>

     */
    topic_load();

    async function topic_load() {
        var res = await request("topic.do", "get", {"opr": "topics"});
        if (res.length > 0) {
            var li = $("<li id='class_month'>")
            res.forEach((e, i) => {
                if (i > 11) {
                    li.append("<a onclick='news_load(1,"+e.tid+")' style='cursor: pointer'><b> " + e.tname + " </b></a></li>")
                } else {
                    li.append("<a onclick='news_load(1,"+e.tid+")' style='cursor: pointer' ><b> " + e.tname + " </b></a>")
                }
                li.appendTo("#topic_box");
            });
        }
    }

    //新闻
    /**
     * <li><a href='#'> 步行者崩盘主要原因在哪 解决3问题能发起更强挑战
     </a><span> 2013-06-06 01:01:47.0 </span></li>
     <li class='space'></li>*/
    news_load(1,0);
    var tid=0;

    async function news_load(page,tid=0) {
        $("#news_list_box").html("");
        tid=tid;
        var res = await request("news.do", "get", {"opr": "list", "pageNum": page, "pageSize": "25","tid":tid});
        if (res.code == 200) {
            res.data.data.forEach((e, i) => {
                var li = $("<li></li>")
                    .append("<a href='http://localhost:8080/news/news.do?opr=newsDetail&nid="+e.nid+"' style='cursor: pointer'>" + e.ntitle + "</a>")
                    .append("<span>2013-06-06 01:01:47.0</span>")

                var br = $("<li class='space'></li>");
                if (i < 5 && i == 4) {
                    li.append(br)
                } else if (i >= 5 && i % 4 == 0) {
                    li.append(br)
                }
                li.appendTo("#news_list_box");
            });
            //分页
            var pre = "&nbsp;&nbsp;<a onclick='news_load("+parseInt(res.data.pageNum-1)+",tid)' style='cursor: pointer'>上一页</a>&nbsp;&nbsp;";
            var next = "&nbsp;&nbsp;<a onclick='news_load("+parseInt(res.data.pageNum+1)+",tid)' style='cursor: pointer'>下一页</a>&nbsp;&nbsp;";
            var menu = "<a onclick='news_load("+parseInt(1)+",tid)' style='cursor: pointer'>首页</a>&nbsp;&nbsp;";
            var mo = "<a onclick='news_load("+parseInt(res.data.totalPages)+",tid)' style='cursor: pointer'>末页</a>&nbsp;&nbsp;";
            var page = $("<p align='right'> 当前页数:[" + res.data.pageNum + "/" + res.data.totalPages + "]</p>");
            //判断显示时机
            if(res.data.pageNum > 1) {
                page.append(pre);
                page.append(menu)
            }
            if(res.data.pageNum >=1 && res.data.pageNum < res.data.totalPages) {
                page.append(next);
                page.append(mo)
            }
            page.appendTo("#news_list_box")
        }
    }

    /*async function aside_news_guonei() {
        var data = await aside_news_ajax("国内");
        console.log(data)
        for(var i = 0; i < data.length; i++) {
            if (i < 8) {
                $("<li style='cursor: pointer;padding: 5px;text-overflow: ellipsis;width: auto;overflow: hidden;white-space: nowrap'></li>")
                    .append("<a><b>" + data[i].ntitle + "</b></a>")
                    .appendTo("#guonei");
            }
        }
    }
    async function aside_news_guoji() {
        var data = await aside_news_ajax("国际");

    }*/


</script>
</html>
