package com.wids.service.impl;

import com.wids.entities.WaterDetails;
import com.wids.mapper.WaterDetailsMapper;
import com.wids.service.WaterDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 送水详细信息模块对应的业务逻辑实现类
 */
@Service
public class WaterDetailsServiceImpl implements WaterDetailsService {

    @Autowired
    private WaterDetailsMapper waterDetailsMapper;

    /**
     * 查询每个送水工为客户送水的详细信息
     *
     * @return 送水详细信息列表
     */
    @Override
    public List<WaterDetails> listWaterDetails() {
        return waterDetailsMapper.listWaterDetails();
    }
}
