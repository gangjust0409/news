package com.just.news.service;

import com.just.news.entiry.News;

import java.util.List;

public interface NewsService {

    /**
     * 根据tid查询信息信息
     * @param tid
     * @return
     */
    List<News> newsByTid(String tid);

    List<News> pages(Integer pageNum, Integer pageSize,Integer tid);
    Integer count(Integer tid);
}
