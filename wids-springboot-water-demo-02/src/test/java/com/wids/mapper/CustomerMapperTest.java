package com.wids.mapper;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.wids.entities.Customer;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
public class CustomerMapperTest {

    @Autowired
    private CustomerMappper customerMappper;

    @Test
    public void listCustomerTest() {
        List<Customer> list = customerMappper.selectList(null);
        for (Customer customer : list) {
            System.out.println(customer);
        }
    }

    @Test
    public void selectOneTest(){
        QueryWrapper<Customer> qw = new QueryWrapper<>();
        qw.eq("id",1);
        Customer customer = customerMappper.selectOne(qw);
        System.out.println(customer);
    }
}
