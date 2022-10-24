package com.just.news.dao;

import com.just.news.entiry.Comments;

import java.util.List;

public interface CommentDao {
    List<Comments> getCommentsByNid(String nid);
}
