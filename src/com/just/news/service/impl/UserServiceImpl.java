package com.just.news.service.impl;

import com.just.news.config.dao.CRUDDao;
import com.just.news.dao.UserDao;
import com.just.news.dao.impl.UserDaoImpl;
import com.just.news.entiry.User;
import com.just.news.service.UserService;
import com.just.news.utils.JustObjectUtil;

public class UserServiceImpl implements UserService {

    UserDao userDao = new UserDaoImpl();


    @Override
    public User doLogin(String uname, String upwd) {
        User user = CRUDDao.selectByField(User.class,"uname",uname);
        if (!JustObjectUtil.isEmpty(user) && user.getUname().equals(uname) && user.getUpwd().equals(upwd)) {
            return user;
        }

        return null;
    }
}
