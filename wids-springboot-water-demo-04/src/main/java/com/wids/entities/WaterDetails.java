package com.wids.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 送水详细信息
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class WaterDetails {
    /**
     * 送水工名称
     */
    private String workerName;
    /**
     * 客户详细信息，例如：“张三，李四”
      */
    private String custDetails;
    /**
     * 每个送水工送水的数量
     */
    private Integer sendWaterCount;
}
