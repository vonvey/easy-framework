package org.easy4j.controller;

import org.easy4j.framework.annotation.Action;
import org.easy4j.framework.annotation.Controller;
import org.easy4j.framework.annotation.Inject;
import org.easy4j.framework.bean.Param;
import org.easy4j.framework.bean.View;
import org.easy4j.framework.helper.ServletHelper;
import org.easy4j.model.Customer;
import org.easy4j.service.CustomerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.Servlet;
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
    public View customer(Param param) {
        ServletHelper.setRequestAttribute("key", "value");
        List<Customer> customerList = customerService.getCustomerList("");
        return new View("customer.jsp").addModel("customerList",customerList);
    }
}
