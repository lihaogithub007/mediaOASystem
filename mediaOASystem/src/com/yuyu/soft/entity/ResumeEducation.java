package com.yuyu.soft.entity;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.yuyu.soft.entity.common.CommonEntity;

@Entity
@Table(name = "tb_resume_education")
public class ResumeEducation extends CommonEntity {

    /**
     *Comment for <code>serialVersionUID</code>
     */
    private static final long serialVersionUID = 1L;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "resume_id")
    private Resume            resume;               //简历
    private Date              admission_date;       //入学时间
    private Date              graduation_date;      //毕业时间
    private String            edu_school_name;      //学校
    private String            edu_major;            //专业
    private String            edu_type;             //学习性质：1.统招、2自考
    private String            edu_offering;         //学位

    public Resume getResume() {
        return resume;
    }

    public void setResume(Resume resume) {
        this.resume = resume;
    }

    public Date getAdmission_date() {
        return admission_date;
    }

    public void setAdmission_date(Date admission_date) {
        this.admission_date = admission_date;
    }

    public Date getGraduation_date() {
        return graduation_date;
    }

    public void setGraduation_date(Date graduation_date) {
        this.graduation_date = graduation_date;
    }

    public String getEdu_school_name() {
        return edu_school_name;
    }

    public void setEdu_school_name(String edu_school_name) {
        this.edu_school_name = edu_school_name;
    }

    public String getEdu_major() {
        return edu_major;
    }

    public void setEdu_major(String edu_major) {
        this.edu_major = edu_major;
    }

    public String getEdu_type() {
        return edu_type;
    }

    public void setEdu_type(String edu_type) {
        this.edu_type = edu_type;
    }

    public String getEdu_offering() {
        return edu_offering;
    }

    public void setEdu_offering(String edu_offering) {
        this.edu_offering = edu_offering;
    }

}
