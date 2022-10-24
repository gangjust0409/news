package com.just.news.config;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * 配置类
 */
public class ConfigManager {

    private static Properties properties;

    static {
        properties = new Properties();
        // 使用ClassLoader加载properties配置文件生成对应的输入流
        InputStream inputStream = ConfigManager.class.getClassLoader().getResourceAsStream("resources/db-driver.properties");
        try {
            properties.load(inputStream);

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                inputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static String getProperty(String key){
        return properties.getProperty(key);
    }


}
