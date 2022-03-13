package com.wids.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.wids.entities.Salary;
import com.wids.entities.Worker;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 计算工资对应的映射器
 */
@Repository
public interface SalaryMapper extends BaseMapper<Salary> {

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
    List<Salary> calcSalaryByCondition(@Param("beginDate") String beginDate , @Param("endDate") String endDate);

    /**
     * 没有在指定时间段为客户送过水的送水工信息
     * @param beginDate 开始时间，不能为空
     * @param endDate 结束时间，可以为空。如果为空，默认显示当前时间。例如：2022-02-28
     * @return 没有为客户送过水的送水工信息列表
     */
    List<Worker> calcSalaryNonByCondition(@Param("beginDate") String beginDate , @Param("endDate") String endDate);
}
