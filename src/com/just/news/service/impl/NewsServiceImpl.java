package com.just.news.service.impl;

import com.alibaba.fastjson2.JSON;
import com.just.news.dao.NewsDao;
import com.just.news.dao.impl.NewsDaoImpl;
import com.just.news.entiry.News;
import com.just.news.service.NewsService;

import java.util.List;

public class NewsServiceImpl implements NewsService {

    NewsDao newsDao = new NewsDaoImpl();

    @Override
    public List<News> newsByTid(String tid) {
        List<News> newsList = newsDao.newsByTid(tid);

        return newsList;
    }

    @Override
    public List<News> pages(Integer pageNum, Integer pageSize,Integer tid) {
        List<News> pages = newsDao.pages((pageNum - 1) * pageSize, pageSize,tid);

        return pages;
    }

    @Override
    public Integer count(Integer tid) {
        return newsDao.count(tid);
    }
}
