package org.easy4j.framework.helper;

import org.easy4j.framework.annotation.Aspect;
import org.easy4j.framework.proxy.AspectProxy;
import org.easy4j.framework.proxy.Proxy;
import org.easy4j.framework.proxy.ProxyChain;
import org.easy4j.framework.proxy.ProxyManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.annotation.Annotation;
import java.util.*;

/**
 * @author fengwei (ivonvey@gmail.com) //
 * @date 15/12/29 23:01
 */

/**
 * 方法拦截助手类
 */
public final class AopHelper {

    private static final Logger LOGGER = LoggerFactory.getLogger(AopHelper.class);
    static {
        try {
            Map<Class<?>, Set<Class<?>>> proxyMap = createProxyMap();
            Map<Class<?>, List<Proxy>> targetMap = createTargetMap(proxyMap);
            for (Map.Entry<Class<?>, List<Proxy>> target : targetMap.entrySet()) {
                Class<?> targetClass = target.getKey();
                List<Proxy> proxyList = target.getValue();
                Object proxy = ProxyManager.createProxy(targetClass, proxyList);

                BeanHelper.putBean(targetClass, proxy);
            }

        } catch (Throwable e) {
            LOGGER.error("aop failure", e);
        }
    }

    /**
     * 获取所有Aspect注解中设置的注解类
     *  @Aspect(@Controller)
     */
    private static Set<Class<?>> createTargetClassSet(Aspect aspect) throws Throwable {
        Set<Class<?>> classSet = new HashSet<>();

        Class<? extends Annotation> annotation = aspect.value();
        if (annotation != null && !annotation.equals(Aspect.class)) {
            classSet.addAll(ClassHelper.getClassSetByAnnotation(annotation));
        }
        return classSet;
    }

    private static Map<Class<?>, Set<Class<?>>> createProxyMap() throws Throwable {
        Map<Class<?>, Set<Class<?>>> proxyMap = new HashMap<Class<?>, Set<Class<?>>>();
        Set<Class<?>> proxyClassSet = ClassHelper.getClassSetBySuperClass(AspectProxy.class);

        for (Class<?> proxyClass : proxyClassSet) {
            Aspect aspect = proxyClass.getAnnotation(Aspect.class);
            Set<Class<?>> targetClassSet = createTargetClassSet(aspect);
            proxyMap.put(proxyClass, targetClassSet);
        }

        return proxyMap;
    }

    private static Map<Class<?>, List<Proxy>> createTargetMap(Map<Class<?>, Set<Class<?>>> proxyMap) throws Throwable {
        Map<Class<?>, List<Proxy>> targetMap = new HashMap<Class<?>, List<Proxy>>();
        for (Map.Entry<Class<?>, Set<Class<?>>> proxyEntry : proxyMap.entrySet()) {
            Class<?> proxyClass = proxyEntry.getKey();
            Set<Class<?>> targetClassSet = proxyEntry.getValue();
            for (Class<?> targetClass : targetClassSet) {
                Proxy proxy = (Proxy) proxyClass.newInstance();
                if (targetMap.containsKey(targetClass)) {
                    targetMap.get(targetClass).add(proxy);
                } else {
                    List<Proxy> proxyList = new ArrayList<Proxy>();
                    proxyList.add(proxy);
                    targetMap.put(targetClass, proxyList);
                }
            }
        }

        return targetMap;
    }

}
