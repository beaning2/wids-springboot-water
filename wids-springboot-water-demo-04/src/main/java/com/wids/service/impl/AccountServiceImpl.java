package com.wids.service.impl;

import cn.hutool.crypto.digest.DigestUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.wids.entities.Account;
import com.wids.mapper.AccountMapper;
import com.wids.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;

/**
 * 账户管理业务逻辑层接口实现类
 */
@Service
public class AccountServiceImpl implements AccountService {
    /**
     * 业务逻辑接口依赖Mapper，在此根据雷兴国注入Mapper对象
     */
    @Autowired
    private AccountMapper accountMapper;

    /**
     * 用户登录的业务逻辑
     * 步骤：
     * 1 根据用户名查询对应的账户信息
     * 2 如果账户为null，返回false，登录失败
     * 3 如果账户非空，对参数userPwd进行MD5加密
     * 4 和数据库的密码进行比较
     * 5 如果两个密文密码相等，登录成功，否则：登录失败
     *
     * @param userName 用户名
     * @param userPwd  密码
     * @return true登录成功，false登录失败
     */
    @Override
    public boolean login(String userName, String userPwd) {
        QueryWrapper<Account> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_name",userName);
        Account account = accountMapper.selectOne(queryWrapper);
        // 条件成立：登录失败
        if (null == account) {
            return false;
        }
        // 对参数密码加密，和数据库的密码进行比较
        String encoding = DigestUtil.md5Hex(userPwd);
        // 条件成立： 表示前端输入密码和数据库密码一致，登录成功
        if (Objects.equals(encoding,account.getUserPwd())) {
            return true;
        }
        return false;
    }
}
