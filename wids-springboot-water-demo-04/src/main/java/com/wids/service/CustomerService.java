package com.wids.service;

import com.wids.entities.Customer;

import java.util.List;

/**
 * 客户管理的业务逻辑接口
 * 封装了客户管理的业务逻辑
 */
public interface CustomerService {
    /**
     * 查询客户表所有的数据
     * @return 返回客户列表
     */
    List<Customer> custList();

    /**
     * 添加客户信息
     * @param customer 前端采集的客户信息
     * @return 大于0：添加成功。 否则：添加失败
     */
    int saveCust(Customer customer);

    /**
     * 根据客户名称搜索对应的客户信息
     * @param custName 前端表单采集的客户名称
     * @return 符合条件的客户列表信息
     */
    List<Customer> searchCustomerList(String custName);

    /**
     * 根据客户ID删除客户信息
     * @param cid 前端传入的客户id
     * @return 受影响行数。大于0：删除成功，否则：删除失败
     */
    int deleteCustomerById(Integer cid);

    /**
     * 根据客户id查询客户信息，该方法用来做修改的数据回显
     * @param cid 前端传入的客户id
     * @return 客户信息
     */
    Customer getCustomerById(Integer cid);

    /**
     * 修改客户信息
     * @param customer 前端页面采集的要修改的客户信息
     * @return 受影响行数。大于0修改成功，否则修改失败
     */
    int updateCustomer(Customer customer);
}
