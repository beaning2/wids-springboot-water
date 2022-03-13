package com.wids.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.wids.entities.Account;
import org.springframework.stereotype.Repository;

/**
 * 账户管理的映射器，在数据库表和Java实体类之间做映射
 * @Repository 是数据访问层的注解，Spring容器启动的时候会注入该接口的实现类
 */
@Repository
public interface AccountMapper extends BaseMapper<Account> {
}
