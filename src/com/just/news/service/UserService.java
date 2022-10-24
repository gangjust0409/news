package com.just.news.service;

import com.just.news.entiry.User;

public interface UserService {
    /**
     * 登录
     * @param uname
     * @param upwd
     * @return
     */
    User doLogin(String uname, String upwd);
}
