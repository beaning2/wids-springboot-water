package com.wids.service.impl;

import com.wids.entities.Customer;
import com.wids.mapper.CustomerMappper;
import com.wids.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerServiceImpl implements CustomerService {

    @Autowired
    private CustomerMappper customerMappper;

    @Override
    public List<Customer> listCustomer() {
        return customerMappper.selectList(null);
    }
}
