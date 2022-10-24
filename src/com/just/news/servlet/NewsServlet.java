package com.just.news.servlet;

import com.alibaba.fastjson2.JSON;
import com.just.news.config.dao.CRUDDao;
import com.just.news.entiry.Comments;
import com.just.news.entiry.News;
import com.just.news.entiry.Topic;
import com.just.news.service.CommentService;
import com.just.news.service.NewsService;
import com.just.news.service.impl.CommentServiceImpl;
import com.just.news.service.impl.NewsServiceImpl;
import com.just.news.config.threadPool.MyThreadPool;
import com.just.news.util.R;
import com.just.news.utils.JustObjectUtil;
import com.just.news.utils.Page;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

@WebServlet(urlPatterns = "/news.do")
public class NewsServlet extends HttpServlet {

    NewsService newsService = new NewsServiceImpl();

    MyThreadPool executor = new MyThreadPool();

    CommentService commentService = new CommentServiceImpl();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        this.doPost(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //设置字符编码
        req.setCharacterEncoding("UTF-8");
        resp.setContentType("text/html");
        resp.setCharacterEncoding("UTF-8");
        PrintWriter writer = resp.getWriter();

        String opr = req.getParameter("opr");

        if ("list".equals(opr)) {

            String pageNum = req.getParameter("pageNum");
            String pageSize = req.getParameter("pageSize");
            //条件
            String tid = req.getParameter("tid");
            if(JustObjectUtil.isEmpty(tid))
                tid = "0";
            if(pageSize ==null)
                pageSize = "10";


            List<News> newsList = newsService.pages(Integer.parseInt(pageNum), Integer.parseInt(pageSize),Integer.parseInt(tid));
            Integer count = newsService.count(Integer.parseInt(tid));
            System.out.println("total " + count);
            Page<News> page = new Page<>();
            page.setData(newsList);
            page.setPageNum(Integer.parseInt(pageNum));
            page.setPageSize(Integer.parseInt(pageSize));
            page.setTotal(count);


            R data = R.success("成功查询到数据").setData(page);
            String json = JSON.toJSONString(data);

            writer.write(json);
        } else if ("newAdd".equals(opr)) {
            //跳转到添加
            toNewsAddPage(req, resp);
        } else if ("add".equals(opr)) {
            //添加到数据库
            try {
                saveNews(req, resp);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if ("del".equals(opr)) {
            //删除
            String nid = req.getParameter("nid");
            Integer res = CRUDDao.delete("nid", nid, News.class);
            writer.write("{\"res\":res}");
        } else if ("modify".equals(opr)) {
            String nid = req.getParameter("nid");
            News news = CRUDDao.selectByField(News.class, "nid", Integer.parseInt(nid));

            //获取当前新闻的主题
            List<Topic> select = CRUDDao.select(Topic.class);

            req.setAttribute("news", news);
            req.setAttribute("topics", select);
            req.getRequestDispatcher("newspages/news_update.jsp").forward(req, resp);
        } else if ("update".equals(opr)) {
            try {
                News news = handlerNews(req, resp);
                System.out.println(news);

                CRUDDao.update(news, "nid");
                resp.sendRedirect("newspages/admin.jsp");

            } catch (Exception e) {
                req.getRequestDispatcher("newspages/news_update.jsp").forward(req, resp);
            }

        } else if ("aside".equals(opr)) {
            String tname = req.getParameter("tname");
            /*Topic topic = CRUDDao.selectByField(Topic.class, "tname", tname);
            List<News> newsList = newsService.newsByTid(topic.getTid().toString());*/
            R r = null;
            //查询主题id
            CompletableFuture<Topic> topicFuture = CompletableFuture.supplyAsync(() -> {
                Topic topic = CRUDDao.selectByField(Topic.class, "tname", tname);
                return topic;
            }, executor);

            try {
                if (topicFuture.get() == null) {
                    r = R.error("当前新闻没有这个主题");
                } else {

                    CompletableFuture<List<News>> newsListFuture = topicFuture.thenApplyAsync(topic -> {
                        List<News> newsList = newsService.newsByTid(topic.getTid().toString());
                        return newsList;
                    }, executor);

                    List<News> newsList = null;
                    CompletableFuture.allOf(topicFuture, newsListFuture).get();
                    newsList = newsListFuture.get();
                    r = R.success("获取成功").setData(newsList);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }

            //r = R.success("获取成功").setData(newsList);
            writer.write(JSON.toJSONString(r));
        } else if("newsDetail".equals(opr)) {
            //新闻详情
            String nid = req.getParameter("nid");
            News news = CRUDDao.selectByField(News.class, "nid", nid);
            //TODO 查询当前新闻的所有评论信息
            List<Comments> comments = commentService.getCommentsByNid(nid);
            req.setAttribute("comments", comments);
            req.setAttribute("news", news);

            req.getRequestDispatcher("newspages/news_read.jsp").forward(req,resp);
        }

    }

    private void saveNews(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        //获取对应的文本框的数据
        News news = handlerNews(req, resp);
        Integer res = CRUDDao.save(news);
        //跳转到admin
        resp.sendRedirect("newspages/admin.jsp");
    }

    private News handlerNews(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        //处理上传图片
        //请求信息是否是multipart
        News news = new News();
        boolean multipartContent = ServletFileUpload.isMultipartContent(req);
        String fileName = "";
        //上传文件存入的目录
        String realPath = "D:\\devs\\nginx-1.23.2\\html\\static";
        if (multipartContent) {
            FileItemFactory factory = new DiskFileItemFactory();
            ServletFileUpload upload = new ServletFileUpload(factory);
            upload.setHeaderEncoding("utf-8");
            //解析form表单的所有文件
            List list = upload.parseRequest(req);
            Iterator iterator = list.iterator();

            //依次处理每个文件
            while (iterator.hasNext()) {
                FileItem next = (FileItem) iterator.next();
                //文件表单字段
                if (!next.isFormField()) {
                    String name = next.getName();
                    System.out.println("file" + name);
                    if (!JustObjectUtil.isEmpty(name)) {
                        fileName = UUID.randomUUID().toString().replace("-", "") + next.getName();
                        File fullFile = new File(fileName);
                        File saveFile = new File(realPath, fullFile.getName());
                        //写入文件
                        next.write(saveFile);
                        //把文件名返回保存在数据库中
                        news.setNpicpath(fileName);
                    }
                } else {
                    String fieldName = next.getFieldName();
                    if (fieldName.equals("nid")) {
                        news.setNid(Integer.parseInt(next.getString("UTF-8")));
                    }
                    switch (fieldName) {
                        case "ntid":
                            news.setNtid(Integer.parseInt(next.getString("UTF-8")));
                            break;
                        case "ntitle":
                            news.setNtitle(next.getString("UTF-8"));
                            break;
                        case "nauthor":
                            news.setNauthor(next.getString("UTF-8"));
                            break;
                        case "nsummary":
                            news.setNsummary(next.getString("UTF-8"));
                            break;
                        case "ncontent":
                            news.setNcontent(next.getString("UTF-8"));
                            break;
                    }
                    news.setNcreatedate(new Date());
                }
            }
        }

        return news;
    }

    private void toNewsAddPage(HttpServletRequest req, HttpServletResponse resp) throws
            ServletException, IOException {
        List<Topic> topics = CRUDDao.select(Topic.class);
        req.setAttribute("topics", topics);
        req.getRequestDispatcher("newspages/news_add.jsp").forward(req, resp);
    }
}
