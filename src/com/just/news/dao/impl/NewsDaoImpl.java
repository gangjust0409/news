package com.just.news.dao.impl;

import com.just.news.dao.NewsDao;
import com.just.news.entiry.News;
import com.just.news.utils.BaseDao;

import java.util.ArrayList;
import java.util.List;

public class NewsDaoImpl implements NewsDao {
    @Override
    public List<News> newsByTid(String tid) {

        String sql = "select nid,ntitle,ncreateDate from news where ntid = ?";

        List<News> newsList = BaseDao.executeQuery(News.class, sql, new Object[]{ tid });
        //News news = CRUDDao.selectByField(News.class, "ntid", tid);

        return newsList;
    }

    @Override
    public List<News> pages(Integer pageNum, Integer pageSize,Integer tid) {
        String sql = "select * from news ";
        ArrayList<Integer> list = new ArrayList<>();
        if(tid != 0) {
            sql += "where ntid = ?";
            list.add(tid);
        }
        sql += " limit ?,?";
        list.add(pageNum);
        list.add(pageSize);
        List<News> newsList = BaseDao.executeQuery(News.class, sql, list.toArray());

        return newsList;
    }

    @Override
    public Integer count(Integer tid) {
        String sql = "select count(1) from news ";
        ArrayList<Integer> list = new ArrayList<>();
        if(tid != 0) {
            sql += "where ntid = ?";
            list.add(tid);
        }
        List<Integer> integers = BaseDao.executeQuery(Integer.class, sql, list.toArray());
        if(integers==null) {
            return 0;
        }
        return integers.get(0);
    }
}
