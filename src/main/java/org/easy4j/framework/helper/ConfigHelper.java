package org.easy4j.framework.helper;

import org.easy4j.framework.ConfigConstant;
import org.easy4j.framework.util.PropertiesUtil;

import java.util.Properties;

/**
 * @author fengwei (ivonvey@gmail.com) //
 * @date 15/12/27 15:06
 */
public final class ConfigHelper {

    private static final Properties DB_CONFIG_PROPS = PropertiesUtil.loadProperties(ConfigConstant.DB_CONFIG_FILE);

    private static final Properties EASY_CONFIG_PROPS = PropertiesUtil.loadProperties(ConfigConstant.FRAMEWORK_CONFIG_FILE);

    /**
     * 获取jdbc驱动
     */
    public static String getJDBCDriver() {
        return PropertiesUtil.getString(DB_CONFIG_PROPS, ConfigConstant.JDBC_DRIVER);
    }

    /**
     * 获取jdbc url
     */
    public static String getJDBCUrl() {
        return PropertiesUtil.getString(DB_CONFIG_PROPS, ConfigConstant.JDBC_URL);
    }

    /**
     * 获取jdbc username
     */
    public static String getJDBCUsername() {
        return PropertiesUtil.getString(DB_CONFIG_PROPS, ConfigConstant.JDBC_USERNAME);
    }

    /**
     * 获取jdbc password
     */
    public static String getJDBCPassword() {
        return PropertiesUtil.getString(DB_CONFIG_PROPS, ConfigConstant.JDBC_PASSWORD);
    }

    /**
     * 获取应用基础包名
     */
    public static String getAppBasePackage() {
        return PropertiesUtil.getString(EASY_CONFIG_PROPS, ConfigConstant.APP_BASE_PACKAGE);
    }

    /**
     * 获取应用JSP路径
     */
    public static String getAppJspPath() {
        return PropertiesUtil.getString(EASY_CONFIG_PROPS, ConfigConstant.APP_JSP_PATH, "/WEB-INF/view/");
    }

    /**
     * 获取应用精通资源路径
     */
    public static String getAppAssetPath() {
        return PropertiesUtil.getString(EASY_CONFIG_PROPS, ConfigConstant.APP_ASSET_PATH, "/asset/");
    }
}
