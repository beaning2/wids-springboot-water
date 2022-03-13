package com.wids.lambda.impl;

import com.wids.lambda.Calc;

public class CalcImpl implements Calc {
    /**
     * 计算行为：列乘以行
     *
     * @param first  列值
     * @param second 行值
     * @return 列乘以行的乘积
     */
    @Override
    public int doCalc(int first, int second) {
        return first * second;
    }
}
