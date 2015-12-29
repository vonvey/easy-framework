package org.easy4j.framework;

/**
 * @author fengwei (ivonvey@gmail.com) //
 * @date 15/12/27 18:36
 */

import org.easy4j.framework.helper.*;
import org.easy4j.framework.util.ClassUtil;

/**
 * 加载相应的Helper类
 */
public final class HelperLoader {
    public static void init() {
        Class<?>[] classList = {
                ClassHelper.class,
                BeanHelper.class,
                AopHelper.class,
                IocHelper.class,
                ControllerHelper.class
        };
        for (Class<?> cls : classList) {
            ClassUtil.loadClass(cls.getName(), true);
        }
    }
}
