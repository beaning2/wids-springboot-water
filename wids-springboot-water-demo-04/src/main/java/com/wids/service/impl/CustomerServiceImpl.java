package com.wids.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.wids.entities.Customer;
import com.wids.mapper.CustomerMapper;
import com.wids.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 客户管理接口实现类
 */
@Service
public class CustomerServiceImpl implements CustomerService {

    @Autowired
    private CustomerMapper customerMapper;

    /**
     * 查询客户表所有的数据
     *步骤：
     * 1 调用customerMapper提供的selectList方法查询所有数据
     * 2 返回客户列表
     * @return 返回客户列表
     */
    @Override
    public List<Customer> custList() {
        return customerMapper.selectList(null);
    }

    /**
     * 添加客户信息
     *
     * @param customer 前端采集的客户信息
     * @return 大于0：添加成功。 否则：添加失败
     */
    @Override
    public int saveCust(Customer customer) {
        return customerMapper.insert(customer);
    }

    /**
     * 根据客户名称搜索对应的客户信息
     * 步骤：
     * 1 创建QueryWrapper对象
     * 2 封装查询条件
     * 3 调用customerMapper接口的selectList方法，并且将QueryWrapper对象注入到该方法中
     * 4 返回搜索的结果
     * @param custName 前端表单采集的客户名称
     * @return 符合条件的客户列表信息
     */
    @Override
    public List<Customer> searchCustomerList(String custName) {
        QueryWrapper<Customer> queryWrapper = new QueryWrapper<>();
        queryWrapper.like("cust_name",custName);
        return customerMapper.selectList(queryWrapper);
    }

    /**
     * 根据客户ID删除客户信息
     * 步骤：
     *  1 创建QueryWrapper对象
     *  2 封装要删除的where条件
     *  3 调用customMapper的delete方法完成删除，并且传入QueryWrapper对象
     *  4 返回删除受影响的行数
     * @param cid 前端传入的客户id
     * @return 受影响行数。大于0：删除成功，否则：删除失败
     */
    @Override
    public int deleteCustomerById(Integer cid) {
        QueryWrapper<Customer> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("cid",cid);
        return customerMapper.delete(queryWrapper);
    }

    /**
     * 根据客户id查询客户信息，该方法用来做修改的数据回显
     * 步骤：
     * 1 创建QueryWrapper对象
     * 2 封装查询条件
     * 3 调用customerMapper的selectOne方法并且输入查询条件，查询指定的客户信息
     * 4 返回客户信息
     * @param cid 前端传入的客户id
     * @return 客户信息
     */
    @Override
    public Customer getCustomerById(Integer cid) {
        QueryWrapper<Customer> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("cid",cid);
        return customerMapper.selectOne(queryWrapper);
    }

    /**
     * 修改客户信息
     * 步骤：
     * 1 创建QueryWrapper对象
     * 2 封装update语句后面的where条件
     * 3 调用customerMapper接口的update方法并将QueryWrapper对象注入到该方法中完成修改
     * 4 返回受影响的行数
     * @param customer 前端页面采集的要修改的客户信息
     * @return 受影响行数。大于0修改成功，否则修改失败
     */
    @Override
    public int updateCustomer(Customer customer) {
        QueryWrapper<Customer> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("cid",customer.getCid());
        return customerMapper.update(customer,queryWrapper);
    }
}
