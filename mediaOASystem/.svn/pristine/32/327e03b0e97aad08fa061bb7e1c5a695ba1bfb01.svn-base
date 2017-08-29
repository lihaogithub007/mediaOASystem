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
@Table(name = "tb_attendance_record")
public class AttendanceRecord extends CommonEntity {

    private static final long serialVersionUID = 1L;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User              user;                         //用户ID
    private Date              attendance_record_month;      //考勤月份（年月）yyyy-MM
    private Integer           need_attendance_days;         //应出勤（天）
    @Column(precision = 3, scale = 1)
    private BigDecimal        real_attendance_days;         //实出勤（天） 
    @Column(precision = 3, scale = 1)
    private BigDecimal        official_business_days;       //公出（天）
    private Integer           not_clock_in_times;           //未打卡（次）
    private Integer           late_early_times;             //迟到早退（次）
    @Column(precision = 4, scale = 1)
    private BigDecimal        workday_overtime_hours;       //加班-工作日时长
    @Column(precision = 4, scale = 1)
    private BigDecimal        offday_overtime_hours;        //加班-休息日时长
    @Column(precision = 4, scale = 1)
    private BigDecimal        holiday_overtime_hours;       //加班-节假日时长
    @Column(precision = 4, scale = 1)
    private BigDecimal        this_month_shift_hours;       //调休-本月调休
    @Column(precision = 4, scale = 1)
    private BigDecimal        last_month_shift_remain_hours; //调休-上月剩余
    @Column(precision = 4, scale = 1)
    private BigDecimal        this_month_shift_remain_hours; //调休-本月剩余
    @Column(precision = 3, scale = 1)
    private BigDecimal        absent_days;                  //旷工（天）       
    @Column(precision = 3, scale = 1)
    private BigDecimal        sick_leave_days;              //病假（天）       
    @Column(precision = 3, scale = 1)
    private BigDecimal        casual_leave_days;            //事假（天）       
    @Column(precision = 3, scale = 1)
    private BigDecimal        marriage_leave_days;          //婚假（天）        
    @Column(precision = 3, scale = 1)
    private BigDecimal        funeral_leave_days;           //丧假（天）       
    @Column(precision = 3, scale = 1)
    private BigDecimal        this_month_annual_leave_days; //本月年假（天）       
    @Column(precision = 3, scale = 1)
    private BigDecimal        total_annual_leave_days;      //累计年假（天）
    @Column(precision = 3, scale = 1)
    private BigDecimal        antenatal_examination_days;   //产检（天）
    @Column(precision = 3, scale = 1)
    private BigDecimal        maternity_leave_days;         //产假（天）
    @Column(precision = 3, scale = 1)
    private BigDecimal        legal_holiday_days;           //公休日
    private String            remark;                       //其他说明
    
    private String 			  employee_number;              //员工编号
    

    public String getEmployee_number() {
		return employee_number;
	}

	public void setEmployee_number(String employee_number) {
		this.employee_number = employee_number;
	}

	public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Date getAttendance_record_month() {
        return attendance_record_month;
    }

    public void setAttendance_record_month(Date attendance_record_month) {
        this.attendance_record_month = attendance_record_month;
    }

    public Integer getNeed_attendance_days() {
        return need_attendance_days;
    }

    public void setNeed_attendance_days(Integer need_attendance_days) {
        this.need_attendance_days = need_attendance_days;
    }

    public BigDecimal getReal_attendance_days() {
        return real_attendance_days;
    }

    public void setReal_attendance_days(BigDecimal real_attendance_days) {
        this.real_attendance_days = real_attendance_days;
    }

    public BigDecimal getOfficial_business_days() {
        return official_business_days;
    }

    public void setOfficial_business_days(BigDecimal official_business_days) {
        this.official_business_days = official_business_days;
    }

    public Integer getNot_clock_in_times() {
        return not_clock_in_times;
    }

    public void setNot_clock_in_times(Integer not_clock_in_times) {
        this.not_clock_in_times = not_clock_in_times;
    }

