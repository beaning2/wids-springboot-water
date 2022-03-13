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

    private Integer id;

    private String custName;

    private String custMobile;

    private String custAddress;

    private Integer custTicket;
}
