package org.easy4j.framework.helper;

/**
 * @author fengwei (ivonvey@gmail.com) //
 * @date 15/12/27 18:20
 */

import org.apache.commons.lang3.ArrayUtils;
import org.apache.shiro.util.AntPathMatcher;
import org.easy4j.framework.annotation.Action;
import org.easy4j.framework.bean.Handler;
import org.easy4j.framework.bean.Request;
import org.easy4j.framework.util.CollectionUtil;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * 控制器助手类
 */
public final class ControllerHelper {
    /**
     * 用于存放请求与处理器的映射关系（简称Action Map）
     */
    private static final Map<Request, Handler> ACTION_MAP = new HashMap<Request, Handler>();

    static {
        //获取所有Controller类
        Set<Class<?>> controllerClassSet = ClassHelper.getControllerClassSet();
        if (!CollectionUtil.isEmpty(controllerClassSet)) {
            //遍历Controller类
            for (Class<?> cls : controllerClassSet) {
                //获取Controller类中定义的方法
                Method[] methods = cls.getDeclaredMethods();
                if (ArrayUtils.isNotEmpty(methods)) {
                    //遍历Controller类中的方法
                    for (Method method : methods) {
                        //判断当期方法是否带有Action注解
                        if (method.isAnnotationPresent(Action.class)) {
                            //从Action注解中获取URL映射规则
                            Action action = method.getAnnotation(Action.class);
                            if (action != null) {
                                String mapping = action.value();
                                //验证映射规则

                                //正则表达式
                                //if (mapping.matches("\\w+:/\\w*")) {
                                    String[] array = mapping.split(":");
                                    if (ArrayUtils.isNotEmpty(array) && array.length == 2) {
                                        //构造Request和Handler
                                        Request request = new Request(array[0], array[1]);
                                        Handler handler = new Handler(cls, method);
                                        //初始化Action Map
                                        ACTION_MAP.put(request, handler);
                                    }
                                //}
                            }
                        }
                    }
                }
            }
        }
    }

    /**
     * 获取Handler
     */
    public static Handler getHandler(String requestMethod, String requestPath) {
        //先直接获取
        Handler handler = ACTION_MAP.get(new Request(requestMethod, requestPath));
        AntPathMatcher matcher = new AntPathMatcher();
        //如果没有
        if (handler == null) {
            for (Map.Entry<Request, Handler> entry : ACTION_MAP.entrySet()) {
                Request request = entry.getKey();
                if (request.getRequestMethod().equals(requestMethod)) {
                    if (matcher.match(request.getRequestPath(), requestPath)) {
                        handler = entry.getValue();
                        break;
                    }
                }
            }
        }

        return handler;
    }
}
