package com.wids.service.impl;

import cn.hutool.core.util.NumberUtil;
import com.wids.entities.History;
import com.wids.mapper.HistoryMapper;
import com.wids.service.HistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;


@Service
public class HistoryServiceImpl implements HistoryService {

    @Autowired
    private HistoryMapper historyMapper;

    /**
     * 查询所有的送水历史信息
     *
     * @return 送水历史列表
     */
    @Override
    public List<History> listHistory() {
        return historyMapper.listHistory();
    }

    /**
     * 添加送水工信息
     *
     * @param history
     * @return 受影响的行数。大于0添加成功，否则添加失败
     */
    @Override
    public int saveHistory(History history) {
        return historyMapper.saveHistory(history);
    }

    /**
     * 根据送水历史ID查询对应的送水历史信息
     *
     * @param hid 送水历史ID
     * @return 送水历史信息
     */
    @Override
    public History getHistoryById(Integer hid) {
        return historyMapper.getHistoryById(hid);
    }

    /**
     * 修改送水历史
     *
     * @param history 前端采集的送水历史信息
     * @return 受影响的行数。 大于0修改成功，否则修改失败
     */
    @Override
    public int updateHistory(History history) {
        return historyMapper.updateHistory(history);
    }

    /**
     *  @Transactional被该注解修饰的方法能够支持事务
     *  rollbackFor = Exception.class表示一旦程序遇到Exception或者Exception的子类将会进行回滚操作，回滚到事务开启之前的状态，
     *  或者说回滚到batchDeleteHistory方法调用之前的状态
     *
     * 批量删除
     * 步骤：
     * 1 将字符串转换为List<Integer>集合
     * 2 调用MyBatis-Plus内置的方法执行批量删除操作，并且返回批量删除受影响的行数
     * @param ids 前端传入的id列表
     * @return 受影响行数，大于0批量删除成功，否则批量删除失败
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public int batchDeleteHistory(String ids) {
        // 将将字符串转换为数组使用半角逗号进行切割
        String[] split = ids.split(",");
        List<Integer> idList = new ArrayList<>();
        // 遍历数组每个元素
        for (String id : split) {
            // 判断id是否为数字，如果条件成立将其放入到idList集合
            if (NumberUtil.isInteger(id)) {
                idList.add(Integer.parseInt(id));
            }
        }
        // 调用MyBatis内置的方法执行“批量删除”操作
        int rows = historyMapper.deleteBatchIds(idList);
        // 程序执行到92行会抛出异常(算术异常)
        // System.out.println(1/0);
        return rows;
    }
}
