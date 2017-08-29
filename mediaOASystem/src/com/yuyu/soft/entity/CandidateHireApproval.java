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
@Table(name = "tb_candidate_hire_approval")
public class CandidateHireApproval extends CommonEntity {

    /**
     *Comment for <code>serialVersionUID</code>
     */
    private static final long serialVersionUID = 1L;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "resume_id")
    private Resume            resume;               //简历   

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "duty_id")
    private Duty              duty;                 //应聘岗位   

    private String            name;                 //姓名
    @Column(precision = 4, scale = 1)
    private BigDecimal        written_score;        //笔试成绩
    @Column(precision = 4, scale = 1)
    private BigDecimal        interview_score;      //面试成绩
    @Column(precision = 4, scale = 1)
    private BigDecimal        final_score;          //最终成绩
    private Integer           hire_result;          //录用结果：1.是、0.否
    private Integer           hired_status;         //录用状态：1.实习、2.试用
    @Column(precision = 12, scale = 2)
    private BigDecimal        current_salary;       //现薪资
    @Column(precision = 12, scale = 2)
    private BigDecimal        expect_salary;        //期望薪资
    private String            personal_information; //个人情况
    @Column(precision = 12, scale = 2)
    private BigDecimal        duty_level;           //核定岗级（工资）
    @Column(precision = 12, scale = 2)
    private BigDecimal        suggest_salary;       //建议薪资

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "department_id")
    private Department        department;           //组别（分配科组）

    private Date              entry_time;           //入职日期
    private String            department_approval;  //科组审批
    private String            leader_approval;      //领导审批
    private Integer           approval_status;      //审批状态：1.未提交、2.已提交

    public Resume getResume() {
        return resume;
    }

    public void setResume(Resume resume) {
        this.resume = resume;
    }

    public Duty getDuty() {
        return duty;
    }

    public void setDuty(Duty duty) {
        this.duty = duty;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getWritten_score() {
        return written_score;
    }

    public void setWritten_score(BigDecimal written_score) {
        this.written_score = written_score;
    }

    public BigDecimal getInterview_score() {
        return interview_score;
    }

    public void setInterview_score(BigDecimal interview_score) {
        this.interview_score = interview_score;
    }

    public BigDecimal getFinal_score() {
        return final_score;
    }

    public void setFinal_score(BigDecimal final_score) {
        this.final_score = final_score;
    }

    public Integer getHire_result() {
        return hire_result;
    }

    public void setHire_result(Integer hire_result) {
        this.hire_result = hire_result;
    }

    public Integer getHired_status() {
        return hired_status;
    }

    public void setHired_status(Integer hired_status) {
        this.hired_status = hired_status;
    }

    public BigDecimal getCurrent_salary() {
        return current_salary;
    }

    public void setCurrent_salary(BigDecimal current_salary) {
        this.current_salary = current_salary;
    }

    public BigDecimal getExpect_salary() {
        return expect_salary;
    }

    public void setExpect_salary(BigDecimal expect_salary) {
        this.expect_salary = expect_salary;
    }

    public String getPersonal_information() {
        return personal_information;
    }

    public void setPersonal_information(String personal_information) {
        this.personal_information = personal_information;
    }

    public BigDecimal getDuty_level() {
        return duty_level;
    }

    public void setDuty_level(BigDecimal duty_level) {
        this.duty_level = duty_level;
    }

    public BigDecimal getSuggest_salary() {
        return suggest_salary;
    }

    public void setSuggest_salary(BigDecimal suggest_salary) {
        this.suggest_salary = suggest_salary;
    }

    public Department getDepartment() {
        return department;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }

    public Date getEntry_time() {
        return entry_time;
    }

    public void setEntry_time(Date entry_time) {
        this.entry_time = entry_time;
    }

    public String getDepartment_approval() {
        return department_approval;
    }

    public void setDepartment_approval(String department_approval) {
        this.department_approval = department_approval;
    }

    public String getLeader_approval() {
        return leader_approval;
    }

    public void setLeader_approval(String leader_approval) {
        this.leader_approval = leader_approval;
    }

    public Integer getApproval_status() {
        return approval_status;
    }

    public void setApproval_status(Integer approval_status) {
        this.approval_status = approval_status;
    }

}
