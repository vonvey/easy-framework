package org.easy4j.controller;

import org.easy4j.framework.annotation.Aspect;
import org.easy4j.framework.annotation.Controller;
import org.easy4j.framework.proxy.AspectProxy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Method;

/**
 * @author fengwei (ivonvey@gmail.com) //
 * @date 15/12/30 10:27
 */
@Aspect(Controller.class)
public class CustomerAspect2 extends AspectProxy {
    private final static Logger LOGGER = LoggerFactory.getLogger(CustomerAspect2.class);

    @Override
    public void begin() {
        LOGGER.error("2 Customer begin");
    }

    @Override
    protected void before(Class<?> cls, Method method, Object[] params) throws Throwable {
        super.before(cls, method, params);
    }

    @Override
    protected void after(Class<?> cls, Method method, Object[] params, Object result) throws Throwable {
        super.after(cls, method, params, result);
    }

    @Override
    protected void error(Class<?> cls, Method method, Object[] params, Throwable e) {
        super.error(cls, method, params, e);
    }

    @Override
    protected void end() {
        LOGGER.error("2 Customer end");
    }
}
