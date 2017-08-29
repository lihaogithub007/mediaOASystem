package com.yuyu.soft.service;

import java.util.List;
import java.util.Map;

import com.yuyu.soft.entity.ScheduleSmsRemind;
import com.yuyu.soft.util.PagerInfo;
import com.yuyu.soft.util.ResultMsg;

/**
 * 日程安排提醒接口
 *                       
 * @Filename: IScheduleSmsRemindService.java
 * @Version: 1.0
 * @Author: 李明
 * @Email: limn_xmj@163.com
 *
 */
public interface IScheduleSmsRemindService {

    /**
     * 定时任务专用
     */
    List<ScheduleSmsRemind> queryScheduleSmsRemindForTimerTask(String current_time);

    /**
     * 查询日程安排提醒列表
     * @param hql
     * @param paramsMap
     * @param pager
     * @return
     */
    List<ScheduleSmsRemind> queryScheduleSmsRemind(String hql, Map<String, Object> paramsMap,
                                                   PagerInfo pager);

    /**
     * 根据ID获取日程安排提醒对象
     * @param id
     * @return
     */
    ScheduleSmsRemind getScheduleSmsRemind(Long id);

    /**
     * 添加日程安排提醒
     * @param scheduleSmsRemind
     */
    void addScheduleSmsRemind(ScheduleSmsRemind scheduleSmsRemind);

    /**
     * 更新日程安排提醒
     * @param scheduleSmsRemind
     */
    public void updateScheduleSmsRemind(ScheduleSmsRemind scheduleSmsRemind);

    /**
     * 删除日程安排提醒
     */
    public void delScheduleSmsRemind(ScheduleSmsRemind scheduleSmsRemind);

    /**
     * 添加日程安排提醒保存
     */
    public ResultMsg add_save(ScheduleSmsRemind scheduleSmsRemind, Long user_id);

    /**
     * 日程安排提醒编辑保存
     */
    public ResultMsg edit_save(ScheduleSmsRemind scheduleSmsRemind, Long user_id);

}
