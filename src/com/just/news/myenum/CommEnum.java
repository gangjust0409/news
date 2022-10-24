package com.just.news.myenum;

public class CommEnum {

    public enum TopicEnum{
        TOPIC_EXISTS_NEW(100,"当前主题还有新闻，不能删除哦！");

        TopicEnum(Integer code, String msg) {
            this.code = code;
            this.msg = msg;
        }

        Integer code;
        String msg;

        public Integer getCode() {
            return code;
        }

        public String getMsg() {
            return msg;
        }
    }

}
