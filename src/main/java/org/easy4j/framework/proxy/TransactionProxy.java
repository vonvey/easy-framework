package org.easy4j.framework.proxy;

import org.easy4j.framework.annotation.Transaction;
import org.easy4j.framework.helper.DatabaseHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Method;

/**
 * @author fengwei (ivonvey@gmail.com) //
 * @date 15/12/31 00:05
 */

/**
 * 事务代理
 */
public class TransactionProxy implements Proxy {
    private static final Logger LOGGER = LoggerFactory.getLogger(TransactionProxy.class);

    /**
     * ThreadLocal
     */
    private static final ThreadLocal<Boolean> FLAG_HODER = new ThreadLocal<Boolean>(){
        @Override
        protected Boolean initialValue() {
            return false;
        }
    };

    @Override
    public Object doProxy(ProxyChain proxyChain) throws Throwable {
        Object result;
        Boolean flag = FLAG_HODER.get();
        Method method = proxyChain.getTargetMethod();
        if (!flag && method.isAnnotationPresent(Transaction.class)) {
            FLAG_HODER.set(true);
            try {
                DatabaseHelper.beginTransaction();
                LOGGER.debug("begin transaction");
                result = proxyChain.doProxyChain();
                DatabaseHelper.commitTransaction();
                LOGGER.debug("commit transaction");
            } catch (Exception e) {
                LOGGER.error(" rollback transaction", e);
                DatabaseHelper.rollbackTransaction();
                throw e;
            }
        } else {
            result = proxyChain.doProxyChain();
        }

        return result;
    }
}
