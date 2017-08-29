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
 * 合同到期提醒表
 *
 */
@Entity
@Table(name = "tb_contract_sms_remind")
public class ContractSmsRemind extends CommonEntity {

    /**
     *Comment for <code>serialVersionUID</code>
     */
    private static final long serialVersionUID = 1L;

    private Integer 		  remind_type;          //提醒类型：0岗位，1个人
    
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    @JsonIgnore
    private User              user;                 //被提醒人
    
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "duty_id")
    @JsonIgnore
    private Duty			  duty;     			//被提醒岗位
    
    private String            remind_content;       //发送内容
    private Date			  expire_time;          //合同到期时间
    private Integer           remind_time;          //提前发送时间（天）
	public Integer getRemind_type() {
		return remind_type;
	}
	public void setRemind_type(Integer remind_type) {
		this.remind_type = remind_type;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public Duty getDuty() {
		return duty;
	}
	public void setDuty(Duty duty) {
		this.duty = duty;
	}
	public String getRemind_content() {
		return remind_content;
	}
	public void setRemind_content(String remind_content) {
		this.remind_content = remind_content;
	}
	public Date getExpire_time() {
		return expire_time;
	}
	public void setExpire_time(Date expire_time) {
		this.expire_time = expire_time;
	}
	public Integer getRemind_time() {
		return remind_time;
	}
	public void setRemind_time(Integer remind_time) {
		this.remind_time = remind_time;
	}

    
}