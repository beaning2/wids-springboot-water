package com.wids.mapper;

import cn.hutool.crypto.digest.DigestUtil;
import org.junit.jupiter.api.Test;

/**
 * 密码加密
 */
public class EncodingTest {

    /**
     * 将明文密码使用MD5加密转换为密文密码
     */
    @Test
    public void encoding() {
        // 明文密码
        String password = "admin";
        // 调用胡图工具包的数字工具类对明文密码admin进行MD5加密，加密之后的密文是一串十六进制表示的字符串
        // admin----> 21232f297a57a5a743894a0e4a801fc3
        String encoding  = DigestUtil.md5Hex(password);
        System.out.println(encoding);
    }
}
