package com.wids.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.wids.entities.Customer;
import org.springframework.stereotype.Repository;

/**
 * CustomerMapper是一个映射器，能够在数据库表和Java实体类之间做数据的映射
 * MyBatis-Plus提供了内置的CRUD方法，我们不用自己定义。但是需要继承BaseMapper，改接口封装了CRUD操作
 */
@Repository
public interface CustomerMapper extends BaseMapper<Customer> {
}
