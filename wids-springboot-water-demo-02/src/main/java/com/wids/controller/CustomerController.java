package com.wids.controller;

import com.wids.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CustomerController {

    @Autowired
    private CustomerService customerService;

    @GetMapping("/allCust")
    public String listCUstomer(){
        return customerService.listCustomer().toString();
    }
}
