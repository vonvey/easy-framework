package org.easy4j.framework.helper;

import org.apache.commons.lang3.ArrayUtils;
import org.easy4j.framework.annotation.Inject;
import org.easy4j.framework.util.ReflectionUtil;
import org.easy4j.framework.util.CollectionUtil;

import java.lang.reflect.Field;
import java.util.Map;

/**
 * @author fengwei (ivonvey@gmail.com) //
 * @date 15/12/27 17:56
 */
public final class IocHelper {

    static {
        //获取所欲的Bean类与Bean实例之间的映射关系（简称Bean Map）
        Map<Class<?>, Object> beanMap = BeanHelper.getBeanMap();
        if (!CollectionUtil.isEmpty(beanMap)) {
            //遍历Bean map
            for (Map.Entry<Class<?>, Object> beanEntry : beanMap.entrySet()) {
                //从BeanMap中获取Bean类与Bean实例
                Class<?> beanClass = beanEntry.getKey();
                Object beanInstance = beanEntry.getValue();
                //获取Bean类定义的所有成员变量（简称Bean Field）
                Field[] beanFields = beanClass.getDeclaredFields();
                if (ArrayUtils.isNotEmpty(beanFields)) {
                    //遍历Bean Field
                    for (Field field : beanFields) {
                        //判断当前Bean Field是否带有Inject注解
                        if (field.isAnnotationPresent(Inject.class)) {
                            //在Bean Map中获取Bean Field对应的实例
                            Class<?> beanFieldClass = field.getType();
                            Object beanFieldInstance = beanMap.get(beanFieldClass);
                            if (beanFieldInstance != null) {
                                //通过反射初始化BeanField的值
                                ReflectionUtil.setField(beanInstance, field, beanFieldInstance);
                            }
                        }
                    }
                }
            }
        }
    }
}
