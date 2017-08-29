package com.yuyu.soft.entity;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.yuyu.soft.entity.common.CommonEntity;

@Entity
@Table(name = "tb_resume_family_member")
public class ResumeFamilyMember extends CommonEntity {

    /**
     *Comment for <code>serialVersionUID</code>
     */
    private static final long serialVersionUID = 1L;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "resume_id")
    private Resume            resume;                  //简历
    private String            family_member_name;      //姓名
    private Integer           family_relationship;     //与本人关系：1.父亲、2.母亲、3.岳父、4.岳母、5.配偶、6.子女、7.兄弟姐妹
    private String            family_member_unit;      //工作单位
    private String            family_member_duty;      //职务
    private String            family_member_work_place; //所在地址
    private String            family_member_mobile;    //联系电话

    public Resume getResume() {
        return resume;
    }

    public void setResume(Resume resume) {
        this.resume = resume;
    }

    public String getFamily_member_name() {
        return family_member_name;
    }

    public void setFamily_member_name(String family_member_name) {
        this.family_member_name = family_member_name;
    }

    public Integer getFamily_relationship() {
        return family_relationship;
    }

    public void setFamily_relationship(Integer family_relationship) {
        this.family_relationship = family_relationship;
    }

    public String getFamily_member_unit() {
        return family_member_unit;
    }

    public void setFamily_member_unit(String family_member_unit) {
        this.family_member_unit = family_member_unit;
    }

    public String getFamily_member_duty() {
        return family_member_duty;
    }

    public void setFamily_member_duty(String family_member_duty) {
        this.family_member_duty = family_member_duty;
    }

    public String getFamily_member_work_place() {
        return family_member_work_place;
    }

    public void setFamily_member_work_place(String family_member_work_place) {
        this.family_member_work_place = family_member_work_place;
    }

    public String getFamily_member_mobile() {
        return family_member_mobile;
    }

    public void setFamily_member_mobile(String family_member_mobile) {
        this.family_member_mobile = family_member_mobile;
    }

}
