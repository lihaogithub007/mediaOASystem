package com.yuyu.soft.entity;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.yuyu.soft.entity.common.CommonEntity;

/**
 * 请假申请明细                      
 * @Filename: VacationApplyDetails.java
 * @Version: 1.0
 * @Author: 李明
 * @Email: limn_xmj@163.com
 *
 */
@Entity
@Table(name = "tb_vacation_apply_details")
public class VacationApplyDetails extends CommonEntity {

    /**
     *Comment for <code>serialVersionUID</code>
     */
    private static final long serialVersionUID = 1L;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User              user;                 //用户

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "vacation_apply_id")
    private VacationApply     vacationApply;        //请假申请

    private Integer           leave_type;           //请假种类：1.年假 2.病假 3.事假 4.产检 5.产假 6.婚假 7.丧假

    private Date              begin_time;           //休假开始日期
    private Date              end_time;             //休假结束日期
    @Column(precision = 4, scale = 1)
    private BigDecimal        dates;                //天数

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public VacationApply getVacationApply() {
        return vacationApply;
    }

    public void setVacationApply(VacationApply vacationApply) {
        this.vacationApply = vacationApply;
    }

    public Integer getLeave_type() {
        return leave_type;
    }

    public void setLeave_type(Integer leave_type) {
        this.leave_type = leave_type;
    }

    public Date getBegin_time() {
        return begin_time;
    }

    public void setBegin_time(Date begin_time) {
        this.begin_time = begin_time;
    }

    public Date getEnd_time() {
        return end_time;
    }

    public void setEnd_time(Date end_time) {
        this.end_time = end_time;
    }

    public BigDecimal getDates() {
        return dates;
    }

    public void setDates(BigDecimal dates) {
        this.dates = dates;
    }
}
