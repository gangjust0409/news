package com.just.news.service.impl;

import com.just.news.dao.CommentDao;
import com.just.news.dao.impl.CommentDaoImpl;
import com.just.news.entiry.Comments;
import com.just.news.service.CommentService;

import java.util.List;

public class CommentServiceImpl implements CommentService {

    CommentDao commentDao = new CommentDaoImpl();

    @Override
    public List<Comments> getCommentsByNid(String nid) {
        List<Comments> comments = commentDao.getCommentsByNid(nid);
        System.out.println("comments " + comments);
        if(comments!=null) {
            return comments;
        }
        return null;
    }
}
