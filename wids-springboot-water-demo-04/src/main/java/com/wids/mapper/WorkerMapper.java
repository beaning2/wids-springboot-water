package com.wids.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.wids.entities.Worker;
import org.springframework.stereotype.Repository;

/**
 * 送水工管理对应的映射器，能够在数据库表的字段和实体类的属性之间做数据的映射
 */
@Repository
public interface WorkerMapper extends BaseMapper<Worker> {
}
