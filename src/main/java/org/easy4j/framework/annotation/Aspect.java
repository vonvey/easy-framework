package org.easy4j.framework.annotation;

import java.lang.annotation.*;

/**
 * @author fengwei (ivonvey@gmail.com) //
 * @date 15/12/29 22:23
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface Aspect {

    /**
     * 注解
     */
    Class<? extends Annotation> value();
}
