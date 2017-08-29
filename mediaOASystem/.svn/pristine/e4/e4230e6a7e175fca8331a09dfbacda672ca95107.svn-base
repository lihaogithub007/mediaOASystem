package com.yuyu.soft.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.yuyu.soft.entity.common.CommonEntity;

/**
 * 部门科组表
 *                       
 * @Filename: Duty.java
 * @Version: 1.0
 * @Author: 李明
 * @Email: limn_xmj@163.com
 *
 */
@Entity
@Table(name = "tb_department")
public class Department extends CommonEntity {

    /**
     *Comment for <code>serialVersionUID</code>
     */
    private static final long           serialVersionUID          = 1L;

    private String                      department_name;                                                   //岗位名称

    @OneToMany(mappedBy = "department", fetch = FetchType.LAZY)
    @JsonIgnore
    private List<User>                  userList                  = new ArrayList<User>();

    @OneToMany(mappedBy = "department", fetch = FetchType.LAZY)
    @JsonIgnore
    private List<Duty>                  dutyList                  = new ArrayList<Duty>();

    @OneToMany(mappedBy = "department", fetch = FetchType.LAZY)
    @JsonIgnore
    private List<CandidateHireApproval> candidateHireApprovalList = new ArrayList<CandidateHireApproval>();

    @OneToMany(mappedBy = "department", fetch = FetchType.LAZY)
    @JsonIgnore
    private List<Assessment>            assessmentList            = new ArrayList<Assessment>();

    @OneToMany(mappedBy = "department", fetch = FetchType.LAZY)
    @JsonIgnore
    private List<VacationApplyApproval> vacationApplyApprovalList = new ArrayList<VacationApplyApproval>();

    public String getDepartment_name() {
        return department_name;
    }

    public void setDepartment_name(String department_name) {
        this.department_name = department_name;
    }

    public List<User> getUserList() {
        return userList;
    }

    public void setUserList(List<User> userList) {
        this.userList = userList;
    }

    public List<Duty> getDutyList() {
        return dutyList;
    }

    public void setDutyList(List<Duty> dutyList) {
        this.dutyList = dutyList;
    }

    public List<CandidateHireApproval> getCandidateHireApprovalList() {
        return candidateHireApprovalList;
    }

    public void setCandidateHireApprovalList(List<CandidateHireApproval> candidateHireApprovalList) {
        this.candidateHireApprovalList = candidateHireApprovalList;
    }

    public List<Assessment> getAssessmentList() {
        return assessmentList;
    }

    public void setAssessmentList(List<Assessment> assessmentList) {
        this.assessmentList = assessmentList;
    }

    public List<VacationApplyApproval> getVacationApplyApprovalList() {
        return vacationApplyApprovalList;
    }

    public void setVacationApplyApprovalList(List<VacationApplyApproval> vacationApplyApprovalList) {
        this.vacationApplyApprovalList = vacationApplyApprovalList;
    }

}
