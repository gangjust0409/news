package com.just.news.util;

import java.util.HashMap;

public class R extends HashMap<String, Object> {

    public R(){}
    private R(Integer code, String msg){
        this.put("code", code);
        this.put("msg", msg);
    }

    private Integer code;
    private String msg;
    private Object data;

    public R setData(Object data){
        this.put("data", data);
        return this;
    }

    public static R success(String msg){
       return new R(200,msg);
    }

    public static R success(Integer code,String msg){

        return new R(code,msg);
    }

    public static R error(String msg){
        return new R(500,msg);
    }

    public static R error(Integer code,String msg){
        return new R(code,msg);
    }

}
