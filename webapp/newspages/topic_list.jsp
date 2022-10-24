<%@ page language="java" import="java.util.*,entity.*" pageEncoding="utf-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <title>添加主题--管理后台</title>
    <link href="../static/css/admin.css" rel="stylesheet" type="text/css"/>
</head>
<body>
<%@include file="console_element/top.jsp" %>
<div id="main">
    <%@include file="console_element/left.html" %>
    <div id="opt_area">
        <ul class="classlist" id="topic_elm"></ul>
    </div>
</div>
<div id="footer">
    <%@include file="console_element/bottom.html" %>
</div>
</body>
<script type="text/javascript" src="static/js/jquery-3.6.1.min.js"></script>
<script type="text/javascript" src="static/js/es6-ajax-promise.js"></script>
<script type="text/javascript">
    $(function () {
      topic_load();
    })

    function topic_load() {
        $("#topic_elm").html("");
        request("topic.do", "get", {"opr": "topics"}).then(res => {
            if(res.length > 0){
              res.forEach(function (item) {
                  var li = $("<li></li>");
                  li.append(item.tname + "&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;<a style='cursor: pointer' id='topic_modify' tid='"+item.tid+"' >修改</a>");
                  li.append("&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;<a style='cursor: pointer' id='topic_del' tid='" + item.tid + "'>删除</a>");
                  li.appendTo("#topic_elm")
              });
          }
    })
        /*$.ajax({
            url: "topic.do",
            type: "get",
            dataType: "json",
            data: "opr=topics",
            success: function (res) {

            }
        })*/
    }
    // 修改
    $(document).on("click", "#topic_modify", function (){
      var tid = $(this).attr("tid");
      location.href = "http://localhost:8080/news/topic.do?opr=modify&tid=" + tid;
    })
    // 删除
    $(document).on("click", "#topic_del", function () {
        var isDel = window.confirm("确认是否删除该主题？");
        if (isDel) {
            request("topic.do", "get", {"opr": "del", "tid": $(this).attr("tid")}).then(res => {
                if(res.data == 1){
                // 刷新
                    topic_load()
                } else if(res.code!=null&&res.code==100){
                  alert(res.msg)
                } else  {
                    alert("删除失败！")
                }
        })
        }
    });
</script>
</html>

