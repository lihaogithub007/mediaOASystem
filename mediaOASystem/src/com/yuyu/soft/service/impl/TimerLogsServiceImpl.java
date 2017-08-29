package com.yuyu.soft.service.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;

import com.yuyu.soft.dao.IBaseDao;
import com.yuyu.soft.entity.TimerLogs;
import com.yuyu.soft.service.IDutyService;
import com.yuyu.soft.service.ITimerLogsService;
import com.yuyu.soft.util.CommUtil;
import com.yuyu.soft.util.PagerInfo;

/**
 * 部门管理
 *                       
 * @Filename: TimerLogsServiceImpl.java
 * @Version: 1.0
 * @Author: 李明
 * @Email: limn_xmj@163.com
 *
 */
@Service("timerLogsService")
public class TimerLogsServiceImpl implements ITimerLogsService {

    protected static final Log  log = LogFactory.getLog(TimerLogsServiceImpl.class);
    @Resource
    private IBaseDao<TimerLogs> timerLogsDao;
    @Resource
    private IDutyService        dutyService;

    @Override
    public List<TimerLogs> queryTimerLogs(String hql, Map<String, Object> paramsMap, PagerInfo pager) {
        if (pager != null) {
            pager.setRowsCount(timerLogsDao.count("select count(t) " + hql, paramsMap));
            return timerLogsDao.find(hql, paramsMap, pager.getStart(), pager.getPageSize());
        } else {
            return timerLogsDao.find(hql, paramsMap);
        }

    }

    @Override
    public TimerLogs getTimerLogs(Long id) {
        return timerLogsDao.get(TimerLogs.class, id);
    }

    @Override
    public void addTimerLogs(TimerLogs timerLogs) {
        this.timerLogsDao.save(timerLogs);
    }

    @Override
    public void updateTimerLogs(TimerLogs timerLogs) {
        this.timerLogsDao.update(timerLogs);
    }

    @Override
    public TimerLogs saveLogs(String timer_log_name) {
        try {
            TimerLogs log = new TimerLogs();
            log.setDisabled(false);
            log.setCreatetime(new Date());
            log.setBeginTime(new Date());
            log.setTimer_log_name(timer_log_name);
            addTimerLogs(log);
            return log;
        } catch (Exception e) {
            e.printStackTrace();
            log.error("定时任务日志记录失败，时间：" + CommUtil.getCurrentTime() + ", 日志名称：" + timer_log_name);
        }
        return null;
    }

    @Override
    public void updateLogs(TimerLogs timerLogs) {
        try {
            timerLogs = getTimerLogs(timerLogs.getId());
            timerLogs.setEndTime(new Date());
            updateTimerLogs(timerLogs);
        } catch (Exception e) {
            log.error("定时任务日志记录失败，时间：" + CommUtil.getCurrentTime());
        }
    }
}
