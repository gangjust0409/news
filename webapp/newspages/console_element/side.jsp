<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2022/10/24
  Time: 15:31
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title></title>
    <link href="http://localhost/static/css/main.css" rel="stylesheet" type="text/css"/>
</head>
<body>
<div class="sidebar">
    <h1><img src="http://localhost/static/images/title_1.gif" alt="国内新闻"/></h1>
    <div class="side_list">
        <ul id="guonei"></ul>
    </div>
    <h1><img src="http://localhost/static/images/title_2.gif" alt="国际新闻"/></h1>
    <div class="side_list">
        <ul id="guoji"></ul>
    </div>
    <h1><img src="http://localhost/static/images/title_3.gif" alt="娱乐新闻"/></h1>
    <div class="side_list">
        <ul id="yule"></ul>
    </div>
</div>
</body>
<script type="text/javascript" src="http://localhost/static/js/jquery-3.6.1.min.js"></script>
<script type="text/javascript" src="http://localhost/static/js/es6-ajax-promise.js"></script>
<script>
    //右侧列表
    aside_news("国内", "guonei");
    aside_news("国际", "guoji");
    aside_news("娱乐", "yule");

    async function aside_news(tname, type) {
        var res = await request("news.do", "get", {"opr": "aside", "tname": tname});
        if (res.code == 200) {
            res.data.forEach((e, i) => {
                if (i < 8) {
                    $("<li style='cursor: pointer;padding: 5px;text-overflow: ellipsis;width: auto;overflow: hidden;white-space: nowrap'></li>")
                        .append("<a href='http://localhost:8080/news/news.do?opr=newsDetail&nid="+e.nid+"'><b>" + e.ntitle + "</b></a>")
                        .appendTo("#" + type);
                }
            });
        }
    }
</script>
</html>
