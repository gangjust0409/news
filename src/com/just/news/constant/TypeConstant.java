package com.just.news.constant;

import java.util.HashMap;
import java.util.Map;

/**
 * 数据库类型对应java类型
 */
public class TypeConstant {

    private static Map<String, Class<?>> map = new HashMap<>();

    static {
        map.put("BIGINT", Long.class);
        map.put("INT", Integer.class);
        map.put("INTEGER", Integer.class);
        map.put("TINYINT", Integer.class);
        map.put("VARCHAR", String.class);
        map.put("CHAR", String.class);
        map.put("DECIMAL", java.math.BigDecimal.class);
        map.put("DATE",java.util.Date.class);
        map.put("DATETIME",java.util.Date.class);
        //map.put("TIMESTAMP",java.util.Date.class);
    }

    public static Class<?> getType(String key){
        return map.get(key);
    }

    public static void addType(String type, Class<?> clazz){
        map.put(type, clazz);
    }

}
