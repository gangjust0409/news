<%@ page language="java" import="java.util.*,java.sql.*" pageEncoding="utf-8" %>
<%@page import="java.text.SimpleDateFormat" %>
<%@page import="java.util.Date" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
    <meta http-equiv="pragma" content="no-cache">
    <meta http-equiv="cache-control" content="no-cache">
    <meta http-equiv="expires" content="0">
    <meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
    <meta http-equiv="description" content="This is my page">
    <title>添加主题--管理后台</title>
    <link href="../static/css/admin.css" rel="stylesheet" type="text/css"/>
</head>
<body>
<%@include file="console_element/top.jsp" %>
<div id="main">
    <%@include file="console_element/left.html" %>
    <div id="opt_area">
        <script language="javascript">
            function clickdel() {
                return confirm("删除请点击确认");
            }

        </script>
        <ul class="classlist" id="news_list">

        </ul>
        <div id="page" style="height: 50px;">

        </div>
    </div>
</div>
<div id="footer">
    <%@include file="console_element/bottom.html" %>
</div>
<script type="text/javascript">
    news_load(1);
    async function news_load(page) {
        $("#news_list").html("");
        $("#page").html("")
        var pageBtn = $("<div class='page-box'></div>")
        var res = await request("news.do","get",{"opr":"list","pageNum":page});
        if(res.code == 200) {
            res.data.data.forEach(function (item,i) {
                var li = $("<li></li>");
                var span = $("<span></span>");
                span.append(" 作者："+item.nauthor+"&#160;&#160;&#160;&#160; <a id='news_modify' style='cursor: pointer' nid='"+item.nid+"'>修改</a>&#160;&#160;&#160;&#160;")
                span.append($("<a id='news_del' style='cursor: pointer' nid='"+item.nid+"'>删除</a>"))
                li.append(item.ntitle);
                li.append(span)
                li.append("<li class='space'></li>");
                li.appendTo("#news_list");
            })

            //遍历分页
            for(var i=1;i<=res.data.totalPages;i++){
                //遍历分页
                pageBtn.append($("<span onclick='news_load("+i+")' style='cursor:pointer;display:inline-block;text-align:center;line-height:25px;margin-right:10px;width: 30px;height: 25px;border: 1px solid #000;'>"+i+"</span>"))
            }

            // 分页
            var pre = "";
            if(page > 1){
                pre = $("<span style='cursor:pointer;' onclick='news_load("+(page-1)+");'>上一页</span>")
                pageBtn.append(pre)
            }

            var next = "";
            if(page>=1&&res.data.totalPages>page) {
                next = $("<span style='cursor:pointer;' onclick='news_load("+(1+page)+");'>下一页</span>")
                pageBtn.append(next)
            }
            pageBtn.appendTo("#page")

        }
    }

    //点击修改
    $(document).on("click","#news_modify",function () {
        location.href = "http://localhost:8080/news/news.do?opr=modify&nid="+$(this).attr("nid");
    })

    // 点击删除
    $(document).on("click","#news_del",function () {
        var fl = confirm("确认是否删除该新闻？");
        if(fl) {
            request("news.do","get",{"opr":"del","nid":$(this).attr("nid")}).then(res => {
                console.log(res)
                if(res.code == 200 && res.data == 1) {
                news_load(1)
            } else if(res.code == 200 && res.data == 0){
                alert("未该有数据！或者已经删除！")
            }
            else {
                alert("删除失败！")
            }
        }, error => {
                console.log(error)
                if(error.status==200){
                    news_load(1)
                }
            })
        }
    })


</script>
</body>
</html>
