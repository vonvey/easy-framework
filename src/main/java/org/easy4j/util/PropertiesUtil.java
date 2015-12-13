package org.easy4j.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * @author fengwei (ivonvey@gmail.com) //
 * @date 15/12/13 22:49
 */

/**
 * 加载属性文件工具类
 */
public class PropertiesUtil {
    private static final Logger LOGGER = LoggerFactory.getLogger(PropertiesUtil.class);

    /**
     * 加载属性文件
     */
    public static Properties loadProperties(String fileName) {
        Properties properties = null;
        InputStream is = null;
        try {
            is = Thread.currentThread().getContextClassLoader().getResourceAsStream(fileName);
            if (is == null) {
                throw new FileNotFoundException("file " + fileName + " is not found");
            }
            properties = new Properties();
            properties.load(is);
        } catch (IOException e) {
            LOGGER.error("load properties file failure", e);
        } finally {
            if (is != null) {
                try {
                    is.close();
                } catch (IOException e) {
                    LOGGER.error("close input stream failure", e);
                }
            }
        }

        return properties;
    }

    /**
     * 获取字符型属性（默认值为空字符串）
     */
    public static String getString(Properties properties, String key) {
        return getString(properties, key, "");
    }

    /**
     * 获取字符型属性（可以指定默认值）
     */
    public static String getString(Properties properties, String key, String defalutValue) {
        String value = defalutValue;
        if (properties.contains(key)) {
            value = properties.getProperty(key);
        }

        return value;
    }


    /**
     * 获取Int属性（默认值为0）
     */
    public static int getInt(Properties properties, String key) {
        return getInt(properties, key, 0);
    }

    /**
     * 获取Int属性（可以指定默认值）
     */
    public static int getInt(Properties properties, String key, int defalutValue) {
        int value = defalutValue;
        if (properties.contains(key)) {
            value = CastUtil.castInt(properties.getProperty(key));
        }

        return value;
    }

    /**
     * 获取boolean属性（默认值为false）
     */
    public static boolean getBoolean(Properties properties, String key) {
        return getBoolean(properties, key, false);
    }

    /**
     * 获取Int属性（可以指定默认值）
     */
    public static boolean getBoolean(Properties properties, String key, boolean defalutValue) {
        boolean value = defalutValue;
        if (properties.contains(key)) {
            value = CastUtil.castBoolean(properties.getProperty(key));
        }

        return value;
    }
}
