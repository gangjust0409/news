package com.just.news.servlet;

import com.just.news.constant.UserConstant;
import com.just.news.entiry.User;
import com.just.news.service.UserService;
import com.just.news.service.impl.UserServiceImpl;
import com.just.news.utils.JustObjectUtil;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet(urlPatterns = "/login.do")
public class LoginUserServlet extends HttpServlet {

    Log log = LogFactory.getLog(LoginUserServlet.class);

    UserService userService = new UserServiceImpl();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        this.doPost(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String opr = req.getParameter("opr");
        // 需要共享页面数据
        HttpSession session = req.getSession();
        String contextPath = req.getContextPath();

        if ("login".equals(opr)) {
            // 获取输入的用户名和密码
            String uname = req.getParameter("uname");
            String upwd = req.getParameter("upwd");

            User user = userService.doLogin(uname,upwd);
            if (!JustObjectUtil.isEmpty(user)) {

                session.setAttribute(UserConstant.CURRENT_LOGIN_USER, user);
                log.info("准备跳转了");
                // 并且重定向到成功登录页
                resp.sendRedirect("newspages/admin.jsp");
            } else {
                session.setAttribute("msg", "用户不存在！");
                resp.sendRedirect("index.jsp");
            }
        } else if("logout".equals(opr)) {
            session.removeAttribute(UserConstant.CURRENT_LOGIN_USER);
            System.out.println("退出成功！"+session.getAttribute(UserConstant.CURRENT_LOGIN_USER));
            resp.sendRedirect("index.jsp");
        }
    }
}
