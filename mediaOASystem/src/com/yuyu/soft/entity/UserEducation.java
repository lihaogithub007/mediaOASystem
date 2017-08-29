package com.yuyu.soft.entity;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.yuyu.soft.entity.common.CommonEntity;

@Entity
@Table(name = "tb_user_education")
public class UserEducation extends CommonEntity {

    /**
     *Comment for <code>serialVersionUID</code>
     */
    private static final long serialVersionUID = 1L;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User              user;                 //用户
    private Date              admission_date;       //入学时间
    private Date              graduation_date;      //毕业时间
    private String            edu_school_name;      //就读院校
    private String            edu_major;            //所学专业
    private String            edu_place;            //地点
    private Integer           edu_degree;           //学历：1.小学、2.初中、3.高中、4.中专、5.大专、6.本科、7.研究生、8.博士生
    private String            edu_offering;         //学位

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
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

    public String getEdu_place() {
        return edu_place;
    }

    public void setEdu_place(String edu_place) {
        this.edu_place = edu_place;
    }

    public Integer getEdu_degree() {
        return edu_degree;
    }

    public void setEdu_degree(Integer edu_degree) {
        this.edu_degree = edu_degree;
    }

    public String getEdu_offering() {
        return edu_offering;
    }

    public void setEdu_offering(String edu_offering) {
        this.edu_offering = edu_offering;
    }

}
