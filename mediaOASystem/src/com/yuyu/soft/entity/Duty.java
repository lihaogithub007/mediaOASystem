package com.yuyu.soft.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.yuyu.soft.entity.common.CommonEntity;

/**
 * 岗位表
 *                       
 * @Filename: Duty.java
 * @Version: 1.0
 * @Author: 李明
 * @Email: limn_xmj@163.com
 *
 */
@Entity
@Table(name = "tb_duty")
public class Duty extends CommonEntity {

    /**
     *Comment for <code>serialVersionUID</code>
     */
    private static final long           serialVersionUID          = 1L;

    private String                      duty_name;                                                         //岗位名称

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "department_id")
    @JsonIgnore
    private Department                  department;                                                        //部门科组

    @OneToMany(mappedBy = "duty", fetch = FetchType.EAGER)
    @JsonIgnore
    private List<User>                  userList                  = new ArrayList<User>();

    @OneToMany(mappedBy = "duty", fetch = FetchType.LAZY)
    @JsonIgnore
    private List<DutyRes>               dutyResList               = new ArrayList<DutyRes>();

    @OneToMany(mappedBy = "duty", fetch = FetchType.LAZY)
    @JsonIgnore
    private List<Resume>                resumeList                = new ArrayList<Resume>();

    @OneToMany(mappedBy = "duty", fetch = FetchType.LAZY)
    @JsonIgnore
    private List<ContractSmsRemind>      contractSmsRemindList      = new ArrayList<ContractSmsRemind>();
    
    @OneToMany(mappedBy = "duty", fetch = FetchType.LAZY)
    @JsonIgnore
    private List<ForeignExpert>         foreignExpertList         = new ArrayList<ForeignExpert>();

    @OneToMany(mappedBy = "duty", fetch = FetchType.LAZY)
    @JsonIgnore
    private List<CandidateHireApproval> candidateHireApprovalList = new ArrayList<CandidateHireApproval>();

    public String getDuty_name() {
        return duty_name;
    }

    public void setDuty_name(String duty_name) {
        this.duty_name = duty_name;
    }

    public Department getDepartment() {
        return department;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }

    public List<User> getUserList() {
        return userList;
    }

    public void setUserList(List<User> userList) {
        this.userList = userList;
    }

    public List<DutyRes> getDutyResList() {
        return dutyResList;
    }

    public void setDutyResList(List<DutyRes> dutyResList) {
        this.dutyResList = dutyResList;
    }

    public List<Resume> getResumeList() {
        return resumeList;
    }

    public void setResumeList(List<Resume> resumeList) {
        this.resumeList = resumeList;
    }

	public List<ContractSmsRemind> getContractSmsRemindList() {
		return contractSmsRemindList;
	}

	public void setContractSmsRemindList(List<ContractSmsRemind> contractSmsRemindList) {
		this.contractSmsRemindList = contractSmsRemindList;
	}
    
    

    public List<ForeignExpert> getForeignExpertList() {
        return foreignExpertList;
    }

    public void setForeignExpertList(List<ForeignExpert> foreignExpertList) {
        this.foreignExpertList = foreignExpertList;
    }

    public List<CandidateHireApproval> getCandidateHireApprovalList() {
        return candidateHireApprovalList;
    }

    public void setCandidateHireApprovalList(List<CandidateHireApproval> candidateHireApprovalList) {
        this.candidateHireApprovalList = candidateHireApprovalList;
    }

}
