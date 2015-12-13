package org.easy4j.service;

import org.easy4j.model.Customer;

import java.util.List;
import java.util.Map;

/**
 * Created by vonvey on 15/11/3.
 */
public class CustomerService {

    public List<Customer> getCustomerList(String keyword) {
        //TODO
        return null;
    }

    public Customer getCustomer(long id) {
        //TODO
        return null;
    }

    public boolean createCustomer(Map<String, Object> fieldMap) {
        return true;
    }

    public boolean updateCustomer(long id, Map<String, Object> fieldMap) {
        return true;
    }

    public boolean deleteCustomer(long id) {
        return true;
    }
}
