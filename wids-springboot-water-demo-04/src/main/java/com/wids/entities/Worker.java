package com.wids.entities;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Worker实体类
 */
@TableName("tb_worker")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Worker {

    /**
     * 送水工ID
     */
    private Integer wid;
    /***
     * 送水工名称
     */
    private String workerName;
    /**
     * 送水工底薪
     */
    private Integer workerSalary;
    /**
     * 送水工提成
     */
    private Double workerMoney;
    /**
     * 送水工照片，此时存储照片的路径，通过路径找到对应的照片
     */
    private String workerImage;
}
