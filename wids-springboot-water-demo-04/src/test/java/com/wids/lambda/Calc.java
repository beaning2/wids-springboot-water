package com.wids.lambda;

/**
 * 99乘法表需要用到的计算接口
 */
@FunctionalInterface
public interface Calc {

    /**
     * 计算行为：列乘以行
     * @param first 列值
     * @param second 行值
     * @return 列乘以行的乘积
     */
    int doCalc(int first , int second);
    // Lambda表达式依赖的接口只能有一个抽象方法
    // void a();
}
