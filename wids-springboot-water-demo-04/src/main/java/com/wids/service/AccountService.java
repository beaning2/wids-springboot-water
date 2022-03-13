package com.wids.service;

/**
 * 账户管理业务逻辑接口
 */
public interface AccountService {


    /**
     * 用户登录的业务逻辑
     * 步骤：
     * 1 根据用户名查询对应的账户信息
     * 2 如果账户为null，返回false，登录失败
     * 3 如果账户非空，对参数userPwd进行MD5加密
     * 4 和数据库的密码进行比较
     * 5 如果两个密文密码相等，登录成功，否则：登录失败
     * @param userName 用户名
     * @param userPwd 密码
     * @return true登录成功，false登录失败
     */
    boolean login(String userName , String userPwd);
}
