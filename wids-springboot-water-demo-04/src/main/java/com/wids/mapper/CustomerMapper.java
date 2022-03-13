package com.wids.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.wids.entities.Customer;
import org.springframework.stereotype.Repository;

/**
 * 客户管理的映射器，它在数据库列和实体属性之间做映射
 * 跟之前大家学习的DAO类似
 */
@Repository
public interface CustomerMapper extends BaseMapper<Customer> {


}
