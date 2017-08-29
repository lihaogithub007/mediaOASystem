package com.yuyu.soft.entity;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Table;

import com.yuyu.soft.entity.common.CommonEntity;

/**
 * 节日短信问候表
 *                       
 * @Filename: HolidaySmsRemind.java
 * @Version: 1.0
 * @Author: 李明
 * @Email: limn_xmj@163.com
 *
 */
@Entity
@Table(name = "tb_holiday_sms_remind")
public class HolidaySmsRemind extends CommonEntity {

    /**
     *Comment for <code>serialVersionUID</code>
     */
    private static final long serialVersionUID = 1L;

    private String            remind_name;          //提醒名称
    private String            remind_content;       //提醒内容
    private Integer           remind_type;          //提醒类型: 0.节日短信 1.生日短信
    private Date              remind_time;          //提醒时间

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

    public Integer getRemind_type() {
        return remind_type;
    }

    public void setRemind_type(Integer remind_type) {
        this.remind_type = remind_type;
    }

    public Date getRemind_time() {
        return remind_time;
    }

    public void setRemind_time(Date remind_time) {
        this.remind_time = remind_time;
    }

}