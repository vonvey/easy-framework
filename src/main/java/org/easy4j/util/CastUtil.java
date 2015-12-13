package org.easy4j.util;

/**
 * @author fengwei (ivonvey@gmail.com) //
 * @date 15/12/13 23:04
 */

import org.apache.commons.lang3.StringUtils;

/**
 * 类型转换工具类
 */
public class CastUtil {

    /**
     * 转为String类型
     */
    public static String castString(Object obj) {
        return castString(obj, "");
    }

    /**
     * 转为String类型, 提供默认值
     */
    public static String castString(Object obj, String defalutValue) {
        return obj == null ? defalutValue : String.valueOf(obj);
    }

    /**
     * 转为int类型
     */
    public static int castInt(Object obj) {
        return castInt(obj, 0);
    }

    /**
     * 转为int类型, 提供默认值
     */
    public static int castInt(Object obj, int defaultValue) {
        int intValue = defaultValue;
        if (obj != null) {
            String strValue = castString(obj);
            if (StringUtils.isNotEmpty(strValue)) {
                try {
                    intValue = Integer.parseInt(strValue);
                } catch (NumberFormatException e) {
                    intValue = defaultValue;
                }
            }
        }
        return intValue;
    }

    /**
     * 转为boolean类型
     */
    public static boolean castBoolean(Object obj) {
        return castBoolean(obj, false);
    }

    /**
     * 转为boolean类型, 提供默认值
     */
    public static boolean castBoolean(Object obj, boolean defaultValue) {
        boolean booleanValue = defaultValue;
        if (obj != null) {
           booleanValue = Boolean.parseBoolean(castString(obj));
        }
        return booleanValue;
    }
}
