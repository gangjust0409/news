package com.just.news.dao;

import com.just.news.entiry.News;

import java.util.List;

public interface NewsDao {
    List<News> newsByTid(String tid);

    List<News> pages(Integer pageNum, Integer pageSize, Integer tid);
    Integer count(Integer tid);

}