    public Integer getLate_early_times() {
        return late_early_times;
    }

    public void setLate_early_times(Integer late_early_times) {
        this.late_early_times = late_early_times;
    }

    public BigDecimal getWorkday_overtime_hours() {
        return workday_overtime_hours;
    }

    public void setWorkday_overtime_hours(BigDecimal workday_overtime_hours) {
        this.workday_overtime_hours = workday_overtime_hours;
    }

    public BigDecimal getOffday_overtime_hours() {
        return offday_overtime_hours;
    }

    public void setOffday_overtime_hours(BigDecimal offday_overtime_hours) {
        this.offday_overtime_hours = offday_overtime_hours;
    }

    public BigDecimal getHoliday_overtime_hours() {
        return holiday_overtime_hours;
    }

    public void setHoliday_overtime_hours(BigDecimal holiday_overtime_hours) {
        this.holiday_overtime_hours = holiday_overtime_hours;
    }

    public BigDecimal getThis_month_shift_hours() {
        return this_month_shift_hours;
    }

    public void setThis_month_shift_hours(BigDecimal this_month_shift_hours) {
        this.this_month_shift_hours = this_month_shift_hours;
    }

    public BigDecimal getLast_month_shift_remain_hours() {
        return last_month_shift_remain_hours;
    }

    public void setLast_month_shift_remain_hours(BigDecimal last_month_shift_remain_hours) {
        this.last_month_shift_remain_hours = last_month_shift_remain_hours;
    }

    public BigDecimal getThis_month_shift_remain_hours() {
        return this_month_shift_remain_hours;
    }

    public void setThis_month_shift_remain_hours(BigDecimal this_month_shift_remain_hours) {
        this.this_month_shift_remain_hours = this_month_shift_remain_hours;
    }

    public BigDecimal getAbsent_days() {
        return absent_days;
    }

    public void setAbsent_days(BigDecimal absent_days) {
        this.absent_days = absent_days;
    }

    public BigDecimal getSick_leave_days() {
        return sick_leave_days;
    }

    public void setSick_leave_days(BigDecimal sick_leave_days) {
        this.sick_leave_days = sick_leave_days;
    }

    public BigDecimal getCasual_leave_days() {
        return casual_leave_days;
    }

    public void setCasual_leave_days(BigDecimal casual_leave_days) {
        this.casual_leave_days = casual_leave_days;
    }

    public BigDecimal getMarriage_leave_days() {
        return marriage_leave_days;
    }

    public void setMarriage_leave_days(BigDecimal marriage_leave_days) {
        this.marriage_leave_days = marriage_leave_days;
    }

    public BigDecimal getFuneral_leave_days() {
        return funeral_leave_days;
    }

    public void setFuneral_leave_days(BigDecimal funeral_leave_days) {
        this.funeral_leave_days = funeral_leave_days;
    }

    public BigDecimal getThis_month_annual_leave_days() {
        return this_month_annual_leave_days;
    }

    public void setThis_month_annual_leave_days(BigDecimal this_month_annual_leave_days) {
        this.this_month_annual_leave_days = this_month_annual_leave_days;
    }

    public BigDecimal getTotal_annual_leave_days() {
        return total_annual_leave_days;
    }

    public void setTotal_annual_leave_days(BigDecimal total_annual_leave_days) {
        this.total_annual_leave_days = total_annual_leave_days;
    }

    public BigDecimal getAntenatal_examination_days() {
        return antenatal_examination_days;
    }

    public void setAntenatal_examination_days(BigDecimal antenatal_examination_days) {
        this.antenatal_examination_days = antenatal_examination_days;
    }

    public BigDecimal getMaternity_leave_days() {
        return maternity_leave_days;
    }

    public void setMaternity_leave_days(BigDecimal maternity_leave_days) {
        this.maternity_leave_days = maternity_leave_days;
    }

    public BigDecimal getLegal_holiday_days() {
        return legal_holiday_days;
    }

    public void setLegal_holiday_days(BigDecimal legal_holiday_days) {
        this.legal_holiday_days = legal_holiday_days;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
