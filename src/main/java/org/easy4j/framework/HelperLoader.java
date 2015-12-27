package org.easy4j.framework;

/**
 * @author fengwei (ivonvey@gmail.com) //
 * @date 15/12/27 18:36
 */

import org.easy4j.framework.helper.BeanHelper;
import org.easy4j.framework.helper.ClassHelper;
import org.easy4j.framework.helper.ControllerHelper;
import org.easy4j.framework.helper.IocHelper;
import org.easy4j.framework.util.ClassUtil;

/**
 * 加载相应的Helper类
 */
public final class HelperLoader {
    public static void init() {
        Class<?>[] classList = {
                ClassHelper.class,
                BeanHelper.class,
                IocHelper.class,
                ControllerHelper.class
        };
        for (Class<?> cls : classList) {
            ClassUtil.loadClass(cls.getName(), true);
        }
    }
}
