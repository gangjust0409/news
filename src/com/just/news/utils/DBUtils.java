package com.just.news.utils;

import com.just.news.config.ConfigManager;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.sql.*;

public class DBUtils {

    private static Log log = LogFactory.getLog(DBUtils.class);

    private static final String MYSQL_JDBC_PREFIX = "jdbc.";

    private static String driver = ConfigManager.getProperty(MYSQL_JDBC_PREFIX+"driver");
    private static String url = ConfigManager.getProperty(MYSQL_JDBC_PREFIX+"url");
    private static String username = ConfigManager.getProperty(MYSQL_JDBC_PREFIX+"username");
    private static String password = ConfigManager.getProperty(MYSQL_JDBC_PREFIX+"password");

    public static ThreadLocal<Connection> threadLocal = new ThreadLocal<>();

    static {
        try {
            Class.forName(driver);
            log.info("加载数据库驱动成功...");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    static Connection getConnection() throws SQLException {
        Connection connection = threadLocal.get();
        if (connection == null || connection.isClosed()) {
            connection = DriverManager.getConnection(url,username,password);
            if (connection.getAutoCommit())
                connection.setAutoCommit(false); // 关闭自动提交事务
            threadLocal.set(connection);
            log.info("Java和数据库创建连接成功...");
        }
        return connection;
    }

    public static void closeConnection(){
        Connection connection = threadLocal.get();
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
                log.info("释放Java和数据库连接成功！");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void closeAll(PreparedStatement ps, ResultSet rs){
        try {
            if (rs != null && !rs.isClosed()) {
                rs.close();
            }

            if (ps != null && !ps.isClosed()) {
                ps.close();
            }

            log.info("释放执行 SQL 语句成功...");
        } catch (SQLException e) {
            e.printStackTrace();
            log.error("释放执行 SQL 语句失败...");
        }
    }

}
