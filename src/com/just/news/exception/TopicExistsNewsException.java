package com.just.news.exception;

import com.just.news.myenum.CommEnum;

public class TopicExistsNewsException extends RuntimeException {
    public TopicExistsNewsException() {
        super(CommEnum.TopicEnum.TOPIC_EXISTS_NEW.getMsg());
    }
}
