package com.wids.entities;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@TableName("tb_history")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class History {

    /**
     * 送水历史ID
     * @TableId为hid属性指定主键，还属性对应的主键列为hid，主键类型为ID自动增长
     */
    @TableId(value = "hid" , type = IdType.AUTO)
    private Integer hid;
    /**
     * 关联送水工实体
     */
    private Worker worker;
    /**
     * 关联客户实体
     */
    private Customer customer;

    /**
     * 送水时间
     *     @DateTimeFormat(pattern = "yyyy-MM-dd")表示已年月日的方式显示送水世家你
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date sendWaterTime;

    /**
     * 送水数量
     */
    private Integer sendWaterCount;
}
