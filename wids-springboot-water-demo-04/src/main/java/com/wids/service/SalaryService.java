package com.wids.service;

import com.wids.entities.Salary;

import java.util.List;

public interface SalaryService {
    /**
     * 计算每个送水工的工资
     * @return 工资列表
     */
    List<Salary> calcSalary();

    /**
     * 查询某一时间段，送水工的工资信息
     * @param beginDate 开始时间，不能为空
     * @param endDate 结束时间，可以为空。如果为空，默认显示当前时间。例如：2022-02-28
     * @return 送水工的工资列表
     */
    List<Salary> calcSalaryByCondition(String beginDate , String endDate);
}
