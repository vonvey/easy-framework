package org.easy4j.test;

import org.easy4j.model.Customer;
import org.easy4j.service.CustomerService;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;
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
    public void init() {
        //TODO 初始化数据库
    }

    @Test
    public void getCustomerListTest() throws Exception {
        long id = 1;
        Customer customer = customerService.getCustomer(id);
        Assert.assertNotNull(customer);
    }

    @Test
    public void createCustomerTest() throws Exception {
        Map<String, Object> fieldMap = new HashMap<String, Object>();
        fieldMap.put("name", "Tom");
        fieldMap.put("contact", "tom@qq.com");
        fieldMap.put("telephont", "13333333333");
        boolean result = customerService.createCustomer(fieldMap);
        Assert.assertTrue(result);
    }

    @Test
    public void updateCustomerTest() throws Exception {
        long id = 1;
        Map<String, Object> fieldMap = new HashMap<String, Object>();
        fieldMap.put("telephont", "13433333333");
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
