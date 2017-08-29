package com.yuyu.soft.service;

import java.util.List;
import java.util.Map;

import com.yuyu.soft.entity.TimerLogs;
import com.yuyu.soft.util.PagerInfo;

/**
 * 定时任务日志接口
 *                       
 * @Filename: ITimerLogsService.java
 * @Version: 1.0
 * @Author: 李明
 * @Email: limn_xmj@163.com
 *
 */
public interface ITimerLogsService {

    /**
     * 查询定时任务日志列表
     * @param hql
     * @param paramsMap
     * @param pager
     * @return
     */
    List<TimerLogs> queryTimerLogs(String hql, Map<String, Object> paramsMap, PagerInfo pager);

    /**
     * 根据ID获取定时任务日志对象
     * @param id
     * @return
     */
    TimerLogs getTimerLogs(Long id);

    /**
     * 添加定时任务日志
     * @param timerLogs
     */
    void addTimerLogs(TimerLogs timerLogs);

    /**
     * 更新定时任务日志
     * @param timerLogs
     */
    public void updateTimerLogs(TimerLogs timerLogs);

    /**
     * 保存日志
     */
    public TimerLogs saveLogs(String timer_log_name);

    /**
     * 更新日志
     */
    public void updateLogs(TimerLogs timerLogs);

}
