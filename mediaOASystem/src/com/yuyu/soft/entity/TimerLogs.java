package com.yuyu.soft.entity;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Table;

import com.yuyu.soft.entity.common.CommonEntity;

/**
 * 定时任务日志表                      
 * @Filename: TimerLogs.java
 * @Version: 1.0
 * @Author: 李明
 * @Email: limn_xmj@163.com
 *
 */
@Entity
@Table(name = "tb_timer_logs")
public class TimerLogs extends CommonEntity {

    /**
     *Comment for <code>serialVersionUID</code>
     */
    private static final long serialVersionUID = 1L;

    private String            timer_log_name;
    private Date              beginTime;
    private Date              endTime;

    public String getTimer_log_name() {
        return timer_log_name;
    }

    public void setTimer_log_name(String timer_log_name) {
        this.timer_log_name = timer_log_name;
    }

    public Date getBeginTime() {
        return beginTime;
    }

    public void setBeginTime(Date beginTime) {
        this.beginTime = beginTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

}