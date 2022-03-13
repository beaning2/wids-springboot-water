package com.wids.service;

import com.wids.entities.WaterDetails;

import java.util.List;

/**
 * 送水详细信息模块对应的业务逻辑接口
 */
public interface WaterDetailsService {
    /**
     * 查询每个送水工为客户送水的详细信息
     * @return 送水详细信息列表
     */
    List<WaterDetails> listWaterDetails();
}
