package com.just.news.test;

import com.just.news.entiry.News;
import com.just.news.service.NewsService;
import com.just.news.service.impl.NewsServiceImpl;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.List;

public class Test {
    public static void main(String[] args) throws UnknownHostException {
        NewsService newsService = new NewsServiceImpl();

       /* List<News> newsList = newsService.newsByTid(30+"");
        System.out.println("news -> ");
        newsList.forEach(System.out::println);*/

        /*final Integer tid = CRUDDao.delete("tid", 30, Topic.class);
        System.out.println(tid);*/
        /*final List<News> pages = newsService.pages(1, 5);
        pages.forEach(System.out::println);*/

    /*    final Jedis jedis = MyRedisPool.jedis();
        System.out.println(jedis);*/

        final String hostName = InetAddress.getLoopbackAddress().getHostName();
        System.out.println(InetAddress.getLocalHost().getHostAddress());
        System.out.println(hostName);

    }
}
