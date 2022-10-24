package com.just.news.servlet;


import com.alibaba.fastjson2.JSON;
import com.just.news.config.dao.CRUDDao;
import com.just.news.entiry.News;
import com.just.news.entiry.Topic;
import com.just.news.myenum.CommEnum;
import com.just.news.service.NewsService;
import com.just.news.service.TopicService;
import com.just.news.service.impl.NewsServiceImpl;
import com.just.news.service.impl.TopicServiceImpl;
import com.just.news.util.R;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

@WebServlet(urlPatterns = "/topic.do")
public class TopicServlet extends HttpServlet {

    TopicService topicService = new TopicServiceImpl();

    NewsService newsService = new NewsServiceImpl();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //设置字符编码
        req.setCharacterEncoding("UTF-8");
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        this.doPost(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        String opr = req.getParameter("opr");
         PrintWriter writer = resp.getWriter();
        if ("topics".equals(opr)) {
            //查询所有的新闻主题
            List<Topic> topics = topicService.topics();
            String json = JSON.toJSONString(topics);
            writer.write(json);
        } else if("del".equals(opr)) {
            //删除当前项
            String tid = req.getParameter("tid");
            Integer count = CRUDDao.count(News.class, "ntid", tid);
            //删除之前还要查询这个在新闻表是否存在当前tid
            if (count > 0) {
                R error = R.error(CommEnum.TopicEnum.TOPIC_EXISTS_NEW.getCode(), CommEnum.TopicEnum.TOPIC_EXISTS_NEW.getMsg());
                writer.write(JSON.toJSONString(error));
            } else {
                // 没有异常才会删除
                Integer res = CRUDDao.delete("tid", Integer.parseInt(tid), Topic.class);
                writer.write("{\"data\":"+res+"}");
            }

        } else if ("modify".equals(opr)) {
            toModifyPage(req, resp);
        } else if("update".equals(opr)) {
            updateTopic(req, resp);
        } else if("add".equals(opr)) {
            addTopic(req, resp);
        }
    }

    private void addTopic(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String tname = req.getParameter("tname");
        Topic topic = new Topic();
        topic.setTname(tname);
        //保存方法
        Integer res = CRUDDao.save(topic);
        resp.sendRedirect("newspages/topic_list.jsp");
    }

    private void updateTopic(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String tid = req.getParameter("tid");
        String tname = req.getParameter("tname");
        Topic topic = new Topic();
        topic.setTid(Integer.parseInt(tid));
        topic.setTname(tname);
        Integer res = CRUDDao.update(topic, "tid");
        resp.sendRedirect("newspages/topic_list.jsp");
    }

    private void toModifyPage(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // 获取id查询对应的信息
        String tid = req.getParameter("tid");
        Topic topic = CRUDDao.selectByField(Topic.class, "tid", tid);
        req.setAttribute("topic", topic);
        req.getRequestDispatcher("newspages/topic_update.jsp").forward(req,resp);
    }
}
