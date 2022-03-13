package com.wids.service;

import com.wids.entities.Worker;

import java.util.List;

/**
 * 客户管理的业务逻辑接口，封装了客户管理需要的业务逻辑方法
 */
public interface WorkerService {

    /**
     * 查询所有的客户信息
     */
    List<Worker> workerList();

    /**
     * 添加送水工
     * @param worker 前端采集的送水工信息
     * @return 受影响行数。 大于0添加成功，否则添加失败
     */
    int saveWorker(Worker worker);

    /**
     * 根据送水工ID查询送水工信息
     * @param wid 送水工ID
     * @return 送水工信息
     */
    Worker getWorkerById(Integer wid);

    /**
     * 修改送水工
     * @param worker 前端采集的送水工信息
     * @return 修改受影响的行数。大于0修改成功，否则修改失败
     */
    int updateWorker(Worker worker);

    /**
     * 调整工资
     * @param wid 送水工ID
     * @param workerSalary 送水工调整之后的工资
     * @return 受影响行数，大于0:调整工资成功。 否则：调整工资失败
     */
    int adjustSalary(Integer wid , Integer workerSalary);
}
