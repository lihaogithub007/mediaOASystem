package com.yuyu.soft.entity;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.yuyu.soft.entity.common.CommonEntity;

/**
 * 请假申请                      
 * @Filename: VacationApply.java
 * @Version: 1.0
 * @Author: 李明
 * @Email: limn_xmj@163.com
 *
 */
@Entity
@Table(name = "tb_vacation_apply")
public class VacationApply extends CommonEntity {

    /**
     *Comment for <code>serialVersionUID</code>
     */
    private static final long           serialVersionUID          = 1L;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User                        user;                                                              //用户
    private Date                        vacation_begin_date;                                               //休假开始日期
    private Date                        vacation_end_date;                                                 //休假结束日期
    @Column(precision = 4, scale = 1)
    private BigDecimal                  vacation_dates;                                                    //休假天数
    private Date                        cancel_vacation_date;                                              //销假日期
    private String                      remark;                                                            //备注

    private String                      attach_ids;                                                        //上传附件(多个附件用逗号分隔)

    @OneToMany(mappedBy = "vacationApply", fetch = FetchType.LAZY)
    @JsonIgnore
    private List<VacationApplyDetails>  vacationApplyDetailsList  = new ArrayList<VacationApplyDetails>(); //请假申请明细

    @OneToMany(mappedBy = "vacationApply", fetch = FetchType.LAZY)
    @JsonIgnore
    private List<VacationApplyApproval> vacationApplyApprovalList = new ArrayList<VacationApplyApproval>(); //请假申请审批

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Date getVacation_begin_date() {
        return vacation_begin_date;
    }

    public void setVacation_begin_date(Date vacation_begin_date) {
        this.vacation_begin_date = vacation_begin_date;
    }

    public Date getVacation_end_date() {
        return vacation_end_date;
    }

    public void setVacation_end_date(Date vacation_end_date) {
        this.vacation_end_date = vacation_end_date;
    }

    public BigDecimal getVacation_dates() {
        return vacation_dates;
    }

    public void setVacation_dates(BigDecimal vacation_dates) {
        this.vacation_dates = vacation_dates;
    }

    public Date getCancel_vacation_date() {
        return cancel_vacation_date;
    }

    public void setCancel_vacation_date(Date cancel_vacation_date) {
        this.cancel_vacation_date = cancel_vacation_date;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public List<VacationApplyDetails> getVacationApplyDetailsList() {
        return vacationApplyDetailsList;
    }

    public void setVacationApplyDetailsList(List<VacationApplyDetails> vacationApplyDetailsList) {
        this.vacationApplyDetailsList = vacationApplyDetailsList;
    }

    public List<VacationApplyApproval> getVacationApplyApprovalList() {
        return vacationApplyApprovalList;
    }

    public void setVacationApplyApprovalList(List<VacationApplyApproval> vacationApplyApprovalList) {
        this.vacationApplyApprovalList = vacationApplyApprovalList;
    }

    public String getAttach_ids() {
        return attach_ids;
    }

    public void setAttach_ids(String attach_ids) {
        this.attach_ids = attach_ids;
    }
}
