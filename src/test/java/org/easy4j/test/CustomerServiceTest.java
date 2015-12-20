package org.easy4j.test;

import org.easy4j.model.Customer;
import org.easy4j.service.CustomerService;
import org.easy4j.util.DatabaseHelper;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by vonvey on 15/11/3.
 */
public class CustomerServiceTest {
    private final CustomerService customerService;

    public CustomerServiceTest () {
        customerService = new CustomerService();
    }

    @Before
    public void init() throws Exception {
        //初始化数据库
        DatabaseHelper.executeFile("sql/customer_init.sql");
    }

    @Test
    public void getCustomerListTest() throws Exception {
        List<Customer> list = customerService.getCustomerList("");
        Assert.assertTrue(list.size() > 0);
        Assert.assertNotNull(list);
    }

    @Test
    public void createCustomerTest() throws Exception {
        Map<String, Object> fieldMap = new HashMap<String, Object>();
        fieldMap.put("name", "Tom");
        fieldMap.put("contact", "tom@qq.com");
        fieldMap.put("telephone", "13333333333");
        boolean result = customerService.createCustomer(fieldMap);
        Assert.assertTrue(result);
    }

    @Test
    public void updateCustomerTest() throws Exception {
        long id = 1;
        Map<String, Object> fieldMap = new HashMap<String, Object>();
        fieldMap.put("telephone", "13433333333");
        boolean result = customerService.updateCustomer(id, fieldMap);
        Assert.assertTrue(result);
    }

    @Test
    public void deleteCustomerTest() throws Exception {
        long id = 1;
        boolean result = customerService.deleteCustomer(id);
        Assert.assertTrue(result);
    }

    @After
    public void after() {

    }

}
