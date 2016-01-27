package org.easy4j.framework;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.easy4j.framework.bean.Data;
import org.easy4j.framework.bean.Handler;
import org.easy4j.framework.bean.Param;
import org.easy4j.framework.bean.View;
import org.easy4j.framework.helper.BeanHelper;
import org.easy4j.framework.helper.ConfigHelper;
import org.easy4j.framework.helper.ControllerHelper;
import org.easy4j.framework.helper.ServletHelper;
import org.easy4j.framework.util.CodecUtil;
import org.easy4j.framework.util.JsonUtil;
import org.easy4j.framework.util.ReflectionUtil;
import org.easy4j.framework.util.StreamUtil;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

/**
 * @author fengwei (ivonvey@gmail.com) //
 * @date 15/12/27 19:26
 */
@WebServlet(urlPatterns = "/*", loadOnStartup = 0)
public class DispatcherServlet extends HttpServlet {
    @Override
    public void init(ServletConfig config) throws ServletException {
        //初始化相关Helper类
        HelperLoader.init();
        //获取ServletContext(用于注册Servlet)
        ServletContext sc = config.getServletContext();
        //注册处理JSP的Servlet
        ServletRegistration jspServlet = sc.getServletRegistration("jsp");
        jspServlet.addMapping(ConfigHelper.getAppJspPath() + "*");
        //注册处理静态资源的默认Servlet
        ServletRegistration defaultServlet = sc.getServletRegistration("default");
        defaultServlet.addMapping(ConfigHelper.getAppAssetPath() + "*");
    }

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ServletHelper.init(req, resp);
        try {
            //获取请求方法与请求路径
            String requestMethod = req.getMethod().toLowerCase();
            String requestPath = req.getPathInfo();
            //获取Action处理器
            Handler handler = ControllerHelper.getHandler(requestMethod, requestPath);
            if (handler != null) {
                //获取Controller类及其Bean实例
                Class<?> controllerClass = handler.getControllerClass();
                Object controllerBean = BeanHelper.getBean(controllerClass);
                //创建请求参数对象
                Map<String, Object> paramMap = new HashMap<String, Object>();
                Enumeration<String> paramNames = req.getParameterNames();
                while (paramNames.hasMoreElements()) {
                    String paramName = paramNames.nextElement();
                    String paramValue = req.getParameter(paramName);
                    paramMap.put(paramName, paramValue);
                }
                String body = CodecUtil.decodeURL(StreamUtil.getString(req.getInputStream()));
                if (StringUtils.isNotEmpty(body)) {
                    String[] params = body.split("&");
                    if (ArrayUtils.isNotEmpty(params)) {
                        for (String param : params) {
                            String[] array = param.split("=");
                            if (ArrayUtils.isNotEmpty(array) && array.length == 2) {
                                if (StringUtils.isNotEmpty(array[0]) && StringUtils.isNotEmpty(array[1])) {
                                    paramMap.put(array[0], array[1]);
                                }
                            }
                        }
                    }
                }

                Method actionMethod = handler.getActionMethod();
                Object result;
                //判断paramMap是否为空，如果为空不传paramMap方法
                if (paramMap.isEmpty() && actionMethod.getParameterCount() == 0) {
                    result = ReflectionUtil.invokeMethod(controllerBean, actionMethod);

                } else {
                    Param param = new Param(paramMap);
                    //调用Action方法
                    result = ReflectionUtil.invokeMethod(controllerBean, actionMethod, param);
                }

                //处理Action方法的返回值
                if (result instanceof View) {
                    //返回JSP页面
                    View view = (View) result;
                    String path = view.getPath();
                    if (StringUtils.isNotEmpty(path)) {
                        if (path.startsWith("/")) {
                            resp.sendRedirect(req.getContextPath() + path);
                        } else {
                            Map<String, Object> model = view.getModel();
                            for (Map.Entry<String, Object> entry : model.entrySet()) {
                                req.setAttribute(entry.getKey(), entry.getValue());
                            }
                            req.getRequestDispatcher(ConfigHelper.getAppJspPath() + path).forward(req, resp);
                        }
                    }
                } else if (result instanceof Data) {
                    //返回JSON数据
                    Data data = (Data) result;
                    Object model = data.getModel();
                    if (model != null) {
                        resp.setContentType("application/json");
                        resp.setCharacterEncoding("UTF-8");
                        PrintWriter printWriter = resp.getWriter();
                        String json = JsonUtil.toJson(model);
                        printWriter.write(json);
                        printWriter.flush();
                        printWriter.close();
                    }
                }
            } else {
                //handler not found
                resp.sendError(HttpServletResponse.SC_NOT_FOUND);
            }
        } finally {
            ServletHelper.destroy();
        }
    }

}
