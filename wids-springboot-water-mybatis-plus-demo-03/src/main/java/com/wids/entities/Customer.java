package com.wids.entities;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@TableName("tb_customer")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Customer {
    private Integer cid;
    /**
     * 客户名称
     */
    private String custName;
    /**
     * 客户地址
     */
    private String custAddress;
    /**
     * 客户联系方式
     */
    private String custMobile;
    /**
     * 客户水票
     */
    private Integer custTicket;
}
