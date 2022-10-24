package com.just.news.utils;

import com.just.news.constant.TypeConstant;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BaseDao {

    private static Log log = LogFactory.getLog(BaseDao.class);

    /**
     * 增删改的方法
     *
     * @return
     */
    public static Integer executeUpdate(String sql, Object... params) {
        int result = 0;
        PreparedStatement ps = null;
        try {
            ps = DBUtils.getConnection().prepareStatement(sql);
            if (params != null || params.length > 0) {
                for (int i = 0; i < params.length; i++) {
                    ps.setObject((i + 1), params[i]);
                }
            }
            result = ps.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            DBUtils.closeAll(ps, null);
        }
        return result;
    }

    protected static Map<String, Object[]> handleParameterMapping(String sql, Object entity) {
        Map<String, Object[]> mappingRsult = new HashMap<>();
        List<String> placeholders = new ArrayList<>();
        int offset = 0;
        while (true) {
            int start = sql.indexOf("#{", offset); // 起始位
            // 当读取到最后一个字段之后就停止
            if (start >= offset) {
                int end = sql.indexOf("}", offset);
                placeholders.add(sql.substring(start + 2, end)); // 添加字段进入list集合
                offset = end + 1;
            } else {
                break;
            }
        }
        // 实体类映射
        if (!JustObjectUtil.isListNull(placeholders)) {
            Object[] params = new Object[placeholders.size()];
            for (int i = 0; i < placeholders.size(); ++i) {
                String placeholder = placeholders.get(i);
                // 将特殊字符换成?
                sql = sql.replaceFirst("\\#\\{" + placeholder + "\\}", "?");
                // 获取对应的getter方法
                // 得到getter方法数据
                try {
                    Method getter = entity.getClass().getMethod("get" + JustObjectUtil.toUpee(placeholder));
                    Object param = getter.invoke(entity);
                    params[i] = param;
                } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
                    log.error("no setting placholder #{" + placeholder + "} error message  " + e.getMessage());
                    e.printStackTrace();
                }
            }
            mappingRsult.put(sql, params); // 返回数据
        } else {
            mappingRsult.put(sql, new Object[]{});
        }
        return mappingRsult;
    }

    protected static  <T> List<T> handleResultMapping(Class<T> clz, ResultSet rs) throws SQLException, IllegalAccessException, InstantiationException {
        // 结果是数字
        if (clz.equals(Integer.class)) {
            List<Integer> result = new ArrayList<>();
            while (rs.next()) {
                result.add(rs.getInt(1));
            }
            return (List<T>) result;
        }
        ResultSetMetaData metaData = rs.getMetaData(); // 得到 rs 中的元数据
        int columnCount = metaData.getColumnCount(); // 得到结果集的列数
        // 得到Java实体类的setter
        Method[] setters = new Method[columnCount];
        Class[] types = new Class[columnCount]; // 得到每列的数据类型
        for (int i = 0, j = 1; i < columnCount; ++i, ++j) {
            String label = metaData.getColumnLabel(j); // 得到查询结果的列标题
            String typeName = metaData.getColumnTypeName(j); // 得到当前列的在数据库的类型
            try {
                Class<?> type = TypeConstant.getType(typeName);
                setters[i] = clz.getMethod("set" + JustObjectUtil.toUpee(label), type);
                // 获取数据库类型在Java语言是什么类型
                types[i] = TypeConstant.getType(typeName);
            } catch (NoSuchMethodException e) {
                setters[i] = null;
                types[i] = null;
                log.error("sql transformation error message  " + e.getMessage());
            }
        }
        List<T> result = new ArrayList<>();
        T instance = null;
        while (rs.next()) {
            instance = clz.newInstance(); // 实例化对象
            for (int k = 0; k < columnCount; ++k) {
                if (setters[k] == null)
                    continue;
                else {
                    try {
                        // 处理日期类型的
                        if (types[k].equals(java.util.Date.class)) {
                            setters[k].invoke(instance, new java.util.Date(rs.getDate(k + 1).getTime()));
                        } else {
                            setters[k].invoke(instance, rs.getObject(k + 1));
                            // setters[k].invoke(instance, rs.getObject(k + 1, types[k]));  错误方式
                        }
                    } catch (InvocationTargetException e) {
                        log.error("query data error message  "+e.getMessage());
                    }
                }
            }
            result.add(instance);
        }
        return result;
    }

    /**
     * 增删改
     * @param sql
     * @param entity
     * @return
     */
    public static Integer executeUpdate(String sql, Object entity) {
        Map<String, Object[]> parameterMapping = handleParameterMapping(sql, entity);
        Map.Entry<String, Object[]> entry = parameterMapping.entrySet().iterator().next();
        // 执行增删改方法
        log.info("日志：SQL 语句：" + entry.getKey() + "... 值为：" + entry.getValue());
        Integer res = executeUpdate(entry.getKey(),entry.getValue());
        return res;
    }

    public static  <T> List<T> executeQuery(Class<T> clz, String sql, Object[] params) {
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            ps = DBUtils.getConnection().prepareStatement(sql);
            if (params != null && params.length > 0) {
                for (int i = 0; i < params.length; i++) {
                    ps.setObject((i+1),params[i]);
                }
            }
            rs = ps.executeQuery();
            log.info("查询；" + sql +"params ");
            return handleResultMapping(clz, rs);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
            throw new RuntimeException("getting error message " + e.getMessage());
        } catch (InstantiationException e) {
            e.printStackTrace();
            throw new RuntimeException("getting error message " + e.getMessage());
        } finally {
            DBUtils.closeAll(ps,rs);
        }
        return null;
    }

    public static  <T> List<T> executeQuery(Class<T> clz, String sql, Object entity) {
        Map<String, Object[]> parameterMapping = handleParameterMapping(sql, entity);
        Map.Entry<String, Object[]> entry = parameterMapping.entrySet().iterator().next();
        log.info("日志：SQL 语句：" + entry.getKey() + "... 值为：" + entry.getValue());
        List<T> list = executeQuery(clz, entry.getKey(), entry.getValue());
        return list;
    }

}
