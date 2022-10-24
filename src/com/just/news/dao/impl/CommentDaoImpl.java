package com.just.news.dao.impl;

import com.just.news.dao.CommentDao;
import com.just.news.entiry.Comments;
import com.just.news.utils.BaseDao;

import java.util.List;

public class CommentDaoImpl implements CommentDao {
    @Override
    public List<Comments> getCommentsByNid(String nid) {
        String sql = "SELECT * FROM `comments` where cnid = ?";
        System.out.println(nid);
        List<Comments> comments = BaseDao.executeQuery(Comments.class, sql, new Object[]{Integer.parseInt(nid)});

        return comments;
    }
}
