package com.wids.entities;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 账户实体类
 * 该类有：id 用户名 密码 邮箱 手机号等属性
 * @TableName("tb_account")表示Account实体类映射的表名称为tb_account
 */
@TableName("tb_account")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Account {

    /**
     * 主键
      */
    private  Integer aid;
    /**
     * 用户名
     */
    private String userName;
    /**
     * 密码
     */
    private String userPwd;
    /**
     * 手机号码
     */
    private String userMobile;
    /**
     * 邮箱
     */
    private String userMail;
}
