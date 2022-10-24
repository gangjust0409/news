package com.just.news.service.impl;

import com.just.news.config.dao.CRUDDao;
import com.just.news.entiry.Topic;
import com.just.news.service.TopicService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.List;

public class TopicServiceImpl implements TopicService {

    Log log = LogFactory.getLog(TopicServiceImpl.class);

    @Override
    public List<Topic> topics() {
        List<Topic> select = CRUDDao.select(Topic.class);
        log.info("select " + select);
        return select;
    }
}
