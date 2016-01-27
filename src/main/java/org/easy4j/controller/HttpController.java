package org.easy4j.controller;

import org.apache.commons.lang3.StringUtils;
import org.easy4j.framework.annotation.Action;
import org.easy4j.framework.annotation.Controller;
import org.easy4j.framework.bean.Data;
import org.easy4j.framework.helper.ServletHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author fengwei (ivonvey@gmail.com) //
 * @date 16/1/19 20:34
 */
@Controller
public class HttpController {
    private final static Logger LOGGER = LoggerFactory.getLogger(HttpController.class);


    @Action("get:/basic")
    public Data basicAuthorization() {
        Data data = new Data();
        HttpServletRequest req = ServletHelper.getServletRequest();
        HttpServletResponse resp = ServletHelper.getServletResponse();
        String auth = req.getHeader(HttpHeaders.AUTHORIZATION);
        if (StringUtils.isEmpty(auth)) {
            resp.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            resp.addHeader(HttpHeaders.WWW_AUTHENTICATE, "Basic realm=\"Input Your ID and Password.\"");
        } else {
            data.setModel(auth);
        }

        return data;
    }

    @Action("get:/get*")
    public Data get() {
        Data data = new Data("get");
        return data;
    }
}
