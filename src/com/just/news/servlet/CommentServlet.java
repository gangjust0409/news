package com.just.news.servlet;

import com.just.news.config.dao.CRUDDao;
import com.just.news.entiry.Comments;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.InetAddress;
import java.util.Date;

@WebServlet(urlPatterns = "/comments.do")
public class CommentServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        this.doPost(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("utf-8");
        resp.setCharacterEncoding("utf-8");
        String opr = req.getParameter("opr");
        if("add".equals(opr)) {
            String nid = req.getParameter("nid");
            String cauthor = req.getParameter("cauthor");
            String ccontent = req.getParameter("ccontent");
            String hostAddress = InetAddress.getLocalHost().getHostAddress();
            Comments comment = new Comments();
            comment.setCauthor(cauthor);
            comment.setCcontent(ccontent);
            comment.setCnid(Integer.parseInt(nid));
            comment.setCip(hostAddress);
            comment.setCdate(new Date());

            //保存
            CRUDDao.save(comment);
            resp.sendRedirect("http://localhost:8080/news/news.do?opr=newsDetail&nid="+nid);
        }

    }
}
