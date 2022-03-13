package com.wids.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.wids.entities.History;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 送水历史映射器
 */
@Repository
public interface HistoryMapper extends BaseMapper<History> {
    /**
     * 查询所有的送水历史信息
     * @return
     */
    List<History> listHistory();

    /**
     * 添加送水工信息
     * @return 受影响的行数。大于0添加成功，否则添加失败
     */
    int saveHistory(History history);

    /**
     * 根据送水历史ID查询对应的送水历史信息
     * @param hid 送水历史ID
     * @return 送水历史信息
     */
    History getHistoryById(Integer hid);

    /**
     * 修改送水历史
     * @param history 前端采集的送水历史信息
     * @return 受影响的行数。 大于0修改成功，否则修改失败
     */
    int updateHistory(History history);
}
