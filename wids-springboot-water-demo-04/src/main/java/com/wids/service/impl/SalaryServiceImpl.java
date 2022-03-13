package com.wids.service.impl;

import cn.hutool.core.util.StrUtil;
import com.wids.entities.Salary;
import com.wids.entities.Worker;
import com.wids.mapper.SalaryMapper;
import com.wids.service.SalaryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class SalaryServiceImpl implements SalaryService {

    @Autowired
    private SalaryMapper salaryMapper;

    /**
     * 计算每个送水工的工资
     * 步骤： 调用SalaryMapper接口的calcSalary方法查询所有送水工的工资
     * @return 工资列表
     */
    @Override
    public List<Salary> calcSalary() {
        return salaryMapper.calcSalary();
    }

    /**
     * 根据指定的区间条件(2022-02-01 ~ 2022-02-28)，查询某一时间段的送水工工资
     * 步骤：
     * 1 判断参数endDate是否为空，如果为空？将系统当前日期赋值给endDate
     * 2 调用salaryMapper接口的calcSalaryByCondition方法查询满足条件的送水工工资信息，并将结果返回
     * 修复BUG：没有在指定时间段为客户送过水的送水工信息需要加入到List<Salary> 列表
     * 步骤：
     * 1 查询没有在指定时间段为客户送过水的送水工信息，并且返回列表List<Worker>
     * 2 将List<Worker>里面的元素添加到List<Salary> 列表
     * 2.1 遍历List<Worker>里面的每个元素
     * 2.2 将List<Worker>里面Worker元素(对象)，添加到List<Salary>
     * 2.3 没有在指定时间段为客户送过水的送水工“实发工资”默认为基本工资，送水数量默认为0
     * @param beginDate 开始时间，不能为空
     * @param endDate 结束时间，可以为空。如果为空，默认显示当前时间。例如：2022-02-28
     * @return  送水工工资列表(送水工名称、底薪、提成、送水数量、实发工资)
     *
     * 修复BUG小结：
     * 1 数据库的SQL语句需要将多个结果集联合成一个。核心union关键字
     * 2 Java的业务逻辑层需要使用JDK8提供的Lambda表达式，将多个结果集的数据联合成一个
     * 知识点：JDK8提供的Lambda表达式
     *           List集合的stream()方法，将集合转换为流(流水线)
     *           Stream流水线的map方法，该方法用来提取数据，此时提取Salary对象的学生姓名
     *           Stream流水线的collect方法，将流水线对象Stream重新转换为集合
     *           ::表示方法引用
     *           forEach方法表示遍历集合中的袁术
     *           -> 是Lambda表达式的核心，就是面向过程的goto，用于跳转
     * Java后端：所有的业务都在service层
     */
    @Override
    public List<Salary> calcSalaryByCondition(String beginDate, String endDate) {
        // 条件成立：表示endDate为空.将系统当前日期赋值给endDate
        if (StrUtil.isEmpty(endDate)) {
            Date date = new Date(System.currentTimeMillis());
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            endDate = sdf.format(date);
        }
        // 在指定区间段为客户送过水的送水工工资列表
        List<Salary> salaryList = salaryMapper.calcSalaryByCondition(beginDate, endDate);
        // 没有在指定时间段为客户送过水的送水工信息列表
        List<Worker> workerList = salaryMapper.calcSalaryNonByCondition(beginDate, endDate);
        // 提取salaryList集合的送水工名称，将提取的数据转换为一个新集合
        // salaryList.stream()将集合转换为流(流水线)，
        // .map(Salary::getWorkerName) 表示将Salary对象的workerName属性提取出来
        // .collect(Collectors.toList());表示将提取出来的workerName存放到一个集合中
        // Salary::getWorkerName ::表示方法引用(调用)，此时引用(调用)了Salary对象的getWorkerName方法
        List<String> workerNameList = salaryList.stream()
                .map(Salary::getWorkerName)
                .collect(Collectors.toList());
        // 遍历workerList的每个元素将其添加到salaryList里面
        // forEach方法表示遍历workerList集合里面的每个元素，此时的worker就是集合中的元素
        workerList.forEach(worker->{
            // 修复显示数据重复的BUG，当worker对象的送水工名称不在Salary里面，将Worker对象信息添加到Salary中
            // 提取salaryList里面的送水工名称，判断该名称是否在worker对象的workerName中，如果不存在将其放入Salary
            if (!workerNameList.contains(worker.getWorkerName())) {
                // 将Worker对象信息重新注入到Salary对象
                Salary salary = new Salary();
                salary.setWorkerName(worker.getWorkerName());
                salary.setWorkerMoney(worker.getWorkerMoney());
                salary.setWorkerSalary(worker.getWorkerSalary());
                // 没有在指定时间段为客户送过水的送水工“实发工资”默认为基本工资，送水数量默认为0
                salary.setSendWaterCount(0);
                salary.setFinalSalary(Double.valueOf(worker.getWorkerSalary()));
                salaryList.add(salary);
            }
        });
        // BUG：没有对“实发工资”按照降序排序。解决方案：对“实发工资”进行比较，然后按照降序排序
        Collections.sort(salaryList, new Comparator<Salary>() {
            @Override
            public int compare(Salary o1, Salary o2) {
                // 对实发工资进行比较，然后针对比较的结果进行排序
                if (o1.getFinalSalary() > o2.getFinalSalary()) {
                    return -1;
                } else if (o1.getFinalSalary() < o2.getFinalSalary()) {
                    return 1;
                } else {
                    return 0;
                }
            }
        });
        return salaryList;
    }
}
