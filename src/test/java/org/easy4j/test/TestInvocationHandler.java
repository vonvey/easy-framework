package org.easy4j.test;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * @author fengwei (ivonvey@gmail.com) //
 * @date 15/12/31 11:50
 */
public class TestInvocationHandler implements InvocationHandler {

    private Object obj;

    public TestInvocationHandler(Object obj) {
        this.obj = obj;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        return method.invoke(obj, args);
    }
}
