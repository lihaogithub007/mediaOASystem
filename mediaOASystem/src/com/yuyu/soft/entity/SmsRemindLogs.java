package com.yuyu.soft.entity;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.yuyu.soft.entity.common.CommonEntity;

/**
 * 短信提醒日志表                      
 * @Filename: TimerLogs.java
 *
 */
@Entity
@Table(name = "tb_sms_remind_logs")
public class SmsRemindLogs extends CommonEntity {

    /**
     *Comment for <code>serialVersionUID</code>
     */
    private static final long serialVersionUID = 1L;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    @JsonIgnore
    private User              		user;                   //被提醒人
    private String            		log_name;				//日志名称
	private String                  log_info;				//日志信息
	private Boolean                 log_result;   	        //0：失败，1：成功
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public String getLog_name() {
		return log_name;
	}
	public void setLog_name(String log_name) {
		this.log_name = log_name;
	}
	public String getLog_info() {
		return log_info;
	}
	public void setLog_info(String log_info) {
		this.log_info = log_info;
	}
	public Boolean getLog_result() {
		return log_result;
	}
	public void setLog_result(Boolean log_result) {
		this.log_result = log_result;
	}
	
	
    
    
    
}