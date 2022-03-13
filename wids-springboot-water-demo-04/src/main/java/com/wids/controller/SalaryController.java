package com.wids.controller;

import com.wids.entities.Salary;
import com.wids.service.SalaryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

/**
 * 计算工资模块的控制器
 */
@Controller
@RequestMapping("/salary")
@Slf4j
public class SalaryController {

    @Autowired
    private SalaryService salaryService;

    /**
     * 点击“计算工资”按钮，后端使用/calcSalaryList路径对应的方法处理前端的请求，将工资列表返回给前端
     * 步骤：
     * 1 调用业务逻辑接口SalaryService的calcSalary方法查询送水工的工资
     * 2 将送水工工资渲染到前端
     * 3 跳转到计算工资列表页面
     * @param model
     * @return 跳转到计算工资列表页面
     */
    @RequestMapping("/calcSalaryList")
    public String calcSalary(Model model) {
        List<Salary> saList = salaryService.calcSalary();
        log.info("salary List ="+saList.toString());
        model.addAttribute("saList",saList);
        return "salaryList";
    }

    /**
     * 前端输入开始时间和结束时间，点击搜索。后端使用/searchSalaryList路径对应的方法处理前端的请求
     * 步骤：
     * 1 打印前端传入到后端的参数2
     * 2 调用salaryService接口的calcSalaryByCondition方法查询满足条件的送水工工资
     * 3 将查询结果渲染到前端页面
     * 4 调整到送水工工资页面
     * @param beginDate 开始时间
     * @param endDate 结束时间
     * @param model
     * @return 送水工工资页面
     */
    @RequestMapping("/searchSalaryList")
    public String searchSalary(String beginDate , String endDate , Model model) {
        log.info("searchSalary beginDate = "+beginDate);
        log.info("searchSalary endDate = "+endDate);
        List<Salary> saList = salaryService.calcSalaryByCondition(beginDate, endDate);
        log.info("salary List ="+saList.toString());
        model.addAttribute("saList",saList);
        // 搜索条件重新传递到计算工资列表页面
        model.addAttribute("searchBeginDate",beginDate);
        model.addAttribute("searchEndDate",endDate);
        return "salaryList";
    }
}
