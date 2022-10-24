package com.just.news.config.dao;

import com.just.news.utils.BaseDao;
import com.just.news.utils.DBUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * 封装常用的 CRUD ，复杂的 SQL 语句需要自己写
 */
public class CRUDDao extends BaseDao {

    private static Log log = LogFactory.getLog(CRUDDao.class);

    /**
     * 封装添加的方法
     *
     * @param source 需要保存的数据
     * @return 返回受影响的行数
     */
    public static Integer save(Object source) {
        Class<?> clazz = source.getClass();
        String tabName = getTableName(clazz);
        // 读取字段
        List<String> fieldList = getTableFields(clazz);

        StringBuffer fieldStr = new StringBuffer();
        StringBuffer valueStr = new StringBuffer();
        for (int i = 0; i < fieldList.size(); i++) {
            String s = fieldList.get(i);
            if (i == fieldList.size() - 1) {
                fieldStr.append(s);
                valueStr.append("#{" + s + "}");
                break;
            } else {
                fieldStr.append(s + ",");
                valueStr.append("#{" + s + "},");
            }
        }
        //System.out.println(fieldStr.append(valueStr));
        StringBuffer sql = new StringBuffer("insert into " + tabName + "(");
        sql.append(fieldStr + ") ");
        sql.append("values (" + valueStr + ")");
        // 执行数据库操作
        Integer res = BaseDao.executeUpdate(sql.toString(), source);
        return commitDb(res);
    }

