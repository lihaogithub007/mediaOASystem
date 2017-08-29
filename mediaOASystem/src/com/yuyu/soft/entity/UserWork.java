package com.yuyu.soft.entity;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.yuyu.soft.entity.common.CommonEntity;

@Entity
@Table(name = "tb_user_work")
public class UserWork extends CommonEntity {

    /**
     *Comment for <code>serialVersionUID</code>
     */
    private static final long serialVersionUID = 1L;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User              user;                 //用户
    private Date              work_time_begin;      //起始时间
    private Date              work_time_end;        //截止时间
    private String            work_comp_name;       //工作单位
    private String            work_duty_name;       //岗位
    private String            work_place;           //工作地点
    private Integer           work_type;            //工作性质：1.全职 2.兼职 3.实习

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Date getWork_time_begin() {
        return work_time_begin;
    }

    public void setWork_time_begin(Date work_time_begin) {
        this.work_time_begin = work_time_begin;
    }

    public Date getWork_time_end() {
        return work_time_end;
    }

    public void setWork_time_end(Date work_time_end) {
        this.work_time_end = work_time_end;
    }

    public String getWork_comp_name() {
        return work_comp_name;
    }

    public void setWork_comp_name(String work_comp_name) {
        this.work_comp_name = work_comp_name;
    }

    public String getWork_duty_name() {
        return work_duty_name;
    }

    public void setWork_duty_name(String work_duty_name) {
        this.work_duty_name = work_duty_name;
    }

    public String getWork_place() {
        return work_place;
    }

    public void setWork_place(String work_place) {
        this.work_place = work_place;
    }

    public Integer getWork_type() {
        return work_type;
    }

    public void setWork_type(Integer work_type) {
        this.work_type = work_type;
    }

}
