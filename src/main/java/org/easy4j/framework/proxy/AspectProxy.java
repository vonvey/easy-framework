package org.easy4j.framework.proxy;

/**
 * @author fengwei (ivonvey@gmail.com) //
 * @date 15/12/29 22:47
 */

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Method;

/**
 * 切面代理
 */
public abstract class AspectProxy implements Proxy {
    private static final Logger LOGGER = LoggerFactory.getLogger(AspectProxy.class);

    @Override
    public final Object doProxy(ProxyChain proxyChain) throws Throwable {
        Object result = null;
        Class<?> cls = proxyChain.getTargetClass();
        Method method = proxyChain.getTargetMethod();
        Object[] params = proxyChain.getMethodParams();

        begin();
        try {
            if (intercept(cls, method, params)) {
                before(cls, method, params);
                result = proxyChain.doProxyChain();
                after(cls, method, params, result);
            }
        } catch (Exception e) {
            LOGGER.error("proxy failure", e);
            error(cls, method, params, e);
            throw e;
        } finally {
            end();
        }
        return result;
    }

    protected void begin() {}

    protected boolean intercept(Class<?> cls, Method method, Object[] params)
            throws Throwable {
        return true;
    }

    protected void before(Class<?> cls, Method method, Object[] params)
            throws Throwable {
    }

    protected void after(Class<?> cls, Method method, Object[] params, Object result)
            throws Throwable {
    }

    protected void error(Class<?> cls, Method method, Object[] params, Throwable e) {
    }

    protected void end(){
    }
}
