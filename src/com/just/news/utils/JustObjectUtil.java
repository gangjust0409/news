package com.just.news.utils;

import java.lang.reflect.Field;
import java.util.Collection;
import java.util.List;

/**
 * 简单开发常用工具类
 * @author lqg
 * @version 1.0
 */
public class JustObjectUtil {

    /**
     * 判断值是否为 null
     * @param value 判断的值
     * @return true | false
     */
    public static Boolean isEmpty(Object value) {
        if ("".equals(value) || value == null) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 判断list集合是否为null
     * @param collection 集合名字
     * @return true | false
     */
    public static Boolean isListNull(Collection collection) {
        if (collection == null && collection.size() == 0) {
            return true;
        }
        return false;
    }

    /**
     * 首字母大写
     * @param value 需要转换的值
     * @return
     */
    public static String toUpee(String value) {
        byte[] bytes = value.getBytes();
        bytes[0] = (byte) ((char)bytes[0] - 'a' + 'A');
        return new String(bytes);
    }

    /**
     * 对象拷贝，必须转换实体必须存在
     * @param sourceObj 拷贝对象来源
     * @param clazz 转成什么类型的class
     * @return 一个新的对象
     */
    public static <T> T copyBean(Object sourceObj, Class clazz) {
        Class<?> sourceObjClass = sourceObj.getClass();

        Field[] sourceObjClassDeclaredFields = sourceObjClass.getDeclaredFields();
        Field[] targetObjClassDeclaredFields = clazz.getDeclaredFields();

        Object targetInstance = null;
        try {
            targetInstance = clazz.newInstance();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        for (Field sourceField : sourceObjClassDeclaredFields) {
            for (Field targetField : targetObjClassDeclaredFields) {
                sourceField.setAccessible(true);
                targetField.setAccessible(true);
                if (sourceField.getName().equals(targetField.getName())) {
                    try {
                        targetField.set(targetInstance, sourceField.get(sourceObj));
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        return (T) targetInstance;
    }

    /**
     * 对象转成 JSON 字符串
     * @param object 需要转换的 JSON 对象
     * @return
     */
    public static String objectToJSON(Object object) {
        Class<?> clazz = object.getClass();
        StringBuffer sb = new StringBuffer("{\""+getClassBys(clazz.getName())+"\""+":"+"{");
        Field[] declaredFields = clazz.getDeclaredFields();
        for (int i = 0;i < declaredFields.length; i++) {
            declaredFields[i].setAccessible(true);
            try {
                String type = declaredFields[i].getType().toString();
                String substring = getClassBys(type);
                if (substring.equals("List")) {
                    sb.append("\""+declaredFields[i].getName()+"\""+":"+"[");
                    List<Object> attrs = (List<Object>) declaredFields[i].get(object);
                    for(int j =0; j < attrs.size(); j++){
                        Object attr = attrs.get(j);
                        Class<?> attrClazz = attr.getClass();
                        Field[] fields = attrClazz.getDeclaredFields();
                        for (int k = 0; k < fields.length; k++) {
                            fields[k].setAccessible(true);
                            sb.append("{\""+fields[k].getName()+"\""+":\""+fields[k].get(attr)+"\"}");
                        }
                        if (!(j == attrs.size()-1)){
                            sb.append(",");
                        }
                    }
                    sb.append("]");
                } else {
                    sb.append("\""+declaredFields[i].getName()+"\""+":"+"\""+declaredFields[i].get(object)+"\"");
                }
                if (declaredFields.length-1 == i){
                    sb.append("}");
                } else {
                    sb.append(",");
                }
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        sb.append("}");
        return sb.toString();
    }

    /**
     * 将 JSON 字符串转成 对象
     * @param json json串
     * @param clazz 需要转换的对象类型
     * @param <T> 返回的类型
     * @return
     */
    public static <T> T JSONToObject(String json, Class<T> clazz) {
        T newInstance = null;
        try {
            newInstance = clazz.newInstance();
            String[] split = json.split("\\{");
            String[] classNames = split[1].split("\"");
            if (getClassBys(clazz.getName()).equals(classNames[1])) {
                String[] fields = split[2].split(",");
                for (int i = 0; i < fields.length; i++) {
                    String[] vars = fields[i].split(":");
                    String s = getFieldByString(vars[1]);
                    for (Field field : clazz.getDeclaredFields()) {
                        field.setAccessible(true);
                        if (getFieldByString(vars[0]).equals(getClassBys(field.getName()))) {
                            String value = getFieldByString(vars[1]);
                            if (s.lastIndexOf("\"") != -1) {
                                // 数组
                                if(getClassBys(field.getType().toString()).equals("String;")){
                                    field.set(newInstance, new String[]{s.substring(0,s.lastIndexOf("\""))});
                                    return newInstance;
                                }
                                field.set(newInstance, s.substring(0,s.lastIndexOf("\"")));
                            } else if(getClassBys(field.getType().toString()).equals("Long")) {
                                field.set(newInstance,Long.parseLong(value));
                            } else if(getClassBys(field.getType().toString()).equals("Char")) {
                                field.set(newInstance,value);
                            }
                        }
                    }
                }
            }
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

        return newInstance;
    }

    private static String getFieldByString(String var) {
        return var.substring(var.indexOf("\"")+1, var.length()-1);
    }

    private static String getClassBys(String type) {
        return type.substring(type.lastIndexOf(".") + 1, type.length());
    }

}
