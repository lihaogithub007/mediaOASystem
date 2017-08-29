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

@Entity
@Table(name = "tb_resume_work")
public class ResumeWork extends CommonEntity {

    /**
     *Comment for <code>serialVersionUID</code>
     */
    private static final long serialVersionUID = 1L;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "resume_id")
    private Resume            resume;               //简历
    private Date              work_time_begin;      //起始时间
    private Date              work_time_end;        //截止时间
    private String            work_comp_name;       //工作单位
    private String            work_duty_name;       //岗位
    @Column(precision = 12, scale = 2)
    private BigDecimal        month_salary;         //月收入税前
    private Integer           work_type;            //工作性质：1.全职 2.兼职 3.实习
    private String            voucher_name;         //证明人
    private String            voucher_mobile;       //证明人电话

    public Resume getResume() {
        return resume;
    }

    public void setResume(Resume resume) {
        this.resume = resume;
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

    public BigDecimal getMonth_salary() {
        return month_salary;
    }

    public void setMonth_salary(BigDecimal month_salary) {
        this.month_salary = month_salary;
    }

    public Integer getWork_type() {
        return work_type;
    }

    public void setWork_type(Integer work_type) {
        this.work_type = work_type;
    }

    public String getVoucher_name() {
        return voucher_name;
    }

    public void setVoucher_name(String voucher_name) {
        this.voucher_name = voucher_name;
    }

    public String getVoucher_mobile() {
        return voucher_mobile;
    }

    public void setVoucher_mobile(String voucher_mobile) {
        this.voucher_mobile = voucher_mobile;
    }
}
