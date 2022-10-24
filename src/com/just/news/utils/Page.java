package com.just.news.utils;

import java.util.List;

/**
 * 分页工具类
 * @author lqg
 * @version 1.0
 */
public class Page<T> {

    /**
     * 当前页
     */
    private Integer pageNum;
    /**
     * 每页显示多少条数据
     */
    private Integer pageSize;
    /**
     * 总条数
     */
    private Integer total;
    /**
     * 总页数
     */
    private Integer totalPages;

    /**
     * 展示的数据
     */
    private List<T> data;

    public Integer getPageNum() {
        return pageNum;
    }

    public void setPageNum(Integer pageNum) {
        this.pageNum = pageNum;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    public Integer getTotalPages() {
        if (this.pageSize != null && this.total != null) {
            this.totalPages = total%pageSize==0?total/pageSize:(total/pageSize)+1;
            System.out.println(this.totalPages);
        }
        return totalPages;
    }

    public List<T> getData() {
        return data;
    }

    public void setData(List<T> data) {
        this.data = data;
    }
}
