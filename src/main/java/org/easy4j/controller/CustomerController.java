package org.easy4j.controller;

import org.easy4j.framework.annotation.Action;
import org.easy4j.framework.annotation.Controller;
import org.easy4j.framework.annotation.Inject;
import org.easy4j.framework.bean.Param;
import org.easy4j.framework.bean.View;
import org.easy4j.model.Customer;
import org.easy4j.service.CustomerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * Created by vonvey on 15/11/3.
 */
@Controller
public class CustomerController {
    private final static Logger LOGGER = LoggerFactory.getLogger(CustomerController.class);
    @Inject
    private CustomerService customerService;

    @Action("get:/customer")
    public View index(Param param) {
        List<Customer> customerList = customerService.getCustomerList("");
        return new View("customer.jsp").addModel("customerList",customerList);
    }
}
