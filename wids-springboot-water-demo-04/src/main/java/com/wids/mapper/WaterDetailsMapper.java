package com.wids.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.wids.entities.WaterDetails;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 送水详细信息模块对应的映射器
 */
@Repository
public interface WaterDetailsMapper extends BaseMapper<WaterDetails> {
    /**
     * 查询每个送水工为客户送水的详细信息
     * @return 送水详细信息列表
     */
    List<WaterDetails> listWaterDetails();
}
