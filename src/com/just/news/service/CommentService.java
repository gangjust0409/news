package com.just.news.service;

import com.just.news.entiry.Comments;

import java.util.List;

public interface CommentService {
    /**
     * 根据新闻id获取当前所有评论
     * @param nid
     * @return
     */
    List<Comments> getCommentsByNid(String nid);
}
