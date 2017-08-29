package com.yuyu.soft.entity;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.yuyu.soft.entity.common.CommonEntity;

/**
 * 日程安排提醒表
 *                       
 * @Filename: ScheduleSmsRemind.java
 * @Version: 1.0
 * @Author: 李明
 * @Email: limn_xmj@163.com
 *
 */
@Entity
@Table(name = "tb_schedule_sms_remind")
public class ScheduleSmsRemind extends CommonEntity {

    /**
     *Comment for <code>serialVersionUID</code>
     */
    private static final long serialVersionUID = 1L;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    @JsonIgnore
    private User              user;                 //被提醒人
    private String            user_mobile;          //被提醒手机号
    private String            remind_name;          //提醒名称
    private String            remind_content;       //提醒内容
    private String            remind_time;          //提醒时间（多个，用逗号隔开）

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getUser_mobile() {
        return user_mobile;
    }

    public void setUser_mobile(String user_mobile) {
        this.user_mobile = user_mobile;
    }

    public String getRemind_name() {
        return remind_name;
    }

    public void setRemind_name(String remind_name) {
        this.remind_name = remind_name;
    }

    public String getRemind_content() {
        return remind_content;
    }

    public void setRemind_content(String remind_content) {
        this.remind_content = remind_content;
    }

    public String getRemind_time() {
        return remind_time;
    }

    public void setRemind_time(String remind_time) {
        this.remind_time = remind_time;
    }

}