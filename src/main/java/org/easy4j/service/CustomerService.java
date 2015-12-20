package org.easy4j.service;

import org.easy4j.model.Customer;
import org.easy4j.util.DatabaseHelper;
import org.easy4j.util.PropertiesUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.xml.crypto.Data;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Properties;

/**
 * Created by vonvey on 15/11/3.
 */
public class CustomerService {

    private static final Logger LOGGER = LoggerFactory.getLogger(CustomerService.class);

    public List<Customer> getCustomerList(String keyword) {
        String sql = "SELECT * FROM customer";
        return DatabaseHelper.queryEntryList(Customer.class, sql);
    }

    public Customer getCustomer(long id) {
        String sql = "SELECT * FROM customer WHERE id=" + id;
        return DatabaseHelper.queryEntry(Customer.class, sql);
    }

    public boolean createCustomer(Map<String, Object> fieldMap) {
        return DatabaseHelper.insertEntity(Customer.class, fieldMap);
    }

    public boolean updateCustomer(long id, Map<String, Object> fieldMap) {
        return DatabaseHelper.updateEntity(Customer.class, id, fieldMap);
    }

    public boolean deleteCustomer(long id) {
        return DatabaseHelper.deleteEntity(Customer.class, id);
    }
}
