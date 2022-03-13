package com.wids.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.wids.entities.Worker;
import com.wids.mapper.WorkerMapper;
import com.wids.service.WorkerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 送水工管理的业务逻辑接口实现类
 * @Service注解用在业务逻辑层。 SpringBoot容器启动的时候会创建WorkerServiceImpl对象，并且注入到Spring容器的Bean中
 */
@Service
public class WorkerServiceImpl implements WorkerService {

    /**
     * 将WorkerMapper映射器注入到业务逻辑层
     */
    @Autowired
    private WorkerMapper workerMapper;

    /**
     * 查询所有的客户信息
     */
    @Override
    public List<Worker> workerList() {
        return workerMapper.selectList(null);
    }

    /**
     * 添加送水工
     *
     * @param worker 前端采集的送水工信息
     * @return 受影响行数。 大于0添加成功，否则添加失败
     */
    @Override
    public int saveWorker(Worker worker) {
        return workerMapper.insert(worker);
    }

    /**
     * 根据送水工ID查询送水工信息
     *
     * @param wid 送水工ID
     * @return 送水工信息
     */
    @Override
    public Worker getWorkerById(Integer wid) {
        QueryWrapper<Worker> queryWrapper  = new QueryWrapper<>();
        queryWrapper.eq("wid",wid);
        return workerMapper.selectOne(queryWrapper);
    }

    /**
     * 修改送水工
     *
     * @param worker 前端采集的送水工信息
     * @return 修改受影响的行数。大于0修改成功，否则修改失败
     */
    @Override
    public int updateWorker(Worker worker) {
        QueryWrapper<Worker> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("wid",worker.getWid());
        return workerMapper.update(worker,queryWrapper);
    }

    /**
     * 调整工资
     * 步骤：
     * 1 创建Worker对象
     * 2 将参数注入到对象中
     * 3 创建QueryWrapper对象
     * 4 将update语句后面的where条件注入到QueryWrapper对象
     * 5 调用workerMapper对象的update方法来更新工资
     * @param wid          送水工ID
     * @param workerSalary 送水工调整之后的工资
     * @return 受影响行数，大于0:调整工资成功。 否则：调整工资失败
     */
    @Override
    public int adjustSalary(Integer wid, Integer workerSalary) {
        Worker worker = new Worker();
        worker.setWorkerSalary(workerSalary);

        QueryWrapper<Worker> queryWrapper = new QueryWrapper();
        queryWrapper.eq("wid",wid);
        return workerMapper.update(worker,queryWrapper);
    }
}
