package com.yuyu.soft.entity;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.yuyu.soft.entity.common.CommonEntity;

/**
 * 请假申请审批                    
 * @Filename: VacationApplyApproval.java
 * @Version: 1.0
 * @Author: 李明
 * @Email: limn_xmj@163.com
 *
 */
@Entity
@Table(name = "tb_vacation_apply_approval")
public class VacationApplyApproval extends CommonEntity {

    /**
     *Comment for <code>serialVersionUID</code>
     */
    private static final long serialVersionUID = 1L;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "vacation_apply_id")
    private VacationApply     vacationApply;        //请假申请

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "department_id")
    private Department        department;           //部门/科组

    private String            department_name;      //审批部门（如果为部门/科组，为部门名称，否则为输入）

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "approval_user_id")
    private User              user;                 //审批人

    private String            approval_text;        //审批意见

    public VacationApply getVacationApply() {
        return vacationApply;
    }

    public void setVacationApply(VacationApply vacationApply) {
        this.vacationApply = vacationApply;
    }

    public Department getDepartment() {
        return department;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }

    public String getDepartment_name() {
        return department_name;
    }

    public void setDepartment_name(String department_name) {
        this.department_name = department_name;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getApproval_text() {
        return approval_text;
    }

    public void setApproval_text(String approval_text) {
        this.approval_text = approval_text;
    }

}