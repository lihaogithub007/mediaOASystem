package com.yuyu.soft.entity;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.yuyu.soft.entity.common.CommonEntity;

/**
 * 加班记录表
 * @Filename: OvertimeRecord.java
 * @Version: 1.0
 * @Author: 李明
 * @Email: limn_xmj@163.com
 *
 */
@Entity
@Table(name = "tb_overtime_record")
public class OvertimeRecord extends CommonEntity {

    /**
     *Comment for <code>serialVersionUID</code>
     */
    private static final long serialVersionUID = 1L;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    @JsonIgnore
    private User              user;                 //用户

    private Integer           overtime_status;      //状态：1.工作日 2.休息日
    private Date              overtime_date;        //加班日期
    private Date              work_begin_time;      //上班起始时间
    private Date              work_end_time;        //上班结束时间
    private String            work_hours;           //加班时长：格式（小时:分钟）

    @Transient
    private Long              user_id;              //用户标识
    @Transient
    private String            overtime_datestr;     //加班日期
    @Transient
    private String            totalTime;            //加班总时长
    

	public String getTotalTime() {
		return totalTime;
	}

	public void setTotalTime(String totalTime) {
		this.totalTime = totalTime;
	}

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Integer getOvertime_status() {
        return overtime_status;
    }

    public void setOvertime_status(Integer overtime_status) {
        this.overtime_status = overtime_status;
    }

    public Date getOvertime_date() {
        return overtime_date;
    }

    public void setOvertime_date(Date overtime_date) {
        this.overtime_date = overtime_date;
    }

    public Date getWork_begin_time() {
        return work_begin_time;
    }

    public void setWork_begin_time(Date work_begin_time) {
        this.work_begin_time = work_begin_time;
    }

    public Date getWork_end_time() {
        return work_end_time;
    }

    public void setWork_end_time(Date work_end_time) {
        this.work_end_time = work_end_time;
    }

    public String getWork_hours() {
        return work_hours;
    }

    public void setWork_hours(String work_hours) {
        this.work_hours = work_hours;
    }

    public Long getUser_id() {
        return user_id;
    }

    public void setUser_id(Long user_id) {
        this.user_id = user_id;
    }

    public String getOvertime_datestr() {
        return overtime_datestr;
    }

    public void setOvertime_datestr(String overtime_datestr) {
        this.overtime_datestr = overtime_datestr;
    }

}
