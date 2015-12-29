package org.easy4j.framework.proxy;

/**
 * @author fengwei (ivonvey@gmail.com) //
 * @date 15/12/29 22:26
 */

/**
 * 代理接口
 */
public interface Proxy {
    /**
     * 执行链式代理
     */
    Object doProxy(ProxyChain proxyChain) throws  Throwable;
}
