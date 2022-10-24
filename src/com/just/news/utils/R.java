package com.just.news.utils;

import java.util.HashMap;

/**
 * 非微服务的 response 返回 JSON 数据
 * @author lqg
 * @version 1.0
 */
public class R<T> extends HashMap {

    private Integer code;
    private String message;
    private T data;

    /**
     * 返回成功的结果
     * @param message 提示消息
     * @param data 数据
     * @return 返回 成功的数据
     */
    public R success(String message, T data) {
        this.code = 0;
        this.message = message;
        this.data = data;
        return this;
    }

    /**
     * 返回失败的结果
     * @param message
     * @return
     */
    public R error(String message) {
        this.code = 500;
        this.message = message;
        this.data = null;
        return this;
    }

}