    /**
     * 提交事务
     * @param res
     * @return
     */
    private static Integer commitDb(Integer res) {
        // 直接提交事务
        Connection connection = DBUtils.threadLocal.get();
        try {
            if (res == 0) {
                log.info("database no row...");
                connection.rollback();
                return 0;
            } else {
                connection.commit();
                System.out.println("database response " + res +  " row...");
                return res;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
        }
    }

    /**
     * 获取表名
     * @param clazz
     * @return
     */
    private static String getTableName(Class<?> clazz) {
        // 以最实体类对象作为表名
        String substring = clazz.toString().substring(clazz.toString().lastIndexOf(".") + 1);
        //String sql = "select * from " + tabName;
        //System.out.println(sql);
        return substring.toLowerCase();
    }

    /**
     * 获取实体类的字段
     * @param clazz
     * @return
     */
    public static List<String> getTableFields(Class<?> clazz) {
        Method[] methods = clazz.getMethods();
        List<String> fieldList = new ArrayList<>();
        for (int i = 0; i < methods.length; i++) {
            Method method = methods[i];
            String s = method.toString();
            if (s.indexOf("set") != -1) {
                String name = method.getName();
                String property = name.substring(name.indexOf("set") + 3).toLowerCase();
                fieldList.add(property);
            }
        }
        return fieldList;
    }


    /**
     * 封装的修改数据库的操作
     * @param source 修改的对象
     * @param choose 条件的字段名称
     * @return 受影响的行数
     */
    public static Integer update(Object source, String choose) {
        Class<?> clazz = source.getClass();
        //List<String> fieldList = getTableFields(clazz);
        List<String> fieldList = new ArrayList<>();
        Method[] methods = clazz.getMethods();
        for (int i = 0; i < methods.length; i++) {
            Method method = methods[i];
            String name = method.getName();
            if (name.indexOf("get") != -1 && !name.equals("getClass")) {
                Object invoke = null;
                try {
                    invoke = method.invoke(source);
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                }
                String property = name.substring(name.indexOf("set") + 4).toLowerCase();
                if (invoke != null && !choose.equals(property)) {
                    fieldList.add(property);
                }
            }
        }
        String tableName = getTableName(clazz);
        StringBuffer fieldStr = new StringBuffer();
        for (int i = 0; i < fieldList.size(); i++) {
            String s = fieldList.get(i);
            if (!s.equals(choose)) {
                if (i == fieldList.size() - 1) {
                    fieldStr.append(s + " = #{" + s + "}");
                    break;
                } else {
                    fieldStr.append(s + " = #{" + s + "}, ");
                }
            }
        }
        StringBuffer sql = new StringBuffer("update " + tableName + " set ");
        sql.append(fieldStr + " where " + choose + " = #{" + choose + "}");

        // 执行 sql语句
        Integer res = BaseDao.executeUpdate(sql.toString(), source);

        return commitDb(res);
    }

    /**
     * 封装删除的方法
     * @param choose 条件的字段
     * @param value 修改字段的值
     * @return 受影响的行数
     */
    public static Integer delete(String choose, Object value, Class<?> clazz) {
        String tableName = getTableName(clazz);
        String sql = "delete from " + tableName + " where " + choose + "= ?";

        // 执行 sql语句
        Integer res = BaseDao.executeUpdate(sql, new Object[]{value});

        return commitDb(res);
    }

    /**
     * 批量删除
     * Arrays.asList(64,65,66,67,68,69) 推荐使用这个
     * @param choose 条件的字段
     * @param values 修改的值集合
     * @param clazz 是哪个实体类型
     * @return 受影响的行数
     */
    public static Integer delete(String choose, List<Object> values, Class<?> clazz) {
        String tableName = getTableName(clazz);
        String wen = new String();
        Object[] objects = new Object[values.size()];
        for (int i = 0;i < values.size(); i++) {
            objects[i] = values.get(i);
            if (values.size() - 1 == i) {
                wen += "?";
                break;
            } else {
                wen += "?,";
            }
        }
        String sql = "delete from " + tableName + " where " + choose + " in ("+wen+")";

        // 执行 sql语句
        Integer res = BaseDao.executeUpdate(sql, objects);

        return commitDb(res);
    }

    /**
     * 查询当前表的信息
     * @param clazz
     * @param <T>
     * @return
     */
    public static  <T> List<T> select( Class<T> clazz){
        String tableName = getTableName(clazz);
        List<String> tableFields = getTableFields(clazz);
        StringBuffer fieldStr = new StringBuffer();
        for (int i = 0; i < tableFields.size(); i++) {
            if (i == tableFields.size()-1) {
                fieldStr.append(tableFields.get(i));
                break;
            } else {
                fieldStr.append(tableFields.get(i) + ",");
            }
        }
        String sql = "select "+ fieldStr +" from " + tableName;
        List<T> students = BaseDao.executeQuery(clazz,sql,null);

        DBUtils.closeConnection();
        if (students == null) {
            return null;
        } else {
            return students;
        }

    }

    /**
     * 查询当前表的信息
     * （支持查询单个）
     * @param field
     * @param val
     * @param clazz
     * @return
     */
    public static  <T> T selectByField( Class<T> clazz, String field,Object val){
        String tableName = getTableName(clazz);
        List<String> tableFields = getTableFields(clazz);
        StringBuffer fieldStr = new StringBuffer();
        for (int i = 0; i < tableFields.size(); i++) {
            if (i == tableFields.size()-1) {
                fieldStr.append(tableFields.get(i));
                break;
            } else {
                fieldStr.append(tableFields.get(i) + ",");
            }
        }
        String sql = "select "+ fieldStr +" from " + tableName;
        if (field != null && val != null) {
            sql += " where " + field + " = ?";
        }
        List<?> objects = BaseDao.executeQuery(clazz, sql, new Object[]{val});
        DBUtils.closeConnection();
        if (objects == null) {
            return null;
        } else {
            return (T) objects.get(0);
        }
    }
    public static Integer count(Class<?> clazz){
        String tableName = getTableName(clazz);
        String sql = "select count(1) from " + tableName;
        List<Integer> ress = BaseDao.executeQuery(Integer.class, sql, null);
        return ress.get(0);
    }

    public static Integer count(Class<?> clazz, String choose, Object val){
        String tableName = getTableName(clazz);
        String sql = "select count(1) from " + tableName + " where " + choose + " = ?";
        List<Integer> ress = BaseDao.executeQuery(Integer.class, sql, new Object[]{val});
        return ress.get(0);
    }


}
